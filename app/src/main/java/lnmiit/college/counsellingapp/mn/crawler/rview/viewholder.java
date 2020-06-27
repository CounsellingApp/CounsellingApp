package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lnmiit.college.counsellingapp.R;

public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private TextView txtquestion;
    private TextView txtauthor;
    private TextView txttags;
    private TextView txt_description;
    private ImageButton btnview;
    private RecyclerView answers_rec_view;
    private TextView txtline;
    private List<AnswerModel> mylist;
    private TextView txt_noa;
    onNoteListener my_notelistener;
    public viewholder(View view)
    {
        super(view);
        txtquestion = view.findViewById(R.id.txtproblem);
        txtauthor = view.findViewById(R.id.txtauthor);
        txt_description = view.findViewById(R.id.txt_description);
        txttags = view.findViewById(R.id.txttags);
        btnview  = view.findViewById(R.id.btnview);
        txt_noa = view.findViewById(R.id.txt_noa);

        //txtline = view.findViewById(R.id.txtline);
    }
    public viewholder(View view, onNoteListener my_notelistener)
    {
        super(view);
        txtquestion = view.findViewById(R.id.txtproblem);
        txtauthor = view.findViewById(R.id.txtauthor);
        txt_description = view.findViewById(R.id.txt_description);
        txttags = view.findViewById(R.id.txttags);
        btnview  = view.findViewById(R.id.btnview);
        txt_noa = view.findViewById(R.id.txt_noa);
        view.setOnClickListener(this);
        btnview.setOnClickListener(this);
        txtquestion.setOnClickListener(this);
        this.my_notelistener = my_notelistener;
        //txtline = view.findViewById(R.id.txtline);
    }

    public TextView getTxtquestion() {
        return txtquestion;
    }
    public TextView getTxtline() {
        return txtline;
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

    public TextView getTxt_noa() {
        return txt_noa;
    }

    public TextView getTxt_description() {
        return txt_description;
    }

    public RecyclerView getAnswers_rec_view() {
        return answers_rec_view;
    }
    public interface onNoteListener{
        void onnoteclick(String question,String descrption,String author,String tags,List<AnswerModel> mylist);
    }

    @Override
    public void onClick(View v) {
        my_notelistener.onnoteclick(txtquestion.getText().toString()+"",txt_description.getText().toString()+"",txtauthor.getText().toString()+"",txttags.getText().toString()+"",mylist);
    }

    public List<AnswerModel> getMylist() {
        return mylist;
    }

    public void setMylist(List<AnswerModel> mylist) {
        this.mylist = mylist;
    }
}
