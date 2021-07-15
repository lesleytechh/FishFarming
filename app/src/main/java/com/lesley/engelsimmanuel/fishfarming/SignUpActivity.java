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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private ImageButton goToSignIn;
    private TextInputEditText name;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText confirmPassword;
    private Button signUp;
    private final NecessaryEvil necessaryEvil = new NecessaryEvil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        goToSignIn = findViewById(R.id.sign_up_go_to_sign_in);
        name = findViewById(R.id.sign_up_name);
        email = findViewById(R.id.sign_up_email);
        password = findViewById(R.id.sign_up_password);
        confirmPassword = findViewById(R.id.sign_up_confirm_password);
        signUp = findViewById(R.id.sign_up_button);

        goToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()) && !TextUtils.isEmpty(confirmPassword.getText().toString())) {
                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                        createUser(name.getText().toString(), email.getText().toString(), password.getText().toString());
                    } else {
                        Snackbar.make(view, getString(R.string.password_mismatch), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(view, getString(R.string.empty_fields), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createUser(final String userName, String userEmail, String userPassword){
        final Dialog d = new Dialog(SignUpActivity.this);
        necessaryEvil.showLoadingDialog(d, getString(R.string.creating_user));

        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnSuccessListener(SignUpActivity.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String, Object> user = new HashMap<>();
                user.put("name", userName);
                user.put("user_id", authResult.getUser().getUid());
                user.put("created_at", FieldValue.serverTimestamp());

                firestore.collection("Users").document(authResult.getUser().getUid()).set(user).addOnSuccessListener(SignUpActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        d.dismiss();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }
        }).addOnFailureListener(SignUpActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                d.dismiss();
                Snackbar.make(findViewById(R.id.sign_up_root_layout), e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}