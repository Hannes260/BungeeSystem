package de.hannezhd.bungeeSystem2.Listener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class JoinListener {
    private final ProxyServer server;
    private final Set<UUID> joinedPlayers;
    private final Path configFilePath;
    private final Gson gson;

    public JoinListener(ProxyServer server) {
        this.server = server;
        this.joinedPlayers = new HashSet<>();
        this.configFilePath = Paths.get("plugins/BungeeSystem/joinedPlayers.json");
        this.gson = new Gson();
        createConfigDirectory();  // Sicherstellen, dass das Verzeichnis existiert
        loadJoinedPlayers();
    }

    @Subscribe
    public void onPlayerJoin(PostLoginEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        // Überprüfen, ob der Spieler schon einmal gejoint ist
        if (!joinedPlayers.contains(playerUUID)) {
            // Nachricht für den ersten Beitritt, die an alle Spieler gesendet wird
            String welcomeMessage = "§f╰┈➤     §3h§b§b§3e§b§b§3i§3ß§f§3e§b§b§3n §b§3w§b§b§3i§3r       §fˏˋ°•*§3⁀➷" + "\n        §9̗̀➛        ➛   §c☠ §9§l" + player.getUsername() +"§c☠      §3§lೃ" +
                    "\n                             §lೃ  §3herzlich Willkommen §lೃ";
            for (Player p : server.getAllPlayers()) {
                p.sendMessage(Component.text(welcomeMessage));
            }

            joinedPlayers.add(playerUUID);
            saveJoinedPlayers();
        }

        // Standard-Beitrittsnachricht
        String joinMessage = "§c☠ §f§l" + player.getUsername() + " §c☠ §7hat den Server betreten.";
        for (Player p : server.getAllPlayers()) {
            p.sendMessage(Component.text(joinMessage));
        }
    }

    private void createConfigDirectory() {
        try {
            Files.createDirectories(configFilePath.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadJoinedPlayers() {
        if (Files.exists(configFilePath)) {
            try (FileReader reader = new FileReader(configFilePath.toFile())) {
                Type type = new TypeToken<Set<UUID>>() {}.getType();
                Set<UUID> loadedPlayers = gson.fromJson(reader, type);
                if (loadedPlayers != null) {
                    joinedPlayers.addAll(loadedPlayers);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveJoinedPlayers() {
        try (FileWriter writer = new FileWriter(configFilePath.toFile())) {
            gson.toJson(joinedPlayers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
