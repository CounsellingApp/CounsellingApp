package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lnmiit.college.counsellingapp.R;

public class Answers_adapter extends RecyclerView.Adapter<Answers_ViewHolder> {

    private int response_index;

    ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();

    public Answers_adapter(int response_index)
    {
        this.response_index = response_index;
    }

    public static String[] responses = {"I am doing just fine","I am building an application at the momment","I am having trouble deciding the layout of applications"
    ,"Serum, there's no doubt about that","No, Ronaldo is my personal favorite","Well, let's hope, fingers crossed"};

    public static  String[] responders = {"Dr. Amit Neogi","Dr. Ruchir Sodhani","Dr. Rahul Banerjee","Dr. Rahul Banerjee","Dr. Amit Neogi","Dr. Ruchir Sodhani"};
    public static int[] responderimages = {R.drawable.amitneogi,R.drawable.ruchirsodhani,R.drawable.rahulbanerjee,R.drawable.rahulbanerjee,R.drawable.amitneogi,R.drawable.ruchirsodhani};

    public void initialize_params()
    {
        for(int i=0;i<6;i++)
        {
            ArrayList<String> arr = new ArrayList<String>();
            arr.add(responses[i]);
            answers.add(arr);
        }
    }
    @NonNull
    @Override
    public Answers_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View view = lf.inflate(R.layout.answers_recycler_view,parent,false);

        return new Answers_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Answers_ViewHolder holder, int position) {

        holder.getTxtanswers().setText(answers.get(response_index).get(position));
        holder.getAnswers_facultyimage().setImageResource(responderimages[response_index]);
        holder.getAnswers_facultyname().setText(responders[response_index]);
    }


    @Override
    public int getItemCount() {
        initialize_params();
        return answers.get(response_index).size();
    }
}
