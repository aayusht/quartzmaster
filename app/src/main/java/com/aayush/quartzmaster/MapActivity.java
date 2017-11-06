package com.aayush.quartzmaster;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends AppCompatActivity {
    String phoneNumber = "";
    String emailAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = w.getDecorView();
            int flags = view.getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
        TextView addressTextView = (TextView) findViewById(R.id.address);
        switch(getIntent().getStringExtra("location")) {
            case "Bayonne, NJ":
                addressTextView.setText("101 E 2nd St\nBayonne, NJ 07002");
                phoneNumber = "tel:2013390900";
                break;
            case "Dallas, TX":
                addressTextView.setText("11129 Zodiac Lane\n#300, Dallas, TX 75229");
                phoneNumber = "tel:9728076187";
                break;
            case "Canton, MA":
                addressTextView.setText("905 Turnpike Street,\nCanton, MA 02021");
                phoneNumber = "tel:7817026835";
                break;
            case "Los Angeles, CA":
                addressTextView.setText("1519 Essex Street Unit #1523\nLos Angeles, CA 90021");
                phoneNumber = "tel:8443201287";
                emailAddress = "info@caquartzmasters.com";
                break;
            case "Palm Beach, FL":
                addressTextView.setText("1800 4th Ave N Unit A\nLake Worth, FL 33461");
                phoneNumber = "tel:5614788805";
                break;
            case "Mountain View, CA":
                addressTextView.setText("772 Mariposa Ave.\nMountain View, CA 94041");
                phoneNumber = "tel:8443201287";
                emailAddress = "info@caquartzmasters.com";
                break;
            case "Nashville, TN":
                addressTextView.setText("3723 Keystone Ave\nNashville, TN 37211");
                phoneNumber = "tel:6158372075";
                break;
            case "Chicago, IL":
                addressTextView.setText("1620 Jarvis Ave\nElk Grove Village, IL 60007");
                phoneNumber = "tel:7088007710";
                emailAddress = "quartzmasterchicago@gmail.com";
                break;
            case "Denver, CO":
                addressTextView.setText("6400 Stapleton Drive South Unit A\nDenver, CO 80216");
                phoneNumber = "tel:7206337852";
                break;
            case "Sterling, VA":
                addressTextView.setText("22714 Glenn Drive\nSterling, VA 20164");
                phoneNumber = "tel:7036892400";
                emailAddress = "info@quartzmasterva.com";
                break;
            case "Roseville, MN":
                addressTextView.setText("2800 Fairview Ave N\nRoseville, MN 55113");
                phoneNumber = "tel:6514831576";
                break;
            case "Toronto":
                addressTextView.setText("200 Trowers Rd.,\nVaughan On Unit 5 L4L 5Z8");
                phoneNumber = "tel:9058507889";
                break;
        }
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog mBottomSheetDialog = new Dialog(MapActivity.this, R.style.MaterialDialogSheet);
                View layout = getLayoutInflater().inflate(R.layout.contact_dialog, null);
                layout.findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(phoneNumber));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                });
                layout.findViewById(R.id.email).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!emailAddress.equals("")) {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                    "mailto", emailAddress, null));
                            startActivity(Intent.createChooser(emailIntent, "Send email..."));
                        } else {
                            Toast.makeText(MapActivity.this, "No email address found for this location!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mBottomSheetDialog.setContentView(layout); // your custom view.
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();
            }
        });
    }
}
