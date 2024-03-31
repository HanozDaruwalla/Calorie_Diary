package com.example.caloriediary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.databinding.ActivityDatabaseBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Database extends AppCompatActivity {

    ReusableFunctions reusableFunctions = new ReusableFunctions();
    private static final String TAG = "Database_Class";


    HashMap<String, Object> Information_Hashmap = new HashMap<>();
    Intent Page_Movement_Intent;


    private String Db_Node = "";
    String Creation_Type = "";
    int Sent_From = -1;
    ArrayList<String> Imported_Data_Arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"Login: onCreate");
        super.onCreate(savedInstanceState);
        com.example.caloriediary.databinding.ActivityDatabaseBinding binding = ActivityDatabaseBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_database);
        setContentView(binding.getRoot());

        Imported_Data_Arraylist = getIntent().getExtras().getStringArrayList("Sent_Info");
        Log.d(TAG, "Imported_Arraylist.Username() = " + Imported_Data_Arraylist.get(0));
        Sent_From = getIntent().getExtras().getInt("Sent_From");

        //FirebaseAuth mAuth = FirebaseAuth.getInstance(); only for reset pw
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        DatabaseReference Database_Controller = null;
        Database_Controller = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG,"Login: onCreate Complete");


        // 0 = Create_Account, 1 = login, 2 = Add Product From Db
        if (Sent_From == (int)0) {// says is redundant but crashes without
            Log.d(TAG,"Login: Create Account Section Called");
            Creation_Type = "Users";
            Db_Node = "Users";

            Validate_Username(Imported_Data_Arraylist,Database_Controller);

        } else if (Sent_From == (int)1) { // says is redundant but crashes without
            Log.d(TAG,"Login: Login Section Called");
            Creation_Type = "Users";
            Db_Node = "Users";

            Login(Imported_Data_Arraylist, Database_Controller);

        } else {
            Log.d(TAG,"Login: Unexpected Page Transfer");
            To_Login(Imported_Data_Arraylist.get(0));
        }
    }

    private void Validate_Username(ArrayList<String> Imported_Data_Arraylist, DatabaseReference Database_Controller) {
        User Creating_User_Details = new User();

        Creating_User_Details.setUsername(Imported_Data_Arraylist.get(0));
        Creating_User_Details.setPassword(Imported_Data_Arraylist.get(1));
        Creating_User_Details.setEmail(Imported_Data_Arraylist.get(2));
        Log.d(TAG, "Username = " + Creating_User_Details.getUsername());
        Log.d(TAG, "Password = " + Creating_User_Details.getPassword());
        Log.d(TAG, "Email = " + Creating_User_Details.getEmail());


        Database_Controller.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child(Db_Node).child(Creating_User_Details.getUsername()).exists())){
                    Creation_Type = "Users";
                    Log.d(TAG, "Validate_Username: Username doesnt exist. creating account");
                    Add_Account(Creating_User_Details, Creation_Type, Database_Controller);
                }else{
                    Log.d(TAG, "Validate_Username: Username exists. Returning user to Create_Account Page");
                    reusableFunctions.Create_Toast(getApplicationContext(),"Change Username");
                    Page_Movement_Intent = new Intent(Database.this, Create_Account.class);
                    startActivity(Page_Movement_Intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                reusableFunctions.Create_Toast(getApplicationContext(),"Database Failure. Calling onCancelled");
                Log.d(TAG, "!!!!!!! MAY NEED TO UPDATE FIREBASE RULES !!!!!!!");
                To_Create_Account();
            }
        });
    }

    public void Add_Account(User Creating_Users_Details, String Creation_Type, DatabaseReference Database_Controller) {
        reusableFunctions.Create_Toast(getApplicationContext(), "Creating Account");

        if (Creation_Type.equals("Users")) {

            Log.d(TAG, "Add Account: Starting Encryption");
            Encryption_Decryption_Class Encryption_Class = new Encryption_Decryption_Class();
            try {
                Creating_Users_Details.setPassword(Encryption_Class.encrypt(Creating_Users_Details.getPassword()));
                Creating_Users_Details.setEmail(Encryption_Class.encrypt(Creating_Users_Details.getEmail()));
                Log.d(TAG, "Add Account: Encryption Complete");
            } catch (Exception e) {
                e.printStackTrace();
                To_Create_Account();
                Log.d(TAG, "Add Account: Encryption Failed");
            }

            Information_Hashmap.put("Username", Creating_Users_Details.getUsername());
            Information_Hashmap.put("Password", Creating_Users_Details.getPassword());
            Information_Hashmap.put("Email", Creating_Users_Details.getEmail());
            Log.d(TAG, "Add Account: Information packed for finish");

            Database_Controller.child(Db_Node).child(Creating_Users_Details.getUsername()).updateChildren(Information_Hashmap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                reusableFunctions.Create_Toast(getApplicationContext(), "Please Login");
                                To_Login(Creating_Users_Details.getUsername());
                                Log.d(TAG, "Add Account: Account Created");
                            } else {
                                reusableFunctions.Create_Toast(getApplicationContext(), "Network/ Database Error. Try Again");
                                To_Create_Account();
                                Log.d(TAG, "Add Account: Account Faled Creation?");
                            }
                        }
                    });
        } else {
            reusableFunctions.Create_Toast(getApplicationContext(), "Products Sections commented");
            /*
            if (Creation_Type.equals("Products")) {
                String Product_Name, Product_Description, Product_Price, Product_Stock;
                Double Converted_Product_Price;
                int Converted_Product_Stock;
                int New_Id;

                Product_Name = Info[0];
                Product_Description = Info[1];
                Product_Price = Info[2];
                Converted_Product_Price = To_Double(Product_Price);
                Product_Stock = Info[3];
                Converted_Product_Stock = To_Int(Product_Stock);

                String GetID = getString(R.string.Max_ID_Value);
                New_Id = To_Int(GetID);

                Information_Hashmap.put("Product_ID", New_Id);
                Information_Hashmap.put("Product_Name", Product_Name);
                Information_Hashmap.put("Product_Description", Product_Description);
                Information_Hashmap.put("Product_Price", Product_Price);
                Information_Hashmap.put("Product_Stock", Product_Stock);

                Database_Controller.child(Db_Node).child(Product_Name).updateChildren(Information_Hashmap)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Create_Toast("Product Created");
                                    Page_Movement_Intent = new Intent(Database2.this, Create_Product.class);//
                                    startActivity(Page_Movement_Intent);

                                } else {
                                    Create_Toast("Network/ Database Error. Try Again");
                                }
                            }
                        });
            }

             */

        }
    }

    private void Login(ArrayList<String> Imported_Data_Arraylist, DatabaseReference Database_Controller){
        User Login_User_Details = new User();

        Login_User_Details.setUsername(Imported_Data_Arraylist.get(0));
        Login_User_Details.setPassword(Imported_Data_Arraylist.get(1));

        String Username = Login_User_Details.getUsername();
        Log.d(TAG,"Login: Username got from GUI");
        Log.d(TAG,"Login: Db_Node = " + Db_Node);
        Log.d(TAG,"Login: Username = " + Username);

        //Username Was Null. not set from the Login Class
        Database_Controller.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(Db_Node).child(Username).exists()){//was dataSnapshot

                    Encryption_Decryption_Class Encryption_Class = new Encryption_Decryption_Class();
                    Log.d(TAG,"Checking Pw");
                    String Unencrypted_Username, Unencrypted_Password = "Unassigned";
                    //Unencrypted Username is kept incase encryption of username is done

                    Log.d(TAG,"Gathering User Info From Db");
                    User Gathered_Account_Details = snapshot.child(Db_Node).child(Username).getValue(User.class);//send users username to the Users_Data class
                    Log.d(TAG,"Attempting Decryption");
                    try {
                        //incase Username is encrpyted in future.
                        Log.d(TAG,"Starting Decryption");
                        //Add is u encrypt Username
                        //Unencrypted_Username = Encryption_Class.decrypt(Gathered_Account_Details.getUsername());
                        //Log.d(TAG,"Login: Username decrypted");
                        Unencrypted_Password = Encryption_Class.decrypt(Gathered_Account_Details.getPassword());
                        Log.d(TAG,"Decryption Success");
                        Log.d(TAG,"Decrypted_Password = " + Unencrypted_Password );
                    } catch (Exception e) {
                        reusableFunctions.Create_Toast(getApplicationContext(),"Decryption Failed. Crashing");
                        Log.d(TAG,"Decryption Failed");
                        e.printStackTrace();
                    }

                    if(Unencrypted_Password.equals((Login_User_Details.getPassword()))){
                        reusableFunctions.Create_Toast(getApplicationContext(),"Login Successful");
                        Log.d(TAG,"Successful Login");
                        //Page_Movement_Intent = new Intent(Database2.this, Menu.class);//
                        //startActivity(Page_Movement_Intent);
                    }else{
                        reusableFunctions.Create_Toast(getApplicationContext(),"Incorrect Username Or Password");
                        Log.d(TAG,"Password Doesnt Exist");
                        To_Login(Login_User_Details.getUsername());
                    }
                }else{
                    reusableFunctions.Create_Toast(getApplicationContext(),"Incorrect Username Or Password");
                    Log.d(TAG,"Username Doesnt Exist");
                    To_Login(Login_User_Details.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                reusableFunctions.Create_Toast(getApplicationContext(),"Database Error. Cancelled");
                To_Login(Login_User_Details.getUsername());
            }
        });
    }

    private void To_Login(String Username){

        reusableFunctions.Create_Toast(getApplicationContext(),"Going To Login");
        Page_Movement_Intent = new Intent(getApplicationContext(), Login.class);//
        Page_Movement_Intent.putExtra("Username",Username);
        startActivity(Page_Movement_Intent);
    }

    private void To_Create_Account(){
        reusableFunctions.Create_Toast(getApplicationContext(),"Going To Login");
        Page_Movement_Intent = new Intent(Database.this, Create_Account.class);//
        startActivity(Page_Movement_Intent);
    }
}







