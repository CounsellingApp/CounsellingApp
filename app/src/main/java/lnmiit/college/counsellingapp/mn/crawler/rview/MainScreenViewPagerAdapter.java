package lnmiit.college.counsellingapp.mn.crawler.rview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainScreenViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitleList = new ArrayList<>();
    private int numberoftabs;
    public static FragmentManager fm;
    public MainScreenViewPagerAdapter(@NonNull FragmentManager fm, int behavior,int numberoftabs) {
        super(fm,behavior);
        this.numberoftabs = numberoftabs;
        this.fm = fm;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
            return mFragmentList.get(position);
//        switch (position)
//        {
//            case 0: return new mainFragment();
//            case 1 : return new RespondFragment();
//            default: return null;
//        }

    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
