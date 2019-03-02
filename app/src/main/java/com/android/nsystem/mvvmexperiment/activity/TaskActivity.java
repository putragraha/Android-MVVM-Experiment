package com.android.nsystem.mvvmexperiment.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.nsystem.mvvmexperiment.R;
import com.android.nsystem.mvvmexperiment.adapter.TaskAdapter;
import com.android.nsystem.mvvmexperiment.entity.Task;
import com.android.nsystem.mvvmexperiment.viewmodel.TaskViewModel;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;

    private TaskViewModel mTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TaskAdapter adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                adapter.submitList(tasks);
            }
        });

        FloatingActionButton addTaskButton = findViewById(R.id.button_add_task);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, TaskFormActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                mTaskViewModel.delete(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TaskActivity.this, "Hmm, your task has been deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Intent intent = new Intent(TaskActivity.this, TaskFormActivity.class);
                intent.putExtra(TaskFormActivity.EXTRA_ID, task.getId());
                intent.putExtra(TaskFormActivity.EXTRA_DESCRIPTION, task.getDescription());

                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK) {
            String description = data.getStringExtra(TaskFormActivity.EXTRA_DESCRIPTION);

            mTaskViewModel.insert(new Task(description));
            Toast.makeText(this, "Yay!!! You have another task to do", Toast.LENGTH_LONG).show();
        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK){
            int id = data.getIntExtra(TaskFormActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Sorry :( Cannot update data, please try again", Toast.LENGTH_LONG).show();
            } else {
                String description = data.getStringExtra(TaskFormActivity.EXTRA_DESCRIPTION);
                Task task = new Task(description);
                task.setId(id);
                mTaskViewModel.update(task);

                Toast.makeText(this, "Yay... Update data success", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Oops :( We found no task you have added", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_delete_all:
                mTaskViewModel.deleteAllTasks();
                Toast.makeText(this, "Hey... All of your tasks have been deleted", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
