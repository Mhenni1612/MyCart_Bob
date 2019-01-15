package com.example.wael.mycart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private EditText mEditTextEmail;
    private EditText mEditTextPasword;
    private Button mButtonSignIn;
    private TextView mTextViewSignUp;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPasword = (EditText) findViewById(R.id.editTextPassword);
        mButtonSignIn = (Button) findViewById(R.id.buttonSingin);
        mTextViewSignUp = (TextView) findViewById(R.id.textViewSingup);

        mButtonSignIn.setOnClickListener(this);
        mTextViewSignUp.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }
    private void userLogin() throws ExecutionException, InterruptedException {

        final String email = mEditTextEmail.getText().toString().trim();
        String password = mEditTextPasword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "S'il vous plaît donner un  email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "S'il vous plaît donner un  mot de passe", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("S'il vous plaît attendez....");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            Log.d("m","kkkkkkkkkkkkkkkkkk");

                            DocumentReference collLivreur = db.collection("/livreur").document(email);
                            collLivreur.update("disponible",true);


                            Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mIntent);

                            Log.d("m","xxxxxxxxxxxxxxxxxx");
                        }
                    }
                });






    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v == mButtonSignIn){
            try {
                userLogin();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(v == mTextViewSignUp){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
