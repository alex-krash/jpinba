package org.krash.jpinba.data;

/**
 * Simple implementation of tag name
 * @author krash
 */
public class TagName implements ITagName {

    private final String name;

    public TagName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
