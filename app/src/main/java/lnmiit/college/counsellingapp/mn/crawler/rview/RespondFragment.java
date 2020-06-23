package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.UnansweredQuestionModel;
import lnmiit.college.counsellingapp.Useremail;


public class RespondFragment extends Fragment implements UnansweredRecyclerViewAdapter.Onrefreshfragment {
    private RecyclerView unansweredrecyclerview;
    private ArrayList<UnansweredQuestionModel> list;
    private FirebaseFirestore ff;
    private UnansweredRecyclerViewAdapter myadapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_respond, container, false);
        unansweredrecyclerview = view.findViewById(R.id.unansweredrecview);
        list= new ArrayList<>();
        ff = FirebaseFirestore.getInstance();
        ff.collection("Questions").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e == null) {

                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        UnansweredQuestionModel ourWorkModel = documentChange.getDocument().toObject(UnansweredQuestionModel.class);
                        if(!ourWorkModel.getFaculty_answers().containsKey(Useremail.email)) {
                            list.add(ourWorkModel);
                        }
                        //myadapter.notifyDataSetChanged();
                        //Log.i("LISTSIZE",list.get(count).getQuestion()+"");
                        //count++;
                    }
                    myadapter.setList(list);

                }
                else{
                    Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                }
            }
        });
        myadapter = new UnansweredRecyclerViewAdapter(getActivity(),getChildFragmentManager(),RespondFragment.this);
        myadapter.setOnrefreshfragment(this);
        unansweredrecyclerview.setAdapter(myadapter);
        unansweredrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void refreshfragment() {
        FragmentTransaction ft = MainScreenViewPagerAdapter.fm.beginTransaction();
        ft.detach(RespondFragment.this);
        ft.attach(RespondFragment.this);
        ft.detach(mainFragment.mainfrag);
        ft.attach(mainFragment.mainfrag);
        ft.commit();
    }
}
