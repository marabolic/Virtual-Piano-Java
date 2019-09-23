package symbols;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class PianoFrame extends JFrame {

    public static final int NUM_OCTAVES = 5;
    public static final int NUM_KEYS = 7;
    public static Composition composition;
    public static FlowView flow;
    public static Octaves octaves;

    public PianoFrame() {
        super("Virtual Piano");
        createEnviroment();
    }

    static private JLayeredPane makeK(){
        int x = 0, y = 0;
        JLayeredPane jl = new JLayeredPane();
        jl.setPreferredSize(new Dimension(1400,162));
        jl.add(Box.createRigidArea(new Dimension(0, 0)));

        for(int i=0; i< NUM_OCTAVES; i++) {
            for (int j = 0; j < NUM_KEYS; j++) {

                JButton whiteButton = new JButton();
                whiteButton.setBounds(x, y, 30, 162);
                whiteButton.setBackground(Color.white);
                jl.add(whiteButton, new Integer(1));
                jl.add(Box.createRigidArea(new Dimension(2, 0)));
                x += 32;
            }
        }

        x = 0;

        for(int i=0; i< NUM_OCTAVES; i++){
            JButton b1 = new JButton();
            JButton b2 = new JButton();
            JButton b3 = new JButton();
            JButton b4 = new JButton();
            JButton b5 = new JButton();
            b1.setBounds(77+(260*i),y,15,95);
            b2.setBounds(115+(260*i),y,15,95);
            b3.setBounds(188+(260*i),y,15,95);
            b4.setBounds(226+(260*i),y,15,95);
            b5.setBounds(264+(260*i),y,15,95);

            b1.setBackground(Color.black);
            b2.setBackground(Color.black);
            b3.setBackground(Color.black);
            b4.setBackground(Color.black);
            b5.setBackground(Color.black);

            jl.add(b1, new Integer(2));
            jl.add(b2, new Integer(2));
            jl.add(b3, new Integer(2));
            jl.add(b4, new Integer(2));
            jl.add(b5, new Integer(2));
        }
        return jl;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PianoFrame n = new PianoFrame();
            }
        });
    }



    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu;
        JMenuItem menuItem;
        JCheckBoxMenuItem cbMenuItem;

        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        menuItem = new JMenuItem("Choose file");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Text file", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    composition = new Composition(chooser.getSelectedFile().getName());
                    flow.loadComposition(composition);
                }
            }
        });
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        menu.add(menuItem);

        menu = new JMenu("Options");
        menu.setMnemonic(KeyEvent.VK_N);

        cbMenuItem = new JCheckBoxMenuItem("Display keys");
        cbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                flow.setTxt(!flow.getTxt());
                flow.repaint();
            }
        });
        menu.add(cbMenuItem);

        cbMenuItem = new JCheckBoxMenuItem("Key assist");
        cbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                octaves.setShowText(!octaves.getShowText());
                octaves.makeKeys(110,0);
            }
        });
        menu.add(cbMenuItem);

        cbMenuItem = new JCheckBoxMenuItem("Wait to play");
        menu.add(cbMenuItem);

        menuBar.add(menu);
        menuBar.setBounds(0,0,15,50);
        this.setJMenuBar(menuBar);
    }

    private void addPianoPanel(Composition composition){

        JPanel piano = new JPanel();
        JPanel buttons = new JPanel();
        JPanel keys = new JPanel();
        piano.setLayout(new BorderLayout());
        buttons.setLayout(new FlowLayout());

        JButton start, pause, stop, record;
        start = new JButton("▶");
        start.setForeground(Color.getHSBColor(0.3f, 0.69f, 0.69f));
        pause = new JButton("⏸");
        pause.setForeground(Color.blue);
        stop = new JButton("⏹");
        stop.setForeground(Color.black);
        record = new JButton("⏺");
        record.setForeground(Color.red);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    MidiPlayer player = new MidiPlayer();
                } catch (MidiUnavailableException e) {
                    e.printStackTrace();
                }

            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        record.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        buttons.setBackground(Color.DARK_GRAY);
        buttons.add(start);
        buttons.add(pause);
        buttons.add(stop);
        buttons.add(record);

        keys.setBackground(Color.DARK_GRAY);
        piano.setBackground(Color.DARK_GRAY);
        piano.setSize(300, 300);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 6;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 30, 90, 30);

        octaves = new Octaves();
        keys.add(octaves);
        piano.add(buttons, BorderLayout.NORTH);
        piano.add(keys, BorderLayout.CENTER);
        this.add(piano, c);
    }

    private FlowView addFlowPanel(){
        flow = new FlowView();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(50, 120, 10, 120);
        this.add(flow, c);
        flow.repaint();
        return flow;
    }

    private void createEnviroment() {
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500, 650);
        MusicSymbol.readMap("map.txt");
        flow = addFlowPanel();
        addPianoPanel(composition);
        addMenu();
        this.setVisible(true);
    }
}
