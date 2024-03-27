package com.example.caloriediary;

import android.content.Intent;
import android.os.Bundle;

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
    private ActivityDatabaseBinding binding;

    HashMap<String, Object> Information_Hashmap = new HashMap<>();
    Intent Page_Movement_Intent;

    private DatabaseReference Database_Controller = null;
    private FirebaseAnalytics mFirebaseAnalytics;

    private String Db_Node = "";
    String Creation_Type = "";
    int Sent_From = -1;
    ArrayList<String> Imported_Data_Arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDatabaseBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_database);
        setContentView(binding.getRoot());

        Imported_Data_Arraylist = getIntent().getExtras().getStringArrayList("Sent_Info");
        Sent_From = getIntent().getExtras().getInt("Sent_From");

        //FirebaseAuth mAuth = FirebaseAuth.getInstance(); only for reset pw
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Database_Controller = FirebaseDatabase.getInstance().getReference();


        // 0 = Create_Account, 1 = login, 2 = Add Product From Db
        if (Sent_From == (int)0) {// says is redundant but crashes without
            Creation_Type = "Users";
            Db_Node = "Users";
            User Creating_User_Details = new User();

            Creating_User_Details.setUsername(Imported_Data_Arraylist.get(0));
            Creating_User_Details.setPassword(Imported_Data_Arraylist.get(1));
            Creating_User_Details.setEmail(Imported_Data_Arraylist.get(2));

            Validate_Username(Creating_User_Details, reusableFunctions);
        } else if (Sent_From == (int)1) { // says is redundant but crashes without
            reusableFunctions.Create_Toast(getApplicationContext(), "Logging you in.");
            Creation_Type = "Users";
            Db_Node = "Users";
            //Login(); ADD LATER

        } else {
            reusableFunctions.Create_Toast(getApplicationContext(), "Unexpected Page Transfer");
        }
    }

    private void Validate_Username(User Creating_Users_Details, ReusableFunctions reusableFunctions) {
        String Username = Creating_Users_Details.getUsername();
        Database_Controller.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child(Db_Node).child(Username).exists())){
                    reusableFunctions.Create_Toast(getApplicationContext(),"Creating Your Account");
                    Creation_Type = "Users";
                    Add_Account(Creating_Users_Details, Creation_Type, reusableFunctions);
                }else{
                    reusableFunctions.Create_Toast(getApplicationContext(),"Change Username");
                    Page_Movement_Intent = new Intent(Database.this, Create_Account.class);
                    startActivity(Page_Movement_Intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                reusableFunctions.Create_Toast(getApplicationContext(),"Database Failure. Calling onCancelled");
            }
        });
    }

    public void Add_Account(User Creating_Users_Details, String Creation_Type, ReusableFunctions reusableFunctions) {
        reusableFunctions.Create_Toast(getApplicationContext(), "Creating Account");

        if (Creation_Type.equals("Users")) {

            Encryption_Decryption_Class Encryption_Class = new Encryption_Decryption_Class();
            try {
                Creating_Users_Details.setPassword(Encryption_Class.encrypt(Creating_Users_Details.getPassword()));
                Creating_Users_Details.setEmail(Encryption_Class.encrypt(Creating_Users_Details.getEmail()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Information_Hashmap.put("Username", Creating_Users_Details.getUsername());
            Information_Hashmap.put("Password", Creating_Users_Details.getPassword());
            Information_Hashmap.put("Email", Creating_Users_Details.getEmail());

            Database_Controller.child(Db_Node).child(Creating_Users_Details.getUsername()).updateChildren(Information_Hashmap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                reusableFunctions.Create_Toast(getApplicationContext(), "Account Created");
                                //To_Login(Username);
                            } else {
                                reusableFunctions.Create_Toast(getApplicationContext(), "Network/ Database Error. Try Again");
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

}






