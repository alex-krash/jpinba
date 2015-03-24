package org.krash.jpinba.data;

/**
 * Tag, that can be added to Request
 * @author krash
 */
public class Tag implements ITag {

    private final ITagName name;
    private final String value;

    public Tag(ITagName name, String value) {
        this.name = name;
        this.value = value;
    }

    public Tag(String name, String value) {
        this(new TagName(name), value);
    }

    public ITagName getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
