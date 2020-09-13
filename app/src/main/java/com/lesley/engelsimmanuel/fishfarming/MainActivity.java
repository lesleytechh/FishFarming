package com.lesley.engelsimmanuel.fishfarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // initialize your views here
    private RecyclerView remindersRecyclerView;
    private ReminderAdapter reminderAdapter;
    private LinearLayout nothingHerelayout;
    private FloatingActionButton addReminder;
    private NecessaryEvil necessaryEvil = new NecessaryEvil();
    private ReminderViewModel reminderViewModel;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // match initialized views to their individual ID's here
        remindersRecyclerView = findViewById(R.id.main_reminders_recycler_view);
        nothingHerelayout = findViewById(R.id.main_nothing_here_layout);
        addReminder = findViewById(R.id.main_add_reminder);

        remindersRecyclerView.setLayoutManager(new GridLayoutManager(this, calculateNumberOfColumns(getApplicationContext(), 230)));
        remindersRecyclerView.setHasFixedSize(true);

        reminderAdapter = new ReminderAdapter();
        remindersRecyclerView.setAdapter(reminderAdapter);

        reminderViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ReminderViewModel.class);

        reminderViewModel.getAllReminders().observe(this, new Observer<List<Reminder>>() {
            @Override
            public void onChanged(List<Reminder> reminders) {
                if (reminders.size() == 0) {
                    remindersRecyclerView.setVisibility(View.GONE);
                    nothingHerelayout.setVisibility(View.VISIBLE);
                } else {
                    reminderAdapter.setReminders(reminders);
                    remindersRecyclerView.setVisibility(View.VISIBLE);
                    nothingHerelayout.setVisibility(View.GONE);
                }
                necessaryEvil.log(TAG, "reminders size: " + reminders.size());
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
                AddReminderBottomSheet addReminderBottomSheet = new AddReminderBottomSheet();
                addReminderBottomSheet.show(getSupportFragmentManager(), "addReminderBottomSheet");
            }
        });

    }

    private int calculateNumberOfColumns(Context context, float columnWidthInDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthInDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthInDp / columnWidthInDp + 0.5);
    }

    // recycler view adapter class
    public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {
        private List<Reminder> reminders = new ArrayList<>();

        @NonNull
        @Override
        public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item, parent, false);
            return new ReminderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReminderViewHolder holder, final int position) {
            Reminder reminder = reminders.get(position);
            holder.costOfStock.setText(reminder.getCurrencyOfCostOfStock() + " " + reminder.getCostOfStock());
            holder.setImage(reminder.getStockStage());
            holder.stockName.setText(reminder.getStockName() + ", " + reminder.getStockStage());
            holder.feedFishEvery.setText("Feed fish every " + reminder.getFeedFishFrequency() + " " + reminder.getFeedFishFrequencyOccurrence());
            holder.giveTreatmentEvery.setText("Give treatment every " + reminder.getTreatFishFrequency() + " " + reminder.getTreatFishFrequencyOccurrence());
            holder.changeWaterEvery.setText("Change water every " + reminder.getChangeWaterFrequency() + " " + reminder.getChangeWaterFrequencyOccurrence());
            holder.sortFishesEvery.setText("Sort fishes every " + reminder.getSortFishFrequency() + " " + reminder.getSortFishFrequencyOccurrence());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ReminderDetailsActivity.class);
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
            });

        }

        @Override
        public int getItemCount() {
            return reminders.size();
        }

        public void setReminders(List<Reminder> reminders) {
            this.reminders = reminders;
            notifyDataSetChanged();
        }

        class ReminderViewHolder extends RecyclerView.ViewHolder {
            private TextView costOfStock, stockName, feedFishEvery, giveTreatmentEvery, changeWaterEvery, sortFishesEvery;
            private ImageView image;

            public ReminderViewHolder(@NonNull View itemView) {
                super(itemView);
                costOfStock = itemView.findViewById(R.id.reminder_item_cost_of_stock);
                image = itemView.findViewById(R.id.reminder_item_image);
                stockName = itemView.findViewById(R.id.reminder_item_stock_name);
                feedFishEvery = itemView.findViewById(R.id.reminder_item_feed_fish_every);
                giveTreatmentEvery = itemView.findViewById(R.id.reminder_item_give_treatment_every);
                changeWaterEvery = itemView.findViewById(R.id.reminder_item_change_water_every);
                sortFishesEvery = itemView.findViewById(R.id.reminder_item_sort_fishes_every);
            }

            private void setImage(String from) {
                if (from.equals("Fingerling")) {
                    image.setImageResource(R.drawable.fingerling);
                } else if (from.equals("Juvenile")) {
                    image.setImageResource(R.drawable.juvenile);
                } else {
                    image.setImageResource(R.drawable.postjuvenile);
                }
            }
        }

    }

}