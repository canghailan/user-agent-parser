package cc.whohow.ua;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Detectors {
    public static final List<Detector> PRE_DEFINED = loadPreDefined();

    private static List<Detector> loadPreDefined() {
        List<Detector> list = new ArrayList<>();
        list.addAll(loadClasspath("user-agent.yml"));
        list.addAll(loadClasspath("device-detector.yml"));
        list.addAll(loadClasspath("ua-parser.yml"));
        return list;
    }


    private static List<Detector> loadClasspath(String classpath) {
        return load(Thread.currentThread().getContextClassLoader().getResource(classpath));
    }

    @SuppressWarnings("unchecked")
    private static List<Detector> load(URL url) {
        try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
            List<Detector> list = new ArrayList<>();
            for (Map<String, String> def : (List<Map<String, String>>) new Yaml().load(reader)) {
                list.add(new RegexDetector(def.get("key"), def, def.get("regex")));
            }
            return Collections.unmodifiableList(list);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
