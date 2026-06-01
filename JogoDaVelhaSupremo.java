
import java.util.Scanner;

public class JogoDaVelhaSupremo {

    static String[] tabuleiroPrincipal = {" ", " ", " ", " ", " ", " ", " ", " ", " "}; // jogo principal, é com isso q o código sabe quem ganhou

    static String[][] tabuleiro = { //tabuleiro que CONTEM todos os mini tabuleiros
        {" ", " ", " ", " ", " ", " ", " ", " ", " ",}/*tabuleiro 1*/, {" ", " ", " ", " ", " ", " ", " ", " ", " ",}/*tabuleiro 2*/, {" ", " ", " ", " ", " ", " ", " ", " ", " ",}/*tabuleiro 3*/,

        {" ", " ", " ", " ", " ", " ", " ", " ", " ",}/*tabuleiro 4*/, {" ", " ", " ", " ", " ", " ", " ", " ", " ",}/*tabuleiro 5*/, {" ", " ", " ", " ", " ", " ", " ", " ", " ",}/*tabuleiro 6*/,

        {" ", " ", " ", " ", " ", " ", " ", " ", " ",}/*tabuleiro 7*/, {" ", " ", " ", " ", " ", " ", " ", " ", " ",}/*tabuleiro 8*/, {" ", " ", " ", " ", " ", " ", " ", " ", " ",}/*tabuleiro 9*/
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean escolhaValida = false;
        do {
            pularLinhas();
            System.out.println("BEM VINDO AO JOGO DA VELHA 2\n");
            System.out.print("OQUE DESEJA FAZER? \n1. Jogar\n2. Ver regras\nX. Sair\nOpção: ");
            String escolha = sc.nextLine().toUpperCase().trim();

            switch (escolha) {
                case "1":
                    jogo();
                    escolhaValida = true;
                    break;
                case "2":
                    verRegras();
                    escolhaValida = true;
                    break;
                case "X":
                    System.out.println("Tchau!");
                    System.exit(0);
                    escolhaValida = true;
                    break;
                default:
                    System.out.println("ERRO: Opção inválida.");
            }
        } while(!escolhaValida);
    }

    public static void verRegras() {
        pularLinhas();
        System.out.println("REGRAS");
        //DESENVOLVIMENTO 1
    }

    public static void jogo() {
        Scanner sc = new Scanner(System.in);

        String jogadorAtual = "X";
        int tabuleiroAtual = -1; // -1 significa que o jogador pode escolher qualquer mini tabuleiro

        boolean jogoAtivo = true;
        while (jogoAtivo) {
            pularLinhas();
            desenharTabuleiroPrincipal();
            desenharTabuleiro();

            // se tabuleiroAtual é -1, o jogador escolhe qual mini tabuleiro jogar
            if (tabuleiroAtual == -1) {
                boolean escolhaValida = false;
                while (!escolhaValida) {
                    System.out.print("Jogador " + jogadorAtual + ", escolha um mini jogo livre (1 a 9): ");
                    int proximoTabuleiro = sc.nextInt();

                    if (proximoTabuleiro >= 1 && proximoTabuleiro <= 9) {
                        // só pode escolher se o mini tabuleiro ainda não foi finalizado
                        if (tabuleiroPrincipal[proximoTabuleiro - 1].equals(" ")) {
                            tabuleiroAtual = proximoTabuleiro - 1;
                            escolhaValida = true;
                        } else {
                            System.out.println("ERRO: Este mini-tabuleiro já foi finalizado!");
                        }
                    } else {
                        System.out.println("ERRO: Opção inválida. Digite de 1 a 9.");
                    }
                }
            } else {
                // o mini tabuleiro foi definido pela jogada anterior
                System.out.println("Jogador " + jogadorAtual + ", você DEVE jogar no mini jogo: " + (tabuleiroAtual + 1));
            }

            boolean escolhaValida = false;
            while (!escolhaValida) {
                System.out.print("Informe uma posição livre para fazer sua jogada (1 a 9): ");
                int posicaoJogada = sc.nextInt();

                if (posicaoJogada >= 1 && posicaoJogada <= 9) {
                    if (tabuleiro[tabuleiroAtual][posicaoJogada - 1].equals(" ")) {
                        // faz a jogada
                        tabuleiro[tabuleiroAtual][posicaoJogada - 1] = jogadorAtual;
                        // verifica se algum mini tabuleiro foi vencido/empatado
                        checarTabuleiro();
                        // o próximo mini tabuleiro obrigatório é a posição que o jogador acabou de marcar
                        // se esse mini tabuleiro já foi finalizado, o jogador pode escolher livremente (-1)
                        tabuleiroAtual = tabuleiroPrincipal[posicaoJogada - 1].equals(" ") ? posicaoJogada - 1 : -1;
                        escolhaValida = true;
                    } else {
                        System.out.println("ERRO: Essa posição já foi usada!");
                    }
                } else {
                    System.out.println("ERRO: Opção inválida. Digite de 1 a 9.");
                }
            }

            // verifica se o tabuleiro principal tem um vencedor
            if (checarTabuleiroPrincipal()) {
                pularLinhas();
                jogoAtivo = false;
                System.out.println("ACABOU");

                //DESENVOLVIMENTO 2
            }

            // ALTERNAR O TURNO
            if (jogoAtivo) {
                jogadorAtual = jogadorAtual.equals("X") ? "O" : "X";
            }
        }

        sc.close();
    }

