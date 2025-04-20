package com.hashan0314.biblemate;

import static java.time.LocalDate.now;

import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

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
import java.util.Locale;

public class DailyVerseActivity extends AppCompatActivity {

    private static final String TAG = "BibleVerseActivity";

    private TextView textDate, textSeason, textSundayCycle, textWeekdayCycle, textLothVolume, textRosarySeries;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_verse);

        datePicker = findViewById(R.id.datePicker);
        textDate = findViewById(R.id.textDate);
        textSeason = findViewById(R.id.textSeason);
        textSundayCycle = findViewById(R.id.textSundayCycle);
        textWeekdayCycle = findViewById(R.id.textWeekdayCycle);
        textLothVolume = findViewById(R.id.textLothVolume);
        textRosarySeries = findViewById(R.id.textRosarySeries);

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                fetchBibleVerse(LocalDate.parse(selectedDate));
            }
        });

        fetchBibleVerse(getSelectedDate());
    }

    private LocalDate getSelectedDate() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1;
        int day = datePicker.getDayOfMonth();
        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);
        return LocalDate.parse(selectedDate);
    }

    private void fetchBibleVerse(LocalDate selectedDate) {
        new Thread(() -> {
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            defaultClient.setBasePath("https://liturgy.day/api");

            DefaultApi apiInstance = new DefaultApi(defaultClient);

            try {
                DayResponse result = apiInstance.day(selectedDate);

                runOnUiThread(() -> {
                    textDate.setText(result.getDate().toString());
                    textSeason.setText(result.getSeason().getValue());
                    textSundayCycle.setText(result.getSundayCycle().getValue());
                    textWeekdayCycle.setText(result.getWeekdayCycle().getValue());
                    textLothVolume.setText(result.getLothVolume().getValue());
                    textRosarySeries.setText(result.getRosarySeries().getValue());
                });

            } catch (ApiException e) {
                Log.e(TAG, "API Error", e);
            }
        }).start();
    }
}