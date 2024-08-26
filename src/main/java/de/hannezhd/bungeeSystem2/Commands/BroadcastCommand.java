package de.hannezhd.bungeeSystem2.Commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import java.util.List;

public class BroadcastCommand implements SimpleCommand {

    private final ProxyServer server;

    public BroadcastCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        List<String> arguments = List.of(invocation.arguments());

        if (arguments.size() < 1) {
            source.sendMessage(Component.text("§cUsage: /broadcast <message>"));
            return;
        }

        String message = String.join(" ", arguments);
        broadcastToALLServers(message);

        source.sendMessage(Component.text("§aBroadcast Nachricht zu allen Servern gesendet"));
    }
    private void broadcastToALLServers(String message) {
        Component componentMessage = Component.text(message);
        for (RegisteredServer server : this.server.getAllServers()) {
            server.sendMessage(componentMessage);
        }
    }
}
