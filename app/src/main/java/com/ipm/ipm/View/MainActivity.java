package com.ipm.ipm.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.ipm.ipm.R;
import com.ipm.ipm.View.Fragment.IndustrialFragment.IndustrialFragment;
import com.ipm.ipm.View.Fragment.HomeFragment.HomeFragment;
import com.ipm.ipm.View.Fragment.UserFragment.UserFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        chipNavigationBar = findViewById(R.id.bottomNav);
        chipNavigationBar.setItemSelected(R.id.home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(i -> {
            Fragment fragment = null;
            switch (i) {
                case R.id.home:
                    fragment = new HomeFragment();
                    break;
                case R.id.industrial:
                    fragment = new IndustrialFragment();
                    break;
                case R.id.user:
                    fragment = new UserFragment();
                    break;
            }
            assert fragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        });
    }

}