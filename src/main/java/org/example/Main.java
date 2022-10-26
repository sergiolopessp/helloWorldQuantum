package org.example;

import org.redfx.strange.*;
import org.redfx.strange.gate.Hadamard;
import org.redfx.strange.local.SimpleQuantumExecutionEnvironment;
import org.redfx.strangefx.render.Renderer;


public class Main {
    public static void main(String[] args) {
        int[] resultado = new int[4];

        Program program = new Program(2);
        Step step1 = new Step();
        step1.addGate(new Hadamard(0));
        step1.addGate(new Hadamard(1));

        program.addStep(step1);

        QuantumExecutionEnvironment simulator = new SimpleQuantumExecutionEnvironment();

        for (int i = 0; i < 1000; i++) {
            Result result = simulator.runProgram(program);
            Qubit[] qubits = result.getQubits();
            Qubit zero = qubits[0];
            Qubit one = qubits[1];

            boolean moedaA = zero.measure() == 1;
            boolean moedaB = one.measure() == 1;

            if (!moedaA && !moedaB) {
                resultado[0]++;
            }

            if (!moedaA && moedaB) {
                resultado[1]++;
            }

            if (moedaA && !moedaB) {
                resultado[2]++;
            }

            if (moedaA && moedaB) {
                resultado[3]++;
            }
        }

        System.out.println("-----------------------------------");
        System.out.println("Resultado do Experimento:");
        System.out.println("Cara/Cara : " + resultado[0]);
        System.out.println("Cara/Coroa : " + resultado[1]);
        System.out.println("Coroa/Cara : " + resultado[2]);
        System.out.println("Coroa/Coroa : " + resultado[3]);
        System.out.println("-----------------------------------");

        Renderer.renderProgram(program);
        Renderer.showProbabilities(program, 1000);

    }
}