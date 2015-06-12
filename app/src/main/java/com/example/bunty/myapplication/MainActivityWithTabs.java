package com.example.bunty.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.example.bunty.myapplication.Fragments.Fragmentbusno;
import com.example.bunty.myapplication.Fragments.Fragmentpath;
import com.example.bunty.myapplication.Fragments.Fragmentsrcdest;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by bunty on 3/24/2015.
 */
public class MainActivityWithTabs extends ActionBarActivity implements MaterialTabListener{

    private MaterialTabHost tabHost;
    private ViewPager viewPager;
    public static final int fragment_bus_no=0;
    public static final int fragment_bus_src_dest=1;
    public static final int fragment_bus_path=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabs);
        tabHost= (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }


    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }


   private class ViewPagerAdapter extends FragmentStatePagerAdapter
   {

       public ViewPagerAdapter(FragmentManager fm) {
           super(fm);
       }

       @Override
       public Fragment getItem(int position) {
           Fragment fragment=null;

           switch (position)
           {
               case fragment_bus_no:
                   fragment= Fragmentbusno.newInstance("","");
                   break;

               case fragment_bus_src_dest:
                   fragment= Fragmentsrcdest.newInstance("","");
                   break;

               case fragment_bus_path:
                   fragment= Fragmentpath.newInstance("","");
                   break;


           }
           return fragment;
       }

       @Override
       public int getCount() {
           return 3;
       }

       @Override
       public CharSequence getPageTitle(int position) {
           return getResources().getStringArray(R.array.tabs)[position];
       }
   }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.refresh:
//                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
//
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
