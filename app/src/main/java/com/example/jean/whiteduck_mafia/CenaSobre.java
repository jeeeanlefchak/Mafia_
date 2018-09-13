package com.example.jean.whiteduck_mafia;

import com.example.jean.whiteduck_mafia.AndGraph.AGGameManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGInputManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGScene;

public class CenaSobre extends AGScene {

    CenaSobre(AGGameManager gameManager){
        super(gameManager);
    }
    @Override
    public void init() {

    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        if (AGInputManager.vrTouchEvents.backButtonClicked()){
            vrGameManager.setCurrentScene(0);
        }
    }
}
