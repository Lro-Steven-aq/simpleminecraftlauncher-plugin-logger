package org.lro.steven.aq.simpleminecraftlauncher.logger;

import org.lro.steven.aq.simpleminecraftlauncher.plugin.api.Plugin;
import org.lro.steven.aq.simpleminecraftlauncher.plugin.api.event.impl.LogEvent;
import org.lro.steven.aq.simpleminecraftlauncher.plugin.api.signalbus.SignalBus;
import org.slf4j.Logger;


public class LoggerPlugin implements Plugin {


    @Override
    public void start(SignalBus signalBus) {
        signalBus.connect("log.info", (e) -> {


            LogEvent event = (LogEvent) e;
            Logger logger = event.getLogger();


            logger.info(event.getMessage());
            IO.println("Implementation class: %s".formatted(logger.getClass().getName()));

        });
    }
}
