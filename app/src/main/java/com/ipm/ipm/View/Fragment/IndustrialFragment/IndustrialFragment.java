package com.ipm.ipm.View.Fragment.IndustrialFragment;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ipm.ipm.Controller.ApiController;
import com.ipm.ipm.Helper.ErrorHelper;
import com.ipm.ipm.Model.Industrial;
import com.ipm.ipm.Model.Response.IndustrialResponse;
import com.ipm.ipm.R;
import com.ipm.ipm.View.Fragment.FactoryFragment.FactoryFragment;
import com.ipm.ipm.databinding.FragmentHomeBinding;
import com.ipm.ipm.databinding.FragmentIndustrialBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndustrialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndustrialFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IndustrialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IndustrialFragment newInstance(String param1, String param2) {
        IndustrialFragment fragment = new IndustrialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentIndustrialBinding binding;
    IndustrialAdapter adapter;
    List<Industrial> industrials;
    int PAGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_industrial, container, false);
        callApiIndustrial(PAGE);
        if (industrials == null) {
            industrials = new ArrayList<>();
        }
        binding.revIndustrial.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    // RecyclerView đã cuộn đến cuối cùng
                    binding.progressIndustrial.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.progressIndustrial.setVisibility(View.GONE);
                            callApiIndustrial(++PAGE);
                        }
                    }, 1000);

                }
            }
        });

        binding.edtSearchIndustrial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String key = editable.toString();
                ApiController.apiService.searchIndustrial(key).enqueue(new Callback<IndustrialResponse>() {
                    @Override
                    public void onResponse(Call<IndustrialResponse> call, Response<IndustrialResponse> response) {
                        if (response.isSuccessful()) {
                            industrials = response.body().getIndustrials();
                            adapter.setIndustrials(industrials);
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
                    public void onFailure(Call<IndustrialResponse> call, Throwable t) {
                        Toast.makeText(getActivity().getBaseContext(), "Có lỗi xảy ra: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return binding.getRoot();
    }

    private void callApiIndustrial(int page) {
        ApiController.apiService.getIndustrials(page).enqueue(new Callback<IndustrialResponse>() {
            @Override
            public void onResponse(Call<IndustrialResponse> call, Response<IndustrialResponse> response) {
                if (response.isSuccessful()) {
                    if (page == 1) {
                        industrials = response.body().getIndustrials();
                        adapter = new IndustrialAdapter(industrials, getContext());
                        binding.revIndustrial.setAdapter(adapter);
                        binding.revIndustrial.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    } else {
                        industrials.addAll(response.body().getIndustrials());
                        adapter.setIndustrials(industrials);
                    }

                    adapter.setiOnClickIndustrial(new IOnClickIndustrial() {
                        @Override
                        public void clickIndustrial(Industrial industrial) {
                            ApiController.apiService.getIndustrialById(industrial.getId()).enqueue(new Callback<Industrial>() {
                                @Override
                                public void onResponse(Call<Industrial> call, Response<Industrial> response) {
                                    if (response.isSuccessful()){
                                        Industrial industrial = response.body();
                                        final Dialog dialog = new Dialog(getContext());
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.dialog_infor_industrial);

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

                                        TextView tvTenKCN = dialog.findViewById(R.id.tvTenKCN);
                                        ImageView imgDong = dialog.findViewById(R.id.imgDong);
                                        TextView tvVitri = dialog.findViewById(R.id.tvViTri);
                                        TextView tvTongDienTich = dialog.findViewById(R.id.tvTongDienTich);
                                        TextView tvTongNX = dialog.findViewById(R.id.tvTongSoNX);
                                        TextView tvMoTa = dialog.findViewById(R.id.tvMoTa);
                                        ImageView imgAnh = dialog.findViewById(R.id.imgIndustrialImage);
                                        TextView tvBanDo = dialog.findViewById(R.id.tvBanDo);
                                        Button btnDsX = dialog.findViewById(R.id.btnDSXuong);

                                        tvTenKCN.setText(industrial.getName());
                                        tvVitri.setText(industrial.getAddress());
                                        tvTongDienTich.setText(industrial.getTotalAcreage()*1.0/10000 + "ha");
                                        tvTongNX.setText(industrial.getFactories() + " nhà xưởng");
                                        tvMoTa.setText(industrial.getDescription());
                                        Glide.with(getActivity()).load(industrial.getImage()).into(imgAnh);

                                        tvBanDo.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                try{
                                                    Uri mapUri = Uri.parse(industrial.getLocation());
                                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                                                    mapIntent.setPackage("com.google.android.apps.maps");
                                                    startActivity(mapIntent);
                                                } catch (Exception e){
                                                    Toast.makeText(getActivity(), "Không định vị được. Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                        imgDong.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });

                                        btnDsX.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                getActivity()
                                                        .getSupportFragmentManager()
                                                        .beginTransaction().replace(R.id.container, new FactoryFragment(industrial))
                                                        .addToBackStack(null)
                                                        .commit();
                                                dialog.dismiss();
                                            }
                                        });

                                        dialog.show();
                                    }else {
                                        ErrorHelper.toastError(getActivity(), response.errorBody());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Industrial> call, Throwable t) {
                                    Toast.makeText(getActivity(), "lỗi đường truyền", Toast.LENGTH_SHORT).show();
                                }
                            });
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
            public void onFailure(Call<IndustrialResponse> call, Throwable t) {
                Toast.makeText(getActivity().getBaseContext(), "Có lỗi xảy ra: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}