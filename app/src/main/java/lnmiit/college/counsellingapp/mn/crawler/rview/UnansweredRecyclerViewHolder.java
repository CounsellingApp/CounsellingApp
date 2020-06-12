package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lnmiit.college.counsellingapp.R;

public class UnansweredRecyclerViewHolder extends RecyclerView.ViewHolder {
    private LinearLayout clickablelinearlayout;
    private TextView txtunansweredatgs, txtunansweredauthor, txtunansweredquestion;
    private ImageButton btn_delete_response_icon;
    public UnansweredRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        clickablelinearlayout = itemView.findViewById(R.id.clickablelinearlayout);
        txtunansweredatgs = itemView.findViewById(R.id.txtunansweredtags);
        txtunansweredauthor = itemView.findViewById(R.id.txtunansweredauthor);
        txtunansweredquestion = itemView.findViewById(R.id.txtunansweredquestion);
        btn_delete_response_icon = itemView.findViewById(R.id.btn_delete_response_icon);
    }

    public LinearLayout getClickablelinearlayout() {
        return clickablelinearlayout;
    }

    public TextView getTxtunansweredatgs() {
        return txtunansweredatgs;
    }

    public TextView getTxtunansweredauthor() {
        return txtunansweredauthor;
    }

    public TextView getTxtunansweredquestion() {
        return txtunansweredquestion;
    }

    public ImageButton getBtn_delete_response_icon() {
        return btn_delete_response_icon;
    }
}
