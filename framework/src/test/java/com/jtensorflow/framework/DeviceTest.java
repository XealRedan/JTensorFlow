package com.jtensorflow.framework;

import org.junit.Test;

/**
 * Created by alexandre on 03/02/16.
 */
public class DeviceTest {
    @Test
    public void testEmpty() {
        assertEquals("", new Device().toString());
        assertEquals("", new Device().parseFromString(""));
    }

    @Test
    public void testConstructor() {
        final Device d = new Device("j", 0, 1, "CPU", 2);

        assertEquals("j", d.getJob());
        assertEquals(0, d.getReplica());
        assertEquals(1, d.getTask());
        assertEquals("CPU", d.getDeviceType());
        assertEquals(2, d.getDeviceIndex());
        assertEquals("/job:j/replica:0/task:1/device:CPU:2", d.toString());
    }

    @Test
    public void testToString() {
        // TODO
        throw new IllegalArgumentException("Not yet implemented");
    }

    @Test
    public void testParseFromString() {
        // TODO
        throw new IllegalArgumentException("Not yet implemented");
    }

    @Test
    public void testFromString() {
        // TODO
        throw new IllegalArgumentException("Not yet implemented");
    }

    @Test
    public void testMerge() {
        // TODO
        throw new IllegalArgumentException("Not yet implemented");
    }

    @Test
    public void testCheckValid() {
        // TODO
        throw new IllegalArgumentException("Not yet implemented");
    }
}
