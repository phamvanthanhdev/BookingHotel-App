package com.example.hotelbookingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbookingapplication.dto.AuthResponse;
import com.example.hotelbookingapplication.dto.LoginRequest;
import com.example.hotelbookingapplication.managers.CallAuthSignIn;
import com.example.hotelbookingapplication.managers.interfaces.iSignIn;

public class SignInActivity extends AppCompatActivity {
    private TextView txtUsername, txtPassword, txtSignUpRedirect;
    private Button btnSignIn;
    private CheckBox cbRememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setView();
        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "").toString();
        String password = sharedPreferences.getString("password", "").toString();
        txtUsername.setText(email);
        txtPassword.setText(password);
    }

    private void setEvent() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                LoginRequest loginRequest = new LoginRequest(email, password);

                getResponseSignIn(loginRequest);
            }
        });

        txtSignUpRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setView() {
        txtUsername = findViewById(R.id.textEmailLogin);
        txtPassword = findViewById(R.id.textPasswordLogin);
        btnSignIn = findViewById(R.id.btnSignIn);
        cbRememberPass = findViewById(R.id.cbRememberPassword);
        txtSignUpRedirect = findViewById(R.id.textSignUpRedirect);
    }

    private void getResponseSignIn(LoginRequest loginRequest){
        CallAuthSignIn.get(loginRequest, new iSignIn() {
            @Override
            public void onSuccess(AuthResponse res) {
                AuthResponse authResponse = res;
                Log.d("AAA", authResponse.toString());
                if(authResponse.getJwt() != null) {
                    if (cbRememberPass.isChecked()) {
                        String email = txtUsername.getText().toString().trim();
                        String password = txtPassword.getText().toString().trim();
                        rememberPassword(email, password, authResponse.getJwt());
                    } else {
                        notRememberPassword(authResponse.getJwt());
                    }
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else{
                    Toast.makeText(SignInActivity.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA", "error: " + t);
            }
        });
    }

    private void rememberPassword(String email, String password, String jwt) {
        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("jwt", jwt);
        editor.apply();
    }

    private void notRememberPassword(String jwt) {
        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.putString("jwt", jwt);
        editor.apply();
//        sharedPreferences.edit().clear().apply();
    }
}