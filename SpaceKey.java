package symbols;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class SpaceKey extends JButton implements ChangeListener {
    private int keyCode;
    private boolean pressed = false;
    private long time = 0;

    public SpaceKey() {
        keyCode = KeyEvent.VK_SPACE;
        addChangeListener(this);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, InputEvent.SHIFT_DOWN_MASK),
                "pressed");
        getActionMap().put("pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                getModel().setPressed(true);
            }
        });
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, InputEvent.SHIFT_DOWN_MASK,
                true), "released");
        getActionMap().put("released", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                getModel().setPressed(false);
            }
        });
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        ButtonModel model = getModel();
        if (PianoFrame.myComposition == null) return;
        try {
            if (model.isPressed() != pressed) {
                if (model.isPressed()) {
                    if (PianoFrame.recordPress) {
                        time = System.currentTimeMillis();
                    }
                } else {
                    time = System.currentTimeMillis() - time;
                    if (time < 400) {
                        PianoFrame.myComposition.append("|");
                    } else {
                        PianoFrame.myComposition.append(" ");
                    }
                }
            }
            pressed = model.isPressed();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
