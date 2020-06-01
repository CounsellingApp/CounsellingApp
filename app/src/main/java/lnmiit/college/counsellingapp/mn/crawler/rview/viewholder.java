package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import lnmiit.college.counsellingapp.R;

public class viewholder extends RecyclerView.ViewHolder
{
    private TextView txtquestion;
    private TextView txtauthor;
    private TextView txttags;
    private ImageButton btnview;
    private RecyclerView answers_rec_view;
    public viewholder(View view)
    {
        super(view);
        txtquestion = view.findViewById(R.id.txtproblem);
        txtauthor = view.findViewById(R.id.txtauthor);
        txttags = view.findViewById(R.id.txttags);
        btnview  = view.findViewById(R.id.btnview);
        answers_rec_view = view.findViewById(R.id.answers_rec_view);
    }

    public TextView getTxtquestion() {
        return txtquestion;
    }

    public TextView getTxtauthor() {
        return txtauthor;
    }

    public TextView getTxttags() {
        return txttags;
    }
    public ImageButton getBtnview() {
        return btnview;
    }

    public RecyclerView getAnswers_rec_view() {
        return answers_rec_view;
    }
}
