package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lnmiit.college.counsellingapp.R;

public class rviewadapter extends RecyclerView.Adapter<lnmiit.college.counsellingapp.mn.crawler.rview.viewholder> {

    String[] questions = {"How are you?","What you doin?","What' your problem?","What is the best VST that you have ever used?","Is Messi better than Ronaldo?","Can Real Madrid win the champions league with their current squad"};
    String[] authors = {"Lakshay","Anubhav","Anonymous","Mehak","Ishaan","Lavish"};
    String [] tags = {"General","General","General","Music Production","Football","Football"};
    ArrayList<String> answers = new ArrayList<String>();
    private boolean answers_visible = false;

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
        holder.getTxtquestion().setText(questions[position]);
        holder.getTxtauthor().setText("By- "+authors[position]);
        holder.getTxttags().setText(""+tags[position]);
//        answers.add("I am doing just fine");
////        answers.add("I am building an application at the momment");
////        answers.add("I am having trouble deciding the layout of applications");
////        answers.add("Serum, there's no doubt about that");
////        answers.add("No, Ronaldo is my personal favorite");
////        answers.add("Well, let's hope, fingers crossed");
        holder.getAnswers_rec_view().setAdapter(new Answers_adapter(position));
        holder.getAnswers_rec_view().setLayoutManager(new LinearLayoutManager(context));
        holder.getBtnview().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(),activity_answer.class);
//                intent.putExtra("solution",answers.get(position));
//                intent.putExtra("problem",questions[position]);
//                v.getContext().startActivity(intent);
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
        return questions.length;
    }
}
