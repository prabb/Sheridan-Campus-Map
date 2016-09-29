package com.prabhsingh.sheridancampuses;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {


    private static final LatLng DAVIS = new LatLng(43.65605439999999, -79.7393437);
    private static final LatLng HMC = new LatLng(43.591075, -79.64704699999999);
    private static final LatLng STC = new LatLng(43.4663681, -79.684664);
    private static final LatLng OAKVILLE = new LatLng(43.47016949999999, -79.70194759999998);

    /**
     * Demonstrates customizing the info window and/or its contents.
     */
    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View mWindow;


        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }


        private void render(Marker marker, View view) {
            int badge;
            // Use the equals() method on a Marker to check for equals.  Do not use ==.
            if (marker.equals(mDavis)) {
                badge = R.drawable.davis;
            } else if (marker.equals(mHMC)) {
                badge = R.drawable.hmc;
            } else if (marker.equals(mSTC)) {
                badge = R.drawable.stc;
            } else if (marker.equals(mOakville)) {
                badge = R.drawable.trafalgar;
            } else {
                // Passing 0 to setImageResource will clear the image view.
                badge = 0;
            }
            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 9) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
    }

    private Marker mDavis;
    private Marker mHMC;
    private Marker mSTC;
    private Marker mOakville;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    /**
     * Called when the map is ready.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // Add some markers to the map, and add a data object to each marker.
        mDavis = mMap.addMarker(new MarkerOptions()
                .position(DAVIS)
                .title("Davis Campus")
                .snippet("Student Enrolled: 18,400")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerblue36)));
        mDavis.setTag(0);

        mHMC = mMap.addMarker(new MarkerOptions()
                .position(HMC)
                .title("Hazel MaCallion Campus")
                .snippet("Student Enrolled: 8,400")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerblue36)));
        mHMC.setTag(0);

        mSTC = mMap.addMarker(new MarkerOptions()
                .position(STC)
                .title("Skills Training Centre")
                .snippet("Student Enrolled: 1,400")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerblue36)));
        mSTC.setTag(0);

        mOakville = mMap.addMarker(new MarkerOptions()
                .position(OAKVILLE)
                .title("Trafalgar Campus")
                .snippet("Student Enrolled: 12,400")
                .flat(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerblue36)));
        mOakville.setTag(0);

        // Polylines are useful for marking paths and routes on the map.
        map.addPolyline(new PolylineOptions()
                .geodesic(true)
                .width(10)
                .color(Color.BLUE)
                .add(DAVIS, HMC, STC, OAKVILLE));

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(DAVIS, 10));

        // Setting an info window adapter allows us to change the both the contents and look of the
        // info window.
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        // Set listeners for marker events.  See the bottom of this class for their behavior.
        mMap.setOnMarkerClickListener(this);
    }

    /**
     * Called when the user clicks a marker.
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        return false;
    }
}