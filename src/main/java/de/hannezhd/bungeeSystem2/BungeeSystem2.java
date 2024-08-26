package de.hannezhd.bungeeSystem2;

import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.plugin.Plugin;
import de.hannezhd.bungeeSystem2.Commands.*;
import de.hannezhd.bungeeSystem2.Listener.DisconnectListener;
import de.hannezhd.bungeeSystem2.Listener.JoinListener;

import javax.inject.Inject;
import java.util.logging.Logger;

@Plugin(id = "bungeesystem2", name = "BungeeSystem2", version = "1.0-SNAPSHOT")
public class BungeeSystem2 {

    private final ProxyServer server;
    private final Logger logger;
    private final EventManager eventManager;
    private final CommandManager commandManager;

    @Subscribe
    public void onProxyinitialization(ProxyInitializeEvent event) {
        // Initialisierung hier durchführen
        logger.info("BungeeSystem2 Plugin enabled!");

        // Registrierung von Events
        eventManager.register(this, new JoinListener(server));
        eventManager.register(this, new DisconnectListener(server));

        // Registrierung von Commands
        CommandMeta msgCommandMeta = commandManager.metaBuilder("msg")
                .aliases("w", "whisper")
                .build();

        commandManager.register(msgCommandMeta, new MsgCommand(server));
        commandManager.register("zauberwald", new ZauberWaldCommand(server));
        commandManager.register("farmwelt", new FarmWeltCommand(server));
        commandManager.register("bauwelt", new BauWeltCommand(server));
        commandManager.register("broadcast", new BroadcastCommand(server));

    }

    @Inject
    public BungeeSystem2(ProxyServer server, Logger logger, EventManager eventManager, CommandManager commandManager) {
        this.server = server;
        this.logger = logger;
        this.eventManager = eventManager;
        this.commandManager = commandManager;

    }

}