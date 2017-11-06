package com.aayush.quartzmaster;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setTitle("Filters");
        findViewById(R.id.email).setOnClickListener(this);
        findViewById(R.id.call).setOnClickListener(this);
        findViewById(R.id.finish_filter).setOnClickListener(this);
        findViewById(R.id.thickness_filter).setOnClickListener(this);
        findViewById(R.id.clear_filters).setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.marble:
            case R.id.starlight:
            case R.id.scenery:
            case R.id.granite:
            case R.id.sound:
            case R.id.mineral:
            case R.id.thickness_checkbox:
            case R.id.finish_checkbox:
            case R.id.qm_checkbox:
            case R.id.mc_checkbox:
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("" + compoundButton.getId(), b);
                Log.d("sdada", b ? "true" : "false");
                editor.commit();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        final Dialog mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        View dialogView;
        switch (view.getId()) {
            case R.id.clear_filters:
                MainActivity.clearFilters(this);
                Toast.makeText(this, "Filters cleared!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.email:
                dialogView = getLayoutInflater().inflate(R.layout.collections_dialog, null);
                ((CheckBox) dialogView.findViewById(R.id.marble)).setChecked(
                        getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.marble, true));
                ((CheckBox) dialogView.findViewById(R.id.starlight)).setChecked(
                        getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.starlight, true));
                ((CheckBox) dialogView.findViewById(R.id.scenery)).setChecked(
                        getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.scenery, true));
                ((CheckBox) dialogView.findViewById(R.id.mineral)).setChecked(
                        getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.mineral, true));
                ((CheckBox) dialogView.findViewById(R.id.granite)).setChecked(
                        getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.granite, true));
                ((CheckBox) dialogView.findViewById(R.id.sound)).setChecked(
                        getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.sound, true));

                ((CheckBox) dialogView.findViewById(R.id.marble)).setOnCheckedChangeListener(this);
                ((CheckBox) dialogView.findViewById(R.id.starlight)).setOnCheckedChangeListener(this);
                ((CheckBox) dialogView.findViewById(R.id.scenery)).setOnCheckedChangeListener(this);
                ((CheckBox) dialogView.findViewById(R.id.mineral)).setOnCheckedChangeListener(this);
                ((CheckBox) dialogView.findViewById(R.id.granite)).setOnCheckedChangeListener(this);
                ((CheckBox) dialogView.findViewById(R.id.sound)).setOnCheckedChangeListener(this);
                launchDialog(mBottomSheetDialog, dialogView);
                break;
            case R.id.thickness_filter:
                dialogView = getLayoutInflater().inflate(R.layout.thickness_dialog, null);
                ((CheckBox) dialogView.findViewById(R.id.thickness_checkbox)).setChecked(
                        getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.thickness_checkbox, true));
                ((CheckBox) dialogView.findViewById(R.id.thickness_checkbox)).setOnCheckedChangeListener(this);
                launchDialog(mBottomSheetDialog, dialogView);
                break;
            case R.id.finish_filter:
                dialogView = getLayoutInflater().inflate(R.layout.finish_dialog, null);
                ((CheckBox) dialogView.findViewById(R.id.finish_checkbox)).setChecked(
                        getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.finish_checkbox, true));
                ((CheckBox) dialogView.findViewById(R.id.finish_checkbox)).setOnCheckedChangeListener(this);
                launchDialog(mBottomSheetDialog, dialogView);
                break;
            case R.id.call:
                dialogView = getLayoutInflater().inflate(R.layout.size_dialog, null);
                ((CheckBox) dialogView.findViewById(R.id.qm_checkbox)).setChecked(
                        getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.qm_checkbox, true));
                ((CheckBox) dialogView.findViewById(R.id.mc_checkbox)).setChecked(
                        getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.mc_checkbox, true));

                ((CheckBox) dialogView.findViewById(R.id.qm_checkbox)).setOnCheckedChangeListener(this);
                ((CheckBox) dialogView.findViewById(R.id.mc_checkbox)).setOnCheckedChangeListener(this);
                launchDialog(mBottomSheetDialog, dialogView);
                break;
        }
    }

    public void launchDialog(Dialog mBottomSheetDialog, View view) {
        mBottomSheetDialog.setContentView(view); // your custom view.
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
    }
}
