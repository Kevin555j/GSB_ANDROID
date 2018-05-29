package com.example.kevinpc.gsb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username, et_password;
    private CardView cv_login;
    private ProgressBar progressBar;
    private String url = Constants.URL_CONNEXION;
    public static final String USERNAME="username";
    public static final String PASSWORD="password";
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Initialisations des boutons
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        cv_login = (CardView)findViewById(R.id.cv_login);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        sessionManager = new SessionManager(this);

        cv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = et_username.getText().toString().trim();
                final String password = et_password.getText().toString().trim();
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String username = jsonObject.getString("username");
                            String password= jsonObject.getString("password");
                            Boolean ok_connection = jsonObject.getBoolean("ok_connection");
                            String id = jsonObject.getString("id");
                            Toast.makeText(LoginActivity.this,"Bienvenue "+username,Toast.LENGTH_LONG).show();
                            if(ok_connection)
                            {
                                sessionManager.insertUser(id, username);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "Connexion échouée !", Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put(USERNAME, username);
                        map.put(PASSWORD, password);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }
}


