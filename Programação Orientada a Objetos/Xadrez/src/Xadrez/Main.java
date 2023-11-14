package Xadrez;

import Pecas.Peao;
import Pecas.Cavalo;
import Pecas.Torre;
import Pecas.Bispo;
import Pecas.Rainha;
import Pecas.Rei;
import auxiliar.Posicao;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Programacao Orientada a Objetos - Trabalho\n");
        System.out.println("Caio Assumpcao Rezzadori - 11810481\n");
        System.out.println("Joao Marcelo R. Junior - 8531118\n\n");
        
        System.out.println("Xadrez");
        System.out.println("Seja bem vindo! Os movimentos do jogo e avisos importantes aparecerao nesta aba. Aqui estao algumas informacoes:\n\n"
                + "-Voce podera salvar o jogo em qualquer momento pressionando a tecla S, gerando um arquivo .zip na pasta ''estados'' \n"
                + "-Caso os jogadores queiram declarar empate, aperte E\n"
                + "-O jogo termina quando o Rei de um dos conjuntos de pecas ficar em cheque em sua posicao atual e em suas posicoes adjacentes, independente do movimento das outras pecas do conjunto\n"
                + "-Sera definido como criterio de empate caso um jogador nao esteja em cheque, mas fique impossibilitado de fazer quaisquer movimentos quando for sua jogada\n"
                + "-Caso os tres movimentos consecutivos de cada conjunto de peca sejam iguais, sera declarado empate.\n\n"
                +"Para iniciar o jogo, aperte digite i e pressione ENTER\n\n\n"
                + "Obs: Faltou implementar alguns movimentos especiais e menos comuns, como o roque (troca do peao com a torre em suas primeiras jogadas) e o ''En Passant''.");
        
        //Inicializando jogo
        Scanner s = new Scanner(System.in);
        
        while(!s.next().equals("i")){
            Thread.sleep(500);
        }
       
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Jogo tMeuJogo = new Jogo();
                tMeuJogo.addPeca(new Peao("PeaoBranco.png", new Posicao(6,0), true), Jogo.CoresConjuntos.BRANCAS);
                tMeuJogo.addPeca(new Peao("PeaoBranco.png", new Posicao(6,1), true), Jogo.CoresConjuntos.BRANCAS);
                tMeuJogo.addPeca(new Peao("PeaoBranco.png", new Posicao(6,2), true), Jogo.CoresConjuntos.BRANCAS);
                tMeuJogo.addPeca(new Peao("PeaoBranco.png", new Posicao(6,3), true), Jogo.CoresConjuntos.BRANCAS);                
                tMeuJogo.addPeca(new Peao("PeaoBranco.png", new Posicao(6,4), true), Jogo.CoresConjuntos.BRANCAS);
                tMeuJogo.addPeca(new Peao("PeaoBranco.png", new Posicao(6,5), true), Jogo.CoresConjuntos.BRANCAS);
                tMeuJogo.addPeca(new Peao("PeaoBranco.png", new Posicao(6,6), true), Jogo.CoresConjuntos.BRANCAS);
                tMeuJogo.addPeca(new Peao("PeaoBranco.png", new Posicao(6,7), true), Jogo.CoresConjuntos.BRANCAS);  
                tMeuJogo.addPeca(new Torre("TorreBranca.png", new Posicao(7,7), true), Jogo.CoresConjuntos.BRANCAS);                  
                tMeuJogo.addPeca(new Torre("TorreBranca.png", new Posicao(7,0), true), Jogo.CoresConjuntos.BRANCAS);                  
                tMeuJogo.addPeca(new Cavalo("CavaloBranco.png", new Posicao(7,1), true), Jogo.CoresConjuntos.BRANCAS);                  
                tMeuJogo.addPeca(new Cavalo("CavaloBranco.png", new Posicao(7,6), true), Jogo.CoresConjuntos.BRANCAS);                                  
                tMeuJogo.addPeca(new Bispo("BispoBranco.png", new Posicao(7,5), true), Jogo.CoresConjuntos.BRANCAS);                                  
                tMeuJogo.addPeca(new Bispo("BispoBranco.png", new Posicao(7,2), true), Jogo.CoresConjuntos.BRANCAS);                                  
                tMeuJogo.addPeca(new Rainha("RainhaBranca.png", new Posicao(7,3), true), Jogo.CoresConjuntos.BRANCAS);                                  
                tMeuJogo.addPeca(new Rei("ReiBranco.png", new Posicao(7,4), true), Jogo.CoresConjuntos.BRANCAS);                                                  
                
                tMeuJogo.addPeca(new Peao("PeaoPreto.png", new Posicao(1,0), false), Jogo.CoresConjuntos.PRETAS);                 
                tMeuJogo.addPeca(new Peao("PeaoPreto.png", new Posicao(1,1), false), Jogo.CoresConjuntos.PRETAS);                                 
                tMeuJogo.addPeca(new Peao("PeaoPreto.png", new Posicao(1,2), false), Jogo.CoresConjuntos.PRETAS);                 
                tMeuJogo.addPeca(new Peao("PeaoPreto.png", new Posicao(1,3), false), Jogo.CoresConjuntos.PRETAS);                                 
                tMeuJogo.addPeca(new Peao("PeaoPreto.png", new Posicao(1,4), false), Jogo.CoresConjuntos.PRETAS);                 
                tMeuJogo.addPeca(new Peao("PeaoPreto.png", new Posicao(1,5), false), Jogo.CoresConjuntos.PRETAS);                                 
                tMeuJogo.addPeca(new Peao("PeaoPreto.png", new Posicao(1,6), false), Jogo.CoresConjuntos.PRETAS);                 
                tMeuJogo.addPeca(new Peao("PeaoPreto.png", new Posicao(1,7), false), Jogo.CoresConjuntos.PRETAS);                                 
                tMeuJogo.addPeca(new Torre("TorrePreta.png", new Posicao(0,7), false), Jogo.CoresConjuntos.PRETAS);                  
                tMeuJogo.addPeca(new Torre("TorrePreta.png", new Posicao(0,0), false), Jogo.CoresConjuntos.PRETAS);                  
                tMeuJogo.addPeca(new Cavalo("CavaloPreto.png", new Posicao(0,1), false), Jogo.CoresConjuntos.PRETAS);                  
                tMeuJogo.addPeca(new Cavalo("CavaloPreto.png", new Posicao(0,6), false), Jogo.CoresConjuntos.PRETAS);                                  
                tMeuJogo.addPeca(new Bispo("BispoPreto.png", new Posicao(0,5), false), Jogo.CoresConjuntos.PRETAS);                                  
                tMeuJogo.addPeca(new Bispo("BispoPreto.png", new Posicao(0,2), false), Jogo.CoresConjuntos.PRETAS);                                  
                tMeuJogo.addPeca(new Rainha("RainhaPreta.png", new Posicao(0,3), false), Jogo.CoresConjuntos.PRETAS);                                  
                tMeuJogo.addPeca(new Rei("ReiPreto.png", new Posicao(0,4), false), Jogo.CoresConjuntos.PRETAS);

                tMeuJogo.setVisible(true);                
                tMeuJogo.go();
            }
        });
    }
}

