package symbols;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WhiteKey extends JButton implements ActionListener {
    private int key;
    private int octave;

    public WhiteKey(int octave, int key){
        super();
        this.octave = octave;
        this.key = key;
        addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
