package br.com.starlog.model;

public class CriptoMensagem {
    private final String remetente;
    private final String mensagem;
    private final int chaveDeslocamento;

    public CriptoMensagem(String remetente, String mensagem, int chaveDeslocamento) {

        if (remetente == null || remetente.trim().isEmpty()) {
            throw new IllegalArgumentException("Remetente não pode ser nulo ou vazio.");
        }
        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("Mensagem não pode ser nula ou vazia.");
        }
        if (chaveDeslocamento < 1 || chaveDeslocamento > 25) {
            throw new IllegalArgumentException("Chave deve estar entre 1 e 25.");
        }

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

    public int calcularChecksum() {
        int checksum = 0;
        for (char c : mensagem.toCharArray()) {
            checksum += (int) c;
        }
        return checksum;
    }
}
