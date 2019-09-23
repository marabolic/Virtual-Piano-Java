package symbols;


import symbols.MusicSymbol;

public class Note extends MusicSymbol {
    private char pitch;
    private int octave;
    private boolean sharp;

    public Note(DURATION dur, char pitch, boolean sharp, int octave) {
        super(dur);
        this.pitch = pitch;
        this.octave = octave;
        this.sharp = sharp;
    }

    public char getPitch() {
        return pitch;
    }

    public void setPitch(char pitch) {
        this.pitch = pitch;
    }

    public int getOctave() {
        return octave;
    }

    public void setOctave(int octave) throws OctaveException {
        if ((this.octave > 3 && octave > 3) || (this.octave < 3 && octave < 3))
            octave = octave;
        if ((this.octave > 3 && octave < 3) || (this.octave < 3 && octave > 3))
            throw new OctaveException();
    }

    public boolean isSharp() {
        return sharp;
    }

    public void setSharp(boolean sharp) throws SharpException{
        if (this.pitch == 'E' || this.pitch == 'B')
            throw new SharpException();
        this.sharp = sharp;
    }

    @Override
    public String printTxt(boolean isTxt) {
        StringBuilder note = new StringBuilder();
        if (isTxt) {
            note.append(pitch);
            if (sharp)
                note.append('#');
            note.append(octave);
            String key = noteKey.get(note.toString());
        }
        else{

        }
        return note.toString();
    }

    @Override
    public String toString() {
        StringBuilder note = new StringBuilder();
        note.append(pitch);
        if (sharp)
            note.append('#');
        note.append(octave);
        return note.toString();
    }
}
