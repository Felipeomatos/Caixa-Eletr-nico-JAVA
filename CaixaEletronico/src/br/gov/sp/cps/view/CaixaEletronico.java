package br.gov.sp.cps.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import br.gov.sp.cps.controllers.CaixaController;
import br.gov.sp.cps.model.Conta;

public class CaixaEletronico extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color AZUL_ESCURO = new Color(30, 36, 50);
    private static final Color LARANJA = new Color(232, 119, 46);
    private static final Color VERDE = new Color(46, 125, 80);
    private static final Color AZUL = new Color(61, 143, 168);
    private static final Color FUNDO = new Color(242, 244, 248);

    private JLabel lblTitulo;
    private JLabel lblTextoSaldo;
    private JLabel lblSaldo;
    private JLabel lblValor;

    private JTextField txtValor;

    private JButton btnDepositar;
    private JButton btnSacar;
    private JButton btnExtrato;

    private JTextArea txtExtrato;
    private JScrollPane scrollExtrato;

    private Conta conta;

    public CaixaEletronico() {

        conta = new Conta();

        setTitle("Banco ETEC - Caixa Eletrônico");
        setSize(420, 520);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FUNDO);

        lblTitulo = new JLabel("BANCO ETEC  ·  CAIXA ELETRÔNICO", SwingConstants.CENTER);
        lblTitulo.setBounds(20, 20, 365, 40);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(AZUL_ESCURO);
        add(lblTitulo);

        lblTextoSaldo = new JLabel("SALDO DISPONÍVEL");
        lblTextoSaldo.setBounds(35, 75, 200, 20);
        lblTextoSaldo.setFont(new Font("Arial", Font.BOLD, 11));
        lblTextoSaldo.setForeground(AZUL_ESCURO);
        add(lblTextoSaldo);

        lblSaldo = new JLabel(Conta.formatar(conta.getSaldo()));
        lblSaldo.setBounds(35, 95, 340, 40);
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 28));
        lblSaldo.setForeground(LARANJA);
        add(lblSaldo);

        lblValor = new JLabel("Valor da operação");
        lblValor.setBounds(20, 150, 200, 20);
        add(lblValor);

        txtValor = new JTextField();
        txtValor.setBounds(20, 172, 365, 38);
        txtValor.setFont(new Font("Arial", Font.PLAIN, 15));
        add(txtValor);

        btnDepositar = criarBotao("DEPOSITAR", VERDE, 20);
        add(btnDepositar);

        btnSacar = criarBotao("SACAR", LARANJA, 148);
        add(btnSacar);

        btnExtrato = criarBotao("EXTRATO", AZUL, 276);
        add(btnExtrato);

        txtExtrato = new JTextArea();
        txtExtrato.setEditable(false);
        txtExtrato.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        txtExtrato.setMargin(new java.awt.Insets(8, 10, 8, 10));
        txtExtrato.setText(conta.getExtratoTexto());

        scrollExtrato = new JScrollPane(txtExtrato);
        scrollExtrato.setBounds(20, 285, 365, 185);
        add(scrollExtrato);

        CaixaController controller = new CaixaController(
                conta, txtValor, lblSaldo, txtExtrato, this);

        btnDepositar.addActionListener(controller.acaoDepositar());
        btnSacar.addActionListener(controller.acaoSacar());
        btnExtrato.addActionListener(controller.acaoExtrato());
    }

    private JButton criarBotao(String texto, Color cor, int x) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, 225, 109, 42);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setForeground(Color.WHITE);
        botao.setBackground(cor);
        botao.setOpaque(true);
        botao.setBorder(BorderFactory.createEmptyBorder());
        botao.setFocusPainted(false);
        return botao;
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
        }

        CaixaEletronico tela = new CaixaEletronico();
        tela.setVisible(true);
    }
}