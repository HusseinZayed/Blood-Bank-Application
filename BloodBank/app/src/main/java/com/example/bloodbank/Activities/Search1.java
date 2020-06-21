package com.example.bloodbank.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

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
import com.example.bloodbank.Models.RequestDataModel;
import com.example.bloodbank.R;
import com.example.bloodbank.Utils.EndPoint;
import com.example.bloodbank.Utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search1 extends AppCompatActivity {

    EditText edit_city ,edit_type;
    Button btn_find;
    String city ,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search1);
        edit_city =findViewById(R.id.city_search);
        edit_type =findViewById(R.id.bloodGroup_search);
        btn_find = findViewById(R.id.btn_donor);

        city = edit_city.getText().toString();
        type = edit_type.getText().toString();
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid(city,type)){
                    getDataSearch(city,type);
                }
            }
        });

    }


    //---------------------------------------------------------------
    private boolean isValid( String city, String bloodGroup) {
        List<String> group = new ArrayList<>();
        group.add("A+");
        group.add("A-");
        group.add("B+");
        group.add("B-");
        group.add("AB+");
        group.add("AB-");
        group.add("O+");
        group.add("O-");
       if (city.isEmpty()) {
            showData("city is empty");
            return false;
        }  else if (!group.contains(bloodGroup)) {
            showData("the letter must is Capital and vaild");
            return false;
        }

        return true;
    }

    private void showData(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void getDataSearch(final String city,final String type){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoint.url_get_data_search,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                params.put("city",city);
                params.put("blood_group",type);
                return params;
            }};
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}
