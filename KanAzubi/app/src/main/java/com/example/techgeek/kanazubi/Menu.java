package com.example.techgeek.kanazubi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.techgeek.kanazubi.Session.Session;

public class Menu extends AppCompatActivity {

    RelativeLayout rellay1, rellay2;
    TextView tvName;
    Spinner status, team;
    Button logoutBtn, searchBtn;
    private String statusStr, teamStr;
    private Session session;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        rellay1 = findViewById(R.id.rellay1);
        rellay2 = findViewById(R.id.rellay2);
        logoutBtn = findViewById(R.id.logoutBtn);
        searchBtn = findViewById(R.id.searchBtn);
        tvName = findViewById(R.id.tvName);

        handler.postDelayed(runnable, 1240);

        session = new Session(this);
        if (!session.loggedIn()) {
            logout();
        }
        String userName = session.getUserName();
        tvName.setText(userName);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        status = findViewById(R.id.spinnerStatus);
        ArrayAdapter<String> aaStatus = new ArrayAdapter<>(Menu.this, R.layout.spinner_items, getResources().getStringArray(R.array.status));
        aaStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(aaStatus);

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusStr = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        team = findViewById(R.id.spinnerTeam);
        ArrayAdapter<String> aaTeam = new ArrayAdapter<>(Menu.this, R.layout.spinner_items, getResources().getStringArray(R.array.team));
        aaTeam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        team.setAdapter(aaTeam);

        team.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teamStr = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (session.getId() == 1) {
            String addTask = "Hinzufügen";
            searchBtn.setText(addTask);
            status.setEnabled(false);
            status.setAlpha(.75f);
            team.setEnabled(false);
            team.setAlpha(.75f);
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.getId() == 1 && session.getEmail().equals("admin@dataport.de")) { // if user_ID = 1 AND email = "admin@dataport.de", then open admin plattform for adding new tasks.
                    Intent i = new Intent(Menu.this, AdminPlattform.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(Menu.this, List.class);
                    i.putExtra("status", statusStr);
                    i.putExtra("team", teamStr);
                    startActivity(i);
                }
            }
        });
    }

    private void logout() {
        session.setLogIn(false);
        finish();
        Intent i = new Intent(Menu.this, Login.class);
        startActivity(i);
    }

    public void onBackPressed() {
        //deactivate back button on menu activity
    }
}
