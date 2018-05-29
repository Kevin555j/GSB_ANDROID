package com.example.kevinpc.gsb;

import android.content.Intent;

/**
 * Created by KEVIN PC on 06/03/2018.
 */

public class Constants {
    //private static final String ROOT_URL = "https://kevin555j.000webhostapp.com/";
    private static final String ROOT_URL = "http://192.168.1.109/";
    public static final String URL_FUNCTIONS = ROOT_URL + "GSB/android/Functions.php?requete=";
    public static final String URL_CONNEXION = URL_FUNCTIONS + "connexion";
    public static final String URL_INSCRIPTION = URL_FUNCTIONS + "inscription";
    public static final String ULR_SAUVEGARDE_KM = URL_FUNCTIONS + "sauvegarde_km";
    public static final String URL_SAUVEGARDE_REPAS = URL_FUNCTIONS +"sauvegarde_repas";
    public static final String URL_SAUVEGARDE_ETAPE = URL_FUNCTIONS +"sauvegarde_etape";
    public static final String URL_SAUVEGARDE_NUITEE = URL_FUNCTIONS +"sauvegarde_nuitee";
    public static final String URL_SAUVEGARDE_HORSFORFAIT= URL_FUNCTIONS +"sauvegarde_hors_forfait";
    public static final String URL_RECAPITULATIF = URL_FUNCTIONS +"recapitulatif";




}
