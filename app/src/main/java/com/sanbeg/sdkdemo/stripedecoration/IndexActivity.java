package com.sanbeg.sdkdemo.stripedecoration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.sanbeg.sdk.stripedecoration.StripeDecoration;

public class IndexActivity extends AppCompatActivity {

    private RadioGroup orientation;
    private RadioGroup color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        orientation = (RadioGroup) findViewById(R.id.or);
        color = (RadioGroup) findViewById(R.id.color);
    }

    public void buttonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        switch (color.getCheckedRadioButtonId()) {
            case R.id.cr:
                intent.putExtra(MainActivity.KEY_COLOR, true);
        }

        int or = StripeDecoration.VERTICAL;
        switch (orientation.getCheckedRadioButtonId()) {
            case R.id.ov:
                or = StripeDecoration.VERTICAL;
                break;
            case R.id.oh:
                or = StripeDecoration.HORIZONTAL;
                break;
            case R.id.og:
                or = StripeDecoration.GRID;
                break;
        }
        intent.putExtra(MainActivity.KEY_ORIENTATION, or);

        startActivity(intent);
    }
}
