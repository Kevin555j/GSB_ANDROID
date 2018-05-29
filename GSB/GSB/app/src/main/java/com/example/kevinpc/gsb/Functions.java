package com.example.kevinpc.gsb;

import android.content.Context;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KEVIN PC on 06/03/2018.
 */

public class Functions {

    public void getArrayData(TextView data, String response){

        JSONArray JA = null;
        try {
            JA = new JSONArray(response);
            String dataParsed = "";
            String singleParsed = "";
            for(int i = 0; i < JA.length(); i++)
            {
                JSONObject JO = (JSONObject) JA.get(i);
                singleParsed =  "ID: " + JO.get("id")+"\n"+
                "DATA: " + JO.get("date")+"\n"+
                "PRIX REPAS: " + JO.get("prix_repas")+"\n"+
                "NUM VISITEUR: " + JO.get("id_visiteur")+"\n";
                dataParsed = dataParsed+singleParsed+"\n";
            }
            data.setText(dataParsed);

            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
}




