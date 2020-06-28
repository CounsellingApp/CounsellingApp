package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lnmiit.college.counsellingapp.R;
import lnmiit.college.counsellingapp.Useremail;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Answers_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Custom_Dialog.Ondialogaction  {

    private int response_index;
    private Context context;
    List<AnswerModel> mainlist = new ArrayList<>();
    private long downloadID=0;
    private String question;
    private String description;
    private String author;
    private String tags;
    private String noa;
    private Map<String,String> answers_map;
    private String question_id;
    private FragmentManager fm;
    ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();


    public Answers_adapter(int response_index, Context context, String question, String description, String author, String tags, String noa, FragmentManager fm, String question_id, Map<String,String> answers_map)
    {
        this.response_index = response_index;
        this.fm = fm;
        this.context = context;
        this.question = question;
        this.description = description;
        this.author = author;
        this.tags = tags;
        this.noa = noa;
        this.question_id = question_id;
        this.answers_map = answers_map;
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
            ((viewholder)holder).getTxt_noa().setText(noa);
            if(!answers_map.containsKey(Useremail.email)) {
                ((viewholder) holder).getAnswer_the_question().setVisibility(View.VISIBLE);
                ((viewholder) holder).getAnswer_the_question().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,Respond_To_A_Question.class);
                        intent.putExtra("question", question);
                        intent.putExtra("author", author);
                        intent.putExtra("tags", tags);
                        intent.putExtra("questionid",question_id);
                        intent.putExtra("faculty_answers", (Serializable) answers_map);
                        intent.putExtra("description",description);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }
                });
            }
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
                            if(Useremail.username.equals(dsnap.get("username").toString()))
                            {
                                ((Answers_ViewHolder) holder).getDelete_relaative_layout().setVisibility(View.VISIBLE);
                                ((Answers_ViewHolder) holder).getDelete_my_response().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Custom_Dialog custom_dialog = new Custom_Dialog();
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("code",40);
                                        custom_dialog.setArguments(bundle);
                                        custom_dialog.setMondialogaction(Answers_adapter.this);
                                        custom_dialog.show(fm,"delete my answer");
                                    }
                                });
                            }
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

    @Override
    public void mainactivity() {
        final ProgressDialog pd = ProgressDialog.show(context,"","Please wait");

        answers_map.remove(Useremail.email);
        FirebaseFirestore.getInstance().collection("Questions").document(question_id).update("faculty_answers",answers_map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"CWPH_LNMIIT/Answers/"+Useremail.email+question_id+".mp4");
                if(file.exists()) {
                    FirebaseStorage.getInstance().getReference().child("Audio_Answers").child(Useremail.email + question_id + ".mp4").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            file.delete();
                            Showfancytoasr.show(context, "Response successfully deleted.");
                            context.startActivity(new Intent(context, MainActivity.class));
                            pd.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Showfancytoasr.show(context, "Failed to delete from external directory + " + e.getMessage());
                            context.startActivity(new Intent(context, MainActivity.class));
                            pd.dismiss();
                        }
                    });
                }
                else {
                    Showfancytoasr.show(context, "Response successfully deleted.");
                    context.startActivity(new Intent(context, MainActivity.class));
                    pd.dismiss();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Showfancytoasr.show(context,"Failed to delete + "+e.getMessage());
                pd.dismiss();
            }
        });

    }
}
