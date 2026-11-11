package iscot.client;

import iscot.client.gui.SensorControl;
import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.GroundMoistureSensorEvent;
import iscot.events.RegistrationEvent;
import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;

public class GroundMoisterSensor {

	/**
	 * 
	 * @param server address and port
	 * @param roomName: string
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws NumberFormatException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws NumberFormatException, InterruptedException {
		if(args.length!=3) {
			System.err.println("Invalid arguments!");
		}
		else {
			// register with the server
			Areas area = Areas.valueOf(args[2]);
			RegistrationEvent regEvent = new RegistrationEvent(area, Type.SENSOR);
			System.out.println("Cliente registado no sistema: " + regEvent);
			

			JFrame janela = new JFrame("Humidade em " + area);
			janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			
			// Criar os componentes gráficos
			JTextArea log = new JTextArea();
			log.setEditable(false);
			JScrollPane logPane = new JScrollPane(log);
			logPane.setPreferredSize(new Dimension(400, 100));

			JPanel painel=new JPanel();
			painel.setLayout(new BorderLayout());
			SensorControl sensorControl= new SensorControl( "☀", "Humidade Solo- " + area, "%",
				    0, 100, 1, 35,
				    valor -> {
				    	// TODO handle change in sensor
						GroundMoistureSensorEvent event = new GroundMoistureSensorEvent(area, valor);
						System.out.println("Evento gerado pelo sensor: " + event);
				    	System.out.println("Mudou para:"+valor);
						log.append("Evento gerado pelo sensor: " + event + "\n");
				    });
			painel.add(sensorControl,BorderLayout.CENTER);
			janela.setContentPane(painel);
			janela.setSize(600,400);
			janela.setLocation(600, 400);
			janela.setMinimumSize(janela.getSize());
			janela.pack();
			janela.setVisible(true);
			painel.add(logPane, BorderLayout.SOUTH);
			
		}
	}

}
