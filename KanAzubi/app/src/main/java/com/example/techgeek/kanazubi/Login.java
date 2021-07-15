package com.example.techgeek.kanazubi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.techgeek.kanazubi.SQL.DatabaseHelper;
import com.example.techgeek.kanazubi.Session.Session;

import org.mindrot.jbcrypt.BCrypt;

public class Login extends AppCompatActivity {

    RelativeLayout rellay1, rellay2;
    Button loginBtn, goToRegisterBtn;
    EditText email, password;
    private DatabaseHelper dbhelper;
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
        setContentView(R.layout.activity_login);

        handler.postDelayed(runnable, 1600);

        dbhelper = new DatabaseHelper(this);
        session = new Session(this);

        rellay1 = findViewById(R.id.rellay1);
        rellay2 = findViewById(R.id.rellay2);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.loginBtn);
        goToRegisterBtn = findViewById(R.id.goToRegisterBtn);

        goToRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Login.this, Register.class);
                Login.this.startActivity(in);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        if(session.loggedIn()){ //check if a session already exists
            Intent i = new Intent(Login.this, Menu.class);
            startActivity(i);
        }
    }

    private void loginUser() {
        String emailStr = email.getText().toString();
        if (dbhelper.checkIfExist(emailStr)) {
            String passwordStr = password.getText().toString();
            String pass = dbhelper.searchForPass(emailStr);
            String nameStr = dbhelper.searchForName(emailStr);
            int idInt = dbhelper.searchForID(emailStr);

            boolean ifMatchPass = BCrypt.checkpw(passwordStr, pass);

            if (!emailStr.equals("") && !passwordStr.equals("")) {
                if (ifMatchPass) {
                    Toast.makeText(Login.this, "Erfolgreich angemeldet!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, Menu.class);
                    session.setLogIn(true); //create a session!
                    session.saveId(idInt); //save userID for the session!
                    session.saveEmail(emailStr); //save email for the session!
                    session.saveUserName(nameStr); //save user's name for the session!
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Passwort ist falsch!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Login.this, "Email/Passwort ist leer!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Login.this, "Email ist invalid!", Toast.LENGTH_SHORT).show();
        }

    }
}