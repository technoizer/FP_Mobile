package xyz.coders_note.masjidku;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JadwalActivity extends AppCompatActivity {
    //private TextView txtPrayerTimes;
    Spinner wilayahList;
    ListView listView;
    String[] coor_wilayah;
    double lat, lng;
    private ItemListAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        coor_wilayah = getResources().getStringArray(R.array.coord_wilayah);
        //txtPrayerTimes = (TextView) findViewById(R.id.txtPrayerTimes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.wilayah, R.layout.spinner_style);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wilayahList = (Spinner)findViewById(R.id.wilayah);
        wilayahList.setAdapter(adapter);

        wilayahList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String coordinate = coor_wilayah[wilayahList.getSelectedItemPosition()];
                String[] parts = coordinate.split(",");
                lat = Double.parseDouble(parts[0]);
                lng = Double.parseDouble(parts[1]);
                //Toast.makeText(getApplicationContext(),"ini posisi " + wilayahList.getSelectedItem().toString() + "\n" + lat + "\n" + lng,Toast.LENGTH_SHORT).show();
                getTime();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getTime() {
        // Retrive lat, lng using location API
        double latitude = lat;
        double longitude = lng;
        double timezone = (Calendar.getInstance().getTimeZone()
                .getOffset(Calendar.getInstance().getTimeInMillis()))
                / (1000 * 60 * 60);
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Makkah);
        prayers.setAsrJuristic(prayers.Shafii);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = { 0, 0, 0, 0, 0, 0, 0 }; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        ArrayList prayerTimes = prayers.getPrayerTimes(cal, latitude,
                longitude, timezone);
        ArrayList prayerNames = prayers.getTimeNames();
//        String tmp = "";
//        for (int i = 0; i < prayerTimes.size(); i++) {
//            tmp += "\n" + prayerNames.get(i) + "\t - \t"+ prayerTimes.get(i);
//        }

        List<Pray> list = new ArrayList<>();

        for (int i = 0; i < prayerTimes.size(); i++) {
            Pray tmp = new Pray(prayerNames.get(i).toString(),prayerTimes.get(i).toString());
            list.add(tmp);
        }

        adapter = new ItemListAdaptor(JadwalActivity.this,R.layout.item_list,list);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);


        //txtPrayerTimes.setText(tmp);

    }
}
