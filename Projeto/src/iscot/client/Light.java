package iscot.client;

import iscot.client.gui.LightIcon;
import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.AreaLightingOrderEvent;
import iscot.events.RegistrationEvent;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Light {

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
			LightIcon light = new LightIcon();
			painel.add(light,BorderLayout.CENTER);
			janela.setContentPane(painel);
			janela.setLocation(600, 400);
			janela.setMinimumSize(janela.getSize());
			janela.pack();
			janela.setVisible(true);
			
			Thread.sleep(5000);
			light.setAtivo(true);

			// TODO: receive and handle messages from server
			
			// appropriate event hardcoded locally and change the GUI accordingly
			Thread.sleep(3000);
			AreaLightingOrderEvent ordemLigar = new AreaLightingOrderEvent(area, true);
			System.out.println("Evento de ordem recebido: " + ordemLigar);
			light.setAtivo(ordemLigar.isActive());
			
			Thread.sleep(4000);
			AreaLightingOrderEvent ordemDesligar = new AreaLightingOrderEvent(area, false);
			System.out.println("Evento de ordem recebido: " + ordemDesligar);
			light.setAtivo(ordemDesligar.isActive());

		}
	}
}
