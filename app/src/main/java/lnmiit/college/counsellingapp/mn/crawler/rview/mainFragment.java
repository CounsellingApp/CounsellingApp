package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.UnansweredQuestionModel;

public class mainFragment extends Fragment {
    RecyclerView recview;
    private List<UnansweredQuestionModel> mainlist;
    private FirebaseFirestore ff;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mainfragment,container,false);
        final rviewadapter adapter = new lnmiit.college.counsellingapp.mn.crawler.rview.rviewadapter(getActivity());
        mainlist = new ArrayList<>();
        ff = FirebaseFirestore.getInstance();
        ff.collection("Questions").whereEqualTo("isanswered",true).orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e==null)
                {
                    for(DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){
                        UnansweredQuestionModel ourWorkModel = documentChange.getDocument().toObject(UnansweredQuestionModel.class);
                        mainlist.add(ourWorkModel);
                    }
                    adapter.Setmainlist(mainlist);
                }
                else
                {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.i("errortag",e.getMessage());
                }
            }
        });
        recview = v.findViewById(R.id.recview);
        recview.setAdapter(adapter);
        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }
}
