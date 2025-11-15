package Model;

import DAO.YourToolsDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class Amigos extends Pessoa {

    // Atributos
    private final YourToolsDAO dao; 

    // Método Construtor de Objeto Vazio
    public Amigos() {
        this.dao = new YourToolsDAO(); // inicializado não importa em qual construtor
    }

    // Método Construtor usando também o construtor da SUPERCLASSE
    public Amigos(int id, String nome, int telefone) {
        super(id, nome, telefone);
        this.dao = new YourToolsDAO(); // inicializado não importa em qual construtor
    }

    // Override necessário para poder retornar os dados de Pessoa no toString para aluno.
    @Override
    public String toString() {
        return "\n ID: " + this.getId()
                + "\n Nome: " + this.getNome()
                + "\n Telefone: " + this.getTelefone()
                + "\n -----------";
    }


    // Retorna a Lista de Alunos(objetos)
    public ArrayList getMinhaListaAmigos() {
        return dao.getMinhaListaAmigos();
    }

    // Cadastra novo aluno
    public boolean InsertAmigosBD(String nome, int telefone) throws SQLException {
        if (nome == null || nome.trim().isEmpty() || telefone <= 0) {
            return false;
        }
        int id = this.maiorIDAmigos() + 1;
        Amigos objeto = new Amigos(id, nome, telefone);
        dao.InsertAmigosBD(objeto);
        return true;

    }

    // Deleta um aluno específico pelo seu campo ID
    public boolean DeleteAmigosBD(int id) {
//        int indice = this.procuraIndice(id);
//        AlunoDAO.MinhaLista.remove(indice);
        dao.DeleteAmigosBD(id);
        return true;
    }

    // Edita um aluno específico pelo seu campo ID
    public boolean UpdateAmigosBD(int id, String nome, int telefone) {
        if (nome == null || nome.trim().isEmpty() || telefone <= 0) {
            return false;
        }
        Amigos objeto = new Amigos(id, nome, telefone);
        dao.UpdateAmigosBD(objeto);
        return true;
    }

    // carrega dados de um aluno específico pelo seu ID
    public Amigos carregaAmigos(int id) {
        return dao.carregaAmigos(id);
    }
    
    // retorna o maior ID da nossa base de dados
        public int maiorIDAmigos() throws SQLException{
        return dao.maiorIDAmigos();
    }   
}
