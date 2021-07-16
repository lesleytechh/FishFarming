package com.lesley.engelsimmanuel.fishfarming;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ReminderAdapter.OnReminderClickListener {
    // initialize your views here
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private TextView userName;
    private ImageButton showInformation;
    private ImageButton signOut;
    private ProgressBar loadingReminders;
    private RecyclerView remindersRecyclerView;
    private ReminderAdapter reminderAdapter;
    private LinearLayout nothingHerelayout;
    private FloatingActionButton addReminder;
    private NecessaryEvil necessaryEvil = new NecessaryEvil();
    private ReminderViewModel reminderViewModel;
    private List<Reminder> reminders;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // match initialized views to their individual ID's here
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userName = findViewById(R.id.main_user_name);
        showInformation = findViewById(R.id.main_reminders_show_information);
        signOut = findViewById(R.id.main_reminders_sign_out);
        loadingReminders = findViewById(R.id.main_reminders_loading_reminders);
        remindersRecyclerView = findViewById(R.id.main_reminders_recycler_view);
        nothingHerelayout = findViewById(R.id.main_nothing_here_layout);
        addReminder = findViewById(R.id.main_add_reminder);

        userName.setText("...");

        firestore.collection("Users").document(auth.getCurrentUser().getUid()).addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                if (value != null && value.exists()) {
                    userName.setText(value.getString("name"));
                }
            }
        });

        reminders = new ArrayList<>();

        if (loadingReminders.getVisibility() == View.GONE) {
            loadingReminders.setVisibility(View.VISIBLE);
        }

        remindersRecyclerView.setLayoutManager(new GridLayoutManager(this, calculateNumberOfColumns(getApplicationContext(), 230)));
        remindersRecyclerView.setHasFixedSize(true);

        necessaryEvil.log(TAG, "reminders size: " + reminders.size());

        reminderViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ReminderViewModel.class);
        reminderViewModel.getAllReminders().observe(this, new Observer<List<Reminder>>() {
            @Override
            public void onChanged(List<Reminder> observedReminders) {
                reminders = observedReminders;

                if (reminders.size() == 0) {
                    remindersRecyclerView.setVisibility(View.GONE);
                    nothingHerelayout.setVisibility(View.VISIBLE);
                } else {
                    reminderAdapter = new ReminderAdapter(reminders, MainActivity.this);
                    remindersRecyclerView.setAdapter(reminderAdapter);
                    reminderAdapter.notifyDataSetChanged();

                    remindersRecyclerView.setVisibility(View.VISIBLE);
                    nothingHerelayout.setVisibility(View.GONE);
                }

                if (loadingReminders.getVisibility() == View.VISIBLE) {
                    loadingReminders.setVisibility(View.GONE);
                }
            }
        });

        // set an onclick listener to the addReminder FloatingActionButton
        // this is equivalent to setting an 'onclick' attribute to the button in XML only that i actually prefer doing it this way
        /*------------------------------------------------------------------------------------------------------------------------*/
        // a floating action is actually a type of button
        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show addReminderBottomSheet onClick this button
                showAddReminderBottomSheet(false, null);
            }
        });

        showInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                necessaryEvil.showErrorDialog(new Dialog(MainActivity.this), "What's This?", "Long press a reminder to show more options. Click on a reminder to view reminder details");
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                necessaryEvil.showSignOutDialog(MainActivity.this, SignInActivity.class, new Dialog(MainActivity.this), "Sign Out", "Are you sure you want to sign out?");
            }
        });
    }

    @Override
    public void onReminderClick(int position) {
        necessaryEvil.log(TAG, "onReminderClick position: " + position);
        Intent intent = new Intent(MainActivity.this, ReminderDetailsActivity.class);
        intent.putExtra("id", reminders.get(position).getId());
        intent.putExtra("stock_name", reminders.get(position).getStockName());
        intent.putExtra("date_stocked", reminders.get(position).getDateStocked());
        intent.putExtra("currency_of_cost_of_stock", reminders.get(position).getCurrencyOfCostOfStock());
        intent.putExtra("cost_of_stock", reminders.get(position).getCostOfStock());
        intent.putExtra("stock_stage", reminders.get(position).getStockStage());
        intent.putExtra("expected_date_of_harvest", reminders.get(position).getExpectedDateOfHarvest());
        intent.putExtra("feed_fish_frequency", reminders.get(position).getFeedFishFrequency());
        intent.putExtra("feed_fish_frequency_occurrence", reminders.get(position).getFeedFishFrequencyOccurrence());
        intent.putExtra("treat_fish_frequency", reminders.get(position).getTreatFishFrequency());
        intent.putExtra("treat_fish_frequency_occurrence", reminders.get(position).getTreatFishFrequencyOccurrence());
        intent.putExtra("change_water_frequency", reminders.get(position).getChangeWaterFrequency());
        intent.putExtra("change_water_frequency_occurrence", reminders.get(position).getChangeWaterFrequencyOccurrence());
        intent.putExtra("sort_fish_frequency", reminders.get(position).getSortFishFrequency());
        intent.putExtra("sort_fish_frequency_occurrence", reminders.get(position).getSortFishFrequencyOccurrence());
        startActivity(intent);
    }

    @Override
    public void onReminderLongClick(final Reminder reminder, int position) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(getLayoutInflater().inflate(R.layout.edit_delete_bottom_sheet_dialog, null));
        bottomSheetDialog.show();

        bottomSheetDialog.findViewById(R.id.edit_delete_bottom_sheet_dialog_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddReminderBottomSheet(true, reminder);
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.findViewById(R.id.edit_delete_bottom_sheet_dialog_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminderViewModel.delete(reminder);
                bottomSheetDialog.dismiss();
            }
        });

    }

    private int calculateNumberOfColumns(Context context, float columnWidthInDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthInDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthInDp / columnWidthInDp + 0.5);
    }

    private void showAddReminderBottomSheet(Boolean isFromEdit, Reminder reminder) {
        AddReminderBottomSheet addReminderBottomSheet = new AddReminderBottomSheet();
        if (isFromEdit) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_from_edit", true);
            bundle.putString("stock_name", reminder.getStockName());
            bundle.putString("date_stocked", reminder.getStockName());
            bundle.putString("currency_of_cost_of_stock", reminder.getStockName());
            bundle.putString("cost_of_stock", reminder.getStockName());
            bundle.putString("stock_stage", reminder.getStockName());
            bundle.putString("expected_date_of_harvest", reminder.getStockName());
            bundle.putString("feed_fish_frequency", reminder.getStockName());
            bundle.putString("feed_fish_frequency_occurrence", reminder.getStockName());
            bundle.putString("treat_fish_frequency", reminder.getStockName());
            bundle.putString("treat_fish_frequency_occurrence", reminder.getStockName());
            bundle.putString("change_water_frequency", reminder.getStockName());
            bundle.putString("change_water_frequency_occurrence", reminder.getStockName());
            bundle.putString("sort_fish_frequency", reminder.getStockName());
            bundle.putString("sort_fish_frequency_occurrence", reminder.getStockName());
            addReminderBottomSheet.setArguments(bundle);
        }
        addReminderBottomSheet.show(getSupportFragmentManager(), "addReminderBottomSheet");
    }

    /*

    private void deleteReminder() {
        ReminderViewModel reminderViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(ReminderDetailsActivity.this.getApplication())).get(ReminderViewModel.class);
        reminderViewModel.delete(new Reminder(getIntent().getStringExtra("stock_name"), getIntent().getStringExtra("date_stocked"),
                getIntent().getStringExtra("currency_of_cost_of_stock"), getIntent().getStringExtra("cost_of_stock"),
                getIntent().getStringExtra("stock_stage"), getIntent().getStringExtra("expected_date_of_harvest"),
                getIntent().getStringExtra("feed_fish_frequency"), getIntent().getStringExtra("feed_fish_frequency_occurrence"),
                getIntent().getStringExtra("treat_fish_frequency"), getIntent().getStringExtra("treat_fish_frequency_occurrence"),
                getIntent().getStringExtra("change_water_frequency"), getIntent().getStringExtra("change_water_frequency_occurrence"),
                getIntent().getStringExtra("sort_fish_frequency"), getIntent().getStringExtra("sort_fish_frequency_occurrence")));
        finish();
    }*/
}