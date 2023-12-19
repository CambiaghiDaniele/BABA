import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class GameEndpoint {

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WebSocket connection opened: " + session.getRemoteAddress().getHostName());
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        System.out.println("WebSocket message received: " + message);
        // Puoi gestire il messaggio ricevuto dal client JavaScript
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        System.out.println("WebSocket connection closed: " + session.getRemoteAddress().getHostName() + ", Reason: " + reason);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("WebSocket error on session " + session.getRemoteAddress().getHostName() + ": " + throwable.getMessage());
    }
}
