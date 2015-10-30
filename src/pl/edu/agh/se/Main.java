package pl.edu.agh.se;


import jpl.Atom;
import jpl.Query;
import jpl.Term;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private JTextArea topDescription;
    private JButton yesButton;
    private JButton noButton;

    public Main() {
        initializeGUI();

        jpl.Query q1 = new Query("consult",
                new Term[]{new Atom("cos.pl")});
        q1.oneSolution();

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }

    private void initializeGUI() {
        Container pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        setTitle("Java Prolog");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        topDescription = new JTextArea("Wybierz");
        pane.add(topDescription);
        yesButton = new JButton("TAK");
        pane.add(yesButton);
        noButton = new JButton("NIE");
        pane.add(noButton);

        JButton quitButton = new JButton("Koniec");
        quitButton.addActionListener(event -> System.exit(0));
        pane.add(quitButton);
    }
}
