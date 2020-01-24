package com.padcmyanmar.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.padcmyanmar.myapplication.HaulioPOCApp;
import com.padcmyanmar.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.btn_sign_up_with_google)
    Button btnSignupWithGoogle;

    protected static final int RC_GOOGLE_SIGN_IN = 1236;

    private FirebaseUser mFirebaseUser;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AuthActivity.class);
        // clearing previous activities from stack
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this, this);

        if (isUserSignIn()) {
            navigateToJobListUI(mFirebaseUser.getDisplayName(), String.valueOf(mFirebaseUser.getPhotoUrl()));
        }

        btnSignupWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();

            }
        });
    }

    private void navigateToJobListUI(String truckDriverName, String truckDriverPhoto) {
        Intent intent = MainActivity.newIntent(getBaseContext(), truckDriverName, truckDriverPhoto);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "onConnectionFailed : " + connectionResult.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            Log.d("status code","Fail"+statusCode);
            processGoogleSignInResult(result);
        }
    }

    private void processGoogleSignInResult(GoogleSignInResult signInResult) {
        if (signInResult.isSuccess()) {
            // Google Sign-In was successful, authenticate with Firebase
            GoogleSignInAccount account = signInResult.getSignInAccount();
            authenticateUserWithGoogleAccount(account);


        } else {
            // Google Sign-In failed
            Log.e(HaulioPOCApp.LOG_TAG, "Google Sign-In failed.");
//            Snackbar.make(rvNewsFeed, "Your Google sign-in failed.", Snackbar.LENGTH_LONG).show();

        }
    }

    public boolean isUserSignIn() {
        showProgressDialog();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        boolean isSignIn = mFirebaseUser != null;
        hideProgressDialog();
        return isSignIn;
    }

    public void signInGoogle() {
        signInWithGoogle();
    }

    public void authenticateUserWithGoogleAccount(final GoogleSignInAccount signInAccount) {
        Log.d(HaulioPOCApp.LOG_TAG, "signInAccount Id :" + signInAccount.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(HaulioPOCApp.LOG_TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(HaulioPOCApp.LOG_TAG, "signInWithCredential", task.getException());
//                            delegate.onFailureAuthenticate(task.getException().getMessage());
                        } else {
                            Log.d(HaulioPOCApp.LOG_TAG, "signInWithCredential - successful");
//                            delegate.onSuccessAuthenticate(signInAccount);
                            navigateToJobListUI(signInAccount.getDisplayName(), String.valueOf(signInAccount.getPhotoUrl()));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(HaulioPOCApp.LOG_TAG, "OnFailureListener : " + e.getMessage());
//                        delegate.onFailureAuthenticate(e.getMessage());
                    }
                });
    }
}
