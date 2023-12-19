import Game.Chunk;
import Game.Player;
import Utility.JSONBuilder;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Player player;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            // Inizializza gli stream di input e output
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));
            writer = new PrintWriter(outputStream, true); // true abilita l'autoflush
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new Player((int)(Math.random()*10000), (int)(Math.random()*10000), "Name");
    }

    @Override
    public void run() {
        try {
            // Avvia un thread per la ricezione dei messaggi dal client
            Thread receiveThread = new Thread(this::receiveMessages);
            receiveThread.start();

            // Avvia un thread per l'invio di aggiornamenti al client
            Thread sendThread = new Thread(this::sendUpdates);
            sendThread.start();

            // Attendi che entrambi i thread terminino prima di chiudere la connessione
            receiveThread.join();
            sendThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Chiudi la connessione quando i thread terminano
            try {
                clientSocket.close();
                System.out.println("Connessione chiusa con " + clientSocket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void receiveMessages() {
        try {
            while (true) {
                messageActions(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try {
            while (true) {
                writer.println(JSONBuilder.buildJSON(getChunksNearby()));
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private Chunk[][] getChunksNearby(){
        Chunk[][] chunks = new Chunk[9][5];
        for(int y=-2; y <= 2; y++){
            for(int x=-4; x <= 4; x++){
                chunks[x][y] = Server.map.getChunk(player.getChunkX()+x, player.getChunkY()+y);
            }
        }
        return chunks;
    }
}
