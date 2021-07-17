package com.lesley.engelsimmanuel.fishfarming;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class NecessaryEvil {
    public void showLoadingDialog(final Dialog d, String loadingBody) {
        d.setContentView(R.layout.loading_dialog);
        d.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        d.getWindow().setAttributes(lp);
        d.show();

        TextView lText = d.findViewById(R.id.loading_dialog_loading_text);
        lText.setText(loadingBody);
    }

    public void showErrorDialog(final Dialog d, String errorTitle, String errorBody) {
        d.setContentView(R.layout.error_dialog);
        d.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        d.getWindow().setAttributes(lp);
        d.show();

        TextView eTitle = d.findViewById(R.id.error_dialog_error_title);
        TextView eText = d.findViewById(R.id.error_dialog_error_text);
        Button eDismiss = d.findViewById(R.id.error_dialog_button);

        eTitle.setText(errorTitle);
        eText.setText(errorBody);

        eDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    }

    public void showSignOutDialog(Activity currentActivity, Class destinationActivityClass, final Dialog d, String title, String body) {
        d.setContentView(R.layout.sign_out_dialog);
        d.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        d.getWindow().setAttributes(lp);
        d.show();

        TextView sTitle = d.findViewById(R.id.sign_out_dialog_title);
        TextView sText = d.findViewById(R.id.sign_out_dialog_text);
        Button sNegButton = d.findViewById(R.id.sign_out_dialog_negative_button);
        Button sPosButton = d.findViewById(R.id.sign_out_dialog_positive_button);

        sTitle.setText(title);
        sText.setText(body);

        sNegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        sPosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                Dialog dg = new Dialog(currentActivity);
                showLoadingDialog(dg, currentActivity.getString(R.string.signing_out));
                FirebaseFirestore.getInstance().collection("Reminders").whereEqualTo("by", FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(currentActivity, new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                            for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                                doc.getReference().update("reminder_booked", false);
                            }
                        }
                        dg.dismiss();
                        WorkManager.getInstance(currentActivity).cancelAllWork();
                        FirebaseAuth.getInstance().signOut();
                        currentActivity.startActivity(new Intent(currentActivity, destinationActivityClass));
                        currentActivity.finish();
                    }
                }).addOnFailureListener(currentActivity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        dg.dismiss();
                        showErrorDialog(new Dialog(currentActivity), "Oops", "We could not sign you out. Please try again");
                    }
                });
            }
        });
    }

    public void showToast(Context context, String toastMessage) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
    }

    public void log(String TAG, String message) {
        Log.wtf(TAG, message);
    }

    public void showNotification(Activity activity, int icon, String channelId, String channelName, String channelDescription, String notificationTitle, String notificationBody, String notificationGroupKey, int notificationId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, channelId)
                .setSmallIcon(icon)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setColor(activity.getResources().getColor(R.color.colorPrimary))
                .setStyle(new NotificationCompat.BigTextStyle())
                .setGroup(notificationGroupKey)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(channelDescription);
            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationManagerCompat.from(activity).notify(notificationId, builder.build());
    }

    public PeriodicWorkRequest periodicWorkRequest(Class workerClass, long repeatInterval, String field, String tag) {
        switch (field) {
            case "Hour(s)":
                return new PeriodicWorkRequest.Builder(workerClass, repeatInterval, TimeUnit.HOURS).addTag(tag).build();
            case "Day(s)":
                return new PeriodicWorkRequest.Builder(workerClass, repeatInterval, TimeUnit.DAYS).addTag(tag).build();
            case "Week(s)":
                return new PeriodicWorkRequest.Builder(workerClass, repeatInterval * 7, TimeUnit.DAYS).addTag(tag).build();
            case "Month(s)":
                return new PeriodicWorkRequest.Builder(workerClass, repeatInterval * 30, TimeUnit.DAYS).addTag(tag).build();
            default:
                return new PeriodicWorkRequest.Builder(workerClass, repeatInterval * 365, TimeUnit.DAYS).addTag(tag).build();
        }
    }
}
