package xyz.coders_note.masjidku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

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
