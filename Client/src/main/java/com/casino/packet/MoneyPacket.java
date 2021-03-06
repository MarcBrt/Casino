package com.casino.packet;

import xyz.baddeveloper.lwsl.packet.Packet;

import java.util.UUID;

public class MoneyPacket extends Packet {

    public MoneyPacket(int money, UUID id) {
        getObject().put("packetId", "defineMoney");
        getObject().put("money", money);
        getObject().put("gameId", id);
    }
}
