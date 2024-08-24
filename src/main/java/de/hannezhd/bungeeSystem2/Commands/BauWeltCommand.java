package de.hannezhd.bungeeSystem2.Commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;

import java.util.Optional;

public class BauWeltCommand implements SimpleCommand {
    private final ProxyServer proxyServer;

    public BauWeltCommand(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (source instanceof Player) {
            if (args.length == 0) {
                String targetServerName = "bauwelt";

                Optional<RegisteredServer> serverInfoOpt = proxyServer.getAllServers().stream()
                        .filter(serverInfo -> serverInfo.getServerInfo().getName().equalsIgnoreCase(targetServerName))
                        .findFirst();
                if (serverInfoOpt.isPresent()) {
                    Player player = (Player) source;
                    player.createConnectionRequest(serverInfoOpt.get()).fireAndForget();
                    source.sendMessage(Component.text("§aDu wirst auf den Server §6" + targetServerName + " §ageleitet."));
                }else {
                    source.sendMessage(Component.text("§cServer §6" + targetServerName + " §cnicht gefunden."));
                }
            }else {
                source.sendMessage(Component.text("§cBenutzung: /zauberwald"));
            }
        }else {
            source.sendMessage(Component.text("§cDieser Befehl kann nur von Spielern verwendet werden."));
        }
    }
}
