package com.lesley.engelsimmanuel.fishfarming;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NecessaryEvil {

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

    public void showToast(Context context, String toastMessage) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
    }

    public void log(String TAG, String message) {
        Log.wtf(TAG, message);
    }

}
