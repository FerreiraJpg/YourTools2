package Integration;

import DAO.YourToolsDAO;
import Model.Ferramentas;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FerramentasIntegrationTest {
    
    static YourToolsDAO dao;
    Ferramentas ferramentaValida;
    Ferramentas ferramentaInvalida;
    
    @BeforeAll
    static void setupDAO() {
        dao = new YourToolsDAO();
    }

    @BeforeEach
    void setupDados() throws SQLException {
        ferramentaValida = new Ferramentas(dao.maiorIDFerramentas() + 1, "Produto Teste Integracao", "Marca Teste Integracao", 27);
        ferramentaInvalida = new Ferramentas(dao.maiorIDFerramentas() + 1, null, "Marca Teste Integracao", 42);
    }
    
    @Test
    @Order(1)
    void testInsertFerramenta() {
    boolean inserido = dao.InsertFerramentasBD(ferramentaValida);
    assertTrue(inserido, "A ferramenta deve ser inserida com sucesso");
    }
    
    @Test
    @Order(2)
    void testInsertFerramentaIncorreta() {
        try {
            boolean inserido = dao.InsertFerramentasBD(ferramentaInvalida);
            assertFalse(inserido, "O método não deveria retornar true em uma inserção inválida");
        } catch (RuntimeException e) {
            assertTrue(true, "Erro lançado corretamente para inserção inválida");
        } catch (Exception e) {
            fail("Deveria lançar RuntimeException, mas lançou outro tipo de erro: " + e.getClass().getSimpleName());
        }
    }
    
    @AfterEach
    void limparBanco() {
            dao.DeleteFerramentasBD(ferramentaValida.getId());
            dao.DeleteFerramentasBD(ferramentaInvalida.getId());
    }
    
}
