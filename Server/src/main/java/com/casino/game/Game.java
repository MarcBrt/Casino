package com.casino.game;

import com.casino.entity.Hand;
import com.casino.enums.Games;
import com.casino.entity.Player;

import java.net.Socket;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import xyz.baddeveloper.lwsl.server.SocketHandler;

public abstract class Game extends Thread{

    private final UUID gameId;
    private String name;
    private List<Player> players = new ArrayList<>();
    private HashMap<Player, Hand> playerCard = new HashMap<Player, Hand>();
    private long createdAt;
    protected int maxPlayer = 1;
    protected int preGameCounter;
    protected int miseTimer = 30;
    private HashMap<Player, Integer> playerMise = new HashMap<>();

    public Game() {
        this.gameId = UUID.randomUUID();
        this.name = this.gameId.toString();
        this.createdAt = Time.from(Instant.now()).getTime();
    }

    public Game(String name) {
        this();
        this.name = name;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getGameName() {
        return name;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(String playerName) {
        this.players.removeIf(player -> player.getUsername().equals(playerName));
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public HashMap<Player, Hand> getPlayerCard() {
        return this.playerCard;
    }

    public HashMap<Player, Integer> getPlayerMises() {
        return this.playerMise;
    }

    public boolean isInGame(SocketHandler socket) {
        for (Player player : players) {
            if (player.getSocket().equals(socket)) {
                return true;
            }
        }

        return false;
    }

    public boolean isInGame(Socket socket) {
        for (Player player : players) {
            if (player.getSocket().getSocket().equals(socket)) {
                return true;
            }
        }

        return false;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    /**
     * Get game Type
     * @return
     */
    public abstract Games getType();

    public int getPreGameCounter() {
        return preGameCounter;
    }

    public int getMiseTimer() {
        return miseTimer;
    }
}
