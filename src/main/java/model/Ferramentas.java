package model;

import dao.YourToolsDAO;
import java.util.ArrayList;

/**
 * Classe que representa uma Ferramenta no sistema YourTools.
 * Implementa operações de persistência através do YourToolsDAO.
 * 
 * <p>Esta classe encapsula a lógica de negócio relacionada às ferramentas,
 * incluindo validações e comunicação com a camada de dados.</p>
 * 
 * @author YourTools Team
 * @version 1.0
 * @since 2025
 * @see YourToolsDAO
 */
public class Ferramentas {

    /**
     * Identificador único da ferramenta.
     */
    private int id;
    
    /**
     * Nome da ferramenta.
     */
    private String nome;
    
    /**
     * Marca ou fabricante da ferramenta.
     */
    private String marca;
    
    /**
     * Custo de aquisição da ferramenta em valor monetário.
     */
    private double custoAquisicao;
    
    /**
     * Objeto de acesso a dados (DAO) utilizado para persistência.
     * É inicializado automaticamente nos construtores.
     */
    private final YourToolsDAO dao;

    /**
     * Construtor padrão que cria um objeto Ferramentas vazio.
     * Inicializa o DAO automaticamente.
     */
    public Ferramentas() {
        this.dao = new YourToolsDAO();
    }

    /**
     * Construtor utilizado para injeção de dependência em testes.
     * Permite passar um DAO mockado para testes unitários.
     * 
     * @param dao YourToolsDAO objeto DAO a ser utilizado (pode ser mock)
     */
    public Ferramentas(YourToolsDAO dao) {
        this.dao = dao;
    }

    /**
     * Construtor parametrizado que cria um objeto Ferramentas com dados específicos.
     * Inicializa todos os atributos e o DAO.
     * 
     * @param id int identificador único da ferramenta
     * @param nome String nome da ferramenta
     * @param marca String marca ou fabricante da ferramenta
     * @param custoAquisicao double custo de aquisição da ferramenta
     */
    public Ferramentas(int id, String nome, String marca, double custoAquisicao) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.custoAquisicao = custoAquisicao;
        this.dao = new YourToolsDAO();
    }

    /**
     * Obtém o ID da ferramenta.
     * 
     * @return int identificador único da ferramenta
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID da ferramenta.
     * 
     * @param id int novo identificador da ferramenta
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o nome da ferramenta.
     * 
     * @return String nome da ferramenta
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da ferramenta.
     * 
     * @param nome String novo nome da ferramenta
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a marca da ferramenta.
     * 
     * @return String marca ou fabricante da ferramenta
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Define a marca da ferramenta.
     * 
     * @param marca String nova marca da ferramenta
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtém o custo de aquisição da ferramenta.
     * 
     * @return double valor do custo de aquisição
     */
    public double getCustoAquisicao() {
        return custoAquisicao;
    }

    /**
     * Define o custo de aquisição da ferramenta.
     * 
     * @param custoAquisicao double novo valor do custo de aquisição
     */
    public void setCustoAquisicao(double custoAquisicao) {
        this.custoAquisicao = custoAquisicao;
    }

    /**
     * Retorna uma representação em String dos dados da ferramenta.
     * Sobrescreve o método toString() para fornecer formatação específica.
     * 
     * @return String formatada contendo ID, Nome, Marca e Custo de Aquisição
     */
    @Override
    public String toString() {
        return "\n ID: " + this.getId()
                + "\n Nome: " + this.getNome()
                + "\n Marca: " + this.getMarca()
                + "\n Custo de Aquisição: " + this.getCustoAquisicao()
                + "\n -----------";
    }

    /**
     * Retorna a lista de todas as ferramentas cadastradas no banco de dados.
     * 
     * @return ArrayList&lt;Ferramentas&gt; lista contendo todos os objetos Ferramentas cadastrados
     */
    public ArrayList<Ferramentas> getMinhaListaFerramentas() {
        return dao.getMinhaListaFerramentas();
    }

    /**
     * Cadastra uma nova ferramenta no banco de dados.
     * Gera automaticamente um novo ID sequencial.
     * 
     * <p>As validações são realizadas na camada DAO:</p>
     * <ul>
     *   <li>Nome não pode ser nulo ou vazio</li>
     *   <li>Marca não pode ser nula ou vazia</li>
     *   <li>Custo de aquisição deve ser maior que zero</li>
     * </ul>
     * 
     * @param nome String nome da ferramenta a ser cadastrada
     * @param marca String marca da ferramenta
     * @param custoAquisicao double custo de aquisição da ferramenta
     * @return boolean true se o cadastro foi bem-sucedido
     * @throws IllegalArgumentException se os dados forem inválidos (lançado pelo DAO)
     */
    public boolean insertFerramentasBD(String nome, String marca, double custoAquisicao) {
        int novoid = this.maiorIDFerramentas() + 1;
        Ferramentas objeto = new Ferramentas(novoid, nome, marca, custoAquisicao);
        dao.insertFerramentasBD(objeto);
        return true;
    }

    /**
     * Remove uma ferramenta específica do banco de dados com base no ID.
     * 
     * @param id int identificador único da ferramenta a ser removida
     * @return boolean true se a exclusão foi bem-sucedida
     */
    public boolean deleteFerramentasBD(int id) {
        dao.deleteFerramentasBD(id);
        return true;
    }

    /**
     * Atualiza os dados de uma ferramenta existente no banco de dados.
     * 
     * <p>As validações são realizadas na camada DAO:</p>
     * <ul>
     *   <li>Nome não pode ser nulo ou vazio</li>
     *   <li>Marca não pode ser nula ou vazia</li>
     *   <li>Custo de aquisição deve ser maior que zero</li>
     * </ul>
     * 
     * @param id int identificador único da ferramenta a ser atualizada
     * @param nome String novo nome da ferramenta
     * @param marca String nova marca da ferramenta
     * @param custoAquisicao double novo custo de aquisição
     * @return boolean true se a atualização foi bem-sucedida
     * @throws IllegalArgumentException se os dados forem inválidos (lançado pelo DAO)
     */
    public boolean updateFerramentasBD(int id, String nome, String marca, double custoAquisicao) {
        Ferramentas objeto = new Ferramentas(id, nome, marca, custoAquisicao);
        dao.updateFerramentasBD(objeto);
        return true;
    }

    /**
     * Carrega os dados de uma ferramenta específica do banco de dados.
     * 
     * @param id int identificador único da ferramenta a ser carregada
     * @return Ferramentas objeto contendo os dados da ferramenta, ou null se não encontrada
     */
    public Ferramentas carregaFerramentas(int id) {
        return dao.carregaFerramentas(id);
    }

    /**
     * Retorna o maior ID de ferramenta presente no banco de dados.
     * Utilizado para gerar novos IDs sequenciais ao cadastrar ferramentas.
     * 
     * @return int o maior ID encontrado na tabela de ferramentas
     */
    public int maiorIDFerramentas() {
        return dao.maiorIDFerramentas();
    }
}