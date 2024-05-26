package com.example.caloriediary.Database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.Bmi_Calc.Enter_Height;
import com.example.caloriediary.Creating_Account_And_Login.Create_Account;
import com.example.caloriediary.Creating_Account_And_Login.Encryption_Decryption_Class;
import com.example.caloriediary.Creating_Account_And_Login.Login;
import com.example.caloriediary.Creating_Account_And_Login.User;
import com.example.caloriediary.R;
import com.example.caloriediary.RecyclerView.Item;
import com.example.caloriediary.RecyclerView.ItemAdapter;
import com.example.caloriediary.ReusableFunctions;
import com.example.caloriediary.databinding.ActivityDatabaseBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Login: onCreate");
        super.onCreate(savedInstanceState);
        com.example.caloriediary.databinding.ActivityDatabaseBinding binding = ActivityDatabaseBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_database);
        setContentView(binding.getRoot());

        //gets the data the user entered
        Imported_Data_Arraylist = getIntent().getExtras().getStringArrayList("Sent_Info");
        Log.d(TAG, "Imported_Arraylist.Username() = " + Imported_Data_Arraylist.get(0));

        //gets where the user came from
        Sent_From = getIntent().getExtras().getInt("Sent_From");

        //firebase extras
        //FirebaseAuth mAuth = FirebaseAuth.getInstance(); only for reset pw
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        DatabaseReference Database_Controller = null;
        Database_Controller = FirebaseDatabase.getInstance().getReference();
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
            Add_Food(Imported_Data_Arraylist, Db_Value_Names, Database_Controller);

        }else if (Sent_From == (int) 3) {
            Log.d(TAG, "Add Food");

            Database_Value_Names Db_Value_Names = new Database_Value_Names();
            Get_Food_Data(Imported_Data_Arraylist, Db_Value_Names, Database_Controller);

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
        Creating_User_Details.setRmi(Imported_Data_Arraylist.get(7));

        //log the data to see if accurate
        Log.d(TAG, "Username = " + Creating_User_Details.getUsername());
        Log.d(TAG, "Password = " + Creating_User_Details.getPassword());
        Log.d(TAG, "Email = " + Creating_User_Details.getEmail());
        Log.d(TAG, "Sex = " + Creating_User_Details.isMale());
        Log.d(TAG, "Age = " + Creating_User_Details.getAge());
        Log.d(TAG, "Height_Cm = " + Creating_User_Details.getHeight_Cm());
        Log.d(TAG, "Weight_Kg = " + Creating_User_Details.getWeight_Kg());
        Log.d(TAG, "Rmi = " + Creating_User_Details.getRmi());
        Log.d(TAG, "Rmi = " + Creating_User_Details.getAge());


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
                Creating_Users_Details.setRmi(Encryption_Class.Encryption_Function(Creating_Users_Details.getRmi()));
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
            Information_Hashmap.put(Db_Value_Names.getDb_Bmr_Name(), Creating_Users_Details.getRmi());
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


    public void Add_Food(ArrayList<String> Name_Date_Food_Arraylist, Database_Value_Names Db_Value_Names, DatabaseReference Database_Controller) {

        Log.d(TAG, "Logging Food");

        String Username = Name_Date_Food_Arraylist.get(0);
        String Date = Name_Date_Food_Arraylist.get(1);
        String Food_Name = Name_Date_Food_Arraylist.get(2);
        String Portion_Size = Name_Date_Food_Arraylist.get(3);
        String Calories = Name_Date_Food_Arraylist.get(4);
        String Fat = Name_Date_Food_Arraylist.get(5);
        String Meal_Type = Name_Date_Food_Arraylist.get(6);

        //add values in a hashmap to add to db
        Information_Hashmap.put(Db_Value_Names.getDb_Username_Name(), Username);
        Information_Hashmap.put(Db_Value_Names.getDb_Date_Name(), Date);
        Information_Hashmap.put(Db_Value_Names.getDb_Food_Name_Name(), Food_Name);
        Information_Hashmap.put(Db_Value_Names.getDb_Portion_Name(), Portion_Size);
        Information_Hashmap.put(Db_Value_Names.getDb_Caloires_Name(),Calories );
        Information_Hashmap.put(Db_Value_Names.getDb_Fat_Name(), Fat);
        Information_Hashmap.put(Db_Value_Names.getDb_Meal_Type_Name(), Meal_Type);

        Log.d(TAG, "Add Account: Information packed for finish");

        DatabaseReference Db_Reference = Database_Controller.child(Db_Value_Names.getDb_Food_Name_Name()).child(Username).push();

        Db_Reference.setValue(Information_Hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            //add data to db (if successful login)/ go back to create account if fail
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Food Added successfully");
                    reusableFunctions.Create_Toast(getApplicationContext(), "Food Added To Database. Press back twice to continue");


                } else {
                    reusableFunctions.Create_Toast(getApplicationContext(), "Network/ Database Error. Try Again");
                    To_Create_Account();
                    Log.d(TAG, "Add Account: Account Filed Creation?");
                }
            }
        });
    }


    public void Get_Food_Data(ArrayList<String> Name_Date_Meal_Type, Database_Value_Names Db_Value_Names, DatabaseReference Database_Controller) {
        Log.d(TAG, "Fetching Food Data");

        String Username = Name_Date_Meal_Type.get(0);
        String Date = Name_Date_Meal_Type.get(1);
        String Meal_Type = Name_Date_Meal_Type.get(2);

        // Reference to the "Food" node in the database
        DatabaseReference Db_Reference = Database_Controller.child(Db_Value_Names.getDb_Food_Name_Name());

        // Query to get all entries where Meal_Type matches
        Query query = Db_Reference.orderByChild(Db_Value_Names.getDb_Meal_Type_Name()).equalTo(Meal_Type);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Item> foodDataList = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Retrieve the information
                        String fetchedUsername = snapshot.child(Db_Value_Names.getDb_Username_Name()).getValue(String.class);
                        String fetchedDate = snapshot.child(Db_Value_Names.getDb_Date_Name()).getValue(String.class);
                        String fetchedFoodName = snapshot.child(Db_Value_Names.getDb_Food_Name_Name()).getValue(String.class);
                        String fetchedPortionSize = snapshot.child(Db_Value_Names.getDb_Portion_Name()).getValue(String.class);
                        int fetchedCalories = snapshot.child(Db_Value_Names.getDb_Caloires_Name()).getValue(Integer.class);
                        int fetchedFat = snapshot.child(Db_Value_Names.getDb_Fat_Name()).getValue(Integer.class);
                        String fetchedMealType = snapshot.child(Db_Value_Names.getDb_Meal_Type_Name()).getValue(String.class);

                        // Check if the fetched data matches the username and date
                        if (Username.equals(fetchedUsername) && Date.equals(fetchedDate)) {
                            Item item = new Item(fetchedFoodName, fetchedCalories, fetchedFat, fetchedPortionSize);
                            foodDataList.add(item);
                        }else{
                            Log.d(TAG, "No food found by user");
                        }
                        ItemAdapter itemAdapter = new ItemAdapter(foodDataList);
                        itemAdapter.
                    }

                } else {
                    Log.d(TAG, "No data found for the given Meal Type");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Database Error: " + databaseError.getMessage());
            }
        });
    }





    private void Login(ArrayList<String> Imported_Data_Arraylist, DatabaseReference Database_Controller) {
        User Login_User_Details = new User();
        Database_Value_Names Db_Value_Names = new Database_Value_Names();

        //get the username and password and set to vars
        Login_User_Details.setUsername(Imported_Data_Arraylist.get(0));
        Login_User_Details.setPassword(Imported_Data_Arraylist.get(1));
        Log.d(TAG, "Login Function Username " + Login_User_Details.getUsername());
        Log.d(TAG, "Login Function Password " + Login_User_Details.getPassword());

        String Username = Login_User_Details.getUsername();
        Log.d(TAG, "Get Username = " + Username);
        Log.d(TAG, "Login: Username got from GUI");


        Database_Controller.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(Db_Value_Names.getDb_Users_Db_Name()).child(Username).exists()) {//was dataSnapshot
                    Encryption_Decryption_Class Encryption_Class = new Encryption_Decryption_Class();
                    Log.d(TAG, "Checking Pw");
                    String Unencrypted_Username = "N/A";
                    String Unencrypted_Password = "N/A";
                    //Unencrypted Username is kept encase encryption of username is done

                    //get all data from db based on username
                    Log.d(TAG, "Gathering User Info From Db");
                    User Gathered_Account_Details = snapshot.child(Db_Value_Names.getDb_Users_Db_Name()).child(Username).getValue(User.class);//send users username to the Users_Data class
                    Log.d(TAG, "Gathered Data Username = " + Gathered_Account_Details.getUsername());
                    Log.d(TAG, "Gathered Data Password = " + Gathered_Account_Details.getPassword());
                    Log.d(TAG, "Attempting Decryption");
                    try {
                        //encase Username is encrypted in future.
                        Log.d(TAG, "Starting Decryption");

                        //Add if u encrypt Username
                        //Unencrypted_Username = Encryption_Class.decrypt(Gathered_Account_Details.getUsername());
                        //Log.d(TAG,"Login: Username decrypted");

                        //unencrypt pw
                        Log.d(TAG, "Password = " + Gathered_Account_Details.getPassword());
                        Unencrypted_Password = Encryption_Class.Decryption_Function(Gathered_Account_Details.getPassword());
                        Log.d(TAG, "Decryption Success");
                        Log.d(TAG, "Decrypted_Password = " + Unencrypted_Password);
                    } catch (Exception e) {//if encryption failure
                        reusableFunctions.Create_Toast(getApplicationContext(), "Decryption Failed. Crashing");
                        Log.d(TAG, "Decryption Failed");
                        e.printStackTrace();
                    }
                    //if pw matches, Allow Login
                    if (Unencrypted_Password.equals((Login_User_Details.getPassword()))) {
                        reusableFunctions.Create_Toast(getApplicationContext(), "Login Successful");
                        Log.d(TAG, "Successful Login");
                        Login_Success(Gathered_Account_Details, reusableFunctions);

                    } else {
                        reusableFunctions.Create_Toast(getApplicationContext(), "Incorrect Username Or Password");
                        Log.d(TAG, "Password Doesn't Exist");
                        To_Login(Login_User_Details.getUsername());
                    }
                } else {//if username doesnt exist
                    reusableFunctions.Create_Toast(getApplicationContext(), "Incorrect Username Or Password");
                    Log.d(TAG, "Username Doesn't Exist");
                    To_Login(Login_User_Details.getUsername());
                }
            }

            @Override//if db cancelled. often cause security
            public void onCancelled(@NonNull DatabaseError error) {
                reusableFunctions.Create_Toast(getApplicationContext(), "Database Error. Cancelled");
                To_Login(Login_User_Details.getUsername());
            }
        });
    }

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
        Log.d(TAG, "Login_Success_Function");
        //if bmi not calculated, calculate, else to Calorie_homepage
        if (Gathered_Account_Details.getHeight_Cm().equals("Undeclared") || Gathered_Account_Details.getWeight_Kg().equals("Undeclared")) {
            Page_Movement_Intent = new Intent(Database.this, Enter_Height.class);//
            Log.d(TAG, "putting extra in Login Success");
            Page_Movement_Intent.putExtra("User_Data", Create_String_Arraylist(Gathered_Account_Details));
            startActivity(Page_Movement_Intent);

        } else {//if bmr already calculated
            reusableFunctions.Create_Toast(getApplicationContext(), "Go To Main App Section");
        }
    }

    private ArrayList<String> Create_String_Arraylist(User Gathered_User_Data) {

        Log.d(TAG, "Data to Arraylist called");
        String Age = reusableFunctions.Int_To_Strng(Gathered_User_Data.getAge());
        ArrayList<String> User_Data_Arraylist = new ArrayList<>();

        Log.d(TAG, "Adding Data");
        User_Data_Arraylist.add(Age);
        User_Data_Arraylist.add(Gathered_User_Data.getEmail());
        User_Data_Arraylist.add(Gathered_User_Data.getHeight_Cm());
        User_Data_Arraylist.add(Gathered_User_Data.getPassword());
        User_Data_Arraylist.add(Gathered_User_Data.getRmi());
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