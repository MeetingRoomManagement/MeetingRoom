package com.example.dell.meetingapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

//        setContentView(R.layout.fragment_home);
//        Button buttonAll = (Button) findViewById(R.id.btn_all);
//        buttonAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showChooseDialog();
//            }
//        });
    }

    private void showChooseDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

}
