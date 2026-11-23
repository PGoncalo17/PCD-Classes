package iscot.server;

import iscot.events.Event;
import iscot.events.HighLevelOrderEvent;
import iscot.events.ReceptionConfirmationMessage;
import java.util.ArrayList;
import java.util.List;

public class EventsChannel extends Thread {
    private final String name;
    private final EventQueue queue = new EventQueue(100);
    private final List<DealWithClients> interestedClients = new ArrayList<>();
    private ModifiedCountdownLatch currentLatch = null;
    private final int waitPeriod;

    public EventsChannel(String name, int waitPeriod) {
        this.name = name;
        this.waitPeriod = waitPeriod;
    }

    public synchronized void addInterestedClient(DealWithClients client) {
        interestedClients.add(client);
        notifyAll();
    }

    public void removeInterestedClient(DealWithClients client) {
        if (interestedClients.remove(client) && currentLatch != null) {
            currentLatch.countdown();
        }
    }

    public void publishEvent(Event event) throws InterruptedException {
        queue.addEvent(event);
    }

    public synchronized void confirmExecution(ReceptionConfirmationMessage msg) {
        System.out.println("[" + name + "] Confirmação recebida do dispositivo " + msg.getDeviceId() + " | Status: " + msg.getStatus());
        if (currentLatch != null) {
            currentLatch.countdown();
            System.out.println("[" + name + "] Latch count após confirmação: " + currentLatch.getCount());
        }
    }


    @Override 
    public void run() {
        try {
            while (true) {
                Event currentEvent = queue.peekNextEvent();

                List<DealWithClients> destinatedClients;
                ModifiedCountdownLatch latch;

                synchronized (this) {
                    while (interestedClients.isEmpty()) {
                        System.out.println("[" + name + "] Evento na fila, mas sem clientes ligados. A aguardar...");
                        wait();
                    }
                    destinatedClients = new ArrayList<>(interestedClients);
                    currentLatch = new ModifiedCountdownLatch(destinatedClients.size());
                    latch = currentLatch;
                }

                boolean isHighLevelOrder = currentEvent instanceof HighLevelOrderEvent;

                System.out.println("\n[" + name + "] >>> NOVO EVENTO À CABEÇA DA FILA: " + currentEvent.getClass().getSimpleName() + " | Fila tem " + queue.size() + " evento(s).");
                System.out.println("[" + name + "] Latch criado a esperar por " + destinatedClients.size() + " cliente(s).");

                for (DealWithClients client : destinatedClients) {
                    try {
                        client.sendEvent(currentEvent);
                        if (!isHighLevelOrder) {
                            latch.countdown();
                            System.out.println("[" + name + "] -> Enviado ao Cliente ID " + client.getClientId() + " | Latch count atual: " + latch.getCount());
                        } else {
                            System.out.println("[" + name + "] -> Ordem enviada ao Atuador ID " + client.getClientId() + ". A aguardar confirmação de execução...");
                        }
                    } catch (Exception e) {
                        latch.countdown();
                        System.out.println("[" + name + "] -> Falha no Cliente ID " + client.getClientId() + " | Latch count atual: " + latch.getCount());
                    }
                }
                System.out.println("[" + name + "] A aguardar no latch.await(" + waitPeriod + "ms)...");
                latch.await(waitPeriod);
                System.out.println("[" + name + "] Latch desbloqueado! (Count final: " + latch.getCount() + ")");

                synchronized (this) {
                    currentLatch = null;
                }
                queue.removeCurrentEvent();
                System.out.println("[" + name + "] <<< Evento removido da fila. Restam " + queue.size() + " na fila.\n");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
}
