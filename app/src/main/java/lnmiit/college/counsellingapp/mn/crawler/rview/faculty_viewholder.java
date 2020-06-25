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

    private TextView fac_name, fac_des;
    private ImageButton fac_phone, fac_email;
    public faculty_viewholder(@NonNull View itemView) {
        super(itemView);
        fac_des = itemView.findViewById(R.id.faculty_designation);
        fac_name = itemView.findViewById(R.id.faculty_name);
        fac_email = itemView.findViewById(R.id.faculty_emailbutton);
        fac_phone = itemView.findViewById(R.id.faculty_phonebutton);
    }

    public TextView getFac_name() {
        return fac_name;
    }

    public TextView getFac_des() {
        return fac_des;
    }

    public ImageButton getFac_phone() {
        return fac_phone;
    }

    public ImageButton getFac_email() {
        return fac_email;
    }
}
