package com.example.kevinpc.gsb;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecapActivity extends AppCompatActivity {

    public static final String ID = "id";
    private SessionManager sessionManager;
    private String url = Constants.URL_RECAPITULATIF;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap);

        sessionManager = new SessionManager(RecapActivity.this);
        if(sessionManager.isLogged()){
            String id = sessionManager.getId();
        }

        final String id = sessionManager.getId();

        listView = (ListView)findViewById(R.id.listView);
        final ArrayList<String> depenses = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(RecapActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("RETOUR JSON", response);
                //Récupération de la liste des dépenses
                try {
                    JSONArray tableau = new JSONArray(response);
                    for(int i = 0; i<tableau.length(); i++)
                    {
                        JSONObject jo = tableau.getJSONObject(i);
                        String row =    jo.getString("ELEMENT")+" - "+
                                        jo.getString("DATE")+ " - "+
                                        jo.getString("PRIX")+"€";

                        depenses.add(row);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RecapActivity.this, android.R.layout.simple_list_item_1,depenses);
                    listView.setAdapter(arrayAdapter);
                }
                catch (JSONException e) {
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
            //Envoi de l'ID en POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(ID, id);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}