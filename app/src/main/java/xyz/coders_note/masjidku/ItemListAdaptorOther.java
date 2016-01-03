package xyz.coders_note.masjidku;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ALPRO on 02/01/2016.
 */
public class ItemListAdaptorOther extends ArrayAdapter<Masjid> {
    private List<Masjid> items;
    private int layoutResourceId;
    private Context context;
    Double lat,lng;

    public ItemListAdaptorOther(Context context, int layoutResourceId, List<Masjid> items, double lat, double lng){
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PrayHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId,parent,false);
        holder = new PrayHolder();
        holder.masjid = items.get(position);
        holder.name_other = (TextView)row.findViewById(R.id.nama_other);
        holder.jarak_other = (TextView)row.findViewById(R.id.jarak_other);
        holder.showOnMap = (ImageButton) row.findViewById(R.id.view_map_other);
        holder.showOnMap.setTag(holder.masjid);
        row.setTag(holder);
        setupItem(holder);
        return row;
    }

    private void setupItem(PrayHolder holder){
        holder.name_other.setText(holder.masjid.getName());
        double jarak = getDistance(lat,lng,holder.masjid.getLat(),holder.masjid.getLng());
        String jarakTxt = "Jarak : ± " + (jarak > 1000 ?  (int)(jarak/1000.0) + " km" : (int)jarak + " m");
        holder.jarak_other.setText(jarakTxt);
    }

    public static class PrayHolder{
        Masjid masjid;
        TextView name_other;
        TextView jarak_other;
        ImageButton showOnMap;
    }

    public Double getDistance(Double latA, Double lngA, Double latB, Double lngB){
        Location locationA = new Location("point A");
        locationA.setLatitude(latA);
        locationA.setLongitude(lngA);
        Location locationB = new Location("point B");
        locationB.setLatitude(latB);
        locationB.setLongitude(lngB);
        double distance = locationA.distanceTo(locationB) ;
        return distance;
    }
}
