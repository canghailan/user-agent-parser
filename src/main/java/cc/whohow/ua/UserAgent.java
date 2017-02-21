package cc.whohow.ua;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserAgent {
    /**
     * PRODUCT = NAME /VERSION? COMMENTS?
     */
    private static final Pattern PRODUCT = Pattern.compile(
            "([^\"^(),/:;<=>?@[\\\\]{}\\s]+)(/([^\"^(),/:;<=>?@[\\\\]{}\\s]+))?(\\s*\\(([^)]*)\\))?");

    private final String userAgent;
    private final List<Product> products;

    public UserAgent(String userAgent) {
        this.userAgent = userAgent;
        List<Product> products = new ArrayList<>();
        Matcher matcher = PRODUCT.matcher(userAgent);
        while (matcher.find()) {
            String name = matcher.group(1);
            String version = matcher.group(3);
            String comment = matcher.group(5);
            List<String> comments = (comment == null) ? null :
                    Stream.of(comment.split(";"))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toList());
            products.add(new Product(name, version, comments));
        }
        this.products = Collections.unmodifiableList(products);
    }

    public List<Product> getProducts() {
        return products;
    }

    public String toString() {
        return userAgent;
    }
}
