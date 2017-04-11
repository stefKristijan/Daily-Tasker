package hr.ferit.kstefancic.dailytasker;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int KEY_REQUEST_TASK = 10;
    public static final String KEY_TASK = "task";
    ArrayList<Task> tasks;
    RecyclerView rvTasksList;
    TaskAdapter mTaskAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.ItemDecoration mItemDecoration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setUI();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent explicitIntent = new Intent(getApplicationContext(),NewTaskActivity.class);
                startActivity(explicitIntent);
            }
        });
    }

    private void setUI() {
        Context context = getApplicationContext();
        tasks = this.loadTasks();
        this.rvTasksList= (RecyclerView) this.findViewById(R.id.rvTasks);
        this.mTaskAdapter = new TaskAdapter(tasks);
        this.mLayoutManager = new LinearLayoutManager(context);
        this.mItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);

        this.rvTasksList.addItemDecoration(this.mItemDecoration);
        this.rvTasksList.setLayoutManager(this.mLayoutManager);
        this.rvTasksList.setAdapter(this.mTaskAdapter);
    }

    private ArrayList<Task> loadTasks() {
        return TaskDBHelper.getInstance(this).getAllTasks();
    }

}
