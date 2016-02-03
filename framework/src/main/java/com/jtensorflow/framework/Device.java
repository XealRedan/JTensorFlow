package com.jtensorflow.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alexandre on 03/02/16.
 */
public class Device {

    private static final int NONE = -1;

    /** Optional device job name. */
    private String job;
    /**  Optional replica index. */
    private int replica;
    /** Optional task index. */
    private int task;
    /** Optional device type string (e.g. "CPU" or "GPU") */
    private String deviceType;
    /** Optional device index.  If left unspecified, device represents 'any' device_index. */
    private int deviceIndex;

    public Device() {
        //
    }

    public Device(String job, int replica, int task, String deviceType, int deviceIndex) {
        this.job = job;
        this.replica = replica;
        this.task = task;
        this.deviceType = deviceType.toUpperCase();
        this.deviceIndex = deviceIndex;
    }

    public void clear() {
        this.job = null;
        this.replica = NONE;
        this.task = NONE;
        this.deviceType = null;
        this.deviceIndex = NONE;
    }

    /**
     * Parse a Device name into its components.
     *
     * @param spec a string of the form
     * /job:<name>/replica:<id>/task:<id>/device:CPU:<id>
     * or
     * /job:<name>/replica:<id>/task:<id>/device:GPU:<id>
     * as cpu and gpu are mutually exclusive.
     * All entries are optional.
     * @return the Device, for convenience.
     * @throws IllegalArgumentException if the spec was not valid.
     */
    public Device parseFromString(String spec) {
        this.clear();

        final List<List<String>> splits = new ArrayList<>();
        for(final String s : spec.split("/")) {
            splits.add(Arrays.asList(s.split(":")));
        }

        try {
            for (final List<String> y : splits) {
                final int ly = y.size();
                if (y != null && !y.isEmpty()) {
                    if (ly == 2 && y.get(0).equals("job")) {
                        this.setJob(y.get(1));
                    } else if (ly == 2 && y.get(0).equals("replica")) {
                        this.setReplica(Integer.parseInt(y.get(1)));
                    } else if (ly == 2 && y.get(0).equals("task")) {
                        this.setTask(Integer.parseInt(y.get(1)));
                    } else if ((ly == 2  || ly == 1) && ((y.get(0).toUpperCase().equals("GPU") || y.get(0).toUpperCase().equals("CPU")))) {
                        if(this.deviceType != null) {
                            throw new IllegalArgumentException("Cannot specify multiple device types: " + spec);
                        }
                        this.setDeviceType(y.get(0).toUpperCase());

                        if(ly == 2 && !y.get(1).equals("*")) {
                            this.setDeviceIndex(Integer.parseInt(y.get(1)));
                        }
                    } else if (ly == 3 && y.get(0).equals("device")) {
                        if(this.deviceType != null) {
                            throw new IllegalArgumentException("Cannot specify multiple device types: " + spec);
                        }
                        this.setDeviceType(y.get(1).toUpperCase());

                        if(!y.get(2).equals("*")) {
                            this.setDeviceIndex(Integer.parseInt(y.get(2)));
                        }
                    } else if (ly > 0 && !y.get(0).isEmpty()) {
                        throw new IllegalArgumentException("Unknown attribute: '" + y.get(0) + "' in '" + spec + "'");
                    }
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }

        return this;
    }

    /**
     * Merge the properties of "dev" into this Device.
     * @param dev a Device.
     */
    public void merge(Device dev) {
        if(dev.job != null) {
            this.setJob(dev.job);
        }
        if(dev.replica != NONE) {
            this.setReplica(dev.replica);
        }
        if(dev.task != NONE) {
            this.setTask(dev.task);
        }
        if(dev.deviceType != null) {
            this.setDeviceType(dev.deviceType);
        }
        if(dev.deviceIndex != NONE) {
            this.setDeviceIndex(dev.deviceIndex);
        }
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getReplica() {
        return replica;
    }

    public void setReplica(int replica) {
        this.replica = replica;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getDeviceIndex() {
        return deviceIndex;
    }

    public void setDeviceIndex(int deviceIndex) {
        this.deviceIndex = deviceIndex;
    }

    /**
     * Return a Device specification string.
     * @return a string of the form /job:<name>/replica:<id>/task:<id>/device:cpu:<id>
     *     or /job:<name>/replica:<id>/task:<id>/device:cpu:<id>.
     */
    @Override
    public String toString() {
        String dev = "";
        if(this.job != null) {
            dev += "/job:" + this.job;
        }
        if(this.replica != NONE) {
            dev += "/replica:" + this.replica;
        }
        if(this.task != NONE) {
            dev += "/task:" + this.task;
        }
        if(this.deviceType != null) {
            dev += "/device:" + this.deviceType + ":" + (this.deviceIndex != NONE ? this.deviceIndex : "");
        }

        return dev;
    }

    /**
     * Construct a Device from a string.
     *
     * @param spec a string of the form
     * /job:<name>/replica:<id>/task:<id>/device:CPU:<id>
     * or
     * /job:<name>/replica:<id>/task:<id>/device:GPU:<id>
     * as cpu and gpu are mutually exclusive.
     * All entries are optional.
     * @return the Device, for convenience.
     * @throws IllegalArgumentException if the spec was not valid.
     */
    public static Device fromString(String spec) {
        return new Device().parseFromString(spec);
    }

    /**
     * Check that a device spec is valid.
     * @param spec a string.
     * @throws IllegalArgumentException thrown if the spec is invalid
     */
    public static void checkValid(String spec) {
        fromString(spec);
    }

    // TODO
    public static Device mergeDevice(String spec) {
        throw new IllegalStateException("Not yet implemented");
    }

    // TODO
    public static Device mergeDevice(Device dev) {
        throw new IllegalStateException("Not yet implemented");
    }
}
