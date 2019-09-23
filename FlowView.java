package symbols;

import javax.swing.*;
import java.awt.*;

public class FlowView extends JPanel {

    private static final int NUM_OF_QUARTERS = 10;
    private Composition composition = null;
    private int cursor;
    private boolean isTxt = false;

    public FlowView() {
        setBackground(Color.GRAY);
    }

    void loadComposition(Composition composition){
        this.composition = composition;
        cursor = 0;
        repaint();
    }

    public void resetCursor(){
        cursor = 0;
    }

    public void setTxt(boolean txt) {
        isTxt = txt;
    }

    public boolean getTxt() {
        return isTxt;
    }

    public void moveCursor(){
        cursor++;
        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < NUM_OF_QUARTERS; i++){
            g.fillRect(i * getWidth()/NUM_OF_QUARTERS, 4 * getHeight()/5, 3, getHeight() / 6);
        }

        if (composition == null)
            return;

        int displayDuration =  0;
        int j = 0;
        while (displayDuration < NUM_OF_QUARTERS * 2 && (cursor + j) < composition.compositionSize()){
            MusicSymbol symbol = composition.getIndex(cursor + j);
            System.out.println(cursor);
            if (symbol instanceof Pause){
                if (symbol.getDuration() == MusicSymbol.DURATION.QUARTER){
                    g.setColor(Color.getHSBColor(0.96f, 0.74f, 0.40f));
                    g.fillRect(displayDuration * (getWidth()/(NUM_OF_QUARTERS * 2)), getHeight()/2 - getHeight()/20,
                            getWidth()/NUM_OF_QUARTERS, getHeight()/10);
                }
                else {
                    g.setColor(Color.getHSBColor(0.3f, 0.58f, 0.30f));
                    g.fillRect(displayDuration * (getWidth()/(NUM_OF_QUARTERS * 2)), getHeight()/2 - getHeight()/20,
                            getWidth()/(2 * NUM_OF_QUARTERS), getHeight()/10);
                }
            }

            if (symbol instanceof Note) {
                if (symbol.getDuration() == MusicSymbol.DURATION.QUARTER) {
                    g.setColor(Color.getHSBColor(0.98f, 0.74f, 0.55f));
                    g.fillRect(displayDuration * (getWidth()/(NUM_OF_QUARTERS * 2)), getHeight()/2 - getHeight()/20,
                             getWidth()/NUM_OF_QUARTERS, getHeight()/10);
                    g.setColor(Color.black);
                    g.drawString(symbol.printTxt(isTxt),displayDuration * (getWidth()/(NUM_OF_QUARTERS * 2)) +
                                    getWidth()/(2 * NUM_OF_QUARTERS), getHeight()/2 + getHeight()/20 );
                }
                else {
                    g.setColor(Color.getHSBColor(0.3f, 0.74f, 0.55f));
                    g.fillRect(displayDuration * (getWidth()/(NUM_OF_QUARTERS * 2)), getHeight()/2 - getHeight()/20,
                            getWidth()/(2 * NUM_OF_QUARTERS), getHeight()/10);
                    g.setColor(Color.black);
                    g.drawString(symbol.printTxt(isTxt),displayDuration * (getWidth()/(NUM_OF_QUARTERS * 2)) +
                                    getWidth()/(4 * NUM_OF_QUARTERS), getHeight()/2 + getHeight()/20);
                }
            }

            if (symbol instanceof Chord){
                g.setColor(Color.getHSBColor(0.98f, 0.74f, 0.55f));
                Chord chord = (Chord)symbol;
                g.fillRect(displayDuration * (getWidth()/(NUM_OF_QUARTERS * 2)), getHeight()/2 - getHeight()/20,
                        getWidth()/NUM_OF_QUARTERS, getHeight()/10);
                g.setColor(Color.black);
                g.drawString(((Chord) symbol).printTxtIndex(isTxt, 0),displayDuration * (getWidth()/(NUM_OF_QUARTERS * 2)) +
                        getWidth()/(2 * NUM_OF_QUARTERS), getHeight()/2 + getHeight()/20 );
                for (int i = 1; i < chord.arrayLen(); i++) {
                    if (i % 2 == 0) { //DOWN
                        g.setColor(Color.getHSBColor(0.98f, 0.74f, 0.55f));
                        g.fillRect(displayDuration * (getWidth() / (NUM_OF_QUARTERS * 2)), getHeight() / 2 + getHeight() / 20,
                                getWidth() / NUM_OF_QUARTERS, getHeight() / 10);
                        g.setColor(Color.black);
                        g.drawString(((Chord) symbol).printTxtIndex(isTxt, i), displayDuration * (getWidth() / (NUM_OF_QUARTERS * 2)) +
                                getWidth() / (2 * NUM_OF_QUARTERS), getHeight() / 2 + getHeight() / 7);
                    }
                    if (i % 2 == 1) { //UP
                        g.setColor(Color.getHSBColor(0.98f, 0.74f, 0.55f));
                        g.fillRect(displayDuration * (getWidth() / (NUM_OF_QUARTERS * 2)), getHeight() / 2 - getHeight() / 7,
                                getWidth() / NUM_OF_QUARTERS, getHeight() / 10);
                        g.setColor(Color.black);
                        g.drawString(((Chord) symbol).printTxtIndex(isTxt, i), displayDuration * (getWidth() / (NUM_OF_QUARTERS * 2)) +
                                getWidth() / (2 * NUM_OF_QUARTERS), getHeight() / 2 - getHeight() / 20);
                    }
                }
            }

            if (symbol.getDuration() == MusicSymbol.DURATION.QUARTER)
                displayDuration += 2;
            else
                displayDuration++;
            j++;
        }

    }


}
