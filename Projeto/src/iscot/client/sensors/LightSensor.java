package iscot.client.sensors;

import iscot.client.gui.SensorControl;
import iscot.enums.Areas;
import iscot.events.LightSensorEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LightSensor extends SensorClient {

	public LightSensor(String ip, int porto, Areas area) {
		super(ip, porto, area);
	}

	public static void main(String[] args) throws NumberFormatException {
		if(args.length!=3) {
			System.err.println("Invalid arguments!");
			return;
		}
		
		String ip = args[0];
		int porto = Integer.parseInt(args[1]);
		Areas area = Areas.valueOf(args[2].toUpperCase());

		LightSensor sensor = new LightSensor(ip, porto, area);

		try {
			sensor.connectToServer();

			JFrame janela = new JFrame("Luz em " + area);
			janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			janela.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    sensor.closeConnection();
                }
            });
	
			JTextArea log = new JTextArea();
			log.setEditable(false);
			JScrollPane logPane = new JScrollPane(log);
			logPane.setPreferredSize(new Dimension(400, 100));
	
			JPanel painel=new JPanel();
			painel.setLayout(new BorderLayout());
			SensorControl sensorControl= new SensorControl( "L", "Luz - " + area, "%",
					0, 100, 1, 50,
					valor -> {
						LightSensorEvent event = new LightSensorEvent(area, valor);
						sensor.sendEvent(event);
						log.append("Evento gerado pelo sensor: " + event + "\n");
					});
			painel.add(sensorControl,BorderLayout.CENTER);
			janela.setContentPane(painel);
			janela.setSize(600,400);
			janela.setLocation(600, 400);
			janela.setMinimumSize(janela.getSize());
			painel.add(logPane, BorderLayout.SOUTH);
			janela.pack();
			janela.setVisible(true);
		
		} catch (Exception e) { 
			System.err.println("Não foi possível conectar ao servidor");
		}
	}
		
}
