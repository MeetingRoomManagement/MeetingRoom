package com.example.dell.meetingapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private HomeFragment homeFragment;
    private DashboardFragment dashboardFragment;
    private NotificationsFragment notificationsFragment;
    FragmentManager fm;
    FragmentTransaction ft;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ft = fm.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (homeFragment.isAdded()) {
                        ft.show(homeFragment);
                    } else {
                        ft.replace(R.id.frame_layout, homeFragment);
                    }
                    ft.commitAllowingStateLoss();
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    if (dashboardFragment.isAdded()) {
                        ft.show( dashboardFragment);
                    } else {
                        ft.replace(R.id.frame_layout, dashboardFragment);
                    }
                    ft.commitAllowingStateLoss();
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    if (notificationsFragment.isAdded()) {
                        ft.show( notificationsFragment);
                    } else {
                        ft.replace(R.id.frame_layout, notificationsFragment);
                    }
                    ft.commitAllowingStateLoss();
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        homeFragment = HomeFragment.newInstance();
        dashboardFragment = DashboardFragment.newInstance();
        notificationsFragment = NotificationsFragment.newInstance();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, homeFragment);
        ft.commit();

    }

}
