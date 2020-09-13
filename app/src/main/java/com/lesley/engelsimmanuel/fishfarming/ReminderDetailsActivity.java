package com.lesley.engelsimmanuel.fishfarming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ReminderDetailsActivity extends AppCompatActivity {
    private ImageButton close;
    private TextView stockNameStageAndCost, dateStocked, expectedDateOfHarvest, feedFishEvery, giveTreatmentEvery, changeWaterEvery, sortFishesEvery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_details);

        close = findViewById(R.id.reminder_details_close);
        stockNameStageAndCost = findViewById(R.id.reminder_details_stock_name_stage_and_cost);
        dateStocked = findViewById(R.id.reminder_details_date_stocked);
        expectedDateOfHarvest = findViewById(R.id.reminder_details_expected_date_of_harvest);
        feedFishEvery = findViewById(R.id.reminder_details_feed_fish_every);
        giveTreatmentEvery = findViewById(R.id.reminder_details_give_treatment_every);
        changeWaterEvery = findViewById(R.id.reminder_details_change_water_every);
        sortFishesEvery = findViewById(R.id.reminder_details_sort_fish_every);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        stockNameStageAndCost.setText(getIntent().getStringExtra("stock_name") + ", " + getIntent().getStringExtra("stock_stage")
                + ", cost " + getIntent().getStringExtra("currency_of_cost_of_stock") + getIntent().getStringExtra("cost_of_stock"));

        dateStocked.setText("Date stocked: " + getIntent().getStringExtra("date_stocked"));

        expectedDateOfHarvest.setText("Expected date of harvest: " + getIntent().getStringExtra("expected_date_of_harvest"));

        feedFishEvery.setText("Feed fish every " + getIntent().getStringExtra("feed_fish_frequency") + " "
                + getIntent().getStringExtra("feed_fish_frequency_occurrence"));

        giveTreatmentEvery.setText("Give treatment every " + getIntent().getStringExtra("treat_fish_frequency") + " "
                + getIntent().getStringExtra("treat_fish_frequency_occurrence"));

        changeWaterEvery.setText("Change water every " + getIntent().getStringExtra("change_water_frequency") + " "
                + getIntent().getStringExtra("change_water_frequency_occurrence"));

        sortFishesEvery.setText("Sort fishes every " + getIntent().getStringExtra("sort_fish_frequency") + " "
                + getIntent().getStringExtra("sort_fish_frequency_occurrence"));

    }

    public void onBackPressed() {
        finish();
    }
}