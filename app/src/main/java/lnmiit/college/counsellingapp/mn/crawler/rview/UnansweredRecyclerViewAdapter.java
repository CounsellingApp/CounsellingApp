package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import lnmiit.college.counsellingapp.R;

public class UnansweredRecyclerViewAdapter extends RecyclerView.Adapter<UnansweredRecyclerViewHolder> {

    private Activity context;
    private FragmentManager fragmentManager;
    private String[] unanswerequestions = {"When will the coronavirus vaccine be developed and commercialized?"
                                            ,"Do you think Zidane should sign Haaland instead of Mbappe?","How to increase CGPA without studying at all?"};
    private String[] unansweredauthors = {"Anonymouys","Lakshay","Ishaan"};
    private String[] unansweredtags = {"Health","Football","Academix"};

    public UnansweredRecyclerViewAdapter(Activity context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public UnansweredRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        View view = inflator.inflate(R.layout.unanswered_recyclerview_item,parent,false);
        return new UnansweredRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UnansweredRecyclerViewHolder holder, int position) {
        holder.getTxtunansweredatgs().setText("Tags : "+unansweredtags[position]);
        holder.getTxtunansweredquestion().setText(unanswerequestions[position]);
        holder.getTxtunansweredauthor().setText(unansweredauthors[position]);
        holder.getClickablelinearlayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"You will be prompted to answer the question",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(),Respond_To_A_Question.class);
                intent.putExtra("question",holder.getTxtunansweredquestion().getText().toString()+"");
                intent.putExtra("author",holder.getTxtunansweredauthor().getText().toString()+"");
                intent.putExtra("tags",holder.getTxtunansweredatgs().getText().toString()+"");
                context.startActivity(intent);
            }
        });
        holder.getBtn_delete_response_icon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Delete_Response_Dialog delete_response_dialog = new Delete_Response_Dialog();
                    delete_response_dialog.show(fragmentManager,"del");
            }
        });
    }

    @Override
    public int getItemCount() {

        return unanswerequestions.length;
    }
}
