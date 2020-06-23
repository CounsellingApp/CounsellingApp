package lnmiit.college.counsellingapp.mn.crawler.rview;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainScreenViewPagerAdapter extends FragmentPagerAdapter {
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
        switch (position)
        {
            case 0: return new mainFragment();
            case 1 : return new RespondFragment();
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return numberoftabs;
    }
}
