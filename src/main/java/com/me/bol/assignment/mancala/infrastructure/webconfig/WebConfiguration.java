package com.me.bol.assignment.mancala.infrastructure.webconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@Slf4j
public class WebConfiguration implements WebMvcConfigurer {

  /**
   * all the jsp pages are available under the specified Prefix address
   */
  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    InternalResourceViewResolver bean = new InternalResourceViewResolver();
    bean.setPrefix("/WEB-INF/pages/");
    bean.setSuffix(".jsp");
    bean.setViewClass(JstlView.class);
    registry.viewResolver(bean);
  }

  /**
   * Registers the resources
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").
        addResourceLocations("/static/"
            , "classpath:/static/");

  }
}
