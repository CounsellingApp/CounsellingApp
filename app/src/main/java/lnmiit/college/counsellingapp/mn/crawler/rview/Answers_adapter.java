package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lnmiit.college.counsellingapp.R;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Answers_adapter extends RecyclerView.Adapter<Answers_ViewHolder>  {

    private int response_index;
    private Context context;
    List<AnswerModel> mainlist = new ArrayList<>();
    private long downloadID=0;

    ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();


    public Answers_adapter(int response_index, Context context)
    {
        this.response_index = response_index;
        this.context = context;
    }

    @NonNull
    @Override
    public Answers_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());

        View view = lf.inflate(R.layout.answers_recycler_view,parent,false);

        return new Answers_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Answers_ViewHolder holder, final int position) {

        final String answerfromdatabase = mainlist.get(position).getAnswer_text();
        FirebaseStorage.getInstance().getReference().child("Audio_Answers").child(answerfromdatabase).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                holder.getAnswers_audio_player().setVisibility(View.VISIBLE);
                holder.getTxtanswers().setVisibility(View.GONE);
                context.registerReceiver(downloadcomplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                String filepath = "CWPH_LNMIIT/Answers";
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),filepath);
                if(!file.exists())
                {
                    file.mkdirs();
                }
                filepath= "CWPH_LNMIIT/Answers/"+answerfromdatabase;
                File audiofile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),filepath);
              if(!audiofile.exists())
                {
                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setDestinationInExternalPublicDir("CWPH_LNMIIT/Answers",answerfromdatabase);
                    //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    downloadID = downloadManager.enqueue(request);
                }
                holder.getAnswers_audio_player().setAudio(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+filepath);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                holder.getTxtanswers().setText(mainlist.get(position).getAnswer_text());
            }
        });

        FirebaseStorage.getInstance().getReference().child("faculty_images").child(mainlist.get(position).getFaculty_image_url()).getDownloadUrl().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                holder.getAnswers_facultyimage().setImageResource(R.drawable.personimage);
            }
        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.getAnswers_facultyimage());
            }
        });

        holder.getAnswers_facultyname().setText(mainlist.get(position).getFaculty_name());

    }


    @Override
    public int getItemCount() {
        return mainlist.size();
    }
    public void Setmainlist(List<AnswerModel> mainlist)
    {
        this.mainlist = mainlist;
        notifyDataSetChanged();
    }
    private BroadcastReceiver downloadcomplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            if(downloadID==id)
            {
                //Toast.makeText(context,"Download has been completed",Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
            }
        }
    };
}
