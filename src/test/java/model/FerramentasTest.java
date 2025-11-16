package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import dao.YourToolsDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
            System.err.println("Erro durante o tearDown do teste: - FerramentasTest.java:43" + e.getMessage());
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

    @Test
    void testUpdateFerramentasBDUnitario() {
        // Cria ferramenta com DAO mockado
        Ferramentas ferramentaComMock = new Ferramentas(daoMock);

        int id = 1;
        String nome = "Martelo Atualizado";
        String marca = "Tramontina";
        double custo = 75.50;

        // Configura o mock para retornar true quando updateFerramentasBD for chamado
        when(daoMock.updateFerramentasBD(any(Ferramentas.class))).thenReturn(true);

        // Chama o método que queremos testar
        boolean resultado = ferramentaComMock.updateFerramentasBD(id, nome, marca, custo);

        // Verifica os resultados
        assertTrue(resultado, "O método deve retornar true");

        // Verifica se o DAO foi chamado exatamente 1 vez
        verify(daoMock, times(1)).updateFerramentasBD(any(Ferramentas.class));

        // Captura o argumento passado para o DAO para validar
        ArgumentCaptor<Ferramentas> captor = ArgumentCaptor.forClass(Ferramentas.class);
        verify(daoMock).updateFerramentasBD(captor.capture());

        Ferramentas ferramentaCapturada = captor.getValue();
        assertEquals(id, ferramentaCapturada.getId());
        assertEquals(nome, ferramentaCapturada.getNome());
        assertEquals(marca, ferramentaCapturada.getMarca());
        assertEquals(custo, ferramentaCapturada.getCustoAquisicao());
    }

    @Test
    void testUpdateFerramentasBD_DadosInvalidos() {
        // Arrange
        Ferramentas ferramentaComMock = new Ferramentas(daoMock);

        int id = 1;
        String nomeInvalido = null; // Nome inválido
        String marca = "Makita";
        double custo = 75.50;

        // Configura o mock para lançar exceção quando receber dados inválidos
        when(daoMock.updateFerramentasBD(any(Ferramentas.class)))
                .thenThrow(new IllegalArgumentException("Nome não pode ser nulo/vazio"));

        //Verifica se a exceção é lançada
        assertThrows(IllegalArgumentException.class, () -> {
            ferramentaComMock.updateFerramentasBD(id, nomeInvalido, marca, custo);
        });

        // Verifica que o DAO foi chamado
        verify(daoMock, times(1)).updateFerramentasBD(any(Ferramentas.class));
    }

    @Test
    void testCarregaFerramentasUnitario() {
        //Cria ferramenta com DAO mockado
        Ferramentas ferramentaComMock = new Ferramentas(daoMock);

        int idBusca = 10;
        Ferramentas ferramentaEsperada = new Ferramentas(idBusca, "Alicate", "Tramontina", 45.90);

        // Configura o mock para retornar a ferramenta esperada
        when(daoMock.carregaFerramentas(idBusca)).thenReturn(ferramentaEsperada);

        //Chama o método
        Ferramentas resultado = ferramentaComMock.carregaFerramentas(idBusca);

        //Verifica os resultados
        assertNotNull(resultado, "A ferramenta não deve ser nula");
        assertEquals(idBusca, resultado.getId());
        assertEquals("Alicate", resultado.getNome());
        assertEquals("Tramontina", resultado.getMarca());
        assertEquals(45.90, resultado.getCustoAquisicao());

        // Verifica que o DAO foi chamado 1 vez com o ID correto
        verify(daoMock, times(1)).carregaFerramentas(idBusca);
    }

    @Test
    void testCarregaFerramentas_NaoEncontrado() {
        // Arrange
        Ferramentas ferramentaComMock = new Ferramentas(daoMock);
        int idInexistente = 999;

        // Configura o mock para retornar null (ferramenta não encontrada)
        when(daoMock.carregaFerramentas(idInexistente)).thenReturn(null);

        // Act
        Ferramentas resultado = ferramentaComMock.carregaFerramentas(idInexistente);

        // Assert
        assertNull(resultado, "Deve retornar null quando a ferramenta não for encontrada");
        verify(daoMock, times(1)).carregaFerramentas(idInexistente);
    }

}