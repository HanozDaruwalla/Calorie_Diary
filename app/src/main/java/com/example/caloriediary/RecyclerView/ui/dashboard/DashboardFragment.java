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
    private RecyclerView Breakfast_Recycler_View;
    private RecyclerView Lunch_Recycler_View;
    private RecyclerView Dinner_Recycler_View;

    private RecyclerView.Adapter adapter;

    private ItemAdapter Breakfast_Item_Adapter;
    private ItemAdapter Lunch_Item_Adapter;
    private ItemAdapter Dinner_Item_Adapter;

    private RecyclerView.LayoutManager breakfast_layoutManager;
    private RecyclerView.LayoutManager lunch_layoutManager;
    private RecyclerView.LayoutManager dinner_layoutManager;

    ArrayList<String> User_Data = new ArrayList();
    final String TAG = "Dashboard_Fragment";
    ReusableFunctions reusable_functions = new ReusableFunctions();
    private ArrayList<ArrayList<Nutrition_Data_From_Db>> Meals_Arraylist_Nested = new ArrayList<>();
    String Nutrition_Option_1_Picked = "undefined";
    String Nutrition_Option_2_Picked = "undefined";

    /*
    ArrayList<Nutrition_Data_From_Db> Breakfast_Arraylist;
    ArrayList<Nutrition_Data_From_Db> Lunch_Arraylist;
    ArrayList<Nutrition_Data_From_Db> Dinner_Arraylist;

     */

    ArrayList<Nutrition_Data_From_Db> Breakfast_Arraylist = new ArrayList<>();
    ArrayList<Nutrition_Data_From_Db> Lunch_Arraylist = new ArrayList<>();
    ArrayList<Nutrition_Data_From_Db> Dinner_Arraylist = new ArrayList<>();

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "oncreate");

        if (getArguments() != null) {
            Meals_Arraylist_Nested = (ArrayList<ArrayList<Nutrition_Data_From_Db>>) getArguments().getSerializable("User_Meals");
            if (Meals_Arraylist_Nested != null) {
                Log.d(TAG, "Successfully got user food arraylist from recycler");

                Log.d(TAG, "Size of arraylist = " + String.valueOf(Meals_Arraylist_Nested.size()));

                Log.d(TAG, "setting up breakfast recycler");
                Breakfast_Item_Adapter = new ItemAdapter(Breakfast_Arraylist);
                Log.d(TAG, "Set Adapter");

                Breakfast_Recycler_View.setAdapter(Breakfast_Item_Adapter);
                Log.d(TAG, "Adding To Breakfast Recycler View");

                for (int i = 0;i < Lunch_Arraylist.size();i++){
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

                for (int i = 0;i < Dinner_Arraylist.size();i++){
                    try{
                        Log.d(TAG, i + " = : '" + Dinner_Arraylist.get(i) + "'" );
                    }catch(IndexOutOfBoundsException Ex){
                        Log.d(TAG, "Done");
                        break;
                    }
                }

                Log.d(TAG, "setting up dinner recycler");
                Dinner_Item_Adapter = new ItemAdapter(Dinner_Arraylist);
                Log.d(TAG, "Set Adapter");
                Dinner_Recycler_View.setAdapter(Dinner_Item_Adapter);
                Log.d(TAG, "Adding To dinner Recycler View");

            } else {
                Log.d(TAG, "Arraylist Empty!");
            }
        } else {
            Log.d(TAG, "No Bundle with name match found (check argument names)");
        }

        //what is done below. for ref only
        //Breakfast_Item_Adapter = new ItemAdapter(Breakfast_Arraylist);


    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Set up the RecyclerView
        Breakfast_Recycler_View = binding.recyclerView1;
        Lunch_Recycler_View = binding.recyclerView2;
        Dinner_Recycler_View = binding.recyclerView3;

        Breakfast_Recycler_View.setHasFixedSize(true);
        Lunch_Recycler_View.setHasFixedSize(true);
        Dinner_Recycler_View.setHasFixedSize(true);

        ArrayList<Nutrition_Data_From_Db> Temp_Loaded_Data_List = new ArrayList<>();
        Temp_Loaded_Data_List.add(new Nutrition_Data_From_Db());

        // Initialize adapters with default data

        Breakfast_Item_Adapter = new ItemAdapter(Temp_Loaded_Data_List); // Triggers setDefaultData
        Breakfast_Recycler_View.setAdapter(Breakfast_Item_Adapter);
        Lunch_Item_Adapter = new ItemAdapter(Temp_Loaded_Data_List);
        Lunch_Recycler_View.setAdapter(Lunch_Item_Adapter);
        Dinner_Item_Adapter = new ItemAdapter(Temp_Loaded_Data_List);
        Dinner_Recycler_View.setAdapter(Dinner_Item_Adapter);



//here and down should fill recyelers with data from db. sent from breakfast recycler where they get all data and return in the bundle

        breakfast_layoutManager = new LinearLayoutManager(getContext());
        Breakfast_Recycler_View.setLayoutManager(breakfast_layoutManager);

        //code commented below messes up view
        //Breakfast_Recycler_View.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        Log.d(TAG, "set breakfast layout manager");

        lunch_layoutManager = new LinearLayoutManager(getContext());
        Lunch_Recycler_View.setLayoutManager(lunch_layoutManager);
        //Lunch_Recycler_View.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        Log.d(TAG, "set lunch layout manager");

        dinner_layoutManager = new LinearLayoutManager(getContext());
        Dinner_Recycler_View.setLayoutManager(dinner_layoutManager);
        Log.d(TAG, "set dinner layout manager");



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

        String[] Filter_Options_1 = inflater.getContext().getResources().getStringArray(R.array.Food_Options);
        //String[] Filter_Options_1 = getResources().getStringArray(R.array.Food_Options);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> Filter_Adapter1 = ArrayAdapter.createFromResource(
                getContext(), R.array.Food_Options,
                android.R.layout.simple_spinner_item
        );
        Filter_Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Nutrition_Option_1.setAdapter(Filter_Adapter1);

        String[] Filter_Options_2 = inflater.getContext().getResources().getStringArray(R.array.Food_Options);
        //String[] Filter_Options_2 = getResources().getStringArray(R.array.Food_Options);

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


        if (Meals_Arraylist_Nested != null && !Meals_Arraylist_Nested.isEmpty()){
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
         Breakfast_Arraylist = PASSED_Meals_Arraylist_Nested.get(0);
        Log.d(TAG, "got breakfast arraylist");
         Lunch_Arraylist = PASSED_Meals_Arraylist_Nested.get(1);
        Log.d(TAG, "got Lunch arraylist");
         Dinner_Arraylist = PASSED_Meals_Arraylist_Nested.get(2);
        Log.d(TAG, "got Dinner arraylist");

        for (int i = 0; i<PASSED_Meals_Arraylist_Nested.size();i++){
            if (!(PASSED_Meals_Arraylist_Nested.get(i).get(0) == null)){
                Log.d(TAG, "Nested List size = " + i);
            }else{
                Log.d(TAG, "Nested List size = " + i + " is null");
            }
        }

        //for (int i = 0;i < 20;i++){
          for (int i = 0;i < Breakfast_Arraylist.size();i++){
            try{
                Log.d(TAG, i + " = : '" + Breakfast_Arraylist.get(i) + "'" );
            }catch(IndexOutOfBoundsException Ex){
                Log.d(TAG, "Done");
                break;
            }
        }
    /*  is to reload recyclers with new data

        Log.d(TAG, "setting up breakfast recycler");
        Breakfast_Item_Adapter = new ItemAdapter(Breakfast_Arraylist);
        Log.d(TAG, "Set Adapter");

        Breakfast_Recycler_View.setAdapter(Breakfast_Item_Adapter);
        Log.d(TAG, "Adding To Breakfast Recycler View");

        for (int i = 0;i < Lunch_Arraylist.size();i++){
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

        for (int i = 0;i < Dinner_Arraylist.size();i++){
            try{
                Log.d(TAG, i + " = : '" + Dinner_Arraylist.get(i) + "'" );
            }catch(IndexOutOfBoundsException Ex){
                Log.d(TAG, "Done");
                break;
            }
        }

        Log.d(TAG, "setting up dinner recycler");
        Dinner_Item_Adapter = new ItemAdapter(Dinner_Arraylist);
        Log.d(TAG, "Set Adapter");
        Dinner_Recycler_View.setAdapter(Dinner_Item_Adapter);
        Log.d(TAG, "Adding To dinner Recycler View");

 */
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}