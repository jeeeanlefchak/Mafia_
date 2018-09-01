package com.example.jean.whiteduck_mafia;

import android.os.Bundle;

import com.example.jean.whiteduck_mafia.AndGraph.AGActivityGame;

public class TelaPrincipal extends AGActivityGame {
    CenaAbertura abertura = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(this,true);

        abertura = new CenaAbertura(getGameManager());

        getGameManager().addScene(abertura);//0
    }
}
