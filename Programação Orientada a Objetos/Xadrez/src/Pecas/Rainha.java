package Pecas;

import Xadrez.Tabuleiro;
import auxiliar.Posicao;
import java.io.Serializable;

public class Rainha extends Peca implements Serializable{
    public Rainha(String sAFileName, Posicao aPosicao, boolean bBrancas) {
        super(sAFileName, aPosicao, bBrancas);
    }
    public String toString(){
        return "Rainha";
    }
    public boolean setPosicao(Posicao umaPosicao, Tabuleiro t, boolean fazerMudanca) {
        if(((t.getPecaEmPosicao(umaPosicao) == null || !this.temAMesmaCorQue(t.getPecaEmPosicao(umaPosicao))))){
            int comprimentoLinha = umaPosicao.getLinha() - this.getLinha();
            int comprimentoColuna = umaPosicao.getColuna() - this.getColuna();
            //Condicoes analogas ao movimento do bispo
            if(Math.abs(comprimentoLinha) == Math.abs(comprimentoColuna)){
                //As condicoes abaixo verificam se existe alguma peca no caminho da rainha
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
            
            //Condicoes analogas ao movimento da torre
            else if(Math.abs(comprimentoLinha) == 0 || Math.abs(comprimentoColuna) == 0){
                //As condicoes abaixo verificam se existe alguma peca no caminho da rainha
                if(comprimentoLinha > 0 && comprimentoColuna == 0){
                    for(int i = 1; i < comprimentoLinha; i++){
                        if(t.getPecaEmPosicao(new Posicao(this.getLinha() + i, this.getColuna())) != null){
                            return false;
                        }
                    }
                }
                else if(comprimentoLinha < 0 && comprimentoColuna == 0){
                    for(int i = 1; i < -comprimentoLinha; i++){
                        if(t.getPecaEmPosicao(new Posicao(this.getLinha() - i, this.getColuna())) != null){
                            return false;
                        }
                    }
                }
                else if(comprimentoLinha == 0 && comprimentoColuna > 0){
                    for(int i = 1; i < comprimentoColuna; i++){
                        if(t.getPecaEmPosicao(new Posicao(this.getLinha(), this.getColuna() + i)) != null){
                            return false;
                        }
                    }
                }
                else if(comprimentoLinha == 0 && comprimentoColuna < 0){
                    for(int i = 1; i < -comprimentoColuna; i++){
                        if(t.getPecaEmPosicao(new Posicao(this.getLinha(), this.getColuna() - i)) != null){
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
