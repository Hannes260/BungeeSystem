package de.hannezhd.bungeeSystem2.Listener;

import com.velocitypowered.api.event.EventHandler;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

public class DisconnectListener {

    private final ProxyServer server;

    public DisconnectListener(ProxyServer server) {
        this.server = server;
    }

    @Subscribe
    public void onPlayerDisconnect(DisconnectEvent event) {
        Player player = event.getPlayer();
        String disconnectMessage = "§c☠ §f§l" + player.getUsername() + " §c☠ §fhat den Server verlassen.";

        // Broadcast an alle Spieler senden
        for (Player p : server.getAllPlayers()) {
            p.sendMessage(Component.text(disconnectMessage));
        }
    }
}