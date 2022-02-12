package com.example.task_champion_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.task_champion_android.R;
import com.example.task_champion_android.databinding.ActivityDetailsBinding;
import com.example.task_champion_android.fragments.BlankFragment1;
import com.example.task_champion_android.fragments.BlankFragment2;
import com.example.task_champion_android.fragments.BlankFragment3;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BlankFragment1 frg=new BlankFragment1();
        BlankFragment2 frg1=new BlankFragment2();
        BlankFragment3 frg2=new BlankFragment3();

        FragmentManager manager=getSupportFragmentManager();

        FragmentTransaction transaction=manager.beginTransaction();

        transaction.add(R.id.My_Container_1_ID, frg, "Frag_Top_tag");
        transaction.add(R.id.My_Container_2_ID, frg1, "Frag_Middle_tag");
        transaction.add(R.id.My_Container_3_ID, frg2, "Frag_Bottom_tag");


        transaction.commit();
    }
}