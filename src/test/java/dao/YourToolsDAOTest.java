package dao;

import model.Amigos;
import model.Ferramentas;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class YourToolsDAOTest {

    private YourToolsDAO dao;
    private static final int ID_AMIGO_TESTE = 9999;
    private static final int ID_FERRAMENTA_TESTE = 8888;

    @BeforeEach
    public void setup() {
        dao = new YourToolsDAO();
    }

    @AfterAll
    public static void cleanUpAll() {
        YourToolsDAO cleanupDao = new YourToolsDAO();
        cleanupDao.DeleteAmigosBD(ID_AMIGO_TESTE);
        cleanupDao.DeleteFerramentasBD(ID_FERRAMENTA_TESTE);
    }

    @Test
    @Order(1)
    public void testGetConexao_Sucesso() {
        assertDoesNotThrow(() -> {
            dao.getConexao().close();
        }, "A conexão com o banco de dados SQLite deve ser bem-sucedida.");
    }

    @Test
    @Order(2)
    public void testMaiorIDAmigos_Sucesso() {
        assertDoesNotThrow(() -> {
            int maiorID = dao.maiorIDAmigos();
            assertTrue(maiorID >= 0, "O maior ID deve ser não-negativo.");
        }, "Não deve lançar exceção ao buscar o maior ID de amigos.");
    }

    @Test
    @Order(3)
    public void testInsertAmigosBD_Sucesso() {
        Amigos amigo = new Amigos(ID_AMIGO_TESTE, "Amigo SQLite", 11223344);

        assertTrue(dao.InsertAmigosBD(amigo), "A inserção do amigo deve retornar true.");
    }

    @Test
    @Order(4)
    public void testCarregaAmigos_Existente() {
        Amigos amigoCarregado = dao.carregaAmigos(ID_AMIGO_TESTE);

        assertNotNull(amigoCarregado, "O amigo deve ser carregado (não nulo).");
        assertEquals(ID_AMIGO_TESTE, amigoCarregado.getId(), "O ID do amigo deve corresponder.");
        assertEquals("Amigo SQLite", amigoCarregado.getNome(), "O nome do amigo deve corresponder.");
    }

    @Test
    @Order(5)
    public void testUpdateAmigosBD_Sucesso() {
        Amigos amigoUpdate = new Amigos(ID_AMIGO_TESTE, "Nome Atualizado SQLite", 99887766);

        assertTrue(dao.UpdateAmigosBD(amigoUpdate), "A atualização do amigo deve retornar true.");

        Amigos amigoVerificado = dao.carregaAmigos(ID_AMIGO_TESTE);
        assertEquals("Nome Atualizado SQLite", amigoVerificado.getNome(), "O nome deve ser atualizado.");
        assertEquals(99887766, amigoVerificado.getTelefone(), "O telefone deve ser atualizado.");
    }

    @Test
    @Order(6)
    public void testDeleteAmigosBD_Sucesso() {
        assertTrue(dao.DeleteAmigosBD(ID_AMIGO_TESTE), "A exclusão do amigo deve retornar true.");

        assertNull(dao.carregaAmigos(ID_AMIGO_TESTE), "O amigo excluído não deve ser mais encontrado.");
    }

    @Test
    @Order(7)
    public void testMaiorIDFerramentas_Sucesso() {
        assertDoesNotThrow(() -> {
            int maiorID = dao.maiorIDFerramentas();
            assertTrue(maiorID >= 0, "O maior ID deve ser não-negativo.");
        }, "Não deve lançar exceção ao buscar o maior ID de ferramentas.");
    }

    @Test
    @Order(8)
    public void testInsertFerramentasBD_Sucesso() {
        Ferramentas ferramenta = new Ferramentas(ID_FERRAMENTA_TESTE, "Chave de Fenda", "Marca Teste", 25.50);

        assertTrue(dao.InsertFerramentasBD(ferramenta), "A inserção da ferramenta deve retornar true.");
    }

    @Test
    @Order(9)
    public void testCarregaFerramentas_Existente() {
        Ferramentas ferramentaCarregada = dao.carregaFerramentas(ID_FERRAMENTA_TESTE);

        assertNotNull(ferramentaCarregada, "A ferramenta deve ser carregada (não nula).");
        assertEquals(ID_FERRAMENTA_TESTE, ferramentaCarregada.getId(), "O ID da ferramenta deve corresponder.");
        assertEquals("Chave de Fenda", ferramentaCarregada.getNome(), "O nome deve corresponder.");
    }

    @Test
    @Order(10)
    public void testDeleteFerramentasBD_Sucesso() {
        assertTrue(dao.DeleteFerramentasBD(ID_FERRAMENTA_TESTE), "A exclusão da ferramenta deve retornar true.");

        ArrayList<Ferramentas> lista = dao.getMinhaListaFerramentas();
        boolean aindaExiste = lista.stream().anyMatch(f -> f.getId() == ID_FERRAMENTA_TESTE);
        assertFalse(aindaExiste, "A ferramenta excluída não deve estar na lista.");
    }

    @Test
    @Order(11)
    public void testGetMinhaListaAmigos_Vazia() {

        dao.DeleteAmigosBD(ID_AMIGO_TESTE);

        ArrayList<Amigos> lista = dao.getMinhaListaAmigos();

        assertNotNull(lista, "A lista de amigos não deve ser nula.");
        assertTrue(lista.isEmpty(), "A lista de amigos deve estar vazia após a exclusão dos registros de teste.");
    }

    @Test
    @Order(12)
    public void testGetMinhaListaFerramentas_Vazia() {

        dao.DeleteFerramentasBD(ID_FERRAMENTA_TESTE);

        ArrayList<Ferramentas> lista = dao.getMinhaListaFerramentas();

        assertNotNull(lista, "A lista de ferramentas não deve ser nula.");
        assertTrue(lista.isEmpty(), "A lista de ferramentas deve estar vazia após a limpeza.");
    }

}
