package com.example.caloriediary.RecyclerView.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriediary.Nutrition_Data_From_Db;
import com.example.caloriediary.R;
import com.example.caloriediary.RecyclerView.ItemAdapter;
import com.example.caloriediary.ReusableFunctions;
import com.example.caloriediary.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView Breakfast_Recycler_View, Lunch_Recycler_View;
    private RecyclerView.Adapter adapter;
    private ItemAdapter Breakfast_Item_Adapter;
    private ItemAdapter Lunch_Item_Adapter;
    private RecyclerView.LayoutManager breakfast_layoutManager;
    private RecyclerView.LayoutManager lunch_layoutManager;
    private RecyclerView.LayoutManager dinner_layoutManager;


    ArrayList<String> User_Data = new ArrayList();
    final String TAG = "Dashboard_Fragment";
    ReusableFunctions reusable_functions = new ReusableFunctions();
    private ArrayList<ArrayList<Nutrition_Data_From_Db>> Meals_Arraylist_Nested = new ArrayList<>();
    String Nutrition_Option_1_Picked = "undefined";
    String Nutrition_Option_2_Picked = "undefined";


    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "oncreate");

        Log.d(TAG, "binding created");
        if (getArguments() != null) {
            Meals_Arraylist_Nested = (ArrayList<ArrayList<Nutrition_Data_From_Db>>) getArguments().getSerializable("User_Meals");
            if (Meals_Arraylist_Nested != null) {
                Log.d(TAG, "Successfully got user food arraylist from recycler");

                Log.d(TAG, "Size of arraylist = " + String.valueOf(Meals_Arraylist_Nested.size()));
            } else {
                Log.d(TAG, "Arraylist Empty!");
            }
        } else {
            Log.d(TAG, "No Bundle with name match found (check argument names)");
        }


    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Set up the RecyclerView
        Breakfast_Recycler_View = binding.recyclerView1;
        Lunch_Recycler_View = binding.recyclerView2;

        Breakfast_Recycler_View.setHasFixedSize(true);
        Lunch_Recycler_View.setHasFixedSize(true);

        breakfast_layoutManager = new LinearLayoutManager(getContext());
        //Breakfast_Recycler_View.setLayoutManager(breakfast_layoutManager);
        Breakfast_Recycler_View.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        Log.d(TAG, "set breakfast layout manager");

        lunch_layoutManager = new LinearLayoutManager(getContext());
        //Lunch_Recycler_View.setLayoutManager(lunch_layoutManager);
        Lunch_Recycler_View.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        Log.d(TAG, "set lunch layout manager");


        Log.d(TAG, "Starting Spinners Now");
        Spinner Nutrition_Option_1 = binding.Option1Spinner;
        Spinner Nutrition_Option_2 = binding.Option2Spinner;

        Nutrition_Option_1.setSelection(3);
        Nutrition_Option_2.setSelection(0);

        Log.d(TAG, "Now beginning Spinner Listener 1");
        Nutrition_Option_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> Adapterview, View view, int position, long id) {
                Log.d(TAG, "OnItemSelected1");
                Nutrition_Option_1_Picked = Adapterview.getItemAtPosition(position).toString();
                Log.d(TAG, "Passing Back To item adapter");
                Breakfast_Item_Adapter.Filter_Changed(Nutrition_Option_1_Picked,Nutrition_Option_2_Picked);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Log.d(TAG, "Now beginning Spinner Listener 1");
        Nutrition_Option_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> Adapterview, View view, int position, long id) {
                Log.d(TAG, "OnItemSelected1");
                Nutrition_Option_2_Picked = Adapterview.getItemAtPosition(position).toString();
                Log.d(TAG, "Passing Back To item adapter");
                Breakfast_Item_Adapter.Filter_Changed(Nutrition_Option_1_Picked,Nutrition_Option_2_Picked);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Log.d(TAG, "Listener 1 and 2 done");

        String[] Filter_Options_1 = getResources().getStringArray(R.array.Food_Options);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> Filter_Adapter1 = ArrayAdapter.createFromResource(
                getContext(), R.array.Food_Options,
                android.R.layout.simple_spinner_item
        );
        Filter_Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Nutrition_Option_1.setAdapter(Filter_Adapter1);

        String[] Filter_Options_2 = getResources().getStringArray(R.array.Food_Options);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> Filter_Adapter2 = ArrayAdapter.createFromResource(
                getContext(), R.array.Food_Options,
                android.R.layout.simple_spinner_item
        );
        Filter_Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Nutrition_Option_2.setAdapter(Filter_Adapter2);
        return root;
    }

    public String Check_Filter1() {
        Log.d(TAG, "In Check_Filter1");
        String Filter1_Value = (String) binding.Option1Spinner.getSelectedItem();
        Log.d(TAG, "Returning" + Filter1_Value);
        return Filter1_Value;
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

    public void Setup_Recycler_View(ArrayList<ArrayList<Nutrition_Data_From_Db>> PASSED_Meals_Arraylist_Nested){
        //set the recycler view to arraylist
        //seperate this 3 arroylist into eperate arraylists

        Log.d(TAG, "Starting On Create View");
        ArrayList<Nutrition_Data_From_Db> Breakfast_Arraylist = PASSED_Meals_Arraylist_Nested.get(0);
        Log.d(TAG, "got breakfast arraylist");
        ArrayList<Nutrition_Data_From_Db> Lunch_Arraylist = PASSED_Meals_Arraylist_Nested.get(1);
        Log.d(TAG, "got Lunch arraylist");
        ArrayList<Nutrition_Data_From_Db> Dinner_Arraylist = PASSED_Meals_Arraylist_Nested.get(2);
        Log.d(TAG, "got Dinner arraylist");

        for (int i = 0;i < 20;i++){
            try{
                Log.d(TAG, i + " = : '" + Breakfast_Arraylist.get(i) + "'" );
            }catch(IndexOutOfBoundsException Ex){
                Log.d(TAG, "Done");
                break;
            }
        }

        Log.d(TAG, "setting up breakfast recycler");
        Breakfast_Item_Adapter = new ItemAdapter(Breakfast_Arraylist);
        Log.d(TAG, "Set Adapter");
        
        Breakfast_Recycler_View.setAdapter(Breakfast_Item_Adapter);
        Log.d(TAG, "Adding To Breakfast Recycler View");

        for (int i = 0;i < 20;i++){
            try{
                Log.d(TAG, i + " = : '" + Lunch_Arraylist.get(i) + "'" );
            }catch(IndexOutOfBoundsException Ex){
                Log.d(TAG, "Done");
                break;
            }
        }


        Log.d(TAG, "setting up lunch recycler");
        Lunch_Item_Adapter = new ItemAdapter(Lunch_Arraylist);
        Log.d(TAG, "Set Adapter");
        Lunch_Recycler_View.setAdapter(Lunch_Item_Adapter);
        Log.d(TAG, "Adding To Lunch Recycler View");



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}