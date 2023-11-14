package Xadrez;

import Auxiliar.Consts;
import Pecas.Peca;
import auxiliar.Posicao;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import javax.swing.JPanel;
import Pecas.Peao;
import Pecas.Bispo;
import Pecas.Cavalo;
import Pecas.Torre;
import Pecas.Rainha;
import Pecas.Rei;
import Auxiliar.ChequeException;
import java.lang.String;

public class Tabuleiro extends JPanel implements Serializable {

    Conjunto cBrancas;
    Conjunto cPretas;

    Tabuleiro(Conjunto cBrancas, Conjunto cPretas) {
        this.cBrancas = cBrancas;
        this.cPretas = cPretas;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        /*64 é o numedo de quadrantes de um tabuleiro de xadrez*/
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((j + i) % 2 == 0) {
                    g2d.setColor(Color.lightGray);
                } else {
                    g2d.setColor(Color.gray);
                }
                g2d.fillRect(j * Consts.SIZE, i * Consts.SIZE,
                        Consts.SIZE, Consts.SIZE);
            }
        }
    }
    
    public Peca getPecaEmPosicao(Posicao umaPosicao) {
        for (int i = 0; i < this.cBrancas.size(); i++) {
            Peca iEsimaPeca = (Peca) this.cBrancas.get(i);
            if (iEsimaPeca.getColuna() == umaPosicao.getColuna() &&
                    iEsimaPeca.getLinha() == umaPosicao.getLinha()) {
                return iEsimaPeca;
            }
        }
        for (int i = 0; i < this.cPretas.size(); i++) {
            Peca iEsimaPeca = (Peca) this.cPretas.get(i);
            if (iEsimaPeca.getColuna() == umaPosicao.getColuna() &&
                    iEsimaPeca.getLinha() == umaPosicao.getLinha()) {
                return iEsimaPeca;
            }
        }
        return null;
    }

    public Posicao peaoNosExtremos(boolean bBranco){
        if(bBranco == true){
            for(int i = 0; i < this.cBrancas.size(); i++){
                Posicao iEsimaPosicao = (Posicao) this.cBrancas.get(i).getPosicao();
                if(iEsimaPosicao.getLinha() == 0 && this.cBrancas.get(i).toString().equals("Peao")){
                    System.out.println("\nEscolha uma das pecas para substituir o peao!");
                    System.out.println("Aperte R para escolher a rainha");
                    System.out.println("Aperte T para escolher a torre");
                    System.out.println("Aperte B para escolher o bispo");
                    System.out.println("Aperte C para escolher o cavalo");   
                    return iEsimaPosicao;
                }
            }
        }
        else{
            for(int i = 0; i < this.cPretas.size(); i++){
                Posicao iEsimaPosicao = (Posicao) this.cPretas.get(i).getPosicao();
                if(iEsimaPosicao.getLinha() == 7 &&  this.cPretas.get(i).toString().equals("Peao")){
                    System.out.println("\nEscolha uma das pecas para substituir o peao!");
                    System.out.println("Aperte R para escolher a rainha");
                    System.out.println("Aperte T para escolher a torre");
                    System.out.println("Aperte B para escolher o bispo");
                    System.out.println("Aperte C para escolher o cavalo");   
                    return iEsimaPosicao;
                }
            }
        }
        return null;
    }
    
    public void trocaPecaPosicao(Posicao posicaoAnterior, Posicao posicaoNova){
        Peca p = this.getPecaEmPosicao(posicaoAnterior);

        if(p.bBrancas){
            //this.cBrancas.add(p);
            if(p.toString().equals("Peao")){
                this.cBrancas.add(new Peao("PeaoBranco.png", posicaoNova, p.bBrancas));
            }
            if(p.toString().equals("Cavalo")){
                this.cBrancas.add(new Cavalo("CavaloBranco.png", posicaoNova, p.bBrancas));
            }
            if(p.toString().equals("Bispo")){
                this.cBrancas.add(new Bispo("BispoBranco.png", posicaoNova, p.bBrancas));
            }
            if(p.toString().equals("Torre")){
                this.cBrancas.add(new Torre("TorreBranca.png", posicaoNova, p.bBrancas));
            }
            if(p.toString().equals("Rainha")){
                this.cBrancas.add(new Rainha("RainhaBranca.png", posicaoNova, p.bBrancas));
            }
            if(p.toString().equals("Rei")){
                this.cBrancas.add(new Rei("ReiBranco.png", posicaoNova, p.bBrancas));
            }

            this.cBrancas.pecaFora(p);
        }
        else{
            if(p.toString().equals("Peao")){
                this.cPretas.add(new Peao("PeaoPreto.png", posicaoNova, p.bBrancas));
            }
            if(p.toString().equals("Cavalo")){
                this.cPretas.add(new Cavalo("CavaloPreto.png", posicaoNova, p.bBrancas));
            }
            if(p.toString().equals("Bispo")){
                this.cPretas.add(new Bispo("BispoPreto.png", posicaoNova, p.bBrancas));
            }
            if(p.toString().equals("Torre")){
                this.cPretas.add(new Torre("TorrePreta.png", posicaoNova, p.bBrancas));
            }
            if(p.toString().equals("Rainha")){
                this.cPretas.add(new Rainha("RainhaPreta.png", posicaoNova, p.bBrancas));
            }
            if(p.toString().equals("Rei")){
                this.cPretas.add(new Rei("ReiPreto.png", posicaoNova, p.bBrancas));
            }
            
            this.cPretas.pecaFora(p);
        }
    }
   
    public Peca conjuntoEmCheque(boolean brancoJoga) {
       Peca reiBrancas = null;
       Peca reiPretas = null;

       for (int i = 0; i < this.cBrancas.size(); i++) {
           if(this.cBrancas.get(i).toString().equals("Rei"))
               reiBrancas = this.cBrancas.get(i);
       }
       for (int i = 0; i < this.cPretas.size(); i++) {
           if(this.cPretas.get(i).toString().equals("Rei"))
               reiPretas = this.cPretas.get(i);
       }
       if(reiBrancas != null && posicaoSobAmeaca(reiBrancas.getPosicao(), true) && brancoJoga){
           return reiBrancas;
       }
       if(reiPretas != null && posicaoSobAmeaca(reiPretas.getPosicao(), false) && !brancoJoga){
           return reiPretas;
       }
       return null;
       
    }
    
    //Funcao que indica se uma certa posicao do tabuleiro pode ser acessada por alguma peca de um certo conjunto
    public boolean posicaoSobAmeaca(Posicao umaPosicao, boolean bBrancas){ //bBrancas representara a cor que esta sob ameaca na posicao umaPosicao
        boolean estaSobAmeaca = false;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Posicao posicaoAnalisada = new Posicao(i, j);
                Peca pecaAnalisada = this.getPecaEmPosicao(posicaoAnalisada);
                if(pecaAnalisada != null && pecaAnalisada.bBrancas != bBrancas){
                    //Peao
                    if(pecaAnalisada.toString().equals("Peao") &&
                            pecaAnalisada.setPosicao(umaPosicao, this, false)){
                        estaSobAmeaca = true;
                    }
                    
                    //Cavalo
                    if(pecaAnalisada.toString().equals("Cavalo") && 
                            pecaAnalisada.setPosicao(umaPosicao, this, false)){
                        estaSobAmeaca = true;
                    }
                    
                    //Bispo
                    if(pecaAnalisada.toString().equals("Bispo") &&
                            pecaAnalisada.setPosicao(umaPosicao, this, false)){
                        estaSobAmeaca = true;
                    }
                    //Torre
                    if(pecaAnalisada.toString().equals("Torre") && 
                            pecaAnalisada.setPosicao(umaPosicao, this, false)){
                        estaSobAmeaca = true;
                    }
                    //Rainha
                    if(pecaAnalisada.toString().equals("Rainha") &&
                            pecaAnalisada.setPosicao(umaPosicao, this, false)){
                        estaSobAmeaca = true;
                    }
                    //Rei
                    if(pecaAnalisada.toString().equals("Rei") && 
                            pecaAnalisada.setPosicao(umaPosicao, this, false)){
                        System.out.println("Rei");
                        estaSobAmeaca = true;
                    }
                }
            }
        }
        return estaSobAmeaca;
    } 
    
    /*O seguinte metodo percorre todas as pecas do conjunto que vai jogar,  e testa todas as posicoes
    possiveis e, apos isso, confere se existe alguma que tire a peca de cheque. Caso esta posicao nao exista e a peca
     tambem esteja em cheque na posicao atual, eh chequemate(retorna 1 ou 2). Caso a posicao nao exista,  mas a peca nao esta em cheque,
    entao a peca nao tem nenhum movimento possivel, caracterizando um empate(retorna 0). Caso contrario, o jogo continua (retorna 1)*/
    public int chequeMate(boolean brancoJoga){
        boolean pecaBloqueada = true;
        int numeroDePecas;
        if(brancoJoga){
            numeroDePecas = this.cBrancas.size();
        }
        else{
            numeroDePecas = this.cPretas.size();
        }
        loopMaior:
        for(int k = 0; k < numeroDePecas; k++){
            Peca pecaAuxiliar;
            if(brancoJoga)
                pecaAuxiliar = this.cBrancas.get(k);
            else
                pecaAuxiliar = this.cPretas.get(k);
            
            Posicao posicaoPecaAuxiliar = new Posicao(pecaAuxiliar.getLinha(), pecaAuxiliar.getColuna());
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    Posicao posicaoPecaAmeacadora = new Posicao(i, j);
                    Peca pecaAmeacadora = this.getPecaEmPosicao(posicaoPecaAmeacadora);
                    
                    boolean pecaMoveu = false;
                    if(!posicaoPecaAmeacadora.igual(posicaoPecaAuxiliar))
                        pecaMoveu = pecaAuxiliar.setPosicao(posicaoPecaAmeacadora, this, false);
                    if(pecaMoveu){
                        
                        this.trocaPecaPosicao(posicaoPecaAuxiliar, posicaoPecaAmeacadora);
                        if(pecaAmeacadora != null){
                            if(brancoJoga)
                                this.cPretas.pecaFora(pecaAmeacadora);
                            else
                                this.cBrancas.pecaFora(pecaAmeacadora);
                        }
                        
                        pecaBloqueada = (pecaBloqueada == true && this.conjuntoEmCheque(brancoJoga) != null);

                        this.trocaPecaPosicao(posicaoPecaAmeacadora, posicaoPecaAuxiliar);
                        if(pecaAmeacadora != null){
                            if(brancoJoga)
                                this.cPretas.add(pecaAmeacadora);
                            else
                                this.cBrancas.add(pecaAmeacadora);
                        }
                    }
                }          
            }
        }
        

        if(pecaBloqueada && this.conjuntoEmCheque(brancoJoga) == null){
            return 0;
        }
        else if(pecaBloqueada && this.conjuntoEmCheque(brancoJoga) != null && !brancoJoga ){
            return 1;
        }
        else if(pecaBloqueada && this.conjuntoEmCheque(brancoJoga) != null && brancoJoga){
            return 2;
        }
        return -1;
        
    }
    
    //Funcao para substituir peao dos extremos   
    public void trocaPeao(String pecaSubstituta, boolean brancoJoga){
        Posicao peaoBranco = this.peaoNosExtremos(true);
        Posicao peaoPreto = this.peaoNosExtremos(false);
        
        if(peaoBranco != null || peaoPreto != null){
              
            if(pecaSubstituta.equals("Rainha")){
                if(peaoBranco != null && !brancoJoga){
                    this.cBrancas.pecaFora(this.getPecaEmPosicao(peaoBranco));
                    this.cBrancas.add(new Rainha("RainhaBranca.png", peaoBranco, true));
                }
                else if(peaoPreto != null && brancoJoga){
                   this.cPretas.pecaFora(this.getPecaEmPosicao(peaoPreto));
                    this.cPretas.add(new Rainha("RainhaPreta.png", peaoPreto, false)); 
                }
            }
            if(pecaSubstituta.equals("Torre")){
                if(peaoBranco != null && !brancoJoga){
                    this.cBrancas.pecaFora(this.getPecaEmPosicao(peaoBranco));
                    this.cBrancas.add(new Torre("TorreBranca.png", peaoBranco, true));
                }
                else if(peaoPreto != null && brancoJoga){
                   this.cPretas.pecaFora(this.getPecaEmPosicao(peaoPreto));
                    this.cPretas.add(new Torre("TorrePreta.png", peaoPreto, false)); 
                }
            }
            if(pecaSubstituta.equals("Bispo")){
                if(peaoBranco != null && !brancoJoga){
                    this.cBrancas.pecaFora(this.getPecaEmPosicao(peaoBranco));
                    this.cBrancas.add(new Bispo("BispoBranco.png", peaoBranco, true));
                }
                else if(peaoPreto != null && brancoJoga){
                   this.cPretas.pecaFora(this.getPecaEmPosicao(peaoPreto));
                    this.cPretas.add(new Bispo("BispoPreto.png", peaoPreto, false)); 
                }
            }
            if(pecaSubstituta.equals("Cavalo")){
                if(peaoBranco != null && !brancoJoga){
                    this.cBrancas.pecaFora(this.getPecaEmPosicao(peaoBranco));
                    this.cBrancas.add(new Cavalo("CavaloBranco.png", peaoBranco, true));
                }
                else if(peaoPreto != null && brancoJoga){
                   this.cPretas.pecaFora(this.getPecaEmPosicao(peaoPreto));
                    this.cPretas.add(new Cavalo("CavaloPreto.png", peaoPreto, false)); 
                }
            }
        }
    }

    public void conjuntoEmChequeException() throws ChequeException{
        if(conjuntoEmCheque(true) != null) throw new ChequeException("Posicao invalida: Pecas brancas estao em cheque!");
        if(conjuntoEmCheque(false) != null) throw new ChequeException("Posicao invalida: Pecas pretas estao em cheque!");
    }
}
