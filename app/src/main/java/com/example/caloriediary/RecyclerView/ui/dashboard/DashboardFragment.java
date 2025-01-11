package com.example.caloriediary.RecyclerView.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriediary.Api_Refactored.MainActivity2;
import com.example.caloriediary.Api_Refactored.NutritionData;
import com.example.caloriediary.Database.Database;
import com.example.caloriediary.RecyclerView.Item;
import com.example.caloriediary.RecyclerView.ItemAdapter;
import com.example.caloriediary.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> User_Data = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        // Set up the RecyclerView
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item("apple", 23, 34, "large"));
        itemList.add(new Item("peach", 23, 34, "large"));
        itemList.add(new Item("Rubarb", 23, 34, "large"));

        //set the recycler view to arraylist
        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);

        return root;

    }

    public void Create_View(ArrayList<ArrayList<NutritionData>> Food_Data_Arraylists){
        //set the recycler view to arraylist
        //seperate this 3 arroylist into eperate arraylists
        Food_Data_Arraylists

        adapter = new ItemAdapter(Food_Data_Arraylists);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
