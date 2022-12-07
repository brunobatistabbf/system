package processSystem;

import java.util.*;
import java.util.ArrayList;

public class remaining {
    public static void main(String[] args) {
        String processName;
        int processArrivalTime, processCPUBurstTime, processBlockingProbability, numberOfProcess, currentTime = 0,
                executionTime = 1, randomNumber;
        ArrayList<processDetails> processList = new ArrayList<processDetails>(); // lista de entradas
        ArrayList<processDetails> newQueue; // Processo é criado
        ArrayList<processDetails> readyQueue = new ArrayList<processDetails>(); // processo esá pronto
        ArrayList<processDetails> resourceQueue = new ArrayList<processDetails>(); // Quando o processo está aguardando
        processDetails currentProcess;
        Random randNum = new Random(); // gerador de numeros

        Scanner scanProcessDetails = new Scanner(System.in); // input
        System.out.println("Entre com o numero de processos: ");
        numberOfProcess = scanProcessDetails.nextInt(); // numero de processos

        System.out.println(
                "Entre com o nome do processo, start time, numero de segundos requeridos para completar o processo e blocking probability: ");
        for (int processDetailsCounter = 0; processDetailsCounter < numberOfProcess; processDetailsCounter++) {
            System.out.println("Nome do processo: ");
            processName = scanProcessDetails.next();
            System.out.println("Start time:");
            processArrivalTime = scanProcessDetails.nextInt();
            System.out.println("Numero de Segundos:");
            processCPUBurstTime = scanProcessDetails.nextInt();
            System.out.println("Blocking probability:");
            processBlockingProbability = scanProcessDetails.nextInt();
            processList.add(new processDetails(processName, processArrivalTime, processCPUBurstTime,
                    processBlockingProbability));
        }

        scanProcessDetails.close(); // fechando o scann
        // executando todos os processos que o usuario de entrada
        while (!processList.isEmpty()) {
            System.out.println("Time Now " + currentTime);
            newQueue = processDetails.findAll(processList, currentTime);
            readyQueue = processDetails.sendFromState(newQueue, readyQueue, resourceQueue);
            if (!resourceQueue.isEmpty())
                processDetails.threeSecondTimer(resourceQueue, readyQueue);
            processDetails.checkProcessesWaiting(resourceQueue);
            if (processDetails.checkProcessesReady(readyQueue)) {
                currentProcess = processDetails.implementPreemptiveSJFAlgo(readyQueue);
                System.out.println("Processo em execução " + currentProcess.getProcessName() + " por " + executionTime
                        + " seconds " + " tempo restante:" + currentProcess.getProcessCPUString());
                currentProcess.setProcessCPUVurstTime(currentProcess.getProcessCPUString() - executionTime);
                randomNumber = (randNum.nextInt(100 - 1) + 1);
                System.out.println(
                        "Processo " + currentProcess.getProcessName() + " numero aleatorio gerado " + randomNumber);
                if (currentProcess.getProcessCPUString() == 0) {
                    System.out.println("Processo " + currentProcess.getProcessName() + " Terminado");
                    processList.remove(currentProcess);
                    readyQueue.remove(currentProcess);
                } else if (randomNumber <= currentProcess.getBlockingProbability()) {
                    resourceQueue.add(currentProcess);
                    readyQueue.remove(currentProcess);
                }
            }
            currentTime += executionTime;
            System.out.println();
        }
        System.out.printf("Now Time: " + currentTime + ". Nenhum processo para rodar. ");
    }
}
