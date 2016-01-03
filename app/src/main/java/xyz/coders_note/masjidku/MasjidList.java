package xyz.coders_note.masjidku;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALPRO on 02/01/2016.
 */
public class MasjidList implements Serializable {
    private List<Masjid> data = new ArrayList<>();

    public MasjidList(List<Masjid> data) {
        this.data = data;
    }

    public List<Masjid> getData() {
        return data;
    }

    public void setData(List<Masjid> data) {
        this.data = data;
    }
}
