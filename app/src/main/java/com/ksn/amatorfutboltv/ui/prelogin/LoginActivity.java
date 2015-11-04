package com.ksn.amatorfutboltv.ui.prelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ksn.amatorfutboltv.R;
import com.ksn.amatorfutboltv.ui.browsing.MainActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText input_username;
    private EditText input_password;
    private Button  btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prelogin_activity_login);
        input_username = (EditText)findViewById(R.id.input_username);
        input_password = (EditText)findViewById(R.id.input_password);
        btnLogin= (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    private void checkAndLogin(){
        if(input_username.getText().toString().equals("")){
            input_username.setError("Kullanıcı adı boş bırakılamaz");
            return;
        }

        if(input_password.getText().toString().equals("")){
            input_username.setError("Şifre boş bırakılamaz");
            return;
        }

        ParseUser.logInInBackground(input_username.getText().toString(), input_password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e!=null){
                    return;
                }

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                checkAndLogin();
                break;
        }
    }
}
