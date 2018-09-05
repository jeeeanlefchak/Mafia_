package com.example.jean.whiteduck_mafia;

import com.example.jean.whiteduck_mafia.AndGraph.AGGameManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGInputManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGScene;
import com.example.jean.whiteduck_mafia.AndGraph.AGScreenManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGSprite;
import com.example.jean.whiteduck_mafia.AndGraph.AGTimer;

import java.util.ArrayList;

public class CenaJogo extends AGScene {
    AGSprite fundo = null;
    AGSprite fundo2 = null;
    AGSprite carro = null;
    AGSprite policia = null;
//    AGSprite caminhao = null;
    AGSprite explosao = null;

    Veiculos veiculos = null;

    int velocidade = 0;
    AGTimer tempoVelocidade = null;

    private float ultimaPosicaoXTela = 0;

    CenaJogo(AGGameManager agGameManager) {
        super(agGameManager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(1, 0, 0);
        fundo = createSprite(R.mipmap.fundoasfalto, 1, 1);
        fundo.setScreenPercent(100, 100);
        fundo.vrPosition.setXY(AGScreenManager.iScreenWidth / 2,
                AGScreenManager.iScreenHeight / 2);
        fundo.vrDirection.setY(-1);

        fundo2 = createSprite(R.mipmap.fundoasfalto2, 1, 1);
        fundo2.setScreenPercent(100, 100);
        fundo2.vrPosition.setXY(AGScreenManager.iScreenWidth / 2,
                AGScreenManager.iScreenHeight + fundo2.getSpriteHeight() / 2 );
        fundo2.vrDirection.setY(-1);

        carro = createSprite(R.mipmap.car1,1,1);
        carro.setScreenPercent(15, 20);
        carro.vrPosition.setXY(AGScreenManager.iScreenWidth / 2,
                AGScreenManager.iScreenHeight / 3 );

        veiculos = new Veiculos(this);
        veiculos.carregarVeiculos();

//        policia = createSprite(R.mipmap.policia,2,1);
//        policia.setScreenPercent(12,20);
//        policia.vrPosition.setXY(AGScreenManager.iScreenWidth / 2,
//                                    policia.getSpriteHeight() / 2);
//        policia.addAnimation(2,true,0,1);
//        policia.bVisible = false;
//
//        caminhao = createSprite(R.mipmap.caminhao, 1, 1);
//        caminhao.setScreenPercent(18, 30);
//        caminhao.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
//        caminhao.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
//        caminhao.vrDirection.setX(-1);
//
//        explosao = createSprite(R.mipmap.explosao, 4, 2);
//        explosao.setScreenPercent(15, 9);
//        explosao.addAnimation(15, false, 0, 1, 2, 3, 4, 5, 6, 7);
//        explosao.bVisible = false;

        tempoVelocidade = new AGTimer(100);
        ultimaPosicaoXTela = AGScreenManager.iScreenWidth / 2;
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        this.controlaCarro();
        this.atualizaCaminhao();
        this.atualizaFundo();
        controlaVelocidade();
        veiculos.atualizaVeiculos(velocidade);
    }

    public void atualizaFundo(){
        fundo.vrPosition.setY(fundo.vrPosition.getY() + fundo.vrDirection.getY() * velocidade);
        fundo2.vrPosition.setY(fundo2.vrPosition.getY() + fundo2.vrDirection.getY() * velocidade);

        if (fundo.vrPosition.getY() <  -fundo.getSpriteHeight()/2) {
            fundo.vrPosition.setY(fundo2.vrPosition.getY() + fundo.getSpriteHeight());
        }
        if (fundo2.vrPosition.getY() <  -fundo2.getSpriteHeight()/2) {
            fundo2.vrPosition.setY(fundo.vrPosition.getY() + fundo2.getSpriteHeight());
        }

    }

    public void controlaCarro(){
        if (AGInputManager.vrTouchEvents.screenDragged()){
            if (ultimaPosicaoXTela > AGInputManager.vrTouchEvents.fPosX) {
                carro.vrDirection.setX(-1);
            } else if (ultimaPosicaoXTela < AGInputManager.vrTouchEvents.fPosX) {
                carro.vrDirection.setX(1);
            } else {
                carro.vrDirection.setX(0);
            }

            carro.vrPosition.setX(carro.vrPosition.getX() + carro.vrDirection.getX() * (velocidade / 3));
            ultimaPosicaoXTela = AGInputManager.vrTouchEvents.fPosX;
        }
    }

    public void verificaColisao(){
//        if(this.veiculos.caminhao.sprite.bVisible && this.veiculos.caminhao.sprite.collide(carro)){
////            caminhao.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
//            this.veiculos.caminhao.sprite.bVisible = false;
//            carro.bVisible = false;
//            explosao.vrPosition.setXY(carro.vrPosition.getX(), carro.vrPosition.getY());
//            explosao.bVisible = true;
//        }
    }

    private void atualizaCaminhao() {
    }

    private void controlaVelocidade() {
        tempoVelocidade.update();
        if (tempoVelocidade.isTimeEnded()) {
            if (velocidade < 70) {
                velocidade++;
                tempoVelocidade.restart();
            }
        }
    }

}