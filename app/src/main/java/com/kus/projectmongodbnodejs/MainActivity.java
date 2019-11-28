package com.kus.projectmongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.ConfigUrl;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private TextView txtdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtdata = (TextView) findViewById(R.id.txtdata);

        //tambahin ini juga
        mRequestQueue = Volley.newRequestQueue(this);


        fetchJsonResponse();
    }

    //accsessdata buka comman ipconfig copy ip yg di wireless lan adapter
    private void fetchJsonResponse() {
        // Pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ConfigUrl.getAllMahasiswa, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("data");
                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                            Log.v("Ini data dari server," ,result.toString());

                            JSONArray res = new JSONArray(result);
                            for (int i = 0; i < res.length(); i++ ){
                                JSONObject jObj = res.getJSONObject(i);
                                txtdata.append( "Npm = " + jObj.getString("npm") + "\n" +
                                                "Nama = " + jObj.getString("name") + "\n" );
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

}
