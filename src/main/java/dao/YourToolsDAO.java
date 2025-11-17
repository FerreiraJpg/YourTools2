package dao;

import model.Amigos;
import model.Ferramentas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsável pelo acesso e manipulação de dados no banco SQLite.
 * Implementa operações CRUD para as entidades
 * Amigos e Ferramentas.
 * 
 * @author YourTools Team
 * @version 1.0
 * @since 2025
 */
public class YourToolsDAO {

    /**
     * Lista estática que armazena objetos do tipo Amigos.
     */
    protected static final ArrayList<Amigos> MinhaLista = new ArrayList<>();
    
    /**
     * Lista estática que armazena objetos do tipo Ferramentas.
     */
    protected static final ArrayList<Ferramentas> MinhaListaFerramentas = new ArrayList<>();
    
    /**
     * Logger para registro de eventos e erros da classe.
     */
    Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Construtor padrão que inicializa o DAO e cria as tabelas necessárias.
     * Chama o método criarTabelas() automaticamente.
     */
    public YourToolsDAO() {
        criarTabelas();
    }

    /**
     * Estabelece e retorna uma conexão com o banco de dados SQLite.
     * Configura propriedades de conexão como busy_timeout.
     * 
     * @return Connection objeto de conexão com o banco de dados
     * @throws IllegalStateException se o driver SQLite não for encontrado ou houver erro na conexão
     */
    public Connection getConexao() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db_yourtools.db";

            Properties props = new Properties();
            props.setProperty("busy_timeout", "5000");

            Connection connection = DriverManager.getConnection(url, props);

