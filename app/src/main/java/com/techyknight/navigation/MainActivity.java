package com.techyknight.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.techyknight.R;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnView=findViewById(R.id.Bottom_Nav_View);

        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.nav_home){
                    loadFrag(new HomeFragment(),true);
                }else if(id==R.id.nav_activity){
                    loadFrag(new ActivityFragment(),false);
                }else if(id==R.id.nav_about_us){
                    loadFrag(new About_UsFragment(),false);
                }else if(id==R.id.nav_contact){
                    loadFrag(new ContactFragment(),false);
                }else {
                    loadFrag(new ProfileFragment(),false);
                }
                return true;
            }
        });
        bnView.setSelectedItemId(R.id.nav_home);

    }
    public void loadFrag(Fragment fragment, boolean flag){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(flag)
            ft.add(R.id.frame_layout,fragment);
        else
            ft.replace(R.id.frame_layout,fragment);
        ft.commit();
    }
}