package Game;

import java.util.Vector;

public class Chunk {
    public int starterX;
    public int starterY;
    public final int width = 1000;
    public final int height = 1000;
    private Vector<Player> players;
    private Vector<Point> points;

    public Chunk(int starterX, int starterY) {
        this.starterX = starterX;
        this.starterY = starterY;
        players = new Vector<>();
        points = new Vector<>();
    }

    public void generatePoint(){
        if((int)(Math.random()*100) < 100 - points.size()){
            points.add(new Point((int)(Math.random()*1000), (int)(Math.random()*1000)));
        }
    }
    public Vector<Player> getPlayers() {
        return players;
    }
    public Vector<Point> getPoints() {
        return points;
    }

    public void deletePlayer(int x, int y){
        for (int i=0; i<players.size(); i++){
            if(players.get(i).getPosX()==x&&players.get(i).getPosY()==y){
                players.remove(i);
            }
        }
    }
    public void deletePoint(int x, int y){
        for (int i=0; i<points.size(); i++){
            if(points.get(i).getPosX()==x&&points.get(i).getPosY()==y){
                points.remove(i);
            }
        }
    }
}
