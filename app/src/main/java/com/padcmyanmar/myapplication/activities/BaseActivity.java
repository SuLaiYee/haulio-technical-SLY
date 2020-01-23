package com.padcmyanmar.myapplication.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.padcmyanmar.myapplication.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected GoogleSignInClient mGoogleSignInClient;

    protected FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("62178072212-3ijrcv5difi8chuo3g34jl298jmnc3ud.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //new style
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.signing_in));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void firebaseSignOut(){
        mFirebaseAuth.signOut();
    }

    public void googleSignOut(){
        mGoogleSignInClient.signOut();
    }

}
