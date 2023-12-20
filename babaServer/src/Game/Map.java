package Game;

import java.util.Vector;

public class Map {
    public final int width = 50000;
    public final int height = 50000;
    private Chunk[][] chunks;

    public Map() {
        chunks = new Chunk[width / 1000][height / 1000];
        generateChunks();
    }

    private void generateChunks(){
        for(int y = 0; y < height / 1000; y++){
            for(int x = 0; x < width / 1000; x++){
                chunks[x][y] = new Chunk(x * 1000, y * 1000);
            }
        }
    }
    public void generatePoints(){
        for (Chunk chunkRow[]:chunks) {
            for(Chunk chunk:chunkRow) {
                chunk.generatePoint();
            }
        }
    }
    public Vector<Player> getPlayers(){
        Vector<Player> players = new Vector<>();
        for (Chunk chunkRow[]:chunks) {
            for(Chunk chunk:chunkRow) {
                players.addAll(chunk.getPlayers());
            }
        }
        return players;
    }
    public Chunk getChunk(int x, int y) {
        return chunks[x][y];
    }
}
