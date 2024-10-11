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

        /*Intent Page_Movement_Intent = new Intent(getActivity(), Database.class);
        Page_Movement_Intent.putExtra("Sent_Info",User_Data);
        Page_Movement_Intent.putExtra("Sent_From",3);
        startActivity(Page_Movement_Intent);

         */

        Database db = new Database();
        db.Get_Food_Data();
        //set the recycler view to arraylist
        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
