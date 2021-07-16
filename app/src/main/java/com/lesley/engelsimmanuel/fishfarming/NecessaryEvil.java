package com.lesley.engelsimmanuel.fishfarming;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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
                FirebaseAuth.getInstance().signOut();
                currentActivity.startActivity(new Intent(currentActivity, destinationActivityClass));
                currentActivity.finish();
            }
        });
    }

    public void showToast(Context context, String toastMessage) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
    }

    public void log(String TAG, String message) {
        Log.wtf(TAG, message);
    }

}
