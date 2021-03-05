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

    public void createUser(View view)
    {
        ParseUser user = new ParseUser();
        user.put("name",fullname.getText());
        user.put("phone",phone.getText());
        user.put("dob",dob.getText());

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e== null) {
                    Log.i("User creation", "Successful!");
                    Toast.makeText(CreateAccountActivity.this,"create username and password",Toast.LENGTH_LONG).show();
                } else {
                    Log.i("Parse Result", "Failed" + e.toString());
                    Toast.makeText(CreateAccountActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
            }
        });


        Intent intent= new Intent(this,UsernamePasswordActivity.class);
        startActivity(intent);

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