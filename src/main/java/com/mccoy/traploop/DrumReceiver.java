/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.traploop;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 *
 * @author rmccoy
 */
public class DrumReceiver implements Receiver{

    @Override
    public void send(MidiMessage message, long timeStamp) {
        byte[] midiMessage = message.getMessage();
        byte noteStatus = midiMessage[0];
        byte note = midiMessage[1];
        if (noteStatus == -111) {
            switch (note) {
                case 63:
                    SoundWorker.playSample(StockSounds.BD_SOUND);
                case 75:
                    SoundWorker.playSample(StockSounds.SNR_SOUND);
                case 87:
                    SoundWorker.playSample(StockSounds.HH_SOUND);
                default:
                    System.out.println(note);
            }
        }
    }

    @Override
    public void close() {
     
    }
    
}
