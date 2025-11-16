package model;

import dao.YourToolsDAO;
import java.util.ArrayList;

public class Ferramentas {

    // Atributos
    private int id;
    private String nome;
    private String marca;
    private double custoAquisicao;
    private final YourToolsDAO dao;

    // Método Construtor de Objeto Vazio
    public Ferramentas() {
        this.dao = new YourToolsDAO(); // inicializado não importa em qual construtor
    }

    // Constructor para testes
    public Ferramentas(YourToolsDAO dao) {
        this.dao = dao;
    }

    // Método Construtor usando também o construtor da SUPERCLASSE
    public Ferramentas(int id, String nome, String marca, double custoAquisicao) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.custoAquisicao = custoAquisicao;
        this.dao = new YourToolsDAO(); // inicializado não importa em qual construtor
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

    // Override necessário para poder retornar os dados de Pessoa no toString para
    // aluno.
    @Override
    public String toString() {
        return "\n ID: " + this.getId()
                + "\n Nome: " + this.getNome()
                + "\n Marca: " + this.getMarca()
                + "\n Custo de Aquisição: " + this.getCustoAquisicao()
                + "\n -----------";
    }

    /*
     * ABAIXO OS MÉTODOS PARA USO JUNTO COM O DAO
     * SIMULANDO A ESTRUTURA EM CAMADAS PARA USAR COM BANCOS DE DADOS.
     * 
     */

      // Retorna a Lista de Ferramentas(objetos)
    public ArrayList<Ferramentas> getMinhaListaFerramentas() {
        return dao.getMinhaListaFerramentas();
    }

    // Cadastra nova Ferramentas
    public boolean insertFerramentasBD(String nome, String marca, double custoAquisicao) {
        int novoid = this.maiorIDFerramentas() + 1;
        Ferramentas objeto = new Ferramentas(novoid, nome, marca, custoAquisicao);
        dao.insertFerramentasBD(objeto);
        return true;

    }

    // Deleta uma Ferramentas específico pelo seu campo ID
    public boolean deleteFerramentasBD(int id) {
        dao.deleteFerramentasBD(id);
        return true;
    }

    // Edita uma Ferramentas específico pelo seu campo ID
    public boolean updateFerramentasBD(int id, String nome, String marca, double custoAquisicao) {
        Ferramentas objeto = new Ferramentas(id, nome, marca, custoAquisicao);
        dao.updateFerramentasBD(objeto);
        return true;
    }

    // carrega dados de um aluno específico pelo seu ID
    public Ferramentas carregaFerramentas(int id) {
        return dao.carregaFerramentas(id); //Retorna o resultado do DAO
    }

    // retorna o maior ID da nossa base de dados
    public int maiorIDFerramentas() {
        return dao.maiorIDFerramentas();
    }
    
}
