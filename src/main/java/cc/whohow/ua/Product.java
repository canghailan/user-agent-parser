package cc.whohow.ua;

import java.util.Collections;
import java.util.List;

public class Product {
    private String name;
    private String version;
    private List<String> comments;

    public Product(String name) {
        this(name, null, null);
    }

    public Product(String name, String version) {
        this(name, version, null);
    }

    public Product(String name, String version, List<String> comments) {
        this.name = name;
        this.version = version;
        this.comments = (comments == null) ? Collections.emptyList() : comments;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public List<String> getComments() {
        return comments;
    }

    public String toString() {
        return name
                + ((version == null) ? "" : "/" + version)
                + ((comments == null || comments.isEmpty()) ? "" : " (" + String.join("; ", comments) + ")");
    }
}
