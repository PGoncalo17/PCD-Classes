package iscot.client;

import iscot.client.gui.VentilatorIcon;
import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.AreaVentilationOrderEvent;
import iscot.events.RegistrationEvent;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;

public class Ventilator {

	/**
	 * 
	 * @param server address and port
	 * @param roomName: string
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws NumberFormatException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException, InterruptedException, ClassNotFoundException {
		if(args.length!=3) {
			System.err.println("Invalid arguments!");
		}
		else {			
			// register with the server
			Areas area=Areas.valueOf(args[2]);
			RegistrationEvent regEvent = new RegistrationEvent(area, Type.ACTUATOR);
			System.out.println("Atuador de Ventilação registado no sistema: " + regEvent);
			
			JFrame janela = new JFrame("Ventilador em "+area);
			janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
			
			// Criar os componentes gráficos
			JPanel painel=new JPanel();
			painel.setLayout(new BorderLayout());
			VentilatorIcon ventilator = new VentilatorIcon();

			JTextArea log = new JTextArea();
			log.setEditable(false);
			JScrollPane logPane = new JScrollPane(log);
			logPane.setPreferredSize(new Dimension(400, 100));
			painel.add(logPane, BorderLayout.SOUTH);

			painel.add(ventilator,BorderLayout.CENTER);
			janela.setContentPane(painel);
			janela.setLocation(600, 400);
			janela.setMinimumSize(janela.getSize());
			janela.pack();
			janela.setVisible(true);
			
			Thread.sleep(5000);
			ventilator.setAtivo(true);

			// TODO: receive and handle messages from server
			
			// appropriate event hardcoded locally and change the GUI accordingly
			Thread.sleep(3000);
			AreaVentilationOrderEvent ordemLigar = new AreaVentilationOrderEvent(area, true);
			System.out.println("Evento de ordem recebido: " + ordemLigar);
			log.append("Evento de ordem recebido: " + ordemLigar + "\n");
			ventilator.setAtivo(ordemLigar.isActive());
			
			Thread.sleep(4000);
			AreaVentilationOrderEvent ordemDesligar = new AreaVentilationOrderEvent(area, false);
			System.out.println("Evento de ordem recebido: " + ordemDesligar);
			log.append("Evento de ordem recebido: " + ordemDesligar + "\n");
			ventilator.setAtivo(ordemDesligar.isActive());

		}
	}
}
