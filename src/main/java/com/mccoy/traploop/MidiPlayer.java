/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.traploop;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

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
    private static final int MIDI_CHANNEL = 0;
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
    private static Transmitter transmitter;
    private static Sequence sequence;
    private static Soundbank tlSoundbank;
    private static Synthesizer synth;
    private static final String TL_SOUNDBANK_PATH = "soundbank/traploopv1.sf2";
    //private static DrumReceiver drumReceiver = new DrumReceiver();
    PlayButton playButton = new PlayButton();
    StopButton stopButton = new StopButton();
    BPMDial bpmDial = new BPMDial();
    TransportUI transportUI = new TransportUI();


    public MidiPlayer() {
        try {
            sequencer = MidiSystem.getSequencer(false);
            tlSoundbank = MidiSystem.getSoundbank(MidiPlayer.class.getClassLoader().getResourceAsStream(TL_SOUNDBANK_PATH));
            transmitter = sequencer.getTransmitter();
            synth = MidiSystem.getSynthesizer();
            synth.open();
            synth.loadAllInstruments(tlSoundbank);
            //Check is soundbank is compatable
            System.out.println("Is the soundbank supported: " + synth.isSoundbankSupported(tlSoundbank));
            MidiChannel[] mc = synth.getChannels();
            System.out.println("Midi: " + mc.length);
            transmitter.setReceiver(synth.getReceiver());

        } catch (MidiUnavailableException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
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
            setBackground(Color.GRAY);
            setOpaque(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            stopPlayer();
        }
    }
    
    private void startPlayer() {
        try {
            setupPlayer();
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
        playButton.setText("PLAY");
        playButton.setEnabled(true);
    }
    
    private void setupPlayer() throws MidiUnavailableException, InvalidMidiDataException {
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            addTracks();
            sequencer.setSequence(sequence);
            sequencer.setLoopStartPoint(LOOP_START);
            sequencer.setLoopEndPoint(LOOP_END);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.setTempoInBPM(getBPM()); 
            sequencer.start();
    }
    
    private void addTracks() {
        //drums in soundbank are on 60 - 64
        
        for (String instrument : PatternSequencer.getInstruments()) {
            int note;
            //this switch is used to map the tracks to soundbank samples
            switch(instrument) {
                case "HH":
                    note = 64;
                    break;
                case "Snr":
                    note = 61;
                    break;
                case "KD":
                    note = 62;
                    break;
                default :
                    note = 60;
            }           
            Track track = sequence.createTrack();
            addNotesToTrack(track, note, PatternSequencer.getInstrumentNoteSequence(instrument));
        }
    }
    
    private void deleteTracks(){
        Track[] currentTracks = sequence.getTracks();
        if (currentTracks.length > 1) {
            for (Track currentTrack : currentTracks) {
                sequence.deleteTrack(currentTrack);
            }
        }    
    }
    
    private void addNotesToTrack(Track track, int note, LinkedList<Boolean> instrumentNoteSequence) {
        int beatNumber = 0;
        for (Iterator<Boolean> iterator = instrumentNoteSequence.iterator(); iterator.hasNext();) {
            Boolean noteState = iterator.next();
            if (noteState.booleanValue() == true) {
                track.add(makeEvent(MIDI_NOTE_ON, MIDI_CHANNEL, note, MIDI_VELOCITY, beatNumber));
            } else {
                track.add(makeEvent(MIDI_NOTE_OFF, MIDI_CHANNEL, note, MIDI_VELOCITY, beatNumber));
            }
            beatNumber++;
        }
        track.add(makeEvent(MIDI_NOTE_OFF, MIDI_CHANNEL, note, MIDI_VELOCITY, 16));
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
    
