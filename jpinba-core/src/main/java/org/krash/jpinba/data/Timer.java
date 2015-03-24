package org.krash.jpinba.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Timer, measuring some useful operation
 * @author krash
 */
public class Timer {

    private boolean stopped;
    private final long startedMillis = System.nanoTime(); // autostart
    private float duration = 0;
    private final Set<ITag> tags = new HashSet<>();
    private int count = 1;

    public Timer() {
    }

    /**
     * @param duration pre-defined
     */
    public Timer(float duration) {
        stopped = true;
        this.duration = duration;
    }

    /**
     * @param tags
     */
    public Timer(Collection<ITag> tags) {
        this.tags.addAll(tags);
    }

    /**
     * Polyadic constructor with tags
     *
     * @param tags list of tags
     */
    public Timer(ITag... tags) {
        Collections.addAll(this.tags, tags);
    }

    /**
     * @param duration
     * @param tags
     */
    public Timer(float duration, Collection<ITag> tags) {
        this(duration);
        this.tags.addAll(tags);
    }

    /**
     * @param duration
     * @param tags
     */
    public Timer(float duration, ITag... tags) {
        this(duration);
        Collections.addAll(this.tags, tags);
    }

    /**
     * Stop timer
     */
    public void stop() {
        if (!stopped) {
            stopped = true;
            duration = (System.nanoTime() - startedMillis) / 1e9f;
        }
    }

    /**
     * @return float value of seconds
     */
    public float getDuration() {
        stop();
        return duration;
    }

    /**
     * Mark timer with tag
     *
     * @param tag to attach
     */
    public void addTag(ITag tag) {
        this.tags.add(tag);
    }

    /**
     * @return all attached tags
     */
    public Collection<ITag> getTags() {
        return tags;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount()
    {
        ++count;
    }
}
