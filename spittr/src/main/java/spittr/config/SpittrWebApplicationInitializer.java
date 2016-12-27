package spittr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import spittr.global.Constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("spring.profiles.default", Constants.DEV);
        servletContext.setInitParameter("spring.profiles.active", Constants.QA);
        super.onStartup(servletContext);
    }
}
