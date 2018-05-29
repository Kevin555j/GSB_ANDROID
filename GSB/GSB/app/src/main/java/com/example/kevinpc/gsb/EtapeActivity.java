package com.example.kevinpc.gsb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
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

public class EtapeActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button btnMoins, btnPlus, btnValider;
    private TextView tvEtape;
    private Integer quantite = 0;
    private String url = Constants.URL_SAUVEGARDE_ETAPE;
    private SessionManager sessionManager;
    private static final String ID = "id";
    private static final String ETAPE = "etape";
    private static final String DATE = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etape);

        btnMoins    = (Button)findViewById(R.id.btn_moins);
        btnPlus     =(Button)findViewById(R.id.btn_plus);
        btnValider  = (Button)findViewById(R.id.btn_valider);
        tvEtape        =(TextView) findViewById(R.id.tv_Etape);
        datePicker  = (DatePicker)findViewById(R.id.datKm);
        sessionManager = new SessionManager(this);


        if(sessionManager.isLogged()){
            // String pseudo = sessionManager.getPseudo();
            String id = sessionManager.getId();
        }

        btnMoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantite >0)
                {
                    quantite --;
                    tvEtape.setText(quantite.toString());
                    RotateAnimation rotateAnimation = new RotateAnimation(0.0f,360.0f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(0);
                    rotateAnimation.setDuration(500);
                    btnMoins.startAnimation(rotateAnimation);
                }

            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantite ++;
                tvEtape.setText(quantite.toString());
                RotateAnimation rotateAnimation = new RotateAnimation(0.0f,360.0f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(0);
                rotateAnimation.setDuration(500);
                btnPlus.startAnimation(rotateAnimation);
            }
        });

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = sessionManager.getId();
                final String cout  = tvEtape.getText().toString().trim();
                final String date = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();

                RequestQueue requestQueue = Volley.newRequestQueue(EtapeActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("retour", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String fraisEtape = jsonObject.getString("etape");
                            Toast.makeText(EtapeActivity.this, fraisEtape+"€ le "+datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear(), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                        }
                )
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put(ID, id);
                        map.put(ETAPE, cout);
                        map.put(DATE, date);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);

                Intent intent = new Intent(EtapeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
