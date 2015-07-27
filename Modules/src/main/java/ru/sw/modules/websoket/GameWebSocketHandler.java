package ru.sw.modules.websoket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.sw.modules.game.Game;
import ru.sw.modules.game.GameRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameWebSocketHandler extends TextWebSocketHandler {

    public static Game activeGame = new Game();
    List<WebSocketSession> sessionList = new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(GameWebSocketHandler.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private GameRepository gameRepository;

    @Override
    public synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if(activeGame.getId() == null) {
            gameRepository.create(activeGame);
        }
        sessionList.add(session);
        activeGame.setCountVisitors(sessionList.size());
        updateSession();
        System.err.println("Session open");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.err.println("------------------");
        System.err.println(message.getPayload().toString());
    }

    @Override
    public synchronized void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        activeGame.setCountVisitors(sessionList.size());
        updateSession();
    }


    public synchronized void updateSession() {
        for (WebSocketSession session : sessionList) {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(activeGame)));
            } catch (IOException e) {
                logger.warn("could not parse active game", e);
            }
        }
    }
}