            return connection;

        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Driver SQLite não encontrado! Verifique o pom.xml.", e);
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao conectar ao banco de dados SQLite!", e);
        }
    }

    /**
     * Cria as tabelas tb_amigos e tb_ferramentas no banco de dados caso não existam.
     * Utiliza a cláusula IF NOT EXISTS para evitar erros.
     * Registra informações de sucesso ou erro através do logger.
     */
    public void criarTabelas() {
        String sqlAmigos =
                "CREATE TABLE IF NOT EXISTS tb_amigos (" +
                        "id INTEGER PRIMARY KEY," +
                        "nome TEXT NOT NULL," +
                        "telefone INTEGER" +
                        ")";

        String sqlFerramentas =
                "CREATE TABLE IF NOT EXISTS tb_ferramentas (" +
                        "id INTEGER PRIMARY KEY," +
                        "nome TEXT NOT NULL," +
                        "marca TEXT," +
                        "custoAquisicao REAL" +
                        ")";

        try (Connection conn = getConexao();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlAmigos);
            stmt.execute(sqlFerramentas);
            logger.info("Tabelas verificadas/criadas com sucesso no SQLite.");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao criar tabelas no SQLite", e);
        }
    }

    // ------------------------------------------------------------
    //  MÉTODOS PARA AMIGOS
    // ------------------------------------------------------------

    /**
     * Retorna o maior ID presente na tabela tb_amigos.
     * 
     * @return int o maior ID encontrado, ou 0 se a tabela estiver vazia ou houver erro
     */
    public int maiorIDAmigos() {
        String sql = "SELECT MAX(id) AS id FROM tb_amigos";

        try (Connection conn = getConexao();
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(sql)) {

            return res.next() ? res.getInt("id") : 0;

        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * Busca e retorna todos os amigos cadastrados no banco de dados.
     * Limpa a lista atual antes de popular com novos dados.
     * 
     * @return ArrayList&lt;Amigos&gt; lista contendo todos os amigos cadastrados
     */
    public ArrayList<Amigos> getMinhaListaAmigos() {
        MinhaLista.clear();
        String sql = "SELECT id, nome, telefone FROM tb_amigos";

        try (Connection conn = getConexao();
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(sql)) {

            while (res.next()) {
                int id = res.getInt("id");
                String nome = res.getString("nome");
                int telefone = res.getInt("telefone");

                Amigos objeto = new Amigos(id, nome, telefone);
                
                MinhaLista.add(objeto);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar amigos: ", e);
        }
        return MinhaLista;
    }

    /**
     * Insere um novo amigo no banco de dados.
     * 
     * @param objeto Amigos objeto contendo os dados do amigo a ser inserido
     * @return boolean true se a inserção foi bem-sucedida
     * @throws IllegalArgumentException se houver erro durante a inserção
     */
    public boolean insertAmigosBD(Amigos objeto) {
        String sql = "INSERT INTO tb_amigos(id,nome,telefone) VALUES(?,?,?)";

        try (Connection conn = getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, objeto.getId());
            stmt.setString(2, objeto.getNome());
            stmt.setInt(3, objeto.getTelefone());
            stmt.execute();
            return true;

        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao inserir amigo!", e);
        }
    }
            
    /**
     * Remove um amigo do banco de dados com base no ID fornecido.
     * 
     * @param id int identificador único do amigo a ser removido
     * @return boolean true se pelo menos uma linha foi afetada (amigo deletado com sucesso)
     * @throws IllegalArgumentException se houver erro durante a exclusão
     */
    public boolean deleteAmigosBD(int id) {
        String sql = "DELETE FROM tb_amigos WHERE id = ?";

        try (Connection conn = getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao deletar amigo:", e);
        }
    }

    /**
     * Atualiza os dados de um amigo existente no banco de dados.
     * 
     * @param objeto Amigos objeto contendo os novos dados do amigo
     * @return boolean true se a atualização foi bem-sucedida
     * @throws IllegalArgumentException se houver erro durante a atualização
     */
    public boolean updateAmigosBD(Amigos objeto) {
        String sql = "UPDATE tb_amigos SET nome = ?, telefone = ? WHERE id = ?";

        try (Connection conn = getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getTelefone());
            stmt.setInt(3, objeto.getId());
            stmt.execute();
            return true;

        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao atualizar amigo!", e);
        }
    }

    /**
     * Carrega os dados de um amigo específico com base no ID fornecido.
     * 
     * @param id int identificador único do amigo a ser carregado
     * @return Amigos objeto contendo os dados do amigo, ou null se não encontrado
     * @throws IllegalArgumentException se houver erro durante a consulta
     */
    public Amigos carregaAmigos(int id) {
        String sql = "SELECT nome, telefone FROM tb_amigos WHERE id = ?";

        try (Connection conn = getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                return new Amigos(
                        id,
                        res.getString("nome"),
                        res.getInt("telefone")
                );
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao carregar amigo:", e);
        }

        return null;
    }

    // ------------------------------------------------------------
    //  MÉTODOS PARA FERRAMENTAS
    // ------------------------------------------------------------

    /**
     * Retorna o maior ID presente na tabela tb_ferramentas.
     * 
     * @return int o maior ID encontrado, ou 0 se a tabela estiver vazia ou houver erro
     */
    public int maiorIDFerramentas() {
        String sql = "SELECT MAX(id) AS id FROM tb_ferramentas";

        try (Connection conn = getConexao();
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(sql)) {

            return res.next() ? res.getInt("id") : 0;

        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * Busca e retorna todas as ferramentas cadastradas no banco de dados.
     * Limpa a lista atual antes de popular com novos dados.
     * 
     * @return ArrayList&lt;Ferramentas&gt; lista contendo todas as ferramentas cadastradas
     */
    public ArrayList<Ferramentas> getMinhaListaFerramentas() {
        MinhaListaFerramentas.clear();
        String sql = "SELECT id, nome, marca, custoAquisicao FROM tb_ferramentas";

        try (Connection conn = getConexao();
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(sql)) {

            while (res.next()) {
                Ferramentas f = new Ferramentas(
                        res.getInt("id"),
                        res.getString("nome"),
                        res.getString("marca"),
                        res.getDouble("custoAquisicao")
                );
                MinhaListaFerramentas.add(f);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar ferramentas: ", e);
        }
        return MinhaListaFerramentas;
    }

    /**
     * Insere uma nova ferramenta no banco de dados.
     * Valida os dados antes da inserção (nome, marca e custo não podem ser vazios/nulos/zero).
     * 
     * @param obj Ferramentas objeto contendo os dados da ferramenta a ser inserida
     * @return boolean true se a inserção foi bem-sucedida
     * @throws IllegalArgumentException se os dados forem inválidos ou houver erro na inserção
     */
    public boolean insertFerramentasBD(Ferramentas obj) {
        String sql = "INSERT INTO tb_ferramentas(id,nome,marca,custoAquisicao) VALUES(?,?,?,?)";

        if (obj.getNome()== null || obj.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo/vazio");
        }
        
        if (obj.getMarca()== null || obj.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("Marca não pode ser nulo/vazio");
        }
        
        if (obj.getCustoAquisicao()< 0 || obj.getCustoAquisicao() == 0) {
            throw new IllegalArgumentException("Preço não pode ser nulo/vazio");
        }
        
        try (Connection conn = getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, obj.getId());
            stmt.setString(2, obj.getNome());
            stmt.setString(3, obj.getMarca());
            stmt.setDouble(4, obj.getCustoAquisicao());
            stmt.execute();
            return true;

        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao inserir ferramenta!", e);
        }
    }

    /**
     * Remove uma ferramenta do banco de dados com base no ID fornecido.
     * 
     * @param id int identificador único da ferramenta a ser removida
     * @return boolean true se pelo menos uma linha foi afetada (ferramenta deletada com sucesso)
     * @throws IllegalStateException se houver erro durante a exclusão
     */
    public boolean deleteFerramentasBD(int id) {
        String sql = "DELETE FROM tb_ferramentas WHERE id = ?";

        try (Connection conn = getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao carregar ferramenta com id " + id, e);
        }
    }

    /**
     * Atualiza os dados de uma ferramenta existente no banco de dados.
     * Valida os dados antes da atualização (nome, marca e custo não podem ser vazios/nulos/zero).
     * 
     * @param obj Ferramentas objeto contendo os novos dados da ferramenta
     * @return boolean true se a atualização foi bem-sucedida
     * @throws IllegalArgumentException se os dados forem inválidos
     * @throws IllegalStateException se houver erro durante a atualização
     */
    public boolean updateFerramentasBD(Ferramentas obj) {
        String sql = "UPDATE tb_ferramentas SET nome = ?, marca = ?, custoAquisicao = ? WHERE id = ?";
        
        if (obj.getNome()== null || obj.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo/vazio");
        }
        
        if (obj.getMarca()== null || obj.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("Marca não pode ser nulo/vazio");
        }
        
        if (obj.getCustoAquisicao()< 0 || obj.getCustoAquisicao() == 0) {
            throw new IllegalArgumentException("Preço não pode ser nulo/vazio");
        }

        try (Connection conn = getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getMarca());
            stmt.setDouble(3, obj.getCustoAquisicao());
            stmt.setInt(4, obj.getId());
            stmt.execute();
            return true;

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar ferramenta!", e);
        }
    }

    /**
     * Carrega os dados de uma ferramenta específica com base no ID fornecido.
     * 
     * @param id int identificador único da ferramenta a ser carregada
     * @return Ferramentas objeto contendo os dados da ferramenta, ou null se não encontrada
     * @throws IllegalStateException se houver erro durante a consulta
     */
    public Ferramentas carregaFerramentas(int id) {
        String sql = "SELECT nome, marca, custoAquisicao FROM tb_ferramentas WHERE id = ?";

        try (Connection conn = getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                return new Ferramentas(
                        id,
                        res.getString("nome"),
                        res.getString("marca"),
                        res.getDouble("custoAquisicao")
                );
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao carregar ferramenta: " + e.getMessage());
        }
        return null;
    }
}