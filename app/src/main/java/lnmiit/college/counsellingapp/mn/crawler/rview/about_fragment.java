package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import lnmiit.college.counsellingapp.R;

public class about_fragment extends Fragment {

    private RecyclerView facultyrecyclerview;
    private SwipeRefreshLayout swipetorefresh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.about_fragment,container,false);
//        facultyrecyclerview = view.findViewById(R.id.facultyrecyclerview);
        swipetorefresh = view.findViewById(R.id.swipetorefresh);
        swipetorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FragmentTransaction ft = MainActivity.mainfm.beginTransaction();
                ft.detach(about_fragment.this);
                ft.attach(about_fragment.this);
                swipetorefresh.setRefreshing(false);
            }
        });
//        facultyrecyclerview.setAdapter(new faculty_recyclerview_adapter());
//        facultyrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);
        return view;
    }


}
