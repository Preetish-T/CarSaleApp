package com.example.carsaleapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carsaleapp.Backend.User;
import com.example.carsaleapp.R;


/**
 * This is the profile page with a gps function.
 * @author Zhe Feng u7133440
 */


public class ProfileSectionActivity extends AppCompatActivity {

    //different users with their correspond & different profile pics, extensive
    int getProfileImageResource(String username) {
        switch (username) {
            case "comp2100@anu.edu.au":
                return R.drawable.a;
            case "comp6442@anu.edu.au":
                return R.drawable.b;
            case "Lisa": //name is not important
                return R.drawable.c;
            case "Jonny":
                return R.drawable.d;
            default:
                return R.drawable.d;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_section);

        User user = (User) getIntent().getExtras().getSerializable("USER");
        String username = user.getName();

        //setImageViewResource
        ImageView profileImageView = findViewById(R.id.profileImageView);
        profileImageView.setImageResource(getProfileImageResource(username));


        //setTextViewResource1
        TextView profileTextView = findViewById(R.id.profileTextView);
        String welcomeMessage = "Welcome! User: " ;
        profileTextView.setText(welcomeMessage);


        //setTextViewResource2
        TextView profileTextView2 = findViewById(R.id.profileTextView2);
        String currentUserName = user.getName();
        profileTextView2.setText(currentUserName);

        //Gps
        View btnLocation = findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSectionActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });


        //Go Back to main page
        View backToMainPage = findViewById(R.id.backToMainPage);

        backToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}