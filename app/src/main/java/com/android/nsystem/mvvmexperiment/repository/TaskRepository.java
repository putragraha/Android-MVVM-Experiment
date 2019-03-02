package com.android.nsystem.mvvmexperiment.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.android.nsystem.mvvmexperiment.dao.TaskDao;
import com.android.nsystem.mvvmexperiment.database.TaskDatabase;
import com.android.nsystem.mvvmexperiment.entity.Task;

import java.util.List;

public class TaskRepository implements TaskDao {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mTaskList;

    public TaskRepository(Application application) {
        TaskDatabase database = TaskDatabase.getInstance(application);
        mTaskDao = database.getTaskDao();
        mTaskList = mTaskDao.getAllTasks();
    }

    @Override
    public void insert(Task task) {
        new InsertTaskAsyncTask(mTaskDao).execute(task);
    }

    @Override
    public void update(Task task) {
        new UpdateTaskAsyncTask(mTaskDao).execute(task);
    }

    @Override
    public void delete(Task task) {
        new DeleteTaskAsyncTask(mTaskDao).execute(task);
    }

    @Override
    public void deleteAllTasks() {
        new DeleteAllTaskAsyncTask(mTaskDao).execute();
    }

    @Override
    public LiveData<List<Task>> getAllTasks() {
        return mTaskList;
    }

    static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mTaskDao;

        InsertTaskAsyncTask(TaskDao taskDao) {
            this.mTaskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            mTaskDao.insert(tasks[0]);
            return null;
        }
    }

    static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mTaskDao;

        UpdateTaskAsyncTask(TaskDao taskDao) {
            this.mTaskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            mTaskDao.update(tasks[0]);
            return null;
        }
    }

    static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mTaskDao;

        DeleteTaskAsyncTask(TaskDao taskDao) {
            this.mTaskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            mTaskDao.delete(tasks[0]);
            return null;
        }
    }

    static class DeleteAllTaskAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao mTaskDao;

        DeleteAllTaskAsyncTask(TaskDao taskDao) {
            this.mTaskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mTaskDao.deleteAllTasks();
            return null;
        }
    }
}
