package pl.edu.agh.se;

import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.term.Term;
import gnu.prolog.vm.Environment;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.PrologException;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        initializeGUI();

        try {
            Environment env = new Environment();
            env.ensureLoaded(AtomTerm.get("cos.pl"));
            Interpreter interpreter = env.createInterpreter();

            env.runInitialization(interpreter);

            Term[] list = new Term[]{AtomTerm.get("e"), AtomTerm.get("b"), AtomTerm.get("c")};
            Term[] arguments = {AtomTerm.get("a"), CompoundTerm.getList(list)};
            CompoundTerm goalTerm = new CompoundTerm(AtomTerm.get("elem"), arguments);

            Interpreter.Goal goal = interpreter.prepareGoal(goalTerm);

            System.out.println(interpreter.execute(goal));
        } catch (PrologException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }

    private void initializeGUI() {
        JButton quitButton = new JButton("Quit");

        quitButton.addActionListener(event -> System.exit(0));

        setTitle("Java Prolog");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(quitButton);
    }
}
