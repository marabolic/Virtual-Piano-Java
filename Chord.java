package symbols;

import java.util.ArrayList;
import java.util.List;

public class Chord extends MusicSymbol {

    private List notes = new ArrayList<Note>();

    public Chord(){ super(DURATION.QUARTER); }
    public void addNote(Note newNote){
        notes.add(newNote);
    }
    public void addPause(Pause pause){
        notes.add(pause);
    }
    public void divideChord() {
       for (int i = 0; i < notes.size(); i++ ){
            Note n = (Note)notes.get(i);
            n.setDuration(DURATION.EIGHTH);
       }

    }
    @Override
    public void printTxt() {
        for (int i = 0; i < notes.size(); i++){
            Note n = (Note)notes.get(i);
            n.printTxt();
        }

    }
}
