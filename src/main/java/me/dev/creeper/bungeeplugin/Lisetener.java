package me.dev.creeper.bungeeplugin;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collection;

public class Lisetener implements Listener {
    @EventHandler
    public void pluginmessage(PluginMessageEvent e) {
        if (e.getTag().equalsIgnoreCase("BungeeCord")) {
            Collection<ProxiedPlayer> player = BungeePlugin.getProxy_().getPlayers();
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));

            try {
                String channel = in.readUTF();
                if (channel.contains(":")) {
                    StringBuilder a = new StringBuilder();
                    StringBuilder b = new StringBuilder();
                    int number = 0;

                    int i;
                    for(i = 0; i < channel.length(); ++i) {
                        if (channel.charAt(i) == ':') {
                            number = i + 1;
                            break;
                        }

                        a.append(channel.charAt(i));
                    }

                    for(i = number; i < channel.length(); ++i) {
                        b.append(channel.charAt(i));
                    }

                    String player_name = a.toString();
                    String message = b.toString();
                    if (message.equals("uhc_lobby_t")) {
                        player.forEach((p) -> {
                            if (p.getDisplayName().equals(player_name)) {
                                p.connect(BungeePlugin.getProxy_().getServerInfo(BungeePlugin.getInstance().getConfig().getString("lobby-server")));
                            }

                        });
                    } else if (message.equals("uhc_again_t")) {
                        player.forEach((p) -> {
                            if (p.getDisplayName().equals(player_name)) {
                                p.connect(BungeePlugin.getProxy_().getServerInfo(BungeePlugin.getInstance().getConfig().getString("again-server-uhc")));
                            }

                        });
                    } else if (message.equals("bedwars_lobby_t")) {
                        player.forEach((p) -> {
                            if (p.getDisplayName().equals(player_name)) {
                                p.connect(BungeePlugin.getProxy_().getServerInfo(BungeePlugin.getInstance().getConfig().getString("lobby-server")));
                            }

                        });
                    } else if (message.equals("bedwars_again_t")) {
                        player.forEach((p) -> {
                            if (p.getDisplayName().equals(player_name)) {
                                p.connect(BungeePlugin.getProxy_().getServerInfo(BungeePlugin.getInstance().getConfig().getString("again-server-bedwars")));
                            }

                        });
                    }
                }
            } catch (IOException var10) {
                var10.printStackTrace();
            }
        }
    }
}
