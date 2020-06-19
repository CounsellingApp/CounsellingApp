package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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


public class RespondFragment extends Fragment {
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
                    int count=0;
                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        UnansweredQuestionModel ourWorkModel = documentChange.getDocument().toObject(UnansweredQuestionModel.class);
                        list.add(ourWorkModel);
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
        myadapter = new UnansweredRecyclerViewAdapter(getActivity(),getChildFragmentManager());
        unansweredrecyclerview.setAdapter(myadapter);
        unansweredrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
