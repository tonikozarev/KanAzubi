package com.example.techgeek.kanazubi;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.techgeek.kanazubi.SQL.DatabaseHelper;
import com.example.techgeek.kanazubi.Session.Session;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    TextView emptyList;
    ListView listResult;
    private int userID;
    private String status, team;
    private ArrayList<Integer> listID;
    private ArrayList<String> listTitle;
    private DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dbhelper = new DatabaseHelper(this);

        Session session = new Session(this);
        userID = session.getId();           //userID

        listResult = findViewById(R.id.listResult);
        emptyList = findViewById(R.id.emptyList);
        emptyList.setVisibility(View.GONE);
        listID = new ArrayList<>();
        listTitle = new ArrayList<>();

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            status = (String) bundle.get("status");   //selected Status
        }
        if (bundle != null) {
            team = (String) bundle.get("team");       //selected Team
        }

        createList();

        if (listID.size() == 0) {
            emptyList.setVisibility(View.VISIBLE);
            listResult.setVisibility(View.GONE);
        }

        listResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(List.this, Task.class);
                i.putExtra("listID", listID);
                i.putExtra("taskID", position);
                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.shake);
                animation.setDuration(75);
                view.startAnimation(animation);
                startActivity(i);
            }
        });

    }

    private void createList() {
        Cursor crs = dbhelper.queryList();
        while (crs.moveToNext()) {
            if ((crs.getInt(4) == 0 || crs.getInt(4) == userID) && crs.getString(2).equals(team) && crs.getString(3).equals(status)) {
                listID.add(crs.getInt(0));
                listTitle.add(crs.getString(1));
            }
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.spinner_items, listTitle) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setBackground(getContext().getDrawable(R.drawable.boarder_row_list)); //set boarder to every row in the list
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = 140; //height for every row in List activity
                view.setLayoutParams(params);
                return view;
            }
        };
        listResult.setAdapter(adapter);

        crs.close();
        dbhelper.close();
    }
}
