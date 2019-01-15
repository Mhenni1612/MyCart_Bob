package com.example.wael.mycart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    //private ImageView mImageView;
    private EditText mEditTextEmail;
    private EditText mEditTextPasword;
    private Button   mButtonRegister;
    private TextView mTExtViewSignIn;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPasword = (EditText) findViewById(R.id.editTextPassword);
        mButtonRegister = (Button) findViewById(R.id.buttonRegister);
        mTExtViewSignIn = (TextView) findViewById(R.id.textViewSingin);
        //mImageView = (ImageView) findViewById(R.id.imageViewLogo);

        //mImageView.setImageResource(R.drawable.logo);
        mButtonRegister.setOnClickListener(this);
        mTExtViewSignIn.setOnClickListener(this);



        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser(){
        String email = mEditTextEmail.getText().toString().trim();
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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(RegisterActivity.this, "RegisterActivity Successfuly", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "vérifier votre email", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
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
        if(v == mButtonRegister){
            registerUser();
        }
        if(v == mTExtViewSignIn){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
