/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.com.mccoy.traploop;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;


/**
 *
 * @author rmccoy
 */
public class SynthTest implements Receiver{
    private static final int MIDI_CHANNEL = 1;
    private static final int MIDI_VELOCITY = 100;
    private static final int MIDI_NOTE_ON = 144;
    private static final int MIDI_NOTE_OFF = 128;

    public static void main(String[] args) {
        SynthTest synth = new SynthTest();
        synth.sendNotes();
  
    }

    private void sendNotes() {
        // Adding some events to the track 
        // one bar
        for (int i = 1; i < (4 * 4); i += 4) {
            send(makeEvent(MIDI_NOTE_ON, MIDI_CHANNEL, 63, MIDI_VELOCITY, i), -1);
            send(makeEvent(MIDI_NOTE_OFF, MIDI_CHANNEL, 63, MIDI_VELOCITY, i + 2), -1);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SynthTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        send(makeEvent(MIDI_NOTE_OFF, MIDI_CHANNEL, 63, MIDI_VELOCITY, 16), -1);
    }
    
    private MidiMessage makeEvent(int command, int channel, int note, int velocity, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(command, channel, note, velocity);

            event = new MidiEvent(a, tick);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return event.getMessage();
    }
    
    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        byte[] note = message.getMessage();
        
        System.out.println("note: " + note[2]);
    }

}

