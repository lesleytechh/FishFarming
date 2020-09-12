package com.lesley.engelsimmanuel.fishfarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    // initialize your views here
    private RecyclerView remindersRecyclerView;
    private LinearLayout nothingHerelayout;
    private FloatingActionButton addReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // match initialized views to their individual ID's here
        remindersRecyclerView = findViewById(R.id.main_reminders_recycler_view);
        nothingHerelayout = findViewById(R.id.main_nothing_here_layout);
        addReminder = findViewById(R.id.main_add_reminder);

        // set an onclick listener to the addReminder FloatingActionButton
        // this is equivalent to setting an 'onclick' attribute to the button in XML only that i actually prefer doing it this way
        /*------------------------------------------------------------------------------------------------------------------------*/
        // a floating action is actually a type of button
        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show addReminderBottomSheet onClick this button
                AddReminderBottomSheet addReminderBottomSheet = new AddReminderBottomSheet();
                addReminderBottomSheet.show(getSupportFragmentManager(), "addReminderBottomSheet");
            }
        });

    }
}
