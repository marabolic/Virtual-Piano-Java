package symbols;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;


public class WhiteKey extends JButton implements ActionListener, KeyListener {
    private int key;
    private int octave;
    private int keyCode;
    private String text;
    public static HashMap<Integer, Integer> keyButtons;

    public WhiteKey(int octave, int key){
        super();
        this.octave = octave;
        this.key = key;
        keyCode = getKeyEvent();
        addKeyListener(this);
        addActionListener(this);
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
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            MidiPlayer m = new MidiPlayer();
            StringBuilder s = new StringBuilder();
            s.append(keyToPitch(key)).append(octave + 2);
            m.play(MusicSymbol.noteMidi.get(s.toString()));
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == keyCode){
            try {
                MidiPlayer m = new MidiPlayer();
                StringBuilder s = new StringBuilder();
                s.append(keyToPitch(key)).append(octave + 2);
                m.play(MusicSymbol.noteMidi.get(s.toString()));
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        if (keyEvent.getKeyCode() == keyCode){
            //doClick();
            try {
                MidiPlayer m = new MidiPlayer();
                StringBuilder s = new StringBuilder();
                s.append(keyToPitch(key)).append(octave + 2);
                m.play(MusicSymbol.noteMidi.get(s.toString()));
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
