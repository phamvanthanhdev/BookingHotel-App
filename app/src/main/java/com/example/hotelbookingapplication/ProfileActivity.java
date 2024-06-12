package com.example.hotelbookingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbookingapplication.dto.UserResponse;
import com.example.hotelbookingapplication.managers.CallProfile;
import com.example.hotelbookingapplication.managers.CallUpdatePassword;
import com.example.hotelbookingapplication.managers.interfaces.iProfile;
import com.example.hotelbookingapplication.untils.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {
    private TextInputEditText edtPasswordNew, edtPasswordConfirm;
    private TextView txtEmail, txtFullName;
    private Button btnUpdate;
    private ImageButton btnLogout;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setView();
        setEvent();
        getDataProfile();
        setBottomNavView();

    }

    private void setBottomNavView() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.btnMenuHome){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                }
                if(item.getItemId() == R.id.btnMenuOrders){
                    startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                    return true;
                }
                if(item.getItemId() == R.id.btnMenuProfile){
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    return true;
                }
                if(item.getItemId() == R.id.btnMenuLogout){
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    return true;
                }

                return false;
            }
        });
    }

    private void getDataProfile(){
        String jwt = Common.getJwtToken(this);
        jwt = "Bearer " + jwt.trim();
        Log.d("AAA", "token " + jwt);
        CallProfile.get(jwt, new iProfile() {
            @Override
            public void onSuccess(UserResponse userResponse) {
                Log.d("AAA" , userResponse.toString());
                setViewData(userResponse);
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA" , "Error" + t);
            }
        });
    }

    private void setViewData(UserResponse userResponse) {
        txtEmail.setText(userResponse.getEmail());
        txtFullName.setText(userResponse.getFullName());
    }

    private void setEvent() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = edtPasswordNew.getText().toString();
                String confirmPassword = edtPasswordConfirm.getText().toString();
                if(!newPassword.equals(confirmPassword)){
                    Toast.makeText(ProfileActivity.this, "Confirm Password is invalid!", Toast.LENGTH_SHORT).show();
                }else{
                    updatePassword(newPassword);
                }
            }
        });
    }

    public void updatePassword(String newPassword){
        String jwt = Common.getJwtToken(this);
        jwt = "Bearer " + jwt.trim();
        CallUpdatePassword.update(jwt, newPassword, new iProfile() {
            @Override
            public void onSuccess(UserResponse userResponse) {
                Toast.makeText(ProfileActivity.this, "Change password successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA","Error: " + t);
                Toast.makeText(ProfileActivity.this, "Error change password: " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setView() {
        edtPasswordNew = findViewById(R.id.edtPasswordNew);
        edtPasswordConfirm = findViewById(R.id.edtPasswordConfirm);
        txtEmail = findViewById(R.id.textEmail);
        txtFullName = findViewById(R.id.textFullName);
        btnLogout = findViewById(R.id.btnLogout);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
}