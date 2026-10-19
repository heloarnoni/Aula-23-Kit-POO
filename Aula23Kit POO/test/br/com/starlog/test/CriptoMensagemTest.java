package br.com.starlog.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import br.com.starlog.model.CriptoMensagem;
import br.com.starlog.controller.TransmissaoController;

import static org.junit.jupiter.api.Assertions.*;

public class CriptoMensagemTest {

    @Test
    @DisplayName("TDD 01: Model deve cifrar mensagem com chave de deslocamento válida")
    void deveCifrarMensagemComChaveValida() {

        CriptoMensagem cripto = new CriptoMensagem("APOLLO", "JAVA", 3);

        String textoCifrado = cripto.cifrar();

        assertEquals("MDYD", textoCifrado);
    }

    @Test
    @DisplayName("TDD 02: Model deve preservar espaços e pontuação na cifra")
    void devePreservarEspacosEPontuacao() {

        CriptoMensagem cripto = new CriptoMensagem("BASE-01", "POO 2026!", 1);

        String textoCifrado = cripto.cifrar();

        assertEquals("QPP 2026!", textoCifrado);
    }

    @Test
    @DisplayName("TDD 03: Model deve aplicar rotação circular do alfabeto (Wrap-around)")
    void deveAplicarRotacaoCircularNoAlfabeto() {

        CriptoMensagem cripto = new CriptoMensagem("FROTA", "XYZ", 1);

        assertEquals("YZA", cripto.cifrar());
    }

    @Test
    @DisplayName("TDD 04: Model deve calcular checksum baseado na soma dos caracteres ASCII")
    void deveCalcularChecksumCorreto() {

        CriptoMensagem cripto = new CriptoMensagem("PILOTO", "AB", 2);

        int checksum = cripto.calcularChecksum();

        assertEquals(131, checksum);
    }

    @Test
    @DisplayName("TDD 05: Fail-Fast - Construtor deve barrar remetente nulo ou em branco")
    void deveLancarExcecaoQuandoRemetenteInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CriptoMensagem("   ", "MENSAGEM", 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new CriptoMensagem(null, "MENSAGEM", 3);
        });
    }

    @Test
    @DisplayName("TDD 06: Fail-Fast - Construtor deve barrar chave de deslocamento fora de 1..25")
    void deveLancarExcecaoQuandoChaveForaDoIntervalo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CriptoMensagem("ALPHA", "MENSAGEM", 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new CriptoMensagem("ALPHA", "MENSAGEM", 26);
        });
    }

    @Test
    @DisplayName("TDD 07: Controller deve formatar o pacote final transmitido corretamente")
    void controllerDeveGerarPacoteFinalFormatado() {

        TransmissaoController controller = new TransmissaoController();

        String pacote = controller.processarTransmissao("BASE", "OLA", "2");

        assertEquals("[BASE] #220 -> QNC", pacote);
        assertEquals(1, controller.getTotalTransmissoes());
    }

    @Test
    @DisplayName("TDD 08: Controller deve barrar chave não numérica capturando NumberFormatException")
    void controllerDeveRejeitarChaveNaoNumerica() {
        TransmissaoController controller = new TransmissaoController();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            controller.processarTransmissao("BASE", "OLA", "abc");
        });
        assertTrue(ex.getMessage().contains("Chave deve ser um número"));
    }
}
