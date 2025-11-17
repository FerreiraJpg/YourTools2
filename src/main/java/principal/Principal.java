
package principal;

import view.TelaInicial;

/**
 * Classe principal que inicia a aplicação YourTools.
 * Contém o método main que é o ponto de entrada da aplicação.
 * 
 * <p>Esta classe é responsável por inicializar a interface gráfica,
 * criando e exibindo a tela inicial do sistema.</p>
 * 
 * @author YourTools Team
 * @version 1.0
 * @since 2025
 */
public class Principal {

    /**
     * Método principal que inicia a aplicação.
     * Cria uma instância da TelaInicial e a torna visível.
     * 
     * @param args String[] argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        TelaInicial objetotela = new TelaInicial();
        objetotela.setVisible(true);
    }
}