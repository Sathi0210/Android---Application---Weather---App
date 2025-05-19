package com.weather_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {

    EditText citynametxt;
    Button clickmebtn;
    TextView temptxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        citynametxt = (EditText) findViewById(R.id.citynametxt);
        clickmebtn = (Button) findViewById(R.id.clickmebtn);
        temptxt = (TextView) findViewById(R.id.temptxt);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);


        clickmebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cname = citynametxt.getText().toString();
                String url = "https://api.openweathermap.org/data/2.5/weather?q="+cname+"&appid=17979f41718e81f87596320b69c4ea01&units=metric";
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONObject main = jsonObject.getJSONObject("main");
                                    temptxt.setText("Todays's Temperature " + main.getString("temp"));
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        temptxt.setText("That didn't work!");
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);

                temptxt.setText("City Name: "+cname);
            }
        });
    }
}