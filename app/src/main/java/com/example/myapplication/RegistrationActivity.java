package com.example.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class
RegistrationActivity extends AppCompatActivity {

    EditText etName,etMobileNumber,etEmailId,etUserName,etPassword;
    CheckBox cbHideShowPassword;
    Button btnSignUp;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etName = findViewById(R.id.etRegisterName);
        etMobileNumber = findViewById(R.id.etRegisterMobileNo);
        etEmailId = findViewById(R.id.etRegisterEmailId);
        etUserName = findViewById(R.id.etRegisterUsername);
        etPassword= findViewById(R.id.etRegisterPassword);
        cbHideShowPassword = findViewById(R.id.cbRegisterShowHidePassword);
        btnSignUp = findViewById(R.id.btnVerify);

        cbHideShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod((PasswordTransformationMethod.getInstance()));
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty()) {
                    etName.setError("Please Enter Your Name");
                } else if (etMobileNumber.getText().toString().isEmpty()) {
                    etMobileNumber.setError("Please Enter Your Mobile Number");
                } else if (etMobileNumber.getText().toString().length() != 10) {
                    etMobileNumber.setError("Please Enter Your Valid Mobile Number");
                } else if (etEmailId.getText().toString().isEmpty()) {
                    etEmailId.setError("Please Enter your Email Id");
                } else if (!etEmailId.getText().toString().contains("@") || !etEmailId.getText().toString().contains(".com")) {
                    etEmailId.setError("Please Enter valid email Id");
                } else if (etUserName.getText().toString().isEmpty()) {
                    etUserName.setError("Please Enter Your Username");
                } else if (etUserName.getText().toString().length() < 8) {
                    etUserName.setError("Username must be greater than 8 characters");
                } else if (!etUserName.getText().toString().matches(".[A-Z].")) {
                    etUserName.setError("Please Enter Atleast 1 Uppercase Letter");
                } else if (!etUserName.getText().toString().matches(".[a-z].")) {
                    etUserName.setError("Please Enter Atleast 1 Lowercase Letter");
                } else if (!etUserName.getText().toString().matches(".[0-9].")) {
                    etUserName.setError("Please Enter Atleast 1 Number");
                } else if (!etUserName.getText().toString().matches(".[@,!,#,$,%,&].")) {
                    etUserName.setError("Please Enter Atleast 1 Special Symbol");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Please Enter Your Password");
                } else if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("Password must be grater than 8");
                }
                else {
                    //Toast.makeText(RegistrationActivity.this, "SignUp Successfully Done", Toast.LENGTH_SHORT).show();
                    progressDialog = new ProgressDialog(RegistrationActivity.this);
                    progressDialog.setTitle("Registration...");
                    progressDialog.setMessage("Please Wait");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + etMobileNumber.getText().toString(),
                            60, TimeUnit.SECONDS, RegistrationActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegistrationActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationcode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressDialog.dismiss();
                                    Intent i = new Intent(RegistrationActivity.this,OTPVerificationActivity.class);
                                    i.putExtra("name",etName.getText().toString());
                                    i.putExtra("mobileno",etMobileNumber.getText().toString());
                                    i.putExtra("emailid",etEmailId.getText().toString());
                                    i.putExtra("username",etUserName.getText().toString());
                                    i.putExtra("password",etPassword.getText().toString());
                                    i.putExtra("verificationcode",verificationcode);
                                    startActivity(i);
                                }
                            });

                     userRegister();


                }
            }

            public void userRegister() {
                AsyncHttpClient client = new AsyncHttpClient();//manages the operation over the network
                RequestParams params = new RequestParams();

                params.put("name", etName.getText().toString());
                params.put("mobileno", etMobileNumber.getText().toString());
                params.put("emailid", etEmailId.getText().toString());
                params.put("username", etUserName.getText().toString());
                params.put("password", etPassword.getText().toString());

                client.post("http://192.168.103.154:80/testifyme/userRegistration.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            String status = response.getString("success");
                            if (status.equals("1")) {
                                Toast.makeText(RegistrationActivity.this, "Resitration Successfully done", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Already data exits", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(RegistrationActivity.this, "Could not connect", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }
}