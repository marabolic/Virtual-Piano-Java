package symbols;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class BlackKey extends JButton implements ActionListener {
    private int octave;
    private Octaves.BLACK_KEY key;


    public BlackKey (int octave, Octaves.BLACK_KEY b){
        super();
        this.octave = octave;
        key = b;
        addActionListener(this);
    }

    private String keyToPitch(Octaves.BLACK_KEY key){
        switch (key){
            case CSHARP: return "C";
            case DSHARP: return "D";
            case FSHARP: return "F";
            case GSHARP: return "G";
            default: return "A";
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            MidiPlayer m = new MidiPlayer();
            StringBuilder s = new StringBuilder();
            s.append(keyToPitch(key)).append("#").append(octave + 2);
            m.play(MusicSymbol.noteMidi.get(s.toString()));
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
}
