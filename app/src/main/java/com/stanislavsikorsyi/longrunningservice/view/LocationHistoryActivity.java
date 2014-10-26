package com.stanislavsikorsyi.longrunningservice.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.stanislavsikorsyi.longrunningservice.R;
import com.stanislavsikorsyi.longrunningservice.model.LocationForSet;
import com.stanislavsikorsyi.longrunningservice.util.Constants;
import com.stanislavsikorsyi.longrunningservice.util.LocationManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by stanislavsikorsyi on 16.07.14.
 */
public class LocationHistoryActivity extends Activity {


    private static final String TAG = "LocationHistoryActivity";

    private ListView listView;
    private TextView quantityView;
    private List<LocationForSet> setOfHistoryLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationhistory_activity);
        Log.d(TAG, "onCreate");
        listView = (ListView)findViewById(R.id.list);
        quantityView = (TextView)findViewById(R.id.location_quantity);
        setOfHistoryLocations = new LinkedList(LocationManager.getLocationSet());
        quantityView.setText("Size: " + setOfHistoryLocations.size());
        ArrayAdapter<LocationForSet> locationsAdapter = new ArrayAdapter<LocationForSet>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, setOfHistoryLocations);
        listView.setAdapter(locationsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LocationHistoryActivity.this, LocationDetailsActivity.class);
                intent.putExtra(Constants.LOCATION_DETAILS, i);
                startActivity(intent);
            }
        });
    }




}
