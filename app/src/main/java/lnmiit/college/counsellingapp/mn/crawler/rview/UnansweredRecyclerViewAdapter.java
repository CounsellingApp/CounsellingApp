package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.UnansweredQuestionModel;
import lnmiit.college.counsellingapp.Useremail;

public class UnansweredRecyclerViewAdapter extends RecyclerView.Adapter<UnansweredRecyclerViewHolder> implements Delete_Response_Dialog.OnInputSelected {

    public interface Onrefreshfragment{
        void refreshfragment();
    }
    Onrefreshfragment onrefreshfragment;
    public void setOnrefreshfragment(Onrefreshfragment onrefreshfragment){
        this.onrefreshfragment = onrefreshfragment;
    }
    private Activity context;
    private FragmentManager fragmentManager;
    private Fragment currentfragment;
    List<UnansweredQuestionModel> list = new ArrayList<>();
    private FirebaseFirestore ff;

    public UnansweredRecyclerViewAdapter(Activity context, FragmentManager fragmentManager, Fragment currentfragment) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.currentfragment = currentfragment;
        ff = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public UnansweredRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        View view = inflator.inflate(R.layout.unanswered_recyclerview_item,parent,false);
        return new UnansweredRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UnansweredRecyclerViewHolder holder, final int position) {
        holder.setMap(list.get(position).getFaculty_answers());
        holder.getTxt_unanswered_noa().setText(holder.getMap().size()+" answer(s)");
        //Showfancytoasr.show(context,"This question has been answered "+holder.getMap().size()+" times");
            holder.getTxtunansweredatgs().setText("Tags : "+list.get(position).getTag());
            holder.getTxtunansweredquestion().setText(list.get(position).getQuestion_title());
            holder.getTxtunanswerddescription().setText(list.get(position).getQuestion());
            holder.getTxtunansweredauthor().setText("By - "+list.get(position).getAsked_by());
            View.OnClickListener onc = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Respond_To_A_Question.class);
                    intent.putExtra("question", holder.getTxtunansweredquestion().getText().toString() + "");
                    intent.putExtra("author", holder.getTxtunansweredauthor().getText().toString() + "");
                    intent.putExtra("tags", holder.getTxtunansweredatgs().getText().toString() + "");
                    intent.putExtra("questionid",list.get(position).getQuestion_id());
                    intent.putExtra("faculty_answers", (Serializable) holder.getMap());
                    intent.putExtra("description",holder.getTxtunanswerddescription().getText().toString()+"");
                    context.startActivity(intent);
                    context.finish();
                }
            };
            holder.getTxtunansweredquestion().setOnClickListener(onc);
            holder.getClickablelinearlayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), Respond_To_A_Question.class);
                    intent.putExtra("question", holder.getTxtunansweredquestion().getText().toString() + "");
                    intent.putExtra("author", holder.getTxtunansweredauthor().getText().toString() + "");
                    intent.putExtra("tags", holder.getTxtunansweredatgs().getText().toString() + "");
                    intent.putExtra("questionid",list.get(position).getQuestion_id());
                    intent.putExtra("faculty_answers", (Serializable) holder.getMap());
                    intent.putExtra("description",holder.getTxtunanswerddescription().getText().toString()+"");
                    context.startActivity(intent);
                    context.finish();
                }
            });
            holder.getBtn_delete_response_icon().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Delete_Response_Dialog delete_response_dialog = new Delete_Response_Dialog();
                delete_response_dialog.setOnInputSelected(UnansweredRecyclerViewAdapter.this);
                Bundle bundle = new Bundle();
                bundle.putString("did",list.get(holder.getAdapterPosition()).getQuestion_id());
                bundle.putInt("position",holder.getAdapterPosition());
                delete_response_dialog.setArguments(bundle);
                delete_response_dialog.show(fragmentManager,"tag");
                }
            });


    }

    @Override
    public int getItemCount() {

        return list.size();
    }
    public void setList(List<UnansweredQuestionModel> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void sendInput(boolean wasquestiondeleted, int position) {
        if(wasquestiondeleted)
        {
            if(list.size()>-1) {
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
                list.remove(list.size()-1);
                notifyItemRemoved(list.size()-1);
                onrefreshfragment.refreshfragment();
            }
        }
    }

}
