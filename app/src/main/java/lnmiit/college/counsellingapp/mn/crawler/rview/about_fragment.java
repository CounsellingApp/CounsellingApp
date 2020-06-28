package lnmiit.college.counsellingapp.mn.crawler.rview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import lnmiit.college.counsellingapp.R;

public class about_fragment extends Fragment implements View.OnClickListener {

    private RecyclerView facultyrecyclerview;
    private SwipeRefreshLayout swipetorefresh;
    private ImageButton amitneogimail, amitneogiphone, apsinghmail, apsinghphone, arshitanairmail, arshitanairphone, ajitpatelmail, ajitpatelphone;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.about_fragment,container,false);
//        facultyrecyclerview = view.findViewById(R.id.facultyrecyclerview);
        amitneogimail = view.findViewById(R.id.amitneogimail);
        amitneogiphone = view.findViewById(R.id.amitneogiphone);
        apsinghmail = view.findViewById(R.id.apsinghmail);
        apsinghphone = view.findViewById(R.id.apsinghphone);
        arshitanairmail = view.findViewById(R.id.arshitanairmail);
        arshitanairphone = view.findViewById(R.id.arshitanairphone);
        ajitpatelmail = view.findViewById(R.id.ajitpatelmail);
        ajitpatelphone = view.findViewById(R.id.ajitpatelphone);
        setonc();
        swipetorefresh = view.findViewById(R.id.swipetorefresh);
        swipetorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FragmentTransaction ft = MainActivity.mainfm.beginTransaction();
                ft.detach(about_fragment.this);
                ft.attach(about_fragment.this);
                swipetorefresh.setRefreshing(false);
            }
        });
//        facultyrecyclerview.setAdapter(new faculty_recyclerview_adapter());
//        facultyrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);
        return view;
    }
    private void setonc()
    {
        amitneogiphone.setOnClickListener(this);
        amitneogimail.setOnClickListener(this);
        apsinghphone.setOnClickListener(this);
        apsinghmail.setOnClickListener(this);
        arshitanairphone.setOnClickListener(this);
        arshitanairmail.setOnClickListener(this);
        ajitpatelphone.setOnClickListener(this);
        ajitpatelmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String phone_number="";
        String email="";
        String name="";
        switch(v.getId())
        {
            case R.id.amitneogimail :
                email = "aneogi@lnmiit.ac.in";
                break;
            case R.id.apsinghmail :
                email = "apsingh@lnmiit.ac.in";
                break;
            case R.id.arshitanairmail :
                email = "arshitanair@lnmiit.ac.in";
                break;
            case R.id.ajitpatelmail:
                email = "ajitpatel@lnmiit.ac.in";
                break;
            case R.id.amitneogiphone :
                phone_number="8963807110";
                name = "Dr. Amit Neogi";
                break;
            case R.id.apsinghphone :
                phone_number="8963807110";
                name = "Prof. AP Singh";
                break;
            case R.id.arshitanairphone :
                phone_number="8963807110";
                name = "Mrs. Arshita Nair";
                break;
            case R.id.ajitpatelphone :
                phone_number="8963807110";
                name = "Dr. Ajit Patel";
                break;

        }
        if(phone_number.equals(""))
        {
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
            getContext().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }
        if(email.equals(""))
        {
            Intent contactsintent = new Intent(ContactsContract.Intents.Insert.ACTION);
            contactsintent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            contactsintent.putExtra(ContactsContract.Intents.Insert.NAME,name);
            contactsintent.putExtra(ContactsContract.Intents.Insert.PHONE,phone_number);
            startActivityForResult(contactsintent,12345);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12345)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                Showfancytoasr.show(getContext(),"Contact Added Successfully");
            }
        }
    }
}
