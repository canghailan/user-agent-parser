package cc.whohow.ua;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDetector implements Detector {
    private static final Pattern VAR = Pattern.compile("\\$(\\d+)");

    private String key;
    private Map<String, String> values;
    private Pattern regex;

    public RegexDetector(String key, Map<String, String> values, String regex) {
        this(key, values, Pattern.compile(regex));
    }

    public RegexDetector(String key, Map<String, String> values, Pattern regex) {
        this.key = key;
        this.values = values;
        this.regex = regex;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Map<String, String> detect(UserAgent userAgent) {
        return detect(userAgent.toString());
    }

    public Map<String, String> detect(String userAgent) {
        Matcher matcher = regex.matcher(userAgent);
        if (matcher.find()) {
            Map<String, String> result = new LinkedHashMap<>(values.size());
            for (Map.Entry<String, String> value : values.entrySet()) {
                if ("key".equals(value.getKey()) || "regex".equals(value.getKey())) {
                    continue;
                }
                result.put(value.getKey(), replace(matcher, value.getValue()));
            }
            return result;
        }
        return null;
    }

    /**
     * replace $1, $2 variables
     */
    private static String replace(Matcher matcher, String text) {
        Matcher var = VAR.matcher(text);
        StringBuffer buffer = null;
        while (var.find()) {
            if (buffer == null) {
                buffer = new StringBuffer();
            }
            int index = Integer.parseInt(var.group(1));
            String value =  matcher.group(index);
            var.appendReplacement(buffer, value == null  ? "" : value);
        }
        if (buffer != null) {
            var.appendTail(buffer);
            return buffer.toString();
        }
        return text;
    }
}
