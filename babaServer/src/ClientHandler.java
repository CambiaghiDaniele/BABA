import Game.Chunk;
import Game.Player;
import Utility.JSONBuilder;

import javax.websocket.OnMessage;
import javax.websocket.Session;

public class ClientHandler implements Runnable {
    private Player player;

    public ClientHandler() {
        player = new Player((int)(Math.random()*Server.map.width), (int)(Math.random()*Server.map.height), "Name");
    }

    @Override
    public void run() {
        sendUpdates();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @OnMessage
    private void receiveMessages(String message, Session session) {
        messageActions(message);
    }
    private void messageActions(String message){
        String action = message.split(",")[0];
        switch (action.substring(action.indexOf(":"))){
            case "PlayerUpdate":
                actionPlayerUpdate(message.split(",")[1], message.split(",")[2]);
                break;
        }
    }
    private void actionPlayerUpdate(String movement1, String movement2){
        boolean moveX = Boolean.parseBoolean(movement1.substring(movement1.indexOf(":")));
        boolean moveY = Boolean.parseBoolean(movement2.substring(movement2.indexOf(":")));
        player.move(moveX, moveY);
    }
    private void sendUpdates() {
        JSONBuilder.buildJSON(getChunksNearby());
    }
    private Chunk[][] getChunksNearby(){
        Chunk[][] chunks = new Chunk[9][5];
        for(int y=-2; y <= 2; y++){
            for(int x=-4; x <= 4; x++){
                chunks[x][y] = coordinateAdjustment(Server.map.getChunk(player.getChunkX()+x, player.getChunkY()+y));
            }
        }
        return chunks;
    }
    private Chunk coordinateAdjustment(Chunk chunk){
        if(chunk.chunkX!= player.getChunkX()&&chunk.chunkY!= player.getChunkY()) {
            for (int i = 0; i < chunk.getPlayers().size(); i++) {

            }
        }
        return chunk;
    }
}
