package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lnmiit.college.counsellingapp.R;


public class RespondFragment extends Fragment {
    RecyclerView unansweredrecyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_respond, container, false);
        unansweredrecyclerview = view.findViewById(R.id.unansweredrecview);
        unansweredrecyclerview.setAdapter(new UnansweredRecyclerViewAdapter(getActivity(),getChildFragmentManager()));
        unansweredrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
