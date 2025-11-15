package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.SQLException;
import java.util.ArrayList;

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
            System.err.println("Erro durante o tearDown do teste: - FerramentasTest.java:34" + e.getMessage());
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

    // Testes de Integração e Validação

    @Test
    void testGetMinhaListaFerramentas() {
        ArrayList<Ferramentas> actualList = ferramentas.getMinhaListaFerramentas();
        assertNotNull(actualList);
        assertTrue(actualList.size() > 0, "A lista de ferramentas não deve estar vazia após a inserção no setUp.");
    }

    @Test
    void testInsertFerramentasBD() throws SQLException {
        String testName = "Ferramenta Inserir " + System.currentTimeMillis();
        String testBrand = "Marca Inserir";
        double testCost = 200.0;
        
        boolean result = ferramentas.InsertFerramentasBD(testName, testBrand, testCost);
        assertTrue(result);

        int newId = ferramentas.maiorIDFerramentas();
        Ferramentas insertedFerramenta = ferramentas.carregaFerramentas(newId);
        
        assertNotNull(insertedFerramenta);
        assertEquals(testName, insertedFerramenta.getNome());
        assertEquals(testBrand, insertedFerramenta.getMarca());
        assertEquals(testCost, insertedFerramenta.getCustoAquisicao());
        
        // Limpa esta ferramenta específica
        ferramentas.DeleteFerramentasBD(newId);
    }

    @Test
    void testInsertFerramentasBD_EmptyName() {
        String testName = ""; // Nome vazio
        String testBrand = "Marca Teste";
        double testCost = 200.0;
        
        assertThrows(IllegalArgumentException.class, () -> {
            ferramentas.InsertFerramentasBD(testName, testBrand, testCost);
        }, "InsertFerramentasBD deveria lançar IllegalArgumentException para nome vazio.");
    }

    @Test
    void testInsertFerramentasBD_NullName() {
        String testName = null; // Nome nulo
        String testBrand = "Marca Teste";
        double testCost = 200.0;
        
        assertThrows(IllegalArgumentException.class, () -> {
            ferramentas.InsertFerramentasBD(testName, testBrand, testCost);
        }, "InsertFerramentasBD deveria lançar IllegalArgumentException para nome nulo.");
    }

    @Test
    void testInsertFerramentasBD_EmptyBrand() {
        String testName = "Ferramenta Teste";
        String testBrand = ""; // Marca vazia
        double testCost = 200.0;
        
        assertThrows(IllegalArgumentException.class, () -> {
            ferramentas.InsertFerramentasBD(testName, testBrand, testCost);
        }, "InsertFerramentasBD deveria lançar IllegalArgumentException para marca vazia.");
    }

    @Test
    void testInsertFerramentasBD_NegativeCost() {
        String testName = "Ferramenta Teste";
        String testBrand = "Marca Teste";
        double testCost = -50.0; // Custo negativo
        
        assertThrows(IllegalArgumentException.class, () -> {
            ferramentas.InsertFerramentasBD(testName, testBrand, testCost);
        }, "InsertFerramentasBD deveria lançar IllegalArgumentException para custo negativo.");
    }

    @Test
    void testDeleteFerramentasBD() throws SQLException {
        // Insere uma nova ferramenta especificamente para este teste de exclusão
        String nameToDelete = "Ferramenta para Excluir " + System.currentTimeMillis();
        String brandToDelete = "Marca Exclusão";
        double costToDelete = 300.0;
        ferramentas.InsertFerramentasBD(nameToDelete, brandToDelete, costToDelete);
        int idToDelete = ferramentas.maiorIDFerramentas();

        boolean result = ferramentas.DeleteFerramentasBD(idToDelete);
        assertTrue(result);

        Ferramentas deletedFerramenta = ferramentas.carregaFerramentas(idToDelete);
        assertNull(deletedFerramenta, "Ferramenta deveria ser nula após a exclusão.");
    }

    @Test
    void testDeleteFerramentasBD_NonExistentID() {
        int nonExistentId = -1; // Um ID que não deve existir
        boolean result = ferramentas.DeleteFerramentasBD(nonExistentId);
        assertFalse(result, "Excluir uma ferramenta inexistente deve retornar false.");
    }

    @Test
    void testUpdateFerramentasBD() throws SQLException {
        String updatedName = "Ferramenta Atualizada " + System.currentTimeMillis();
        String updatedBrand = "Marca Atualizada";
        double updatedCost = 350.0;
        boolean result = ferramentas.UpdateFerramentasBD(testToolId, updatedName, updatedBrand, updatedCost);
        assertTrue(result);
        
        Ferramentas updatedFerramenta = ferramentas.carregaFerramentas(testToolId);
        assertNotNull(updatedFerramenta);
        assertEquals(updatedName, updatedFerramenta.getNome());
        assertEquals(updatedBrand, updatedFerramenta.getMarca());
        assertEquals(updatedCost, updatedFerramenta.getCustoAquisicao());
    }

    @Test
    void testUpdateFerramentasBD_EmptyName() {
        String updatedName = ""; // Nome vazio
        String updatedBrand = "Marca Teste";
        double updatedCost = 350.0;
        
        assertThrows(IllegalArgumentException.class, () -> {
            ferramentas.UpdateFerramentasBD(testToolId, updatedName, updatedBrand, updatedCost);
        }, "UpdateFerramentasBD deveria lançar IllegalArgumentException para nome vazio.");
    }

    @Test
    void testUpdateFerramentasBD_NegativeCost() {
        String updatedName = "Ferramenta Atualizada";
        String updatedBrand = "Marca Teste";
        double updatedCost = -100.0; // Custo negativo
        
        assertThrows(IllegalArgumentException.class, () -> {
            ferramentas.UpdateFerramentasBD(testToolId, updatedName, updatedBrand, updatedCost);
        }, "UpdateFerramentasBD deveria lançar IllegalArgumentException para custo negativo.");
    }

    @Test
    void testUpdateFerramentasBD_NonExistentID() {
        int nonExistentId = -1;
        String updatedName = "Atualização Inexistente";
        String updatedBrand = "Marca Inexistente";
        double updatedCost = 200.0;
        boolean result = ferramentas.UpdateFerramentasBD(nonExistentId, updatedName, updatedBrand, updatedCost);
        assertFalse(result, "Atualizar uma ferramenta inexistente deve retornar false ou não existir.");
    }

    @Test
    void testCarregaFerramentas() throws SQLException {
        Ferramentas loadedFerramenta = ferramentas.carregaFerramentas(testToolId);
        
        assertNotNull(loadedFerramenta, "A ferramenta carregada não deveria ser nula.");
        assertEquals(testToolId, loadedFerramenta.getId());
    }

    @Test
    void testCarregaFerramentas_NonExistentID() {
        int nonExistentId = -1;
        Ferramentas loadedFerramenta = ferramentas.carregaFerramentas(nonExistentId);
        assertNull(loadedFerramenta, "Carregar ferramenta com ID inexistente deve retornar nulo.");
    }

    @Test
    void testMaiorIDFerramentas() throws SQLException {
        int result = ferramentas.maiorIDFerramentas();
        assertTrue(result >= testToolId, "O maior ID deve ser maior ou igual ao ID da ferramenta de teste.");
    }
}
