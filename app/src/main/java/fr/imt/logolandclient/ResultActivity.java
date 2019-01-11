package fr.imt.logolandclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ResultActivity extends AppCompatActivity {

    private EditText numberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int imgSearchId = getIntent().getExtras().getInt(MainActivity.ID_IMG_SEARCH_KEY);
        numberText = findViewById(R.id.numberText);
        numberText.setText(""+imgSearchId);

    }
}
