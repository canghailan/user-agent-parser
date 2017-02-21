package cc.whohow.ua;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserAgentDetector implements Detector {
    @Override
    public String getKey() {
        return "userAgent";
    }

    @Override
    public Map<String, String> detect(UserAgent userAgent) {
        List<Product> products= userAgent.getProducts();
        Product last = products.get(products.size() - 1);
        Map<String, String> result = new LinkedHashMap<>(2);
        result.put("name", last.getName());
        result.put("version", last.getVersion());
        return result;
    }
}
