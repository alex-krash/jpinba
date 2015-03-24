package org.krash.jpinba.data;

/**
 * Value object, handling
 * - overall time
 * - memory peak usage
 * - Utime
 * - Stime
 * User: krash
 * Date: 2/11/15
 * Time: 7:23 PM
 */
public class Measure {
    private float requestTime;
    private final int maxMemory;
    private final float utime;
    private final float stime;

    public static final Measure EMPTY = new Measure(0, 0, 0, 0);

    public Measure(float request_time, int max_memory, float utime, float stime) {
        this.requestTime = request_time;
        this.maxMemory = max_memory;
        this.utime = utime;
        this.stime = stime;
    }


    public void setRequestTime(float time) {
        requestTime = time;
    }

    public float getRequestTime() {
        return requestTime;
    }

    public int getMaxMemory() {
        return maxMemory;
    }

    public float getUtime() {
        return utime;
    }

    public float getStime() {
        return stime;
    }

    public static Measure createEmpty() {
        return new Measure(0,0,0,0);
    }
}
