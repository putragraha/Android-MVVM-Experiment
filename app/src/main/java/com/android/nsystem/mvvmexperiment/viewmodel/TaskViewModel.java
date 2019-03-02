package com.android.nsystem.mvvmexperiment.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.android.nsystem.mvvmexperiment.dao.TaskDao;
import com.android.nsystem.mvvmexperiment.entity.Task;
import com.android.nsystem.mvvmexperiment.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel implements TaskDao {

    private TaskRepository mTaskRepository;
    private LiveData<List<Task>> mTaskList;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        mTaskRepository = new TaskRepository(application);
        mTaskList = mTaskRepository.getAllTasks();
    }

    @Override
    public void insert(Task task) {
        mTaskRepository.insert(task);
    }

    @Override
    public void update(Task task) {
        mTaskRepository.update(task);
    }

    @Override
    public void delete(Task task) {
        mTaskRepository.delete(task);
    }

    @Override
    public void deleteAllTasks() {
        mTaskRepository.deleteAllTasks();
    }

    @Override
    public LiveData<List<Task>> getAllTasks() {
        return mTaskList;
    }
}
