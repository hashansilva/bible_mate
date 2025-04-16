package com.hashan0314.biblemate;

import static java.time.LocalDate.now;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.DayResponse;
import org.openapitools.client.model.WeekResponse;

import java.time.LocalDate;

public class DailyVerseActivity extends AppCompatActivity {

    private static final String TAG = "BibleVerseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily_verse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        new Thread(() -> {
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            defaultClient.setBasePath("https://liturgy.day/api");

            DefaultApi apiInstance = new DefaultApi(defaultClient);
            LocalDate date = now();

            try {
                DayResponse result = apiInstance.day(date);
                Log.d(TAG, "Bible Verse: " + result.toString());
                // Optionally update UI via runOnUiThread()
            } catch (ApiException e) {
                Log.e(TAG, "Exception when calling DefaultApi#day", e);
            }
        }).start();
    }
}