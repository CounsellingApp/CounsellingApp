package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import lnmiit.college.counsellingapp.R;
import me.jagar.chatvoiceplayerlibrary.VoicePlayerView;

public class Answers_ViewHolder extends RecyclerView.ViewHolder {
    private TextView answers_facultyname , answers_date,txtanswers;
    private CircleImageView answers_facultyimage;
    private VoicePlayerView answers_audio_player;
    private RelativeLayout delete_relaative_layout;
    private ImageButton delete_my_response;
    public Answers_ViewHolder(@NonNull View View) {

        super(View);

        answers_facultyname = View.findViewById(R.id.answers_facultyname);
        answers_facultyimage = View.findViewById(R.id.answers_facultyimage);
        txtanswers = View.findViewById(R.id.txt_answers);
        answers_audio_player = View.findViewById(R.id.answer_audio_player);
        delete_relaative_layout = View.findViewById(R.id.delete_relativelayout);
        delete_my_response = View.findViewById(R.id.delete_my_answer);
    }

    public RelativeLayout getDelete_relaative_layout() {
        return delete_relaative_layout;
    }

    public ImageButton getDelete_my_response() {
        return delete_my_response;
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

    public VoicePlayerView getAnswers_audio_player() {
        return answers_audio_player;
    }
}
