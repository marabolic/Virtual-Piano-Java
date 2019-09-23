package symbols;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

public abstract class MusicSymbol {

    public static HashMap<String, Integer> noteMidi;
    public static HashMap<String, String> keyNote;
    public static HashMap<String, String> noteKey;
    public enum  DURATION {QUARTER, EIGHTH}
   //public enum MIDI : String { C = "C", "C#", "D", "D#" ,"E", "F", "F#", "G", "G#", "A", "A#", "B"}
    private DURATION duration; //4 or 8
    private String midiChar;
    private boolean inChord = false;
    private boolean firstSplit = false, secondSplit = false;

    public MusicSymbol(DURATION duration) {
        this.duration = duration;
    }

    public String getMidiChar() {
        return midiChar;
    }

    public void setDuration(DURATION duration) {
        this.duration = duration;
    }

    public DURATION getDuration() {
        return duration;
    }

    public abstract String printTxt(boolean isTxt);

    public void setMidiChar(String midiChar){
        this.midiChar = midiChar;
    }

    public boolean isInChord() {
        return inChord;
    }

    public void setInChord(boolean inChord) {
        this.inChord = inChord;
    }

    public boolean isFirstSplit() {
        return firstSplit;
    }

    public void setFirstSplit(boolean firstSplit) {
        this.firstSplit = firstSplit;
    }

    public boolean isSecondSplit() {
        return secondSplit;
    }

    public void setSecondSplit(boolean secondSplit) {
        this.secondSplit = secondSplit;
    }

    public static void readMap(String filename){
        noteMidi = new HashMap<String, Integer>();
        noteKey = new HashMap<String, String>();
        keyNote = new HashMap<String, String>();
        try {
            File file = new File(filename);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String pattern = "(.*),(.*),(.*)"; // t, C4, 60

                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(data);
                if (m.find( )) {
                    String key = m.group(1);
                    String note = m.group(2);
                    String midi = m.group(3);
                    keyNote.put(key, note);
                    noteKey.put(note, key);
                    noteMidi.put(note, Integer.parseInt(midi));
                }else {
                    System.out.println("NO MATCH");
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public static void printMap(){
        noteMidi.forEach((k,v) -> System.out.println(k + ", " + v));
        keyNote.forEach((k,v) -> System.out.println(k + ", " + v));
    }
}
