package com.example.jean.whiteduck_mafia;

import com.example.jean.whiteduck_mafia.AndGraph.AGScene;
import com.example.jean.whiteduck_mafia.AndGraph.AGScreenManager;

public class Veiculos {
    Veiculo caminhao = null;
    AGScene scene = null;

    Veiculos(AGScene scene) {
        this.scene = scene;
    }

    public void carregarVeiculos() {
        caminhao = new Veiculo();
        caminhao.sprite = this.scene.createSprite(R.mipmap.caminhao, 1, 1);
        caminhao.sprite.setScreenPercent(18, 30);
        caminhao.sprite.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        caminhao.sprite.vrPosition.setY(AGScreenManager.iScreenHeight + (AGScreenManager.iScreenHeight / 2));
        caminhao.sprite.vrDirection.setX(-1);
        caminhao.velocidade = 3;
    }
}
