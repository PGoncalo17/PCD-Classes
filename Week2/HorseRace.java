import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class Horse extends Thread{
    private JTextField jtf = new JTextField();

    public Horse(JTextField jtf){
        this.jtf = jtf;
    }

    @Override 
    public void run(){
        try{
            for(int i = 0; i <= 30; i ++){
                int dist = 30 - i;
                jtf.setText("Movements to finish the race: " + dist);
                long n = (long)(Math.random() * 1000);
                Thread.sleep(n);
            }
            jtf.setText("End of the race");
        }catch(InterruptedException e){}
    }
}


public class HorseRace extends JFrame{
    private JTextField jtf1 = new JTextField("30");
    private JTextField jtf2 = new JTextField("30");
    private JTextField jtf3 = new JTextField("30");
    private JButton btnStart = new JButton("Start the Race");

    public HorseRace(){
        super("Horse Race");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 5, 5));

        add(jtf1);
        add(jtf2);
        add(jtf3);
        add(btnStart);

        jtf1.setEditable(false);
        jtf2.setEditable(false);
        jtf3.setEditable(false);

        btnStart.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e){
                Horse h1 = new Horse(jtf1);
                Horse h2 = new Horse(jtf2);
                Horse h3 = new Horse(jtf3);

                h1.start();
                h2.start();
                h3.start();
            }
        });
        pack();
        setLocationRelativeTo(null);
    }
    public static void main(String[] args){
        HorseRace window = new HorseRace();
        window.setVisible(true);
    }

}