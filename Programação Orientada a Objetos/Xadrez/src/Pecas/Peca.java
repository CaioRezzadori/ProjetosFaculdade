package Pecas;

import Auxiliar.Consts;
//import Xadrez.Jogo;
import Xadrez.Tabuleiro;
import auxiliar.Posicao;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;


public abstract class Peca implements Serializable{
    protected ImageIcon iImage;
    protected Posicao pPosicao;
    public boolean bBrancas;

    protected Peca(String sAFileName, Posicao aPosicao, boolean bBrancas) {
        this.pPosicao = aPosicao;
        this.bBrancas = bBrancas;
        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath()+Consts.PATH + sAFileName);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }        
    }
    
    public Posicao getPosicao() {
        return pPosicao;
    }

    public int getColuna() {
        return pPosicao.getColuna();
    }
    
    public int getLinha() {
        return pPosicao.getLinha();
    }    
    
    public boolean temAMesmaCorQue(Peca umaPeca){       
        return (this.bBrancas == umaPeca.bBrancas);
    }
    
    public boolean verificaCheque(Tabuleiro tTabuleiro, Posicao umaPosicao){
        if(tTabuleiro.getPecaEmPosicao(umaPosicao).toString().equals("Rei") && tTabuleiro.posicaoSobAmeaca(umaPosicao, bBrancas)){
            return true;
        }
        return false;
    }
   
    public void autoDesenho(Tabuleiro tTabuleiro){
        iImage.paintIcon(tTabuleiro, (Graphics2D)tTabuleiro.getGraphics(),
                         pPosicao.getColuna() * Consts.SIZE, pPosicao.getLinha() * Consts.SIZE);        
    }
    
    public boolean foiClicada(Posicao aPosicao){
        return this.pPosicao.igual(aPosicao);
    }
    
    public abstract boolean setPosicao(Posicao umaPosicao, Tabuleiro t, boolean fazerMudanca);
}
