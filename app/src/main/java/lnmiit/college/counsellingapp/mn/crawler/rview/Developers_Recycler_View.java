package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import lnmiit.college.counsellingapp.R;

public class Developers_Recycler_View extends RecyclerView.Adapter<Developers_Recycler_View.Developers_Viewholder> {

    private Context context;
    public Developers_Recycler_View(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Developers_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.developers_item,parent,false);
        return new Developers_Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Developers_Viewholder holder, int position) {

        switch (position)
            {
                case 0 :
                    holder.getDeveloper_name().setText("Lakshay Bhagtani");
                    holder.getDeveloper_desc().setText("Android Developer | Competitive Programmer");
                    holder.getDeveloper_image().setImageResource(R.drawable.lakshay_bhagtani);
                    final String link1 = "https://www.linkedin.com/in/lakshay-bhagtani-08971318a/";
                    final String email1 = "lavi@weraveyou.com";
                    holder.getDeveloper_mail().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email1});
                            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        }
                    });
                    holder.getLinkedin().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link1));
                            context.startActivity(browserIntent);
                        }
                    });
                    break;
                case 1 :
                    holder.getDeveloper_name().setText("Govind Rathi");
                    holder.getDeveloper_desc().setText("Visual Designer");
                    holder.getDeveloper_image().setImageResource(R.drawable.govind_rathi);
                    final String link2="https://www.linkedin.com/in/govindrathi99/";
                    final String email2 = "govindr.designs@gmail.com";
                    holder.getDeveloper_mail().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email2});
                            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        }
                    });
                    holder.getLinkedin().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link2));
                            context.startActivity(browserIntent);
                        }
                    });
                    break;
                case 2 :
                    holder.getDeveloper_name().setText("Anubhav Goyal");
                    holder.getDeveloper_desc().setText("Full Stack Developer");
                    holder.getDeveloper_image().setImageResource(R.drawable.anubhav_goyal);
                    final String link3 ="https://www.linkedin.com/in/anubhav-goyal-921187195/";
                    final String email3 = "wewillrecreateindia@gmail.com";
                    holder.getDeveloper_mail().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email3});
                            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        }
                    });
                    holder.getLinkedin().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link3));
                            context.startActivity(browserIntent);
                        }
                    });
                    break;
            }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
    public class Developers_Viewholder extends RecyclerView.ViewHolder{
        private CircleImageView developer_image;
        private TextView developer_name, developer_desc;
        private ImageButton linkedin, developer_mail;

        public CircleImageView getDeveloper_image() {
            return developer_image;
        }

        public TextView getDeveloper_name() {
            return developer_name;
        }

        public TextView getDeveloper_desc() {
            return developer_desc;
        }

        public ImageButton getLinkedin() {
            return linkedin;
        }

        public ImageButton getDeveloper_mail() {
            return developer_mail;
        }

        public Developers_Viewholder(@NonNull View itemView){
            super(itemView);
            developer_image = itemView.findViewById(R.id.developer_image);
            developer_name = itemView.findViewById(R.id.developer_name);
            developer_desc = itemView.findViewById(R.id.developer_desc);
            linkedin = itemView.findViewById(R.id.linkedin);
            developer_mail = itemView.findViewById(R.id.developer_mail);

        }
    }
}
