package com.sanbeg.sdk.stripedecoration;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by steve on 3/30/17.
 */

public class StripeDecoration extends RecyclerView.ItemDecoration {
    private final Drawable stripe;

    public StripeDecoration(Drawable stripe) {
        this.stripe = stripe;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int count = parent.getChildCount();
        if (count > 0) {
            int offset = parent.getChildAdapterPosition(parent.getChildAt(0));
            for (int i = 1 - offset % 2; i < count; i += 2) {
                View child = parent.getChildAt(i);
                stripe.setBounds(0, child.getTop(), parent.getWidth(), child.getBottom());
                stripe.draw(c);
            }
        }
    }
}
