package iscot.client;

import iscot.client.gui.SensorControl;
import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.HumiditySensorEvent;
import iscot.events.RegistrationEvent;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HumiditySensor {

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
			

			JFrame janela = new JFrame("Humidade em "+area);
			janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			
			// Criar os componentes gráficos
			JPanel painel=new JPanel();
			painel.setLayout(new BorderLayout());
			SensorControl sensorControl= new SensorControl( "☀", "Humidade - " + area, "%",
				    0, 100, 1, 50,
				    valor -> {
				    	// handle change in sensor
						HumiditySensorEvent event = new HumiditySensorEvent(area, valor);
						System.out.println("Evento gerado pelo sensor: " + event);
				    	System.out.println("Mudou para:"+valor);
				    });
			painel.add(sensorControl,BorderLayout.CENTER);
			janela.setContentPane(painel);
			janela.setLocation(600, 400);
			janela.setMinimumSize(janela.getSize());
			janela.pack();
			janela.setVisible(true);
			
		}
	}

}
