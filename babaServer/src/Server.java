import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

public class Server {

    public static void main(String[] args) {
        Server server = new Server(8080);

        try {
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);

            // Configura il server WebSocket
            WebSocketServerContainerInitializer.configureContext(context).addEndpoint(GameEndpoint.class);

            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
