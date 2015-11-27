package agh.se;

import jpl.Atom;
import jpl.Compound;
import jpl.Query;
import jpl.Term;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	private static Main instance;

    private final JTextField temperatureField;
    private final Container mainPane;
    private JPanel row;

    private JLabel topDescription;
    private JButton yesButton;
    private JButton noButton;

    private static final String YES = "t";
    private static final String NO = "n";

    private String answer = YES;

    private Main() {
        mainPane = getContentPane();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));

        setTitle("Program sugerujący lek");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // temperature row
        row = new JPanel();
        row.add(new JLabel("Temperatura: "));
        temperatureField = new JTextField();
        temperatureField.setColumns(6);
        row.add(temperatureField);
        mainPane.add(row);
        JButton button = new JButton("OK");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSelectedTemperature();
            }
        });
        mainPane.add(button);
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

                main.start();
            }
        });
    }

    public static void printQuestion(String value) {
        getInstance().topDescription.setText("Czy odczuwasz: " + value + "?");
    }

    public static void printSolution(String illness, String medicine) {
        getInstance().clearButtons();
        getInstance().topDescription.setText("<html>Stwierdzona choroba: " + illness + "<br>Proponowany lek: " + medicine + "</html>");

    }

    public static void informAboutNoSolution() {
        getInstance().clearButtons();
        getInstance().topDescription.setText("<html>Niestety nie udało się znaleźć rozwiązania.<br>Skontaktuj się z lekarzem</html>");

    }

    public static String getAnswer() {
        getInstance().topDescription.setText("");

        return getInstance().answer;
    }

    public static void waitForAnswer() throws InterruptedException {
        Main.getInstance().waitForIt();
    }

    private void clearButtons() {
        mainPane.remove(row);
    }

    public void onSelectedTemperature() {
        try {
            double temp = Double.parseDouble(temperatureField.getText());
            Query q = new Query("assertz", new Compound("temperatura", new Term[]{new jpl.Float(temp)}));
            q.hasSolution();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Query q = new Query("start");
                    q.hasSolution();
                }
            }).start();

            showSecondPage();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Niepoprawna temperatura");
        }
    }

    private void showSecondPage() {
        mainPane.removeAll();

        topDescription = new JLabel();
        row = new JPanel();
        mainPane.add(topDescription);
        yesButton = new JButton("TAK");
        row.add(yesButton);
        noButton = new JButton("NIE");
        row.add(noButton);
        mainPane.add(row);

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
                answer = YES;
                Main.getInstance().wakeUp();
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                answer = NO;
                Main.getInstance().wakeUp();
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(quitButton);

        mainPane.add(bottomPanel);
        mainPane.repaint();
    }

    private void start() {
        Query q1 = new Query("consult", new Term[]{new Atom("program.pl")});
        q1.hasSolution();
    }

    public synchronized void wakeUp() {
        notifyAll();
    }

    public synchronized void waitForIt() throws InterruptedException {
        wait();
    }

}
