import Game.Chunk;
import Game.Player;
import Game.Point;

public class GameManager implements Runnable{
    public GameManager() {

    }
    @Override
    public void run() {
        while(true){
            Thread pointGenerator = new Thread(this::generatePoints);
            checkCollision();
        }
    }
    private void generatePoints(){
        Server.map.generatePoints();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void checkCollision(){
        for (Player player:Server.map.getPlayers()) {
            Chunk[][] chunksToCheck = getChunksToCheck(player);
            checkPointCollision(player, chunksToCheck);
            checkPlayerCollision(player, chunksToCheck);
        }
    }
    private Chunk[][] getChunksToCheck(Player player){
        Chunk[][] chunksToCheck = new Chunk[3][3];
        for(int y=-1; y <= 1; y++){
            for(int x=-1; x <= 1; x++){
                chunksToCheck[x][y] = Server.map.getChunk(player.getChunkX()+x, player.getChunkY()+y);
            }
        }
        return chunksToCheck;
    }
    private void checkPointCollision(Player player, Chunk[][] chunks){
        for (Chunk[] chunkRow:chunks) {
            for(Chunk chunk:chunkRow){
                for (Point point:chunk.getPoints()) {
                    if(point.getPosX()>player.getPosX() - player.getSize()&&point.getPosX()<player.getPosX() + player.getSize()&&point.getPosY()>player.getPosY() - player.getSize()&&point.getPosY()<player.getPosY() + player.getSize()){
                        chunk.deletePoint(point.getPosX(), point.getPosY());
                        player.setSize(player.getSize()+1);
                    }
                }
            }
        }
    }
    private void checkPlayerCollision(Player player, Chunk[][] chunks){
        for (Chunk[] chunkRow:chunks) {
            for(Chunk chunk:chunkRow){
                for (Player otherPlayer:chunk.getPlayers()) {
                    if(otherPlayer.getSize()<player.getSize()&&otherPlayer.getPosX()>player.getPosX() - player.getSize()&&otherPlayer.getPosX()<player.getPosX() + player.getSize()&&otherPlayer.getPosY()>player.getPosY() - player.getSize()&&otherPlayer.getPosY()<player.getPosY() + player.getSize()){
                        chunk.deletePlayer(otherPlayer.getPosX(), otherPlayer.getPosY());
                        player.setSize(player.getSize()+otherPlayer.getSize()/2);
                    }
                }
            }
        }
    }
}
