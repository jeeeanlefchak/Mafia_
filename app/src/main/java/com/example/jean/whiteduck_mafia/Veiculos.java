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
        carro.sprite = this.scene.createSprite(R.mipmap.caminhao, 1, 1);
        carro.sprite.setScreenPercent(18, 30);
        carro.sprite.vrPosition.setX(posicoesFaixas.get(0));
        carro.sprite.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
        carro.sprite.vrDirection.setY(-1);
        carro.velocidadeRelativa = 60;
        carro.carregarSomMotor("engine1.wav");
        veiculos.add(carro);

        carro = new Veiculo();
        carro.sprite = this.scene.createSprite(R.mipmap.car_traffic1,1,1);
        carro.sprite.setScreenPercent(10,15);
        carro.sprite.vrDirection.setY(-1);
        carro.velocidadeRelativa = 65;
        carro.carregarSomMotor("engine2.wav");
        veiculos.add(carro);
//
        carro = new Veiculo();
        carro.sprite = this.scene.createSprite(R.mipmap.car_traffic2,1,1);
        carro.sprite.setScreenPercent(10,15);
        carro.sprite.vrDirection.setY(-1);
        carro.velocidadeRelativa = 70;
        carro.carregarSomMotor("engine2.wav");
        veiculos.add(carro);

        carro = new Veiculo();
        carro.sprite = this.scene.createSprite(R.mipmap.car_traffic3,1,1);
        carro.sprite.setScreenPercent(10,15);
        carro.sprite.vrDirection.setY(-1);
        carro.velocidadeRelativa = 67;
        carro.carregarSomMotor("engine3.wav");
        veiculos.add(carro);

        carro = new Veiculo();
        carro.sprite = this.scene.createSprite(R.mipmap.car_traffic4,1,1);
        carro.sprite.setScreenPercent(10,15);
        carro.sprite.vrDirection.setY(-1);
        carro.velocidadeRelativa = 63;
        carro.carregarSomMotor("engine3.wav");
        veiculos.add(carro);

        inicializarVeiculos();

    }

    public void atualizaVeiculos(int velocidade) {
        for (int i = 0; i < veiculos.size(); i++) {
            veiculos.get(i).sprite.vrPosition.setY(veiculos.get(i).sprite.vrPosition.getY() + veiculos.get(i).sprite.vrDirection.getY() *
                    ((velocidade * veiculos.get(i).velocidadeRelativa) / 100));

            //PRECISO VALIDAR!
            if(veiculos.get(i).somMotor > 0 && veiculos.get(i).sprite.bVisible) {
                veiculos.get(i).atualizarSom();
            }
            if (veiculos.get(i).sprite.vrPosition.getY() < -AGScreenManager.iScreenHeight / 2) {
                veiculos.get(i).sprite.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
                veiculos.get(i).sprite.vrPosition.setX(posicoesFaixas.get(random.nextInt(posicoesFaixas.size())));
                veiculos.get(i).sprite.bVisible = (Math.random() < 0.5);

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
        }

        //verificaColisoes();
        verificaEspacamentoEntreVeiculos();
    }

    public void verificaColisoes() {
        for (int i = 0; i < veiculos.size(); i++) {
            for (int w = 0; w < veiculos.size(); w++) {
                if (w != i) {
                    if (veiculos.get(i).sprite.collide(veiculos.get(w).sprite) && veiculos.get(i).sprite != veiculos.get(w).sprite) {
//                    veiculos.get(w).sprite.vrPosition.setX(posicoesFaixas.get(random.nextInt(posicoesFaixas.size())));
                        // posição 0 para 1
                        if (veiculos.get(i).sprite.vrPosition.getX() == posicoesFaixas.get(0)) {
                            veiculos.get(i).sprite.moveTo(100, posicoesFaixas.get(1), veiculos.get(i).sprite.vrPosition.getY());
                            break;
                        }
                        //posicao 1 para 0
                        if (veiculos.get(i).sprite.vrPosition.getX() == posicoesFaixas.get(1)) {
                            veiculos.get(i).sprite.moveTo(100, posicoesFaixas.get(0), veiculos.get(i).sprite.vrPosition.getY());
                            break;
                        }
                        // posição 2 para 3
                        if (veiculos.get(i).sprite.vrPosition.getX() == posicoesFaixas.get(2)) {
                            veiculos.get(i).sprite.moveTo(100, posicoesFaixas.get(3), veiculos.get(i).sprite.vrPosition.getY());
                            break;
                        }
                        //posicao 3 para 2
                        if (veiculos.get(i).sprite.vrPosition.getX() == posicoesFaixas.get(3)) {
                            veiculos.get(i).sprite.moveTo(100, posicoesFaixas.get(2), veiculos.get(i).sprite.vrPosition.getY());
                            break;
                        }
                    }
                }
            }
        }
    }

    public void verificaEspacamentoEntreVeiculos() {
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).sprite.vrPosition.getY() > AGScreenManager.iScreenHeight) {
                for (int w = 0; w < veiculos.size(); w++) {
                    if (w != i) {
                        float distanciaVeiculos = veiculos.get(i).sprite.vrPosition.getY() - veiculos.get(w).sprite.vrPosition.getY();
                        distanciaVeiculos = distanciaVeiculos < 0 ? distanciaVeiculos * -1 : distanciaVeiculos;
                        if (distanciaVeiculos < veiculos.get(i).sprite.getSpriteHeight() / 2) {
                            veiculos.get(i).sprite.vrPosition.setY(veiculos.get(i).sprite.vrPosition.getY() + (veiculos.get(i).sprite.getSpriteHeight() * 2));
                        }
                    }
                }
            }
        }
    }

    //RETORNA A POSIÇÃO "x" EM UMA PORCENTAGEM DA TELA
    public int getScreenPositionXAtPercentage(int percentX) {
      return (AGScreenManager.iScreenWidth * percentX) / 100;
    }

    private void inicializarVeiculos() {
        for (int i = 0; i < veiculos.size(); i++) {
            veiculos.get(i).sprite.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
            veiculos.get(i).sprite.vrPosition.setX(posicoesFaixas.get(random.nextInt(posicoesFaixas.size())));
            veiculos.get(i).sprite.bVisible = (Math.random() < 0.5);

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
    }

}
