package com.lesley.engelsimmanuel.fishfarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ImageButton goToSignUp;
    private TextInputEditText email;
    private TextInputEditText password;
    private Button signIn;
    private TextView forgotPassword;
    private final NecessaryEvil necessaryEvil = new NecessaryEvil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();
        goToSignUp = findViewById(R.id.sign_in_go_to_sign_up);
        email = findViewById(R.id.sign_in_email);
        password = findViewById(R.id.sign_in_password);
        signIn = findViewById(R.id.sign_in_button);
        forgotPassword = findViewById(R.id.sign_in_forgot_password);

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {
                    authenticateUser(email.getText().toString(), password.getText().toString());
                } else {
                    Snackbar.make(view, getString(R.string.empty_fields), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.unimplemented), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void authenticateUser(String userEmail, String userPassword) {
        final Dialog d = new Dialog(SignInActivity.this);
        necessaryEvil.showLoadingDialog(d, getString(R.string.authenticating));

        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnSuccessListener(SignInActivity.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                d.dismiss();
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            }
        }).addOnFailureListener(SignInActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                d.dismiss();
                Snackbar.make(findViewById(R.id.sign_in_root_layout), e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}