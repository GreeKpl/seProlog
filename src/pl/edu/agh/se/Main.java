package pl.edu.agh.se;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import jpl.Atom;
import jpl.Compound;
import jpl.Query;
import jpl.Term;
import jpl.Util;

public class Main extends JFrame {

	private JTextArea topDescription;
	private JButton yesButton;
	private JButton noButton;

	public Main() {
		initializeGUI();

		Query q1 = new Query("consult", new Term[] { new Atom("cos.pl") });

		System.out.println("loaded successfully: " + q1.hasSolution());

		Query query = new Query(new Compound("elem", new Term[] {
				new Atom("a"),
				Util.termArrayToList(new Term[] { atom("a"), atom("b"),
						atom("c") }) }));
		topDescription.append("elem('f', ['a', 'b', 'c']");

		System.out.println(query.hasSolution());

	}

	private static Atom atom(String value) {
		return new Atom(value);
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
