
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class GUI extends JFrame{
    private Balanca balanca;
    private JFrame frame;
    private JTextField jtf;
    private JButton stop;
    private Ourives ourives;
    private Escavadora escavadora;

    public GUI() {
        super("Gold Simulator");

        setLayout(new FlowLayout());
        jtf = new JTextField("0.0 Kg", 10);
        jtf.setEditable(false);
        stop = new JButton("Stop");
        add(jtf);
        add(stop);

        balanca = new Balanca(jtf);
        escavadora = new Escavadora(balanca);
        ourives = new Ourives(balanca);

        stop.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e){
                escavadora.interrupt();
                ourives.interrupt();

                try {
                    System.out.println(Thread.currentThread() + " - antes de fazer join escavadora");
                    escavadora.join();
                    System.out.println(Thread.currentThread() + " - antes de fazer join ourives");
                    ourives.join();
                } catch (Exception t) { System.out.println(Thread.currentThread().toString() + " - antes de tratar InterruptedException");}
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        escavadora.start();
        ourives.start();
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override 
            public void run(){
                new GUI().setVisible(true);
            }
        });
    }
}