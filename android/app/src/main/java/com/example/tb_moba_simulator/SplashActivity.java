/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tb_moba_simulator.objects.Game;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email_field, pass_field;
    private Button login_button;
    private TextView error_text;
    private static final String TAG = "SplashActivity";
    private String email, pass;
    private View thisView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseManager.initFirebaseAuth();
        mAuth = FirebaseManager.mAuth;
        FirebaseManager.initFireStore();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        initEditText();
        initButtons();
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            GameManager.loadDefaultConfiguration();
            System.out.println("User : " + currentUser.getEmail() + " has been logged in!");
            Intent loadMenu = new Intent(SplashActivity.this, MenuActivity.class);
            startActivity(loadMenu);
        }
    }
    private void initEditText(){
        email_field = findViewById(R.id.login_email);
        pass_field = findViewById(R.id.login_pass);
        error_text = findViewById(R.id.login_error);
        email_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                View view = v;
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        pass_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                View view = v;
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }
    private void initButtons(){
        thisView = this.getCurrentFocus();
        login_button = findViewById(R.id.login_button2);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_field.getText().toString();
                pass = pass_field.getText().toString();
                if(email.length() == 0 || pass.length() == 0) {
                    error_text.setText("Neither email or password can be empty!");
                } else {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                updateUI(mAuth.getCurrentUser());
                            } else {
                                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            updateUI(mAuth.getCurrentUser());
                                        } else {
                                            error_text.setText(task.getException().getMessage());
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
           }
        } );
    }

    @Override
    public void onBackPressed() { }
}
