/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.com.mccoy.traploop;

import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.*;
import javax.sound.midi.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author rmccoy
 */
public class MiscTest {
    private static final int MIDI_CHANNEL = 1;
    private static final int MIDI_VELOCITY = 100;
    private static final int MIDI_NOTE_ON = 144;
    private static final int MIDI_NOTE_OFF = 128;
    private static final long LOOP_START = 0;
    private static final long LOOP_END = 16;
    
    public static void main(String[] args) {
        MiscTest playTrack = new MiscTest();
        playTrack.midiPlayer();
    }

    public void midiPlayer() {
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();
            addNotes(track);
            sequencer.setSequence(sequence);
            sequencer.setLoopStartPoint(LOOP_START);
            sequencer.setLoopEndPoint(LOOP_END);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.setTempoInBPM(80); 
            sequencer.start(); 
  
            while (true) { 
  
                if (!sequencer.isRunning()) { 
                    sequencer.close(); 
                    System.exit(1); 
                } 
            }   
            
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(MiscTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(MiscTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
    private void addNotes(Track track) {
                        // Adding some events to the track 
                        // one bar
                for (int i = 1; i < (4 * 4); i += 4) 
            { 
                track.add(makeEvent(MIDI_NOTE_ON, MIDI_CHANNEL, 63, MIDI_VELOCITY, i)); 
                track.add(makeEvent(MIDI_NOTE_OFF, MIDI_CHANNEL, 63, MIDI_VELOCITY, i + 2)); 
            } 
            track.add(makeEvent(MIDI_NOTE_OFF, MIDI_CHANNEL, 63, MIDI_VELOCITY, 16));  
    }
    
    private MidiEvent makeEvent(int command, int channel, int note, int velocity, int tick) {
        
        MidiEvent event = null; 
  
        try { 
  
            ShortMessage a = new ShortMessage(); 
            a.setMessage(command, channel, note, velocity); 
  
            event = new MidiEvent(a, tick); 
        } 
        catch (Exception ex) { 
  
            ex.printStackTrace(); 
        } 
        return event; 
        
    }
    
  
    
    public class MyReceiver implements Receiver{

        public MyReceiver() {
        }


        
    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
    
}
