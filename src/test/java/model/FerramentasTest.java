package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import dao.YourToolsDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FerramentasTest {

    private Ferramentas ferramentas;
    private int testToolId;
    
    @Mock
    private YourToolsDAO daoMock;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        
        ferramentas = new Ferramentas();
        String initialTestName = "Ferramenta de Teste " + System.currentTimeMillis();
        String initialTestBrand = "Marca Teste";
        double initialTestCost = 100.0;
        ferramentas.insertFerramentasBD(initialTestName, initialTestBrand, initialTestCost);
        testToolId = ferramentas.maiorIDFerramentas();
    }

    @AfterEach
    void tearDown() {
        try {
            ferramentas.deleteFerramentasBD(testToolId);
        } catch (Exception e) {
            System.err.println("Erro durante o tearDown do teste: - FerramentasTest.java:41" + e.getMessage());
        }
    }

    // ✅ TESTE UNITÁRIO PARA O MÉTODO getMinhaListaFerramentas()
    @Test
    void testGetMinhaListaFerramentasUnitario() {
        // Arrange - Cria uma ferramenta com DAO mockado
        Ferramentas ferramentaComMock = new Ferramentas(daoMock);
        
        // Cria lista esperada
        ArrayList<Ferramentas> listaEsperada = new ArrayList<>();
        listaEsperada.add(new Ferramentas(1, "Martelo", "Tramontina", 50.0));
        listaEsperada.add(new Ferramentas(2, "Chave de Fenda", "Stanley", 30.0));
        
        // Configura o mock para retornar a lista quando chamado
        when(daoMock.getMinhaListaFerramentas()).thenReturn(listaEsperada);
        
        // Act - Chama o método que queremos testar
        ArrayList<Ferramentas> resultado = ferramentaComMock.getMinhaListaFerramentas();
        
        // Assert - Verifica se funcionou corretamente
        assertNotNull(resultado, "A lista não deve ser nula");
        assertEquals(2, resultado.size(), "Deve retornar 2 ferramentas");
        assertEquals("Martelo", resultado.get(0).getNome());
        assertEquals("Chave de Fenda", resultado.get(1).getNome());
        assertEquals(50.0, resultado.get(0).getCustoAquisicao());
        assertEquals(30.0, resultado.get(1).getCustoAquisicao());
        
        // Verifica que o método do DAO foi chamado exatamente 1 vez
        verify(daoMock, times(1)).getMinhaListaFerramentas();
    }

    // Seus outros testes continuam aqui...
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