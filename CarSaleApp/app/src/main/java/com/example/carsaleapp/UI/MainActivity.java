package com.example.carsaleapp.UI;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import com.example.carsaleapp.Backend.Car;
import com.example.carsaleapp.Backend.User;
import com.example.carsaleapp.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// @Author: Preetish Thirumalai u7157098
// Main Activity is login page only
public class MainActivity extends AppCompatActivity {
    private EditText emailTextView, passwordTextView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    List<Car> cars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        emailTextView = findViewById(R.id.username);
        passwordTextView = findViewById(R.id.password);
        Button btn = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        Button regbtn = findViewById(R.id.registerButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });
        //go to Registration page
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUserAccount() {
        progressBar.setVisibility(View.VISIBLE);
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_LONG).show();
            return;
        }

        // Signin valid user
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    User user = new User();
                    user.setName(email);
                    user.setWord(password);
                    Intent intent = new Intent(MainActivity.this, com.example.carsaleapp.UI.MainPage.class);
                    intent.putExtra("USER", user);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}