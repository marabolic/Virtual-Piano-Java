package symbols;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Octaves extends JLayeredPane {
    public int octaveID;
    private boolean showText = false;

    private ArrayList<WhiteKey> whiteKeyArray;
    private ArrayList<BlackKey> blackKeyArray;

    public static enum BLACK_KEY {CSHARP, DSHARP, FSHARP, GSHARP, ASHARP}

    public static final int PIANO_WIDTH = 1400, PIANO_HEIGHT = 200,
            WHITE_W = 33, WHITE_H = 200, GAP = 1, BLACK_W = 20, BLACK_H = 120,
            OCTAVE_WIDTH = PianoFrame.NUM_KEYS * WHITE_W + (PianoFrame.NUM_KEYS - 1) * GAP;

    public Octaves() {
        super();
        int x = 110, y = 0;
        this.setPreferredSize(new Dimension(PIANO_WIDTH, PIANO_HEIGHT));
        this.add(Box.createRigidArea(new Dimension(0, 0)));
        this.setBackground(Color.DARK_GRAY);
        this.setOpaque(true);
        setFocusable(true);
        requestFocusInWindow();
        whiteKeyArray = new ArrayList<WhiteKey>();
        blackKeyArray = new ArrayList<BlackKey>();
        try {
            makeKeys(x, y);
        } catch (MidiUnavailableException e) {
            //e.printStackTrace();
        }
    }

    public boolean getShowText() {
        return showText;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
        if (showText) {
            for (int i = 0; i < whiteKeyArray.size(); i++) {
                whiteKeyArray.get(i).setText(whiteKeyArray.get(i).getMyText());
            }
            for (int i = 0; i < blackKeyArray.size(); i++) {
                blackKeyArray.get(i).setText(blackKeyArray.get(i).getMyText());
            }
        } else {
            for (int i = 0; i < whiteKeyArray.size(); i++) {
                whiteKeyArray.get(i).setText(" ");
            }
            for (int i = 0; i < blackKeyArray.size(); i++) {
                blackKeyArray.get(i).setText(" ");
            }
        }
    }

    public void makeKeys(int x, int y) throws MidiUnavailableException {


        for (int i = 0; i < PianoFrame.NUM_OCTAVES; i++) {
            for (int j = 0; j < PianoFrame.NUM_KEYS; j++) {

                WhiteKey whiteButton = new WhiteKey(i, j);
                whiteButton.setBounds(x + i * OCTAVE_WIDTH + i * GAP, y, WHITE_W, WHITE_H);
                whiteButton.setBackground(Color.white);
                whiteButton.setMargin(new Insets(0, 0, 0, 0));
                whiteButton.setFont(new Font("Arial", Font.PLAIN, 15));
                whiteButton.setForeground(Color.black);
                whiteButton.setMargin(new Insets(0, 0, 0, 0));
                whiteButton.setFont(new Font("Arial", Font.PLAIN, 15));

                whiteKeyArray.add(whiteButton);
                this.add(whiteButton, new Integer(1));
                this.add(Box.createRigidArea(new Dimension(2, 0)));
                x += WHITE_W + GAP;
            }
            x = 110;
        }

        for (int i = 0; i < PianoFrame.NUM_OCTAVES; i++) {
            BlackKey b1 = new BlackKey(i, BLACK_KEY.CSHARP);
            BlackKey b2 = new BlackKey(i, BLACK_KEY.DSHARP);
            BlackKey b3 = new BlackKey(i, BLACK_KEY.FSHARP);
            BlackKey b4 = new BlackKey(i, BLACK_KEY.GSHARP);
            BlackKey b5 = new BlackKey(i, BLACK_KEY.ASHARP);
            b1.setMargin(new Insets(0, 0, 0, 0));
            b1.setFont(new Font("Arial", Font.PLAIN, 14));
            b2.setMargin(new Insets(0, 0, 0, 0));
            b2.setFont(new Font("Arial", Font.PLAIN, 14));
            b3.setMargin(new Insets(0, 0, 0, 0));
            b3.setFont(new Font("Arial", Font.PLAIN, 14));
            b4.setMargin(new Insets(0, 0, 0, 0));
            b4.setFont(new Font("Arial", Font.PLAIN, 14));
            b5.setMargin(new Insets(0, 0, 0, 0));
            b5.setFont(new Font("Arial", Font.PLAIN, 14));

            int overlapWidth = (BLACK_W - GAP) / 2;
            b1.setBounds(x + WHITE_W - overlapWidth + OCTAVE_WIDTH * i, y, BLACK_W, BLACK_H);
            b2.setBounds(x + WHITE_W * 2 + GAP - overlapWidth + OCTAVE_WIDTH * i, y, BLACK_W, BLACK_H);
            b3.setBounds(x + WHITE_W * 4 + 3 * GAP - overlapWidth + OCTAVE_WIDTH * i, y, BLACK_W, BLACK_H);
            b4.setBounds(x + WHITE_W * 5 + 4 * GAP - overlapWidth + OCTAVE_WIDTH * i, y, BLACK_W, BLACK_H);
            b5.setBounds(x + WHITE_W * 6 + 5 * GAP - overlapWidth + OCTAVE_WIDTH * i, y, BLACK_W, BLACK_H);

            b1.setBackground(Color.black);
            b2.setBackground(Color.black);
            b3.setBackground(Color.black);
            b4.setBackground(Color.black);
            b5.setBackground(Color.black);

            blackKeyArray.add(b1);
            blackKeyArray.add(b2);
            blackKeyArray.add(b3);
            blackKeyArray.add(b4);
            blackKeyArray.add(b5);

            this.add(b1, new Integer(2));
            this.add(b2, new Integer(2));
            this.add(b3, new Integer(2));
            this.add(b4, new Integer(2));
            this.add(b5, new Integer(2));
        }
    }



}