package com.zhiku.util.monitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;

@Component
public class ServletContextListener implements javax.servlet.ServletContextListener {

    @Value( "${file_path.visit_statistics}" )
    private String statisticsPath;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        VisitStatistics.setSaveFile(statisticsPath);
        VisitStatistics.run();
    }

}
