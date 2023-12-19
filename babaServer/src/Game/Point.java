package Game;

public class Point {
    private int posX;
    private int posY;
    private String color;

    public Point(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.color = generateColor();
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String generateColor(){
        int sel = (int)(Math.random()*5);
        switch (sel){
            case 0:
                return "red";
            case 1:
                return "blue";
            case 2:
                return "purple";
            case 3:
                return "green";
            case 4:
                return "yellow";
        }
        return "-1";
    }
}
