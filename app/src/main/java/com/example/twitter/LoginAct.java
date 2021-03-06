package com.example.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginAct extends AppCompatActivity {

EditText editUname;
EditText editPword;

public void SignupBack(View view)
{
    Intent intent = new Intent(this,CreateAccountActivity.class);
    startActivity(intent);
}

public  void enterFeed()
{
if (ParseUser.getCurrentUser()!=null){
    Intent intent= new Intent(this,UsersActivity.class);
    startActivity(intent);}
}
    public void login(View view)
    {
        if (editUname.getText().toString().matches("")||editPword.getText().toString().matches(""))
        {
            Toast.makeText(this,"username and password are required to sign in",Toast.LENGTH_SHORT).show();

        }
        ParseUser.logInInBackground(editUname.getText().toString(), editPword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e==null&&user!=null)
                {
                    Log.i("Succesfully","signed in "+ ParseUser.getCurrentUser().getUsername());
                    Toast.makeText(getApplicationContext(),"Succesfully signed in as @"+ParseUser.getCurrentUser().getUsername(),Toast.LENGTH_SHORT).show();
                    enterFeed();



                }
                else {
                    //Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //enterFeed();

        getSupportActionBar().hide();

        editUname= findViewById(R.id.editUsernameLg);
        editPword= findViewById(R.id.editTextPasswordLg);


    }
}