    public static void desenharTabuleiro() {
        System.out.println("\nTABULEIRO");
        for (int blocoLinha = 0; blocoLinha < 3; blocoLinha++) {
            for (int linhaInterna = 0; linhaInterna < 3; linhaInterna++) {
                for (int blocoCol = 0; blocoCol < 3; blocoCol++) {
                    int i = blocoLinha * 3 + blocoCol; // índice do mini tabuleiro
                    for (int colInterna = 0; colInterna < 3; colInterna++) {
                        int j = linhaInterna * 3 + colInterna; // índice da célula dentro do mini tabuleiro

                        // se a célula é █ ou se o jogo já acabou e a célula é espaço, imprime sem colchetes
                        if (tabuleiro[i][j].equals("\u2588") || (!tabuleiroPrincipal[i].equals(" ") && tabuleiro[i][j].equals(" "))) {
                            System.out.print(" " + tabuleiro[i][j] + " ");
                        } else {
                            System.out.print("[" + tabuleiro[i][j] + "]");
                        }
                    }
                    if (blocoCol < 2) System.out.print(" \u2588 "); // separador vertical entre blocos
                }
                System.out.println();
            }
            if (blocoLinha < 2) System.out.println("\u2588 \u2588 \u2588 \u2588 \u2588 \u2588 \u2588 \u2588 \u2588 \u2588 \u2588 \u2588 \u2588 \u2588 \u2588 \u2588 \u2588"); // separador horizontal entre blocos
        }
        System.out.println();
    }

    public static void desenharTabuleiroPrincipal() {
        System.out.println("\nTABULEIRO PRINCIPAL");
        for (int i = 0; i < 9; i++) {
            System.out.print("[" + tabuleiroPrincipal[i] + "]");
            if (i == 2 || i == 5) System.out.println(); // quebra de linha após cada linha do tabuleiro
        }
        System.out.println();
    }

    public static void checarTabuleiro() {
        for (int i = 0; i < 9; i++) {
            String jogador = " "; // guarda quem venceu esse mini tabuleiro

            // verifica linhas (índices 0-2, 3-5, 6-8)
            for (int j = 0; j < 9; j += 3) {
                if (!tabuleiro[i][j].equals(" ") && tabuleiro[i][j].equals(tabuleiro[i][j+1]) && tabuleiro[i][j].equals(tabuleiro[i][j+2])) {
                    jogador = tabuleiro[i][j];
                }
            }
            // verifica colunas (índices 0,3,6 | 1,4,7 | 2,5,8)
            for (int j = 0; j < 3; j++) {
                if (!tabuleiro[i][j].equals(" ") && tabuleiro[i][j].equals(tabuleiro[i][j+3]) && tabuleiro[i][j].equals(tabuleiro[i][j+6])) {
                    jogador = tabuleiro[i][j];
                }
            }
            // verifica diagonais (0,4,8 e 2,4,6)
            if (!tabuleiro[i][4].equals(" ")) {
                if (tabuleiro[i][0].equals(tabuleiro[i][4]) && tabuleiro[i][4].equals(tabuleiro[i][8])) jogador = tabuleiro[i][4];
                if (tabuleiro[i][2].equals(tabuleiro[i][4]) && tabuleiro[i][4].equals(tabuleiro[i][6])) jogador = tabuleiro[i][4];
            }

            // verifica empate: todas as posições ocupadas e sem vencedor
            for (int u = 0; u < 9; u++) {
                if (tabuleiro[i][u].equals(" ")) {
                    break;
                }
                if (u == 8) {
                    jogador = "*"; // * representa empate (carta coringa)
                }
            }

            // se houve vencedor ou empate, marca o mini tabuleiro com o símbolo do jogador
            if (!jogador.equals(" ") && tabuleiroPrincipal[i].equals(" ")) {
                tabuleiroPrincipal[i] = jogador;
                for (int k = 0; k < 9; k++) {
                    tabuleiro[i][k] = " ";
                    if (jogador.equals("*")) if (k == 4) tabuleiro[i][k] = "\u2588"; // ponto no meio = empate
                    if (jogador.equals("X")) if (k == 0 || k == 2 || k == 4 || k == 6 || k == 8) tabuleiro[i][k] = "\u2588"; // X com blocos
                    if (jogador.equals("O")) if (k == 0 || k == 1 || k == 2 || k == 3 || k == 5 || k == 6 || k == 7 || k == 8) tabuleiro[i][k] = "\u2588"; // O com blocos
                }
            }
        }
    }

