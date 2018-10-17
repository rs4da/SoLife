package virginia.edu.solife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v4.app.FragmentManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback{

    private GoogleMap mMap;
    private String user;
    private FirebaseDatabase db;
    private ArrayList<EventItem> asyncQueue;
    private HashMap<String, EventItem> events;
    private boolean mPermissionDenied = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_list:
                    //getSupportFragmentManager().findFragmentById(R.id.map).getTra;
                    Intent intent = new Intent(MapsActivity.this, EventsActivity.class);
                    intent.putExtra("user", user);
                    MapsActivity.this.startActivity(intent);
                case R.id.navigation_camera:
                    //mTextMessage.setText(R.string.title_notifications);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");
        db = FirebaseDatabase.getInstance();
        asyncQueue = new ArrayList<EventItem>();
        events = new HashMap<String, EventItem>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, AddEventActivity.class);
                MapsActivity.this.startActivityForResult(intent, 1);
            }
        });
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, 1,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != 1) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

// ----------------------
// Start the map over UVA
// ----------------------
        mMap = googleMap;
        LatLng UVA = new LatLng(38.0336, -78.507980);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UVA));
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

// ------------------------
// Load events into the map
// ------------------------
        DatabaseReference ref = db.getReference();
        Log.d("MAP", "Map is ready");
        ref.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("EVENT LOADING", "database has been retrieved");
                for(DataSnapshot eventSnap : dataSnapshot.getChildren()){
                    if(eventSnap.child("invite").hasChild("public") || eventSnap.child("invite").hasChild(user)){
                        createMarker(eventSnap.getKey(), eventSnap.child("address").getValue(),
                                eventSnap.child("name").getValue(),
                                new Date(Integer.parseInt(eventSnap.child("date").child("year").getValue().toString()),
                                        Integer.parseInt(eventSnap.child("date").child("month").getValue().toString()),
                                        Integer.parseInt(eventSnap.child("date").child("date").getValue().toString()),
                                        Integer.parseInt(eventSnap.child("date").child("hours").getValue().toString()),
                                        Integer.parseInt(eventSnap.child("date").child("minutes").getValue().toString())),
                                eventSnap.child("host").getValue(), eventSnap.child("desc").getValue());
//                        eventSnap.child("date").child("month").getValue(),
//                                eventSnap.child("date").child("day").getValue(),
//                                eventSnap.child("date").child("year").getValue(),
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                EventItem clicked = events.get(marker.getTitle());
                launchFocus(clicked);
            }
        });
    }

// -------------------------------
// Launch focus activity for event
// -------------------------------
    public void launchFocus(EventItem e){
        Intent i = new Intent(this, FocusActivity.class);
        i.putExtra("name", e.getname());
        i.putExtra("desc", e.getDesc());
        i.putExtra("host", e.gethost());
        i.putExtra("time", e.getDate().toString());
        i.putExtra("adrs", e.getAddress());
//        startActivityForResult(i, 1);
        startActivity(i);
    }


// -----------------------------
// Get lat and long from address
// -----------------------------
    public void createMarker(Object i, Object a, Object n, Date t, Object h, Object d){
        String id, address, name, desc, host;

        try{
            address = (String)a;
            name = (String)n;
            desc = (String)d;
            host = (String)h;
            asyncQueue.add(new EventItem(name, host, desc, address, t));
            Log.d("CREATED MARKER", "success: " + a + " " + n + " " + t);

// ---------------
// Set up API call
// ---------------
            if(address == null)
                return;
            address = address.replace(' ', '+');
            Log.d("GEOCODE API", address);
            String GEOCODE_API = "AIzaSyC55We6_XnAppTxl50XAKBcVedWM0yhAdQ";
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="https://maps.googleapis.com/maps/api/geocode/json?address="+address+",+Charlottesville,+VA&key=" + GEOCODE_API;

// ----------------------------------
// Parse string response for lat/long
// ----------------------------------
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            double[] toReturn = new double[2];
                            int index1 = response.indexOf("lat\" : ")+6;
                            int index2 = response.indexOf("lng\" : ")+6;
                            toReturn[0] = Double.parseDouble(response.substring(index1, index1 + 12).split(",")[0]);
                            toReturn[1] = Double.parseDouble(response.substring(index2, index2 + 12).split("\\}")[0]);
                            addLL(toReturn);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Response is: ", "Didn't work");
                }
            });
            queue.add(stringRequest);
        } catch(Exception e) {
            Log.d("DB RETRIEVAL", "couldn't convert event details to strings");
        }
    }

// ------------------------------------------------
// Create a new marker after the async Geocode call
// ------------------------------------------------
    public void addLL(double[] coords) {
        EventItem temp = asyncQueue.remove(0);
//        temp.setLati(coords[0]);
//        temp.setLati(coords[1]);
        events.put(temp.getname(), temp);
        mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(coords[0], coords[1]))
                        .title(temp.getname())
                        .snippet(temp.getDate()+""));
    }
}
