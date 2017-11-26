package com.demoapp.keane.demojson;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainActivity extends Activity {
    private static Logger log = LoggerFactory.getLogger(MainActivity.class);
    RelativeLayout relativeLayout;
    Button button;
    TextView cityName;
    TextView resultTextView;
    String appid = "d6b5f0a6c8b4137aaea7d9c5bd2c9bad";//not use please cretae myself

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        button = (Button) findViewById(R.id.button);
        cityName = (TextView) findViewById(R.id.cityName);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                Toast.makeText(getApplicationContext(),"Could not find werther", Toast.LENGTH_LONG);

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//object content :
//            {"coord":{"lon":-0.13,"lat":51.51},
//                "weather":[{"id":802, "main":"Clouds","description":"scattered clouds","icon":"03d"}],
//                "base":"stations",
//                    "main":{"temp":278.95,"pressure":1018,"humidity":70,"temp_min":278.15,"temp_max":280.15},
//                "visibility":10000,"wind":{"speed":4.6,"deg":280},"clouds":{"all":40},"dt":1511623200,"sys":{"type":1,"id":5093,"message":0.0069,"country":"GB","sunrise":1511595370,"sunset":1511625535},"id":2643743,"name":"London","cod":200}
            try {
                if (!"".equals(result) ) {
                    JSONObject jsonObject = new JSONObject(result);

                    //get Json是否含有"weather"字串
                    //[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}]
                    String weatherInfo = jsonObject.getString("weather");

                    Log.i("Weather content", weatherInfo);

                    //將weather 底下的這一段轉成陣列
                    //[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}]
                    JSONArray arr = new JSONArray(weatherInfo);
                    StringBuilder sb = new StringBuilder(120);
                    for (int i = 0; i < arr.length(); i++) {

                        JSONObject jsonPart = arr.getJSONObject(i);
                        log.debug("main = "+jsonPart.getString("main"));
                        log.debug("description + "+ jsonPart.getString("description"));
                        sb.append("main =").append(jsonPart.getString("main")).append("\n");
                        sb.append("description = ").append(jsonPart.getString("description")).append("\r\n");
                    }

                    if(!"".equals(sb.toString())){
                        resultTextView.setText(sb.toString());
                    }else{
                        Toast.makeText(getApplicationContext(),"Could not find werther", Toast.LENGTH_LONG);
                    }
                } else {
                    log.debug("result = null");
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Could not find werther", Toast.LENGTH_LONG);
                e.printStackTrace();
            }
        }
    }

    public void findWeather(View v) {
        log.debug("cityName = " + cityName.getText().toString());

        //關android原生的鍵盤
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        StringBuilder url = new StringBuilder(60);
        url.append("http://api.openweathermap.org/data/2.5/weather?q="); //London,uk&appid=" + apikey;
        try {
            //防止出現space空格之類的，轉成UTF-8 格式 EG:san△francisco ==> urlsan20%francisco
            url.append(URLEncoder.encode(cityName.getText().toString(), "UTF-8"));
            url.append("&appid=").append(appid);
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

