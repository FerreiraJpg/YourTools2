package Integration;

import DAO.YourToolsDAO;
import Model.Amigos;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AmigosIntegrationTest {

    static YourToolsDAO dao;

    @BeforeAll
    static void setup() {
        dao = new YourToolsDAO();
    }

    @Test
    @Order(1)
    void testInsertAmigo() {
        Amigos amigo = new Amigos(999, "Teste Integracao", 123456789);
        boolean inserido = dao.InsertAmigosBD(amigo);
        assertTrue(inserido, "O amigo deve ser inserido com sucesso");
    }

    @Test
    @Order(2)
    void testInsertAmigoIncorreto() {
        Amigos amigoInvalido = new Amigos(1000, null, 999999999);

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
        dao.DeleteAmigosBD(999);
        dao.DeleteAmigosBD(1000);
    }

}
