package Utility;

import Game.Chunk;
import Game.Player;
import Game.Point;

import java.util.Vector;

public class JSONBuilder {
    public static String buildJSON(Chunk[][] chunks){
        String jsonString = "";
        jsonString += playersBuilder(getPlayersFromChunks(chunks));
        jsonString += pointsBuilder(getPointsFromChunks(chunks));
        return jsonString;
    }
    private static String playersBuilder(Vector<Player> players){
        String playersString = "\"players\":[";
        for(Player player:players){
            playersString += "{\"x\":" + player.getPosX() + ",\"y\":" + player.getPosY() + ",\"size\":" + player.getSize() + ",\"name\":" + player.getPlayerName() + "},";
        }
        playersString += "\b],";
        return playersString;
    }
    private static String pointsBuilder(Vector<Point> points){
        String pointsString = "\"points\":[";
        for(Point point:points){
            pointsString += "{\"x\":" + point.getPosX() + ",\"y\":" + point.getPosY() + ",\"color\":" + point.getColor() + "},";
        }
        pointsString += "\b]";
        return pointsString;
    }
    private static Vector<Player> getPlayersFromChunks(Chunk[][] chunks){
        Vector<Player> players = new Vector<>();
        for (Chunk[] chunkRow:chunks) {
            for (Chunk chunk : chunkRow) {
                players.addAll(chunk.getPlayers());
            }
        }
        return players;
    }
    private static Vector<Point> getPointsFromChunks(Chunk[][] chunks){
        Vector<Point> points = new Vector<>();
        for (Chunk[] chunkRow:chunks) {
            for (Chunk chunk : chunkRow) {
                points.addAll(chunk.getPoints());
            }
        }
        return points;
    }
}
