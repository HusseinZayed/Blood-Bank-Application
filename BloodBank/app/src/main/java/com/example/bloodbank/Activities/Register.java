package com.example.bloodbank.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bloodbank.R;
import com.example.bloodbank.Utils.EndPoint;
import com.example.bloodbank.Utils.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Register extends AppCompatActivity {

    private EditText txt_name , txt_city , txt_numberPhone , txt_bloodGroup , txt_password;
    private Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_name = findViewById(R.id.name);
        txt_city = findViewById(R.id.city);
        txt_numberPhone = findViewById(R.id.mobile);
        txt_bloodGroup = findViewById(R.id.bloodGroup);
        txt_password = findViewById(R.id.input_password);
        signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          final String name, city, number, bloodGroup, password;
                                          name = txt_name.getText().toString();
                                          city = txt_city.getText().toString();
                                          number = txt_numberPhone.getText().toString();
                                          bloodGroup = txt_bloodGroup.getText().toString();
                                          password = txt_password.getText().toString();

                                          if (isValid(name, city, number, bloodGroup, password)) {
                                              register(name,city,number,bloodGroup,password);
                                          }
                                      }
        });}
            //--------------------------------------------------------
            private void register(final String name, final String city, final String number, final String bloodGroup, final String password) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoint.url_register,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")) {
                                    showData(response);
                                    startActivity(new Intent(Register.this, MainActivity.class));
                                    Register.this.finish();
                                }
                                else{
                                    showData("wrong response");
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
                        params.put("name", name);  // key must same in php ($_POST[key])
                        params.put("city", city);
                        params.put("number", number);
                        params.put("blood_group", bloodGroup);
                        params.put("password", password);
                        return params;
                    }

                };
                VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
            }


            //---------------------------------------------------------------
            private boolean isValid(String name, String city, String number, String bloodGroup, String password) {
                List<String> group = new ArrayList<>();
                group.add("A+");
                group.add("A-");
                group.add("B+");
                group.add("B-");
                group.add("AB+");
                group.add("AB-");
                group.add("O+");
                group.add("O-");
                if (name.isEmpty()) {
                    showData("name is empty");
                    return false;
                } else if (city.isEmpty()) {
                    showData("city is empty");
                    return false;
                } else if (number.length() != 11) {
                    showData("number must is 11 digit");
                    return false;
                } else if (password.isEmpty()) {
                    showData("password is empty");
                    return false;
                } else if (!group.contains(bloodGroup)) {
                    showData("the letter must is Capital and vaild");
                    return false;
                }

                return true;
            }

            private void showData(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }

        }