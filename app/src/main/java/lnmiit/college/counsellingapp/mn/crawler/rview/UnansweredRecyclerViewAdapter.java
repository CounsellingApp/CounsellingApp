package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.UnansweredQuestionModel;

public class UnansweredRecyclerViewAdapter extends RecyclerView.Adapter<UnansweredRecyclerViewHolder> implements Delete_Response_Dialog.OnInputSelected {

    private Activity context;
    private FragmentManager fragmentManager;
    List<UnansweredQuestionModel> list = new ArrayList<>();
    private FirebaseFirestore ff;

    public UnansweredRecyclerViewAdapter(Activity context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
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
        if(holder.getMap().containsKey(holder.getFirebaseUser().getUid())){
            holder.getMainlayout().setVisibility(View.GONE);
        }
        else {
            holder.getTxtunansweredatgs().setText("Tags : ");
            holder.getTxtunansweredquestion().setText(list.get(position).getQuestion());
            holder.getTxtunansweredauthor().setText(list.get(position).getAsked_by());
            holder.getClickablelinearlayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You will be prompted to answer the question", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(v.getContext(), Respond_To_A_Question.class);
                    intent.putExtra("question", holder.getTxtunansweredquestion().getText().toString() + "");
                    intent.putExtra("author", holder.getTxtunansweredauthor().getText().toString() + "");
                    intent.putExtra("tags", holder.getTxtunansweredatgs().getText().toString() + "");
                    context.startActivity(intent);
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
            }
        }
    }

}
