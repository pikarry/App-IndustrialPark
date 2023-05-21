package com.ipm.ipm.View.Fragment.FactoryFragment;

import static com.ipm.ipm.View.LoginActivity.jwt;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ipm.ipm.Constant.CurrencyFormatter;
import com.ipm.ipm.Controller.ApiController;
import com.ipm.ipm.Model.Account;
import com.ipm.ipm.Model.Contract;
import com.ipm.ipm.Model.Factory;
import com.ipm.ipm.Model.Industrial;
import com.ipm.ipm.Model.Request.CreateContractRequest;
import com.ipm.ipm.Model.Response.FactoriesResponse;
import com.ipm.ipm.R;
import com.ipm.ipm.View.LoginActivity;
import com.ipm.ipm.databinding.FragmentFactoryBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FactoryFragment extends Fragment {

    Industrial industrial;

    public FactoryFragment(Industrial industrial) {
        this.industrial = industrial;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentFactoryBinding binding;
    FactoryAdapter adapter;
    List<Factory> factories;
    int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_factory, container, false);
        binding.tvTenKCN.setText(industrial.getName());
        callApiFactories(page);
        if (factories == null) {
            factories = new ArrayList<>();
        }

        binding.edtSearchFactory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String key = editable.toString();
                ApiController.apiService.searchFactories(industrial.getId(), key).enqueue(new Callback<FactoriesResponse>() {
                    @Override
                    public void onResponse(Call<FactoriesResponse> call, Response<FactoriesResponse> response) {
                        if (response.isSuccessful()) {
                            factories = response.body().getFactories();
                            adapter.setFactories(factories);
                        } else {
                            String err = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                err = jsonObject.getString("message");
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getActivity().getBaseContext(), "Có lỗi xảy ra: " + err, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FactoriesResponse> call, Throwable t) {
                        Toast.makeText(getActivity().getBaseContext(), "Có lỗi xảy ra: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.revFactory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    // RecyclerView đã cuộn đến cuối cùng
                    binding.progressFactory.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.progressFactory.setVisibility(View.GONE);
                            callApiFactories(++page);
                        }
                    }, 1000);
                }
            }
        });

        return binding.getRoot();
    }

    private void callApiFactories(int page) {
        ApiController.apiService.getFactoriesOfIndustrial(industrial.getId(), page).enqueue(new Callback<FactoriesResponse>() {
            @Override
            public void onResponse(Call<FactoriesResponse> call, Response<FactoriesResponse> response) {
                if (response.isSuccessful()) {
                    if (page == 1) {
                        factories = response.body().getFactories();
                        adapter = new FactoryAdapter(factories, getContext());
                        binding.revFactory.setAdapter(adapter);
                        binding.revFactory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    } else {
                        factories.addAll(response.body().getFactories());
                        adapter.setFactories(factories);
                    }
                    if (factories.size() == 0) {
                        Toast.makeText(getContext(), "Không tìm thầy xưởng sản xuất nào", Toast.LENGTH_SHORT).show();
                    }
                    adapter.setiOnClickFactory(new IOnClickFactory() {
                        @Override
                        public void onClickFactory(Factory factory) {
                            showDialog(factory);
                        }
                    });
                } else {
                    String err = "";
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        err = jsonObject.getString("message");
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity().getBaseContext(), "Có lỗi xảy ra: " + err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FactoriesResponse> call, Throwable t) {
                Toast.makeText(getActivity().getBaseContext(), "Có lỗi xảy ra: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void showDialog(Factory factory) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_hire_factory);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = Gravity.CENTER_VERTICAL;
        window.setAttributes(windowAtributes);

        dialog.setCancelable(true);
        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        TextView tvNameFactory = dialog.findViewById(R.id.tvNameFactory);
        TextView tvStatusFactory = dialog.findViewById(R.id.tvStatusFactory);
        TextView tvAreaFactory = dialog.findViewById(R.id.tvAreaFactory);
        EditText edtFullName = dialog.findViewById(R.id.edtFullName);
        EditText edtEmail = dialog.findViewById(R.id.edtEmail);
        EditText edtPhone = dialog.findViewById(R.id.edtPhone);
        EditText edtStartAt = dialog.findViewById(R.id.edtStartAt);
        EditText edtEndAt = dialog.findViewById(R.id.edtEndAt);
        EditText edtDeposit = dialog.findViewById(R.id.edtDeposit);
        Button btnHireFactory = dialog.findViewById(R.id.btnHireFactory);

        switch (factory.getStatus()) {
            case "0":
                tvStatusFactory.setBackgroundResource(R.drawable.fr_stt_0);
                tvStatusFactory.setTextColor(R.color.text_factory_status_0);
                tvStatusFactory.setText("Chưa thuê");
                break;
            case "1":
                tvStatusFactory.setBackgroundResource(R.drawable.fr_stt_1);
                tvStatusFactory.setTextColor(R.color.text_factory_status_1);
                tvStatusFactory.setText("Chờ duyệt");
                break;
            case "2":
                tvStatusFactory.setBackgroundResource(R.drawable.fr_stt_2);
                tvStatusFactory.setTextColor(R.color.text_factory_status_2);
                tvStatusFactory.setText("Đã thuê");
                break;
            default:
                break;
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //get user infor
        ApiController.apiService.getProfile("Bearer " + jwt).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    edtFullName.setText(response.body().getFullname());
                    edtEmail.setText(response.body().getEmail());
                    edtPhone.setText(response.body().getPhone());
                } else {
                    String err = "";
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        err = jsonObject.getString("message");
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity().getBaseContext(), "Có lỗi xảy ra: " + err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi server", Toast.LENGTH_SHORT).show();
            }
        });

        tvNameFactory.setText(factory.getName());
        tvAreaFactory.setText(factory.getAcreage() + " m2");

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        edtStartAt.setText(currentDate + "");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 3); // thêm 3 tháng
        Date date = calendar.getTime();
        String dateEnd = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date);
        edtEndAt.setText(dateEnd + "");

        edtDeposit.setText(CurrencyFormatter.formatVND(factory.getPrice()));

        btnHireFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateContractRequest contract = new CreateContractRequest(factory.getId(), edtStartAt.getText().toString().trim(), edtEndAt.getText().toString().trim());
                ApiController.apiService.createContract("Bearer " + jwt, contract).enqueue(new Callback<Contract>() {
                    @Override
                    public void onResponse(Call<Contract> call, Response<Contract> response) {
                        if (response.isSuccessful()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("ĐÃ GỬI ĐƠN");
                            builder.setMessage("Yêu cầu thuê xưởng của bạn đã được gửi tới ADMIN. Vui lòng để ý email hoặc điện thoại khi ADMIN duyệt yêu cầu của bạn");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        } else {
                            String err = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                err = jsonObject.getString("message");
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getContext(), "Có lỗi xảy ra: " + err, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Contract> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi server ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }
}