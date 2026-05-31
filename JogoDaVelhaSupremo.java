
import java.util.Scanner;

public class JogoDaVelhaSupremo {

    static String[][] tabuleiroPequeno = new String[9][9];
    static String[][] tabuleiroGrande = new String[3][3];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

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

        System.out.println("BEM VINDO AO JOGO DA VELHA 2");

        while (jogoAtivo) {
            desenharTabuleiro();

            if (proximoMiniJogo == -1) {
                System.out.println("Jogador " + jogadorAtual + "escolha um mini jogo livre (0 a 8): ");
                proximoMiniJogo = sc.nextInt();
                //tem que ter uma validacao para ver se o número está entre 0 e 8 e se o tabuleiro grande está vazio nessa escolha 

            }

            
            //precisa ler a posicaoPequena, validar, salvar a jogada e alternar o turno!

            jogoAtivo = false;
        }
        

    }
    public static void desenharTabuleiro() {
        System.out.println("TABULEIRO DO JOGO");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print("[" + tabuleiroPequeno[i][j] + "]");
                if ((j + 1) % 3 == 0 && j < 8) {
                    System.out.print(" | "); 
                }
            }
            System.out.println();
            if ((i + 1) % 3 == 0 && i < 8) {
                System.out.println("-------------------------------------"); 
            }
        }
    }
}

