package org.krash.jpinba.data;

/**
 * Pair of key-value, attached to Timer
 * @author krash
 */
public interface ITag {

    /**
     * @return tag name
     */
    public ITagName getName();

    /**
     * @return value of tag
     */
    public String getValue();
}
