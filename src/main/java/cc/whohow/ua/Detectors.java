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
    /**
     * Lazy Load
     */
    static class PreDefined {
        static final List<Detector> DETECTORS = loadClasspath("detector.yml");
    }

    public static List<Detector> getPreDefined() {
        return PreDefined.DETECTORS;
    }

    public static List<Detector> loadClasspath(String classpath) {
        return load(Thread.currentThread().getContextClassLoader().getResource(classpath));
    }

    /**
     * Load RegexDetector from URL
     */
    @SuppressWarnings("unchecked")
    public static List<Detector> load(URL url) {
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
