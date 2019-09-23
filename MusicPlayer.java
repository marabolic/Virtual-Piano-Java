package symbols;

import javax.sound.midi.MidiUnavailableException;

public class MusicPlayer extends Thread {

    private boolean flag = false;
    private boolean runCompleted = false;

    public MusicPlayer() {
        start();
    }

    public boolean isRunCompleted() {
        return runCompleted;
    }

    public synchronized void play(){
        flag = true;
        notifyAll();
    }
    public synchronized void pause(){
        flag = false;
    }

    @Override
    public void run() {
        if (PianoFrame.composition == null){
            runCompleted = true;
            return;
        }
        try {
        for (int i = 0; i < PianoFrame.composition.compositionSize(); i++) {
                if (interrupted()) {
                    runCompleted = true;
                    return;
                }
                synchronized (this) {
                    while (!flag) wait();
                }
                MidiPlayer m = new MidiPlayer();
                if (PianoFrame.composition.getIndex(i) instanceof Note) {
                    Note n = (Note)PianoFrame.composition.getIndex(i);
                    StringBuilder note = new StringBuilder();
                    note.append(n.getPitch());
                    if (n.isSharp())
                        note.append('#');
                    note.append(n.getOctave());
                    if (n.getDuration() == MusicSymbol.DURATION.QUARTER) {
                        m.play(MusicSymbol.noteMidi.get(note.toString()), PianoFrame.LONG_PAUSE);
                    } else
                        m.play(MusicSymbol.noteMidi.get(note.toString()), PianoFrame.SHORT_PAUSE);
                    PianoFrame.flow.moveCursor();
                    PianoFrame.flow.repaint();
                } else {
                    if (PianoFrame.composition.getIndex(i) instanceof Pause) {
                        Pause p = (Pause)PianoFrame.composition.getIndex(i);
                        if (p.getDuration() == MusicSymbol.DURATION.QUARTER)
                            m.pause(PianoFrame.LONG_PAUSE);
                        else
                            m.pause(PianoFrame.SHORT_PAUSE);
                        PianoFrame.flow.moveCursor();
                    } else {  //Chord
                        Chord c = (Chord)PianoFrame.composition.getIndex(i);
                        for (int j = 0; j < c.arrayLen(); j++) {
                            Note n = (Note) c.getIndex(j);
                            StringBuilder note = new StringBuilder();
                            note.append(n.getPitch());
                            if (n.isSharp())
                                note.append('#');
                            note.append(n.getOctave());
                            m.play(MusicSymbol.noteMidi.get(note.toString()));
                        }
                        Thread.sleep(PianoFrame.LONG_PAUSE);
                        for (int j = 0; j < c.arrayLen(); j++) {
                            Note n = (Note) c.getIndex(j);
                            StringBuilder note = new StringBuilder();
                            note.append(n.getPitch());
                            if (n.isSharp())
                                note.append('#');
                            note.append(n.getOctave());
                            m.release(MusicSymbol.noteMidi.get(note.toString()));
                        }
                        PianoFrame.flow.moveCursor();
                    }
                }
            }
        }catch (MidiUnavailableException | InterruptedException e) {
        }
        runCompleted = true;
    }
}
