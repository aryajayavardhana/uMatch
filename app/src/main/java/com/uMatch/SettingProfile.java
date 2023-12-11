package com.uMatch;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SettingProfile extends AppCompatActivity {
    TextView textViewemail, textViewgender, textViewnim, textViewjurusan, path1, textViewerror;
    EditText editTextname, editTextphone;
    SharedPreferences sharedPreferences;
    ImageButton buttonback;
    Button buttonsave;
    String name, phone,email;
    ImageView imageView;
    Bitmap bitmap;
    String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);
        editTextname = findViewById(R.id.txtNama);
        textViewemail = findViewById(R.id.email);
        editTextphone = findViewById(R.id.phone);
        textViewgender = findViewById(R.id.gender);
        textViewjurusan = findViewById(R.id.txtJurusan);
        textViewnim = findViewById(R.id.nim);
        buttonsave = findViewById(R.id.save);
        buttonback = findViewById(R.id.back);
        imageView = findViewById(R.id.gambarprofile);
       // path1 = findViewById(R.id.path);
        textViewerror = findViewById(R.id.error);
        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        editTextname.setText(sharedPreferences.getString("name",""));
        textViewemail.setText(sharedPreferences.getString("email",""));
        editTextphone.setText(sharedPreferences.getString("phone",""));
        textViewgender.setText(sharedPreferences.getString("gender",""));
        textViewnim.setText(sharedPreferences.getString("nim",""));
        textViewjurusan.setText(sharedPreferences.getString("jurusan",""));
      //  path1.setText(sharedPreferences.getString("path",""));


        String imgurl = "https://umatchumn.000webhostapp.com/" + sharedPreferences.getString("path", "");
        //String imgurl = "http://10.239.173.90/new%20folder/" + sharedPreferences.getString("path", "");
       new fetchImage(imgurl).start();


        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NavBar.class);
                startActivity(intent);
                finish();
            }
        });


        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                imageView.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });

        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = textViewemail.getText().toString();
                name = editTextname.getText().toString();
                phone = editTextphone.getText().toString();
                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if(bitmap != null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    final String base64image = Base64.encodeToString(bytes, Base64.DEFAULT);


                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    //String url = "http://10.239.173.90/new%20folder/update.php"; //Balifiber
                    String url = "http://192.168.1.10/new%20folder/update.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                try {
                                     JSONObject jsonObject = new JSONObject(response);
                                     String status = jsonObject.getString("status");
                                     String message = jsonObject.getString("message");
                                        if(status.equals("success")){
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("logged", "true");
                                            editor.putString("name", name);
                                            editor.putString("phone", phone);
                                            editor.putString("path", jsonObject.getString("path"));
                                            editor.apply();
                                            path = jsonObject.getString("path");
                                            Intent intent = new Intent(getApplicationContext(), NavBar.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
                                            textViewerror.setText(message);
                                    textViewerror.setVisibility(View.VISIBLE);
                                    }catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            textViewerror.setText(error.getLocalizedMessage());
                            textViewerror.setVisibility(View.VISIBLE);
                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("image",  base64image);
                            paramV.put("email", email);
                            paramV.put("phone", phone);
                            paramV.put("name",name);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
                else Toast.makeText(getApplicationContext(), "Select the image first", Toast.LENGTH_SHORT).show();
            }
        });
    }


    class fetchImage extends Thread {

        String URL;

        fetchImage(String URL) {
            this.URL = URL;
        }

        @Override
        public void run() {

            InputStream inputStream;

            try {
                inputStream = new java.net.URL(URL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            });
        }
    }
}