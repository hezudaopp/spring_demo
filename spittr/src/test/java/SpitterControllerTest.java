import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import spittr.config.DataConfig;
import spittr.config.RootConfig;
import spittr.config.WebConfig;
import spittr.web.SpitterController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by 273cn on 16/12/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class, DataConfig.class})
public class SpitterControllerTest {
    @Autowired
    private SpitterController spitterController;

    @Test
    public void testRegister() throws Exception {
        MockMvc mockMvc = standaloneSetup(spitterController)
                .build();
        ResultActions result = mockMvc.perform(post("/spitters")
                .param("realName", "Jack Tang")
                .param("username", "jack")
                .param("password", "24hours")
                .param("mobileNo", "13345678902"));
        System.out.println(result.andReturn().getResponse().getContentAsString());;
    }

    @Test
    public void testProfile() throws Exception {
        MockMvc mockMvc = standaloneSetup(spitterController)
                .build();
        ResultActions result = mockMvc.perform(get("/spitters/jack"));
        System.out.println(result.andReturn().getResponse().getContentAsString());
    }
}
