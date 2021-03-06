package org.michiganhackers.michiganhackers.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.michiganhackers.michiganhackers.MainActivity;
import org.michiganhackers.michiganhackers.R;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private Button btnLogin, btnSignUp;
    private TextInputLayout textInputEmail, textInputPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_sign_up);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_pwd);
        textInputEmail = findViewById(R.id.text_input_email);
        textInputPassword = findViewById(R.id.text_input_password);

        progressBar = findViewById(R.id.progress_bar);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                Boolean warningShown = false;
                if (TextUtils.isEmpty(email)) {
                    textInputEmail.setError(getString(R.string.enter_email));
                    warningShown = true;
                } else {
                    textInputEmail.setError(null);
                }

                if (password.length() < 8) {
                    textInputPassword.setError(getString(R.string.pwd_too_short));
                    warningShown = true;
                } else {
                    textInputPassword.setError(null);
                }

                if (warningShown) {
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Exception exception = task.getException();
                                    String msg = exception == null ? "" : ": " + exception.getLocalizedMessage();
                                    Snackbar.make(coordinatorLayout, getString(R.string.auth_failed_signup) + msg, Snackbar.LENGTH_LONG).show();
                                } else {
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    setResult(Activity.RESULT_OK);
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
