/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.traploop;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author rmccoy
 */
public class MidiPlayer {
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    private static final int MIDI_CHANNEL = 1;
    private static final int MIDI_VELOCITY = 100;
    private static final int MIDI_NOTE_ON = 144;
    private static final int MIDI_NOTE_OFF = 128;
    private static final long LOOP_START = 0;
    private static final long LOOP_END = 16;
    private static int defaultBPM = 120;
    private static final int MAX_BPM = 200;
    private static final int MIN_BPM = 1;
    private static final Logger LOG = Logger.getLogger(MidiPlayer.class.getName());
    private static Sequencer sequencer;
    private static Track track;
    private static Sequence sequence;
    PlayButton playButton = new PlayButton();
    StopButton stopButton = new StopButton();
    BPMDial bpmDial = new BPMDial();
    TransportUI transportUI = new TransportUI();


    public MidiPlayer() {
        try {
            sequencer = MidiSystem.getSequencer();
        } catch (MidiUnavailableException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
    }
    
    private class TransportUI extends JPanel {
        public TransportUI() {
            setBackground(Color.BLACK);
            add(new JLabel("BPM")).setForeground(Color.WHITE);
            add(bpmDial);
            add(playButton);
            add(stopButton);
        }  
    }
    
    private class BPMDial extends JSpinner {

        public BPMDial() {
            super(new SpinnerNumberModel(defaultBPM, MIN_BPM, MAX_BPM, 1));
            setEnabled(true);
        }
        
    }

    private class PlayButton extends JButton implements ActionListener{

        public PlayButton() {
            super("PLAY");
            addActionListener(this);
            setBackground(Color.GREEN);
            setOpaque(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            startPlayer();
        }
    
    }
    
    private class StopButton extends JButton implements ActionListener{

        public StopButton() {
            super("STOP");
            addActionListener(this);
            setBackground(Color.RED);
            setOpaque(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            stopPlayer();
        }
    }
    
    private void startPlayer() {
        try {
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            addNotes(track, PatternSequencer.getInstrumentNoteSequence("Snr"));
            sequencer.setSequence(sequence);
            sequencer.setLoopStartPoint(LOOP_START);
            sequencer.setLoopEndPoint(LOOP_END);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.setTempoInBPM(getBPM()); 
            sequencer.start();
        } catch (MidiUnavailableException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (InvalidMidiDataException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        playButton.setText("PLAYING...");
        playButton.setEnabled(false);
    }
    
    private void stopPlayer() {
        sequencer.stop();
        sequence.deleteTrack(track);
        playButton.setText("PLAY");
        playButton.setEnabled(true);
    }
    
    private void addNotes(Track track, LinkedList<Boolean> instrumentNoteSequence) {
        int beatNumber = 0;
        for (Iterator<Boolean> iterator = instrumentNoteSequence.iterator(); iterator.hasNext();) {
            Boolean noteState = iterator.next();
            if (noteState.booleanValue() == true) {
                track.add(makeEvent(MIDI_NOTE_ON, MIDI_CHANNEL, 63, MIDI_VELOCITY, beatNumber));
            } else {
                track.add(makeEvent(MIDI_NOTE_OFF, MIDI_CHANNEL, 63, MIDI_VELOCITY, beatNumber));
            }
            beatNumber++;
        }
        track.add(makeEvent(MIDI_NOTE_OFF, MIDI_CHANNEL, 63, MIDI_VELOCITY, 16));
    }
    
    private Integer getBPM() {
        try {
            bpmDial.commitEdit();
        } catch (ParseException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
        return (Integer)bpmDial.getValue();
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
}
    
