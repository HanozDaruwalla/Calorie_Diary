package com.example.caloriediary.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriediary.Api_Refactored.Nutrition_Data__From_Api;
import com.example.caloriediary.Nutrition_Data_From_Db;
import com.example.caloriediary.R;
import com.example.caloriediary.RecyclerView.ui.dashboard.DashboardFragment;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<Nutrition_Data_From_Db> itemList = new ArrayList();
    private static String TAG = "Item_Adapter";

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView Value_1_TextView, Value_2_TextView, Value_3_TextView, Value_4_TextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            Value_1_TextView = itemView.findViewById(R.id.Food_Name);
            Value_2_TextView = itemView.findViewById(R.id.Serving_Size);
            Value_3_TextView = itemView.findViewById(R.id.Calories);
            Value_4_TextView = itemView.findViewById(R.id.Fat);
            Log.d(TAG, "Interface binding set");
        }
    }

    public ItemAdapter(ArrayList<Nutrition_Data_From_Db> itemList) {
        if (itemList != null) {
            this.itemList = itemList;
        } else {
            this.itemList = new ArrayList<>(); // Initialize to an empty list
            Log.w(TAG, "Constructor received null itemList, initialized to empty ArrayList.");
        }
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
        Log.d(TAG, "in bindviewholder");

        if (position >= 0 && position < itemList.size()) {
            Log.d(TAG, "Valid Position");
            Nutrition_Data_From_Db currentItem = itemList.get(position);
            Log.d(TAG, "Position " + position + " set");

            Log.d(TAG, "Food Name = " + currentItem.getName_Of_Food());
            Log.d(TAG, "Serving Size = " + currentItem.getPortion_Size());
            holder.Value_1_TextView.setText(currentItem.getName_Of_Food());
            holder.Value_2_TextView.setText(currentItem.getPortion_Size());

            DashboardFragment Dash_Frag = new DashboardFragment();
            String Filter1_Selected = Dash_Frag.Check_Filter1();

            //Change all this to hard coded string or see why in dashboard frag, why getresources wirk
            switch (Filter1_Selected){
                if (Filter1_Selected.equals(getResources().getString(R.string.food_option_calories))) {
                    return getResources().getString(R.string.food_option_calories);
                } else if (Filter1_Selected.equals(getResources().getString(R.string.food_option_product_name))) {
                    return getResources().getString(R.string.food_option_product_name);
                } else if (Filter1_Selected.equals(getResources().getString(R.string.food_option_serving_size))) {
                    return getResources().getString(R.string.food_option_serving_size);
                } else if (Filter1_Selected.equals(getResources().getString(R.string.food_option_total_fat))) {
                    return getResources().getString(R.string.food_option_total_fat);
                } else if (Filter1_Selected.equals(getResources().getString(R.string.food_option_cholesterol))) {
                    return getResources().getString(R.string.food_option_cholesterol);
                } else if (Filter1_Selected.equals(getResources().getString(R.string.food_option_sodium))) {
                    return getResources().getString(R.string.food_option_sodium);
                } else if (Filter1_Selected.equals(getResources().getString(R.string.food_option_total_carbohydrate))) {
                    return getResources().getString(R.string.food_option_total_carbohydrate);
                } else if (Filter1_Selected.equals(getResources().getString(R.string.food_option_sugar))) {
                    return getResources().getString(R.string.food_option_sugar);
                } else if (Filter1_Selected.equals(getResources().getString(R.string.food_option_dietary_fiber))) {
                    return getResources().getString(R.string.food_option_dietary_fiber);
                } else if (Filter1_Selected.equals(getResources().getString(R.string.food_option_potassium))) {
                    return getResources().getString(R.string.food_option_potassium);
                } else if (Filter1_Selected.equals(getResources().getString(R.string.food_option_protein))) {
                    return getResources().getString(R.string.food_option_protein);
                } else {
                    return "N/A";
                }
            }

            Log.d(TAG, "Setting textviews");
            holder.Value_3_TextView.setText(String.valueOf(currentItem.getCalories()));
            holder.Value_4_TextView.setText(String.valueOf(currentItem.getFat()));
            Log.d(TAG, "calories = " + currentItem.getCalories());
            Log.d(TAG, "Total Fat = " + currentItem.getFat()); // Added space for readability



            Log.d(TAG, "textviews set");
        } else {
            Log.e(TAG, "Error: Invalid position " + position + " for itemList size " + itemList.size());
            // Optionally clear text views or handle this error state visually
            holder.Value_1_TextView.setText("");
            holder.Value_2_TextView.setText("");
            holder.Value_3_TextView.setText("");
            holder.Value_4_TextView.setText("");
        }

    }

    public void Set_Items(ArrayList<Nutrition_Data_From_Db> items) {
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
        return itemList.size();
    }


}