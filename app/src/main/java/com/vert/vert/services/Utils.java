package com.vert.vert.services;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;



public class Utils {

    private Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public static void setupViewPager(ViewPager viewPager
            , Fragment fragment1, Fragment fragment2
            , String str1, String str2, FragmentManager FragmentManager) {
        ViewPagerAdapter adapter = new Utils.ViewPagerAdapter(FragmentManager);


        adapter.addFragment(fragment1, str1);
        adapter.addFragment(fragment2, str2);


        viewPager.setAdapter(adapter);
    }


    public static void setupTabIcons(TabLayout tabLayout) {
        if (tabLayout != null) {
            tabLayout.getTabAt(0);
        }
        if (tabLayout != null) {
            tabLayout.getTabAt(1);
        }

    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
