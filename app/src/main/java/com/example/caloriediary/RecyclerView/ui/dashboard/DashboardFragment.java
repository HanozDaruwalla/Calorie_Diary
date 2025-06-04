package com.example.caloriediary.RecyclerView.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriediary.Api_Refactored.NutritionData;
import com.example.caloriediary.RecyclerView.ItemAdapter;
import com.example.caloriediary.ReusableFunctions;
import com.example.caloriediary.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> User_Data = new ArrayList();
    final String TAG = "Dashboard_Fragment";
    ReusableFunctions reusable_functions = new ReusableFunctions();
    private ArrayList<ArrayList<NutritionData>> Meals_Arraylist_Nested = new ArrayList<>();

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            Meals_Arraylist_Nested = (ArrayList<ArrayList<NutritionData>>) getArguments().getSerializable("User_Meals");
            if (Meals_Arraylist_Nested != null){
                Log.d(TAG, "Successfully got user food arraylist from recycler");

                Log.d(TAG, "Size of arraylist = " + String.valueOf(Meals_Arraylist_Nested.size()));
            }else{
                Log.d(TAG, "Arraylist Empty!");
            }
        }else{
            Log.d(TAG, "No Bundle with name match found (check argument names)");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Set up the RecyclerView
        recyclerView = binding.recyclerView1;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
/*
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item("apple", 23, 34, "large"));
        itemList.add(new Item("peach", 23, 34, "large"));
        itemList.add(new Item("Rubarb", 23, 34, "large"));
        //set the recycler view to arraylist
        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);
 */
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(Meals_Arraylist_Nested !=null){
            Log.d(TAG, "Successfully got user food arraylist from recycler (in onViewCreated)");
            Log.d(TAG, "Size of arraylist = " + String.valueOf(Meals_Arraylist_Nested.size()));
            Setup_Recycler_View(Meals_Arraylist_Nested);
        }
        else{
            Log.d(TAG, "meals arraylist empty... (in onViewCreated)");
        }

    }

    public void Setup_Recycler_View(ArrayList<ArrayList<NutritionData>> PASSED_Meals_Arraylist_Nested){
        //set the recycler view to arraylist
        //seperate this 3 arroylist into eperate arraylists

        Log.d(TAG, "Starting On Create View");
        ArrayList<NutritionData> Breakfast_Arraylist = PASSED_Meals_Arraylist_Nested.get(0);
        Log.d(TAG, "got breakfast arraylist");
        ArrayList<NutritionData> Lunch_Arraylist = PASSED_Meals_Arraylist_Nested.get(1);
        Log.d(TAG, "got Lunch arraylist");
        ArrayList<NutritionData> Dinner_Arraylist = PASSED_Meals_Arraylist_Nested.get(2);
        Log.d(TAG, "got Dinner arraylist");

        for (int i = 0;i < 20;i++){
            try{
                Log.d(TAG, i + " = : '" + Lunch_Arraylist.get(i) + "'" );
            }catch(IndexOutOfBoundsException Ex){
                Log.d(TAG, "Done");
                break;
            }
        }

        Log.d(TAG, "Log Breakfast Arraylist reader done");
        adapter = new ItemAdapter(Lunch_Arraylist);
        Log.d(TAG, "Set Adapter");
        recyclerView.setAdapter(adapter);
        Log.d(TAG, "Adding To Recycler View");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}