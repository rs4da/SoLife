package virginia.edu.solife;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class FocusActivity extends AppCompatActivity {

    TextView nameField, descField, hostField, timeField, adrsField, latiField, lngtField;
    ImageView qrImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("Event Details");
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PRESSED", "back pressed");
                onBackPressed();
            }
        });

        nameField = (TextView) findViewById(R.id.nameField);
        descField = (TextView) findViewById(R.id.descField);
        hostField = (TextView) findViewById(R.id.hostField);
        timeField = (TextView) findViewById(R.id.timeField);
        adrsField = (TextView) findViewById(R.id.adrsField);
        latiField = (TextView) findViewById(R.id.latiField);
        lngtField = (TextView) findViewById(R.id.lngtField);
        qrImg = (ImageView) findViewById(R.id.qrImg);

        Intent i = getIntent();
        nameField.setText(i.getStringExtra("name"));
        descField.setText(i.getStringExtra("desc"));
        hostField.setText(i.getStringExtra("host"));
        timeField.setText(i.getStringExtra("time"));
        adrsField.setText(i.getStringExtra("adrs"));
        latiField.setText(i.getStringExtra("lati"));
        lngtField.setText(i.getStringExtra("lngt"));

        setSupportActionBar(toolbar);

        String text= nameField.getText().toString();// Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrImg.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void goBack(){
        finish();
        return;
    }

}
