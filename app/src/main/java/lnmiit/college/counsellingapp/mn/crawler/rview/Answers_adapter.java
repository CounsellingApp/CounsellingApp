package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lnmiit.college.counsellingapp.R;

public class Answers_adapter extends RecyclerView.Adapter<Answers_ViewHolder> {

    private int response_index;
    List<AnswerModel> mainlist = new ArrayList<>();

    ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();

    public Answers_adapter(int response_index)
    {
        this.response_index = response_index;
    }

    @NonNull
    @Override
    public Answers_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View view = lf.inflate(R.layout.answers_recycler_view,parent,false);

        return new Answers_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Answers_ViewHolder holder, int position) {

        holder.getTxtanswers().setText(mainlist.get(position).getAnswer_text());
        FirebaseStorage.getInstance().getReference().child("faculty_images").child(mainlist.get(position).getFaculty_image_url()).getDownloadUrl().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                holder.getAnswers_facultyimage().setImageResource(R.drawable.personimage);
            }
        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.getAnswers_facultyimage());
            }
        });

        holder.getAnswers_facultyname().setText(mainlist.get(position).getFaculty_name());
    }


    @Override
    public int getItemCount() {
        return mainlist.size();
    }
    public void Setmainlist(List<AnswerModel> mainlist)
    {
        this.mainlist = mainlist;
        notifyDataSetChanged();
    }
}
