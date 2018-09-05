package com.example.jean.whiteduck_mafia;

import com.example.jean.whiteduck_mafia.AndGraph.AGGameManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGScene;
import com.example.jean.whiteduck_mafia.AndGraph.AGScreenManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGSoundManager;
import com.example.jean.whiteduck_mafia.AndGraph.AGSprite;
import com.example.jean.whiteduck_mafia.AndGraph.AGTimer;

import java.util.ArrayList;

public class Veiculo {
    private boolean iniciado = false;
    private AGTimer tempoCarregarSom = null;
    AGSprite sprite = null;
    int velocidadeRelativa = 0;
    int somMotor;
    float volume = 0.0f;

    public void carregarSomMotor(String nomeSom) {
        if (!iniciado) {
            tempoCarregarSom = new AGTimer(1000);
            somMotor = AGSoundManager.vrSoundEffects.loadSoundEffect(nomeSom);
        }
    }

    public void atualizarSom() {
        tempoCarregarSom.update();
        if (!iniciado && tempoCarregarSom.isTimeEnded()) {
            AGSoundManager.vrSoundEffects.play(somMotor, true, 0.0f, 0.0f);
            iniciado = true;
        }

        if (sprite.vrPosition.getY() > AGScreenManager.iScreenHeight / 2) {
            volume = (100 - (sprite.vrPosition.getY() * 100 / AGScreenManager.iScreenHeight) + 30) / 100;
        } else {
            volume = ((sprite.vrPosition.getY() * 100 / AGScreenManager.iScreenHeight) + 30) / 100;
        }
        AGSoundManager.vrSoundEffects.setVolumeSound(somMotor, volume, volume);
    }
}
