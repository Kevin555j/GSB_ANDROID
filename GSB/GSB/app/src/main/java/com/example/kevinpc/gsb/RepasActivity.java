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

public class RepasActivity extends AppCompatActivity {

    private Button btn_Valider;
    private EditText edt_Repas;
    private DatePicker datePicker;
    //private Double coutRepas = 0.00;
    private String url = Constants.URL_SAUVEGARDE_REPAS;
    private SessionManager sessionManager;
    private static final String ID = "id";
    private static final String REPAS = "repas";
    private static final String DATE  = "date";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repas);
        btn_Valider = (Button)findViewById(R.id.btn_Repas);
        edt_Repas = (EditText)findViewById(R.id.et_Repas);
        datePicker = (DatePicker)findViewById(R.id.datRepas);
        sessionManager = new SessionManager(this);
        if(sessionManager.isLogged())
        {
            String id = sessionManager.getId();
        }

        btn_Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = sessionManager.getId();
                final String cout_repas = edt_Repas.getText().toString().trim();
                final String date = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
                final String jour = Integer.toString(datePicker.getDayOfMonth());
                final String mois = Integer.toString(datePicker.getMonth()+1);

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Toast.makeText(RepasActivity.this, cout_repas+"€ le "+jour+"/"+mois  , Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put(ID, id);
                        map.put(REPAS, cout_repas);
                        map.put(DATE, date);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });

    }
}
