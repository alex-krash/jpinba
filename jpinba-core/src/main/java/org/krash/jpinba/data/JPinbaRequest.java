package org.krash.jpinba.data;

import org.krash.jpinba.request.PinbaRequest;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Value-object, incapsulating data for single pinba request
 * @author krash
 */
public class JPinbaRequest {

    private final String hostName;
    private final String serverName;
    private final String scriptName;
    private final Measure measure;
    private AtomicInteger requestCount = new AtomicInteger(1);
    private int documentSize = 0;
    private int status = 200;
    private boolean isDocumentSizeInstalled = false;
    private String schema;

    private List<Timer> timers = Collections.synchronizedList(new ArrayList<Timer>());

    /**
     * @param hostName
     * @param serverName
     * @param scriptName
     * @param measure
     */
    public JPinbaRequest(String hostName, String serverName, String scriptName, Measure measure) {
        this.hostName = hostName;
        this.serverName = serverName;
        this.scriptName = scriptName;
        this.measure = measure;
    }

    public JPinbaRequest(String hostAndServerName, String scriptName) {
        this(hostAndServerName, scriptName, Measure.createEmpty());
    }

    public JPinbaRequest(String hostName, String serverName, String scriptName) {
        this(hostName, serverName, scriptName, Measure.createEmpty());
    }

    /**
     * Constructor with equal host-server
     *
     * @param hostAndServerName
     * @param scriptName
     * @param measure
     */
    public JPinbaRequest(String hostAndServerName, String scriptName, Measure measure) {
        this(hostAndServerName, hostAndServerName, scriptName, measure);
    }

    public void addTimers(Timer... timers) {
        Collections.addAll(this.timers, timers);
    }

    public void addTimers(Collection<Timer> timers) {
        this.timers.addAll(timers);
    }

    /**
     * Increment request count
     */
    public synchronized void incRequests() {
        requestCount.incrementAndGet();
    }

    /**
     * Set size of document, being processed
     * @param size
     */
    public void setDocumentSize(int size) {
        documentSize = size;
        isDocumentSizeInstalled = true;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setRequestTime(float requestTime) {
        this.measure.setRequestTime(requestTime);
    }

    /**
     * Fill builder with data to send
     *
     * @param builder Request body builder
     */
    public void initBuilder(PinbaRequest.RequestBody.Builder builder) {
        builder.setHostname(hostName);
        builder.setServerName(serverName);
        builder.setScriptName(scriptName);
        builder.setRequestCount(requestCount.intValue());
        builder.setDocumentSize(documentSize);
        builder.setStatus(status);

        if(null != schema) {
            builder.setSchema(schema);
        }

        builder.setMemoryPeak(measure.getMaxMemory());
        builder.setRequestTime(measure.getRequestTime());
        builder.setRuUtime(measure.getUtime());
        builder.setRuStime(measure.getStime());


        Collection<Timer> merged = mergeTimers(timers);
        Map<String, Integer> dictionary = new LinkedHashMap<>();

        for (Timer timer : merged) {
            timer.stop();

            builder.addTimerHitCount(timer.getCount());
            builder.addTimerValue(timer.getDuration());

            Map<Integer, Integer> tagMap = new LinkedHashMap<>();
            for (ITag tag : timer.getTags()) {
                String key = tag.getName().getName();
                String value = tag.getValue();
                Integer keyPosition, valuePosition;
                if (null == (keyPosition = dictionary.get(key))) {
                    keyPosition = dictionary.size();
                    dictionary.put(key, keyPosition);
                }
                if (null == (valuePosition = dictionary.get(value))) {
                    valuePosition = dictionary.size();
                    dictionary.put(value, valuePosition);
                }
                tagMap.put(keyPosition, valuePosition);
            }
            // list of ids of timer tags
            builder.addAllTimerTagName(tagMap.keySet());
            // list of ids of timer values
            builder.addAllTimerTagValue(tagMap.values());
            // total number of tags for
            builder.addTimerTagCount(timer.getTags().size());
        }
        builder.addAllDictionary(dictionary.keySet());
    }

    /**
     * Merge timers with similar tag values into smaller collection
     * @param timers to merge
     * @return merged collection
     */
    private Collection<Timer> mergeTimers(Collection<Timer> timers)
    {
        Map<Collection<ITag>,Timer> merged = new HashMap<>();
        for(Timer timer : timers)
        {
            Collection<ITag> tags = timer.getTags();
            Timer existing = merged.get(tags);
            if(null != existing) {
                existing = new Timer(existing.getDuration() + timer.getDuration(), tags);
                existing.incrementCount();
            } else {
                existing = timer;
            }
            merged.put(tags, existing);
        }
        return merged.values();
    }

    public boolean isDocumentSizeInstalled() {
        return isDocumentSizeInstalled;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
