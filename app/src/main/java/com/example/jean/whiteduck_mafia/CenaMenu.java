package com.example.jean.whiteduck_mafia;

import com.example.jean.whiteduck_mafia.AndGraph.AGGameManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGInputManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGScene;
import com.example.jean.whiteduck_mafia.AndGraph.AGScreenManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGSprite;

public class CenaMenu extends AGScene {
    AGSprite botaoJogar = null;
    AGSprite botaoSobre = null;
    AGSprite titulo = null;
    AGSprite botaoSair = null;

    CenaMenu(AGGameManager gameManager){
        super(gameManager);
    }

    @Override
    public void init() {
        //chamado quando essa cena for carregada
        this.setSceneBackgroundColor(0,0,0);
        titulo = createSprite(R.mipmap.s_logo,1,1);
        titulo.setScreenPercent(80,40);
        titulo.vrPosition.setXY(AGScreenManager.iScreenWidth / 2,
                AGScreenManager.iScreenHeight - titulo.getSpriteHeight() / 2);

        botaoJogar = createSprite(R.mipmap.btn_play_anim,5,4);
        botaoJogar.setScreenPercent(30,18);
        botaoJogar.vrPosition.setXY(AGScreenManager.iScreenWidth / 2,
                titulo.vrPosition.fY - titulo.getSpriteHeight() / 2  - botaoJogar.getSpriteHeight() / 2);
        botaoJogar.addAnimation(13,true,0,17);

        botaoSobre = createSprite(R.mipmap.btn_about_anim,5,4);
        botaoSobre.setScreenPercent(30,18);
        botaoSobre.vrPosition.setXY(AGScreenManager.iScreenWidth / 2,
                botaoJogar.vrPosition.fY - botaoJogar.getSpriteHeight() / 6 - botaoSobre.getSpriteHeight() / 1);
        botaoSobre.addAnimation(13,true,0,17);

        botaoSair = createSprite(R.mipmap.btn_exit_anim,5,4);
        botaoSair.setScreenPercent(30,18);
        botaoSair.vrPosition.setXY(AGScreenManager.iScreenWidth / 2,
                botaoSobre.vrPosition.fY - botaoSobre.getSpriteHeight() / 6 - botaoSair.getSpriteHeight() / 1 );
        botaoSair.addAnimation(13,true,0,17);

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
        this.click();
    }

    public void click(){
        if (AGInputManager.vrTouchEvents.screenClicked()){
            if (botaoJogar.collide(AGInputManager.vrTouchEvents.getLastPosition())){ // click no botao Jogar
                vrGameManager.setCurrentScene(1);
                return;
            }
            if (botaoSobre.collide(AGInputManager.vrTouchEvents.getLastPosition())){ // click no botao sobre
                vrGameManager.setCurrentScene(2);
                return;
            }
            if (botaoSair.collide(AGInputManager.vrTouchEvents.getLastPosition())){// Click Sair
                vrGameManager.vrActivity.finish();
            }
        }
        if (AGInputManager.vrTouchEvents.backButtonClicked()){
            vrGameManager.vrActivity.finish();
        }
    }
}
