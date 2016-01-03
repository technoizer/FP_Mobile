package xyz.coders_note.masjidku;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView image;
    private float currenDegree = 0f;
    private SensorManager mSensorManager;
    private double lat, lng;
    private RelativeLayout layoutKiblat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

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
        title.setText("Penunjuk Kiblat");
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        image = (ImageView) findViewById(R.id.imageKiblat);
        layoutKiblat = (RelativeLayout) findViewById(R.id.layoutKompas);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        GPSTracker mGPS = new GPSTracker(this);
        if(mGPS.canGetLocation ){
            mGPS.getLocation();
            lat = mGPS.getLatitude();
            lng = mGPS.getLongitude();
        }else{
            Toast.makeText(this, "Unable To Determine Location!", Toast.LENGTH_SHORT).show();
            finish();
        }
        Location locationA = new Location("point A");
        locationA.setLatitude(lat);
        locationA.setLongitude(lng);
        Location locationB = new Location("point B");
        locationB.setLatitude(21.4225239);
        locationB.setLongitude(39.8239876);
        double kiblat = locationA.bearingTo(locationB);
        image.setRotation((float)kiblat);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currenDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        layoutKiblat.startAnimation(ra);
        currenDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

    private double angleFromCoordinate(double lat2, double long2, double lat1, double long1) {
        double dLon = (long2 - long1);
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
        double angle = Math.atan2(y, x);
        angle = Math.toDegrees(angle);
        angle = (angle + 360) % 360;
        angle = 360 - angle;
        return angle;
    }

}
