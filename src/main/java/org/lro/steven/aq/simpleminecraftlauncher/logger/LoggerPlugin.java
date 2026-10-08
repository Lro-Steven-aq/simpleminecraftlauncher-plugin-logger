package org.lro.steven.aq.simpleminecraftlauncher.logger;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.lro.steven.aq.simpleminecraftlauncher.plugin.api.Plugin;
import org.lro.steven.aq.simpleminecraftlauncher.plugin.api.event.impl.LogEvent;
import org.lro.steven.aq.simpleminecraftlauncher.plugin.api.signalbus.SignalBus;

import java.io.IOException;
import java.io.InputStream;


public class LoggerPlugin implements Plugin {

    private Logger logger;


    @Override
    public void start(SignalBus signalBus) {
        signalBus.connect("log.info", (e) -> {
            LogEvent event = (LogEvent) e;
            execuate(() -> {
                logger.info(event.getMessage());
            });

        });
    }


    private void execuate(Runnable runnable) {

        ClassLoader old = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("log4j2.xml");
            if (stream == null) {throw new RuntimeException("Can't get configuration file: log4j2.xml in classpath");}
            ConfigurationSource source = new ConfigurationSource(stream);
            LoggerContext context = Configurator.initialize("Plugin-Context", null, source.getURI());
            logger = context.getLogger("org.lro.steven.aq.simpleminecraftlauncher.plugin");

            runnable.run();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            Thread.currentThread().setContextClassLoader(old);
        }
    }
}
