package com.lesley.engelsimmanuel.fishfarming;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

public class SortFishWorker extends Worker {
    private final NecessaryEvil necessaryEvil = new NecessaryEvil();
    private final Constants constants = new Constants();
    private Context context;

    public SortFishWorker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        necessaryEvil.showNotification(context, R.drawable.alarm, constants.REMINDER_ALERT_CHANNEL_ID, constants.REMINDER_ALERT_CHANNEL_NAME, constants.REMINDER_ALERT_CHANNEL_DESCRIPTION, constants.REMINDER_SORT_FISH_ALERT_NOTIFICATION_TITLE, String.format(constants.REMINDER_SORT_FISH_ALERT_NOTIFICATION_BODY, getInputData().getString("stock_name")), constants.REMINDER_ALERT_NOTIFICATION_GROUP_KEY, constants.REMINDER_ALERT_NOTIFICATION_ID);
        return Result.success();
    }
}
