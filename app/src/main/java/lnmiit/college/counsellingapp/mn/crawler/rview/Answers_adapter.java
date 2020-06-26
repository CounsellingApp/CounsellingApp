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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lnmiit.college.counsellingapp.R;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Answers_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private int response_index;
    private Context context;
    List<AnswerModel> mainlist = new ArrayList<>();
    private long downloadID=0;
    private String question;
    private String description;
    private String author;
    private String tags;
    ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();


    public Answers_adapter(int response_index, Context context, String question, String description, String author, String tags)
    {
        this.response_index = response_index;
        this.context = context;
        this.question = question;
        this.description = description;
        this.author = author;
        this.tags = tags;
    }
    public Answers_adapter(int response_index, Context context)
    {
        this.response_index = response_index;
        this.context = context;

    }
    @Override
    public int getItemViewType(int position) {
        if(position==0)
        {
            return 100;
        }
        else
        {
            return 200;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());

        if(viewType==100) {
            View view = lf.inflate(R.layout.reclayout, parent, false);
            return new viewholder(view);
        }
        else
        {
            View view = lf.inflate(R.layout.answers_recycler_view, parent, false);
            return new Answers_ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        if(getItemViewType(position)==100)
        {
            ((viewholder)holder).getTxtquestion().setText(question+"");
            ((viewholder)holder).getTxt_description().setText(description+"");
            ((viewholder)holder).getTxtauthor().setText(author+"");
            ((viewholder)holder).getTxttags().setText(tags);
            ((viewholder)holder).getBtnview().setVisibility(View.INVISIBLE);
        }
        else {
            final String answerfromdatabase = mainlist.get(position-1).getAnswer_text();
            FirebaseStorage.getInstance().getReference().child("Audio_Answers").child(answerfromdatabase).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    ((Answers_ViewHolder)holder).getAnswers_audio_player().setVisibility(View.VISIBLE);
                    ((Answers_ViewHolder)holder).getTxtanswers().setVisibility(View.GONE);
                    context.registerReceiver(downloadcomplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                    String filepath = "CWPH_LNMIIT/Answers";
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filepath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    filepath = "CWPH_LNMIIT/Answers/" + answerfromdatabase;
                    File audiofile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filepath);
                    if (!audiofile.exists()) {
                        DownloadManager downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setDestinationInExternalPublicDir("CWPH_LNMIIT/Answers", answerfromdatabase);
                        //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        downloadID = downloadManager.enqueue(request);
                    }
                    ((Answers_ViewHolder)holder).getAnswers_audio_player().setAudio(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + filepath);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    ((Answers_ViewHolder)holder).getTxtanswers().setText(mainlist.get(position-1).getAnswer_text());
                }
            });
            FirebaseFirestore.getInstance().collection("Faculty_Bag").document(mainlist.get(position-1).getFaculty_name()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        DocumentSnapshot dsnap = task.getResult();
                        if(dsnap.exists())
                        {
                            ((Answers_ViewHolder)holder).getAnswers_facultyname().setText((dsnap.get("username")).toString()+"");
                            Uri uri = Uri.parse(dsnap.get("photo_uri").toString());
                            Picasso.get().load(uri).into(((Answers_ViewHolder)holder).getAnswers_facultyimage());
                        }
                        else
                        {
                            ((Answers_ViewHolder)holder).getAnswers_facultyname().setText(mainlist.get(position-1).getFaculty_name());
                            ((Answers_ViewHolder)holder).getAnswers_facultyimage().setImageResource(R.drawable.personimage);
                        }
                    }
                }
            });

//            FirebaseStorage.getInstance().getReference().child("faculty_images").child(mainlist.get(position-1).getFaculty_image_url()).getDownloadUrl().addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    ((Answers_ViewHolder)holder).getAnswers_facultyimage().setImageResource(R.drawable.personimage);
//                }
//            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    Picasso.get().load(uri).into(((Answers_ViewHolder)holder).getAnswers_facultyimage());
//                }
//            });


        }
    }


    @Override
    public int getItemCount() {
        return mainlist.size()+1;
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
