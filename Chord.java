package symbols;

import java.util.ArrayList;
import java.util.List;

public class Chord extends MusicSymbol {

    private ArrayList<Note> notes = new ArrayList<Note>();

    public Chord(){ super(DURATION.QUARTER); }
    public void addNote(Note newNote){
        notes.add(newNote);
    }
    public void divideChord() {
       for (int i = 0; i < notes.size(); i++ ){
            Note n = (Note)notes.get(i);
            n.setDuration(DURATION.EIGHTH);
       }
    }

    public int arrayLen(){
        return notes.size();
    }

    public Note getIndex(int i){
        return notes.get(i);
    }

    @Override
    public String printTxt(boolean isTxt) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < notes.size(); i++) {
            Note n = (Note) notes.get(i);
            s.append(n.printTxt(isTxt)).append("\n");
        }
        return s.toString();
    }

    public String printTxtIndex(boolean isTxt, int index){
        StringBuilder s = new StringBuilder();
        Note n = (Note) notes.get(index);
        s.append(n.printTxt(isTxt));
        return  s.toString();
    }



}
