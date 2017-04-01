package com.sanbeg.sdk.stripedecoration;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by steve on 3/30/17.
 */

public class StripeDecoration extends RecyclerView.ItemDecoration {
    public static final int GRID = -1;
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private final Drawable stripe;

    private int orientation = VERTICAL;

    public StripeDecoration(Drawable stripe) {
        this.stripe = stripe;
    }


    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL && orientation != GRID) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL, VERTICAL or GRID");
        }
        this.orientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int count = parent.getChildCount();
        if (count > 0) {
            int offset = parent.getChildAdapterPosition(parent.getChildAt(0));
            for (int i = 1 - offset % 2; i < count; i += 2) {
                View child = parent.getChildAt(i);

                final int left;
                final int top;
                final int right;
                final int bottom;
                if (orientation == VERTICAL) {
                    left = 0;
                    right = parent.getWidth();
                } else {
                    left  = child.getLeft();
                    right = child.getRight();
                }

                if (orientation == HORIZONTAL) {
                    top = 0;
                    bottom = parent.getHeight();
                } else {
                    top    = child.getTop();
                    bottom = child.getBottom();
                }

                stripe.setBounds(left, top, right, bottom);
                stripe.draw(c);
            }
        }
    }
}
