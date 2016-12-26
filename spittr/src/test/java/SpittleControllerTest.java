import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import spittr.config.DataConfig;
import spittr.domain.Spittle;
import spittr.config.RootConfig;
import spittr.config.WebConfig;
import spittr.data.SpittleRepository;
import spittr.web.SpittleController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by 273cn on 16/12/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class, DataConfig.class})
public class SpittleControllerTest {
    @Autowired
    SpittleController spittleController;

    @Test
    public void testSpittles() throws Exception {
        MockMvc mockMvc = standaloneSetup(spittleController).build();
        System.out.println(mockMvc.perform(get("/spittles")));
    }

    @Test
    public void testSpittle() throws Exception {
        SpittleController controller = new SpittleController();
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/views/spittle.jsp"))
                .build();
        mockMvc.perform(get("/spittles/12345"))
                .andExpect(view().name("spittle"))
                .andExpect(model().attributeExists("spittle"))
                .andExpect(model().attribute("spittle", notNull()));
    }
}
