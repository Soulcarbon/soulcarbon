package ru.sw.modules.websoket;

import ru.sw.modules.game.Game;

import java.util.Calendar;

public class GameLogic {
    private Calendar startGame;
    public Game activeGame = new Game();
    public Object monitor = new Object();


}
