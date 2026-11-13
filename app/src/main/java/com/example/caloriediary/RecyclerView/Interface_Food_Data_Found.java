package com.example.caloriediary.RecyclerView;

import com.example.caloriediary.Nutrition_Data_From_Db;

import java.util.ArrayList;

public class Interface_Food_Data_Found {
    public interface Food_Data_FoundListener {
        void Food_Data_Found(ArrayList<ArrayList<Nutrition_Data_From_Db>> foodDataList);
    }
}
