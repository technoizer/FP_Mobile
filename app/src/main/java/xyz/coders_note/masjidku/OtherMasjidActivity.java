package xyz.coders_note.masjidku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OtherMasjidActivity extends AppCompatActivity {
    double lat,lng;
    MasjidList data;
    ItemListAdaptorOther adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_masjid);
        listView = (ListView) findViewById(R.id.listViewOther);

        android.support.v7.app.ActionBar baru = getSupportActionBar();
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar().getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        final View customActionBarView = inflater.inflate(
                R.layout.back_action_bar, null);

        baru.setHomeButtonEnabled(true);
        baru.setHomeButtonEnabled(true);
        baru.setDisplayHomeAsUpEnabled(false);
        baru.setDisplayShowTitleEnabled(false);
        baru.setIcon(R.drawable.masjid_ikon);
        baru.setCustomView(customActionBarView);
        baru.setDisplayShowCustomEnabled(true);
        ImageView back = (ImageView) customActionBarView.findViewById(R.id.backBtn);
        TextView title = (TextView) customActionBarView.findViewById(R.id.bar_title);
        title.setText("Masjid Alternatif");
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Bundle b = getIntent().getExtras();
        lat = b.getDouble("lat");
        lng = b.getDouble("lng");
        data = (MasjidList) b.getSerializable("data");

        adapter = new ItemListAdaptorOther(OtherMasjidActivity.this,R.layout.item_list_other,data.getData(),lat,lng);
        listView.setAdapter(adapter);
    }

    public void showOtherMap(View v){
        Masjid item = (Masjid) v.getTag();
        Intent i = new Intent(getApplicationContext(), MapMasjid.class);
        Bundle b = new Bundle();
        b.putDouble("lat", item.getLat());
        b.putDouble("lng", item.getLng());
        b.putString("nama", item.getName());
        i.putExtras(b);
        startActivity(i);
    }
}
