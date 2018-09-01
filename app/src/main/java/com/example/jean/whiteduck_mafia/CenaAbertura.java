package com.example.jean.whiteduck_mafia;

import com.example.jean.whiteduck_mafia.AndGraph.AGGameManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGScene;

public class CenaAbertura extends AGScene {

    CenaAbertura(AGGameManager gameManager){
        super(gameManager);
    }
    @Override
    public void init() {
        //chamado quando essa cena for carregada
        this.setSceneBackgroundColor(1,0,0);

    }

    @Override
    public void restart() {
        //chamado quando a cena volta de interrupção
    }

    @Override
    public void stop() {
        //chamado quando a cena sofre interrupçao
    }

    @Override
    public void loop() {


    }
}
