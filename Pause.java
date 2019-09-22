package symbols;

public class Pause extends MusicSymbol {
    public Pause(DURATION duration) {
        super(duration);
    }

    @Override
    public void printTxt() {
        String pause = "";
        if (getDuration() == DURATION.QUARTER)
            pause = " ";
        else
            pause = "|";
        System.out.println(pause);
    }
}
