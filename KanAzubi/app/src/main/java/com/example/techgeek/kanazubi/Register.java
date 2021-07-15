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

import com.example.techgeek.kanazubi.Model.User;
import com.example.techgeek.kanazubi.SQL.DatabaseHelper;

import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    RelativeLayout rellayReg1, rellayReg2;
    Button goToLogin, registerBtn;
    EditText email, password, confirmPassword, name;
    private DatabaseHelper dbhelper;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellayReg1.setVisibility(View.VISIBLE);
            rellayReg2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbhelper = new DatabaseHelper(this);

        rellayReg1 = findViewById(R.id.rellayReg1);
        rellayReg2 = findViewById(R.id.rellayReg2);
        registerBtn = findViewById(R.id.registerBtn);
        goToLogin = findViewById(R.id.goToLoginBtn);

        handler.postDelayed(runnable, 1600);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBtn.setEnabled(false);
                registerUser();
                registerBtn.setEnabled(true);
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin.setEnabled(false);
                Intent in = new Intent(Register.this, Login.class);
                Register.this.startActivity(in);
                goToLogin.setEnabled(true);
            }
        });
    }

    //check the structure of the entered email and returns true if it's valid
    public static boolean isEmailValid(String email) {
        String emailPattern = "^[a-zA-Z0-9_*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailPattern);
        return email != null && pat.matcher(email).matches();
    }

    public void registerUser() {
        email = findViewById(R.id.addEmail);
        name = findViewById(R.id.addName);
        password = findViewById(R.id.addPassword);
        confirmPassword = findViewById(R.id.confirmPassword);

        String emailStr = email.getText().toString().trim();
        String nameStr = name.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        String confirmPasswordStr = confirmPassword.getText().toString().trim();

        if(dbhelper.checkIfExist(emailStr)) {
            Toast.makeText(Register.this, "Dieser Email wurde schon benutzt!", Toast.LENGTH_SHORT).show();
        }else if(!isEmailValid(emailStr)){
            Toast.makeText(Register.this, "Email ist nicht valid!", Toast.LENGTH_SHORT).show();
        }else if (!passwordStr.equals(confirmPasswordStr)) {
            Toast.makeText(Register.this, "Passwörter stimmen nicht überein!", Toast.LENGTH_SHORT).show();
        }else if(emailStr.equals("") || nameStr.equals("") || passwordStr.equals("") || confirmPasswordStr.equals("")) {
            Toast.makeText(Register.this, "Nicht alle Informationen sind eingegeben!", Toast.LENGTH_SHORT).show();
        }else if(passwordStr.length() < 6){
            Toast.makeText(Register.this, "Passwort soll mindestens 6 Zeichen enthalten!", Toast.LENGTH_SHORT).show();
        }else {
            String generatedPasswordHash = BCrypt.hashpw(passwordStr, BCrypt.gensalt(12));

            User user = new User();
            user.setEmail(emailStr);
            user.setPassword(generatedPasswordHash);
            user.setName(nameStr);

            dbhelper.insertUser(user);
            Toast.makeText(Register.this, "Benutzer wurde erstellt!", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(Register.this, Login.class);
            Register.this.startActivity(in);
        }
    }
}