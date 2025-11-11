package Model;

import java.util.*;
import DAO.YourToolsDAO;
import java.sql.SQLException;

public class Ferramentas {

    // Atributos
    private int id;
    private String nome;
    private String marca;
    private double custoAquisicao;
    private final YourToolsDAO dao; 

    // M�todo Construtor de Objeto Vazio
    public Ferramentas() {
        this.dao = new YourToolsDAO(); // inicializado n�o importa em qual construtor
    }

    // M�todo Construtor de Objeto, inserindo dados
//    public Amigos(String curso, int fase) {;
//        this.curso = curso;
//        this.fase = fase;
//        this.dao = new YourToolsDAO(); // inicializado n�o importa em qual construtor
//    }

    // M�todo Construtor usando tamb�m o construtor da SUPERCLASSE
    public Ferramentas(int id, String nome, String marca, double custoAquisicao) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.custoAquisicao = custoAquisicao;
        this.dao = new YourToolsDAO(); // inicializado n�o importa em qual construtor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getCustoAquisicao() {
        return custoAquisicao;
    }

    public void setCustoAquisicao(double custoAquisicao) {
        this.custoAquisicao = custoAquisicao;
    }
    
    

    // Override necess�rio para poder retornar os dados de Pessoa no toString para aluno.
    @Override
    public String toString() {
        return "\n ID: " + this.getId()
                + "\n Nome: " + this.getNome()
                + "\n Marca: " + this.getMarca()
                + "\n Custo de Aquisição: " + this.getCustoAquisicao()
                + "\n -----------";
    }

    /*
        ABAIXO OS M�TODOS PARA USO JUNTO COM O DAO
        SIMULANDO A ESTRUTURA EM CAMADAS PARA USAR COM BANCOS DE DADOS.
    
     */
    // Retorna a Lista de Ferramentas(objetos)
    public ArrayList getMinhaListaFerramentas() {
        //return AlunoDAO.MinhaLista;
        return dao.getMinhaListaFerramentas();
    }

    // Cadastra nova Ferramentas
//    public boolean InsertAlunoBD(String curso, int fase, String nome, int idade) {
    public boolean InsertFerramentasBD(String nome, String marca, double custoAquisicao) throws SQLException {
        int id = this.maiorIDFerramentas() + 1;
        Ferramentas objeto = new Ferramentas(id, nome, marca, custoAquisicao);
//        AlunoDAO.MinhaLista.add(objeto);
        dao.InsertFerramentasBD(objeto);
        return true;

    }

    // Deleta uma Ferramentas espec�fico pelo seu campo ID
    public boolean DeleteFerramentasBD(int id) {
//        int indice = this.procuraIndice(id);
//        AlunoDAO.MinhaLista.remove(indice);
        dao.DeleteFerramentasBD(id);
        return true;
    }

    // Edita uma Ferramentas espec�fico pelo seu campo ID
    public boolean UpdateFerramentasBD(int id, String nome, String marca, double custoAquisicao) {
        Ferramentas objeto = new Ferramentas(id, nome, marca, custoAquisicao);
//        int indice = this.procuraIndice(id);
//        AlunoDAO.MinhaLista.set(indice, objeto);
        dao.UpdateFerramentasBD(objeto);
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
    public Ferramentas carregaFerramentas(int id) {
//        int indice = this.procuraIndice(id);
//        return AlunoDAO.MinhaLista.get(indice);
        dao.carregaFerramentas(id);
        return null;
    }
    
    // retorna o maior ID da nossa base de dados
        public int maiorIDFerramentas() throws SQLException{
//    public int maiorID(){
//        return AlunoDAO.maiorID();
        return dao.maiorIDFerramentas();
    }   
}

