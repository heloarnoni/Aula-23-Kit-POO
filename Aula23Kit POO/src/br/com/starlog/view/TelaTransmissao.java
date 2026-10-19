package br.com.starlog.view;

import br.com.starlog.controller.TransmissaoController;

import javax.swing.*;
import java.awt.*;

/**
 * CAMADA VIEW: só coleta texto, delega ao Controller e exibe o resultado.
 * Nenhuma regra de negócio aqui. O botão é um "garçom".
 */
public class TelaTransmissao extends JFrame {

    private final TransmissaoController controller = new TransmissaoController();

    private final JTextField txtRemetente = new JTextField(20);
    private final JTextField txtMensagem = new JTextField(20);
    private final JTextField txtChave = new JTextField(5);
    private final JButton btnTransmitir = new JButton("Cifrar e Transmitir");
    private final JTextArea areaLog = new JTextArea(10, 35);

    public TelaTransmissao() {
        super("STARLOG - MVC");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Remetente da Frota:"));
        add(txtRemetente);
        add(new JLabel("Mensagem Secreta:"));
        add(txtMensagem);
        add(new JLabel("Chave de Deslocamento (César 1 a 25):"));
        add(txtChave);
        add(btnTransmitir);
        areaLog.setEditable(false);
        add(new JScrollPane(areaLog));

        btnTransmitir.addActionListener(e -> transmitir());
    }

    private void transmitir() {
        try {
            String pacote = controller.processarTransmissao(
                    txtRemetente.getText(), txtMensagem.getText(), txtChave.getText());
            areaLog.append(pacote + "\n");
            JOptionPane.showMessageDialog(this, "Transmissão efetuada com sucesso!");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "ERRO: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaTransmissao().setVisible(true));
    }
}
