package com.lesley.engelsimmanuel.fishfarming;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ReminderDetailsActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private ImageButton close;
    private TextView stockNameStageAndCost, dateStocked, expectedDateOfHarvest, feedFishEvery, giveTreatmentEvery, changeWaterEvery, sortFishesEvery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_details);

        firestore = FirebaseFirestore.getInstance();
        close = findViewById(R.id.reminder_details_close);
        stockNameStageAndCost = findViewById(R.id.reminder_details_stock_name_stage_and_cost);
        dateStocked = findViewById(R.id.reminder_details_date_stocked);
        expectedDateOfHarvest = findViewById(R.id.reminder_details_expected_date_of_harvest);
        feedFishEvery = findViewById(R.id.reminder_details_feed_fish_every);
        giveTreatmentEvery = findViewById(R.id.reminder_details_give_treatment_every);
        changeWaterEvery = findViewById(R.id.reminder_details_change_water_every);
        sortFishesEvery = findViewById(R.id.reminder_details_sort_fish_every);

        firestore.collection("Reminders").document(getIntent().getStringExtra("doc_id")).addSnapshotListener(ReminderDetailsActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                if (value != null && value.exists()) {
                    stockNameStageAndCost.setText(value.getString("stock_name") + ", " + value.getString("stock_stage")
                            + ", cost " + value.getString("currency_of_cost_of_stock") + value.getString("cost_of_stock"));

                    dateStocked.setText("Date stocked: " + value.getString("date_stocked"));

                    expectedDateOfHarvest.setText("Expected date of harvest: " + value.getString("expected_date_of_harvest"));

                    feedFishEvery.setText("Feed fish every " + value.getString("feed_fish_frequency") + " "
                            + value.getString("feed_fish_frequency_occurrence"));

                    giveTreatmentEvery.setText("Give treatment every " + value.getString("treat_fish_frequency") + " "
                            + value.getString("treat_fish_frequency_occurrence"));

                    changeWaterEvery.setText("Change water every " + value.getString("change_water_frequency") + " "
                            + value.getString("change_water_frequency_occurrence"));

                    sortFishesEvery.setText("Sort fishes every " + value.getString("sort_fish_frequency") + " "
                            + value.getString("sort_fish_frequency_occurrence"));
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onBackPressed() {
        finish();
    }

}