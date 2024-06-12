package com.example.hotelbookingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbookingapplication.dto.AuthResponse;
import com.example.hotelbookingapplication.dto.LoginRequest;
import com.example.hotelbookingapplication.dto.UserRequest;
import com.example.hotelbookingapplication.managers.CallAuthSignIn;
import com.example.hotelbookingapplication.managers.CallAuthSignUp;
import com.example.hotelbookingapplication.managers.interfaces.iSignIn;

public class SignUpActivity extends AppCompatActivity {
    private TextView txtEmail, txtPassword, txtFullName;
    private Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setView();
        setEvent();
    }

    private void setEvent() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String fullname = txtFullName.getText().toString().trim();

                UserRequest userRequest = new UserRequest(fullname, email, password, "ROLE_CUSTOMER");
                getResponseSignUp(userRequest);
            }
        });
    }

    private void setView() {
        txtEmail = findViewById(R.id.textEmailSignUp);
        txtPassword = findViewById(R.id.textPasswordSignUp);
        txtFullName = findViewById(R.id.textFullnameSignup);
        btnSignUp = findViewById(R.id.btnSignup);
    }

    private void getResponseSignUp(UserRequest userRequest){
        CallAuthSignUp.get(userRequest, new iSignIn() {
            @Override
            public void onSuccess(AuthResponse res) {
                AuthResponse authResponse = res;
                Log.d("AAA", authResponse.toString());
                if(authResponse.getJwt() != null) {
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    Toast.makeText(SignUpActivity.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignUpActivity.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA", "error: " + t);
            }
        });
    }



}