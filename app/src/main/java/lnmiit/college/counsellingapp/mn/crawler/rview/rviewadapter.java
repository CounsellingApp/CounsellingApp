package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.UnansweredQuestionModel;

public class rviewadapter extends RecyclerView.Adapter<lnmiit.college.counsellingapp.mn.crawler.rview.viewholder> {

    String[] questions = {"How are you?","What you doin?","What' your problem?","What is the best VST that you have ever used?","Is Messi better than Ronaldo?","Can Real Madrid win the champions league with their current squad"};
    String[] authors = {"Lakshay","Anubhav","Anonymous","Mehak","Ishaan","Lavish"};
    String [] tags = {"General","General","General","Music Production","Football","Football"};
    ArrayList<String> answers = new ArrayList<String>();
    private boolean answers_visible = false;
    List<UnansweredQuestionModel> mainlist = new ArrayList<>();
    private Context context;
    public rviewadapter(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public lnmiit.college.counsellingapp.mn.crawler.rview.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View view = lf.inflate(R.layout.reclayout,parent,false);
        return new lnmiit.college.counsellingapp.mn.crawler.rview.viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final lnmiit.college.counsellingapp.mn.crawler.rview.viewholder holder, final int position) {
        holder.getTxtquestion().setText(mainlist.get(position).getQuestion());
        holder.getTxtauthor().setText("By- "+mainlist.get(position).getAsked_by());
        holder.getTxttags().setText(""+mainlist.get(position).getTag());
        Answers_adapter adapter = new Answers_adapter(position,context);
        Map<String,String> mymap = mainlist.get(position).getFaculty_answers();
        List<AnswerModel> answerslist = new ArrayList<>();
        for(Map.Entry<String,String> entry : mymap.entrySet() )
        {
            answerslist.add(new AnswerModel(entry.getKey(),entry.getValue(),entry.getKey()+".png"));
        }
        adapter.Setmainlist(answerslist);
        holder.getAnswers_rec_view().setAdapter(adapter);
        holder.getAnswers_rec_view().setLayoutManager(new LinearLayoutManager(context));
        holder.getBtnview().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(answers_visible) {
                    holder.getAnswers_rec_view().setVisibility(View.GONE);
                    answers_visible=false;
                    v.animate().rotationBy(-90).setDuration(50);
                }
                else {
                    holder.getAnswers_rec_view().setVisibility(View.VISIBLE);
                    answers_visible= true;
                    v.animate().rotationBy(90).setDuration(50);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mainlist.size();
    }

    public void Setmainlist(List<UnansweredQuestionModel> mainlist)
    {
        this.mainlist = mainlist;
        notifyDataSetChanged();
    }
}
