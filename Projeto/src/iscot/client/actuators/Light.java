package iscot.client.actuators;

import iscot.client.gui.LightIcon;
import iscot.enums.Areas;
import iscot.events.AreaLightingOrderEvent;
import iscot.events.HighLevelOrderEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Light extends ActuatorClient {
	private static final JTextArea log = new JTextArea();
	private static final LightIcon lightIcon = new LightIcon();

	public Light(String ip, int porto, Areas area) {
		super(ip, porto, area);
	}

	@Override 
	public boolean processOrderEvent(HighLevelOrderEvent order) {
		if (order instanceof AreaLightingOrderEvent areaLightingOrderEvent) {
			AreaLightingOrderEvent lights = areaLightingOrderEvent;
			if (lights.isActive()) {
				AreaLightingOrderEvent ordemLigar = new AreaLightingOrderEvent(getArea(), true);
				System.out.println("Evento de ordem recebido: " + ordemLigar);
				log.append("Evento de ordem recebido: " + ordemLigar + "\n");
				lightIcon.setAtivo(ordemLigar.isActive());
			} else {
				AreaLightingOrderEvent ordemDesligar = new AreaLightingOrderEvent(getArea(), false);
				System.out.println("Evento de ordem recebido: " + ordemDesligar);
				log.append("Evento de ordem recebido: " + ordemDesligar + "\n");
				lightIcon.setAtivo(ordemDesligar.isActive());
			}
			return true;
		}
		return false;
	}


	public static void main(String[] args) throws NumberFormatException {
		if(args.length!=3) {
			System.err.println("Invalid arguments!");
			return;
		}	
		
		String ip = args[0];
		int porto = Integer.parseInt(args[1]);
		Areas area = Areas.valueOf(args[2].toUpperCase());

		Light light = new Light(ip, porto, area);

		
		JFrame janela = new JFrame("Luz em " + area);
		janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		janela.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    light.closeConnection();
                }
            });
		
		JPanel painel=new JPanel();
		painel.setLayout(new BorderLayout());
		
		log.setEditable(false);
		JScrollPane logPane = new JScrollPane(log);
		logPane.setPreferredSize(new Dimension(400, 100));
		painel.add(logPane, BorderLayout.SOUTH);
		
		painel.add(lightIcon,BorderLayout.CENTER);
		janela.setContentPane(painel);
		janela.setLocation(600, 400);
		janela.setMinimumSize(janela.getSize());
		janela.pack();
		janela.setVisible(true);
		
		light.runClient();

	}
}
