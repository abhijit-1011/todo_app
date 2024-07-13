package com.example.todo_app;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private TaskAdapter adapter;
    private EditText editTextTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        editTextTask = findViewById(R.id.editTextTask);
        Button buttonAddTask = findViewById(R.id.buttonAddTask);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(db.getAllTasks(), db);
        recyclerView.setAdapter(adapter);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    private void addTask() {
        String taskName = editTextTask.getText().toString().trim();
        if (!TextUtils.isEmpty(taskName)) {
            Task task = new Task(taskName, false);
            db.addTask(task);
            adapter.addTask(task);
            editTextTask.setText("");
        }
    }
}
