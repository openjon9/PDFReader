package com.coder.pdfreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText ed_account;
    private EditText ed_pass;
    private LoginActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context =this;
        findview();

    }

    private void findview() {
        ed_account = (EditText) findViewById(R.id.ed_account);
        ed_pass = (EditText) findViewById(R.id.ed_pass);
    }


    public void login(View view) {

        String account = ed_account.getText().toString();
        String pass = ed_pass.getText().toString();

//        if (account.equals("") || pass.equals("")) {
//            return;
//        }

        Intent intent =new Intent(context,MainActivity.class);
        startActivity(intent);
        finish();

    }
}
