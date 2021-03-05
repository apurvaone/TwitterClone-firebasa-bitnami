package com.example.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateAccountActivity extends AppCompatActivity {
    EditText edittext;
    final Calendar myCalendar = Calendar.getInstance();

    EditText fullname;
    EditText dob;
    EditText phone;
    static ParseUser user;

    public void createUser(View view)
    {
         user = new ParseUser();
        user.put("name",fullname.getText().toString());
        user.put("phone",phone.getText().toString());
        user.put("dob",dob.getText().toString());
        Log.i("in","createUser");
        Intent intent= new Intent(getApplicationContext(),UsernamePasswordActivity.class);
        startActivity(intent);

        /*user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e== null) {
                    Log.i("User creation", "Successful!");
                    Toast.makeText(CreateAccountActivity.this,"create username and password",Toast.LENGTH_SHORT).show();


                } else {
                    Log.i("Parse Result", "Failed" + e.toString());
                    Toast.makeText(CreateAccountActivity.this,"Error:"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });*/



    }



    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getSupportActionBar().hide();

        fullname= findViewById(R.id.editFullName);
        phone= findViewById(R.id.editPhone);
        dob= findViewById(R.id.editDob);





       // final Calendar myCalendar = Calendar.getInstance();

        edittext= (EditText) findViewById(R.id.editDob);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateAccountActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}