package symbols;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackKey extends JButton implements ActionListener {
    private int octave;
    private Octaves.BLACK_KEY key;
    public BlackKey (int octave, Octaves.BLACK_KEY b){
        super();
        this.octave = octave;
        key = b;
        addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        
    }
}
