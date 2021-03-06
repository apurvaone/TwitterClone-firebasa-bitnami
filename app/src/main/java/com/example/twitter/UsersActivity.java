package com.example.twitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    ArrayList<String> users= new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater= new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_file,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.Tweet)
        {
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("What's happening...");
            EditText tweetEditText= new EditText(this);
            builder.setView(tweetEditText);
            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("info",tweetEditText.getText().toString());

                    ParseObject tweet = new ParseObject("Tweet");
                    tweet.put("tweet",tweetEditText.getText().toString());
                    tweet.put("username",ParseUser.getCurrentUser().getUsername());

                    tweet.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                       if (e==null)
                       {
                           Toast.makeText(UsersActivity.this,"Tweet done",Toast.LENGTH_SHORT).show();
                       }
                       else {
                           Toast.makeText(UsersActivity.this,"Tweet Failed",Toast.LENGTH_SHORT).show();

                       }
                        }
                    });
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("info","tweet cancelled");
                    dialog.cancel();
                }
            });
            builder.show();
        }
        else  if (item.getItemId()==R.id.Logout)
        {
            ParseUser.logOut();
            Intent intent= new Intent(getApplicationContext(),LoginAct.class);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        setTitle("User List");

        ListView listView= findViewById(R.id.listView);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_checked,users);
listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView= (CheckedTextView) view;
                if (checkedTextView.isChecked()){
                    Log.i("Info","Checked");
                    ParseUser.getCurrentUser().add("isFollowing",users.get(position));
                }else {
                    Log.i("Info","Not checked");
                    ParseUser.getCurrentUser().getList("isFollowing").remove(users.get(position));
                    List tempUsers= ParseUser.getCurrentUser().getList("isFollowing");
                    ParseUser.getCurrentUser().remove("isFollowing");
                    ParseUser.getCurrentUser().put("isFollowing",tempUsers);
                }
                ParseUser.getCurrentUser().saveInBackground();
            }
        });


        ParseQuery<ParseUser> query= ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e==null&&objects.size()>0){
                    for(ParseUser user:objects)
                    {
                        users.add(user.getUsername());
                    }
                    arrayAdapter.notifyDataSetChanged();

                    for (String username:users)
                    {
                        if (ParseUser.getCurrentUser().getList("isFollowing").contains(username))
                        {
                            listView.setItemChecked(users.indexOf(username),true);
                        }
                    }
                }
            }
        });

    }
}