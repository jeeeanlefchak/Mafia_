package com.example.jean.whiteduck_mafia;

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
        Veiculo caminhao = new Veiculo();
        caminhao.sprite = this.scene.createSprite(R.mipmap.caminhao, 1, 1);
        caminhao.sprite.setScreenPercent(18, 30);
        caminhao.sprite.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        caminhao.sprite.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
        caminhao.sprite.vrDirection.setY(-1);
        caminhao.velocidadeRelativa = 50;
        caminhao.carregarSomMotor("engine1.wav");

        veiculos.add(caminhao);

        //POSICOES NA ESQUERDA
        posicoesFaixas.add(getScreenPositionXAtPercentage(26));
        posicoesFaixas.add(getScreenPositionXAtPercentage(40));

        //POSICOES NA DIREITA
        posicoesFaixas.add(getScreenPositionXAtPercentage(57));
        posicoesFaixas.add(getScreenPositionXAtPercentage(74));
    }

    public void atualizaVeiculos(int velocidade) {
        for (int i = 0; i < veiculos.size(); i++) {
            veiculos.get(i).sprite.vrPosition.setY(veiculos.get(i).sprite.vrPosition.getY() + veiculos.get(i).sprite.vrDirection.getY() *
                    ((velocidade * veiculos.get(i).velocidadeRelativa) / 100));
            veiculos.get(i).atualizarSom();

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
        }
    }

    //RETORNA A POSIÇÃO "x" EM UMA PORCENTAGEM DA TELA
    public int getScreenPositionXAtPercentage(int percentX) {
      return (AGScreenManager.iScreenWidth * percentX) / 100;
    }

}
