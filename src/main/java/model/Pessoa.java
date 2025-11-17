package model;

/**
 * Classe abstrata que representa uma Pessoa no sistema YourTools.
 * Serve como classe base para entidades que possuem atributos comuns
 * de pessoas, como ID, nome e telefone.
 * 
 * <p>Esta classe não pode ser instanciada diretamente, devendo ser
 * estendida por outras classes concretas como Amigos.</p>
 * 
 * @author YourTools Team
 * @version 1.0
 * @since 2025
 */
public abstract class Pessoa {

    /**
     * Identificador único da pessoa.
     */
    private int id;
    
    /**
     * Nome completo da pessoa.
     */
    private String nome;
    
    /**
     * Número de telefone da pessoa.
     */
    private int telefone;

    /**
     * Construtor padrão que cria um objeto Pessoa vazio.
     * Inicializa os atributos com valores padrão.
     */
    public Pessoa() {
    }

    /**
     * Construtor parametrizado que cria um objeto Pessoa com dados específicos.
     * 
     * @param id int identificador único da pessoa
     * @param nome String nome completo da pessoa
     * @param telefone int número de telefone da pessoa
     */
    public Pessoa(int id, String nome, int telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    /**
     * Obtém o ID da pessoa.
     * 
     * @return int identificador único da pessoa
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID da pessoa.
     * 
     * @param id int novo identificador da pessoa
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o nome da pessoa.
     * 
     * @return String nome completo da pessoa
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da pessoa.
     * 
     * @param nome String novo nome da pessoa
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o telefone da pessoa.
     * 
     * @return int número de telefone da pessoa
     */
    public int getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone da pessoa.
     * 
     * @param telefone int novo número de telefone
     */
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
}