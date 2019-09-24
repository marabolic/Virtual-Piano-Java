package symbols;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class BlackKey extends JButton implements ChangeListener {
    private int octave;
    private Octaves.BLACK_KEY key;

    private int keyCode;
    private String text;
    private boolean pressed = false;
    private static int cnt = 0;
    private MidiPlayer m;

    public BlackKey (int octave, Octaves.BLACK_KEY b) throws MidiUnavailableException {
        super();
        m = new MidiPlayer();
        this.octave = octave;
        key = b;
        text = " ";
        keyCode = getKeyEvent();
        addChangeListener(this);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, InputEvent.SHIFT_DOWN_MASK),
                "pressed");
        getActionMap().put("pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                getModel().setPressed(true);
            }
        });
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode,InputEvent.SHIFT_DOWN_MASK,
                true), "released");
        getActionMap().put("released", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                getModel().setPressed(false);
            }
        });
    }

    public String getMyText() {
        return text;
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

    public int getKeyEvent(){
        switch(octave) {
            case 0:
                switch (key) {
                    case CSHARP: text = "!"; return KeyEvent.VK_1;
                    case DSHARP: text = "@"; return KeyEvent.VK_2;
                    case FSHARP: text = "$"; return KeyEvent.VK_4;
                    case GSHARP: text = "%"; return KeyEvent.VK_5;
                    default: text = "^"; return KeyEvent.VK_6;
                }
            case 1:
                switch (key) {
                    case CSHARP: text = "*"; return KeyEvent.VK_8;
                    case DSHARP: text = "("; return KeyEvent.VK_9;
                    case FSHARP: text = "Q"; return KeyEvent.VK_Q;
                    case GSHARP: text = "W"; return KeyEvent.VK_W;
                    default: text = "E"; return KeyEvent.VK_E;
                }
            case 2:
                switch (key) {
                    case CSHARP: text = "T"; return KeyEvent.VK_T;
                    case DSHARP: text = "Y"; return KeyEvent.VK_Y;
                    case FSHARP: text = "I"; return KeyEvent.VK_I;
                    case GSHARP: text = "O"; return KeyEvent.VK_O;
                    default: text = "P"; return KeyEvent.VK_P;
                }
            case 3:
                switch (key) {
                    case CSHARP: text = "S"; return KeyEvent.VK_S;
                    case DSHARP: text = "D"; return KeyEvent.VK_D;
                    case FSHARP: text = "G"; return KeyEvent.VK_G;
                    case GSHARP: text = "H"; return KeyEvent.VK_H;
                    default: text = "J"; return KeyEvent.VK_J;
                }
            default:
                switch (key) {
                    case CSHARP: text = "L"; return KeyEvent.VK_L;
                    case DSHARP: text = "Z"; return KeyEvent.VK_Z;
                    case FSHARP: text = "C"; return KeyEvent.VK_C;
                    case GSHARP: text = "V"; return KeyEvent.VK_V;
                    default: text = "B"; return KeyEvent.VK_B;
                }
        }
    }


    public void stateChanged(ChangeEvent e) {
        ButtonModel model = getModel();

        if (model.isPressed() != pressed) {
            if (model.isPressed()) {
                if (PianoFrame.waitToPlay) {
                    MusicSymbol symbol = PianoFrame.composition.getIndex(PianoFrame.flow.retCursor());
                    if (symbol instanceof Note) {
                        Note n = (Note) symbol;
                        StringBuilder note = new StringBuilder();
                        note.append(n.getPitch());
                        note.append('#');
                        note.append(n.getOctave());
                        System.out.println(text);
                        if (MusicSymbol.noteKey.get(note.toString()).equals(text)) {
                            PianoFrame.flow.moveCursor();
                            while (PianoFrame.composition.getIndex(PianoFrame.flow.retCursor()) instanceof Pause){
                                PianoFrame.flow.moveCursor();
                            }
                        }
                    } else {
                        if (symbol instanceof Chord) {
                            Chord c = (Chord)symbol;
                            for (int i = 0; i < c.arrayLen(); i++){
                                Note n = (Note) c.getIndex(i);
                                StringBuilder note = new StringBuilder();
                                note.append(n.getPitch());
                                note.append('#');
                                note.append(n.getOctave());
                                if (MusicSymbol.noteKey.get(note.toString()).equals(text)) {
                                    cnt++;
                                }
                            }
                            if (cnt == c.arrayLen()) {
                                PianoFrame.flow.moveCursor();
                                while (PianoFrame.composition.getIndex(PianoFrame.flow.retCursor()) instanceof Pause){
                                    PianoFrame.flow.moveCursor();
                                }
                                cnt = 0;
                            }
                        } else { //Pause
                            PianoFrame.flow.moveCursor();
                        }
                    }
                }
                m.play(MusicSymbol.noteMidi.get(MusicSymbol.keyNote.get(text)));
            } else {
                m.release(MusicSymbol.noteMidi.get(MusicSymbol.keyNote.get(text)));
            }
        }
        pressed = model.isPressed();
    }

}
