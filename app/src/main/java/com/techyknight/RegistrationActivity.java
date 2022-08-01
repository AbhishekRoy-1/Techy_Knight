package com.techyknight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.techyknight.models.UserModel;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {
    EditText name, signUpEmail, signUpPassword, confirmSignUpPassword;
    TextView signIn;
    Button signUpBtn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        auth= FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        name=findViewById(R.id.name);
        signUpEmail=findViewById(R.id.signUp_email);
        signUpPassword=findViewById(R.id.signUp_pass);
        signIn=findViewById(R.id.signIn);
        signUpBtn=findViewById(R.id.signUp_button);
        confirmSignUpPassword=findViewById(R.id.confirmPass);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    private void createUser() {
        String userName= name.getText().toString();
        String userEmail= signUpEmail.getText().toString();
        String userPassword=signUpPassword.getText().toString();
        String ConfirmPassword= confirmSignUpPassword.getText().toString();
        if(userName.isEmpty()){
            name.setError("Full name is required");
            name.requestFocus();
            return;
        }
        if(userEmail.isEmpty()){
            signUpEmail.setError("Email is required");
            signUpEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            signUpEmail.setError("Provide a Valid Email");
            signUpEmail.requestFocus();
            return;

        }
        if(userPassword.isEmpty()){
            signUpPassword.setError("Password is required");
            signUpPassword.requestFocus();
            return;
        }
        if(ConfirmPassword.isEmpty()){
            confirmSignUpPassword.setError("Confirm your Password");
            confirmSignUpPassword.requestFocus();
            return;
        }
        if(!ConfirmPassword.equals(userPassword)){
            confirmSignUpPassword.setError("Password don't match");
            confirmSignUpPassword.requestFocus();
            return;
        }
        if(userPassword.length()<5){
            signUpPassword.setError("Password length too short");
            signUpPassword.requestFocus();
        }

        //create User
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            UserModel userModel= new UserModel(userName,userEmail,userPassword);
                            String id= Objects.requireNonNull(task.getResult().getUser()).getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);
                            Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                           // startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }
                        else
                        {
                            Toast.makeText(RegistrationActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}