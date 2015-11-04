package com.ksn.amatorfutboltv.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ksn.amatorfutboltv.base.StarterApplication;
import com.ksn.amatorfutboltv.modal.Modal;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public StarterApplication getApp(){
        return ((StarterApplication)getApplicationContext());
    }

    public Modal getModal(){
        return getApp().getModal();
    }
}
