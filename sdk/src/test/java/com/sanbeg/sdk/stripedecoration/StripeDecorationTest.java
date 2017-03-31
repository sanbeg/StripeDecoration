package com.sanbeg.sdk.stripedecoration;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 3/30/17.
 */
public class StripeDecorationTest {
    static final int PARENT_WIDTH = 123;
    static final int CHILD_HEIGHT = 12;

    @Mock
    Drawable stripe;
    @Mock
    Canvas c;
    @Mock
    View child0;
    @Mock View child1;
    @Mock View child2;

    @Mock
    RecyclerView rv;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(rv.getChildCount()).thenReturn(3);
        when(rv.getWidth()).thenReturn(PARENT_WIDTH);
        when(rv.getChildAt(0)).thenReturn(child0);
        when(rv.getChildAt(1)).thenReturn(child1);
        when(rv.getChildAt(2)).thenReturn(child2);

        when(child0.getTop()).thenReturn(0);
        when(child0.getBottom()).thenReturn(CHILD_HEIGHT);
        when(child1.getTop()).thenReturn(CHILD_HEIGHT);
        when(child1.getBottom()).thenReturn(2 * CHILD_HEIGHT);
        when(child2.getTop()).thenReturn(2 * CHILD_HEIGHT);
        when(child2.getBottom()).thenReturn(3 * CHILD_HEIGHT);
    }

    @Test
    public void testOnDrawTop() throws Exception {
        when(rv.getChildAdapterPosition(child0)).thenReturn(0);

        StripeDecoration decoration = new StripeDecoration(stripe);
        decoration.onDraw(c, rv, null);
        verify(stripe).setBounds(0, CHILD_HEIGHT, PARENT_WIDTH, 2 * CHILD_HEIGHT);
        verify(stripe).draw(c);
    }

    @Test
    public void testOnDrawNext() throws Exception {
        when(rv.getChildAdapterPosition(child0)).thenReturn(1);
        StripeDecoration decoration = new StripeDecoration(stripe);
        decoration.onDraw(c, rv, null);
        verify(stripe).setBounds(0, 0, PARENT_WIDTH, CHILD_HEIGHT);
        verify(stripe).setBounds(0, 2 * CHILD_HEIGHT, PARENT_WIDTH, 3 * CHILD_HEIGHT);

        verify(stripe, times(2)).draw(c);
    }

}