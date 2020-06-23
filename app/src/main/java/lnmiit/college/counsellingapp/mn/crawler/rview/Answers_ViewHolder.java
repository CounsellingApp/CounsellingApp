package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import lnmiit.college.counsellingapp.R;

public class Answers_ViewHolder extends RecyclerView.ViewHolder {
    private TextView answers_facultyname , answers_date,txtanswers;
    private CircleImageView answers_facultyimage;
    public Answers_ViewHolder(@NonNull View View) {

        super(View);

        answers_facultyname = View.findViewById(R.id.answers_facultyname);
        answers_facultyimage = View.findViewById(R.id.answers_facultyimage);
        txtanswers = View.findViewById(R.id.txt_answers);
    }

    public TextView getAnswers_facultyname() {
        return answers_facultyname;
    }

    public TextView getAnswers_date() {
        return answers_date;
    }

    public CircleImageView getAnswers_facultyimage() {
        return answers_facultyimage;
    }

    public TextView getTxtanswers() {
        return txtanswers;
    }
}
