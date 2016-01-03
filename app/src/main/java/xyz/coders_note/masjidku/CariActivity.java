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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.util.ArrayList;
import java.util.List;

public class CariActivity extends AppCompatActivity {
    ProgressBar pb;
    List<Masjid> masjids = new ArrayList<>();
    TextView res, nama_masjid, jarak,txtWait;
    Double lat, lng;
    Button showMap, otherMasjid;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari);
        GPSTracker mGPS = new GPSTracker(this);

        if(mGPS.canGetLocation ){
            mGPS.getLocation();
            lat = mGPS.getLatitude();
            lng = mGPS.getLongitude();
        }else{
            Toast.makeText(this, "Unable To Determine Location!", Toast.LENGTH_SHORT).show();
            finish();
        }

        pb = (ProgressBar)findViewById(R.id.pb);
        res = (TextView) findViewById(R.id.deskripsi_masjid);
        nama_masjid = (TextView) findViewById(R.id.nama_masjid);
        jarak = (TextView) findViewById(R.id.jarak_masjid);
        showMap = (Button) findViewById(R.id.show_cari);
        txtWait = (TextView) findViewById(R.id.textWait);
        otherMasjid = (Button) findViewById(R.id.other_cari);
        logo = (ImageView) findViewById(R.id.logo_cari);
        pb.setVisibility(View.VISIBLE);
        txtWait.setVisibility(View.VISIBLE);
        res.setVisibility(View.GONE);
        logo.setVisibility(View.GONE);
        nama_masjid.setVisibility(View.GONE);
        jarak.setVisibility(View.GONE);
        showMap.setVisibility(View.GONE);
        otherMasjid.setVisibility(View.GONE);

        new MyAsyncTask().execute("hehehe");
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
            res.setVisibility(View.VISIBLE);
            nama_masjid.setVisibility(View.VISIBLE);
            jarak.setVisibility(View.VISIBLE);
            showMap.setVisibility(View.VISIBLE);
            logo.setVisibility(View.VISIBLE);
            otherMasjid.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
            txtWait.setVisibility(View.GONE);
            if (!masjids.isEmpty()){
                nama_masjid.setVisibility(View.VISIBLE);
                jarak.setVisibility(View.VISIBLE);
                Location locationA = new Location("point A");
                locationA.setLatitude(lat);
                locationA.setLongitude(lng);
                Location locationB = new Location("point B");
                locationB.setLatitude(masjids.get(0).getLat());
                locationB.setLongitude(masjids.get(0).getLng());
                double distance = locationA.distanceTo(locationB) ;
                res.setText("Saat ini anda sedang berada di dekat masjid :");
                nama_masjid.setText(masjids.get(0).getName().toUpperCase());
                String jarakTxt = "± " + (distance > 1000 ?  (int)(distance/1000.0) + " km" : (int)distance + " m");
                jarak.setText(jarakTxt);

                showMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), MapMasjid.class);
                        Bundle b = new Bundle();
                        b.putDouble("lat", masjids.get(0).getLat());
                        b.putDouble("lng", masjids.get(0).getLng());
                        b.putString("nama", masjids.get(0).getName());
                        i.putExtras(b);
                        startActivity(i);
                    }
                });

                otherMasjid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), OtherMasjidActivity.class);
                        Bundle b = new Bundle();
                        b.putDouble("lat", lat);
                        b.putDouble("lng", lng);
                        MasjidList data = new MasjidList(masjids);
                        b.putSerializable("data",data);
                        i.putExtras(b);
                        startActivity(i);
                    }
                });
            }
            else {
                res.setText("Tidak Ada Masjid di Sekitar Anda!");
                nama_masjid.setVisibility(View.GONE);
                jarak.setVisibility(View.GONE);
                showMap.setVisibility(View.GONE);
                otherMasjid.setVisibility(View.GONE);
            }
        }

        protected void onProgressUpdate(Integer... progress) {
            pb.setProgress(progress[0]);
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
                    Log.d("MASJID KE " + i, data[i].toString());
                    masjids.add(data[i]);
                }
            } catch (ClientProtocolException e) {
            } catch (IOException e) {
            }
            finally {
            }
        }
    }
}
