
import java.util.Scanner;

public class JogoDaVelhaSupremo {

    static String[][] tabuleiroPequeno = new String[9][9];
    static String[][] tabuleiroGrande = new String[3][3];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Iniciando os tabuleiros
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tabuleiroPequeno[i][j] = " ";
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiroGrande[i][j] = " ";
            }
        }

        String jogadorAtual = "X";
        int proximoMiniJogo = -1;
        boolean jogoAtivo = true;

        System.out.println("BEM VINDO AO JOGO DA VELHA 2\n");

        while (jogoAtivo) {
            desenharTabuleiro();

            if (proximoMiniJogo == -1) {
                boolean escolhaValida = false;
                while (!escolhaValida) {
                    System.out.print("Jogador " + jogadorAtual + ", escolha um mini jogo livre (0 a 8): ");
                    proximoMiniJogo = sc.nextInt();

                    if (proximoMiniJogo >= 0 && proximoMiniJogo <= 8) {
                        int linhaMacro = proximoMiniJogo / 3;
                        int colunaMacro = proximoMiniJogo % 3;

                        if (tabuleiroGrande[linhaMacro][colunaMacro].equals(" ")) {
                            escolhaValida = true;
                        } else {
                            System.out.println("ERRO: Este mini-tabuleiro já foi finalizado!");
                        }
                    } else {
                        System.out.println("ERRO: Opção inválida. Digite de 0 a 8.");
                    }
                }
            } else {
                System.out.println("Jogador " + jogadorAtual + ", você DEVE jogar no mini jogo: " + proximoMiniJogo);
            }

            int linhaMini = -1;
            int colunaMini = -1;
            boolean jogadaValida = false;

            int linhaOffset = (proximoMiniJogo / 3) * 3;
            int colunaOffset = (proximoMiniJogo % 3) * 3;

            while (!jogadaValida) {
                System.out.print("Informe a LINHA da casa (0 a 2): ");
                linhaMini = sc.nextInt();
                System.out.print("Informe a COLUNA da casa (0 a 2): ");
                colunaMini = sc.nextInt();

                if (linhaMini >= 0 && linhaMini <= 2 && colunaMini >= 0 && colunaMini <= 2) {
                    int linhaGlobal = linhaOffset + linhaMini;
                    int colunaGlobal = colunaOffset + colunaMini;

                    if (tabuleiroPequeno[linhaGlobal][colunaGlobal].equals(" ")) {
                        tabuleiroPequeno[linhaGlobal][colunaGlobal] = jogadorAtual;
                        jogadaValida = true;
                    } else {
                        System.out.println("ERRO: Essa casa já está ocupada!");
                    }
                } else {
                    System.out.println("ERRO: Posição inválida. Use apenas 0, 1 ou 2.");
                }
            }

            // ====================================================================
            // PROCESSAMENTO DE VITÓRIAS (CHAMADA DAS FUNÇÕES DO GRUPO)
            // ====================================================================
            int linhaMacroAtual = proximoMiniJogo / 3;
            int colunaMacroAtual = proximoMiniJogo % 3;

            // aqui chama a função para o grupo verificar se o minitabuleiro atual foi vencido
            String vencedorMini = checarVitoriaMinitabuleiro(linhaOffset, colunaOffset);

            if (!vencedorMini.equals(" ")) {
                // Se houve vencedor (X ou O), salva no tabuleiro macro
                tabuleiroGrande[linhaMacroAtual][colunaMacroAtual] = vencedorMini;
                System.out.println("\n*** JOGADOR " + vencedorMini + " CONQUISTOU O MINI-TABULEIRO " + proximoMiniJogo + "! ***");

                // Em seguida, já checa se essa conquista deu a vitória no jogo inteiro
                String vencedorMacro = checarVitoriaMacro();
                if (!vencedorMacro.equals(" ")) {
                    desenharTabuleiro();
                    System.out.println("\n=============================================");
                    System.out.println("FIM DE JOGO! O JOGADOR " + vencedorMacro + " VENCEU O JOGO DA VELHA 2!");
                    System.out.println("=============================================");
                    jogoAtivo = false;
                    break; // Sai do laço do jogo imediatamente
                }
            }
            // ====================================================================

            // DEFINIR O PRÓXIMO MINITABULEIRO
            int proximoAlvo = (linhaMini * 3) + colunaMini;
            int macroL = proximoAlvo / 3;
            int macroC = proximoAlvo % 3;

            if (!tabuleiroGrande[macroL][macroC].equals(" ")) {
                proximoMiniJogo = -1;
                System.out.println("\nO próximo minitabuleiro já está fechado. Próximo jogador terá escolha LIVRE!");
            } else {
                proximoMiniJogo = proximoAlvo;
            }

            // ALTERNAR O TURNO
            if (jogoAtivo) {
                jogadorAtual = jogadorAtual.equals("X") ? "O" : "X";
            }
        }

        sc.close();
    }

    public static void desenharTabuleiro() {
        System.out.println("\nTABULEIRO DO JOGO");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print("[" + tabuleiroPequeno[i][j] + "]");
                if ((j + 1) % 3 == 0 && j < 8) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if ((i + 1) % 3 == 0 && i < 8) {
                System.out.println("---------------------------------------");
            }
        }
        System.out.println();
    }

    /**
     * TODO: GRUPO ACHO LEGAL IMPLEMENTAR ESSA FUNÇÃO
     * Verifica se houve um vencedor dentro do minitabuleiro que acabou de ser jogado.
     * @param linhaOffset Ponto inicial da linha do minitabuleiro na matriz 9x9 (0, 3 ou 6)
     * @param colunaOffset Ponto inicial da coluna do minitabuleiro na matriz 9x9 (0, 3 ou 6)
     * @return "X" ou "O" se houver vencedor, ou " " (espaço em branco) se não houver.
     */
    public static String checarVitoriaMinitabuleiro(int linhaOffset, int colunaOffset) {
        // Dica: varrer 3 linhas e 3 colunas a partir do offset recebido.
        return " "; // Retorno temporário para não dar erro de compilação
    }

    /**
     * TODO: GRUPO ACHO LEGAL IMPLEMENTAR ESSA FUNÇÃO
     * Verifica se houve um vencedor no Tabuleiro Macro (3x3).
     * @return "X" ou "O" se houver um ganhador do jogo, ou " " (espaço em branco) se a partida continuar.
     */
    public static String checarVitoriaMacro() {
        // Dica: varrer as linhas, colunas e diagonais da matriz 'tabuleiroGrande'.
        return " "; // Retorno temporário para não dar erro de compilação
    }
}