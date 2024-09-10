package com.example.caloriediary.Bmi_Calc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.caloriediary.Calorie_Homepage;
import com.example.caloriediary.Database.Database;
import com.example.caloriediary.R;
import com.example.caloriediary.databinding.ActivityOptionForBmiBinding;
import com.example.caloriediary.databinding.ActivityUserEnterHeightBinding;

import java.util.ArrayList;

public class OptionForBmi extends AppCompatActivity {

    private ActivityOptionForBmiBinding binding;
    ArrayList<String> User_Data = new ArrayList<>();
    public static final String TAG = "OptionForBmi_Section";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_option_for_bmi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            binding = ActivityOptionForBmiBinding.inflate(getLayoutInflater());
            User_Data = getIntent().getExtras().getStringArrayList("User_Data");

            return insets;
        });
    }

    public void Yes_Button_Pressed(View view) {
        Intent Page_Movement_Intent = new Intent(this, Enter_Height.class);//
        Log.d(TAG, "putting extra in Login Success");
        Page_Movement_Intent.putExtra("User_Data", User_Data);
        startActivity(Page_Movement_Intent);
    }

    public void No_Button_Pressed(View view) {
        Intent Page_Movement_Intent = new Intent(this, Calorie_Homepage.class);

        Log.d(TAG, "Size = " + User_Data.size());
        Page_Movement_Intent.putExtra("User_Data", User_Data);

        startActivity(Page_Movement_Intent);

    }
}