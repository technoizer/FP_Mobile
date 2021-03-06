package xyz.coders_note.masjidku;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ALPRO on 31/12/2015.
 */
public class ItemListAdaptor extends ArrayAdapter<Pray> {
    private List<Pray> items;
    private int layoutResourceId;
    private Context context;

    public ItemListAdaptor(Context context, int layoutResourceId, List<Pray> items){
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PrayHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId,parent,false);
        holder = new PrayHolder();
        holder.pray = items.get(position);
        holder.pray_name = (TextView)row.findViewById(R.id.pray_name);
        holder.pray_time = (TextView)row.findViewById(R.id.pray_time);
        row.setTag(holder);
        setupItem(holder);
        return row;
    }

    private void setupItem(PrayHolder holder){
        holder.pray_name.setText(holder.pray.getPray_name());
        holder.pray_time.setText(holder.pray.getPray_time());
    }

    public static class PrayHolder{
        Pray pray;
        TextView pray_name;
        TextView pray_time;
    }
}
