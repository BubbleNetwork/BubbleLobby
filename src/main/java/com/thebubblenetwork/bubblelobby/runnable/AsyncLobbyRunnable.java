package com.thebubblenetwork.bubblelobby.runnable;

import com.thebubblenetwork.api.framework.plugin.util.BubbleRunnable;
import com.thebubblenetwork.api.global.bubblepackets.messaging.messages.request.ServerListRequest;
import com.thebubblenetwork.api.global.type.ServerType;
import com.thebubblenetwork.bubblelobby.BubbleLobby;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AsyncLobbyRunnable extends BubbleRunnable{
    private static ServerType lobbytype = ServerType.getType("Lobby");

    private BubbleLobby lobby;

    public AsyncLobbyRunnable(BubbleLobby lobby) {
        this.lobby = lobby;
        runTaskTimerAsynchronously(getLobby(), TimeUnit.SECONDS, 10);
    }

    public BubbleLobby getLobby() {
        return lobby;
    }

    @Override
    public void run() {
        try {
            getLobby().getNetwork().getPacketHub().sendMessage(getLobby().getNetwork().getProxy(), new ServerListRequest(lobbytype));
        } catch (IOException e) {
        }
    }
}
