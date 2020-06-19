package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import lnmiit.college.counsellingapp.R;


public class MainScreenViewPager extends Fragment {


    TabLayout tabLayout;
    ViewPager mainviewpager;
    TabItem feed;
    TabItem respond;
    MainScreenViewPagerAdapter mainScreenViewPagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen_view_pager, container, false);
        tabLayout = view.findViewById(R.id.tablayout);

        mainviewpager = view.findViewById(R.id.mainviewpager);
        feed = view.findViewById(R.id.feed);
        respond = view.findViewById(R.id.respond);
        mainScreenViewPagerAdapter = new MainScreenViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,tabLayout.getTabCount());
        mainviewpager.setAdapter(mainScreenViewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainviewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mainviewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return view;
    }
}
