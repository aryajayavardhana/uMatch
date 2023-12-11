package com.uMatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class acceptLike extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_like);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url2 = "https://umatchumn.000webhostapp.com/insertmatch.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if(status.equals("success")){
                        Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                    }
                    else
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                //textViewerror.setVisibility(View.VISIBLE);
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                Intent i = getIntent();
                String userSatu = i.getStringExtra("userSatu");
                String userNIMsatu = i.getStringExtra("userNIMsatu");
                String userEmailSatu = i.getStringExtra("userEmailsatu");
                String userDua = i.getStringExtra("userDua");
                String userNIMdua = i.getStringExtra("userNIMdua");
                String userEmailDua = i.getStringExtra("userEmailDua");
                String status = i.getStringExtra("status");
                paramV.put("userSatu", userSatu);
                paramV.put("userNIMsatu", userNIMsatu);
                paramV.put("userEmailSatu", userEmailSatu);
                paramV.put("userDua", userDua);
                paramV.put("userNIMdua", userNIMdua);
                paramV.put("userEmailDua", userEmailDua);
                paramV.put("status", status);
                return paramV;
            }
        };
        queue.add(stringRequest);

        Button btnGoChat = (Button) findViewById(R.id.btnGoChat);
        Button btnGoHome = (Button) findViewById(R.id.btnGoHome);

        btnGoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NavBar.class);
                startActivity(i);
            }
        });

        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NavBar.class);
                startActivity(i);
            }
        });
    }
}