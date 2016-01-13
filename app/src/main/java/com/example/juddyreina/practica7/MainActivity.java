package com.example.juddyreina.practica7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private Button BNext;
    private Button BLogin;
    private Button BG;
    private EditText name;
    private EditText passw;

    //FACEBOOK
    private CallbackManager cbM;
    private AccessTokenTracker atT;
    private ProfileTracker pt;
    //GOOGLE
    private static final String TAG="MainActivity";
    private static final int RC_SIGN_IN=9001;
    private GoogleApiClient mGoogleApiClient;
    private boolean flag=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        findViewById(R.id.bGoogle).setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        BG = (Button) findViewById(R.id.bGoogle);
        LoginButton loginButton = (LoginButton) findViewById(R.id.bFB);
        name=(EditText) findViewById(R.id.etName);
        passw=(EditText) findViewById(R.id.etPassw);
        BNext = (Button) findViewById(R.id.bRegistro);
        BLogin = (Button) findViewById(R.id.bIngresar);
        cbM = CallbackManager.Factory.create();
        atT = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            }
        };
        pt = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
            }
        };
        atT.startTracking();
        pt.startTracking();
        //VER SI YA SE HA INICIADO SESION CON FACEBOOK
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if( accessToken != null ){
            Intent i = new Intent(MainActivity.this, ultima.class);
            startActivity(i);
        }

        loginButton.registerCallback(cbM, callback);
        BNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, registro.class);
                startActivity(i);
            }
        });

        BLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n,p;
                n=name.getText().toString();
                p=passw.getText().toString();



            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cbM.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if(result.isSuccess()){
            BG.setText(R.string.SO);
            GoogleSignInAccount acct=result.getSignInAccount();
            //ABRIR LA NUEVA ACTIVIDAD DESPUES DE QUE SE HA LOGUEADO CON GOOGLE
            Intent i = new Intent(MainActivity.this, ultima.class);
            i.putExtra("nombre",getString(R.string.signed_int_fmt,acct.getDisplayName()));
            startActivity(i);
        }else{
            BG.setText(R.string.google);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        atT.stopTracking();
        pt.stopTracking();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
    }

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken at = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            //ABRIR LA NUEVA ACTIVIDAD DESPUES DE QUE SE HA LOGUEADO CON FACEBOOK
            Intent i = new Intent(MainActivity.this, ultima.class);
            startActivity(i);

        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException error) {
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bGoogle:
                if(flag){
                    flag=false;
                    BG.setText(R.string.SO);
                    singIn();
                }else{
                    flag=true;
                    BG.setText(R.string.google);
                    singOut();
                }
                break;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG,"onConnectionFailed:"+ connectionResult);
    }

    public void singIn(){
        Intent singInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(singInIntent,RC_SIGN_IN);
    }

    public void singOut(){
        BG.setText(R.string.google);
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                }
        );
    }

}
