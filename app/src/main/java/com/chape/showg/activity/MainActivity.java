package com.chape.showg.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.chape.showg.Fragment.MgFragment;
import com.chape.showg.Fragment.PictureFragment;
import com.chape.showg.Fragment.VideoFragment;
import com.chape.showg.R;
import com.chape.showg.base.Picture;
import com.chape.showg.util.HttpUtil;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener
{
    private BottomNavigationBar bottomNavigationBar;
    private List<Fragment> fragments;
    private PictureFragment pictureFragment;
    private VideoFragment videoFragment;
    private MgFragment mgFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       pictureFragment=new PictureFragment();
        videoFragment=new VideoFragment();
        mgFragment=new MgFragment();
        initBottomNavigationBar();

        setDefaultFragment();




    }






    private void initBottomNavigationBar(){
        bottomNavigationBar=(BottomNavigationBar)findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.clearAll();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);

        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.picture,"图片"))
                              .addItem(new BottomNavigationItem(R.drawable.video,"视频"))
                            .addItem(new BottomNavigationItem(R.drawable.mg,"消息"))
                .initialise();


    }

    private void setDefaultFragment(){
    getSupportFragmentManager().beginTransaction().replace(R.id.mainfragment,pictureFragment).commit();
    }
    @Override
    public void onTabSelected(int position) {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        switch (position){
            case 0:

             ft.replace(R.id.mainfragment,pictureFragment).commit();
              //  ft.addToBackStack(null);
                break;
            case 1:
                ft.replace(R.id.mainfragment,videoFragment).commit();
                break;
            case 2:
                ft.replace(R.id.mainfragment,mgFragment).commit();
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


}
