package com.example.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class UsernamePasswordActivity extends AppCompatActivity {

    EditText editUsername;
    EditText editPassword;
public  void signUp(View view)
{
   CreateAccountActivity.user.setUsername(editUsername.getText().toString());
   CreateAccountActivity.user.setPassword(editPassword.getText().toString());

   CreateAccountActivity.user.signUpInBackground(new SignUpCallback() {
       @Override
       public void done(ParseException e) {
           if (e==null){
               Log.i("Sign up ","done @"+ ParseUser.getCurrentUser().getUsername());
               Toast.makeText(getApplicationContext(),"Account Created Successfully",Toast.LENGTH_SHORT).show();
           }
           else {Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
               e.printStackTrace();
           }

       }
   });




}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username_password);

        editUsername= findViewById(R.id.editUsername);
        editPassword= findViewById(R.id.editPassword);
    }
}