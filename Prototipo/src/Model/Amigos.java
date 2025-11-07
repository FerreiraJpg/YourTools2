package Model;

import java.util.*;
import DAO.YourToolsDAO;
import java.sql.SQLException;

public class Amigos extends Pessoa {

    // Atributos
    private final YourToolsDAO dao; 

    // M�todo Construtor de Objeto Vazio
    public Amigos() {
        this.dao = new YourToolsDAO(); // inicializado n�o importa em qual construtor
    }

    // M�todo Construtor de Objeto, inserindo dados
//    public Amigos(String curso, int fase) {;
//        this.curso = curso;
//        this.fase = fase;
//        this.dao = new YourToolsDAO(); // inicializado n�o importa em qual construtor
//    }

    // M�todo Construtor usando tamb�m o construtor da SUPERCLASSE
    public Amigos(int id, String nome, int telefone) {
        super(id, nome, telefone);
        this.dao = new YourToolsDAO(); // inicializado n�o importa em qual construtor
    }

    // Override necess�rio para poder retornar os dados de Pessoa no toString para aluno.
    @Override
    public String toString() {
        return "\n ID: " + this.getId()
                + "\n Nome: " + this.getNome()
                + "\n Telefone: " + this.getTelefone()
                + "\n -----------";
    }

    /*
        ABAIXO OS M�TODOS PARA USO JUNTO COM O DAO
        SIMULANDO A ESTRUTURA EM CAMADAS PARA USAR COM BANCOS DE DADOS.
    
     */
    // Retorna a Lista de Alunos(objetos)
    public ArrayList getMinhaListaAmigos() {
        //return AlunoDAO.MinhaLista;
        return dao.getMinhaListaAmigos();
    }

    // Cadastra novo aluno
//    public boolean InsertAlunoBD(String curso, int fase, String nome, int idade) {
    public boolean InsertAmigosBD(String nome, int telefone) throws SQLException {
        int id = this.maiorIDAmigos() + 1;
        Amigos objeto = new Amigos(id, nome, telefone);
//        AlunoDAO.MinhaLista.add(objeto);
        dao.InsertAmigosBD(objeto);
        return true;

    }

    // Deleta um aluno espec�fico pelo seu campo ID
    public boolean DeleteAmigosBD(int id) {
//        int indice = this.procuraIndice(id);
//        AlunoDAO.MinhaLista.remove(indice);
        dao.DeleteAmigosBD(id);
        return true;
    }

    // Edita um aluno espec�fico pelo seu campo ID
    public boolean UpdateAmigosBD(int id, String nome, int telefone) {
        Amigos objeto = new Amigos(id, nome, telefone);
//        int indice = this.procuraIndice(id);
//        AlunoDAO.MinhaLista.set(indice, objeto);
        dao.UpdateAmigosBD(objeto);
        return true;
    }

    // procura o INDICE de objeto da MinhaLista que contem o ID enviado.
//    private int procuraIndice(int id) {
//        int indice = -1;
//        for (int i = 0; i < AlunoDAO.MinhaLista.size(); i++) {
//            if (AlunoDAO.MinhaLista.get(i).getId() == id) {
//                indice = i;
//            }
//        }
//        return indice;
//    }

    // carrega dados de um aluno espec�fico pelo seu ID
    public Amigos carregaAmigos(int id) {
//        int indice = this.procuraIndice(id);
//        return AlunoDAO.MinhaLista.get(indice);
        dao.carregaAmigos(id);
        return null;
    }
    
    // retorna o maior ID da nossa base de dados
        public int maiorIDAmigos() throws SQLException{
//    public int maiorID(){
//        return AlunoDAO.maiorID();
        return dao.maiorIDAmigos();
    }   
}
