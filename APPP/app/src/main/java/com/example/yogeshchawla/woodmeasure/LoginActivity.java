package com.example.yogeshchawla.woodmeasure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.*;

public class LoginActivity extends AppCompatActivity {
Button btnlogin,btnskip;
EditText etip,etport;
    String oldnew;

    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rg=(RadioGroup) findViewById(R.id.oldnew);
        btnlogin=(Button)findViewById(R.id.btnlogin);
        btnskip=(Button)findViewById(R.id.btnskip);
        etip=(EditText)findViewById(R.id.etip);
        etport=(EditText)findViewById(R.id.etport);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipport = "http://"+etip.getText().toString()+":"+etport.getText().toString()+"/WoodApplication";
                SharedPreferences sp = getSharedPreferences("ipfile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("ip",ipport);
                editor.commit();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                getChoice();
                i.putExtra("no",oldnew);
                startActivity(i);
            }
        });
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                getChoice();
                i.putExtra("no",oldnew);
                startActivity(i);
            }
        });
    }
    public void getChoice()
    {
        int rbid=rg.getCheckedRadioButtonId();
        RadioButton rb=(RadioButton)findViewById(rbid);
        oldnew=rb.getText().toString();
    }


    public void onBackPressed()
    {

     /*   Log.d("60","background");
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);*/
    }

}



