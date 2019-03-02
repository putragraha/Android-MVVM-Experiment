package com.android.nsystem.mvvmexperiment.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.nsystem.mvvmexperiment.R;

public class TaskFormActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.android.nsystem.mvvmexperiment.activity.EXTRA_ID";
    public static final String EXTRA_DESCRIPTION = "com.android.nsystem.mvvmexperiment.activity.EXTRA_DESCRIPTION";

    private EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        descriptionEditText = findViewById(R.id.edit_text_description);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        if (getIntent().hasExtra(EXTRA_ID)) {
            setTitle("Edit Task");
            String description = getIntent().getStringExtra(EXTRA_DESCRIPTION);
            descriptionEditText.setText(description);
        } else {
            setTitle("New Task");
        }

        Button addTaskButton = findViewById(R.id.button_done);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        String description = descriptionEditText.getText().toString();
        if (description.trim().isEmpty()) {
            Toast.makeText(this, "Please fill in your task", Toast.LENGTH_LONG).show();
        } else {
            Intent data = new Intent();
            data.putExtra(EXTRA_DESCRIPTION, description);

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_ID, id);
            }

            setResult(RESULT_OK, data);
            finish();
        }
    }
}
