package austinabell8.sweme.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import austinabell8.sweme.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginButton FBLoginButton;

    private CallbackManager callbackManager;

    ProgressDialog progressDialog;
    private Button mLoginBtn;
    private Button mSignUpBtn;
    private EditText mEmail;
    private EditText mPassword;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private SharedPreferences mPreferences;

    private static final String TAG = "Sweme.LoginActivity";
//    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        FirebaseApp.initializeApp(this);

        LoginManager.getInstance().logOut();
        mFirebaseAuth = FirebaseAuth.getInstance();

        if (isLoggedIn()){
            Intent mainIntent = new Intent (LoginActivity.this, MainActivity.class);
            LoginActivity.this.finish();
            LoginActivity.this.startActivity(mainIntent);
        }

        setContentView(R.layout.activity_login);
        FBLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        mLoginBtn = (Button) findViewById(R.id.login_button);
        mLoginBtn.setOnClickListener(this);
        mSignUpBtn = (Button) findViewById(R.id.sign_up_button);
        mSignUpBtn.setOnClickListener(this);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mLoginBtn.performClick();
                    hideKeyboard(LoginActivity.this);
                    return true;
                }
                return false;
            }
        });

        mPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        mEmail.setText(mPreferences.getString("email", ""));

        // Callback registration
        FBLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d(TAG, "Austin8onFBCallbackSuccess:");
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage(getString(R.string.authenticating));
                progressDialog.setCancelable(false);
                progressDialog.show();

                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "Austin8onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "Austin8onAuthStateChanged:signed_out");
                }

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:

                if (!mEmail.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()){

                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage(getString(R.string.authenticating));
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    mFirebaseAuth.signInWithEmailAndPassword(
                            mEmail.getText().toString(), mPassword.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        SharedPreferences.Editor prefEditor = mPreferences.edit();
                                        prefEditor.putString("email", mEmail.getText().toString());
                                        prefEditor.apply();
                                        Intent mainIntent = new Intent (LoginActivity.this, MainActivity.class);
                                        LoginActivity.this.finish();
                                        LoginActivity.this.startActivity(mainIntent);
                                        progressDialog.hide();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        progressDialog.hide();
                                    }

                                }
                            });
                }

                break;
            case R.id.sign_up_button:
                if (!mEmail.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()){
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage(getString(R.string.creating_user));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    mFirebaseAuth.createUserWithEmailAndPassword(
                            mEmail.getText().toString(), mPassword.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        SharedPreferences.Editor prefEditor = mPreferences.edit();
                                        prefEditor.putString("email", mEmail.getText().toString());
                                        prefEditor.apply();
                                        progressDialog.hide();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        progressDialog.hide();
                                    }
                                }

                            });
                }
                break;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
        if (progressDialog != null) {
            //If process is cancelled by leaving app, be able to cancel dialog
            progressDialog.setCancelable(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "Austin8handleFacebookStart:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Austin8handleFacebookSuccess:success");
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            progressDialog.hide();
                            Intent mainIntent = new Intent (LoginActivity.this, MainActivity.class);
                            LoginActivity.this.finish();
                            LoginActivity.this.startActivity(mainIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Austin8signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            LoginManager.getInstance().logOut();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            progressDialog.hide();
                        }

                        // ...
                    }
                });
    }

    public boolean isLoggedIn() {
        return mFirebaseAuth.getCurrentUser() != null;
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        return accessToken != null;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
