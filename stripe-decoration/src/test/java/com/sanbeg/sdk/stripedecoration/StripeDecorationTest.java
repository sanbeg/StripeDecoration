package com.sanbeg.sdk.stripedecoration;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

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
    static final int PARENT_HEIGHT = 234;
    static final int CHILD_HEIGHT = 12;

    static final int CHILD_WIDTH = 23;

    @Mock
    Drawable stripe;

    @Mock
    Drawable stripe2;

    @Mock
    Canvas c;

    @Mock View child0;
    @Mock View child1;
    @Mock View child2;


    @Mock View childv0;
    @Mock View childv1;
    @Mock View childv2;

    @Mock
    RecyclerView rv;

    @Mock
    RecyclerView horiz;

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

        when(horiz.getChildCount()).thenReturn(3);
        when(horiz.getHeight()).thenReturn(PARENT_HEIGHT);
        when(horiz.getChildAt(0)).thenReturn(childv0);
        when(horiz.getChildAt(1)).thenReturn(childv1);
        when(horiz.getChildAt(2)).thenReturn(childv2);

        when(childv0.getLeft()).thenReturn(0);
        when(childv0.getRight()).thenReturn(CHILD_WIDTH);
        when(childv1.getLeft()).thenReturn(CHILD_WIDTH);
        when(childv1.getRight()).thenReturn(2 * CHILD_WIDTH);
        when(childv2.getLeft()).thenReturn(2 * CHILD_WIDTH);
        when(childv2.getRight()).thenReturn(3 * CHILD_WIDTH);
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
    public void testOnDrawTwo() throws Exception {
        when(rv.getChildAdapterPosition(child0)).thenReturn(0);

        StripeDecoration decoration = new StripeDecoration(stripe);
        decoration.setDrawables(stripe, stripe2);
        decoration.onDraw(c, rv, null);
        verify(stripe).setBounds(0, 0, PARENT_WIDTH, CHILD_HEIGHT);
        verify(stripe2).setBounds(0, CHILD_HEIGHT, PARENT_WIDTH, 2 * CHILD_HEIGHT);
        verify(stripe).setBounds(0, 2 * CHILD_HEIGHT, PARENT_WIDTH, 3 * CHILD_HEIGHT);
        verify(stripe, times(2)).draw(c);
        verify(stripe2).draw(c);
    }


    @Test
    public void testOnDrawNoPosition() throws Exception {
        when(rv.getChildAdapterPosition(child0)).thenReturn(RecyclerView.NO_POSITION);

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


    @Test
    public void testOnDrawNextHoriz() throws Exception {
        when(horiz.getChildAdapterPosition(childv0)).thenReturn(1);
        StripeDecoration decoration = new StripeDecoration(stripe);
        decoration.setOrientation(StripeDecoration.HORIZONTAL);
        decoration.onDraw(c, horiz, null);
        verify(stripe).setBounds(0, 0, CHILD_WIDTH, PARENT_HEIGHT);
        verify(stripe).setBounds(2 * CHILD_WIDTH, 0, 3 * CHILD_WIDTH, PARENT_HEIGHT);

        verify(stripe, times(2)).draw(c);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadOrientation() {
        StripeDecoration decoration = new StripeDecoration(stripe);
        decoration.setOrientation(42);
    }

}