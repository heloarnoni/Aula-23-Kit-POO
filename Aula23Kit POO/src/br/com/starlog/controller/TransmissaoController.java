package br.com.starlog.controller;

import br.com.starlog.model.CriptoMensagem;
import java.util.ArrayList;
import java.util.List;

/**
 * CAMADA CONTROLLER (O Maestro da Transmissão)
 * Converte tipos brutos e aciona o Model.
 * Zero imports de javax.swing: quem mostra o erro na tela é a View.
 */
public class TransmissaoController {

    private final List<String> bufferTransmissoes = new ArrayList<>();

    /**
     * @return Pacote formatado no padrão: [REMETENTE] #CHECKSUM -> TEXTO_CIFRADO
     * @throws IllegalArgumentException se a chave não for número ou o Model rejeitar os dados
     */
    public String processarTransmissao(String remetenteRaw, String msgRaw, String chaveStr) {
        // 1. Conversão segura de tipo (responsabilidade do Controller)
        int chave;
        try {
            chave = Integer.parseInt(chaveStr.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("Chave deve ser um número inteiro válido.", e);
        }

        // 2. Model valida as regras de negócio (lança IllegalArgumentException)
        CriptoMensagem cripto = new CriptoMensagem(remetenteRaw, msgRaw, chave);

        // 3. Monta o pacote
        String pacote = String.format("[%s] #%d -> %s",
                cripto.getRemetente(), cripto.calcularChecksum(), cripto.cifrar());

        // 4. Guarda e devolve
        bufferTransmissoes.add(pacote);
        return pacote;
    }

    public int getTotalTransmissoes() {
        return bufferTransmissoes.size();
    }

    public List<String> getHistorico() {
        return new ArrayList<>(bufferTransmissoes);
    }
}
