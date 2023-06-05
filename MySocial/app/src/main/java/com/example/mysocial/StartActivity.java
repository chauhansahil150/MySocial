package com.example.mysocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mysocial.Adapter.PostAdapter;
import com.example.mysocial.Fragments.HomeFragment;
import com.example.mysocial.Fragments.NotificationFragment;
import com.example.mysocial.Fragments.ProfileFragment;
import com.example.mysocial.Fragments.SearchFragment;
import com.example.mysocial.Model.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);




        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        selectorFragment=new HomeFragment();
                        break;
                    case R.id.nav_search:
                        selectorFragment=new SearchFragment();
                        break;
                    case R.id.nav_profile:
                        selectorFragment=new ProfileFragment();
                        break;
                    case R.id.nav_add:
                        selectorFragment=null;
                        startActivity(new Intent(StartActivity.this,PostActivity.class));
                        break;
                    case R.id.nav_heart:
                        selectorFragment=new NotificationFragment();
                        break;
                }
//                if(selectorFragment!=null){
//                    FragmentManager fm=getSupportFragmentManager();
//                    FragmentTransaction ft=fm.beginTransaction();
//                    if(selectorFragment==new HomeFragment()){
//                        ft.add(R.id.fragment_container,selectorFragment);
//                        fm.popBackStack("root_fragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                        ft.addToBackStack("root_fragment");
//                    }else{
//                        ft.replace(R.id.fragment_container,selectorFragment);
//                        ft.addToBackStack(null);
//                    }
//                    ft.commit();
//                }
                if (selectorFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment,null).setReorderingAllowed(true).addToBackStack(null).commit();
                }

                return true;
            }
        });
        Bundle intent=getIntent().getExtras();
        if(intent!=null){
            String profileId=intent.getString("publisherId");
            getSharedPreferences("PROFILE",MODE_PRIVATE).edit().putString("profileId",profileId).apply();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).addToBackStack(null).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        }

    }


}