package app.directpath.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class DirectPathService {

    private Map<String, String> forwardMap;
    private Map<String, String> reverseMap;
    Logger log = LoggerFactory.getLogger(DirectPathService.class);

    @PostConstruct
    public void init() {
        try {
            File file = ResourceUtils.getFile("classpath:city.txt");
            forwardMap = Files.lines(Paths.get(file.getPath())).map(s -> s.split(",")).
                    collect(HashMap::new, (map, k) -> map.put(k[0], k[1].trim()), Map::putAll);
            reverseMap = Files.lines(Paths.get(file.getPath())).map(s -> s.split(",")).
                    collect(HashMap::new, (map, k) -> map.put(k[1].trim(), k[0]), Map::putAll);
            log.info("" + forwardMap);
            log.info("" + reverseMap);
        } catch (IOException e) {
            log.error("IOException", e);
        }

    }

    public String isDirectPath(String origin, String destination) {
        if (findPath(forwardMap, origin, destination)) return "yes";
        if (findPath(reverseMap, origin, destination)) return "yes";
        return "no";
    }

    private boolean findPath(Map<String,String> map, String origin, String destination) {
        String res = map.get(origin);
        while (res != null) {
            if(res.equals(destination))
                return true;
            res = map.get(res);
        }
        return false;
    }
}
