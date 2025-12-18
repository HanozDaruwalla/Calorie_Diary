package com.example.caloriediary.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriediary.Food_Item_Adapter_Methods;
import com.example.caloriediary.Nutrition_Data_From_Db;
import com.example.caloriediary.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<Nutrition_Data_From_Db> itemList = new ArrayList();
    private static final String TAG = "Item_Adapter";
    String Filter1_Selected = "Undefined";
    String Filter2_Selected = "Undefined";
    Food_Item_Adapter_Methods Recycler_Methods = new Food_Item_Adapter_Methods();


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView Food_Name_Textview, Serving_Size_Textview, Dynamic_Textview_1, Dynamic_Textview_2, Dynamic_Lb_1, Dynamic_Lb_2;

        public ItemViewHolder(View itemView) {
            super(itemView);
            Food_Name_Textview = itemView.findViewById(R.id.Food_Name);
            Serving_Size_Textview = itemView.findViewById(R.id.Serving_Size);

            Dynamic_Lb_1 = itemView.findViewById(R.id.Filter_Value_1_Lb);
            Dynamic_Textview_1 = itemView.findViewById(R.id.Filter_Option_1_Result);

            Dynamic_Lb_2 = itemView.findViewById(R.id.Filter_Value_2_Lb);
            Dynamic_Textview_2 = itemView.findViewById(R.id.Filter_Option_2_Result);
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
        String Value_For_Dyn_1 = "N/A";
        String Lb_For_Dyn_1 = "N/A";
        String Empty_Data_String = "N/A";
        ArrayList<String> Dynamic_Data_Returned = new ArrayList<>();
        ArrayList<String> Dynamic_Data_Returned2 = new ArrayList<>();

        Log.d(TAG, "in bindviewholder");

        if (position >= 0 && position < itemList.size()) {
            Log.d(TAG, "Valid Position");
            Nutrition_Data_From_Db currentItem = itemList.get(position);
            Log.d(TAG, "Position " + position + " set");

            Log.d(TAG, "Food Name = " + currentItem.getName_Of_Food());
            Log.d(TAG, "Serving Size = " + currentItem.getPortion_Size());
            holder.Food_Name_Textview.setText(currentItem.getName_Of_Food());
            holder.Serving_Size_Textview.setText(currentItem.getPortion_Size());

            Dynamic_Data_Returned = Recycler_Methods.Find_Filter_Value(Filter1_Selected, currentItem,Value_For_Dyn_1,Lb_For_Dyn_1);
            Dynamic_Data_Returned2 = Recycler_Methods.Find_Filter_Value(Filter2_Selected, currentItem,Value_For_Dyn_1,Lb_For_Dyn_1);

        } else {
            Log.e(TAG, "Error: Invalid position " + position + " for itemList size " + itemList.size());
            // Optionally clear text views or handle this error state visually
            holder.Food_Name_Textview.setText("");
            holder.Serving_Size_Textview.setText("");
            holder.Dynamic_Textview_1.setText("");
            holder.Dynamic_Textview_2.setText("");
        }
        holder.Dynamic_Textview_1.setText(Dynamic_Data_Returned.get(0));
        holder.Dynamic_Lb_1.setText(Dynamic_Data_Returned.get(1));

        holder.Dynamic_Textview_2.setText(Dynamic_Data_Returned2.get(0));
        holder.Dynamic_Lb_2.setText(Dynamic_Data_Returned2.get(1));


    }

    public void Filter_Changed(String Filter1_Value, String Filter2_Value){
        Log.d(TAG, "In Filter_Changed");
        Filter1_Selected=Filter1_Value;
        Filter2_Selected=Filter2_Value;
        Log.d(TAG, "Filter1_Value = " + Filter1_Value);
        notifyDataSetChanged();
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