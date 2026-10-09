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

/**
 * The logger plugin was provided officially to provide generally log service to all the plugins and the main program.
 * <br/>
 * @author lro.steven.aq
 * @since 0.1
 */
public class LoggerPlugin implements Plugin {

    private static LoggerContext context;
    private static Logger LOGGER;


    @Override
    public void start(SignalBus signalBus) {

        initialize();

        signalBus.connect("log.info", (e) -> {
            LogEvent event = (LogEvent) e;
            LOGGER.info(event.getMessage());
        });

        signalBus.connect("log.error", (e) -> {
            LogEvent event = (LogEvent) e;
            LOGGER.error(event.getMessage());
        });

        signalBus.connect("log.warn", (e) -> {
            LogEvent event = (LogEvent) e;
            LOGGER.warn(event.getMessage());
        });

        signalBus.connect("log.debug", (e) -> {
            LogEvent event = (LogEvent) e;
            LOGGER.debug(event.getMessage());
        });

        signalBus.connect("log.fatal", (e) -> {
            LogEvent event = (LogEvent) e;
            LOGGER.fatal(event.getMessage());
        });

        signalBus.connect("log.trace", (e) -> {
            LogEvent event = (LogEvent) e;
            LOGGER.trace(event.getMessage());
        });
    }

    @Override
    public void stop() {
        Plugin.super.stop();
        if (context != null) {
            context.stop();
            context = null;
            LOGGER = null;
        }
    }

    private Logger logger() {
        return LOGGER;
    }

    private void initialize() {

        //  /////////////////  init context  ////////////////////
        if (context == null) {
            try {
                InputStream stream = getClass().getClassLoader().getResourceAsStream("log4j2.xml");
                assert stream != null;
                ConfigurationSource source = new ConfigurationSource(stream);
                context = Configurator.initialize("Logger-Plugin-Context", getClass().getClassLoader(), source.getURI());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // ///////////////// end of init context  //////////////////
        // ///////////////// init LOGGER  /////////////////////////
        if (LOGGER == null) {
            LOGGER = context.getLogger("Logger-Plugin");
        }
        // ////////////////end of init LOGGER //////////////////////

    }
}
