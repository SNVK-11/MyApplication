package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;

    CheckBox cbShowHidePassword;

    Button btnLogin;

    TextView tvSignup;

    ProgressDialog progressDialog;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    AppCompatButton btnSignInWithGoogle;
    // private com.google.android.gms.auth.api.signin.GoogleSignInOptions GoogleSignInOptions;


    //    GoogleSignInOption  => GoogleSignIn =>GoogleSignInClient =>GoogleSignIn =>GoogleSignInAccount (name,email,photo)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        onStart();

        etUsername = findViewById(R.id.etLoginUserName);
        etPassword = findViewById(R.id.etLoginPassword);
        cbShowHidePassword = findViewById(R.id.cbLoginShowHidePassword);
        btnLogin = findViewById(R.id.bynLogin);
        tvSignup = findViewById(R.id.tvLoginSignup);
        btnSignInWithGoogle = findViewById(R.id.acbtnLoginSignInWithGoogle);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);

        btnSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }

            private void signIn() {
            }
        });

        cbShowHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etPassword.getText().toString().isEmpty()) {
                    etUsername.setError("Please Enter Your Username");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Please Enter Your Password");
                } else if (etUsername.getText().toString().length() < 8) {
                    etUsername.setError("Please Enter 8 Character Username");
                } else if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("Please Enter 8 Character Passaword ");
                } else if (!etUsername.getText().toString().matches(".*[A-Z].*")) {
                    etUsername.setError("Please Used 1 Uppercase Letter");
                } else if (!etUsername.getText().toString().matches(".*[a-z].*")) {
                    etUsername.setError("Please Used 1 LowerCase Letter");
                } else if (!etUsername.getText().toString().matches(".*[1-9].*")) {
                    etUsername.setError("Please Used 1 Number");
                } else if (!etUsername.getText().toString().matches(".*[@,#,&,$,!].*")) {
                    etUsername.setError("Please Used 1 Special Symbol");
                } else {
                    // Intent i = new Intent(RegisrationActivity.this,LoginActivity.class);
                    //  startActivity(i);

                    Toast.makeText(LoginActivity.this, "Login Successfully Done", Toast.LENGTH_SHORT).show();
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Login User");
                    progressDialog.setMessage("Please Wait..");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    userLogin();
                }
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });

    }

    public void userLogin() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username", etUsername.getText().toString());
        params.put("password", etPassword.getText().toString());


        client.post("http//192.168.143.154:80/testifyme/userLogin.php", params,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            String status = response.getString("success");
                            if (status.equals("1"))
                                Toast.makeText(LoginActivity.this, "Login Successfully done", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, Field.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Data does't exits", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(LoginActivity.this, "Could not connect", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

    }

    private Intent GoogleSignInIntent() {
        Intent i = GoogleSignInIntent();
        startActivityForResult(i,999);
        return i;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 999){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                Intent intent= new Intent(LoginActivity.this,HomeFragment.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListener);
}
}





