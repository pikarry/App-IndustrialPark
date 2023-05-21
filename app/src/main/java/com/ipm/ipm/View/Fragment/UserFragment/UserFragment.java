package com.ipm.ipm.View.Fragment.UserFragment;

import static com.ipm.ipm.View.LoginActivity.jwt;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ipm.ipm.Controller.ApiController;
import com.ipm.ipm.Model.Account;
import com.ipm.ipm.Model.Request.ChangePasswordRequest;
import com.ipm.ipm.R;
import com.ipm.ipm.View.LoginActivity;
import com.ipm.ipm.View.MainActivity;
import com.ipm.ipm.databinding.FragmentUserBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment {

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);
        getUserProfile();

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = binding.edtFullName.getText().toString().trim();
                String phone = binding.edtPhone.getText().toString().trim();
                String address = binding.edtAddress.getText().toString().trim();
                String gender = "nam";
                if (binding.rdNu.isChecked()){
                    gender = "nữ";
                }
                Account account = new Account(phone, address, fullName, gender);
                ApiController.apiService.updateProfile("Bearer "+ jwt, account).enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
                        }else {
                            String err="";
                            try {
                                JSONObject jsonObject= new JSONObject(response.errorBody().string());
                                err= jsonObject.getString("message");
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getContext(), "Có lỗi xảy ra: "+ err, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_reset_password);

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
                ImageView imgEye = dialog.findViewById(R.id.imgEye);
                ImageView imgEyeNewPassword = dialog.findViewById(R.id.imgEyeNewPassword);
                ImageView imgEyeConfirmNewPass = dialog.findViewById(R.id.imgEyeConfirmNewPass);
                EditText edtCurrentPassword = dialog.findViewById(R.id.edtCurrentPassword);
                EditText edtNewPassword = dialog.findViewById(R.id.edtNewPassword);
                EditText edtConfirmPassword = dialog.findViewById(R.id.edtConfirmPassword);
                Button btnReset = dialog.findViewById(R.id.btnReset);

                btnReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String oldPassword = edtCurrentPassword.getText().toString().trim();
                        String newPassword = edtNewPassword.getText().toString().trim();
                        String confirmPassword = edtConfirmPassword.getText().toString().trim();

                        if (!newPassword.equals(confirmPassword)){
                            Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                        }else {
                            ChangePasswordRequest passwordRequest = new ChangePasswordRequest(oldPassword, newPassword);
                            ApiController.apiService.changePassword("Bearer "+ jwt, passwordRequest).enqueue(new Callback<Account>() {
                                @Override
                                public void onResponse(Call<Account> call, Response<Account> response) {
                                    if (response.isSuccessful()){
                                        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }else {
                                        String err="";
                                        try {
                                            JSONObject jsonObject= new JSONObject(response.errorBody().string());
                                            err= jsonObject.getString("message");
                                        } catch (JSONException | IOException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getContext(), "Có lỗi xảy ra: "+ err, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Account> call, Throwable t) {
                                    Toast.makeText(getContext(), "Lỗi server", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                final boolean[] canLookPassword = {false, false, false};
                imgEye.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!canLookPassword[0]) {
                            canLookPassword[0] = true;
                            imgEye.setImageResource(R.drawable.dis_eye);
                            edtCurrentPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        } else {
                            canLookPassword[0] = false;
                            imgEye.setImageResource(R.drawable.eye);
                            edtCurrentPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                    }
                });
                imgEyeNewPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!canLookPassword[1]) {
                            canLookPassword[1] = true;
                            imgEyeNewPassword.setImageResource(R.drawable.dis_eye);
                            edtNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        } else {
                            canLookPassword[1] = false;
                            imgEyeNewPassword.setImageResource(R.drawable.eye);
                            edtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                    }
                });
                imgEyeConfirmNewPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!canLookPassword[2]) {
                            canLookPassword[2] = true;
                            imgEyeConfirmNewPass.setImageResource(R.drawable.dis_eye);
                            edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        } else {
                            canLookPassword[2] = false;
                            imgEyeConfirmNewPass.setImageResource(R.drawable.eye);
                            edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                    }
                });

                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        return binding.getRoot();
    }

    private void getUserProfile() {
        ApiController.apiService.getProfile("Bearer " + jwt).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account = response.body();
                    binding.edtEmail.setText(account.getEmail());
                    binding.edtFullName.setText(account.getFullname());
                    binding.edtPhone.setText(account.getPhone());
                    binding.edtAddress.setText(account.getAddress());
                    if (account.getGender().equalsIgnoreCase("nam")) {
                        binding.rdNam.setChecked(true);
                    } else {
                        binding.rdNu.setChecked(true);
                    }
                    Glide.with(getContext()).load(account.getAvt()).placeholder(R.drawable.icon).into(binding.imgAvt);
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
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}