package symbols;

public class Pause extends MusicSymbol {
    public Pause(DURATION duration) {
        super(duration);
    }

    @Override
    public String printTxt(boolean isTxt) {
        String pause = "";
        if (isTxt) {
            if (getDuration() == DURATION.QUARTER)
                pause = " ";
            else
                pause = "|";
        }
        return pause.toString();
    }

    @Override
    public String toString() {
        return " ";
    }
}
