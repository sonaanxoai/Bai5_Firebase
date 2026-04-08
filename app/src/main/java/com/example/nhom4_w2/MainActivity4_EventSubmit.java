package com.example.nhom4_w2;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4_EventSubmit extends AppCompatActivity {

    CheckBox cbIphone, cbAndroid, cbWindow;
    RadioGroup rgGender;
    RatingBar ratingBar;
    Spinner spCountry, spUniversity;
    Button btnSubmit;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity4_event_submit);
        // hoặc đúng với tên layout bạn đang dùng

        cbIphone = findViewById(R.id.cbIphone);
        cbAndroid = findViewById(R.id.cbAndroid);
        cbWindow = findViewById(R.id.cbWindow);

        rgGender = findViewById(R.id.rgGender);
        ratingBar = findViewById(R.id.ratingBar);

        spCountry = findViewById(R.id.spCountry);
        spUniversity = findViewById(R.id.spUniversity);

        btnSubmit = findViewById(R.id.btnSubmit);
        tvResult = findViewById(R.id.tvResult);

        String[] countries = {"Vietnam", "Malaysia", "Thailand", "Singapore"};
        String[] universities = {"PTIT", "HUST", "FPT", "NEU"};

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                countries
        );

        ArrayAdapter<String> universityAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                universities
        );

        spCountry.setAdapter(countryAdapter);
        spUniversity.setAdapter(universityAdapter);

        btnSubmit.setOnClickListener(v -> {

            StringBuilder phone = new StringBuilder();
            if (cbIphone.isChecked()) phone.append("Iphone ");
            if (cbAndroid.isChecked()) phone.append("Android ");
            if (cbWindow.isChecked()) phone.append("Window Mobile ");

            int genderId = rgGender.getCheckedRadioButtonId();
            RadioButton rbGender = findViewById(genderId);
            String gender = rbGender.getText().toString();

            int romance = (int) ratingBar.getRating();

            String country = spCountry.getSelectedItem().toString();
            String university = spUniversity.getSelectedItem().toString();

            String result =
                    "Result:\n" +
                            "Phone: " + phone.toString().trim() + "\n" +
                            "Gender: " + gender + "\n" +
                            "Romance: " + romance + "\n" +
                            "Country: " + country + "\n" +
                            "University: " + university;

            tvResult.setText(result);
        });
    }
}
