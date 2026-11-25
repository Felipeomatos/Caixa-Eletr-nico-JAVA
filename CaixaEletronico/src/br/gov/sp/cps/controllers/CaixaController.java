package br.gov.sp.cps.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.gov.sp.cps.model.Conta;

public class CaixaController {

    private Conta conta;
    private JTextField txtValor;
    private JLabel lblSaldo;
    private JTextArea txtExtrato;
    private JFrame tela;

    public CaixaController(Conta conta, JTextField txtValor, JLabel lblSaldo,
            JTextArea txtExtrato, JFrame tela) {
        this.conta = conta;
        this.txtValor = txtValor;
        this.lblSaldo = lblSaldo;
        this.txtExtrato = txtExtrato;
        this.tela = tela;
    }

    public ActionListener acaoDepositar() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    double valor = lerValor();

                    if (valor <= 0) {
                        JOptionPane.showMessageDialog(tela,
                                "O valor deve ser maior que zero!",
                                "Depósito recusado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    conta.depositar(valor);

                    atualizarTela();

                    JOptionPane.showMessageDialog(tela,
                            "Depósito de " + Conta.formatar(valor) + " realizado com sucesso!",
                            "Depósito", JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException erro) {
                    mensagemValorInvalido();
                }
            }
        };
    }

    public ActionListener acaoSacar() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    double valor = lerValor();

                    if (valor <= 0) {
                        JOptionPane.showMessageDialog(tela,
                                "O valor deve ser maior que zero!",
                                "Saque recusado", JOptionPane.WARNING_MESSAGE);

                    } else if (valor > conta.getSaldo()) {
                        JOptionPane.showMessageDialog(tela,
                                "Saldo insuficiente!\nSaldo disponível: "
                                        + Conta.formatar(conta.getSaldo()),
                                "Saque recusado", JOptionPane.WARNING_MESSAGE);

                    } else if (valor > Conta.LIMITE_SAQUE) {
                        JOptionPane.showMessageDialog(tela,
                                "O saque máximo por operação é de "
                                        + Conta.formatar(Conta.LIMITE_SAQUE) + "!",
                                "Saque recusado", JOptionPane.WARNING_MESSAGE);

                    } else if (valor % Conta.MULTIPLO_SAQUE != 0) {
                        JOptionPane.showMessageDialog(tela,
                                "O valor do saque deve ser múltiplo de 10!\n"
                                        + "O caixa só tem notas de R$ 100, R$ 50 e R$ 10.",
                                "Saque recusado", JOptionPane.WARNING_MESSAGE);

                    } else {
                        int[] notas = conta.sacar(valor);

                        atualizarTela();

                        JOptionPane.showMessageDialog(tela,
                                "Saque de " + Conta.formatar(valor) + " realizado!\n\n"
                                        + "Notas entregues:\n" + conta.descreverNotas(notas),
                                "Saque", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (NumberFormatException erro) {
                    mensagemValorInvalido();
                }
            }
        };
    }

    public ActionListener acaoExtrato() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarTela();
            }
        };
    }

    private double lerValor() {
        String texto = txtValor.getText().trim().replace(",", ".");

        double valor = Double.parseDouble(texto);

        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            throw new NumberFormatException("valor inválido");
        }

        return Math.round(valor * 100) / 100.0;
    }

    private void mensagemValorInvalido() {
        JOptionPane.showMessageDialog(tela,
                "Digite um valor numérico válido! (ex.: 200 ou 150,50)",
                "Valor inválido", JOptionPane.ERROR_MESSAGE);
        txtValor.requestFocusInWindow();
    }

    private void atualizarTela() {
        lblSaldo.setText(Conta.formatar(conta.getSaldo()));
        txtExtrato.setText(conta.getExtratoTexto());
        txtExtrato.setCaretPosition(txtExtrato.getDocument().getLength());
        txtValor.setText("");
        txtValor.requestFocusInWindow();
    }
}