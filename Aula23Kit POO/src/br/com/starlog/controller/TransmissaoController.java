package br.com.starlog.controller;

import br.com.starlog.model.CriptoMensagem;
import java.util.ArrayList;
import java.util.List;

public class TransmissaoController {

    private final List<String> bufferTransmissoes = new ArrayList<>();

    public String processarTransmissao(String remetenteRaw, String msgRaw, String chaveStr) {

        int chave;
        try {
            chave = Integer.parseInt(chaveStr.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("Chave deve ser um número inteiro válido.", e);
        }

        CriptoMensagem cripto = new CriptoMensagem(remetenteRaw, msgRaw, chave);

        String pacote = String.format("[%s] #%d -> %s",
                cripto.getRemetente(), cripto.calcularChecksum(), cripto.cifrar());


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
