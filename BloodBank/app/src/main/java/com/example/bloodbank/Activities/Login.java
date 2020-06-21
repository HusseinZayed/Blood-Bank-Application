package com.example.bloodbank.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bloodbank.R;
import com.example.bloodbank.Utils.EndPoint;
import com.example.bloodbank.Utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText txtPhone , txtPassword ;
    private Button login;
    private TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtPhone = findViewById(R.id.phone);
        txtPassword = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.link_signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String number = txtPhone.getText().toString();
             String password = txtPassword.getText().toString();
             if(isVaild(number,password)) {
                 login(number,password);
             }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(Login.this,Register.class));
            }
        });
    }

    private boolean isVaild(String phone , String password)
    {
        if (phone.length()!=11) {
            showMessage("phone must is 11 digit");
            return false;
        } else if (password.isEmpty()) {
            showMessage("password is empty");
            return false;
        }
      return true;
    }

    private void showMessage(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void login(final String number ,final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoint.url_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("incorrect data")) {
                            showMessage(response);
                            startActivity(new Intent(Login.this, MainActivity.class));
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                                    .putString("number",number).apply();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                                    .putString("city",response).apply();
                            Login.this.finish();
                        }
                        else{
                            showMessage("wrong response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT)
                                .show();
                        Log.d("VOLLEY", volleyError.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("number", number);  // key must same in php ($_POST[key])
                params.put("password", password);
                return params;
            }};
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
