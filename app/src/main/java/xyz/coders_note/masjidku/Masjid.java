package xyz.coders_note.masjidku;

import java.io.Serializable;

/**
 * Created by ALPRO on 01/01/2016.
 */
public class Masjid implements Serializable {
    private int id;
    private String name;
    private Double lat;
    private Double lng;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String toString (){
        return getName() + " - " + getLat() + " - " + getLng() + " - " + getPhoto();
    }
}
