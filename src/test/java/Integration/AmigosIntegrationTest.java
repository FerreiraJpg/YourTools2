package Integration;

import DAO.YourToolsDAO;
import Model.Amigos;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AmigosIntegrationTest {

    static YourToolsDAO dao;
    Amigos amigoValido;
    Amigos amigoInvalido;

    @BeforeAll
    static void setupDAO() {
        dao = new YourToolsDAO();
    }

    @BeforeEach
    void setupDados() throws SQLException {
        amigoValido = new Amigos(dao.maiorIDAmigos() + 1, "Teste Integracao", 123456789);
        amigoInvalido = new Amigos(dao.maiorIDAmigos() + 1, null, 999999999);
    }

    @Test
    @Order(1)
    void testInsertAmigo() {
        boolean inserido = dao.InsertAmigosBD(amigoValido);
        assertTrue(inserido, "O amigo deve ser inserido com sucesso");
    }

    @Test
    @Order(2)
    void testInsertAmigoIncorreto() {
        try {
            boolean inserido = dao.InsertAmigosBD(amigoInvalido);
            assertFalse(inserido, "O método não deveria retornar true em uma inserção inválida");
        } catch (IllegalArgumentException e) {
            assertTrue(true, "Erro lançado corretamente para inserção inválida");
        } catch (RuntimeException e) {
            assertTrue(true, "Erro lançado corretamente para inserção inválida");
        } catch (Exception e) {
            fail("Deveria lançar RuntimeException, mas lançou outro tipo de erro: " + e.getClass().getSimpleName());
        }
    }
    
    @Test
    @Order(3)
    void testAtualizarAmigo() {
        dao.InsertAmigosBD(amigoValido);
        amigoValido.setNome("Amigo Atualizado");
        dao.UpdateAmigosBD(amigoValido);

        Amigos amigoBanco = dao.carregaAmigos(amigoValido.getId());

        assertNotNull(amigoBanco, "O amigo atualizado deve existir no banco");
        assertEquals("Amigo Atualizado", amigoBanco.getNome(), "O nome deve ter sido atualizado corretamente");
        dao.DeleteAmigosBD(amigoValido.getId());
    }
    
    @Test
    @Order(4)
    void testAtualizarAmigoDadoInvalido() {
        dao.InsertAmigosBD(amigoValido);
        amigoValido.setNome(null);
        
        try {
            boolean atualizado = dao.UpdateAmigosBD(amigoValido);
            assertFalse(atualizado, "O método não deveria retornar true em um ajuste inválido");
        } catch (IllegalArgumentException e) {
            assertTrue(true, "Erro lançado corretamente para ajuste inválido");
        } catch (RuntimeException e) {
            assertTrue(true, "Erro lançado corretamente para ajuste inválido");
        } catch (Exception e) {
            fail("Deveria lançar RuntimeException, mas lançou outro tipo de erro: " + e.getClass().getSimpleName());
        }
        dao.DeleteAmigosBD(amigoValido.getId());
    }
    
    @Test
    @Order(5)
    void testExcluirAmigo() {
        int amigoValidoID = amigoValido.getId();
        boolean deletado = dao.DeleteAmigosBD(amigoValidoID);
        assertTrue(deletado, "O amigo deve ser excluído com sucesso");
    }
    
    @Test
    @Order(6)
    void testExcluirAmigoNull() throws SQLException {
        int idInvalido = dao.maiorIDAmigos() + 1;

        try
        {
            boolean deletado = dao.DeleteAmigosBD(idInvalido);
            assertFalse(deletado, "Não deve excluir caso não haja um ID válido");
        } catch (RuntimeException e) {
            assertTrue(true, "Erro lançado corretamente para exclusão de ID inválido");
        } catch (Exception e) {
            fail("Deveria lançar RuntimeException, mas lançou outro tipo de erro: " + e.getClass().getSimpleName());
        }
    }
    
    @Test
    @Order(7)
    void testListarAmigos() {
        dao.InsertAmigosBD(amigoValido);
        List<Amigos> lista = dao.getMinhaListaAmigos();
        assertNotNull(lista);
        assertFalse(lista.isEmpty(), "A lista de amigos deve conter ao menos um registro");
        dao.DeleteAmigosBD(amigoValido.getId());
    }
    
    @Test
    @Order(8)
    void testListarAmigosZerado() {
        dao.DeleteAmigosBD(amigoValido.getId());
        dao.DeleteAmigosBD(amigoInvalido.getId());
        List<Amigos> lista = dao.getMinhaListaAmigos();
        assertNotNull(lista);
        assertTrue(lista.isEmpty(), "A lista de amigos deve estar vazia");
    }

    @AfterEach
    void limparBanco() {
        dao.DeleteAmigosBD(amigoValido.getId());
        dao.DeleteAmigosBD(amigoInvalido.getId());
    }
    
}