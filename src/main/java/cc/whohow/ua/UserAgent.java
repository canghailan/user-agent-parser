package cc.whohow.ua;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserAgent {
    private static final Pattern PRODUCT = Pattern.compile(
            "([^\"^(),/:;<=>?@[\\\\]{}\\s]+)(/([^\"^(),/:;<=>?@[\\\\]{}\\s]+))?(\\s*\\(([^)]*)\\))?");

    private final String userAgent;
    private List<Product> products;
    private Map<String, Map<String, String>> detections;

    public UserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    private UserAgent parse() {
        if (products != null) {
            return this;
        }
        products = new ArrayList<>();
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
        products = Collections.unmodifiableList(products);
        return this;
    }

    public List<Product> getProducts() {
        return parse().products;
    }

    public UserAgent detect() {
        return detect(Detectors.PRE_DEFINED);
    }

    public UserAgent detect(List<Detector> detectors) {
        if (detections != null) {
            return this;
        }
        detections = new HashMap<>();
        for (Detector detector : detectors) {
            Map<String, String> values = detector.detect(userAgent);
            if (values != null) {
                update(detector.getKey(), values);
            }
        }
        detections = Collections.unmodifiableMap(detections);
        return this;
    }

    private void update(String key, Map<String, String> values) {
        Map<String, String> detected = detections.get(key);
        if (detected == null
                || detected.size() < values.size()) {
            detections.put(key, values);
        }
    }

    public Map<String, Map<String, String>> getDetections() {
        return detect().detections;
    }

    public String toString() {
        parse();
        detect();
        StringBuilder buffer = new StringBuilder();
        for (Product product : products) {
            buffer.append(product).append(" ");
        }
        buffer.setLength(buffer.length() - 1);
        buffer.append("\n");
        buffer.append("product: \n");
        for (Product product : products) {
            buffer.append(product).append("\n");
        }
        buffer.append("detection: \n");
        for (Map.Entry<String, Map<String, String>> detection : detections.entrySet()) {
            for (Map.Entry<String, String> keyValues : detection.getValue().entrySet()) {
                buffer.append(detection.getKey()).append(".").append(keyValues.getKey()).append(": ");
                buffer.append(keyValues.getValue()).append("\n");
            }
        }
        return buffer.toString();
    }
}
