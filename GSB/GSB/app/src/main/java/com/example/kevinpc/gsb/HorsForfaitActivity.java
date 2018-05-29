package com.example.kevinpc.gsb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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

public class HorsForfaitActivity extends AppCompatActivity {

    DatePicker datePicker;
    Button button;
    EditText motif, montant;
    private String url = Constants.URL_SAUVEGARDE_HORSFORFAIT;
    private SessionManager sessionManager;
    private static final String ID = "id";
    private static final String HORSFORFAIT = "horsForfait";
    private static final String DATE  = "date";
    private static final String MOTIF  = "motif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hors_forfait);

        datePicker = (DatePicker)findViewById(R.id.datHF);
        button = (Button)findViewById(R.id.btn_ValiderHF);
        motif = (EditText) findViewById(R.id.motif);
        montant = (EditText) findViewById(R.id.montant);
        sessionManager = new SessionManager(this);

        if(sessionManager.isLogged()){
            String id = sessionManager.getId();
        }
        final String id = sessionManager.getId();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String leMotif = motif.getText().toString();
                final String leMontant = montant.getText().toString();
                final String date = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();

                RequestQueue requestQueue = Volley.newRequestQueue(HorsForfaitActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            Log.i("LE JSON", String.valueOf(jo));
                            Log.i("tentative", jo.getString("motif"));
                            String motif = jo.getString("motif");
                            String montant = jo.getString("horsForfait");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(HorsForfaitActivity.this, montant.getText()+"€ le "+datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear()+" MOTIF : "+motif.getText(), Toast.LENGTH_LONG).show();
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put(ID, id);
                        map.put(HORSFORFAIT,leMontant );
                        map.put(MOTIF, leMotif);
                        map.put(DATE, date);
                        return map;
                    }
                };

                requestQueue.add(stringRequest);

                Intent intent = new Intent(HorsForfaitActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
