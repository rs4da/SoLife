package virginia.edu.solife;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder{

        //public CheckBox completedView;
        public TextView eventTextView;
        public TextView hostTextView;
        public TextView dateTextView;
        public Button button;

        public ViewHolder(View itemView) {
            super(itemView);

            button = (Button) itemView.findViewById(R.id.button);
            eventTextView = (TextView) itemView.findViewById(R.id.event_name);
            hostTextView = (TextView) itemView.findViewById(R.id.host_name);
            dateTextView = (TextView) itemView.findViewById(R.id.ev_date);

        }
    }

    private List<EventItem> mItems;
    private Context mContext;
    private DateFormat df;

    public EventAdapter(Context context, List<EventItem> items){
        mItems = items;
        mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        df = new SimpleDateFormat("yyyy-MM-dd");

        // Inflate the custom layout
        View eventItemView = inflater.inflate(R.layout.item_event, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(eventItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder viewHolder, final int position) {
        final int idx = position;
        EventItem item = mItems.get(position);
        Button button = viewHolder.button;

        //CheckBox checkBox = viewHolder.completedView;
        TextView eventnameView = viewHolder.eventTextView;
        TextView hostnameView = viewHolder.hostTextView;
        TextView dateView = viewHolder.dateTextView;
        eventnameView.setText(item.getname());
        hostnameView.setText(item.gethost());
        dateView.setText(df.format(item.getDate()));
        //checkBox.setChecked(item.getCompleted());

        final String og_eventname = item.getname();
        final String og_hostname = item.gethost();
        final String og_desc = item.getDesc();
        final String og_address = item.getAddress();
        final Date og_date = item.getDate();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), FocusActivity.class);
                intent.putExtra("name", og_eventname);
                intent.putExtra("desc", og_desc);
                intent.putExtra("host", og_hostname);
                intent.putExtra("adrs", og_address);
                intent.putExtra("time", og_date.toString());
                ((Activity) view.getContext()).startActivityForResult(intent, 2);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

