package ru.sw.modules.websoket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.sw.modules.game.Game;
import ru.sw.modules.game.GameRepository;
import ru.sw.modules.game.player.Player;
import ru.sw.platform.core.exceptions.PlatofrmExecption;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class GameWebSocketHandler extends TextWebSocketHandler {

    public static Object monitor = new Object();
    public static Game activeGame = new Game();
    public static List<WebSocketSession> sessionList = new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(GameWebSocketHandler.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Calendar startGame;


    @Autowired
    private GameRepository gameRepository;

    @Override
    public synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            if (activeGame.getId() == null) {
                gameRepository.create(activeGame);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
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

    public static void startGame(){
        activeGame.setState(Game.GameState.Active);
        startGame = Calendar.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                    synchronized (monitor) {
                        startGame = null;

                        Integer min = 0;
                        Integer max = 100;
                        Double generatedValue = min + Math.random() * (max - min);
                        System.err.println("Generated value"  + generatedValue);
                        Player winner = null;
                        double sum = 0;
                        for(Player player : activeGame.getPlayers()) {
                            sum += player.getProbability();
                            if(generatedValue < sum) {
                                winner = player;
                                break;
                            }
                        }
                        System.err.println("winner:" + winner.getNickName());
                        activeGame = new Game();
                        activeGame.setLastWinnerNickName(winner.getNickName());
                        activeGame.setLastWinnerImageUrl(winner.getImageUrl());
                        updateSession();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        updateSession();
    }


    public static void updateSession() {
        synchronized (monitor) {
            for (WebSocketSession session : sessionList) {
                try {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(activeGame)));
                } catch (IOException e) {
                    throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ActionError);
                }
            }
        }
    }
}
