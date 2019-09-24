package symbols;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;


public class WhiteKey extends JButton implements ChangeListener {
    private int key;
    private int octave;
    private int keyCode;
    private String text;
    private boolean pressed = false;
    private MidiPlayer m;
    private static int cnt = 0;

    public static HashMap<Integer, Integer> keyButtons;

    public WhiteKey(int octave, int key) throws MidiUnavailableException {
        super();
        m = new MidiPlayer();

        this.octave = octave;
        this.key = key;
        keyCode = getKeyEvent();
        addChangeListener(this);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode,0), "pressed");
        getActionMap().put("pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                getModel().setPressed(true);
            }
        });
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode,0, true), "released");
        getActionMap().put("released", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                getModel().setPressed(false);
            }
        });
    }

    public String getMyText() {
        return text;
    }

    public int getKeyEvent(){
        switch(octave) {
            case 0:
                switch (key) {
                    case 0: text = "1"; return KeyEvent.VK_1;
                    case 1: text = "2"; return KeyEvent.VK_2;
                    case 2: text = "3"; return KeyEvent.VK_3;
                    case 3: text = "4"; return KeyEvent.VK_4;
                    case 4: text = "5"; return KeyEvent.VK_5;
                    case 5: text = "6"; return KeyEvent.VK_6;
                    default:  text = "7"; return KeyEvent.VK_7;
                }
            case 1:
                switch (key) {
                    case 0: text = "8"; return KeyEvent.VK_8;
                    case 1: text = "9"; return KeyEvent.VK_9;
                    case 2: text = "0"; return KeyEvent.VK_0;
                    case 3: text = "q"; return KeyEvent.VK_Q;
                    case 4: text = "w"; return KeyEvent.VK_W;
                    case 5: text = "e"; return KeyEvent.VK_E;
                    default:  text = "r"; return KeyEvent.VK_R;
                }
            case 2:
                switch (key) {
                    case 0: text = "t"; return KeyEvent.VK_T;
                    case 1: text = "y"; return KeyEvent.VK_Y;
                    case 2: text = "u"; return KeyEvent.VK_U;
                    case 3: text = "i"; return KeyEvent.VK_I;
                    case 4: text = "o"; return KeyEvent.VK_O;
                    case 5: text = "p"; return KeyEvent.VK_P;
                    default:  text = "a"; return KeyEvent.VK_A;
                }
            case 3:
                switch (key) {
                    case 0: text = "s"; return KeyEvent.VK_S;
                    case 1: text = "d"; return KeyEvent.VK_D;
                    case 2: text = "f"; return KeyEvent.VK_F;
                    case 3: text = "g"; return KeyEvent.VK_G;
                    case 4: text = "h"; return KeyEvent.VK_H;
                    case 5: text = "j"; return KeyEvent.VK_J;
                    default:  text = "k"; return KeyEvent.VK_K;
                }
            default:
                switch (key) {
                    case 0: text = "l"; return KeyEvent.VK_L;
                    case 1: text = "z"; return KeyEvent.VK_Z;
                    case 2: text = "x"; return KeyEvent.VK_X;
                    case 3: text = "c"; return KeyEvent.VK_C;
                    case 4: text = "v"; return KeyEvent.VK_V;
                    case 5: text = "b"; return KeyEvent.VK_B;
                    default:  text = "n"; return KeyEvent.VK_N;
                }
        }
    }

    private String keyToPitch(int key){
        switch (key){
            case 0: return "C";
            case 1: return "D";
            case 2: return "E";
            case 3: return "F";
            case 4: return "G";
            case 5: return "A";
            default: return "B";
        }

    }


    @Override
    public void stateChanged(ChangeEvent e) {
        ButtonModel model = getModel();

        try {
            if (model.isPressed() != pressed) {
                if (model.isPressed()) {
                    if (PianoFrame.waitToPlay) {
                        MusicSymbol symbol = PianoFrame.composition.getIndex(PianoFrame.flow.retCursor());
                        if (symbol instanceof Note) {
                            Note n = (Note) symbol;
                            StringBuilder note = new StringBuilder();
                            note.append(n.getPitch()); //C
                            note.append(n.getOctave()); //4
                            System.out.println(MusicSymbol.noteKey.get(note.toString()));
                            if (MusicSymbol.noteKey.get(note.toString()).equals(text)) {
                                PianoFrame.flow.moveCursor();
                                while (PianoFrame.composition.getIndex(PianoFrame.flow.retCursor()) instanceof Pause) {
                                    PianoFrame.flow.moveCursor();
                                }
                                System.out.println("henlo");
                            }
                        } else {
                            if (symbol instanceof Chord) {
                                Chord c = (Chord) symbol;
                                for (int i = 0; i < c.arrayLen(); i++) {
                                    Note n = (Note) c.getIndex(i);
                                    StringBuilder note = new StringBuilder();
                                    note.append(n.getPitch());
                                    note.append(n.getOctave());
                                    if (MusicSymbol.noteKey.get(note.toString()).equals(text)) {
                                        cnt++;
                                    }
                                }
                                if (cnt == c.arrayLen()) {
                                    PianoFrame.flow.moveCursor();
                                    while (PianoFrame.composition.getIndex(PianoFrame.flow.retCursor()) instanceof Pause) {
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
        } catch (IndexOutOfBoundsException g){}
    }
}
