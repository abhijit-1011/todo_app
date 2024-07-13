package com.example.todo_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private DatabaseHelper db;

    public TaskAdapter(List<Task> taskList, DatabaseHelper db) {
        this.taskList = taskList;
        this.db = db;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.textViewTaskName.setText(task.getName());
        holder.checkBoxCompleted.setChecked(task.isCompleted());
        holder.checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            db.updateTask(task);
        });
        holder.buttonDeleteTask.setOnClickListener(v -> {
            db.deleteTask(task.getId());
            taskList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, taskList.size());
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void addTask(Task task) {
        taskList.add(task);
        notifyItemInserted(taskList.size() - 1);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTaskName;
        CheckBox checkBoxCompleted;
        ImageButton buttonDeleteTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
            buttonDeleteTask = itemView.findViewById(R.id.buttonDeleteTask);
        }
    }
}
