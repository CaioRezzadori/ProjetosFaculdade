package Pecas;

import Xadrez.Jogo;
import Xadrez.Tabuleiro;
import auxiliar.Posicao;
import java.io.Serializable;

public class Bispo extends Peca implements Serializable {

    public Bispo(String sAFileName, Posicao aPosicao, boolean bBrancas) {
        super(sAFileName, aPosicao, bBrancas);
    }

    public String toString() {
        return "Bispo";
    }

    @Override
    public boolean setPosicao(Posicao umaPosicao, Tabuleiro t, boolean fazerMudanca) {
        if(((t.getPecaEmPosicao(umaPosicao) == null || !this.temAMesmaCorQue(t.getPecaEmPosicao(umaPosicao))))){
            int comprimentoLinha = umaPosicao.getLinha() - this.getLinha();
            int comprimentoColuna = umaPosicao.getColuna() - this.getColuna();
            if(Math.abs(comprimentoLinha) == Math.abs(comprimentoColuna)){
                //As condicoes abaixo verificam se existe alguma peca no caminho do bispo
                if(comprimentoLinha > 0 && comprimentoColuna > 0){
                    for(int i = 1; i < comprimentoLinha; i++){
                        if(t.getPecaEmPosicao(new Posicao(this.getLinha() + i, this.getColuna() + i)) != null){
                            return false;
                        }
                    }
                }
                else if(comprimentoLinha > 0 && comprimentoColuna < 0){
                    for(int i = 1; i < comprimentoLinha; i++){
                        if(t.getPecaEmPosicao(new Posicao(this.getLinha() + i, this.getColuna() - i)) != null){
                            return false;
                        }
                    }
                }
                else if(comprimentoLinha < 0 && comprimentoColuna > 0){
                    for(int i = 1; i < -comprimentoLinha; i++){
                        if(t.getPecaEmPosicao(new Posicao(this.getLinha() - i, this.getColuna() + i)) != null){
                            return false;
                        }
                    }
                }
                else if(comprimentoLinha < 0 && comprimentoColuna < 0){
                    for(int i = 1; i < -comprimentoLinha; i++){
                        if(t.getPecaEmPosicao(new Posicao(this.getLinha() - i, this.getColuna() - i)) != null){
                            return false;
                        }
                    }
                }
                if(fazerMudanca){
                    this.pPosicao.setPosicao(umaPosicao);
                }
                return true;
            }
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
