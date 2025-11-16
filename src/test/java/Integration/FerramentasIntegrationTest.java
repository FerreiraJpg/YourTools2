package Integration;

import dao.YourToolsDAO;
import model.Ferramentas;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FerramentasIntegrationTest {
    
    static YourToolsDAO dao;
    Ferramentas ferramentaValida;
    Ferramentas ferramentaNomeInvalido;
    Ferramentas ferramentaMarcaInvalida;
    Ferramentas ferramentaPrecoInvalido;
    
    @BeforeAll
    static void setupDAO() {
        dao = new YourToolsDAO();
    }

    @BeforeEach
    void setupDados() throws SQLException {
        ferramentaValida = new Ferramentas(dao.maiorIDFerramentas() + 1, "Produto Teste Integracao", "Marca Teste Integracao", 27);
        ferramentaNomeInvalido = new Ferramentas(dao.maiorIDFerramentas() + 1, null, "Marca Teste Integracao", 42);
        ferramentaMarcaInvalida = new Ferramentas(dao.maiorIDFerramentas() + 1, "Nome Teste Integracao", null, 42);
        ferramentaPrecoInvalido = new Ferramentas(dao.maiorIDFerramentas() + 1, "Nome Teste Integracao", "Marca Teste Integracao", 0);
    }
    
    @Test
    @Order(1)
    void testInsertFerramenta() {
    boolean inserido = dao.insertFerramentasBD(ferramentaValida);
    assertTrue(inserido, "A ferramenta deve ser inserida com sucesso");
    }
    
    @Test
    @Order(2)
    void testInsertFerramentaIncorreta() {
        try {
            boolean inserido = dao.insertFerramentasBD(ferramentaNomeInvalido);
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
    void testAtualizarFerramenta() {
        dao.insertFerramentasBD(ferramentaValida);
        
        ferramentaValida.setNome("Ferramenta Atualizada");
        ferramentaValida.setMarca("Marca Atualizada");
        ferramentaValida.setCustoAquisicao(25);
        dao.updateFerramentasBD(ferramentaValida);
        
        Ferramentas ferramentaBanco = dao.carregaFerramentas(ferramentaValida.getId());

        assertNotNull(ferramentaBanco, "a ferramenta atualizada deve existir no banco");
        assertEquals("Ferramenta Atualizada", ferramentaBanco.getNome(), "O nome do produto deve ter sido atualizado corretamente");
        dao.deleteFerramentasBD(ferramentaValida.getId());
    }

    @Test
    @Order(4)
    void testExcluirFerramentaValida() {
        dao.insertFerramentasBD(ferramentaValida);
        int ferramentaValidaID = ferramentaValida.getId();
        boolean deletada = dao.deleteFerramentasBD(ferramentaValidaID);
        assertTrue(deletada, "A ferramenta deve ser excluída com sucesso");
    }
    
    @Test
    @Order(5)
    void testExcluirferramentaNomeInvalido() throws SQLException {
        int idInvalido = dao.maiorIDFerramentas() + 1;

        try
        {
            boolean deletada = dao.deleteFerramentasBD(idInvalido);
            assertFalse(deletada, "Não deve excluir caso não haja um ID válido");
        } catch (IllegalArgumentException e) {
            assertTrue(true, "Erro lançado corretamente para inserção inválida");
        } catch (RuntimeException e) {
            assertTrue(true, "Erro lançado corretamente para exclusão de ID inválido");
        }
    }
    
    @Test
    @Order(6)
    void testListarFerramentas() {
        dao.insertFerramentasBD(ferramentaValida);
        List<Ferramentas> lista = dao.getMinhaListaFerramentas();
        assertNotNull(lista);
        assertFalse(lista.isEmpty(), "A lista de ferramentas deve conter ao menos um registro");
        dao.deleteFerramentasBD(ferramentaValida.getId());
    }
    
    @Test
    @Order(7)
    void testListarFerramentasVazia() {
        List<Ferramentas> todosFerramentas = dao.getMinhaListaFerramentas();
        for (Ferramentas a : todosFerramentas) {
            dao.deleteFerramentasBD(a.getId());
        }
        List<Ferramentas> lista = dao.getMinhaListaFerramentas();
        assertNotNull(lista);
        assertTrue(lista.isEmpty(), "A lista de ferramentas deve estar vazia");
    }
    
    @Test
    @Order(8)
    void testDeleteFerramentaBDCatch() throws Exception {
        YourToolsDAO daoErro = new YourToolsDAO();
        daoErro.getConexao().close();

        boolean resultado = daoErro.deleteFerramentasBD(1);
        assertFalse(resultado, "Quando ocorrer SQLException, o método deve retornar false");
    }

    @Test
    @Order(9)
    void testInsertFerramentasMarcaInvalida() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.insertFerramentasBD(ferramentaMarcaInvalida);
        });

        assertEquals("Marca não pode ser nulo/vazio", exception.getMessage());
    }
    
    @Test
    @Order(10)
    void testInsertFerramentasPrecoInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.insertFerramentasBD(ferramentaPrecoInvalido);
        });

        assertEquals("Preço não pode ser nulo/vazio", exception.getMessage());
    }
    
    @Test
    @Order(11)
    void testAtualizarFerramentaNomeInvalido() {
        dao.insertFerramentasBD(ferramentaValida);
        
        ferramentaValida.setNome(null);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.updateFerramentasBD(ferramentaValida);
        });

        dao.deleteFerramentasBD(ferramentaValida.getId());
    }
    
    @Test
    @Order(12)
    void testAtualizarFerramentaMarcaInvalido() {
        dao.insertFerramentasBD(ferramentaValida);
        
        ferramentaValida.setMarca(null);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.updateFerramentasBD(ferramentaValida);
        });

        dao.deleteFerramentasBD(ferramentaValida.getId());
    }
    
    @Test
    @Order(13)
    void testAtualizarFerramentaPrecoInvalido() {
        dao.insertFerramentasBD(ferramentaValida);
        
        ferramentaValida.setCustoAquisicao(0);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.updateFerramentasBD(ferramentaValida);
        });

        dao.deleteFerramentasBD(ferramentaValida.getId());
    }

    
    @AfterEach
    void limparBanco() {
        dao.deleteFerramentasBD(ferramentaValida.getId());
        dao.deleteFerramentasBD(ferramentaNomeInvalido.getId());
    }
    
}
