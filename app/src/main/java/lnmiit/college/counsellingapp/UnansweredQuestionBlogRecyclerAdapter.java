package lnmiit.college.counsellingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.UnansweredQuestion;
import lnmiit.college.counsellingapp.UnansweredQuestionModel;

public class UnansweredQuestionBlogRecyclerAdapter extends RecyclerView.Adapter<UnansweredQuestionBlogRecyclerAdapter .ViewHolder> {
    List<UnansweredQuestionModel> list;
    public UnansweredQuestionBlogRecyclerAdapter (List<UnansweredQuestionModel> list){
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reclayout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.map=list.get(position).getFaculty_answers();
        if(holder.map.containsKey(holder.firebaseUser.getUid())){
            //do nothing;
        }
        else{
            // do everything
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Map<String,String>map=new HashMap<>();
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firebaseAuth=FirebaseAuth.getInstance();
            firebaseUser=firebaseAuth.getCurrentUser();
        }
    }
}
