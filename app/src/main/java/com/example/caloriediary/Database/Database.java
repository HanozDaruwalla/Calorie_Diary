package com.example.caloriediary.Database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.Api_Refactored.MainActivity2;
import com.example.caloriediary.Bmi_Calc.Enter_Height;
import com.example.caloriediary.Creating_Account_And_Login.Create_Account;
import com.example.caloriediary.Creating_Account_And_Login.Encryption_Decryption_Class;
import com.example.caloriediary.Creating_Account_And_Login.Login;
import com.example.caloriediary.Creating_Account_And_Login.User;
import com.example.caloriediary.Bmi_Calc.OptionForBmi;
import com.example.caloriediary.Nutrition_Data_From_Db;
import com.example.caloriediary.R;
import com.example.caloriediary.RecyclerView.Breakfast_RecyclerView;
import com.example.caloriediary.RecyclerView.Interface_Food_Data_Found;
import com.example.caloriediary.RecyclerView.Interface_Food_Data_Found;
import com.example.caloriediary.ReusableFunctions;
import com.example.caloriediary.databinding.ActivityDatabaseBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Database extends AppCompatActivity {

    ReusableFunctions reusableFunctions = new ReusableFunctions();
    private static final String TAG = "Database_Class";

    HashMap<String, Object> Information_Hashmap = new HashMap<>();
    Intent Page_Movement_Intent;

    private String Creation_Type = "";
    int Sent_From = -1;
    ArrayList<String> Imported_Data_Arraylist = new ArrayList<>();
    String Todays_Date = (ReusableFunctions.Date_Creator().replace("/","-"));
    public String Next_Pk = "Na";

    DatabaseReference Database_Controller = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Login: onCreate");
        super.onCreate(savedInstanceState);
        com.example.caloriediary.databinding.ActivityDatabaseBinding binding = ActivityDatabaseBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_database);
        setContentView(binding.getRoot());
        getSupportActionBar(). hide();

        Database_Controller = FirebaseDatabase.getInstance().getReference();
        //gets the data the user entered
        Imported_Data_Arraylist = getIntent().getExtras().getStringArrayList("Sent_Info");
        Log.d(TAG, "Imported_Arraylist.Username() = " + Imported_Data_Arraylist.get(0));

        //gets where the user came from
        Sent_From = getIntent().getExtras().getInt("Sent_From");

        //firebase extras
        //FirebaseAuth mAuth = FirebaseAuth.getInstance(); only for reset pw
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Log.d(TAG, "Login: onCreate Complete");

        // 0 = Create_Account, 1 = login, 2 = Add Product From Db
        if (Sent_From == (int) 0) {// says is redundant but crashes without
            Log.d(TAG, "Login: Create Account Section Called");
            Creation_Type = "Users";

            Validate_Username(Imported_Data_Arraylist, Database_Controller);

        // 0 = Create_Account, 1 = login, 2 = Add Product From Db
        } else if (Sent_From == (int) 1) { // says is redundant but crashes without
            Log.d(TAG, "Login: Login Section Called");
            Creation_Type = "Users";

            Log.d(TAG, "Username = " + Imported_Data_Arraylist.get(0));
            Log.d(TAG, "Pw = " + Imported_Data_Arraylist.get(1));

            Login(Imported_Data_Arraylist, Database_Controller);

        // 0 = Create_Account, 1 = login, 2 = Add Food
        }else if (Sent_From == (int) 2) {
            Log.d(TAG, "Add Food");

            Database_Value_Names Db_Value_Names = new Database_Value_Names();
            try {
                Add_Food(Imported_Data_Arraylist, Db_Value_Names, Database_Controller);
            } catch (InterruptedException e) {
                Log.d(TAG, "IO ERROR FOR ADD_FOOD");
                throw new RuntimeException(e);

            }

        }else if (Sent_From == (int) 3) {
            Log.d(TAG, "Get Food");

            Database_Value_Names Db_Value_Names = new Database_Value_Names();
            Get_Food_Data(Imported_Data_Arraylist, new Interface_Food_Data_Found.Food_Data_FoundListener(){
                @Override
                public void Food_Data_Found(ArrayList<ArrayList<Nutrition_Data_From_Db>> foodDataList) {
                    Log.d(TAG, "Food_Data found overrided in opening function ");
                }
            });

        } else {
            Log.d(TAG, "Login: Unexpected Page Transfer");
            To_Login(Imported_Data_Arraylist.get(0));
        }
    }

    private void Validate_Username(ArrayList<String> Imported_Data_Arraylist, DatabaseReference Database_Controller) {
        User Creating_User_Details = new User();
        Database_Value_Names Db_Value_Names = new Database_Value_Names();

        //set values from the arraylist imported
        Creating_User_Details.setUsername(Imported_Data_Arraylist.get(0));
        Creating_User_Details.setPassword(Imported_Data_Arraylist.get(1));
        Creating_User_Details.setEmail(Imported_Data_Arraylist.get(2));

        Creating_User_Details.setMale(reusableFunctions.String_To_Bool(Imported_Data_Arraylist.get(3)));

        Creating_User_Details.setAge(reusableFunctions.To_Int(Imported_Data_Arraylist.get(4)));
        Creating_User_Details.setHeight_Cm(Imported_Data_Arraylist.get(5));
        Creating_User_Details.setWeight_Kg(Imported_Data_Arraylist.get(6));
        Creating_User_Details.setBmr(Imported_Data_Arraylist.get(7));

        //log the data to see if accurate
        Log.d(TAG, "Username = " + Creating_User_Details.getUsername());
        Log.d(TAG, "Password = " + Creating_User_Details.getPassword());
        Log.d(TAG, "Email = " + Creating_User_Details.getEmail());
        Log.d(TAG, "Sex = " + Creating_User_Details.isMale());
        Log.d(TAG, "Age = " + Creating_User_Details.getAge());
        Log.d(TAG, "Height_Cm = " + Creating_User_Details.getHeight_Cm());
        Log.d(TAG, "Weight_Kg = " + Creating_User_Details.getWeight_Kg());
        Log.d(TAG, "Rmi = " + Creating_User_Details.getBmr());


        Database_Controller.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //sees if username exists in db. if does creates account, else taken back to create account
                if (!(snapshot.child(Db_Value_Names.getDb_Users_Db_Name()).child(Creating_User_Details.getUsername()).exists())) {
                    Creation_Type = "Users";
                    Log.d(TAG, "Validate_Username: Username doesn't exist. creating account");
                    Add_Account(Creating_User_Details, Db_Value_Names, Creation_Type, Database_Controller);
                } else {
                    Log.d(TAG, "Validate_Username: Username exists. Returning user to Create_Account Page");
                    reusableFunctions.Create_Toast(getApplicationContext(), "Change Username");
                    Page_Movement_Intent = new Intent(Database.this, Create_Account.class);
                    startActivity(Page_Movement_Intent);
                }
                //return null;
            }

            @Override
            //tells u if db is out of date
            public void onCancelled(@NonNull DatabaseError error) {
                reusableFunctions.Create_Toast(getApplicationContext(), "Database Failure. Calling onCancelled");
                Log.d(TAG, "!!!!!!! MAY NEED TO UPDATE FIREBASE RULES !!!!!!!");
                To_Create_Account();
            }
        });
    }

    public void Add_Account(User Creating_Users_Details, Database_Value_Names Db_Value_Names, String Creation_Type, DatabaseReference Database_Controller) {

        if (Creation_Type.equals("Users")) {
            //encrypt data for db
            Log.d(TAG, "Add Account: Starting Encryption");
            Encryption_Decryption_Class Encryption_Class = new Encryption_Decryption_Class();
            try {
                Creating_Users_Details.setPassword(Encryption_Class.Encryption_Function(Creating_Users_Details.getPassword()));
                Creating_Users_Details.setEmail(Encryption_Class.Encryption_Function(Creating_Users_Details.getEmail()));
                Creating_Users_Details.setBmr(Encryption_Class.Encryption_Function(Creating_Users_Details.getBmr()));
                Creating_Users_Details.setHeight_Cm(Encryption_Class.Encryption_Function(Creating_Users_Details.getHeight_Cm()));
                Creating_Users_Details.setWeight_Kg(Encryption_Class.Encryption_Function(Creating_Users_Details.getWeight_Kg()));
                Log.d(TAG, "Add Account: Encryption Complete");
                //error when encryptng
            } catch (Exception e) {
                e.printStackTrace();
                To_Create_Account();
                Log.d(TAG, "Add Account: Encryption Failed");
            }
            //These names must be the same as the User/ Tester classes (they go into the DB)
            //add values in a hashmap to add to db
            Information_Hashmap.put(Db_Value_Names.getDb_Age_Name(), Creating_Users_Details.getAge());
            Information_Hashmap.put(Db_Value_Names.getDb_Email_Name(), Creating_Users_Details.getEmail());
            Information_Hashmap.put(Db_Value_Names.getDb_Height_Name(), Creating_Users_Details.getHeight_Cm());
            Information_Hashmap.put(Db_Value_Names.getDb_Password_Name(), Creating_Users_Details.getPassword());
            Information_Hashmap.put(Db_Value_Names.getDb_Bmr_Name(), Creating_Users_Details.getBmr());
            Information_Hashmap.put(Db_Value_Names.getDb_isMale_Name(), Creating_Users_Details.isMale());
            Information_Hashmap.put(Db_Value_Names.getDb_Username_Name(), Creating_Users_Details.getUsername());
            Information_Hashmap.put(Db_Value_Names.getDb_Weight_Name(), Creating_Users_Details.getWeight_Kg());

            Log.d(TAG, "Add Account: Information packed for finish");

            Database_Controller.child(Db_Value_Names.getDb_Users_Db_Name()).child(Creating_Users_Details.getUsername()).updateChildren(Information_Hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                //add data to db (if successful login)/ go back to create account if fail
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        To_Login(Creating_Users_Details.getUsername());
                        Log.d(TAG, "Add Account: Account Created");
                    } else {
                        reusableFunctions.Create_Toast(getApplicationContext(), "Network/ Database Error. Try Again");
                        To_Create_Account();
                        Log.d(TAG, "Add Account: Account Filed Creation?");
                    }
                }
            });
        }else{
            Log.d(TAG,"Db Name doesnt match");
        }
    }


    public void Add_Food(ArrayList<String> Name_Date_Food_Arraylist, Database_Value_Names Db_Value_Names, DatabaseReference Database_Controller) throws InterruptedException {
        Log.d(TAG, "Logging Food");

        String Username = Name_Date_Food_Arraylist.get(0);
        String Date = Name_Date_Food_Arraylist.get(1);
        String Food_Name = Name_Date_Food_Arraylist.get(2);
        String Portion_Size = Name_Date_Food_Arraylist.get(3);
        String Calories = Name_Date_Food_Arraylist.get(4);
        String Fat = Name_Date_Food_Arraylist.get(5);
        String Cholesterol = Name_Date_Food_Arraylist.get(6);
        String Sodium = Name_Date_Food_Arraylist.get(7);
        String Potassium = Name_Date_Food_Arraylist.get(8);
        String Total_Carbs = Name_Date_Food_Arraylist.get(9);
        String Sugar = Name_Date_Food_Arraylist.get(10);
        String Dietary_Fiber = Name_Date_Food_Arraylist.get(11);
        String Protein = Name_Date_Food_Arraylist.get(12);
        String Meal_Type = Name_Date_Food_Arraylist.get(13);

        DatabaseReference Db_Reference = Database_Controller.child(Db_Value_Names.getDb_Food_Name_Name()).child(Username).child(Todays_Date).child(Meal_Type).child("Undefined");

        //add values in a hashmap to add to db
        Information_Hashmap.put(Db_Value_Names.getDb_Username_Name(), Username);
        Information_Hashmap.put(Db_Value_Names.getDb_Date_Name(), Date);
        Information_Hashmap.put(Db_Value_Names.getDb_Food_Name_Name(), Food_Name);
        Information_Hashmap.put(Db_Value_Names.getDb_Portion_Name(), Portion_Size);
        Information_Hashmap.put(Db_Value_Names.getDb_Caloires_Name(),Calories );
        Information_Hashmap.put(Db_Value_Names.getDb_Fat_Name(), Fat);
        Information_Hashmap.put(Db_Value_Names.getDb_Cholesterol_Name(), Cholesterol);
        Information_Hashmap.put(Db_Value_Names.getDb_Sodium_Name(), Sodium);
        Information_Hashmap.put(Db_Value_Names.getDb_Potassium_Name(), Potassium);
        Information_Hashmap.put(Db_Value_Names.getDb_Total_Carbs_Name(), Total_Carbs);
        Information_Hashmap.put(Db_Value_Names.getDb_Sugar_Name(), Sugar);
        Information_Hashmap.put(Db_Value_Names.getDb_Dietary_Fiber_Name(), Dietary_Fiber);
        Information_Hashmap.put(Db_Value_Names.getDb_Protein_Name(), Protein);
        Information_Hashmap.put(Db_Value_Names.getDb_Meal_Type_Name(), Meal_Type);

        Log.d(TAG, "Add Account: Information packed for finish");

        //Path

        //creates random primary key
        //DatabaseReference Db_Reference = Database_Controller.child(Db_Value_Names.getDb_Food_Name_Name()).child(Username).child(Todays_Date).child(Meal_Type).push());

        Find_Next_Food_Pk(Username, Meal_Type, new OnPrimaryKeyFoundListener() {
            @Override
            public void onPrimaryKeyFound(String nextPk) {
                // HERE IS DB CODE
                Log.d(TAG, "New Pk Found");

                DatabaseReference Db_Reference = Database_Controller.child(Db_Value_Names.getDb_Food_Name_Name()).child(Username)
                        .child(Todays_Date).child(Meal_Type).child(Next_Pk.toString()); //should be .child(add pk)

                Log.d(TAG, Db_Reference.toString());

                Log.d(TAG, "Next_Pk after save = " + Next_Pk);
                Db_Reference.setValue(Information_Hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    // add data to db (if successful login)/ go back to create account if fail
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Food Added successfully");
                            reusableFunctions.Create_Toast(getApplicationContext(), "Food Added To Database...");
                            Log.d(TAG, "home Clicked");
                            Log.d(TAG, "Search Button Pressed");
                            Intent intent = new Intent(Database.this, MainActivity2.class);
                            startActivity(intent);
                        } else {
                            reusableFunctions.Create_Toast(getApplicationContext(), "Network/ Database Error. Try Again");
                            To_Create_Account();
                            Log.d(TAG, "Add Account: Account Failed Creation?");
                        }
                    }
                });
            }
        });


    }


    public interface OnPrimaryKeyFoundListener {
        void onPrimaryKeyFound(String nextPk);
    }

    private void Find_Next_Food_Pk(String Username, String Meal_Type, OnPrimaryKeyFoundListener listener){
        Database_Value_Names Db_Value_Names = new Database_Value_Names();
        Database_Controller.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int int_x = 0;
                String str_x;
                Boolean Found_Next_Pk = false;
                while (Found_Next_Pk == false){
                    str_x = reusableFunctions.Int_To_String(int_x);
                    DataSnapshot pathSnapshot = snapshot
                            .child(Db_Value_Names.getDb_Food_Name_Name()).child(Username)
                            .child(Todays_Date).child(Meal_Type).child(str_x);
                    if (pathSnapshot.exists()) {
                        int_x = int_x + 1;
                        str_x = reusableFunctions.Int_To_String(int_x);
                        Log.d(TAG, "PK " + int_x + " Exists");
                    } else {
                        // Found the first missing primary key
                        Log.d(TAG, "PK " + str_x + " Does Not Exist - Available as new PK");
                        Next_Pk = str_x;
                        Log.d(TAG, "Nxet_Pk = " + Next_Pk);
                        Found_Next_Pk = true;
                        // Call the callback with the found PK
                        listener.onPrimaryKeyFound(Next_Pk);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                reusableFunctions.Create_Toast(getApplicationContext(), "Database Error. Cancelled");
                Log.d(TAG, "Error iterating Food Db in Db_Food_Primary_Key_Checker");
            }

        });
    }



    public void Get_Food_Data(ArrayList<String> Name_Date_Meal_Type, Interface_Food_Data_Found.Food_Data_FoundListener callback) {
        //return type was string

        Database_Controller = FirebaseDatabase.getInstance().getReference();

        Database_Value_Names Db_Value_Names = new Database_Value_Names();
        Log.d(TAG, "\n\n -------------------- Fetching Food Data  -------------------- \n");

        String Username = Name_Date_Meal_Type.get(0);
        String Date = Name_Date_Meal_Type.get(1);
        String Meal_Type = Name_Date_Meal_Type.get(2);
        Log.d(TAG, "Username = " + Username + "date = "+ Date + "Meal_Type=" + Meal_Type);
        Log.d(TAG, "Vars Set");

        // Reference to the "Food" node in the database
        //DatabaseReference Db_Reference = Database_Controller.child(Db_Value_Names.getDb_Food_Name_Name());
        Log.d(TAG, "Path = " + Db_Value_Names.getDb_Food_Name_Name() + Username + Todays_Date + Meal_Type);

        DatabaseReference Db_Reference = Database_Controller.child(Db_Value_Names.getDb_Food_Name_Name())
                .child(Username)
                .child(Todays_Date).child(Meal_Type);

        Db_Reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int i = 0;
                Log.d(TAG, "path set to " + Db_Value_Names.getDb_Food_Name_Name() + Username + Todays_Date + Meal_Type + reusableFunctions.Int_To_String(i));
                ArrayList<ArrayList<Nutrition_Data_From_Db>> Nested_Food_Data_List = new ArrayList<>();
                ArrayList<Nutrition_Data_From_Db> Breakfast_Food_List = new ArrayList<>();
                ArrayList<Nutrition_Data_From_Db> Lunch_Food_List = new ArrayList<>();
                ArrayList<Nutrition_Data_From_Db> Dinner_Food_List = new ArrayList<>();


                Boolean Continue_Collecting_Data = true;

                while (Continue_Collecting_Data == true) {

                    if (dataSnapshot.child(reusableFunctions.Int_To_String(i)).exists()) {
                        Log.d(TAG, "food data found for the given Username");

                        Nutrition_Data_From_Db Gathered_Food = dataSnapshot.child(reusableFunctions.Int_To_String(i))
                                .getValue(Nutrition_Data_From_Db.class);
                        Log.d(TAG, "got info for id" + i);
                        i++;

                        switch (Meal_Type) {
                            case "Breakfast":
                                Breakfast_Food_List.add(Gathered_Food);
                                Log.d(TAG, "Breakfast_Added");
                                break;
                            case "Lunch":
                                Lunch_Food_List.add(Gathered_Food);
                                Log.d(TAG, "Lunch_Added");
                                break;
                            case "Dinner":
                                Dinner_Food_List.add(Gathered_Food);
                                Log.d(TAG, "Dinner_Added");
                                break;
                            default:
                                Log.d(TAG, "Meal Type Not Identified");
                        }


                    } else {

                        Log.d(TAG, "No food data found for the given Username");

                        switch (Meal_Type) {
                            case "Breakfast":
                                Breakfast_Food_List.add(new Nutrition_Data_From_Db());
                                Log.d(TAG, "Default Breakfast_Added");
                                break;
                            case "Lunch":
                                Lunch_Food_List.add(new Nutrition_Data_From_Db());
                                Log.d(TAG, "Default Lunch_Added");
                                break;
                            case "Dinner":
                                Dinner_Food_List.add(new Nutrition_Data_From_Db());
                                Log.d(TAG, "Default Dinner_Added");
                                break;
                            default:
                                Log.d(TAG, "Meal Type Not Identified");
                        }

                        Continue_Collecting_Data = false;
                        //return null;
                    }
                }

                Nested_Food_Data_List.add(Breakfast_Food_List);
                Nested_Food_Data_List.add(Lunch_Food_List);
                Nested_Food_Data_List.add(Dinner_Food_List);

                // Call the success callback with the retrieved data
                Log.d(TAG, "sending back");
                callback.Food_Data_Found(Nested_Food_Data_List);
            }

            @Override
            public void onCancelled (DatabaseError databaseError){
                Log.d(TAG, "Database Error: " + databaseError.getMessage());
            }

        });
    }

    private Nutrition_Data_From_Db No_Food_Added(Nutrition_Data_From_Db Checking_Arraylist){
        if(Checking_Arraylist.getName_Of_Food().equals("Undefined")){
            //Checking_Arraylist.add(new Nutrition_Data_From_Db());
            reusableFunctions.Create_Toast(getApplicationContext(), "no food data for today");

        }else{
            return Checking_Arraylist;
        }
        return Checking_Arraylist;
    }


    private void Login(ArrayList<String> Imported_Data_Arraylist, DatabaseReference Database_Controller) {

        // ------------------------------ LOGIN FUNCTION! --------------------------------------------
        // -------------------------------------------------------------------------------------------
        //THIS CODE IS FOR LEARNING/ DEMO PURPOSES


        // ---- KEY FOR PASSED VARIABLES ----
        // Imported_Data_Arraylist: all data passed from login page (including username and password user entered)
        // Database_Controller: Reference to Firebase Realtime Db

        User Login_User_Details = new User();
        Database_Value_Names Db_Value_Names = new Database_Value_Names();//contains names of database fields
        //get the username and password which the user entered.

        Login_User_Details.setUsername(Imported_Data_Arraylist.get(0));
        Login_User_Details.setPassword(Imported_Data_Arraylist.get(1));
        String Username = Login_User_Details.getUsername();
        Log.d(TAG, "username and password taken from string arraylist");


        Database_Controller.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //checks if the username exists in the database (if exists then checks if the password saved with the username, matches the password the user entered previously)
                //if not, go back to login page

                if (snapshot.child(Db_Value_Names.getDb_Users_Db_Name()).child(Username).exists()) { //was dataSnapshot instead of snapshot

                    //this class is capable of encrypting data before sending to the database
                    Encryption_Decryption_Class Encryption_Class = new Encryption_Decryption_Class();

                    //default values
                    String Unencrypted_Username = "N/A";
                    String Unencrypted_Password = "N/A";

                    //Unencrypted Username is kept encase encryption of username is done (database needs resetting before encrypting username)
                    //get all data from db based on username

                    //goes to the database and gathers all data from the database on the account the user is trying to login to
                    //this is done to get the password and compare (if fails user logs in again)
                    Log.d(TAG, "Gathering User Info From Db");
                    User Gathered_Account_Details = snapshot.child(Db_Value_Names.getDb_Users_Db_Name()).child(Username).getValue(User.class);//send users username to the Users_Data class

                    //logs out the info gathered from the database
                    Log.d(TAG, "Gathered data from db ");
                    Log.d(TAG, "Attempting Decryption");

                    try {
                        //start decrypting data from the database
                        Log.d(TAG, "Starting Decryption");

                        //encase Username is encrypted in future. (uncomment all this)
                        //Unencrypted_Username = Encryption_Class.decrypt(Gathered_Account_Details.getUsername());
                        //Log.d(TAG,"Login: Username decrypted");

                        //decrypt the password from the database
                        Unencrypted_Password = Encryption_Class.Decryption_Function(Gathered_Account_Details.getPassword());
                        Log.d(TAG, "Decryption of password Success");

                        //if decryption fails show error in logs
                    } catch (Exception e) {
                        //creates a pop up message showing failure
                        reusableFunctions.Create_Toast(getApplicationContext(), "Decryption Failed. Crashing");
                        Log.d(TAG, "Decryption Failed");
                        e.printStackTrace();
                    }

                    //if password entered by the user matches the coount they trying to login to, Allow Login
                    Log.d(TAG, "Checking Pw");
                    if (Unencrypted_Password.equals((Login_User_Details.getPassword()))) {
                        //say login successul
                        reusableFunctions.Create_Toast(getApplicationContext(), "Login Successful");
                        Log.d(TAG, "Successful Login");

                        //REPLACES Password locally from database TO PREVENT DATA BEING USED or accessed (the correct password is still in database)
                        Gathered_Account_Details.setPassword("Why Do U Wanna Know?");

                        //THE USER HAS LOGGED IN SUCCESSFULLY
                        //prepares the user to carry on through the app (Bmi Calculations variable setting)
                        Login_Success(Gathered_Account_Details, reusableFunctions);
                    } else {
                        //If the user enters the password incorrectly. they are taken back to the login page to re-enter details
                        reusableFunctions.Create_Toast(getApplicationContext(), "Incorrect Username Or Password");
                        Log.d(TAG, "Password Doesn't Exist");
                        To_Login(Login_User_Details.getUsername());
                    }
                } else {//if username doesnt exist
                    //If the username doesnt exist. they are taken back to the login page to re-enter details
                    reusableFunctions.Create_Toast(getApplicationContext(), "Incorrect Username Or Password");
                    Log.d(TAG, "Username Doesn't Exist");
                    To_Login(Login_User_Details.getUsername());
                }
                //return null;
            }
            @Override//if database error. often cause security settings need changing or no wifi
            public void onCancelled(@NonNull DatabaseError error) {
                reusableFunctions.Create_Toast(getApplicationContext(), "Database Error. Cancelled");
                //sends user back to login page
                To_Login(Login_User_Details.getUsername());
            }
        });
    }



    public void Edit_User_Data(String Creation_Type, HashMap<String, Object> Information_Hashmap, String Username) {
        Log.d(TAG, "In Edit Data Function");
        DatabaseReference Database_Controller = null;
        Database_Controller = FirebaseDatabase.getInstance().getReference();
        Database_Value_Names Db_Value_Names = new Database_Value_Names();

        if (Creation_Type.equals("Users")) {
            Log.d(TAG, "In users area");
            Database_Controller.child(Db_Value_Names.getDb_Users_Db_Name()).child(Username).updateChildren(Information_Hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                //add data to db (if successful login)/ go back to create account if fail
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Information Edited");
                    } else {
                        reusableFunctions.Create_Toast(getApplicationContext(), "Network/ Database Error. Try Again");
                        Log.d(TAG, "Edit Failed!");
                    }
                }
            });
        }else{
            Log.d(TAG,"Db Name doesnt match");
        }
    }


    // --------------------------------------- EXTRAS -----------------------------------------

    private void To_Login(String Username) {
        //pass username and take user to login page
        Log.d(TAG, "To_Login_Called");
        Page_Movement_Intent = new Intent(getApplicationContext(), Login.class);//

        Page_Movement_Intent.putExtra("Username", Username);
        Log.d(TAG, "Moving to login");
        startActivity(Page_Movement_Intent);
    }

    private void To_Create_Account() {
        //takes user to create account page
        Page_Movement_Intent = new Intent(Database.this, Create_Account.class);//
        startActivity(Page_Movement_Intent);
    }

    private void Login_Success(User Gathered_Account_Details, ReusableFunctions reusableFunctions) {
        String Decrypted_Bmr = "Undeclared!";
        String Decrypted_Height = "Undeclared!";
        Log.d(TAG, "Login_Success_Function");
        //if bmi not calculated, calculate, else to Calorie_homepage

        try{
            Encryption_Decryption_Class Decryption = new Encryption_Decryption_Class();
            Decrypted_Bmr = Decryption.Decryption_Function(Gathered_Account_Details.getBmr());
            Decrypted_Height = Decryption.Decryption_Function(Gathered_Account_Details.getHeight_Cm());

        }catch(Exception E){
            Log.d(TAG, "Decryption Failed");

        }
        Log.d(TAG, "Get Username = " + Gathered_Account_Details.getUsername());
        Log.d(TAG, "Get Bmr = " + Decrypted_Bmr);
        Log.d(TAG, "Get Height = " + Decrypted_Height);


            if(Decrypted_Bmr.equals("Undeclared")){
                Page_Movement_Intent = new Intent(Database.this, Enter_Height.class);//
                Log.d(TAG, "taking user to bmi calcs");
                Page_Movement_Intent.putExtra("User_Data", Create_String_Arraylist(Gathered_Account_Details));
                startActivity(Page_Movement_Intent);

            }else{
                Page_Movement_Intent = new Intent(Database.this, OptionForBmi.class);//
                Log.d(TAG, "taking user to bmi option");
                Page_Movement_Intent.putExtra("User_Data", Create_String_Arraylist(Gathered_Account_Details));
                startActivity(Page_Movement_Intent);
            }
    }

    private ArrayList<String> Create_String_Arraylist(User Gathered_User_Data) {

        Log.d(TAG, "Data to Arraylist called");
        String Age = reusableFunctions.Int_To_String(Gathered_User_Data.getAge());
        ArrayList<String> User_Data_Arraylist = new ArrayList<>();

        Log.d(TAG, "Adding Data");
        User_Data_Arraylist.add(Age);
        User_Data_Arraylist.add(Gathered_User_Data.getEmail());
        User_Data_Arraylist.add(Gathered_User_Data.getHeight_Cm());
        User_Data_Arraylist.add(Gathered_User_Data.getPassword());
        User_Data_Arraylist.add(Gathered_User_Data.getBmr());
        User_Data_Arraylist.add(String.valueOf(Gathered_User_Data.isMale()));
        User_Data_Arraylist.add(Gathered_User_Data.getUsername());
        User_Data_Arraylist.add(Gathered_User_Data.getWeight_Kg());

        Log.d(TAG, "Returning");

        return User_Data_Arraylist;
    }
    Database_Value_Names Db_Names = new Database_Value_Names();

    private void Update_User_Details(String Username, HashMap<String, Object> Data_To_Update) {
        DatabaseReference Controller = FirebaseDatabase.getInstance().getReference(Db_Names.getDb_Users_Db_Name());

        //update data in db. of fail say fails
        Controller.child(Username).updateChildren(Data_To_Update).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "User details updated successfully for " + Username);
            reusableFunctions.Create_Toast(getApplicationContext(), "User details updated successfully.");

        }).addOnFailureListener(e -> {
            Log.e(TAG, "Failed to update user details for " + Username + ": " + e.getMessage());
            reusableFunctions.Create_Toast(getApplicationContext(), "Failed to update user details. Try again.");

        });
    }
}