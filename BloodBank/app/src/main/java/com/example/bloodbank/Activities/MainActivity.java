package com.example.bloodbank.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.bloodbank.Adapters.AdapterRequest;
import com.example.bloodbank.Models.RequestDataModel;
import com.example.bloodbank.R;
import com.example.bloodbank.Utils.EndPoint;
import com.example.bloodbank.Utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AdapterRequest adapter;
    List<RequestDataModel> myData;
    List<RequestDataModel> myDataList;
    TextView request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        startActivity(new Intent(MainActivity.this,Search1.class));
                        return true;
                }
                return false;
            }
        });


        request= findViewById(R.id.request);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(MainActivity.this,MakeRequest.class));
            }
        });
        myDataList = new ArrayList<>() ;
        myData = new ArrayList<>();

        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterRequest(myDataList,this);
        recyclerView.setAdapter(adapter);
        addData();



    }

    private void addData()
    {
       final String city = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("city","no_city");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoint.url_get_data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      Gson gson = new Gson();
                      Type type = new TypeToken<List<RequestDataModel>>(){}.getType();
                      myData = gson.fromJson(response,type);
                      myDataList.addAll(myData);
                      adapter.notifyDataSetChanged();
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
                return params;
            }};
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
            }


}
