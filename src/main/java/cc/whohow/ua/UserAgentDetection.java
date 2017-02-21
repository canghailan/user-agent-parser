package cc.whohow.ua;

import java.util.*;

public class UserAgentDetection {
    private final UserAgent userAgent;
    private final Map<String, Map<String, String>> detections ;

    public UserAgentDetection(UserAgent userAgent) {
        this(userAgent, Detectors.getPreDefined());
    }

    public UserAgentDetection(UserAgent userAgent, List<Detector> detectors) {
        this.userAgent = userAgent;
        Map<String, Map<String, String>> detections = new LinkedHashMap<>();
        for (Detector detector : detectors) {
            Map<String, String> values = detector.detect(userAgent);
            if (values != null) {
                Map<String, String> detected = detections.get(detector.getKey());
                if (detected == null
                        || detected.size() < values.size()) {
                    detections.put(detector.getKey(), Collections.unmodifiableMap(values));
                }
            }
        }
        this.detections = Collections.unmodifiableMap(detections);
    }

    public Map<String, Map<String, String>> getDetections() {
        return detections;
    }

    public String get(String key, String subKey) {
       return detections.getOrDefault(key, Collections.emptyMap()).get(subKey);
    }

    public String getOsName() {
        return get("os", "name");
    }

    public String getOsVersion() {
        return get("os", "version");
    }

    public String getBrowserName() {
        return get("browser", "name");
    }

    public String getBrowserVersion() {
        return get("browser", "version");
    }

    public String getUserAgentName() {
        return get("userAgent", "name");
    }

    public String getUserAgentVersion() {
        return get("userAgent", "version");
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (Map.Entry<String, Map<String, String>> detection : detections.entrySet()) {
            for (Map.Entry<String, String> keyValues : detection.getValue().entrySet()) {
                buffer.append(detection.getKey()).append(".").append(keyValues.getKey()).append(": ");
                buffer.append(keyValues.getValue()).append("\n");
            }
        }
        return buffer.toString();
    }
}
