package com.sanbeg.sdkdemo.stripedecoration;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanbeg.java.numbertext.NumberText;
import com.sanbeg.java.numbertext.NumberTextEn;
import com.sanbeg.sdk.stripedecoration.StripeDecoration;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_ORIENTATION = "orientation";
    public static final String KEY_COLOR = "color";

    static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

    int itemLayout = R.layout.item_text;

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private final NumberText numberText = new NumberTextEn();

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(itemLayout, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(numberText.number(position+1));
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setAdapter(new MyAdapter());
        StripeDecoration decoration = new StripeDecoration(
                new PaintDrawable(ContextCompat.getColor(this, R.color.neutralStripe))
        );

        Intent intent = getIntent();

        if (intent.getBooleanExtra(KEY_COLOR, false)) {
            int[] rbc = {
                    0xAAFF0000,
                    0xAAFF7F00,
                    0xAAFFFF00,
                    0xAA00FF00,
                    0xAA0000FF,
                    0xAA4B0082,
                    0xAA8F00FF,
                    Color.TRANSPARENT
            };

            PaintDrawable[] rbp = new PaintDrawable[rbc.length];
            for (int i = 0; i < rbc.length; ++i) {
                rbp[i] = new PaintDrawable(rbc[i]);
            }
            decoration.setDrawables(rbp);
        }

        LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        switch(intent.getIntExtra(KEY_ORIENTATION, StripeDecoration.VERTICAL)) {
            case StripeDecoration.VERTICAL:
                decoration.setOrientation(layoutManager.getOrientation());
                break;
            case StripeDecoration.HORIZONTAL:
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                decoration.setOrientation(layoutManager.getOrientation());
                break;
            case StripeDecoration.GRID:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                decoration.setOrientation(StripeDecoration.GRID);
                itemLayout = R.layout.grid_cell;
        }

        recyclerView.addItemDecoration(decoration);
    }
}