    public static boolean checarTabuleiroPrincipal() {
        // verifica linhas, colunas e diagonais do tabuleiro principal
        // o * é coringa: pode ser usado por qualquer jogador para completar uma linha
        // mas precisa ter pelo menos um símbolo real do jogador na combinação

        // linhas
        for (int i = 0; i < 9; i += 3) {
            for (String j : new String[]{"X", "O"}) {
                if ((tabuleiroPrincipal[i].equals(j) || tabuleiroPrincipal[i].equals("*"))
                        && (tabuleiroPrincipal[i+1].equals(j) || tabuleiroPrincipal[i+1].equals("*"))
                        && (tabuleiroPrincipal[i+2].equals(j) || tabuleiroPrincipal[i+2].equals("*"))
                        && (tabuleiroPrincipal[i].equals(j) || tabuleiroPrincipal[i+1].equals(j) || tabuleiroPrincipal[i+2].equals(j))) return true;
            }
        }
        // colunas
        for (int i = 0; i < 3; i++) {
            for (String j : new String[]{"X", "O"}) {
                if ((tabuleiroPrincipal[i].equals(j) || tabuleiroPrincipal[i].equals("*"))
                        && (tabuleiroPrincipal[i+3].equals(j) || tabuleiroPrincipal[i+3].equals("*"))
                        && (tabuleiroPrincipal[i+6].equals(j) || tabuleiroPrincipal[i+6].equals("*"))
                        && (tabuleiroPrincipal[i].equals(j) || tabuleiroPrincipal[i+3].equals(j) || tabuleiroPrincipal[i+6].equals(j))) return true;
            }
        }
        // diagonais
        for (String j : new String[]{"X", "O"}) {
            if ((tabuleiroPrincipal[0].equals(j) || tabuleiroPrincipal[0].equals("*"))
                    && (tabuleiroPrincipal[4].equals(j) || tabuleiroPrincipal[4].equals("*"))
                    && (tabuleiroPrincipal[8].equals(j) || tabuleiroPrincipal[8].equals("*"))
                    && (tabuleiroPrincipal[0].equals(j) || tabuleiroPrincipal[4].equals(j) || tabuleiroPrincipal[8].equals(j))) return true;

            if ((tabuleiroPrincipal[2].equals(j) || tabuleiroPrincipal[2].equals("*"))
                    && (tabuleiroPrincipal[4].equals(j) || tabuleiroPrincipal[4].equals("*"))
                    && (tabuleiroPrincipal[6].equals(j) || tabuleiroPrincipal[6].equals("*"))
                    && (tabuleiroPrincipal[2].equals(j) || tabuleiroPrincipal[4].equals(j) || tabuleiroPrincipal[6].equals(j))) return true;
        }
        return false;
    }

    public static void pularLinhas() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    /**
     * TODO: GRUPO ANALISEM O CODIGO E TENTEM ENCONTRAR FALHAS

     * TODO: TAREFAS
     * Desenvolva o método verRegras(); esse método deve apenas mostrar as opções e ter um jeito pra voltar pro main (marquei no codigo como DESENVOLVIMENTO 1)
     * Desenvolva melhor o IF que tem dentro do jogo, esse IF é responsavel descobrir se alguem ja venceu, e caso tenha vencido, finalizar o jogo, mostrando quem ganhou X ou O, e dar uma opcao para voltar para o menu principal, o main (marquei no codigo como DESENVOLVIMENTO 2)
     */
}