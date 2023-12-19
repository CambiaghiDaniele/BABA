package Game;

import java.util.Vector;

public class Map {
    public final int width = 10000;
    public final int height = 10000;
    private Chunk[][] chunks;

    public Map() {
        chunks = new Chunk[width / 100][height / 100];
        generateChunks();
    }

    private void generateChunks(){
        for(int y = 0; y < height / 100; y++){
            for(int x = 0; x < width / 100; x++){
                chunks[x][y] = new Chunk(x * 100, y * 100);
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
