package DAO;

import Model.Amigos;
import Model.Ferramentas;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class YourToolsDAO {

    public static ArrayList<Amigos> MinhaLista = new ArrayList<Amigos>();

    public YourToolsDAO() {
    
    criarTabelas(); 
}

    public int maiorIDAmigos() throws SQLException {

        int maiorID = 0;
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT MAX(id) id FROM tb_amigos");
            res.next();
            maiorID = res.getInt("id");

            stmt.close();

        } catch (SQLException ex) {
        }

        return maiorID;
    }

    public Connection getConexao() {
        try {

            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:db_yourtools.db";

            Connection connection = DriverManager.getConnection(url);
            System.out.println("Conectado ao SQLite com sucesso!");
            return connection;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver SQLite não encontrado! Verifique o pom.xml.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados SQLite!", e);
        }
    }

    
    public void criarTabelas() {
        String sqlAmigos = "CREATE TABLE IF NOT EXISTS tb_amigos ("
                + "id INTEGER PRIMARY KEY,"
                + "nome TEXT NOT NULL,"
                + "telefone INTEGER"
                + ")";

        String sqlFerramentas = "CREATE TABLE IF NOT EXISTS tb_ferramentas ("
                + "id INTEGER PRIMARY KEY,"
                + "nome TEXT NOT NULL,"
                + "marca TEXT,"
                + "custoAquisicao REAL"
                + ")";

        try (Connection conn = this.getConexao(); Statement stmt = conn.createStatement()) {

            stmt.execute(sqlAmigos);
            stmt.execute(sqlFerramentas);
            System.out.println("Tabelas verificadas/criadas com sucesso no SQLite.");

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas no SQLite: " + e.getMessage());
        }
    }
// -----------------------------------------------------------------------
//  -- retorna a Lista de Amigos -- 
// -----------------------------------------------------------------------

    public ArrayList getMinhaListaAmigos() {

        MinhaLista.clear(); // Limpa nosso ArrayList

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_amigos");
            while (res.next()) {

                int id = res.getInt("id");
                String nome = res.getString("nome");
                int telefone = res.getInt("telefone");

                Amigos objeto = new Amigos(id, nome, telefone);

                MinhaLista.add(objeto);
            }

            stmt.close();

        } catch (SQLException ex) {
        }

        return MinhaLista;
    }
// -----------------------------------------------------------------------
//  -- Cadastrar um novo Amigo -- 
// -----------------------------------------------------------------------

    public boolean InsertAmigosBD(Amigos objeto) {
        String sql = "INSERT INTO tb_amigos(id,nome,telefone) VALUES(?,?,?)";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, objeto.getId());
            stmt.setString(2, objeto.getNome());
            stmt.setInt(3, objeto.getTelefone());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    // -----------------------------------------------------------------------
//  -- Deletar um Amigo especifico pelo seu campo ID -- 
// -----------------------------------------------------------------------    
    public boolean DeleteAmigosBD(int id) {
        try {
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM tb_amigos WHERE id = " + id);
            stmt.close();

        } catch (SQLException erro) {
        }

        return true;
    }
// -----------------------------------------------------------------------
//  -- Edita um Amigo pelo campo ID -- 
// -----------------------------------------------------------------------

    public boolean UpdateAmigosBD(Amigos objeto) {

        String sql = "UPDATE tb_amigos set nome = ?, telefone = ? WHERE id = ?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getTelefone());
            stmt.setInt(3, objeto.getId());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    public Amigos carregaAmigos(int id) {

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_amigos WHERE id = " + id);
            if (res.next()) {
                Amigos objeto = new Amigos();
                objeto.setId(id);
                objeto.setNome(res.getString("nome"));
                objeto.setTelefone(res.getInt("telefone"));
                stmt.close();
                return objeto;
            } else {
                stmt.close();
                return null; // Return null if no amigo is found
            }

        } catch (SQLException erro) {
            System.err.println("Erro ao carregar amigo: " + erro.getMessage());
            return null;
        }
    }

    // -----------------------------------------------------------------------
    //  -- ArrayList das Ferramentas -- 
    // -----------------------------------------------------------------------
    public static ArrayList<Ferramentas> MinhaListaFerramentas = new ArrayList<Ferramentas>();

    public int maiorIDFerramentas() throws SQLException {

        int maiorID = 0;
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT MAX(id) id FROM tb_ferramentas");
            res.next();
            maiorID = res.getInt("id");

            stmt.close();

        } catch (SQLException ex) {
        }

        return maiorID;
    }

    // -----------------------------------------------------------------------
//  -- retorna a Lista de Ferramentas -- 
// -----------------------------------------------------------------------
    public ArrayList getMinhaListaFerramentas() {

        MinhaListaFerramentas.clear(); // Limpa nosso ArrayList

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramentas");
            while (res.next()) {

                int id = res.getInt("id");
                String nome = res.getString("nome");
                String marca = res.getString("marca");
                double custoAquisicao = res.getDouble("custoAquisicao");

                Ferramentas objeto = new Ferramentas(id, nome, marca, custoAquisicao);

                MinhaListaFerramentas.add(objeto);
            }

            stmt.close();

        } catch (SQLException ex) {
        }

        return MinhaListaFerramentas;
    }

    // -----------------------------------------------------------------------
//  -- Cadastrar uma nova Ferramenta -- 
// -----------------------------------------------------------------------
    public boolean InsertFerramentasBD(Ferramentas objeto) {
        String sql = "INSERT INTO tb_ferramentas(id,nome,marca,custoAquisicao) VALUES(?,?,?,?)";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, objeto.getId());
            stmt.setString(2, objeto.getNome());
            stmt.setString(3, objeto.getMarca());
            stmt.setDouble(4, objeto.getCustoAquisicao());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    // -----------------------------------------------------------------------
//  -- Deletar uma ferramenta especifica pelo seu campo ID -- 
// -----------------------------------------------------------------------    
    public boolean DeleteFerramentasBD(int id) {
        try {
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM tb_ferramentas WHERE id = " + id);
            stmt.close();

        } catch (SQLException erro) {
        }

        return true;
    }

    // -----------------------------------------------------------------------
//  -- Edita uma Ferramenta pelo campo ID -- 
// -----------------------------------------------------------------------
    public boolean UpdateFerramentasBD(Ferramentas objeto) {

        String sql = "UPDATE tb_ferramentas set nome = ?  ,telefone = ? WHERE id = ?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, objeto.getId());
            stmt.setString(2, objeto.getNome());
            stmt.setString(3, objeto.getMarca());
            stmt.setDouble(4, objeto.getCustoAquisicao());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    public Ferramentas carregaFerramentas(int id) {

        Ferramentas objeto = new Ferramentas();
        objeto.setId(id);

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramentas WHERE id = " + id);
            res.next();

            objeto.setNome(res.getString("nome"));
            objeto.setMarca(res.getString("marca"));
            objeto.setCustoAquisicao(res.getInt("custo de aquisicao"));

            stmt.close();

        } catch (SQLException erro) {
        }
        return objeto;
    }

}
