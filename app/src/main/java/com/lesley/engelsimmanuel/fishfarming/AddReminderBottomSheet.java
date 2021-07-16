package com.lesley.engelsimmanuel.fishfarming;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddReminderBottomSheet extends BottomSheetDialogFragment {
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private TextInputEditText stockName, stockDate, stockCost, stockHarvestDate, feedFrequency, treatmentFrequency, changeFrequency, sortFrequency;
    private Button selectStockDate, selectStockHarvestDate, addReminder;
    private Spinner selectStockCostCurrency, selectStockStage, selectFeedFrequencyOccurence, selectTreatmentFrequencyOccurence, selectChangeFrequencyOccurence, selectSortFrequencyOccurence;
    private final NecessaryEvil necessaryEvil = new NecessaryEvil();
    private final Constants constants = new Constants();

    private String TAG = "AddRmdrBtmSht";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_reminder_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        stockName = view.findViewById(R.id.stock_name);
        stockDate = view.findViewById(R.id.stock_date);
        selectStockDate = view.findViewById(R.id.select_stock_date);
        selectStockCostCurrency = view.findViewById(R.id.select_stock_cost_currency);
        stockCost = view.findViewById(R.id.stock_cost);
        selectStockStage = view.findViewById(R.id.select_stock_stage);
        stockHarvestDate = view.findViewById(R.id.stock_harvest_date);
        selectStockHarvestDate = view.findViewById(R.id.select_stock_harvest_date);
        feedFrequency = view.findViewById(R.id.feed_frequency);
        selectFeedFrequencyOccurence = view.findViewById(R.id.select_feed_frequency_occurence);
        treatmentFrequency = view.findViewById(R.id.treatment_frequency);
        selectTreatmentFrequencyOccurence = view.findViewById(R.id.select_treatment_frequency_occurence);
        changeFrequency = view.findViewById(R.id.change_frequency);
        selectChangeFrequencyOccurence = view.findViewById(R.id.select_change_frequency_occurence);
        sortFrequency = view.findViewById(R.id.sort_frequency);
        selectSortFrequencyOccurence = view.findViewById(R.id.select_sort_frequency_occurence);
        addReminder = view.findViewById(R.id.add_reminder);

        /*if(getArguments().getBoolean("is_from_edit")){
            stockName.setText(getArguments().getString(""));
            stockDate.setText(getArguments().getString(""));
            //selectStockCostCurrency.setSelection();
            stockCost.setText(getArguments().getString(""));
            //selectStockStage.setSelection();
            stockHarvestDate.setText(getArguments().getString(""));
            feedFrequency.setText(getArguments().getString(""));
            //selectFeedFrequencyOccurence.setSelection();
            treatmentFrequency.setText(getArguments().getString(""));
            //selectTreatmentFrequencyOccurence.setSelection();
            changeFrequency.setText(getArguments().getString(""));
            //selectChangeFrequencyOccurence.setSelection();
            sortFrequency.setText(getArguments().getString(""));
            //selectSortFrequencyOccurence.setSelection();
        }*/

        selectStockDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        stockDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        stockDate.setTextColor(getResources().getColor(android.R.color.black));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        selectStockHarvestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        stockHarvestDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        stockHarvestDate.setTextColor(getResources().getColor(android.R.color.black));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(stockName.getText().toString()) && !TextUtils.isEmpty(stockDate.getText().toString()) &&
                        !TextUtils.isEmpty(stockCost.getText().toString()) && !TextUtils.isEmpty(stockHarvestDate.getText().toString()) &&
                        !TextUtils.isEmpty(feedFrequency.getText().toString()) && !TextUtils.isEmpty(treatmentFrequency.getText().toString()) &&
                        !TextUtils.isEmpty(changeFrequency.getText().toString()) && !TextUtils.isEmpty(sortFrequency.getText().toString())) {

                    validateInput();

                } else {
                    necessaryEvil.showErrorDialog(new Dialog(getContext()), "Empty Field(s)", "Some fields are empty");
                }
            }
        });

    }

    private void validateInput() {
        Dialog d = new Dialog(getContext());

        if ((Integer.parseInt(feedFrequency.getText().toString()) > 24 || Integer.parseInt(feedFrequency.getText().toString()) == 0) && selectFeedFrequencyOccurence.getSelectedItemPosition() == 0) {
            necessaryEvil.showErrorDialog(d, "Invalid Feed Frequency", "You have selected an invalid number of hour(s) for fish feed frequency");

        } else if ((Integer.parseInt(feedFrequency.getText().toString()) > 7 || Integer.parseInt(feedFrequency.getText().toString()) == 0) && selectFeedFrequencyOccurence.getSelectedItemPosition() == 1) {
            necessaryEvil.showErrorDialog(d, "Invalid Feed Frequency", "You have selected an invalid number of day(s) for fish feed frequency");

        } else if ((Integer.parseInt(feedFrequency.getText().toString()) > 4 || Integer.parseInt(feedFrequency.getText().toString()) == 0) && selectFeedFrequencyOccurence.getSelectedItemPosition() == 2) {
            necessaryEvil.showErrorDialog(d, "Invalid Feed Frequency", "You have selected an invalid number of week(s) for fish feed frequency");

        } else if ((Integer.parseInt(feedFrequency.getText().toString()) > 12 || Integer.parseInt(feedFrequency.getText().toString()) == 0) && selectFeedFrequencyOccurence.getSelectedItemPosition() == 3) {
            necessaryEvil.showErrorDialog(d, "Invalid Feed Frequency", "You have selected an invalid number of month(s) for fish feed frequency");

        } else if (Integer.parseInt(feedFrequency.getText().toString()) == 0 && selectFeedFrequencyOccurence.getSelectedItemPosition() == 4) {
            necessaryEvil.showErrorDialog(d, "Invalid Feed Frequency", "You have selected an invalid number of year(s) for fish feed frequency");


        } else if ((Integer.parseInt(treatmentFrequency.getText().toString()) > 24 || Integer.parseInt(treatmentFrequency.getText().toString()) == 0) && selectTreatmentFrequencyOccurence.getSelectedItemPosition() == 0) {
            necessaryEvil.showErrorDialog(d, "Invalid Treatment Frequency", "You have selected an invalid number of hour(s) for fish treatment frequency");

        } else if ((Integer.parseInt(treatmentFrequency.getText().toString()) > 7 || Integer.parseInt(treatmentFrequency.getText().toString()) == 0) && selectTreatmentFrequencyOccurence.getSelectedItemPosition() == 1) {
            necessaryEvil.showErrorDialog(d, "Invalid Treatment Frequency", "You have selected an invalid number of day(s) for fish treatment frequency");

        } else if ((Integer.parseInt(treatmentFrequency.getText().toString()) > 4 || Integer.parseInt(treatmentFrequency.getText().toString()) == 0) && selectTreatmentFrequencyOccurence.getSelectedItemPosition() == 2) {
            necessaryEvil.showErrorDialog(d, "Invalid Treatment Frequency", "You have selected an invalid number of week(s) for fish treatment frequency");

        } else if ((Integer.parseInt(treatmentFrequency.getText().toString()) > 12 || Integer.parseInt(treatmentFrequency.getText().toString()) == 0) && selectTreatmentFrequencyOccurence.getSelectedItemPosition() == 3) {
            necessaryEvil.showErrorDialog(d, "Invalid Treatment Frequency", "You have selected an invalid number of month(s) for fish treatment frequency");

        } else if (Integer.parseInt(treatmentFrequency.getText().toString()) == 0 && selectTreatmentFrequencyOccurence.getSelectedItemPosition() == 4) {
            necessaryEvil.showErrorDialog(d, "Invalid Treatment Frequency", "You have selected an invalid number of year(s) for fish treatment frequency");


        } else if ((Integer.parseInt(changeFrequency.getText().toString()) > 24 || Integer.parseInt(changeFrequency.getText().toString()) == 0) && selectChangeFrequencyOccurence.getSelectedItemPosition() == 0) {
            necessaryEvil.showErrorDialog(d, "Invalid Change Frequency", "You have selected an invalid number of hour(s) for fish water change frequency");

        } else if ((Integer.parseInt(changeFrequency.getText().toString()) > 7 || Integer.parseInt(changeFrequency.getText().toString()) == 0) && selectChangeFrequencyOccurence.getSelectedItemPosition() == 1) {
            necessaryEvil.showErrorDialog(d, "Invalid Change Frequency", "You have selected an invalid number of day(s) for fish water change frequency");

        } else if ((Integer.parseInt(changeFrequency.getText().toString()) > 4 || Integer.parseInt(changeFrequency.getText().toString()) == 0) && selectChangeFrequencyOccurence.getSelectedItemPosition() == 2) {
            necessaryEvil.showErrorDialog(d, "Invalid Change Frequency", "You have selected an invalid number of week(s) for fish water change frequency");

        } else if ((Integer.parseInt(changeFrequency.getText().toString()) > 12 || Integer.parseInt(changeFrequency.getText().toString()) == 0) && selectChangeFrequencyOccurence.getSelectedItemPosition() == 3) {
            necessaryEvil.showErrorDialog(d, "Invalid Change Frequency", "You have selected an invalid number of month(s) for fish water change frequency");

        } else if (Integer.parseInt(changeFrequency.getText().toString()) == 0 && selectChangeFrequencyOccurence.getSelectedItemPosition() == 4) {
            necessaryEvil.showErrorDialog(d, "Invalid Change Frequency", "You have selected an invalid number of year(s) for fish water change frequency");


        } else if ((Integer.parseInt(sortFrequency.getText().toString()) > 24 || Integer.parseInt(sortFrequency.getText().toString()) == 0) && selectSortFrequencyOccurence.getSelectedItemPosition() == 0) {
            necessaryEvil.showErrorDialog(d, "Invalid Sort Frequency", "You have selected an invalid number of hour(s) for fish sort frequency");

        } else if ((Integer.parseInt(sortFrequency.getText().toString()) > 7 || Integer.parseInt(sortFrequency.getText().toString()) == 0) && selectSortFrequencyOccurence.getSelectedItemPosition() == 1) {
            necessaryEvil.showErrorDialog(d, "Invalid Sort Frequency", "You have selected an invalid number of day(s) for fish sort frequency");

        } else if ((Integer.parseInt(sortFrequency.getText().toString()) > 4 || Integer.parseInt(sortFrequency.getText().toString()) == 0) && selectSortFrequencyOccurence.getSelectedItemPosition() == 2) {
            necessaryEvil.showErrorDialog(d, "Invalid Sort Frequency", "You have selected an invalid number of week(s) for fish sort frequency");

        } else if ((Integer.parseInt(sortFrequency.getText().toString()) > 12 || Integer.parseInt(sortFrequency.getText().toString()) == 0) && selectSortFrequencyOccurence.getSelectedItemPosition() == 3) {
            necessaryEvil.showErrorDialog(d, "Invalid Sort Frequency", "You have selected an invalid number of month(s) for fish sort frequency");

        } else if (Integer.parseInt(sortFrequency.getText().toString()) == 0 && selectSortFrequencyOccurence.getSelectedItemPosition() == 4) {
            necessaryEvil.showErrorDialog(d, "Invalid Sort Frequency", "You have selected an invalid number of year(s) for fish sort frequency");


        } else {
            uploadReminderToDatabase();
        }
    }

    private void uploadReminderToDatabase() {
        Map<String, Object> reminderMap = new HashMap<>();
        reminderMap.put("stock_name", stockName.getText().toString());
        reminderMap.put("date_stocked", stockDate.getText().toString());
        reminderMap.put("currency_of_cost_of_stock", selectStockCostCurrency.getSelectedItem().toString());
        reminderMap.put("cost_of_stock", stockCost.getText().toString());
        reminderMap.put("stock_stage", selectStockStage.getSelectedItem().toString());
        reminderMap.put("expected_date_of_harvest", stockHarvestDate.getText().toString());
        reminderMap.put("feed_fish_frequency", feedFrequency.getText().toString());
        reminderMap.put("feed_fish_frequency_occurrence", selectFeedFrequencyOccurence.getSelectedItem().toString());
        reminderMap.put("treat_fish_frequency", treatmentFrequency.getText().toString());
        reminderMap.put("treat_fish_frequency_occurrence", selectTreatmentFrequencyOccurence.getSelectedItem().toString());
        reminderMap.put("change_water_frequency", changeFrequency.getText().toString());
        reminderMap.put("change_water_frequency_occurrence", selectChangeFrequencyOccurence.getSelectedItem().toString());
        reminderMap.put("sort_fish_frequency", sortFrequency.getText().toString());
        reminderMap.put("sort_fish_frequency_occurrence", selectSortFrequencyOccurence.getSelectedItem().toString());
        reminderMap.put("by", auth.getCurrentUser().getUid());
        reminderMap.put("timestamp", FieldValue.serverTimestamp());
        firestore.collection("Reminders").add(reminderMap);
        necessaryEvil.showNotification(getActivity(), constants.REMINDER_ADDED_CHANNEL_ID, constants.REMINDER_ADDED_CHANNEL_NAME, constants.REMINDER_ADDED_CHANNEL_DESCRIPTION, constants.REMINDER_ADDED_NOTIFICATION_TITLE, constants.REMINDER_ADDED_NOTIFICATION_BODY, constants.REMINDER_ADDED_NOTIFICATION_GROUP_KEY, constants.REMINDER_ADDED_NOTIFICATION_ID);
        dismiss();
    }

}