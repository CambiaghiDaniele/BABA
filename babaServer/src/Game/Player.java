package Game;

public class Player {
    private int posX;
    private int posY;
    private int size;
    private String playerName;
    private int chunkX;
    private int chunkY;

    public Player(int posX, int posY, String playerName) {
        this.posX = posX;
        this.posY = posY;
        this.playerName = playerName;
        size = 250;
        chunkX = (int)(posX/1000);
        chunkY = (int)(posY/1000);
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public String getPlayerName() {
        return playerName;
    }
    public void setName(String playerName) {
        this.playerName = playerName;
    }
    public int getChunkX() {
        return chunkX;
    }

    private void updateChunkX() {
        chunkX = (int)(posX/100);
    }
    public int getChunkY() {
        return chunkY;
    }
    private void updateChunkY() {
        chunkY = (int)(posY/100);
    }

    public void move(boolean moveX, boolean moveY){
        if(moveX){
            if(size<500)
                posX += 500 / size;
            else
                posX += 1;
        }else{
            if(size<500)
                posX -= 500 / size;
            else
                posX -= 1;
        }
        if(moveY){
            if(size<500)
                posY += 500 / size;
            else
                posY += 1;
        }else{
            if(size<500)
                posY -= 500 / size;
            else
                posY -= 1;
        }
        updateChunkX();
        updateChunkY();
    }
}
