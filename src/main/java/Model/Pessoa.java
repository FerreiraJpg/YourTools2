package Model;

public abstract class Pessoa {

    // Atributos
    private int id;
    private String nome;
    private int telefone;

    // M�todo Construtor de Objeto Vazio
    public Pessoa() {
    }

    // M�todo Construtor de Objeto, inserindo dados
    public Pessoa(int id, String nome, int telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    // M�todos GET e SET
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

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

}
