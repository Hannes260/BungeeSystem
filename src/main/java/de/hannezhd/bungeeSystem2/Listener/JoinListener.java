package de.hannezhd.bungeeSystem2.Listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

public class JoinListener {
    private final ProxyServer server;
    public JoinListener(ProxyServer server) {
        this.server = server;
    }
    @Subscribe
    public void onPlayerJoin(ServerPostConnectEvent event) {
        Player player = event.getPlayer();
        String joinMessage = "§c☠ §f§l" + player.getUsername() + " §c☠ §fhat den Server betreten.";

        for (Player p : server.getAllPlayers()) {
            p.sendMessage(Component.text(joinMessage));
        }
    }
}
