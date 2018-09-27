package com.lyphomed.nishantpatel.projectguestlogix.ui.maps;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.lyphomed.nishantpatel.projectguestlogix.R;
import com.lyphomed.nishantpatel.projectguestlogix.config.PublicKeys;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.FullViaPath;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Maps Activity which deals with showing paths on map
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsContract.View {

    private GoogleMap mMap;
    private Routes mDirectPath;
    private FullViaPath mViaPath;
    private CompositeDisposable mCompositeDisposable;
    private MapsPresenter mPresenter;

    /**
     * Call this method to get intent to start {@link MapsActivity}
     *
     * @param context Context of activity from which we want to start {@link MapsActivity}
     * @return starter intent
     */
    public static Intent getStarterIntent(Context context) {
        return new Intent(context, MapsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        mPresenter = new MapsPresenter(DataManager.getInstance());
        mPresenter.attachView(this);

        mCompositeDisposable = new CompositeDisposable();
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            if (receivedIntent.hasExtra(PublicKeys.KEY_DIRECT_PATH)) {
                mDirectPath = (Routes) receivedIntent.getSerializableExtra(PublicKeys.KEY_DIRECT_PATH);
            }
            if (receivedIntent.hasExtra(PublicKeys.KEY_VIA_PATH)) {
                mViaPath = (FullViaPath) receivedIntent.getSerializableExtra(PublicKeys.KEY_VIA_PATH);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mDirectPath != null) {
            mPresenter.provideAirportDetails(mDirectPath.getOrigin(), mDirectPath.getDestination());
        } else {
            mPresenter.provideAirportDetails(mViaPath.getOrigin(), mViaPath.getVia(), mViaPath.getDestination());
        }
    }

    @Override
    public void onDisposable(Disposable d) {
        mCompositeDisposable.add(d);
    }

    @Override
    public void onAirports(List<Airports> airports) {
        for (Airports airport : airports) {
            // Loop through whole list and place marker on the map
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(airport.getLatitude()), Double.parseDouble(airport.getLatitude())))
                    .title(airport.getAirportName())
                    .snippet("Airport : " + airport.getAirportName() + "\n" + "City : " + airport.getCity()
                            + "\n" + "IATA : " + airport.getAirportCode())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        // If it's direct path connect two airports
        if (airports.size() == 2) {
            connectMarkers(Double.parseDouble(airports.get(0).getLatitude()),
                    Double.parseDouble(airports.get(0).getLongitude()),
                    Double.parseDouble(airports.get(1).getLatitude()),
                    Double.parseDouble(airports.get(1).getLongitude()));
        }

        // If it's via path connect origin and destination with via location
        if (airports.size() == 3) {
            connectMarkers(Double.parseDouble(airports.get(0).getLatitude()),
                    Double.parseDouble(airports.get(0).getLongitude()),
                    Double.parseDouble(airports.get(1).getLatitude()),
                    Double.parseDouble(airports.get(1).getLongitude()));

            connectMarkers(Double.parseDouble(airports.get(1).getLatitude()),
                    Double.parseDouble(airports.get(1).getLongitude()),
                    Double.parseDouble(airports.get(2).getLatitude()),
                    Double.parseDouble(airports.get(2).getLongitude()));
        }
    }

    /**
     * Call this method to connect lines between markers
     *
     * @param startLat First point latitude
     * @param startLng first point longitude
     * @param endLat   second point latitude
     * @param endLng   second point longitude
     */
    public void connectMarkers(double startLat, double startLng, double endLat, double endLng) {
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(startLat, startLng), new LatLng(endLat, endLng))
                .width(5)
                .color(getColor(R.color.colorAccent)));
        line.setVisible(true);
    }
}

