package iscot.client.actuators;

import iscot.client.gui.IrrigationIcon;
import iscot.enums.Areas;
import iscot.events.AreaIrrigationOrderEvent;
import iscot.events.HighLevelOrderEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Irrigation extends ActuatorClient {
	private static final JTextArea log = new JTextArea();
	private static final IrrigationIcon irrigator = new IrrigationIcon();

	public Irrigation(String ip, int porto, Areas area) {
		super(ip, porto, area);
	}


	@Override 
	public void processOrderEvent(HighLevelOrderEvent order) {
		if (order instanceof AreaIrrigationOrderEvent areaIrrigationOrderEvent) {
			AreaIrrigationOrderEvent irrigations = areaIrrigationOrderEvent;
			if (irrigations.isActive()) {
				AreaIrrigationOrderEvent ordemLigar = new AreaIrrigationOrderEvent(getArea(), true);
				System.out.println("Evento de ordem recebido: " + ordemLigar);
				log.append("Evento de ordem recebido: " + ordemLigar + "\n");
				irrigator.setAtivo(ordemLigar.isActive());
			} else {
				AreaIrrigationOrderEvent ordemDesligar = new AreaIrrigationOrderEvent(getArea(), false);
				System.out.println("Evento de ordem recebido: " + ordemDesligar);
				log.append("Evento de ordem recebido: " + ordemDesligar + "\n");
				irrigator.setAtivo(ordemDesligar.isActive());
			}
		}
	}


	public static void main(String[] args) throws NumberFormatException {
		if(args.length!=3) {
			System.err.println("Invalid arguments!");
			return;
		}		

		String ip = args[0];
		int porto = Integer.parseInt(args[1]);
		Areas area = Areas.valueOf(args[2].toUpperCase());

		Irrigation irrigation = new Irrigation(ip, porto, area);
			
		JFrame janela = new JFrame("Ventilador em "+area);
		janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		janela.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    irrigation.closeConnection();
                }
            });

		JPanel painel=new JPanel();
		painel.setLayout(new BorderLayout());

		log.setEditable(false);
		JScrollPane logPane = new JScrollPane(log);
		logPane.setPreferredSize(new Dimension(400, 100));
		painel.add(logPane, BorderLayout.SOUTH);

		painel.add(irrigator,BorderLayout.CENTER);
		janela.setContentPane(painel);
		janela.setLocation(600, 400);
		janela.setMinimumSize(janela.getSize());
		janela.pack();
		janela.setVisible(true);
			
		irrigation.runClient();
	}
}
