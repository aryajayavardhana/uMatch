package com.uMatch;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

class data {
    String id;
    String nama;
    String email;
    String jurusan;
    String nim;
    String phone;
    String gender;
    String type;
    int liked;
}

public class Home extends Fragment {

    TextView textViewname;
    SharedPreferences sharedPreferences;
    ImageView imageView;
    Bitmap bitmap;
    private static final String TAG_DATA = "data";
    private static final String TAG_ID = "id";
    private static final String TAG_NAMA = "nama";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_JURUSAN = "jurusan";
    private static final String TAG_NIM = "nim";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_TYPE = "Type";


    public Home(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        TextView txtnama = view.findViewById(R.id.txtNama);
        TextView txtjurusan = view.findViewById(R.id.txtJurusan);
        TextView txthidden = view.findViewById(R.id.nyimpenID);
        TextView txtemail = view.findViewById(R.id.nyimpenEmail);
        TextView txtType = view.findViewById(R.id.txtType);
        FloatingActionButton btnRight = view.findViewById(R.id.btnRight);
        FloatingActionButton btnLeft = view.findViewById(R.id.btnLeft);
        FloatingActionButton btnLike = view.findViewById(R.id.btnLike);
        String url = "https://umatchumn.000webhostapp.com/getdata.php";

        sharedPreferences = getActivity().getSharedPreferences("MyAppName", MODE_PRIVATE);
        final String stat = sharedPreferences.getString("type","");
        String acc = "";
        if (stat.equals("1")){
            acc = "VIP";
            txtType.setText(acc);
        } else {
            acc = "Basic";
            txtType.setText(acc);
        }

        data[] dataList = new data[25];
        final int[] indexCount = new int[5];
        final int[] Count = new int[5];

        for(int k = 0; k < dataList.length; k++){
            dataList[k] = new data();
        }

        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jobj = new JSONObject(response);
                    JSONArray member = jobj.getJSONArray(TAG_DATA);

                    int i;
                    for (i = 0; i < member.length(); i++) {

                        JSONObject a = member.getJSONObject(i);
                        String id = a.getString(TAG_ID);
                        String nama = a.getString(TAG_NAMA);
                        String email = a.getString(TAG_EMAIL);
                        String nim = a.getString(TAG_NIM);
                        String gender = a.getString(TAG_GENDER);
                        String phone = a.getString(TAG_PHONE);
                        String jurusan = a.getString(TAG_JURUSAN);

                        dataList[i].id = id;
                        dataList[i].nama = nama;
                        dataList[i].email = email;
                        dataList[i].nim = nim;
                        dataList[i].gender = gender;
                        dataList[i].phone = phone;
                        dataList[i].jurusan = jurusan;
                    }

                    final int index = 0;
                    String nama = dataList[index].nama;
                    String jurusan = dataList[index].jurusan;
                    String email = dataList[index].email;
                    String nim = dataList[index].nim;
                    txtnama.setText(nama);
                    txtemail.setText(email);
                    txthidden.setText(nim);
                    txtjurusan.setText(jurusan);
                    indexCount[0] = index;

                    btnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int c = indexCount[0];
                            int count = Count[0];
                            c++;
                            if(dataList[c].id == null){
                                Toast.makeText(getContext().getApplicationContext(), "Theres is no more user to see", Toast.LENGTH_SHORT).show();
                            } else {
                                int max = 0;
                                if (stat.equals("1")){
                                    max = 999;
                                } else {
                                    max = 5;
                                }
                                if (count <= max){
                                    String nim = dataList[c].nim;
                                    String nama = dataList[c].nama;
                                    String email = dataList[c].email;
                                    String jurusan = dataList[c].jurusan;
                                    txthidden.setText(nim);
                                    txtnama.setText(nama);
                                    txtemail.setText(email);
                                    txtjurusan.setText(jurusan);
                                    indexCount[0] = c;
                                    Count[0]++;
                                } else {
                                    Toast.makeText(getContext().getApplicationContext(), "Maximum Swipes Reached", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                    });

                    btnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int j = indexCount[0];
                            j--;
                            int count = Count[0];
                            if(j == -1){
                                Toast.makeText(getContext().getApplicationContext(), "Theres is no more user to see", Toast.LENGTH_SHORT).show();
                            } else {
                                int max = 0;
                                if (stat.equals("1")){
                                    max = 999;
                                } else {
                                    max = 5;
                                }

                                if(count < max){
                                    String nim = dataList[j].nim;
                                    String nama = dataList[j].nama;
                                    String email = dataList[j].email;
                                    String jurusan = dataList[j].jurusan;
                                    txthidden.setText(nim);
                                    txtnama.setText(nama);
                                    txtemail.setText(email);
                                    txtjurusan.setText(jurusan);
                                    indexCount[0] = j;
                                    Count[0]++;
                                } else {
                                    Toast.makeText(getContext().getApplicationContext(), "Maximum Swipes Reached", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                    btnLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnLike.setImageResource(R.drawable.likered);
                            AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                            dialog.setTitle("Confirmation");
                            dialog.setMessage("Do you want to match with this user?");
                            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //sharedPreferences = getActivity().getSharedPreferences("MyAppName", MODE_PRIVATE);
                                    String userSatu = sharedPreferences.getString("name", "");
                                    String userEmailSatu = sharedPreferences.getString("email", "");
                                    String userNIMsatu = sharedPreferences.getString("nim", "");
                                    String userDua = txtnama.getText().toString();
                                    String userNIMdua = txthidden.getText().toString();
                                    String userEmailDua = txtemail.getText().toString();
                                    String status = "liked";
                                    //Toast.makeText(getContext().getApplicationContext(), userNIMsatu + " " + userNIMdua + " " + status, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getActivity().getApplicationContext(), acceptLike.class);
                                    i.putExtra("userSatu", userSatu);
                                    i.putExtra("userNIMsatu", userNIMsatu);
                                    i.putExtra("userEmailsatu", userEmailSatu);
                                    i.putExtra("userDua", userDua);
                                    i.putExtra("userNIMdua", userNIMdua);
                                    i.putExtra("userEmailDua", userEmailDua);
                                    i.putExtra("status", status);
                                    startActivity(i);

                                }
                            });
                            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    btnLike.setImageResource(R.drawable.like);
                                    Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.show();
                        }
                    });
                } catch (Exception ex) {
                    Log.e("Error: ", ex.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(view.getContext(), "Silahkan cek koneksi internet " +
                        "Anda!", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });
        queue.add(stringRequest);

        return view;
    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // textViewname = (TextView) getView().findViewById(R.id.name);
        imageView = getActivity().findViewById(R.id.gambarprofile);

        sharedPreferences = getActivity().getSharedPreferences("MyAppName", MODE_PRIVATE);
       // textViewname.setText(sharedPreferences.getString("name",""));

        String imgurl = sharedPreferences.getString("path", "");
        //String imgurl = "http://10.239.173.90/new%20folder/" + sharedPreferences.getString("path", "");
        new fetchImage(imgurl).start();
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
                        imageView.setImageBitmap(bitmap);
                    }
                }
            });
        }
    }

}