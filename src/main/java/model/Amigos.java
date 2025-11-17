package model;

import dao.YourToolsDAO;
import java.util.ArrayList;

/**
 * Classe que representa um Amigo no sistema YourTools.
 * Herda da classe abstrata Pessoa e implementa operações de persistência
 * através do YourToolsDAO.
 * 
 * <p>Esta classe encapsula a lógica de negócio relacionada aos amigos,
 * incluindo validações e comunicação com a camada de dados.</p>
 * 
 * @author YourTools Team
 * @version 1.0
 * @since 2025
 * @see Pessoa
 * @see YourToolsDAO
 */
public class Amigos extends Pessoa {

    /**
     * Objeto de acesso a dados (DAO) utilizado para persistência.
     * É inicializado automaticamente nos construtores.
     */
    private final YourToolsDAO dao; 

    /**
     * Construtor padrão que cria um objeto Amigos vazio.
     * Inicializa o DAO automaticamente.
     */
    public Amigos() {
        this.dao = new YourToolsDAO(); 
    }

    /**
     * Construtor parametrizado que cria um objeto Amigos com dados específicos.
     * Utiliza o construtor da superclasse Pessoa e inicializa o DAO.
     * 
     * @param id int identificador único do amigo
     * @param nome String nome completo do amigo
     * @param telefone int número de telefone do amigo
     */
    public Amigos(int id, String nome, int telefone) {
        super(id, nome, telefone);
        this.dao = new YourToolsDAO(); 
    }

    /**
     * Retorna uma representação em String dos dados do amigo.
     * Sobrescreve o método toString() para fornecer formatação específica.
     * 
     * @return String formatada contendo ID, Nome e Telefone do amigo
     */
    @Override
    public String toString() {
        return "\n ID: " + this.getId()
                + "\n Nome: " + this.getNome()
                + "\n Telefone: " + this.getTelefone()
                + "\n -----------";
    }

    /**
     * Retorna a lista de todos os amigos cadastrados no banco de dados.
     * 
     * @return ArrayList&lt;Amigos&gt; lista contendo todos os objetos Amigos cadastrados
     */
    public ArrayList<Amigos> getMinhaListaAmigos() {
        return dao.getMinhaListaAmigos();
    }

    /**
     * Cadastra um novo amigo no banco de dados.
     * Realiza validações antes de inserir os dados.
     * 
     * <p>Validações realizadas:</p>
     * <ul>
     *   <li>Nome não pode ser nulo ou vazio</li>
     *   <li>Telefone deve ser maior que zero</li>
     * </ul>
     * 
     * @param nome String nome do amigo a ser cadastrado
     * @param telefone int número de telefone do amigo
     * @return boolean true se o cadastro foi bem-sucedido, false se os dados forem inválidos
     */
    public boolean insertAmigosBD(String nome, int telefone) {
        if (nome == null || nome.trim().isEmpty() || telefone <= 0) {
            return false;
        }
        int id = this.maiorIDAmigos() + 1;
        Amigos objeto = new Amigos(id, nome, telefone);
        dao.insertAmigosBD(objeto);
        return true;
    }

    /**
     * Remove um amigo específico do banco de dados com base no ID.
     * 
     * @param id int identificador único do amigo a ser removido
     * @return boolean true se a exclusão foi bem-sucedida
     */
    public boolean deleteAmigosBD(int id) {
        dao.deleteAmigosBD(id);
        return true;
    }

    /**
     * Atualiza os dados de um amigo existente no banco de dados.
     * Realiza validações antes de atualizar os dados.
     * 
     * <p>Validações realizadas:</p>
     * <ul>
     *   <li>Nome não pode ser nulo ou vazio</li>
     *   <li>Telefone deve ser maior que zero</li>
     * </ul>
     * 
     * @param id int identificador único do amigo a ser atualizado
     * @param nome String novo nome do amigo
     * @param telefone int novo número de telefone
     * @return boolean true se a atualização foi bem-sucedida, false se os dados forem inválidos
     */
    public boolean updateAmigosBD(int id, String nome, int telefone) {
        if (nome == null || nome.trim().isEmpty() || telefone <= 0) {
            return false;
        }
        Amigos objeto = new Amigos(id, nome, telefone);
        dao.updateAmigosBD(objeto);
        return true;
    }

    /**
     * Carrega os dados de um amigo específico do banco de dados.
     * 
     * @param id int identificador único do amigo a ser carregado
     * @return Amigos objeto contendo os dados do amigo, ou null se não encontrado
     */
    public Amigos carregaAmigos(int id) {
        return dao.carregaAmigos(id);
    }
    
    /**
     * Retorna o maior ID de amigo presente no banco de dados.
     * Utilizado para gerar novos IDs sequenciais ao cadastrar amigos.
     * 
     * @return int o maior ID encontrado na tabela de amigos
     */
    public int maiorIDAmigos() {
        return dao.maiorIDAmigos();
    }   
}