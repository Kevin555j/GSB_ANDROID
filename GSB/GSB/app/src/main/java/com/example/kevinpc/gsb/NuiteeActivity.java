package com.example.kevinpc.gsb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NuiteeActivity extends AppCompatActivity {

    Button btn_valider;
    EditText edt_Nuitee;
    DatePicker datePicker;
    String url = Constants.URL_SAUVEGARDE_NUITEE;
    private static final String ID = "id";
    private static final String NUITEE = "nuitee";
    private static final String DATE = "date";
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuitee);
        btn_valider = (Button)findViewById(R.id.btn_Nuitee);
        edt_Nuitee = (EditText)findViewById(R.id.et_Nuitee);
        datePicker = (DatePicker)findViewById(R.id.datNuitee);
        sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.isLogged()){
            String id = sessionManager.getId();
        }

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = sessionManager.getId();
                final String date = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
                final String nuitee = edt_Nuitee.getText().toString().trim();
                final String mois = Integer.toString(datePicker.getMonth()+1);
                final String jour = Integer.toString(datePicker.getDayOfMonth());


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(NuiteeActivity.this, nuitee+"€ le "+jour+"/"+mois, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NuiteeActivity.this, "ki gogote", Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put(ID, id);
                        map.put(DATE, date);
                        map.put(NUITEE, nuitee);
                        return map ;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });




    }
}
