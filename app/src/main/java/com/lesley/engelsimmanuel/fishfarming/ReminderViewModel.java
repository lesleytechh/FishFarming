package com.lesley.engelsimmanuel.fishfarming;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {
    private ReminderRepository repository;
    private LiveData<List<Reminder>> allReminders;

    public ReminderViewModel(@NonNull Application application) {
        super(application);
        repository = new ReminderRepository(application);
        allReminders = repository.getAllReminders();
    }

    public void insert(Reminder reminder) {
        repository.insert(reminder);
    }

    public void update(Reminder reminder) {
        repository.update(reminder);
    }

    public void delete(Reminder reminder) {
        repository.delete(reminder);
    }

    public void deleteAllReminders() {
        repository.deleteAllReminders();
    }

    public LiveData<List<Reminder>> getAllReminders() {
        return allReminders;
    }
}