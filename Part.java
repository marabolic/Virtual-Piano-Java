package symbols;

import java.awt.*;
import java.util.ArrayList;

public class Part {
    private ArrayList<MusicSymbol> musicSymbols;

    public Part() {
        musicSymbols = new ArrayList<MusicSymbol>();
    }

    public void addNote(Note note) {
        musicSymbols.add(note);
    }

    public void addPause(Pause pause) {
        musicSymbols.add(pause);
    }

    public void addChord(Chord chord) {
        musicSymbols.add(chord);
    }
}

