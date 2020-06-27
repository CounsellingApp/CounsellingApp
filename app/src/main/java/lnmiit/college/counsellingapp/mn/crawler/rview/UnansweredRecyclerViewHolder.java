package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import lnmiit.college.counsellingapp.R;

public class UnansweredRecyclerViewHolder extends RecyclerView.ViewHolder {
    private LinearLayout clickablelinearlayout;
    private TextView txtunansweredatgs, txtunansweredauthor, txtunansweredquestion;
    private ImageButton btn_delete_response_icon;
    private Map<String,String> map=new HashMap<>();
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private CardView mainlayout;
    private TextView txtunanswerddescription;
    private TextView txt_unanswered_noa;

    public UnansweredRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        clickablelinearlayout = itemView.findViewById(R.id.clickablelinearlayout);
        txtunansweredatgs = itemView.findViewById(R.id.txtunansweredtags);
        txtunansweredauthor = itemView.findViewById(R.id.txtunansweredauthor);
        txtunansweredquestion = itemView.findViewById(R.id.txtunansweredquestion);
        btn_delete_response_icon = itemView.findViewById(R.id.btn_delete_response_icon);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        mainlayout = itemView.findViewById(R.id.mainlayout);
        txtunanswerddescription = itemView.findViewById(R.id.txtunanswereddescription);
        txt_unanswered_noa = itemView.findViewById(R.id.txt_unanswered_noa);
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

    public Map<String, String> getMap() {
        return map;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public TextView getTxt_unanswered_noa() {
        return txt_unanswered_noa;
    }

    public CardView getMainlayout() {
        return mainlayout;
    }

    public TextView getTxtunanswerddescription() {
        return txtunanswerddescription;
    }
}
