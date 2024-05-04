package com.example.caloriediary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Nav_Bar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Nav_Bar extends Fragment {

    private static final String TAG = "Nav_Bar_Fragment";
    private TextView Info_Text_View;
    private ImageView Info_Img;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Nav_Bar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Nav_Bar.
     */
    // TODO: Rename and change types and number of parameters
    public static Nav_Bar newInstance(String param1, String param2) {
        Nav_Bar fragment = new Nav_Bar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_nav__bar, container, false);

        //interface
        Info_Text_View = rootView.findViewById(R.id.Info_Text);
        Info_Img = rootView.findViewById(R.id.Info_Img);

        //Info Section
        Info_Text_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info_Section_Clicked();
            }
        });

        Info_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info_Section_Clicked();
            }
        });

        return rootView;
    }

    private void Info_Section_Clicked(){
        Log.d(TAG, "Info Clicked");
        Intent pageMovementIntent = new Intent(getContext(), Info.class);

        startActivity(pageMovementIntent);

    }



}