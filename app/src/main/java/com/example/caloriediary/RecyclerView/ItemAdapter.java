package com.example.caloriediary.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriediary.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView Food_Name_TextView, Serving_Size_TextView, Calories_TextView, Fat_TextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            Food_Name_TextView = itemView.findViewById(R.id.Food_Name);
            Serving_Size_TextView = itemView.findViewById(R.id.Serving_Size);
            Calories_TextView = itemView.findViewById(R.id.Calories);
            Fat_TextView = itemView.findViewById(R.id.Fat);
        }
    }

    public ItemAdapter(List<Item> itemList) {
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
        Item currentItem = itemList.get(position);

        holder.Food_Name_TextView.setText(currentItem.getFood_Name());
        holder.Serving_Size_TextView.setText(currentItem.getServings());
        holder.Calories_TextView.setText(String.valueOf(currentItem.getCalories()));
        holder.Fat_TextView.setText(String.valueOf(currentItem.getFat()));
    }

    public void Set_Items(List<Item> items) {
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
        return itemList != null ? itemList.size() : 0;
    }
}