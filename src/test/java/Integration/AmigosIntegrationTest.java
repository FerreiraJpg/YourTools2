package Integration;

import DAO.YourToolsDAO;
import Model.Amigos;
import java.sql.SQLException;
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
        } catch (RuntimeException e) {
            assertTrue(true, "Erro lançado corretamente para inserção inválida");
        } catch (Exception e) {
            fail("Deveria lançar RuntimeException, mas lançou outro tipo de erro: " + e.getClass().getSimpleName());
        }
    }
    
    @AfterEach
    void limparBanco() {
        dao.DeleteAmigosBD(amigoValido.getId());
        dao.DeleteAmigosBD(amigoInvalido.getId());
    }

}
