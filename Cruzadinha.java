
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Classe principal da aplicação que herda de JFrame e implementa ActionListener para lidar com eventos de clique
public class Cruzadinha extends JFrame implements ActionListener {

    private static final int GRID_SIZE = 16;  // Tamanho da grade, ajustado para suportar palavras maiores
    private JTextField[][] grid = new JTextField[GRID_SIZE][GRID_SIZE];  // Matriz de campos de texto que compõem a cruzadinha

    // Palavras a serem adivinhadas e suas respectivas dicas
    private String[] words = {"RM", "JIN", "SUGA", "JHOPE", "JIMIN", "V", "JK", "ARMY", "DYNAMITE", "BUTTER"};
    private String[] hints = {
        "0. Outra música de sucesso do BTS, cheia de energia e charme (Horizontal)",
        "1. Líder do BTS, conhecido como Rap Monster (Horizontal)",
        "2. O membro mais velho do BTS, Worldwide Handsome (Vertical)",
        "3. Conhecido como o 'Gênio da Música' (Vertical)",
        "4. O 'raio de sol' do grupo (Vertical)",
        "5. Conhecido por seu sorriso encantador (Horizontal)",
        "6. O nome artístico de Kim Taehyung (Vertical)",
        "7. O maknae (mais jovem) do grupo (Horizontal)",
        "8. O fandom leal do BTS (Horizontal)",
        "9. Um dos maiores hits globais do BTS, lançado em 2020 (Horizontal)"
    };

