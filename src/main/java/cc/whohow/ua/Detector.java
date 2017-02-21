package cc.whohow.ua;

import java.util.Map;

public interface Detector {
    String getKey();

    Map<String, String> detect(UserAgent userAgent);
}
