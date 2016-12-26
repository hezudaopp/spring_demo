import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spittr.config.DataConfig;
import spittr.data.SpitterRepository;

import static org.junit.Assert.assertEquals;

/**
 * Created by 273cn on 16/12/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class})
public class SpitterRepositoryTest {
    @Autowired
    private SpitterRepository spitterRepository;

    @Test
    public void testNotAnRealMethod() {
        assertEquals(1L, spitterRepository.notAnRealMethod().longValue());
    }
}
