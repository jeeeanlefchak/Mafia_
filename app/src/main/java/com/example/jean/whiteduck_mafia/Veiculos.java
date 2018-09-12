package com.example.jean.whiteduck_mafia;

import android.util.Log;

import com.example.jean.whiteduck_mafia.AndGraph.AGScene;
import com.example.jean.whiteduck_mafia.AndGraph.AGScreenManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGSoundManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGSprite;

import java.util.ArrayList;
import java.util.Random;

public class Veiculos {
    ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
    AGScene scene = null;
    ArrayList<Integer> posicoesFaixas = new ArrayList<Integer>();
    private Random random = new Random();

    Veiculos(AGScene scene) {
        this.scene = scene;
    }

    public void carregarVeiculos() {
        //POSICOES NA ESQUERDA
        posicoesFaixas.add(getScreenPositionXAtPercentage(26));
        posicoesFaixas.add(getScreenPositionXAtPercentage(40));

        //POSICOES NA DIREITA
        posicoesFaixas.add(getScreenPositionXAtPercentage(57));
        posicoesFaixas.add(getScreenPositionXAtPercentage(74));
        Veiculo carro = new Veiculo();
        carro = new Veiculo();
        carro.sprite = this.scene.createSprite(R.mipmap.caminhao, 1, 1);
        carro.sprite.setScreenPercent(18, 30);
        carro.sprite.vrPosition.setX(posicoesFaixas.get(random.nextInt(posicoesFaixas.size())));
        carro.sprite.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
        carro.sprite.vrDirection.setY(-1);
        carro.velocidadeRelativa = 7;
        carro.carregarSomMotor("engine1.wav");
        veiculos.add(carro);
        carro = new Veiculo();
        carro.sprite = this.scene.createSprite(R.mipmap.car_traffic1,1,1);
        carro.sprite.setScreenPercent(10,15);
        carro.sprite.vrPosition.setX(posicoesFaixas.get(random.nextInt(posicoesFaixas.size())));
        carro.sprite.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
        carro.sprite.vrDirection.setY(-1);
        carro.velocidadeRelativa = 7;
        veiculos.add(carro);
        carro = new Veiculo();
        carro.sprite = this.scene.createSprite(R.mipmap.car_traffic2,1,1);
        carro.sprite.setScreenPercent(10,15);
        carro.sprite.vrPosition.setX(posicoesFaixas.get(random.nextInt(posicoesFaixas.size())));
        carro.sprite.vrPosition.setY(AGScreenManager.iScreenHeight);
        carro.sprite.vrDirection.setY(-1);
        carro.velocidadeRelativa = 3;
        veiculos.add(carro);
        carro = new Veiculo();
        carro.sprite = this.scene.createSprite(R.mipmap.car_traffic3,1,1);
        carro.sprite.setScreenPercent(10,15);
        carro.sprite.vrPosition.setX(posicoesFaixas.get(random.nextInt(posicoesFaixas.size())));
        carro.sprite.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
        carro.sprite.vrDirection.setY(-1);
        carro.velocidadeRelativa = 5;
        veiculos.add(carro);
//        carro = new Veiculo();
//        carro.sprite = this.scene.createSprite(R.mipmap.car_traffic4,1,1);
//        carro.sprite.setScreenPercent(10,15);
//        carro.sprite.vrPosition.setX(posicoesFaixas.get(random.nextInt(posicoesFaixas.size())));
//        carro.sprite.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
//        carro.sprite.vrDirection.setY(-1);
//        carro.velocidadeRelativa = 55;
        veiculos.add(carro);

    }

    public void atualizaVeiculos(int velocidade) {
        for (int i = 0; i < veiculos.size(); i++) {
            veiculos.get(i).sprite.vrPosition.setY(veiculos.get(i).sprite.vrPosition.getY() + veiculos.get(i).sprite.vrDirection.getY() *
                    ((velocidade * veiculos.get(i).velocidadeRelativa) / 100));

            //PRECISO VALIDAR!
            if(veiculos.get(i).somMotor > 0 ) {
                veiculos.get(i).atualizarSom();
            }
            if (veiculos.get(i).sprite.vrPosition.getY() < -AGScreenManager.iScreenHeight / 2) {
                veiculos.get(i).sprite.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
                veiculos.get(i).sprite.vrPosition.setX(posicoesFaixas.get(random.nextInt(posicoesFaixas.size())));

                //SE O VEÍCLUDO ESTIVER NA FAIXA DA ESQUERDA
                if (veiculos.get(i).sprite.vrPosition.getX() < AGScreenManager.iScreenWidth / 2) {
                    if (veiculos.get(i).sprite.iMirror == AGSprite.NONE) {
                        veiculos.get(i).sprite.iMirror = AGSprite.VERTICAL;
                        veiculos.get(i).velocidadeRelativa += 60;
                    }
                } else {
                    if (veiculos.get(i).sprite.iMirror == AGSprite.VERTICAL) {
                        veiculos.get(i).sprite.iMirror = AGSprite.NONE;
                        veiculos.get(i).velocidadeRelativa -= 60;
                    }

                }
            }

            for (int w = 0; w < veiculos.size(); w++) {
                if (veiculos.get(i).sprite.collide(veiculos.get(w).sprite) && veiculos.get(i).sprite != veiculos.get(w).sprite){
//                    veiculos.get(w).sprite.vrPosition.setX(posicoesFaixas.get(random.nextInt(posicoesFaixas.size())));
                    // posição 0 para 1
                    if(veiculos.get(i).sprite.vrPosition.getX() ==  posicoesFaixas.get(0)){
                        veiculos.get(i).sprite.vrPosition.setX(posicoesFaixas.get(1));
                    }
                    //posicao 1 para 0
                    if(veiculos.get(i).sprite.vrPosition.getX() ==  posicoesFaixas.get(1)){
                        veiculos.get(i).sprite.vrPosition.setX(posicoesFaixas.get(0));
                    }
                    // posição 2 para 3
                    if(veiculos.get(i).sprite.vrPosition.getX() == posicoesFaixas.get(2)){
                        veiculos.get(i).sprite.vrPosition.setX(posicoesFaixas.get(3));
                    }
                    //posicao 3 para 2
                    if(veiculos.get(i).sprite.vrPosition.getX() ==  posicoesFaixas.get(3)) {
                        veiculos.get(i).sprite.vrPosition.setX(posicoesFaixas.get(2));
                    }
                }
            }
        }
    }

    //RETORNA A POSIÇÃO "x" EM UMA PORCENTAGEM DA TELA
    public int getScreenPositionXAtPercentage(int percentX) {
      return (AGScreenManager.iScreenWidth * percentX) / 100;
    }

}
