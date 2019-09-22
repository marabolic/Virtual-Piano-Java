package symbols;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Composition {
    public Part leftPart;
    public Part rightPart;

    public Composition(Part left, Part right) {
        leftPart = left;
        rightPart = right;
    }


    public static void readComposition(String filename){
        Part leftPart = new Part();
        Part rightPart = new Part();
        try {
            File file = new File(filename);
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

                        Chord leftChord = new Chord(), rightChord = new Chord();
                        while (noteMatcher.find()) {
                            String getNote = MusicSymbol.keyNote.get(noteMatcher.group(0));
                            Note note;
                            if (getNote.length() == 3)
                                note = new Note(MusicSymbol.DURATION.QUARTER, getNote.charAt(0),
                                        true,  Character.getNumericValue(getNote.charAt(2)));
                            else
                                note = new Note(MusicSymbol.DURATION.QUARTER, getNote.charAt(0),
                                        false,  Character.getNumericValue(getNote.charAt(1)));
                            //Pause pause = new Pause(MusicSymbol.DURATION.QUARTER);
                            if (note.getOctave() > 3) {
                                rightChord.addNote(note);
                                //leftChord.addPause(pause);
                            }
                            else {
                                leftChord.addNote(note);
                                //rightChord.addPause(pause);
                            }
                            //System.out.println("nota u akordu");
                        }
                        rightChord.printTxt();
                        leftChord.printTxt();


                        leftPart.addChord(leftChord);
                        rightPart.addChord(rightChord);
                        //System.out.println(bracketMatcher.group(0));
                    }

                    else if (symbolMatcher.group(0).length() == 1){ //cevrtina

                        if (symbolMatcher.group(0).equals("|")){
                            Pause shortPause = new Pause(MusicSymbol.DURATION.EIGHTH);
                            leftPart.addPause(shortPause);
                            rightPart.addPause(shortPause);
                            shortPause.printTxt();
                            //System.out.println("kratka pauza");
                        }
                        else if (symbolMatcher.group(0).equals(" ")){
                            Pause longPause = new Pause(MusicSymbol.DURATION.QUARTER);
                            leftPart.addPause(longPause);
                            rightPart.addPause(longPause);
                            longPause.printTxt();
                            //System.out.println("duga pauza");
                        }
                        else {
                            String getNote = MusicSymbol.keyNote.get(symbolMatcher.group(0));
                            //System.out.println(symbolMatcher.group(0));
                            Note note;
                            if (getNote.length() == 3)
                                note = new Note(MusicSymbol.DURATION.QUARTER, getNote.charAt(0),
                                        true,  Character.getNumericValue(getNote.charAt(2)));
                            else
                                note = new Note(MusicSymbol.DURATION.QUARTER, getNote.charAt(0),
                                        false,  Character.getNumericValue(getNote.charAt(1)));
                            Pause pause = new Pause(note.getDuration());
                            if (note.getOctave() > 3) {
                                rightPart.addNote(note);
                                leftPart.addPause(pause);
                            }
                            else {
                                leftPart.addNote(note);
                                rightPart.addPause(pause);
                            }
                            note.printTxt();
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
                                note = new Note(MusicSymbol.DURATION.QUARTER, getNote.charAt(0),
                                        true,  Character.getNumericValue(getNote.charAt(2)));
                            else
                                note = new Note(MusicSymbol.DURATION.QUARTER, getNote.charAt(0),
                                        false,  Character.getNumericValue(getNote.charAt(1)));
                            Pause pause = new Pause(note.getDuration());
                            if (note.getOctave() > 3) {
                                rightPart.addNote(note);
                                leftPart.addPause(pause);
                            }
                            else {
                                leftPart.addNote(note);
                                rightPart.addPause(pause);
                            }
                            note.printTxt();
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
        Composition composition = new Composition(leftPart, rightPart);

    }
}
