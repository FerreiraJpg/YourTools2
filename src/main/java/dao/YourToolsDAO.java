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

public class YourToolsDAO {

    public static final ArrayList<Amigos> MinhaLista = new ArrayList<>();
    public static final ArrayList<Ferramentas> MinhaListaFerramentas = new ArrayList<>();
    Logger logger = Logger.getLogger(getClass().getName());


    public YourToolsDAO() {
        criarTabelas();
    }

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
    //  AMIGOS
    // ------------------------------------------------------------

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
    //  FERRAMENTAS
    // ------------------------------------------------------------

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
