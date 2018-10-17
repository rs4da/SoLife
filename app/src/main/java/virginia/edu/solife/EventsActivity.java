package virginia.edu.solife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class EventsActivity extends AppCompatActivity {

    private RecyclerView rvItems;
    private String user;
    private FirebaseDatabase db;
    ArrayList<EventItem> items;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    Intent intent = new Intent(EventsActivity.this, MapsActivity.class);
                    intent.putExtra("user", user);
                    EventsActivity.this.startActivity(intent);
                    return true;
                case R.id.navigation_list:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_camera:
                    //mTextMessage.setText(R.string.title_notifications);
                    Intent intent2 = new Intent(EventsActivity.this, ScannerActivity.class);
                    EventsActivity.this.startActivity(intent2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_list);

        user = getIntent().getStringExtra("user");
        db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        items = EventItem.createInitialEventList();
        ref.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("EVENT LOADING", "database has been retrieved");
                onUpdate(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("TEST9", "" + items.size());
        rvItems = (RecyclerView) findViewById(R.id.rvItems);

        //Collections.sort(items);
        EventAdapter adapter = new EventAdapter(this, items);
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void onUpdate(DataSnapshot dataSnapshot){
        for(DataSnapshot eventSnap : dataSnapshot.getChildren()) {
            if (eventSnap.child("invite").hasChild("public") || eventSnap.child("invite").hasChild(user)) {
                Log.d("EVENT LOADING", "we are inside the loop");
                EventItem item = new EventItem((String)eventSnap.child("name").getValue(),
                        (String)eventSnap.child("host").getValue(),
                        (String)eventSnap.child("desc").getValue(),
                        (String)eventSnap.child("address").getValue(),
                        new Date(Integer.parseInt(eventSnap.child("date").child("year").getValue().toString()),
                                Integer.parseInt(eventSnap.child("date").child("month").getValue().toString()),
                                Integer.parseInt(eventSnap.child("date").child("date").getValue().toString()),
                                Integer.parseInt(eventSnap.child("date").child("hours").getValue().toString()),
                                Integer.parseInt(eventSnap.child("date").child("minutes").getValue().toString())));
                items.add(item);
            }
        }
        rvItems = (RecyclerView) findViewById(R.id.rvItems);

        //Collections.sort(items);
        EventAdapter adapter = new EventAdapter(this, items);
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

}
