package com.example.kevinpc.gsb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btn_km,btn_repas, btn_nuitee, btn_etape, btn_horsForfait, btn_recap, btn_transfert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clicBtn(btn_km = (Button)findViewById(R.id.cmdKm),KmActivity.class, false);
        clicBtn(btn_repas = (Button)findViewById(R.id.cmdRepas),RepasActivity.class, false);
        clicBtn(btn_nuitee = (Button)findViewById(R.id.cmdNuitee),NuiteeActivity.class, false);
        clicBtn(btn_etape = (Button)findViewById(R.id.cmdEtape), EtapeActivity.class, false);
        clicBtn(btn_horsForfait = (Button)findViewById(R.id.cmdHf), HorsForfaitActivity.class, false);
        clicBtn(btn_recap = (Button)findViewById(R.id.cmdHfRecap),RecapActivity.class,false);

      clicBtn(btn_transfert = (Button)findViewById(R.id.cmdTransfert),LogoutActivity.class,true);


    }

    private void clicBtn(Button btn, final Class classe, final Boolean finish)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, classe);
                startActivity(intent);
                if(finish){
                    finish();
                }


            }
        });
    }



}