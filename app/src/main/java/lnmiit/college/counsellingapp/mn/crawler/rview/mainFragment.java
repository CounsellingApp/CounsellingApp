package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.UnansweredQuestionModel;
import lnmiit.college.counsellingapp.Useremail;

public class mainFragment extends Fragment implements viewholder.onNoteListener {
    RecyclerView recview;
    private List<UnansweredQuestionModel> mainlist;
    private FirebaseFirestore ff;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static Fragment mainfrag;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mainfragment,container,false);
        final rviewadapter adapter = new lnmiit.college.counsellingapp.mn.crawler.rview.rviewadapter(getActivity(),this);
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
                    Showfancytoasr.show(getContext(),"Error updating data, please try again");
                    Log.i("errortag",e.getMessage());
                }
            }
        });
        swipeRefreshLayout = v.findViewById(R.id.mainfragmentswipetorefresh);
        //final FragmentManager nowfm = MainScreenViewPagerAdapter.fm;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FragmentTransaction nowft;
                if(Useremail.isfaculty) {
                     nowft = MainScreenViewPagerAdapter.fm.beginTransaction();
                }
                else
                {
                     nowft = MainActivity.mainfm.beginTransaction();
                }
                nowft.detach(mainFragment.this);
                nowft.attach(mainFragment.this);
                nowft.commit();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recview = v.findViewById(R.id.recview);
        recview.setAdapter(adapter);
        fab = v.findViewById(R.id.fab);
        if(Useremail.isfaculty)
        {
            fab.setVisibility(View.GONE);

        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ask_a_question_dialog ask = new ask_a_question_dialog();
                ask.show(getChildFragmentManager(),"ask");
            }
        });
        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainfrag = mainFragment.this;
        return v;
    }

    @Override
    public void onnoteclick(String question, String description, String author, String tags, List<AnswerModel> mylist, String noa, Map<String,String> answers_map, String id) {
        Intent intent = new Intent(getContext(),activity_answer.class);
        intent.putExtra("question",question);
        intent.putExtra("description",description);
        intent.putExtra("author",author);
        intent.putExtra("tags",tags);
        intent.putExtra("mylist", (Serializable) mylist);
        intent.putExtra("noa",noa);
        intent.putExtra("id",id);
        intent.putExtra("map",(Serializable) answers_map);
        startActivity(intent);
        getActivity().finish();
    }
}
