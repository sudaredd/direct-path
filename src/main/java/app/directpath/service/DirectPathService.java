package app.directpath.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class DirectPathService {

    Logger log = LoggerFactory.getLogger(DirectPathService.class);

    private Map<String, Set<String>> graph = new HashMap<>();

    public final static String YES = "yes";

    public final static String NO = "no";

    @PostConstruct
    public void init() {
        try {
            File file = ResourceUtils.getFile("classpath:city.txt");
            try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    int index = line.indexOf(",");
                    if (index > 0) {
                        String city1 = line.substring(0, index).trim();
                        String city2 = line.substring(index + 1).trim();
                        graph.computeIfAbsent(city1, k -> new HashSet<>()).add(city2);
                        graph.computeIfAbsent(city2, k -> new HashSet<>()).add(city1);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error occurred while building a graph => " + e.getMessage());
            }
            log.info("" + graph);
        } catch (IOException e) {
            log.error("IOException", e);
        }

    }

    public String isDirectPath(String origin, String destination) {
        return isDirectPath(origin, destination, new HashSet<>());
    }

    public String isDirectPath(String origin, String destination, Set<String> visited) {
        if (!graph.containsKey(origin))
            return NO;

        if (graph.get(origin).contains(destination)) {
            return YES;
        }
        visited.add(origin);

        for (String city : graph.get(origin)) {
            if (!visited.contains(city)) {
                String res = isDirectPath(city, destination, visited);
                if (YES.equals(res)) {
                    return YES;
                }
            }
        }
        return NO;
    }
}
