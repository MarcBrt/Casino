package com.casino.server.event;

import com.casino.game.Game;
import com.casino.main.Main;
import xyz.baddeveloper.lwsl.packet.Packet;
import xyz.baddeveloper.lwsl.server.SocketHandler;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class JoinStatusEvent extends Event{
    @Override
    public void onPacketReceived(SocketHandler socket, Packet packet) {
        if (!packet.getObject().getString("packetId").equals("askInfoPacket")) {
            return;
        }

        Optional<Game> oGame = Main.gm.getGame(UUID.fromString(packet.getObject().getString("gameId")));

        if (oGame.isEmpty()) {
            return;
        }

        Game game = oGame.get();

        if (packet.getObject().getBoolean("isJoined")) {
            if (!game.isInGame(socket)) {
                game.addPlayer(Main.getPlayer(socket.getSocket()));
            }
        } else {
            game.removePlayer(Objects.requireNonNull(Main.getPlayer(socket.getSocket())).getUsername());
        }

    }
}