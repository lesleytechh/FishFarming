package com.lesley.engelsimmanuel.fishfarming;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReminderDao {

    @Insert
    void insert(Reminder reminder);

    @Update
    void update(Reminder reminder);

    @Delete
    void delete(Reminder reminder);

    @Query("DELETE FROM reminder_table")
    void deleteAllReminders();

    @Query("SELECT * FROM reminder_table ORDER BY id DESC")
    List<Reminder> getAllReminders();

}