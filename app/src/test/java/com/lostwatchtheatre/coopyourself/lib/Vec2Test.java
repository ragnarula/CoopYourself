package com.lostwatchtheatre.coopyourself.lib;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by rag on 06/02/2017.
 */
public class Vec2Test {
    @Test
    public void add() throws Exception {
        Vec2 v = new Vec2(1,1);

        v.add(v);

        assertEquals(v.getX(), 2);
        assertEquals(v.getY(), 2);

    }

    @Test
    public void subtract() throws Exception {
        Vec2 v = new Vec2(1,1);

        v.subtract(v);

        assertEquals(v.getX(), 0);
        assertEquals(v.getY(), 0);
    }

    @Test
    public void rotate() throws Exception {
        Vec2 v = new Vec2(1,0);

        v.rotate(90);

        assertEquals(v.getY(), -1);
        assertEquals(v.getX(), 0);
    }

}