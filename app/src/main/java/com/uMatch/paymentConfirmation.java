package com.uMatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class paymentConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        final MyDBHandler dbHandler = new MyDBHandler(paymentConfirmation.this);
        EditText inputNumber = (EditText) findViewById(R.id.inputCCNum);
        EditText inputCVV = (EditText) findViewById(R.id.inputCVV);
        EditText inputValid = (EditText) findViewById(R.id.inputValid);
        EditText inputName = (EditText) findViewById(R.id.inputName);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        Button btnConfirm = (Button) findViewById(R.id.btnPurch);


        String filled = dbHandler.checkDB();

        if(filled.equals("yes")){
            String CC = dbHandler.getCC(1);
            String CVV = dbHandler.getCVV(1);
            if (CC.length() != 0 && CVV.length() != 0){
                inputCVV.setText(CVV);
                inputNumber.setText(CC);
            }
        }


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(inputNumber.length() == 0 || inputCVV.length() == 0 || inputValid.length() == 0 || inputName.length() == 0){
                    Toast.makeText(getApplicationContext(), "Please fill out all the fields in the form", Toast.LENGTH_SHORT).show();
                } else {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "https://umatchumn.000webhostapp.com/inserttransaction.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String status = "success";
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), "Transaksi Berhasil!", Toast.LENGTH_SHORT).show();
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            SharedPreferences sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);
                            String userID = sharedPreferences.getString("id", "");
                            String namaUser = sharedPreferences.getString("name", "");
                            String nimUser = sharedPreferences.getString("nim", "");
                            String creditCardNumber = inputNumber.getText().toString();
                            String cvv = inputCVV.getText().toString();
                            String status = "Confirmed";
                            paramV.put("userID", userID);
                            paramV.put("namaUser", namaUser);
                            paramV.put("nimUser", nimUser);
                            paramV.put("creditCardNumber", creditCardNumber);
                            paramV.put("CVV", cvv);
                            paramV.put("Status", status);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                    String creditCardNumber = inputNumber.getText().toString();
                    String cvv = inputCVV.getText().toString();
                    dbHandler.insertInfo(creditCardNumber, cvv);
                    Intent i = new Intent(getApplicationContext(), landingPayment.class);
                    startActivity(i);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NavBar.class);
                startActivity(i);
            }
        });
    }
}