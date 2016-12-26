package spittr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by 273cn on 16/12/13.
 */
public class SpittrWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    public String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    public Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class };
    }

    @Override
    public Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }
}
