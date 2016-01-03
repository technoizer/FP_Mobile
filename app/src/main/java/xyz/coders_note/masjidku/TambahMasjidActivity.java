package xyz.coders_note.masjidku;

import android.annotation.TargetApi;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class TambahMasjidActivity extends AppCompatActivity {
    double lat, lng;
    TextView namaTxt, latTxt, lngTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_masjid);
        namaTxt = (TextView) findViewById(R.id.nama_in);
        latTxt = (TextView) findViewById(R.id.lat_in);
        lngTxt = (TextView) findViewById(R.id.lng_in);
    }

    public void getLocation(View v){
        GPSTracker mGPS = new GPSTracker(this);

        if(mGPS.canGetLocation ){
            mGPS.getLocation();
            lat = mGPS.getLatitude();
            lng = mGPS.getLongitude();
            latTxt.setText(String.valueOf(lat));
            lngTxt.setText(String.valueOf(lng));
        }else{
            Toast.makeText(this, "Unable To Determine Location!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData(params[0]);
            return null;
        }

        protected void onPostExecute(Double result) {
            //Toast.makeText(getApplicationContext(), "command sent",Toast.LENGTH_LONG).show();
        }

        protected void onProgressUpdate(Integer... progress) {

            //pb.setProgress(progress[0]);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public void postData(String valueIWantToSend) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpGet = new HttpPost(
                    "http://fpmobile.esy.es/api/getMosque");
            try {
                HttpResponse response = httpclient.execute(httpGet);
                Reader reader = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                Gson baru = new Gson();
                Masjid[] data = baru.fromJson(reader, Masjid[].class);
                for (int i = 0; i < data.length; i++){
//                    Log.d("MASJID KE " + i, data[i].toString());
//                    mas.add(data[i]);
                }
            } catch (ClientProtocolException e) {
            } catch (IOException e) {
            }
            finally {
            }
        }
    }
}
