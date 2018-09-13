package com.example.jean.whiteduck_mafia;

import com.example.jean.whiteduck_mafia.AndGraph.AGGameManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGInputManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGScene;
import com.example.jean.whiteduck_mafia.AndGraph.AGScreenManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGSoundManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGSprite;
import com.example.jean.whiteduck_mafia.AndGraph.AGTimer;

import java.util.ArrayList;

public class CenaJogo extends AGScene {
    AGSprite fundo = null;
    AGSprite fundo2 = null;
    AGSprite carro = null;
    AGSprite explosao = null;
    int valorPlacar = 0;
    Veiculos veiculos = null;
    AGSprite[] placar = null;
    int velocidade = 0;
    AGTimer tempoVelocidade = null;
    Boolean pararJogo = false;
    private float ultimaPosicaoXTela = 0;
    AGSprite policia = null;
    AGSprite menuOpcoes = null;
    AGSprite restart = null;
    AGSprite botaoSair = null;
    private int sirene;
    private float volumePolicia = 0.0f;
    private AGTimer tempoCarregarSirene = null;
    private boolean sireneIniciado = false;

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

        this.jogo();
    }

    public void jogo(){
        carro = null;
        veiculos = new Veiculos(this);
        policia = null;
        placar = new AGSprite[8];
        explosao = null;
        tempoVelocidade = new AGTimer(100);
        menuOpcoes = null;

        carro = createSprite(R.mipmap.car1,1,1);
        carro.setScreenPercent(12, 19);
        carro.vrPosition.setXY(AGScreenManager.iScreenWidth / 2,
                AGScreenManager.iScreenHeight / 3 );
        veiculos.carregarVeiculos();
        policia = createSprite(R.mipmap.policia,2,1);
        policia.setScreenPercent(12, 19);
        policia.vrPosition.setXY(veiculos.posicoesFaixas.get(2),
                (AGScreenManager.iScreenHeight * 5) * -1);
        policia.addAnimation(2,true,0,1);
        sirene = AGSoundManager.vrSoundEffects.loadSoundEffect("sirene.wav");
        tempoCarregarSirene = new AGTimer(1000);

        explosao = createSprite(R.mipmap.explosao, 4, 2);
        explosao.setScreenPercent(15, 9);
        explosao.addAnimation(15, false, 0, 1, 2, 3, 4, 5, 6, 7);
        explosao.bVisible = false;

        ultimaPosicaoXTela = AGScreenManager.iScreenWidth / 2;

        menuOpcoes = createSprite(R.mipmap.pause_menu_bckg,1,1);
        menuOpcoes.setScreenPercent(80,80);
        menuOpcoes.bVisible = false;
        menuOpcoes.vrPosition.setXY(AGScreenManager.iScreenWidth / 2,
                AGScreenManager.iScreenHeight / 2);

        restart = createSprite(R.mipmap.btn_restart_anim,5,4);
        restart.setScreenPercent(30,18);
        restart.vrPosition.setXY(AGScreenManager.iScreenWidth / 2,
                AGScreenManager.iScreenHeight / 2);
        restart.addAnimation(13,true,0,17);
        restart.bVisible = false;

        botaoSair = createSprite(R.mipmap.btn_exit_anim,5,4);
        botaoSair.setScreenPercent(30,18);
        botaoSair.vrPosition.setXY(AGScreenManager.iScreenWidth / 2,
                restart.vrPosition.fY - restart.getSpriteHeight() / 6 - botaoSair.getSpriteHeight() / 1 );
        botaoSair.addAnimation(13,true,0,17);
        botaoSair.bVisible = false;
        for (int iIndex = 0; iIndex < 8; iIndex++) {
            placar[iIndex] = createSprite(R.mipmap.fonte, 4, 4);
            placar[iIndex].setScreenPercent(8, 8);
            placar[iIndex].vrPosition.setY(AGScreenManager.iScreenHeight - placar[iIndex].getSpriteHeight() / 2);
            placar[iIndex].vrPosition.setX(placar[iIndex].getSpriteWidth() / 2 + iIndex * placar[iIndex].getSpriteWidth());
            placar[iIndex].bAutoRender = false;
            for (int i = 0; i < 10; i++) {
                placar[iIndex].addAnimation(1, false, i);
            }
        }
    }

    public void render(){
        super.render();
        for(AGSprite digito : placar){
            digito.render();
        }
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        if(!pararJogo) {
            this.controlaCarro();
            this.atualizaFundo();
            controlaVelocidade();
            veiculos.atualizaVeiculos(velocidade);
            this.verificaColisao();
            this.atualizaPlacar();
            this.atualizaPolicia();
        }
        this.acaoes();
    }

    public void atualizaFundo(){
            fundo.vrPosition.setY(fundo.vrPosition.getY() + fundo.vrDirection.getY() * velocidade);
            fundo2.vrPosition.setY(fundo2.vrPosition.getY() + fundo2.vrDirection.getY() * velocidade);

            if (fundo.vrPosition.getY() < -fundo.getSpriteHeight() / 2) {
                fundo.vrPosition.setY(fundo2.vrPosition.getY() + fundo.getSpriteHeight());
            }
            if (fundo2.vrPosition.getY() < -fundo2.getSpriteHeight() / 2) {
                fundo2.vrPosition.setY(fundo.vrPosition.getY() + fundo2.getSpriteHeight());
            }
    }

    public void controlaCarro(){
        if (AGInputManager.vrTouchEvents.screenDragged()){
            if (ultimaPosicaoXTela > (AGInputManager.vrTouchEvents.fPosX + 10)) {
                carro.vrDirection.setX(-1);
            } else if (ultimaPosicaoXTela < (AGInputManager.vrTouchEvents.fPosX - 10)) {
                carro.vrDirection.setX(1);
            } else {
                carro.vrDirection.setX(0);
            }
            if((carro.vrPosition.getX() - 10) < veiculos.posicoesFaixas.get(0) && (carro.vrPosition.getX() + 10) > veiculos.posicoesFaixas.get(0)){
                policia.moveTo(100, veiculos.posicoesFaixas.get(0), policia.vrPosition.getY());
            }
            if((carro.vrPosition.getX() - 10) < veiculos.posicoesFaixas.get(1) && (carro.vrPosition.getX() + 10) > veiculos.posicoesFaixas.get(1)){
                policia.moveTo(100, veiculos.posicoesFaixas.get(1), policia.vrPosition.getY());
            }
            if((carro.vrPosition.getX() - 10) < veiculos.posicoesFaixas.get(2) && (carro.vrPosition.getX() + 10) > veiculos.posicoesFaixas.get(2)){
                policia.moveTo(100, veiculos.posicoesFaixas.get(2), policia.vrPosition.getY());
            }
            if((carro.vrPosition.getX() - 10) < veiculos.posicoesFaixas.get(3) && (carro.vrPosition.getX() + 10) > veiculos.posicoesFaixas.get(3)){
                policia.moveTo(100, veiculos.posicoesFaixas.get(3), policia.vrPosition.getY());
            }
            carro.vrPosition.setX(carro.vrPosition.getX() + carro.vrDirection.getX() * (velocidade / 2));
            ultimaPosicaoXTela = AGInputManager.vrTouchEvents.fPosX;
        }
        if (AGInputManager.vrTouchEvents.screenClicked()) {
            ultimaPosicaoXTela = AGInputManager.vrTouchEvents.fPosX;
        }
    }

    public void verificaColisao(){
        for (int i = 0; i <  veiculos.veiculos.size(); i++) {
            if (veiculos.veiculos.get(i).sprite.bVisible && veiculos.veiculos.get(i).sprite.collide(carro)) {
                //            caminhao.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
                this.veiculos.veiculos.get(i).sprite.bVisible = false;
                carro.bVisible = false;
                explosao.vrPosition.setXY(carro.vrPosition.getX(), carro.vrPosition.getY());
                explosao.bVisible = true;
                pararJogo = true;
                menuOpcoes.bVisible = true;
                restart.bVisible = true;
                botaoSair.bVisible = true;
                for (int iIndex = 0; iIndex < 8; iIndex++) {
                    placar[iIndex].vrPosition.setY(AGScreenManager.iScreenWidth +  AGScreenManager.iScreenWidth / 4);
                    placar[iIndex].vrPosition.setX(placar[iIndex].getSpriteWidth() * 3 + iIndex * placar[iIndex].getSpriteWidth());
                }
            }
        }
    }

    private void controlaVelocidade() {
        tempoVelocidade.update();
        if (tempoVelocidade.isTimeEnded()) {
            if (velocidade < 60) {
                velocidade++;
                tempoVelocidade.restart();
            }
        }
    }

    private void atualizaPolicia() {
        tempoCarregarSirene.update();
        if (!sireneIniciado && tempoCarregarSirene.isTimeEnded()) {
            AGSoundManager.vrSoundEffects.play(sirene, true, 0.0f, 0.0f);
            sireneIniciado = true;
        }

        if (policia.vrPosition.getY() < carro.getSpriteHeight() / 3) {
            if (policia.vrPosition.getY() < 0) {
                policia.vrPosition.setY(policia.vrPosition.getY() * -1 * (velocidade / 2));
            } else {
                policia.vrPosition.setY(policia.vrPosition.getY()  * (velocidade / 2));
            }
        }

        volumePolicia = (100 - (policia.vrPosition.getY() * 100 / AGScreenManager.iScreenHeight) + 30) / 100;
        AGSoundManager.vrSoundEffects.setVolumeSound(sirene, volumePolicia, volumePolicia);

        for(int i = 0;i < veiculos.veiculos.size(); i++){
            if (policia.collide(veiculos.veiculos.get(i).sprite)){
                if(veiculos.veiculos.get(i).sprite.vrPosition.getX() ==  policia.vrPosition.getX()){
                    policia.vrPosition.setX(veiculos.posicoesFaixas.get(1));
                }
                //posicao 1 para 0
                if(veiculos.veiculos.get(i).sprite.vrPosition.getX() ==  policia.vrPosition.getX()){
                    policia.vrPosition.setX(veiculos.posicoesFaixas.get(0));
                }
                // posição 2 para 3
                if(veiculos.veiculos.get(i).sprite.vrPosition.getX() == policia.vrPosition.getX()){
                    policia.vrPosition.setX(veiculos.posicoesFaixas.get(3));
                }
                //posicao 3 para 2
                if(veiculos.veiculos.get(i).sprite.vrPosition.getX() ==  policia.vrPosition.getX()) {
                    policia.vrPosition.setX(veiculos.posicoesFaixas.get(2));
                }
            }
        }
    }

    private void atualizaPlacar(){
            valorPlacar++;
            placar[7].setCurrentAnimation(valorPlacar % 10);
            placar[6].setCurrentAnimation((valorPlacar % 100) / 10);
            placar[5].setCurrentAnimation((valorPlacar % 1000) / 100);
            placar[4].setCurrentAnimation((valorPlacar % 10000) / 1000);
            placar[3].setCurrentAnimation((valorPlacar % 100000) / 10000);
            placar[2].setCurrentAnimation((valorPlacar % 1000000) / 100000);
            placar[1].setCurrentAnimation((valorPlacar % 10000000) / 1000000);
            placar[0].setCurrentAnimation((valorPlacar % 100000000) / 10000000);
    }

    public void acaoes(){
        if (AGInputManager.vrTouchEvents.backButtonClicked()){
            vrGameManager.setCurrentScene(0);
        }
        if (restart.collide(AGInputManager.vrTouchEvents.getLastPosition()) && pararJogo){
            this.novoJogo();
        }
        if (botaoSair.collide(AGInputManager.vrTouchEvents.getLastPosition()) && pararJogo){
            vrGameManager.setCurrentScene(0);
        }

    }
    public void novoJogo(){
        pararJogo = false;
        for(int i =0; i < veiculos.veiculos.size(); i++){
            veiculos.veiculos.get(i).sprite.bVisible = true;
        }
        carro.bVisible = true;
        restart.bVisible = false;
        menuOpcoes.bVisible = false;
        botaoSair.bVisible = false;
        for (int iIndex = 0; iIndex < 8; iIndex++) {
            placar[iIndex].vrPosition.setY(AGScreenManager.iScreenHeight - placar[iIndex].getSpriteHeight() / 2);
            placar[iIndex].vrPosition.setX(placar[iIndex].getSpriteWidth() / 2 + iIndex * placar[iIndex].getSpriteWidth());
        }
          this.valorPlacar = 0;
//        tempoVelocidade = new AGTimer(100);
//        velocidade = 1;
//        controlaVelocidade();
        pararJogo = false;
    }
}