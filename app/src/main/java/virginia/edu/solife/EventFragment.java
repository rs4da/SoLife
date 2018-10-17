//package virginia.edu.solife;
//
//import android.support.v4.app.Fragment;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class EventFragment extends Fragment {
//    private RecyclerView rvItems;
//    ArrayList<EventItem> items;
//    private FirebaseDatabase db;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.activity_events, container, false);
//        rvItems = (RecyclerView) view.findViewById(R.id.rvItems);
//        items = EventItem.createInitialEventList();
//        EventAdapter adapter = new EventAdapter(getContext(), items);
//        rvItems.setAdapter(adapter);
//        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
//        return view;
//
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        // Setup any handles to view objects here
//        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
//    }
//}
