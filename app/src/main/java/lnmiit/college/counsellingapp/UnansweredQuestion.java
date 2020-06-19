package lnmiit.college.counsellingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class UnansweredQuestion extends Fragment {
    private RecyclerView blog_list_view;
    private List<UnansweredQuestionModel> list;
    private FirebaseFirestore ff;
    private UnansweredQuestionBlogRecyclerAdapter blogRecyclerAdapter;
    public UnansweredQuestion() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_unanswered_question, container, false);
        list=new ArrayList<>();
        blogRecyclerAdapter=new UnansweredQuestionBlogRecyclerAdapter(list);
        blog_list_view=view.findViewById(R.id.unansweredQuestionrecycler);
        blog_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        blog_list_view.setAdapter(blogRecyclerAdapter);

        ff.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange documentChange:queryDocumentSnapshots.getDocumentChanges()){
                    UnansweredQuestionModel ourWorkModel=documentChange.getDocument().toObject(UnansweredQuestionModel.class);
                    list.add(ourWorkModel);
                    blogRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }
}