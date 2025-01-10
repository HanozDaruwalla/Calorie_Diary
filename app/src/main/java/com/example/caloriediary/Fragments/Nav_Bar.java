package com.example.caloriediary.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.caloriediary.Calorie_Homepage;
import com.example.caloriediary.Coming_Soon;
import com.example.caloriediary.Creating_Account_And_Login.User;
import com.example.caloriediary.Info;
import com.example.caloriediary.R;
import com.example.caloriediary.RecyclerView.recyclerview;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Nav_Bar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Nav_Bar extends Fragment {

    private static final String TAG = "Nav_Bar_Fragment";
    private TextView Info_Text_View, Calorie_Counter_View, Weight_Monitor_View;
    private ImageView Info_Img, Calorie_Counter_Img, Weight_Monitor_Image;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Nav_Bar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Nav_Bar.
     */
    // TODO: Rename and change types and number of parameters


    ArrayList<String> User_Data = new ArrayList<>();

    public static Nav_Bar newInstance(String param1, String param2) {
        Nav_Bar fragment = new Nav_Bar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_nav__bar, container, false);

        //interface
        Info_Text_View = rootView.findViewById(R.id.Info_Text);
        Info_Img = rootView.findViewById(R.id.Info_Img);

        Calorie_Counter_View = rootView.findViewById(R.id.Calorie_Counter_Text);
        Calorie_Counter_Img = rootView.findViewById(R.id.Calorie_Counter_Image);

        Weight_Monitor_View = rootView.findViewById((R.id.Weight_Text));
        Weight_Monitor_Image = rootView.findViewById((R.id.Weight_Image));

        SharedPreferences shared_preference = getActivity().getSharedPreferences("User_Data", Context.MODE_PRIVATE);

        String Age = shared_preference.getString("Age", null);
        String Email = shared_preference.getString("Email", null);
        String Height_Cm = shared_preference.getString("Height_Cm", null);
        String Password = shared_preference.getString("Password", null);
        String Bmr = shared_preference.getString("Bmr", null);
        String Is_male = shared_preference.getString("Is_male", null);
        String Username = shared_preference.getString("Username", null);
        String Weight_Kg = shared_preference.getString("Weight_Kg", null);

// Optional: Log to verify values
        Log.d(TAG, "Age: " + Age);
        Log.d(TAG, "Email: " + Email);
        Log.d(TAG, "Height (cm): " + Height_Cm);
        Log.d(TAG, "Password: " + Password);
        Log.d(TAG, "BMR: " + Bmr);
        Log.d(TAG, "Is Male: " + Is_male);
        Log.d(TAG, "Username: " + Username);
        Log.d(TAG, "Weight (kg): " + Weight_Kg);

        User_Data.add(Age);
        User_Data.add(Email);
        User_Data.add(Height_Cm);
        User_Data.add(Password);
        User_Data.add(Bmr);
        User_Data.add(Is_male);
        User_Data.add(Username);
        User_Data.add(Weight_Kg);

        //Info Section
        Info_Text_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info_Section_Clicked();
            }
        });

        Info_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info_Section_Clicked();
            }
        });

        //Calorie Counter Img Section
        Calorie_Counter_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calorie_Diary_Button_Pressed();
            }
        });

        Calorie_Counter_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calorie_Diary_Button_Pressed();
            }
        });


        //Weight_Monitor_Section
        Weight_Monitor_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Weight_Monitor_Pressed();
            }
        });

        Weight_Monitor_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Weight_Monitor_Pressed();
            }
        });

        return rootView;
    }

//Functions Taking User To other Pages
    private void Info_Section_Clicked(){
        Log.d(TAG, "Info Clicked");
        Intent Page_Movement_Intent = new Intent(getContext(), Info.class);

        startActivity(Page_Movement_Intent);
    }

    private void Calorie_Diary_Button_Pressed(){
        Log.d(TAG, "Calorie Diary Clicked");

        Intent intent = new Intent(getContext(), recyclerview.class);
        intent.putExtra("User_Data",User_Data);
        startActivity(intent);
    }

    private void Weight_Monitor_Pressed(){
        Log.d(TAG, "Weight Monitor Clicked");
        Intent Page_Movement_Intent = new Intent(getContext(), Coming_Soon.class);

        startActivity(Page_Movement_Intent);
    }

    public void Home_Button_Pressed(View view) {
        Log.d(TAG, "Calorie Diary Clicked");

    }
}