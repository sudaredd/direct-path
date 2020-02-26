package app.directpath.controller;

import app.directpath.service.DirectPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class DirectPathController {
    
    @Autowired
    private DirectPathService directPathService;
    
    @RequestMapping(method = RequestMethod.GET, value = "/connected")
    public String findDirectPath(@RequestParam Map<String, String> params) {
        String origin = params.get("origin");
        String destination = params.get("destination");
        if(origin == null || destination == null)
            return "Invalid request";
        return directPathService.isDirectPath(origin, destination);
    }
}
