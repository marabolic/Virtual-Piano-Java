package symbols;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Composition {
    private ArrayList<MusicSymbol> musicSymbols;


    public Composition(String filename) {
        musicSymbols = new ArrayList<MusicSymbol>();
        readComposition(filename);
    }

    public int compositionSize(){
        return musicSymbols.size();
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

    public MusicSymbol getIndex(int i){
        return musicSymbols.get(i);
    }



    public void readComposition(String filename){
        try {
            File file = new File(filename);
            if (file == null) System.out.println("File is null");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String isSymbol = "\\[[^\\[\\]]*\\]|.";
                Pattern symbolPattern = Pattern.compile(isSymbol);
                Matcher symbolMatcher = symbolPattern.matcher(data);
                final List<String> matches2 = new ArrayList<>();
                while (symbolMatcher.find()) {

                    String chords = "(\\[([^ ]){2,}\\])"; //akord
                    Pattern chordPattern = Pattern.compile(chords);
                    Matcher chordMatcher = chordPattern.matcher(symbolMatcher.group(0));
                    if (chordMatcher.find()){

                        matches2.add(symbolMatcher.group(0));
                        String notes = "[^\\[\\]]";
                        Pattern notePattern = Pattern.compile(notes);
                        Matcher noteMatcher = notePattern.matcher(symbolMatcher.group(0));

                        Chord chord = new Chord();
                        while (noteMatcher.find()) {
                            String getNote = MusicSymbol.keyNote.get(noteMatcher.group(0));
                            Note note;
                            if (getNote.length() == 3)
                                note = new Note(MusicSymbol.DURATION.QUARTER, getNote.charAt(0),
                                        true,  Character.getNumericValue(getNote.charAt(2)));
                            else
                                note = new Note(MusicSymbol.DURATION.QUARTER, getNote.charAt(0),
                                        false,  Character.getNumericValue(getNote.charAt(1)));
                            chord.addNote(note);
                            //System.out.println("nota u akordu");
                        }
                        addChord(chord);
                    }

                    else if (symbolMatcher.group(0).length() == 1){ //cevrtina

                        if (symbolMatcher.group(0).equals("|")){
                            Pause shortPause = new Pause(MusicSymbol.DURATION.EIGHTH);
                            addPause(shortPause);
                            //System.out.println("kratka pauza");
                        }
                        else if (symbolMatcher.group(0).equals(" ")){
                            Pause longPause = new Pause(MusicSymbol.DURATION.QUARTER);
                            addPause(longPause);
                            //System.out.println("duga pauza");
                        }
                        else {
                            String getNote = MusicSymbol.keyNote.get(symbolMatcher.group(0));
                            Note note;
                            if (getNote.length() == 3)
                                note = new Note(MusicSymbol.DURATION.QUARTER, getNote.charAt(0),
                                        true,  Character.getNumericValue(getNote.charAt(2)));
                            else
                                note = new Note(MusicSymbol.DURATION.QUARTER, getNote.charAt(0),
                                        false,  Character.getNumericValue(getNote.charAt(1)));
                            addNote(note);
                           // System.out.println("cetvrtina");
                        }
                    }
                    else {
                        String eights = "[^\\[\\]\\ ]";
                        Pattern eightPattern = Pattern.compile(eights);
                        Matcher eightMatcher = eightPattern.matcher(symbolMatcher.group(0));
                        while (eightMatcher.find()) {
                            String getNote = MusicSymbol.keyNote.get(eightMatcher.group(0));
                            Note note;
                            if (getNote.length() == 3)
                                note = new Note(MusicSymbol.DURATION.EIGHTH, getNote.charAt(0),
                                        true,  Character.getNumericValue(getNote.charAt(2)));
                            else
                                note = new Note(MusicSymbol.DURATION.EIGHTH, getNote.charAt(0),
                                        false,  Character.getNumericValue(getNote.charAt(1)));
                            addNote(note);
                            //System.out.println("osmina"); //osmina
                        }
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
}
