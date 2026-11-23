package iscot.client.actuators;

import iscot.client.gui.ACIcon;
import iscot.enums.AirConditioningMode;
import iscot.enums.Areas;
import iscot.events.AreaTemperatureOrderEvent;
import iscot.events.HighLevelOrderEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class AirConditioning extends ActuatorClient {
	private static final JTextArea log = new JTextArea();
	private static final ACIcon acIcon = new ACIcon();

	public AirConditioning(String ip, int porto, Areas area) {
		super(ip, porto, area);
	}


	@Override
	public boolean processOrderEvent(HighLevelOrderEvent order) {
		if (order instanceof AreaTemperatureOrderEvent areaTemperatureOrderEvent) {
			AreaTemperatureOrderEvent acs = areaTemperatureOrderEvent;
			if(acs.getMode() == AirConditioningMode.Aquecimento){
				AreaTemperatureOrderEvent ordemAquecimento = new AreaTemperatureOrderEvent(getArea(), AirConditioningMode.Aquecimento);
				System.out.println("Evento de ordem recebido: " + ordemAquecimento);
				log.append("Evento de ordem recebido: " + ordemAquecimento + "\n");
				acIcon.setModo(ordemAquecimento.getMode());
			} else if (acs.getMode() == AirConditioningMode.Frio) {
				AreaTemperatureOrderEvent ordemFrio = new AreaTemperatureOrderEvent(getArea(), AirConditioningMode.Frio);
				System.out.println("Evento de ordem recebido: " + ordemFrio);
				log.append("Evento de ordem recebido: " + ordemFrio + "\n");
				acIcon.setModo(ordemFrio.getMode());
			} else if (acs.getMode() == AirConditioningMode.Ventilacao) {
				AreaTemperatureOrderEvent ordemVentilacao = new AreaTemperatureOrderEvent(getArea(), AirConditioningMode.Ventilacao);
				System.out.println("Evento de ordem recebido: " + ordemVentilacao);
				log.append("Evento de ordem recebido: " + ordemVentilacao + "\n");
				acIcon.setModo(ordemVentilacao.getMode());
			} else {
				AreaTemperatureOrderEvent ordemDesligar = new AreaTemperatureOrderEvent(getArea(), AirConditioningMode.Desligado);
				System.out.println("Evento de ordem recebido: " + ordemDesligar);
				log.append("Evento de ordem recebido: " + ordemDesligar + "\n");
				acIcon.setModo(ordemDesligar.getMode());
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

		AirConditioning ac = new AirConditioning(ip, porto, area);
	
		JFrame janela = new JFrame("Ventilador em "+area);
		janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		janela.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    ac.closeConnection();
                }
            });

		JPanel painel=new JPanel();
		painel.setLayout(new BorderLayout());
			
		log.setEditable(false);
		JScrollPane logPane = new JScrollPane(log);
		logPane.setPreferredSize(new Dimension(400, 100));
		painel.add(logPane, BorderLayout.SOUTH);

		painel.add(acIcon, BorderLayout.CENTER);
		janela.setContentPane(painel);
		janela.setLocation(600, 400);
		janela.setMinimumSize(janela.getSize());
		janela.pack();
		janela.setVisible(true);
			
		acIcon.setAtivo(true);

		ac.runClient();
	}
}
