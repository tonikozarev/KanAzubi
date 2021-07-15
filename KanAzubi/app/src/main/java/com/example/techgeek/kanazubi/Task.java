package com.example.techgeek.kanazubi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techgeek.kanazubi.SQL.DatabaseHelper;
import com.example.techgeek.kanazubi.Session.Session;

import java.util.ArrayList;

public class Task extends AppCompatActivity {

    TextView taskTitle, taskReleased, taskTeam, taskStatus, taskDescr;
    Button joinTaskBtn;
    private ArrayList<Integer> listID;
    private int taskID, taskNr, userID;
    private String openTask = "Offen", inProgressTask = "In Bearbeitung", buttonText;
    private DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        dbhelper = new DatabaseHelper(this);

        Session session = new Session(this);
        userID = session.getId();

        taskTitle = findViewById(R.id.taskTitle);
        taskReleased = findViewById(R.id.taskReleased);
        taskTeam = findViewById(R.id.taskTeam);
        taskStatus = findViewById(R.id.taskStatus);
        taskDescr = findViewById(R.id.taskDescr);
        taskDescr.setMovementMethod(new ScrollingMovementMethod());
        joinTaskBtn = findViewById(R.id.joinTaskBtn);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            taskID = bundle.getInt("taskID");
            listID = bundle.getIntegerArrayList("listID");
        }

        assert listID != null;
        for(int i = 0; i < listID.size(); i++){
            if(i == taskID){
                taskNr = listID.get(i);
                addData(taskNr);
            }
        }

        joinTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonText.equals(openTask)) {
                    dbhelper.acceptTask(userID, taskNr);
                } else if(buttonText.equals(inProgressTask)){
                    dbhelper.doneTask(taskNr);
                }
                Intent i = new Intent(Task.this, Menu.class);
                startActivity(i);
                finish();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void addData(final int taskNr) {
        Cursor crs = dbhelper.queryList();
        if (crs.getCount() == 0) {
            Toast.makeText(this, "Die Liste ist leer", Toast.LENGTH_LONG).show();
        } else {
            while (crs.moveToNext()) {
                if(taskNr == crs.getInt(0)){
                    taskTitle.setText(crs.getString(1));
                    taskReleased.setText("Veröffentlicht seit: " + crs.getString(5));
                    taskTeam.setText("Team: " + crs.getString(2));
                    taskStatus.setText("Status: " + crs.getString(3));
                    buttonText = crs.getString(3);
                    taskDescr.setText(crs.getString(6));

                    String doneTask = "Erledigt";
                    if (buttonText.equals(openTask)) {
                        joinTaskBtn.setText("Annehmen");
                    } else if (buttonText.equals(inProgressTask)) {
                        joinTaskBtn.setText("Erledigen");
                    } else if(buttonText.equals(doneTask)){
                        joinTaskBtn.setEnabled(false);
                        joinTaskBtn.setVisibility(View.GONE);
                    }
                }
            }
        }
        crs.close();
        dbhelper.close();
    }
}