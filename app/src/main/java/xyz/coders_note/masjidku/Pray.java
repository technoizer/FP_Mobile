package xyz.coders_note.masjidku;

import java.io.Serializable;

/**
 * Created by ALPRO on 31/12/2015.
 */
public class Pray implements Serializable {
    private String pray_name = "";
    private String pray_time = "";

    Pray(String pray_name, String pray_time){
        this.setPray_name(pray_name);
        this.setPray_time(pray_time);
    }

    public String getPray_name() {
        return pray_name;
    }

    public void setPray_name(String pray_name) {
        this.pray_name = pray_name;
    }

    public String getPray_time() {
        return pray_time;
    }

    public void setPray_time(String pray_time) {
        this.pray_time = pray_time;
    }
}
