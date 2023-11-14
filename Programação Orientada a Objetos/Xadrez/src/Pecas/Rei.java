package Pecas;

import Xadrez.Tabuleiro;
import auxiliar.Posicao;
import java.io.Serializable;

public class Rei extends Peca implements Serializable{
    
    public Rei(String sAFileName, Posicao aPosicao, boolean bBrancas) {
        super(sAFileName, aPosicao, bBrancas);
    }
    
    @Override
    public String toString(){
        return "Rei";
    }
    
    @Override
    public boolean setPosicao(Posicao umaPosicao, Tabuleiro t, boolean fazerMudanca) {
                if(((t.getPecaEmPosicao(umaPosicao) == null || !this.temAMesmaCorQue(t.getPecaEmPosicao(umaPosicao)))) && (//Espaco vazio ou com pecas de cor oposta
                (Math.abs(this.getLinha() - umaPosicao.getLinha()) < 2 &&
                Math.abs(this.getColuna() - umaPosicao.getColuna()) < 2) //Movimento 
                )){
            if(fazerMudanca){
                this.pPosicao.setPosicao(umaPosicao);
            }
            return true;
        }
        else if(this.pPosicao.igual(umaPosicao)){//Deselecionando peca
            if(fazerMudanca){
                this.pPosicao.setPosicao(umaPosicao);
            }
            return true;
        }
        return false;
    }
}