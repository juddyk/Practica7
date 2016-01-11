package com.example.juddyreina.practica7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class ultima extends AppCompatActivity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_ultima);
        tv= (TextView) findViewById(R.id.text_usr);
        String name=getIntent().getStringExtra("nombre");
        tv.setText(name);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if( accessToken != null ){
            Profile p=Profile.getCurrentProfile();
            String n;
            if(p!=null){
                n=p.getName();
            }
            else{
                n=" ";
            }
            tv.setText(n);
        }




    }
}
