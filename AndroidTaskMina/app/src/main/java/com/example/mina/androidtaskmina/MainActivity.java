package com.example.mina.androidtaskmina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView percentage, user_list, user_orrder;
    Button button;
    EditText userID;
    ArrayList<pagesObject> lists;
    ArrayList<userObject> users;
    claculationsClass claculations;
    double percentNum;
    int order;
    String userList = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SqlliteOpenHelper s = new SqlliteOpenHelper(MainActivity.this);
        percentage = (TextView) findViewById(R.id.percentage);
        user_list = (TextView) findViewById(R.id.user_list);
        user_orrder = (TextView) findViewById(R.id.user_orrder);
        userID = (EditText) findViewById(R.id.uID);
        button = (Button) findViewById(R.id.button);

        /*
insert data to users table
 */
        userObject c = new userObject();
        c.setName("Jana");
        c.setId("1");
        userObject c2 = new userObject();
        c2.setName("mohamed");
        c2.setId("2");
        s.adduser(c);
        s.adduser(c2);

        //   get data to users table
        users = s.getAllusers();
        for (int i = 0; i < users.size(); i++) {

            userList = userList + "user ID: " + users.get(i).getId() + "  User Name :" + users.get(i).getName() + "\n";
        }
        /////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        //  insert data to ReadPages table
        s.delete();
        pagesObject object = new pagesObject();
        object.setFrom(1);
        object.setTo(20);
        object.setuID("1");
        pagesObject object2 = new pagesObject();
        object2.setFrom(33);
        object2.setTo(47);
        object2.setuID("2");
        pagesObject object3 = new pagesObject();
        object3.setFrom(9);
        object3.setTo(30);
        object3.setuID("1");
        pagesObject object4 = new pagesObject();
        object4.setFrom(39);
        object4.setTo(67);
        object4.setuID("2");
        s.adduserPages(object);
        s.adduserPages(object2);
        s.adduserPages(object3);
        s.adduserPages(object4);

        //  get data from ReadPages table

        lists = s.getAllpages();
        claculations = new claculationsClass(lists, users);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID.getText().toString().equals("1") || userID.getText().toString().equals("2")) {
                    claculations.claculate();
                    percentNum = claculations.claculetePercentage(userID.getText().toString());
                    percentage.setText("percentNum = " + String.valueOf(percentNum));
                    order = claculations.getOreder(userID.getText().toString());
                    user_orrder.setText("user order :" + String.valueOf(order));

                    user_list.setText(userList);

                } else {
                    Toast.makeText(MainActivity.this, "user id not exists", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

}
