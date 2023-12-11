package com.uMatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class LogIn extends AppCompatActivity {

    EditText editTextemail, editTextpassword;
    Button buttonsubmit;
    String name, email, password, apiKey, gender, phone, nim, jurusan, path;
    TextView textViewerror;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextemail = findViewById(R.id.email);
        editTextpassword = findViewById(R.id.password);
        buttonsubmit = findViewById(R.id.submit);
        textViewerror = findViewById(R.id.error);
        progressBar = findViewById(R.id.progressBar);
        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        ImageButton logoHidden = (ImageButton) findViewById(R.id.logoHidden);

        logoHidden.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        editTextpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        editTextpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;
            }
        });



        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewerror.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                email = String.valueOf(editTextemail.getText());
                password = String.valueOf(editTextpassword.getText());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                //String url = "http://10.239.173.90/new%20folder/login.php"; //Balifiber
                //String url = "http://192.168.1.10/new%20folder/login.php";
                //String url = "http://172.21.61.150/new%20folder/login.php";
                String url ="https://umatchumn.000webhostapp.com/login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");
                                    if(status.equals("success")){
                                        String id = jsonObject.getString("id");
                                        name = jsonObject.getString("name");
                                        email = jsonObject.getString("email");
                                        nim = jsonObject.getString("nim");
                                        gender = jsonObject.getString("gender");
                                        String type = jsonObject.getString("Type");
                                        phone = jsonObject.getString("phone");
                                        jurusan = jsonObject.getString("jurusan");
                                        apiKey = jsonObject.getString("apiKey");
                                        path = jsonObject.getString("path");
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("logged", "true");
                                        editor.putString("id", id);
                                        editor.putString("name", name);
                                        editor.putString("email", email);
                                        editor.putString("nim", nim);
                                        editor.putString("gender", gender);
                                        editor.putString("type", type);
                                        editor.putString("phone", phone);
                                        editor.putString("jurusan", jurusan);
                                        editor.putString("apiKey", apiKey);
                                        editor.putString("path", path);
                                        editor.apply();
                                        Intent intent= new Intent(getApplicationContext(), NavBar.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        textViewerror.setText(message);
                                        textViewerror.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        textViewerror.setText(error.getLocalizedMessage());
                        textViewerror.setVisibility(View.VISIBLE);
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });

    }
}