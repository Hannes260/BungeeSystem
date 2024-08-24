package de.hannezhd.bungeeSystem2.Commands;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MsgCommand implements SimpleCommand {

    private final ProxyServer proxyServer;

    public MsgCommand(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length < 2) {
            source.sendMessage(Component.text("§cBenutzung: /msg <Spieler> <Nachricht>"));
            return;
        }

        String targetPlayerName = args[0];
        Optional<Player> targetPlayerOpt = proxyServer.getPlayer(targetPlayerName);

        if (!targetPlayerOpt.isPresent()) {
            source.sendMessage(Component.text("§cSpieler §6" + targetPlayerName + " §cnicht gefunden."));
            return;
        }

        Player targetPlayer = targetPlayerOpt.get();

        if (source instanceof Player && targetPlayer.getUsername().equals(((Player) source).getUsername())) {
            source.sendMessage(Component.text("§cDu kannst dir selbst keine Nachrichten senden."));
            return;
        }

        String message = String.join(" ", args).substring(targetPlayerName.length()).trim();

        Component privateMessage = Component.text("§aVon §6" + ((Player) source).getUsername() + "§7: " + message);

        targetPlayer.sendMessage(privateMessage);
        source.sendMessage(Component.text("§aAn §6" + targetPlayerName + "§7: " + message));
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] args = invocation.arguments();

        Collection<Player> onlinePlayers = null;
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            onlinePlayers = proxyServer.getAllPlayers();
            return onlinePlayers.stream()
                    .map(Player::getUsername)
                    .filter(name -> name.toLowerCase().startsWith(prefix))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}