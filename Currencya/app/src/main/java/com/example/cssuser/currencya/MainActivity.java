package com.example.cssuser.currencya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //private static final Object JsonObjectRequest = null;
    String url="https://api.exchangeratesapi.io/latest";
    RequestQueue rq;
    String rates;

    EditText nameTxt;
    EditText inputTxt;

    TextView resultTxt;

    String[] name;
    String[] value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





    rq = Volley.newRequestQueue(this);

        sendjsonrequest();

        System.out.println(rates);



//        //////////////////////////////
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("https://api.exchangeratesapi.io/latest")
//                .build();
//
//
//        try {
//            response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        /////////////////////////////
//
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//
//            }
//
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//
//            public void onResponse(Call call, final Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    throw new IOException("Unexpected code " + response);
//                } else {
//                    // do something wih the result
//                    System.out.println(response);
//                }
//            }
//
//    });

    }

    public void sendjsonrequest(){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String[] list=new String[200];
                    name=new String[200];
                    value=new String[200];
                  //  rates = response.getString("rates").split(",");
                    //response.getJSONArray()
                    list = response.getString("rates").split(",");

//                    JSONArray jsonArray=response.getJSONArray("rates");
//                    for (int i=0;i<jsonArray.length();i++){
//                        JSONObject currny=jsonArray.getJSONObject(i);
//
//                        System.out.println(currny);
                    list[0]=list[0].replace("{","");
                    list[list.length-1]=list[list.length-1].replace("}","");

                    //System.out.println(list[0]);

                    for (int i=0;i<list.length;i++){
                       // System.out.println(list[i]);
                        name[i]=list[i].split(":")[0].replace("\"","");

                        value[i]=list[i].split(":")[1];

                        System.out.println(name[i]+"   ");
                        System.out.println(value[i]);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        rq.add(jsonObjectRequest);

    }

    public void convert(View view) {

        nameTxt=findViewById(R.id.nameTxt);
        inputTxt=findViewById(R.id.InputValueTxt);

        resultTxt=findViewById(R.id.resultTxt);

        String inputValue=inputTxt.getText().toString();
        String inputCurrency=nameTxt.getText().toString();

        int detectedIndex = 0;

        for (int i=0;i<name.length;i++){

            if(inputCurrency.equals(name[i])){

                detectedIndex=i;
                System.out.println(name[i]);
                break;

            }
        }

        System.out.println("Cuucy Array length "+name.length+"  "+inputCurrency);
        System.out.println("Detected Index "+detectedIndex);

        double outPutResult=Double.parseDouble(value[detectedIndex])*Double.parseDouble(inputValue);

        resultTxt.setText(outPutResult+"");


    }
}


