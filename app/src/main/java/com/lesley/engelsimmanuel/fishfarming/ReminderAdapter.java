package com.lesley.engelsimmanuel.fishfarming;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {
    private List<Reminder> reminders;
    private OnReminderClickListener onReminderClickListener;

    public ReminderAdapter(List<Reminder> reminders, OnReminderClickListener onReminderClickListener) {
        this.reminders = reminders;
        this.onReminderClickListener = onReminderClickListener;
    }

    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item, parent, false);
        return new ReminderViewHolder(view, onReminderClickListener);
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
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    /*public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
        notifyDataSetChanged();
    }

    public Reminder getReminderAt(int position) {
        return reminders.get(position);
    }*/

    class ReminderViewHolder extends RecyclerView.ViewHolder {
        private TextView costOfStock, stockName, feedFishEvery, giveTreatmentEvery, changeWaterEvery, sortFishesEvery;
        private ImageView image;

        public ReminderViewHolder(@NonNull View itemView, final OnReminderClickListener onReminderClickListener) {
            super(itemView);

            costOfStock = itemView.findViewById(R.id.reminder_item_cost_of_stock);
            image = itemView.findViewById(R.id.reminder_item_image);
            stockName = itemView.findViewById(R.id.reminder_item_stock_name);
            feedFishEvery = itemView.findViewById(R.id.reminder_item_feed_fish_every);
            giveTreatmentEvery = itemView.findViewById(R.id.reminder_item_give_treatment_every);
            changeWaterEvery = itemView.findViewById(R.id.reminder_item_change_water_every);
            sortFishesEvery = itemView.findViewById(R.id.reminder_item_sort_fishes_every);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onReminderClickListener.onReminderClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onReminderClickListener.onReminderLongClick(reminders.get(getAdapterPosition()), getAdapterPosition());
                    return true;
                }
            });
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

    public interface OnReminderClickListener {
        void onReminderClick(int position);
        void onReminderLongClick(Reminder reminder, int position);
    }

}