    // Solução da cruzadinha, com números representando as dicas no grid
    private String[][] solution = {
        {"1", "R", "M", " ", " ", " ", " ", "9", "D", "Y", "N", "A", "M", "I", "T", "E", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", "3", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", "S", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {"0", "B", "U", "T", "T", "E", "R", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", "G", " ", " ", " ", " ", "2", " ", " ", " ", " ", "4", " ", " ", " ", " ", " "},
        {" ", " ", "A", " ", " ", " ", " ", "J", " ", " ", " ", "7", "J", "K", " ", " ", "6", " "},
        {" ", " ", " ", " ", " ", " ", " ", "I", " ", " ", " ", " ", "H", " ", " ", " ", "V", " "},
        {" ", " ", "5", "J", "I", "M", "I", "N", " ", " ", " ", " ", "O", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "P", " ", " ", " ", " ", " "},
        {"8", "A", "R", "M", "Y", " ", " ", " ", " ", " ", " ", " ", "E", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}
    };

    private JTextArea hintArea;  // Área de texto para exibir as dicas

    // Construtor da classe Cruzadinha
    public Cruzadinha() {
        // Configurações da janela principal
        setTitle("Palavras Cruzadas BTS com Dicas");
        setSize(800, 800);  // Dimensões ajustadas para acomodar a cruzadinha
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel da cruzadinha usando GridLayout para organizar os campos de texto em uma grade
        JPanel crosswordPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        crosswordPanel.setBackground(Color.WHITE);  // Definição de cor do fundo

        // Preenchendo a matriz de campos de texto com base na solução fornecida
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = new JTextField(1);  // Cada célula é um campo de texto
                grid[i][j].setFont(new Font("Arial", Font.BOLD, 18));  // Fonte grande para o texto
                grid[i][j].setHorizontalAlignment(JTextField.CENTER);  // Centraliza o texto no campo
                grid[i][j].setForeground(Color.WHITE);  // Cor do texto
                grid[i][j].setBackground(new Color(128, 0, 128));  // Cor de fundo do campo
                crosswordPanel.add(grid[i][j]);

                // Configuração das células bloqueadas (sem solução ou contendo números das dicas)
                if (solution[i][j].equals(" ")) {
                    grid[i][j].setEditable(false);  // Células bloqueadas
                    grid[i][j].setBackground(new Color(200, 162, 200));  // Cor para as células vazias
                } else if (solution[i][j].matches("[0-9]")) {  // Números das dicas
                    grid[i][j].setEditable(false);
                    grid[i][j].setBackground(new Color(128, 0, 128));  // Cor das células com números
                    grid[i][j].setForeground(Color.WHITE);  // Cor do texto (números)
                    grid[i][j].setText(solution[i][j]);  // Exibe o número da dica
                }
            }
        }

        // Adiciona o painel da cruzadinha à janela principal
        add(crosswordPanel, BorderLayout.CENTER);

        // Configura a área de dicas
        hintArea = new JTextArea(10, 20);  // Área de texto para exibir as dicas
        hintArea.setText("Dicas:\n");  // Inicializa com o título "Dicas"
        hintArea.setEditable(false);  // A área de dicas não pode ser editada
        hintArea.setWrapStyleWord(true);  // Quebra de linha com palavras completas
        hintArea.setLineWrap(true);  // Quebra automática de linha
        add(new JScrollPane(hintArea), BorderLayout.EAST);  // Adiciona a área de dicas no lado direito

        // Botão para exibir as dicas
        JButton hintButton = new JButton("Mostrar Dicas");
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDicas();  // Exibe as dicas quando o botão é pressionado
            }
        });
        hintButton.setBackground(new Color(200, 162, 200));  // Cor do botão
        hintButton.setForeground(Color.BLACK);  // Cor do texto do botão

        // Botão para verificar a resposta
        JButton checkButton = new JButton("Verificar");
        checkButton.setBackground(new Color(128, 0, 128));  // Cor do botão
        checkButton.setForeground(Color.WHITE);  // Cor do texto do botão
        checkButton.addActionListener(this);  // Atribui o método actionPerformed para verificar respostas

        // Painel que contém os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hintButton);
        buttonPanel.add(checkButton);
        add(buttonPanel, BorderLayout.SOUTH);  // Adiciona o painel de botões na parte inferior da janela

        setVisible(true);  // Torna a janela visível
    }

    // Método para exibir as dicas no painel à direita
    private void mostrarDicas() {
        hintArea.setText("Dicas:\n");  // Reinicializa o texto da área de dicas
        for (String hint : hints) {
            hintArea.append(hint + "\n");  // Adiciona cada dica na área de texto
        }
    }

    // Método que verifica a solução quando o botão "Verificar" é pressionado
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean correto = true;  // Variável que indica se todas as respostas estão corretas
        boolean erro = false;  // Variável que indica se há erros
        boolean faltando = false;  // Variável que indica se há campos vazios

        // Verifica cada célula da grade
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (solution[i][j].matches("[0-9 ]")) {
                    // Ignora células com dicas ou vazias
                    continue;
                }
                String userInput = grid[i][j].getText().trim().toUpperCase();  // Obtém o texto inserido pelo usuário e converte para maiúscula
                if (userInput.isEmpty()) {
                    faltando = true;  // Marca que há campos vazios
                    grid[i][j].setBackground(Color.YELLOW);
                    grid[i][j].setForeground(Color.BLACK);  // Marcar células faltando com amarelo
                    correto = false;
                } else {
                    // Verifica se a entrada do usuário coincide com a solução
                    if (userInput.equals(solution[i][j])) {
                        grid[i][j].setBackground(new Color(144, 238, 144));  // Verde para respostas corretas
                        grid[i][j].setForeground(Color.BLACK);  // Muda a cor do texto para preto após o acerto
                    } else {
                        grid[i][j].setBackground(Color.RED);  // Vermelho para respostas incorretas
                        erro = true;
                        correto = false;
                    }
                }
            }
        }

        // Mensagens de acordo com o resultado
        if (correto && !faltando) {
            JOptionPane.showMessageDialog(this, "Parabéns! Você acertou todas as palavras!");
        } else if (faltando) {
            JOptionPane.showMessageDialog(this, "Algumas palavras estão faltando. Preencha todos os campos.");
        } else if (erro) {
            JOptionPane.showMessageDialog(this, "Algumas palavras estão incorretas. Tente novamente!");
        }
    }

    // Método principal para executar a aplicação
    public static void main(String[] args) {
        new Cruzadinha();  // Cria uma nova instância da aplicação Cruzadinha
    }
}
