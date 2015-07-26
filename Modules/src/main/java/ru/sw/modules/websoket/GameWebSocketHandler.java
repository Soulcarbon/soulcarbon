package ru.sw.modules.websoket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.sw.modules.game.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameWebSocketHandler extends TextWebSocketHandler {

    public static Game activeGame = new Game();
    List<WebSocketSession> sessionList = new ArrayList<>();

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);

        session.sendMessage(new TextMessage("fsdgdsg"));
        System.err.println("Session open");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.err.println(message.getPayload().toString());
        session.sendMessage(new TextMessage("asfasf"));
    }

    @Override
    public synchronized void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        System.err.println("Session close");
    }


    public synchronized void updateSession() {
        for (WebSocketSession session : sessionList) {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(activeGame)));
            } catch (IOException e) {

            }
        }
    }
}
