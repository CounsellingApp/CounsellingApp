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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lnmiit.college.counsellingapp.R;

public class about_fragment extends Fragment {

    RecyclerView facultyrecyclerview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment,container,false);
        facultyrecyclerview = view.findViewById(R.id.facultyrecyclerview);
        facultyrecyclerview.setAdapter(new faculty_recyclerview_adapter());
        facultyrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.askquestion);
        item.setEnabled(false);
        item.setVisible(false);
        item = menu.findItem(R.id.filters);
        item.setEnabled(false);
        item.setVisible(false);
    }
}
