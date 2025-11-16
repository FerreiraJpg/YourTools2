package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.*;

class FerramentasTest {

    private Ferramentas ferramentas;
    private int testToolId;

    @BeforeEach
    void setUp() throws SQLException {
        ferramentas = new Ferramentas();
        // Insere uma ferramenta de teste para operações CRUD gerais e armazena seu ID
        String initialTestName = "Ferramenta de Teste " + System.currentTimeMillis();
        String initialTestBrand = "Marca Teste";
        double initialTestCost = 100.0;
        ferramentas.InsertFerramentasBD(initialTestName, initialTestBrand, initialTestCost);
        testToolId = ferramentas.maiorIDFerramentas();
    }

    @AfterEach
    void tearDown() {
        // Limpa a ferramenta de teste criada no setUp
        try {
            ferramentas.DeleteFerramentasBD(testToolId);
        } catch (Exception e) {
            System.err.println("Erro durante o tearDown do teste: - FerramentasTest.java:37" + e.getMessage());
        }
    }

    // Testes de Unidade para a classe Ferramentas
    @Test
    void testFerramentasEmptyConstructor() {
        Ferramentas newFerramenta = new Ferramentas();
        assertNotNull(newFerramenta);
        assertEquals(0, newFerramenta.getId());
        assertNull(newFerramenta.getNome());
        assertNull(newFerramenta.getMarca());
        assertEquals(0.0, newFerramenta.getCustoAquisicao());
    }

    @Test
    void testFerramentasParameterizedConstructor() {
        int id = 1;
        String nome = "Ferramenta de Teste";
        String marca = "Marca Teste";
        double custo = 150.50;
        Ferramentas newFerramenta = new Ferramentas(id, nome, marca, custo);
        assertNotNull(newFerramenta);
        assertEquals(id, newFerramenta.getId());
        assertEquals(nome, newFerramenta.getNome());
        assertEquals(marca, newFerramenta.getMarca());
        assertEquals(custo, newFerramenta.getCustoAquisicao());
    }

    @Test
    void testToString() {
        int id = 1;
        String nome = "Ferramenta de Teste";
        String marca = "Marca Teste";
        double custo = 150.50;
        Ferramentas newFerramenta = new Ferramentas(id, nome, marca, custo);
        String expectedToString = "\n ID: " + id
                + "\n Nome: " + nome
                + "\n Marca: " + marca
                + "\n Custo de Aquisição: " + custo
                + "\n -----------";
        assertEquals(expectedToString, newFerramenta.toString());
    }

    @Test
    void testFerramentasGettersAndSetters() {
        Ferramentas newFerramenta = new Ferramentas();
        newFerramenta.setId(10);
        newFerramenta.setNome("Teste de Setter");
        newFerramenta.setMarca("Marca Setter");
        newFerramenta.setCustoAquisicao(250.75);

        assertEquals(10, newFerramenta.getId());
        assertEquals("Teste de Setter", newFerramenta.getNome());
        assertEquals("Marca Setter", newFerramenta.getMarca());
        assertEquals(250.75, newFerramenta.getCustoAquisicao());
    }

}