package com.example.kevinpc.gsb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class LogoutActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        sessionManager = new SessionManager(this);

        sessionManager.logout();
        Toast.makeText(this, "déconnexion en cours", Toast.LENGTH_SHORT).show();



        
    }
}
