package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


public class OTPVerification {

    public class OTP_Verification_Activity extends AppCompatActivity {
        EditText etInputOTP1, etInputOTP2, etInputOTP3, etInputOTP4, etInputOTP5, etInputOTP6;
        TextView tvMobileNo;
        TextView tvResendOTP;
        AppCompatButton btnVerify;
        ProgressDialog progressDialog;
        private String strName, strMobileNo, strEmail, strUsername, strPassword, strVerificationCode;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.otpverification);

            tvMobileNo = findViewById(R.id.tvVerifyOTPMobileno);
            tvResendOTP = findViewById(R.id.tvResendOTP);
            etInputOTP1 = findViewById(R.id.etVerifyOTPInputCode1);
            etInputOTP2 = findViewById(R.id.etVerifyOTPInputCode2);
            etInputOTP3 = findViewById(R.id.etVerifyOTPInputCode3);
            etInputOTP4 = findViewById(R.id.etVerifyOTPInputCode4);
            etInputOTP5 = findViewById(R.id.etVerifyOTPInputCode5);
            etInputOTP6 = findViewById(R.id.etVerifyOTPInputCode6);
            tvResendOTP = findViewById(R.id.tvResendOTP);
            btnVerify = findViewById(R.id.btnVerify);

            strVerificationCode = getIntent().getStringExtra("verificatincode");
            strName = getIntent().getStringExtra("name");
            strMobileNo = getIntent().getStringExtra("mobileno");
            strEmail = getIntent().getStringExtra("emailid");
            strUsername = getIntent().getStringExtra("username");
            strPassword = getIntent().getStringExtra("password");
            btnVerify = findViewById(R.id.btnVerify);

            setUpIntutOTP();
        }

        private void setUpIntutOTP() {
            etInputOTP1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty()) {
                        etInputOTP2.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            etInputOTP2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty()) {
                        etInputOTP3.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            etInputOTP3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty()) {
                        etInputOTP4.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            etInputOTP4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty()) {
                        etInputOTP5.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            etInputOTP5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty()) {
                        etInputOTP6.requestFocus();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}
