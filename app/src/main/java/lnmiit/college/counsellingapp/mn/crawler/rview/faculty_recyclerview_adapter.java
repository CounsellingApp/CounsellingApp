package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lnmiit.college.counsellingapp.R;

public class faculty_recyclerview_adapter extends RecyclerView.Adapter<faculty_viewholder> {

    String[] facultynames = {"Dr. Amit Neogi","Dr. Ruchir Sodhani","Dr. Rahul Banerjee"};
    String[] facultynmubers = {"8963807110","9468586840","9799225550"};
    int [] images = {R.drawable.amitneogi,R.drawable.ruchirsodhani, R.drawable.rahulbanerjee};
    @NonNull
    @Override
    public faculty_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.facultyrecview,parent,false);
        return new faculty_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final faculty_viewholder holder, final int position) {
        holder.getCircleImageView().setImageResource(images[position]);
        holder.getFacultyname().setText(facultynames[position]);

        holder.getAddtocontacts().assignContactFromPhone(facultynmubers[position],true);


    }

    @Override
    public int getItemCount() {
        return facultynames.length;
    }
}

