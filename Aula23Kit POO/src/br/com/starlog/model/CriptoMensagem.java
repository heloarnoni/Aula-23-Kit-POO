package br.com.starlog.model;

/**
 * CAMADA MODEL (Modelo de Domínio)
 * Representa uma mensagem criptográfica a ser transmitida.
 * Zero imports de javax.swing: roda e testa sem abrir janela.
 */
public class CriptoMensagem {
    private final String remetente;
    private final String mensagem;
    private final int chaveDeslocamento;

    public CriptoMensagem(String remetente, String mensagem, int chaveDeslocamento) {
        // Barreira Fail-Fast
        if (remetente == null || remetente.trim().isEmpty()) {
            throw new IllegalArgumentException("Remetente não pode ser nulo ou vazio.");
        }
        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("Mensagem não pode ser nula ou vazia.");
        }
        if (chaveDeslocamento < 1 || chaveDeslocamento > 25) {
            throw new IllegalArgumentException("Chave deve estar entre 1 e 25.");
        }

        // Normalização
        this.remetente = remetente.trim().toUpperCase();
        this.mensagem = mensagem.trim().toUpperCase();
        this.chaveDeslocamento = chaveDeslocamento;
    }

    public String getRemetente() {
        return remetente;
    }

    public String getMensagem() {
        return mensagem;
    }

    public int getChaveDeslocamento() {
        return chaveDeslocamento;
    }

    /**
     * Cifra de César com deslocamento circular ('A'..'Z').
     * Preserva espaços, números e pontuações sem alteração.
     */
    public String cifrar() {
        StringBuilder cifrado = new StringBuilder();
        for (int i = 0; i < mensagem.length(); i++) {
            char c = mensagem.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                cifrado.append((char) ('A' + (c - 'A' + chaveDeslocamento) % 26));
            } else {
                cifrado.append(c);
            }
        }
        return cifrado.toString();
    }

    /**
     * Checksum: soma do código ASCII de cada caractere da mensagem em maiúsculo.
     */
    public int calcularChecksum() {
        int checksum = 0;
        for (char c : mensagem.toCharArray()) {
            checksum += (int) c;
        }
        return checksum;
    }
}
