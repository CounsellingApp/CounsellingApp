package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.view.View;
import android.widget.ImageButton;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import lnmiit.college.counsellingapp.R;

public class faculty_viewholder extends RecyclerView.ViewHolder {

    private QuickContactBadge addtocontacts;
    private TextView facultyname;
    private CircleImageView circleImageView;
    public faculty_viewholder(@NonNull View itemView) {
        super(itemView);
        circleImageView = itemView.findViewById(R.id.facultyimage);
        facultyname = itemView.findViewById(R.id.txtfacultyname);
        addtocontacts = itemView.findViewById(R.id.btnaddtocontacts);
    }

    public QuickContactBadge getAddtocontacts() {
        return addtocontacts;
    }

    public TextView getFacultyname() {
        return facultyname;
    }

    public CircleImageView getCircleImageView() {
        return circleImageView;
    }
}
