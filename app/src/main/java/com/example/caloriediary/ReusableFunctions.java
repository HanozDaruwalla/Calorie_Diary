package com.example.caloriediary;

import android.widget.Toast;
import android.content.Context;

public class ReusableFunctions {

    public void Create_Toast(Context context, String Toast_Msg) {
        Toast toast = Toast.makeText(context, Toast_Msg, Toast.LENGTH_SHORT);//was "LONG"
        //toast.setGravity(Gravity.CENTER_VERTICAL,0,250);
        toast.show();
    }


}
