package agh.se;

import jpl.Atom;
import jpl.Query;
import jpl.Term;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private static Main instance;
    private JButton tempButton;
    private JTextArea topDescription;
    private JButton yesButton;
    private JButton noButton;
    private String answer = "n";

    private Main() {
        Container pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        setTitle("Java Prolog");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        topDescription = new JTextArea("Wybierz\n");
        pane.add(topDescription);
        yesButton = new JButton("TAK");
        pane.add(yesButton);
        noButton = new JButton("NIE");
        pane.add(noButton);
        tempButton = new JButton("37.9");
        pane.add(tempButton);

        JButton quitButton = new JButton("Koniec");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                answer = "t.";
                Main.getInstance().wakeUp();
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                answer = "n.";
                Main.getInstance().wakeUp();
            }
        });

        tempButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                answer = "37.9.";
                Main.getInstance().wakeUp();
            }
        });

        pane.add(quitButton);
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
                Main main = Main.getInstance();
                main.setVisible(true);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Query q1 = new Query("consult", new Term[]{new Atom("program.pl")});
                        q1.hasSolution();

//		Query query = new Query(new Compound("elem", new Term[] {
//				new Atom("a"),
//				Util.termArrayToList(new Term[] { atom("a"), atom("b"),
//						atom("c") }) }));

                        Query query = new Query("start");
                        query.hasSolution();

//                        query = new Query("assertz", new Term[]{new Atom("temperatura", "39.1")});

//        System.out.println("ELEMENT " + query.hasSolution());


                    }
                });
                thread.start();
            }
        });
    }

    public static void printQuestion(String value) {
        getInstance().topDescription.append(value + "\n");
    }

    public static String getAnswer() {
        return getInstance().answer;
    }

    public synchronized void wakeUp() {
        notifyAll();
    }

    public synchronized void waitForIt() throws InterruptedException {
        wait();
    }

    public static void waitForAnswer() throws InterruptedException {
        Main.getInstance().waitForIt();
    }

}
