package com.demoapp.keane.demojson;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//public class MainActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        DownTadk task = new DownTadk();
//        task.execute("http://api.openweathermap.org/data/2.5/weather?q=London,uk");
//
//    }
//
//    public class DownTadk extends AsyncTask<String, Void, String> {
//
//        URL url;
//        HttpURLConnection httpURLConnection = null;
//
//        //        @Override
////        protected String doInBackground(String... urls) {
////            try {
////                url = new URL(urls[0]);
////                httpURLConnection = (HttpURLConnection) url.openConnection();
////
////                InputStream in = httpURLConnection.getInputStream();
////                InputStreamReader reader = new InputStreamReader(in);
////
////                int parket = reader.read();
////                String s = "";
////                while (parket != -1) {
////
////                    s += (char) parket;
////                }
////                return s;
////            } catch (MalformedURLException e) {
////                e.printStackTrace();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            return null;
////        }
//        @Override
//        protected String doInBackground(String... urls) {
//
//            String result = "";
//            URL url;
//            HttpURLConnection urlConnection = null;
//
//            try {
//                url = new URL(urls[0]);
//
//                urlConnection = (HttpURLConnection) url.openConnection();
//
//                InputStream in = urlConnection.getInputStream();
//
//                InputStreamReader reader = new InputStreamReader(in);
//
//                int data = reader.read();
//
//                while (data != -1) {
//
//                    char current = (char) data;
//
//                    result += current;
//
//                    data = reader.read();
//
//                }
//
//                return result;
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//
//        //    @Override
////    protected void onPostExecute(String result) {
////        super.onPostExecute(result);
////        try {
////            JSONObject jobject =  new JSONObject(result);
////            String weatherInfo = jobject.getString("")
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////    }
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            try {
//
//                JSONObject jsonObject = new JSONObject(result);
//
//                String weatherInfo = jsonObject.getString("weather");
//
//                Log.i("Weather content", weatherInfo);
//
//                JSONArray arr = new JSONArray(weatherInfo);
//
//                for (int i = 0; i < arr.length(); i++) {
//
//                    JSONObject jsonPart = arr.getJSONObject(i);
//
//                    Log.i("main", jsonPart.getString("main"));
//                    Log.i("description", jsonPart.getString("description"));
//
//                }
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//
//    }
//
//
//}


//package com.example.robpercival.jsondemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.demoapp.keane.demojson.R;

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
import java.util.Iterator;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String apikey = "d6b5f0a6c8b4137aaea7d9c5bd2c9bad";
        String query = null;
//        try {
////            query = URLEncoder.encode("apples oranges", "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=d6b5f0a6c8b4137aaea7d9c5bd2c9bad
        String url = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=" + apikey;
        DownloadTask task = new DownloadTask();
        task.execute("http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=d6b5f0a6c8b4137aaea7d9c5bd2c9bad");

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

//                if(   urlConnection.HTTP_OK ! = true) {
//                    inputStream = urlConnection.getErrorStream();
//                    //Get more informations about the problem
//                } else {
//                    inputStream = urlConnection.getInputStream();
//                }
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);
                urlConnection.setChunkedStreamingMode(0);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

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
                if (result != null && result != "") {
                    JSONObject jsonObject = new JSONObject(result);

                    //get Json是否含有"weather"字串
                    //[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}]
                    String weatherInfo = jsonObject.getString("weather");

                    Log.i("Weather content", weatherInfo);

                    //將weather 底下的這一段轉成陣列
                    //[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}]
                    JSONArray arr = new JSONArray(weatherInfo);

                    for (int i = 0; i < arr.length(); i++) {

                        JSONObject jsonPart = arr.getJSONObject(i);

                        Log.i("main", jsonPart.getString("main"));
                        Log.i("description", jsonPart.getString("description"));

                    }
                }else {
                    Log.i("main","result = null");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
//EditText fieldweight = (EditText)findViewById(R.id.weight);
//double weight = Double.parseDouble(fieldweight.getText().toString());
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}

