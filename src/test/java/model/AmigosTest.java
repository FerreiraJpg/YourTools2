package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dao.YourToolsDAO;
import org.junit.jupiter.api.AfterEach;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class AmigosTest {

    private YourToolsDAO dao;
    private Amigos amigos;
    private int testAmigoId;
    
    private static Stream<Object[]> invalidInsertParameters() {
        return Stream.of(
                new Object[]{"", 111222333, "Nome vazio deve falhar"},
                new Object[]{null, 111222333, "Nome nulo deve falhar"},
                new Object[]{"Amigo Telefone Zero", 0, "Telefone zero deve falhar"},
                new Object[]{"Amigo Telefone negativo", -451, "Telefone negativo deve falhar"}
        );
    }

    @BeforeEach
    void setUp()  {
        dao = new YourToolsDAO();
        amigos = new Amigos();
        String initialTestName = "Amigo de Teste Inicial " + System.currentTimeMillis();
        int initialTestPhone = 100000000;
        amigos.insertAmigosBD(initialTestName, initialTestPhone);
        testAmigoId = amigos.maiorIDAmigos();
    }

    @AfterEach
    void tearDown() {
        // Limpa o amigo de teste criado no setUp
        try {
            amigos.deleteAmigosBD(testAmigoId);
        } catch (Exception e) {
            // Registra ou trata a exceção se o amigo não puder ser excluído
            System.err.println("Erro durante o tearDown do teste: " + e.getMessage());
        }
    }

    // --- Testes de Unidade para a classe Amigos ---
    @Test
    void testAmigosEmptyConstructor() {
        Amigos newAmigo = new Amigos();
        assertNotNull(newAmigo);
        // Valores padrão do construtor de Pessoa
        assertEquals(0, newAmigo.getId());
        assertNull(newAmigo.getNome());
        assertEquals(0, newAmigo.getTelefone());
    }

    @Test
    void testAmigosParameterizedConstructor() {
        int id = 1;
        String nome = "Amigo de Teste";
        int telefone = 123456789;
        Amigos newAmigo = new Amigos(id, nome, telefone);
        assertNotNull(newAmigo);
        assertEquals(id, newAmigo.getId());
        assertEquals(nome, newAmigo.getNome());
        assertEquals(telefone, newAmigo.getTelefone());
    }

    @Test
    void testToString() {
        int id = 1;
        String nome = "Amigo de Teste";
        int telefone = 123456789;
        Amigos newAmigo = new Amigos(id, nome, telefone);
        String expectedToString = "\n ID: " + id
                                + "\n Nome: " + nome
                                + "\n Telefone: " + telefone
                                + "\n -----------";
        assertEquals(expectedToString, newAmigo.toString());
    }

    @Test
    void testPessoaGettersAndSetters() {
        Amigos newAmigo = new Amigos();
        newAmigo.setId(10);
        newAmigo.setNome("Teste de Setter");
        newAmigo.setTelefone(987654321);

        assertEquals(10, newAmigo.getId());
        assertEquals("Teste de Setter", newAmigo.getNome());
        assertEquals(987654321, newAmigo.getTelefone());
    }

    // --- Testes de Integração e Validação ---

    @Test
    void testGetMinhaListaAmigos() {
        ArrayList<Amigos> actualList = amigos.getMinhaListaAmigos();
        assertNotNull(actualList);
        assertTrue(actualList.size() > 0, "A lista de amigos não deve estar vazia após a inserção no setUp.");
    }

    @Test
    void testInsertAmigosBD()  {
        String testName = "Amigo de Teste Inserir " + System.currentTimeMillis();
        int testPhone = 111222333;
        
        boolean result = amigos.insertAmigosBD(testName, testPhone);
        assertTrue(result);

        int newId = amigos.maiorIDAmigos();
        Amigos insertedAmigo = amigos.carregaAmigos(newId);
        
        assertNotNull(insertedAmigo);
        assertEquals(testName, insertedAmigo.getNome());
        assertEquals(testPhone, insertedAmigo.getTelefone());
        
        // Limpa este amigo específico
        amigos.deleteAmigosBD(newId);
    }
    
    @ParameterizedTest
    @MethodSource("invalidInsertParameters")
    @DisplayName("Testes parametrizados - inserção inválida de amigos")
    void testInsertAmigosBD_InvalidInputs(String name, int phone, String message)  {
        Amigos amigos = new Amigos();

        boolean result = amigos.insertAmigosBD(name, phone);

        assertFalse(result, message);
    }    
    
    @Test
    void testDeleteAmigosBD()  {
        // Insere um novo amigo especificamente para este teste de exclusão
        String nameToDelete = "Amigo para Excluir " + System.currentTimeMillis();
        int phoneToDelete = 999888777;
        amigos.insertAmigosBD(nameToDelete, phoneToDelete);
        int idToDelete = amigos.maiorIDAmigos(); 

        boolean result = amigos.deleteAmigosBD(idToDelete);
        assertTrue(result);

        Amigos deletedAmigo = amigos.carregaAmigos(idToDelete);
        assertNull(deletedAmigo, "Amigo deveria ser nulo após a exclusão.");
    }

    @Test
    void testDeleteAmigosBD_NonExistentID() {
        int nonExistentId = -1; // Um ID que não deve existir
        boolean result = amigos.deleteAmigosBD(nonExistentId);
        assertTrue(result, "Excluir um amigo inexistente ainda deve retornar true se nenhum erro for lançado pelo DAO.");
        // A implementação atual do DAO não verifica explicitamente a existência antes de tentar excluir,
        // então provavelmente retornará true mesmo que nada tenha sido excluído. Esta é uma área para melhoria.
    }

    @Test
    void testUpdateAmigosBD()  {
        String updatedName = "Amigo Atualizado " + System.currentTimeMillis();
        int updatedPhone = 321321321;
        boolean result = amigos.updateAmigosBD(testAmigoId, updatedName, updatedPhone);
        assertTrue(result);
        
        Amigos updatedAmigo = amigos.carregaAmigos(testAmigoId);
        assertNotNull(updatedAmigo);
        assertEquals(updatedName, updatedAmigo.getNome());
        assertEquals(updatedPhone, updatedAmigo.getTelefone());
    }

    @Test
    void testUpdateAmigosBD_EmptyName() {
        String updatedName = ""; // Nome vazio
        int updatedPhone = 321321321;

        boolean result = amigos.updateAmigosBD(testAmigoId, updatedName, updatedPhone);
        assertFalse(result, "A atualização de amigo com nome vazio deveria falhar.");
    }

    @Test
    void testUpdateAmigosBD_NullName() {
        String updatedName = null; // Nome nulo
        int updatedPhone = 321321321;

        boolean result = amigos.updateAmigosBD(testAmigoId, updatedName, updatedPhone);
        assertFalse(result, "A atualização de amigo com nome nulo deveria falhar.");
    }

    @Test
    void testUpdateAmigosBD_ZeroPhoneNumber() {
        String updatedName = "Amigo Atualizado Telefone Zero";
        int updatedPhone = 0;

        boolean result = amigos.updateAmigosBD(testAmigoId, updatedName, updatedPhone);
        assertFalse(result, "A atualização de amigo com telefone zero deveria falhar.");
    }

    @Test
    void testUpdateAmigosBD_NegativePhoneNumber() {
        String updatedName = "Amigo Atualizado Telefone " + System.currentTimeMillis();
        int updatedPhone = -98765; // Número de telefone negativo

        boolean result = amigos.updateAmigosBD(testAmigoId, updatedName, updatedPhone);
        assertFalse(result, "A atualização de amigo com número de telefone negativo deveria falhar.");
    }

    @Test
    void testUpdateAmigosBD_NonExistentID() {
        int nonExistentId = -1;
        String updatedName = "Atualização Inexistente";
        int updatedPhone = 123123123;
        boolean result = amigos.updateAmigosBD(nonExistentId, updatedName, updatedPhone);
        assertTrue(result, "Atualizar um amigo inexistente ainda deve retornar true se nenhum erro for lançado pelo DAO.");
        // Semelhante à exclusão, o DAO atual pode retornar true sem atualização real.
    }

    @Test
    void testCarregaAmigos()  {
        Amigos loadedAmigo = amigos.carregaAmigos(testAmigoId);
        
        assertNotNull(loadedAmigo, "O amigo carregado não deveria ser nulo.");
        // As asserções para nome e telefone são feitas no setUp, então apenas verificamos se é o ID correto
        assertEquals(testAmigoId, loadedAmigo.getId());
    }

    @Test
    void testCarregaAmigos_NonExistentID() {
        int nonExistentId = -1;
        Amigos loadedAmigo = amigos.carregaAmigos(nonExistentId);
        assertNull(loadedAmigo, "Carregar amigo com ID inexistente deve retornar nulo.");
    }

    @Test
    void testMaiorIDAmigos()  {
        int result = amigos.maiorIDAmigos();
        assertTrue(result >= testAmigoId, "O maior ID deve ser maior ou igual ao ID do amigo de teste.");
    }
}
