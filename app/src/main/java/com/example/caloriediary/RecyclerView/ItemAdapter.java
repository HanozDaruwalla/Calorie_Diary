package com.example.caloriediary.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriediary.Api_Refactored.NutritionData;
import com.example.caloriediary.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<NutritionData> itemList = new ArrayList();
    private static String TAG = "Item_Adapter";

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView Value_1_TextView, Value_2_TextView, Value_3_TextView, Value_4_TextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            Value_1_TextView = itemView.findViewById(R.id.Food_Name);
            Value_2_TextView = itemView.findViewById(R.id.Serving_Size);
            Value_3_TextView = itemView.findViewById(R.id.Calories);
            Value_4_TextView = itemView.findViewById(R.id.Fat);
        }
    }

    public ItemAdapter(ArrayList<NutritionData> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        NutritionData currentItem = itemList.get(position);

        Log.d(TAG, "Food Name = " + currentItem.getFoodName());
        Log.d(TAG, "Serving Size = " + currentItem.getServingSize());
        Log.d(TAG, "calories = " + currentItem.getCalories());
        Log.d(TAG, "Total Fat" + currentItem.getTotalfat());

        holder.Value_1_TextView.setText(currentItem.getFoodName());
        holder.Value_1_TextView.setText(currentItem.getServingSize());
        holder.Value_3_TextView.setText(String.valueOf(currentItem.getCalories()));
        holder.Value_4_TextView.setText(String.valueOf(currentItem.getTotalfat()));
    }

    public void Set_Items(ArrayList<NutritionData> items) {
        if (items == null) {
            this.itemList = new ArrayList<>(); // Assign an empty list if null

        } else {
            this.itemList = items;
        }
        // Notify that the dataset has changed
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        //return itemList != null ? itemList.size() : 0
        return itemList.size();
    }
}