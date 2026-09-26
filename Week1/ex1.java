import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Dimension;

public class ex1 {
	private JFrame frame;

	public ex1() {
		frame = new JFrame("Test");
		
		// para que o botao de fechar a janela termine a aplicacao
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		addFrameContent();
		
		// para que a janela se redimensione de forma a ter todo o seu conteudo visivel
		frame.pack();
	}

	public void open() {
		// para abrir a janela (torna-la visivel)
		frame.setVisible(true);
	}

	private void addFrameContent() {
		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */
		frame.setLayout(new GridLayout(4,2));
		JLabel label = new JLabel("title");
		frame.add(label);
		JTextField text = new JTextField("Hello");
		frame.add(text);
		JLabel width = new JLabel("width");
		frame.add(width);
		JTextField widthText = new JTextField("300");
		frame.add(widthText);
		JLabel height = new JLabel("height");
		frame.add(height);
		JTextField heightText = new JTextField("150");
		frame.add(heightText);
		JButton button = new JButton("update");
		frame.add(button);
		JCheckBox check = new JCheckBox("center");
		frame.add(check);
		 button.addActionListener(new ActionListener() {
		 	@Override
		 	public void actionPerformed(ActionEvent e) {
				int w = Integer.parseInt(widthText.getText());
				int h = Integer.parseInt(heightText.getText());
				frame.setSize(w, h);
				frame.setTitle(text.getText());
				
				if(check.isSelected()){ 
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					int y = (((int) dimension.getHeight() - h))/2;
					int x = (((int) dimension.getWidth() - w))/2;
					frame.setLocation(x, y);
		 		}
			}});
		 	frame.add(button);
	}

	public static void main(String[] args) {
		ex1 window = new ex1();
		window.open();
	}
}
