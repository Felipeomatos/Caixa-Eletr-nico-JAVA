package br.gov.sp.cps.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Conta {

    public static final double LIMITE_SAQUE = 1000.00;

    public static final int MULTIPLO_SAQUE = 10;

    public static final int NOTA_100 = 100;
    public static final int NOTA_50 = 50;
    public static final int NOTA_10 = 10;

    private double saldo = 1000.00;

    private final List<String> extrato = new ArrayList<String>();

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        saldo = saldo + valor;
        extrato.add("Depósito de " + formatar(valor));
    }

    public int[] sacar(double valor) {
        saldo = saldo - valor;

        int[] notas = calcularNotas((int) valor);

        extrato.add("Saque de " + formatar(valor) + "\n  " + descreverNotas(notas));
        return notas;
    }

    public int[] calcularNotas(int valor) {
        int notas100 = valor / NOTA_100;
        int resto = valor % NOTA_100;

        int notas50 = resto / NOTA_50;
        resto = resto % NOTA_50;

        int notas10 = resto / NOTA_10;

        return new int[] { notas100, notas50, notas10 };
    }

    public String descreverNotas(int[] notas) {
        StringBuilder sb = new StringBuilder();
        int[] valores = { NOTA_100, NOTA_50, NOTA_10 };

        for (int i = 0; i < notas.length; i++) {
            if (notas[i] > 0) {
                if (sb.length() > 0) {
                    sb.append("   ");
                }
                sb.append(notas[i]).append("x R$ ").append(valores[i]);
            }
        }
        return sb.toString();
    }

    public String getExtratoTexto() {
        if (extrato.isEmpty()) {
            return "Nenhuma operação realizada.";
        }

        StringBuilder sb = new StringBuilder();
        for (String linha : extrato) {
            sb.append(linha).append("\n");
        }
        return sb.toString();
    }

    public static String formatar(double valor) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(Locale.forLanguageTag("pt-BR"));
        DecimalFormat formato = new DecimalFormat("#,##0.00", simbolos);
        return "R$ " + formato.format(valor);
    }
}