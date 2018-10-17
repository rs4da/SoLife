package virginia.edu.solife;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity {
    private FirebaseDatabase db;

    private TextInputEditText eventname;
    private TextInputEditText desc;
    private TextInputEditText address;
    private TextInputEditText host;
    private TextInputEditText invites;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eventname = (TextInputEditText)findViewById(R.id.eventname_field);
        desc = (TextInputEditText)findViewById(R.id.desc_field);
        address = (TextInputEditText)findViewById(R.id.address_field);
        // TODO: the host should be automatically set to the logged in user
        host = (TextInputEditText)findViewById(R.id.host_field);
        invites = (TextInputEditText)findViewById(R.id.invites_field);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Create New Event");
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        db = FirebaseDatabase.getInstance();
    }

    public void addEventItem(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        try{
            // Adds newly created event to the database
            EventItem event = new EventItem(eventname.getText().toString(),host.getText().toString(), desc.getText().toString(),
                    address.getText().toString(),new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                    timePicker.getHour(), timePicker.getMinute()));
            DatabaseReference mDatabase = db.getReference();
            ArrayList<String> lst =
                    new ArrayList<String>(Arrays.asList(invites.getText().toString().split(",")));
            mDatabase.child("events").child(eventname.getText().toString()).setValue(event);
            for(int i = 0; i < lst.size(); i++)
                mDatabase.child("events").child(event.getname()).child("invite").child(lst.get(i)).setValue(true);
            setResult(Activity.RESULT_OK, intent);
        }
        catch (Exception e){
            setResult(-42);
        }
        finish();
    }
}
