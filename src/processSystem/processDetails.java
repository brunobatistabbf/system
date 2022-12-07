package processSystem;

import java.util.ArrayList;

public class processDetails {
    String processName;
    int processArrivalTime;
    int processCPUBurstTime;
    int blockingProbability;
    int counter;

    public processDetails(String name, int arrivalTime, int CPUBurstTime, int blockingProbability) {
        this.processName = name;
        this.processArrivalTime = arrivalTime;
        this.processCPUBurstTime = CPUBurstTime;
        this.blockingProbability = blockingProbability;
        this.counter = 0; // contador em 0s
    }

    // getters and setters
    public String getProcessName() {
        return this.processName;
    }

    public int getProcessArrivalTime() {
        return this.processArrivalTime;
    }

    public int getProcessCPUString() {
        return this.processCPUBurstTime;
    }

    public int getBlockingProbability() {
        return this.blockingProbability;
    }

    public void setProcessCPUVurstTime(int CPUBurstTime) {
        this.processCPUBurstTime = CPUBurstTime;
    }

    // processo de filtro com time de chegada recebendo time atual
    public static ArrayList<processDetails> findAll(ArrayList<processDetails> processList, int currentTime) {
        ArrayList<processDetails> newQueue = new ArrayList<processDetails>();
        for (processDetails process : processList) {
            if (process.getProcessArrivalTime() <= currentTime) {
                newQueue.add(process);
            }

        }
        return newQueue;
    }

    // Verifica a fila de recursos a cada 3 segundos
    public static ArrayList<processDetails> threeSecondTimer(ArrayList<processDetails> resourceQueue,
            ArrayList<processDetails> readyQueue) {
        ArrayList<processDetails> tempResourceQueue = new ArrayList<processDetails>();
        for (processDetails process : resourceQueue) {
            if (process.counter == 3) {
                System.out.println("processo" + process.getProcessName() + "Time Counter = " + process.counter);
                process.counter = 0;
                readyQueue.add(process);
                tempResourceQueue.add(process);
            }
        }
        for (processDetails process : tempResourceQueue) {
            resourceQueue.remove(process);
        }
        for (processDetails process : resourceQueue) {
            process.counter++;
        }
        return readyQueue;
    }

    public static void chackProcessesWaiting(ArrayList<processDetails> resourceQueue) {
        if (!resourceQueue.isEmpty()) {
            for (processDetails process : resourceQueue) {// processo de impressão em espera na fila de recursos
                System.out.println("Processo " + process.getProcessName() + " esta esperando o estado ");
            }
        } else {
            System.out.println("Sem processos no estado pronto");
        }
    }

    public static boolean checkProcessesReady(ArrayList<processDetails> readyQueue) {
        if (!readyQueue.isEmpty()) { // printa o processo na fila de pontos
            for (processDetails process : readyQueue) {
                System.out.println("Processo " + process.getProcessName() + " está pronto");
                return true;
            }
        } else {
            System.out.println("Nenhum processo no estado pronto");
            return false;
        }
        return false;

    }

    // seleciona o processo da fila pronto
    public static processDetails implementPreemptiveSJFAlgo(ArrayList<processDetails> readyQueue) {
        int leastCPUBurst = readyQueue.get(0).getProcessCPUString();
        int leastArrivalTime = readyQueue.get(0).getProcessArrivalTime();
        processDetails selectedProcess = null;
        for (processDetails process : readyQueue) {
            if (process.getProcessCPUString() == leastCPUBurst && process.getProcessArrivalTime() <= leastArrivalTime) {
                selectedProcess = process;
            } else if (process.getProcessCPUString() < leastCPUBurst) {
                leastArrivalTime = process.getProcessArrivalTime();
                leastCPUBurst = process.getProcessCPUString();
                selectedProcess = process;
            }
        }

        return selectedProcess;

    }
}
