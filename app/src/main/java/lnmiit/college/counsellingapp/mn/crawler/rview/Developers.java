package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lnmiit.college.counsellingapp.R;


public class Developers extends Fragment {
    private RecyclerView developer_recyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_developers, container, false);
        developer_recyclerview = view.findViewById(R.id.developer_recyclerview);
        Developers_Recycler_View adapter = new Developers_Recycler_View(view.getContext());
        developer_recyclerview.setAdapter(adapter);
        developer_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
