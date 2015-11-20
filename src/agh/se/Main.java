package agh.se;

import jpl.Atom;
import jpl.Query;
import jpl.Term;
import jpl.Variable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private static Main instance;
    private JTextArea topDescription;
    private JButton yesButton;
    private JButton noButton;

    private Main() {
        initializeGUI();

        Query q1 = new Query("consult", new Term[]{new Atom("program.pl")});

        System.out.println("loaded successfully: " + q1.hasSolution());

//		Query query = new Query(new Compound("elem", new Term[] {
//				new Atom("a"),
//				Util.termArrayToList(new Term[] { atom("a"), atom("b"),
//						atom("c") }) }));
        Variable var = new Variable("L");
        Query query = new Query("start");
        query.hasSolution();

        query = new Query("assertz", new Term[]{new Atom("temperatura", "39.1")});

        System.out.println("ELEMENT " + query.hasSolution());



        topDescription.append("elem('f', ['a', 'b', 'c']");
    }

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main main = new Main();
                main.setVisible(true);
            }
        });
    }

    public static void printSomething(String value) {
        System.err.println("Tekst: " + value);
    }


    private void initializeGUI() {
        Container pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        setTitle("Java Prolog");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        topDescription = new JTextArea("Wybierz\n");
        pane.add(topDescription);
        // yesButton = new JButton("TAK");
        // pane.add(yesButton);
        // noButton = new JButton("NIE");
        // pane.add(noButton);

        JButton quitButton = new JButton("Koniec");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        pane.add(quitButton);
    }
}
