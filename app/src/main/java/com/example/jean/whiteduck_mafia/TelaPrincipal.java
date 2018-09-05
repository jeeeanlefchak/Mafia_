package com.example.jean.whiteduck_mafia;

import android.os.Bundle;

import com.example.jean.whiteduck_mafia.AndGraph.AGActivityGame;

public class TelaPrincipal extends AGActivityGame {
    CenaMenu abertura = null;
    CenaJogo cenaJogo = null;
    CenaSobre cenaSobre = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(this,true);

        abertura = new CenaMenu(getGameManager());
        cenaJogo = new CenaJogo(getGameManager());
        cenaSobre = new CenaSobre(getGameManager());

        getGameManager().addScene(abertura);//0
        getGameManager().addScene(cenaJogo); // 1
        getGameManager().addScene(cenaSobre);// 2
    }
}
