package cc.whohow.ua;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.net.URL;

public class Transformer {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

    public static void main(String[] args) throws Exception {
        System.out.println(OBJECT_MAPPER.writeValueAsString(transformDeviceDetector()));
    }

    private static ArrayNode transformDeviceDetector() throws Exception {
        ArrayNode output = OBJECT_MAPPER.createArrayNode();
        output.addAll(transformDeviceDetector("os", "https://raw.githubusercontent.com/piwik/device-detector/master/regexes/oss.yml"));
        output.addAll(transformDeviceDetectorDevice("https://raw.githubusercontent.com/piwik/device-detector/master/regexes/device/cameras.yml"));
        output.addAll(transformDeviceDetectorDevice("https://raw.githubusercontent.com/piwik/device-detector/master/regexes/device/car_browsers.yml"));
        output.addAll(transformDeviceDetectorDevice("https://raw.githubusercontent.com/piwik/device-detector/master/regexes/device/consoles.yml"));
        output.addAll(transformDeviceDetectorDevice("https://raw.githubusercontent.com/piwik/device-detector/master/regexes/device/mobiles.yml"));
        output.addAll(transformDeviceDetectorDevice("https://raw.githubusercontent.com/piwik/device-detector/master/regexes/device/portable_media_player.yml"));
        output.addAll(transformDeviceDetectorDevice("https://raw.githubusercontent.com/piwik/device-detector/master/regexes/device/televisions.yml"));
        output.addAll(transformDeviceDetectorBrowser("https://raw.githubusercontent.com/piwik/device-detector/master/regexes/client/browsers.yml"));
        output.addAll(transformDeviceDetector("renderingEngine", "https://raw.githubusercontent.com/piwik/device-detector/master/regexes/client/browser_engine.yml"));
        output.addAll(transformDeviceDetector("app", "https://raw.githubusercontent.com/piwik/device-detector/master/regexes/client/mobile_apps.yml"));
        output.addAll(transformDeviceDetector("library", "https://raw.githubusercontent.com/piwik/device-detector/master/regexes/client/libraries.yml"));
        output.addAll(transformDeviceDetectorBots("https://raw.githubusercontent.com/piwik/device-detector/master/regexes/bots.yml"));
        return output;
    }

    private static ArrayNode transformDeviceDetector(String key, String url) throws Exception {
        ArrayNode input = (ArrayNode) OBJECT_MAPPER.readTree(new URL(url));
        ArrayNode output = OBJECT_MAPPER.createArrayNode();
        for (JsonNode object : input) {
            ObjectNode r = OBJECT_MAPPER.createObjectNode();
            r.put("key", key);
            r.setAll((ObjectNode) object);
            output.add(r);
        }
        return output;
    }

    private static ArrayNode transformDeviceDetectorDevice(String url) throws Exception {
        ObjectNode input = (ObjectNode) OBJECT_MAPPER.readTree(new URL(url));
        ArrayNode output = OBJECT_MAPPER.createArrayNode();
        for (String vendor : (Iterable<String>) input::fieldNames) {
            ObjectNode r = OBJECT_MAPPER.createObjectNode();
            r.put("key", "device");
            r.put("vendor", vendor);
            r.setAll((ObjectNode) input.get(vendor));
            if (r.path("models").isMissingNode()) {
                output.add(r);
                continue;
            }
            ArrayNode models = (ArrayNode) r.remove("models");
            output.add(r);
            for (JsonNode model : models) {
                ObjectNode r2 = r.deepCopy();
                r2.setAll((ObjectNode) model);
                output.add(r2);
            }
        }
        return output;
    }

    private static ArrayNode transformDeviceDetectorBrowser(String url) throws Exception {
        ArrayNode input = (ArrayNode) OBJECT_MAPPER.readTree(new URL(url));
        ArrayNode output = OBJECT_MAPPER.createArrayNode();
        for (JsonNode object : input) {
            ObjectNode r = OBJECT_MAPPER.createObjectNode();
            r.put("key", "browser");
            r.setAll((ObjectNode) object);
            r.remove("engine");
            output.add(r);
        }
        return output;
    }

    private static ArrayNode transformDeviceDetectorBots(String url) throws Exception {
        ArrayNode input = (ArrayNode) OBJECT_MAPPER.readTree(new URL(url));
        ArrayNode output = OBJECT_MAPPER.createArrayNode();
        for (JsonNode object : input) {
            ObjectNode r = OBJECT_MAPPER.createObjectNode();
            r.put("key", "renderingEngine");
            r.setAll((ObjectNode) object);
            ObjectNode producer = (ObjectNode) r.remove("producer");
            if (producer != null) {
                r.set("producer", producer.path("name"));
            }
            output.add(r);
        }
        return output;
    }
}
