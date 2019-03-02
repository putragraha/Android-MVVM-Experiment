package com.android.nsystem.mvvmexperiment.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.android.nsystem.mvvmexperiment.dao.TaskDao;
import com.android.nsystem.mvvmexperiment.entity.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    private static TaskDatabase instance;

    public abstract TaskDao getTaskDao();

    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null)  {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TaskDatabase.class,
                    "Task")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao mTaskDao;

        PopulateDBAsyncTask(TaskDatabase db) {
            mTaskDao = db.getTaskDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mTaskDao.insert(new Task("Learning MVVM"));
            mTaskDao.insert(new Task("Implement MVVM Project"));
            mTaskDao.insert(new Task("Master MVVM"));
            return null;
        }
    }
}
