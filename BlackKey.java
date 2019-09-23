package symbols;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BlackKey extends JButton implements ActionListener {
    private int octave;
    private Octaves.BLACK_KEY key;

    private int keyCode;
    private String text;

    public BlackKey (int octave, Octaves.BLACK_KEY b){
        super();
        this.octave = octave;
        key = b;
        text = " ";
        keyCode = getKeyEvent();
        addActionListener(this);
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
