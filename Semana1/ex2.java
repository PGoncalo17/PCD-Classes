import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;

public class ex2 {
    private JFrame frame;
    private int currentImage = 0;
   

    public ex2(String path) {
        frame = new JFrame("Ex2");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addFrameContent(path);
        frame.pack();
    }

    public void open() {
        frame.setVisible(true);
    }

    public void addFrameContent(String path) {
        JButton button1 = new JButton("<");
        frame.add(button1, BorderLayout.WEST);
        JButton button2 = new JButton(">");
        frame.add(button2, BorderLayout.EAST);
        JLabel title = new JLabel("Images");
        frame.add(title, BorderLayout.NORTH);
        JButton update = new JButton("Update");
        frame.add(update, BorderLayout.SOUTH);
        JLabel image = new JLabel("");
        frame.add(image, BorderLayout.CENTER);
        
        
        File files[] = new File(path).listFiles(new FileFilter() {
            public boolean accept(File f){
                if(f.isFile() && f.getName().toLowerCase().endsWith(".jpg"))
                    return true;
                else
                    return false;
            }
        });

        if(files != null && files.length > 0){
            ImageIcon icon = new ImageIcon(files[currentImage].getAbsolutePath());
            image.setIcon(icon);
            image.setText("");
            title.setText(files[currentImage].getName());
        }

        button1.addActionListener(new ActionListener(){
        @Override 
        public void actionPerformed(ActionEvent e){
            if(currentImage > 0){
                image.setText("");
                currentImage--;
                title.setText(files[currentImage].getName());
                ImageIcon icon = new ImageIcon(files[currentImage].getAbsolutePath());
                image.setIcon(icon);
            } else{
                currentImage = -1;
                title.setText(":(");
                image.setIcon(null);
                image.setText("Reached end of fotos :(");
            }
        }});
            

        button2.addActionListener(new ActionListener(){
        @Override 
        public void actionPerformed(ActionEvent e){
            if(currentImage < files.length - 1){
                image.setText("");
                currentImage++;
                ImageIcon icon = new ImageIcon(files[currentImage].getAbsolutePath());
                image.setIcon(icon);
                title.setText(files[currentImage].getName());
            }
            else{
                currentImage = 3;
                image.setIcon(null);
                image.setText("Reached end of fotos :(");
                title.setText(":(");

            }
            }});
        };


    public static void main(String[] args) {
        if(args.length > 0){ 
            ex2 window = new ex2(args[0]);
            window.open();
        }
    }
}