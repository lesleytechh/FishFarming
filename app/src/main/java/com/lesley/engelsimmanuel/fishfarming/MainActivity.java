package com.lesley.engelsimmanuel.fishfarming;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private TextView userName;
    private ImageButton showInformation;
    private ImageButton signOut;
    private ProgressBar loadingReminders;
    private RecyclerView remindersRecyclerView;
    private FirestoreRecyclerAdapter<Reminder, ReminderViewHolder> reminderAdapter;
    private LinearLayout nothingHereErrorlayout;
    private ImageView nothingHereErrorIllustration;
    private TextView nothingHereErrorText;
    private FloatingActionButton addReminder;
    private NecessaryEvil necessaryEvil = new NecessaryEvil();

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userName = findViewById(R.id.main_user_name);
        showInformation = findViewById(R.id.main_reminders_show_information);
        signOut = findViewById(R.id.main_reminders_sign_out);
        loadingReminders = findViewById(R.id.main_reminders_loading_reminders);
        remindersRecyclerView = findViewById(R.id.main_reminders_recycler_view);
        nothingHereErrorlayout = findViewById(R.id.main_nothing_here_error_layout);
        nothingHereErrorIllustration = findViewById(R.id.main_nothing_here_error_illustration);
        nothingHereErrorText = findViewById(R.id.main_nothing_here_error_text);
        addReminder = findViewById(R.id.main_add_reminder);

        loadingReminders.setVisibility(View.VISIBLE);

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

        remindersRecyclerView.setHasFixedSize(true);
        remindersRecyclerView.setLayoutManager(new GridLayoutManager(this, calculateNumberOfColumns(getApplicationContext(), 230)));

        Query query = firestore.collection("Reminders").whereEqualTo("by", auth.getCurrentUser().getUid());
        FirestoreRecyclerOptions<Reminder> options = new FirestoreRecyclerOptions.Builder<Reminder>().setQuery(query, Reminder.class).build();

        reminderAdapter = new FirestoreRecyclerAdapter<Reminder, ReminderViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public ReminderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item, parent, false);
                return new ReminderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull ReminderViewHolder holder, int position, @NonNull @NotNull Reminder reminder) {
                holder.costOfStock.setText(reminder.getCurrency_of_cost_of_stock() + " " + reminder.getCost_of_stock());
                holder.setImage(reminder.getStock_stage());
                holder.stockName.setText(reminder.getStock_name() + ", " + reminder.getStock_stage());
                holder.feedFishEvery.setText("Feed fish every " + reminder.getFeed_fish_frequency() + " " + reminder.getFeed_fish_frequency_occurrence());
                holder.giveTreatmentEvery.setText("Give treatment every " + reminder.getTreat_fish_frequency() + " " + reminder.getTreat_fish_frequency_occurrence());
                holder.changeWaterEvery.setText("Change water every " + reminder.getChange_water_frequency() + " " + reminder.getChange_water_frequency_occurrence());
                holder.sortFishesEvery.setText("Sort fishes every " + reminder.getSort_fish_frequency() + " " + reminder.getSort_fish_frequency_occurrence());
            }

            @Override
            public void onDataChanged() {
                loadingReminders.setVisibility(View.GONE);
                if (getItemCount() == 0) {
                    remindersRecyclerView.setVisibility(View.GONE);
                    nothingHereErrorlayout.setVisibility(View.VISIBLE);
                    nothingHereErrorIllustration.setImageDrawable(getResources().getDrawable(R.drawable.noreminderillustration));
                    nothingHereErrorText.setText(R.string.no_reminders_yet);
                } else {
                    remindersRecyclerView.setVisibility(View.VISIBLE);
                    nothingHereErrorlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(@NonNull @NotNull FirebaseFirestoreException e) {
                necessaryEvil.log(TAG, e.getMessage());
                loadingReminders.setVisibility(View.GONE);
                remindersRecyclerView.setVisibility(View.GONE);
                nothingHereErrorlayout.setVisibility(View.VISIBLE);
                nothingHereErrorIllustration.setImageDrawable(getResources().getDrawable(R.drawable.errorloadingremindersillustration));
                nothingHereErrorText.setText(R.string.error_loading_reminders);
            }
        };

        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddReminderBottomSheet addReminderBottomSheet = new AddReminderBottomSheet();
                addReminderBottomSheet.show(getSupportFragmentManager(), "addReminderBottomSheet");
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

        remindersRecyclerView.setAdapter(reminderAdapter);
    }

    private class ReminderViewHolder extends RecyclerView.ViewHolder {
        private TextView costOfStock, stockName, feedFishEvery, giveTreatmentEvery, changeWaterEvery, sortFishesEvery;
        private ImageView image;

        ReminderViewHolder(View itemView) {
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
                    Intent intent = new Intent(MainActivity.this, ReminderDetailsActivity.class);
                    intent.putExtra("doc_id", reminderAdapter.getSnapshots().getSnapshot(getAdapterPosition()).getId());
                    startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                    bottomSheetDialog.setContentView(getLayoutInflater().inflate(R.layout.delete_bottom_sheet_dialog, null));
                    bottomSheetDialog.show();

                    bottomSheetDialog.findViewById(R.id.delete_bottom_sheet_dialog_delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reminderAdapter.getSnapshots().getSnapshot(getAdapterPosition()).getReference().delete();
                            bottomSheetDialog.dismiss();
                        }
                    });
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

    private int calculateNumberOfColumns(Context context, float columnWidthInDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthInDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthInDp / columnWidthInDp + 0.5);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reminderAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        reminderAdapter.stopListening();
    }
}