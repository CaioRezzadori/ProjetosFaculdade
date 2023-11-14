package Pecas;

import Auxiliar.Consts;
import Xadrez.Tabuleiro;
import auxiliar.Posicao;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Peao extends Peca implements Serializable{

    private boolean bPrimeiroLance;

    public Peao(String sAFileName, Posicao aPosicao, boolean bBrancas) {
        super(sAFileName, aPosicao, bBrancas);
        this.bPrimeiroLance = true;
    }

    @Override
    public String toString() {
        return "Peao";
    }
    

    
    @Override
    
    public boolean setPosicao(Posicao umaPosicao, Tabuleiro t, boolean fazerMudanca){
        if(t.getPecaEmPosicao(umaPosicao) != null){//Caso em que a posicao nao esta vazia
            if(!this.temAMesmaCorQue(t.getPecaEmPosicao(umaPosicao))){ //Caso em que a peca na posicao eh da cor oposta do peao
                if((this.bBrancas && umaPosicao.getLinha() - this.getLinha() == -1) ||
                    (!this.bBrancas && umaPosicao.getLinha() - this.getLinha() == 1)){ //Garantindo que o peao nao pode voltar e so pode ir 1 casa para frente
                    if(Math.abs(umaPosicao.getColuna() - this.getColuna()) == 1){ //Garantindo que so pode comer na diagonal
                        if(fazerMudanca){
                            bPrimeiroLance = false;
                            this.pPosicao.setPosicao(umaPosicao);
                        }
                        //Caso de peoes nos extremos
                        if(((this.bBrancas && umaPosicao.getLinha() == 0) || (!this.bBrancas && umaPosicao.getLinha() == 7)) && fazerMudanca){
                            System.out.println("\nEscolha uma das pecas para substituir o peao!");
                            System.out.println("Aperte R para escolher a rainha");
                            System.out.println("Aperte T para escolher a torre");
                            System.out.println("Aperte B para escolher o bispo");
                            System.out.println("Aperte C para escolher o cavalo");   
                        }
                        return true;
                    }
                }
            }
            else{//Caso em que a peca eh da mesma cor do peao
                if(this.pPosicao.igual(umaPosicao)){//Deselecionando a peca
                    if(fazerMudanca){
                        this.pPosicao.setPosicao(umaPosicao);
                    }
                    //Caso de peoes nos extremos
                    if(((this.bBrancas && umaPosicao.getLinha() == 0) || (!this.bBrancas && umaPosicao.getLinha() == 7)) && fazerMudanca){
                        System.out.println("\nEscolha uma das pecas para substituir o peao!");
                        System.out.println("Aperte R para escolher a rainha");
                        System.out.println("Aperte T para escolher a torre");
                        System.out.println("Aperte B para escolher o bispo");
                        System.out.println("Aperte C para escolher o cavalo");   
                    }
                    return true;
                }
            }
        }
        else{//Caso em que a posicao esta vazia
            if((this.bBrancas && umaPosicao.getLinha() < this.getLinha()) ||
                (!this.bBrancas && umaPosicao.getLinha() > this.getLinha())){
        
                if(bPrimeiroLance && //Primeiro lance
                        (this.bBrancas && this.getLinha() == 6) ||
                        (!this.bBrancas && this.getLinha() == 1)){
                    if(Math.abs(umaPosicao.getLinha() - this.getLinha()) == 2 && (umaPosicao.getColuna() - this.getColuna() == 0)) {
                        if(fazerMudanca){
                            bPrimeiroLance = false;
                            this.pPosicao.setPosicao(umaPosicao);
                        }
                        //Caso de peoes nos extremos
                        if(((this.bBrancas && umaPosicao.getLinha() == 0) || (!this.bBrancas && umaPosicao.getLinha() == 7)) && (true == fazerMudanca)){
                            System.out.println("\nEscolha uma das pecas para substituir o peao!");
                            System.out.println("Aperte R para escolher a rainha");
                            System.out.println("Aperte T para escolher a torre");
                            System.out.println("Aperte B para escolher o bispo");
                            System.out.println("Aperte C para escolher o cavalo");   
                        }
                        return (true == fazerMudanca);
                        /*A operacao logica acima eh para evitar que este metodo realize a operacao de movimentar
                        o peao quando na verdade apenas necessita-se retornar o valor true(posicao valida para o peao acessar)
                        */
                    }
                }
                if(Math.abs(umaPosicao.getLinha() - this.getLinha()) == 1){
                    if(umaPosicao.getColuna() - this.getColuna() == 0){
                        if(fazerMudanca){
                            bPrimeiroLance = false;
                            this.pPosicao.setPosicao(umaPosicao);
                        }
                        //Caso de peoes nos extremos
                        if(((this.bBrancas && umaPosicao.getLinha() == 0) || (!this.bBrancas && umaPosicao.getLinha() == 7)) && (true == fazerMudanca)){
                            System.out.println("\nEscolha uma das pecas para substituir o peao!");
                            System.out.println("Aperte R para escolher a rainha");
                            System.out.println("Aperte T para escolher a torre");
                            System.out.println("Aperte B para escolher o bispo");
                            System.out.println("Aperte C para escolher o cavalo");   
                        }
                        return (true == fazerMudanca);
                        /*Mesma justificativa do ultimo "return"*/
                    }
                    else if(Math.abs(umaPosicao.getColuna() - this.getColuna()) == 1){
                        return(false == fazerMudanca);
                        /*Mesma justificativa do penultimo "return", mas pro caso que eh necessario retornar false*/
                    }
                }
            }
        }
        return false;
    }
}
