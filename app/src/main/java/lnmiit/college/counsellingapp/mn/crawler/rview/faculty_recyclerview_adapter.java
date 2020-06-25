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


    @NonNull
    @Override
    public faculty_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.facultyrecview,parent,false);
        return new faculty_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final faculty_viewholder holder, final int position) {
        switch (position)
        {
            case 0:
                holder.getFac_name().setText("Dr. Amit Neogi");
                holder.getFac_des().setText("Head");
                break;
            case 1:
                holder.getFac_name().setText("Prof. AP Singh");
                holder.getFac_des().setText("SAC Member");
                break;
            case 2:
                holder.getFac_name().setText("Mrs. Arshita Nair");
                holder.getFac_des().setText("Student Councellor");
                break;
            case 3:
                holder.getFac_name().setText("Dr. Ajit Patel");
                holder.getFac_des().setText("Cheif Warden");
                break;
        }


    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

