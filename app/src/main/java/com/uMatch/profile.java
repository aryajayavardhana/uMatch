package com.uMatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class profile extends Fragment {
    TextView textViewname, path1;
    Button buttonprof, buttonlogout, buttonabout, buttontutor;
    SharedPreferences sharedPreferences;
    ImageView imageView1;
    Bitmap bitmap;


    public profile(){
        // require a empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewname = (TextView) getView().findViewById(R.id.txtNama);
        buttonprof = getActivity().findViewById(R.id.btnprof);
        buttonlogout = getActivity().findViewById(R.id.logout);
        buttonabout = getActivity().findViewById(R.id.about);
        buttontutor = getActivity().findViewById(R.id.btntutor);
        imageView1 = getActivity().findViewById(R.id.gambarprol);
        //path1 = getActivity().findViewById(R.id.path);
        sharedPreferences = getActivity().getSharedPreferences("MyAppName", Context.MODE_PRIVATE);

        textViewname.setText(sharedPreferences.getString("name",""));
        //path1.setText(sharedPreferences.getString("path",""));

        String imgurl = "https://umatchumn.000webhostapp.com/" + sharedPreferences.getString("path", "");
        //String imgurl = "http://10.239.173.90/new%20folder/" + sharedPreferences.getString("path", "");
        new fetchImage(imgurl).start();


        buttonprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SettingProfile.class));
            }
        });

        buttontutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Tutorial.class));
            }
        });

        buttonlogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RequestQueue queue = Volley.newRequestQueue(getContext());
            //String url = "http://10.239.173.90/new%20folder/logout.php"; //Balifiber
            String url ="https://umatchumn.000webhostapp.com/logout.php";
           // String url = "http://172.21.87.28/new%20folder/logout.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("success")) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("logged", "");
                                editor.putString("name", "");
                                editor.putString("email", "");
                                editor.putString("apiKey", "");
                                editor.apply();
                                startActivity(new Intent(getContext(),MainActivity.class));
                            }
                            else{
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }){
                protected Map<String, String> getParams(){
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("email", sharedPreferences.getString("email",""));
                    paramV.put("apiKey", sharedPreferences.getString("apiKey",""));
                    return paramV;
                }
            };
            queue.add(stringRequest);
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

            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bitmap != null) {
                        imageView1.setImageBitmap(bitmap);
                    }
                }
            });
        }

    }





}