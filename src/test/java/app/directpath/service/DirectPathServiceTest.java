package app.directpath.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class DirectPathServiceTest {
    @Autowired
    private DirectPathService directPathService;

    @Test
    public void testTrueDirectPath() {
        String rs = directPathService.isDirectPath("Boston", "Newark");
        assertEquals("yes", rs);
    } 
    @Test
    public void testTrueDirectPath2() {
        String rs = directPathService.isDirectPath("Boston", "Philadelphia");
        assertEquals("yes", rs);
    } 
    
    @Test
    public void testFalseDirectPath2() {
        String rs = directPathService.isDirectPath("Philadelphia", "Albany");
        assertEquals("no", rs);
    }
}
