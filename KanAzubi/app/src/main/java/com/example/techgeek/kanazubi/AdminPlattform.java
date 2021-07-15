package com.example.techgeek.kanazubi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.techgeek.kanazubi.SQL.DatabaseHelper;

public class AdminPlattform extends AppCompatActivity {

    TextView et_taskTitle, et_taskDescr, et_taskReleased, et_taskTeam;
    Button addTaskBtn;
    private String title, descr, released, team;
    private DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_plattform);

        dbhelper = new DatabaseHelper(this);

        et_taskTitle = findViewById(R.id.et_taskTitle);
        et_taskDescr = findViewById(R.id.et_taskDescr);
        et_taskReleased = findViewById(R.id.et_taskReleased);
        et_taskTeam = findViewById(R.id.et_taskTeam);
        addTaskBtn = findViewById(R.id.addTaskBtn);

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = et_taskTitle.getText().toString();
                descr = et_taskDescr.getText().toString();
                released = et_taskReleased.getText().toString();
                team = et_taskTeam.getText().toString();
                dbhelper.addTask(title,descr,released,team);
                Intent i = new Intent(AdminPlattform.this, Menu.class);
                startActivity(i);
                finish();
            }
        });
    }
}
