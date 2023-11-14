package Xadrez;

import Auxiliar.ChequeException;
import Auxiliar.Consts;
import Pecas.*;
import auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
//import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

public class Jogo extends javax.swing.JFrame implements MouseListener, KeyListener {

    private Tabuleiro tTabuleiro;//Atributo com a janela de desenho
    public Conjunto cBrancas;
    public Conjunto cPretas;
    boolean bEmJogada;
    boolean brancoJoga; //Variavel que determina qual conjunto vai jogar
    
    //ADICIONAR NA UML
    private Posicao[] tresUltimasPosicoesBranco;
    private Posicao[] tresUltimasPosicoesPreto;
    private int[] aux; //Contador
    
    protected int situacaoJogo; //Variavel que determina situacao do jogo. -1: Em andamento; 1: Brancas venceram; 2: Pretas venceram; 0: Empate
    Peca pecaEmMovimento;

    public enum CoresConjuntos {
        BRANCAS, PRETAS
    };

    public Jogo() {
        cBrancas = new Conjunto();
        cPretas = new Conjunto();
        tTabuleiro = new Tabuleiro(cBrancas, cPretas);//Alocação do painel de desenho
        tTabuleiro.setFocusable(true);        
        tTabuleiro.addMouseListener(this);//Adiciona evento de mouse ao Painel de desenho
        tTabuleiro.addKeyListener(this);
        bEmJogada = false;
        
        brancoJoga = true; //Pecas brancas iniciam o jogo
        situacaoJogo = -1; //Inicia jogo
        pecaEmMovimento = null;
        initComponents();
        
        
        tresUltimasPosicoesBranco = new Posicao[5];
        tresUltimasPosicoesPreto = new Posicao[5]; //Vetores para guardar posicoes (caso de empate por repeticao de movimentos)
        aux = new int[2];
        aux[0] = 0;
        aux[1] = 0;//Contadores auxiliares
    }

    public void addPeca(Peca aPeca, CoresConjuntos aCorConjunto) {
        if (aCorConjunto == CoresConjuntos.BRANCAS) {
            cBrancas.add(aPeca);
        } else {
            cPretas.add(aPeca);
        }
    }
    
    public Peca getPecaClicada(Posicao aPosicao) {
        Peca pTemp = cBrancas.getPecaClicada(aPosicao);
        if (pTemp != null) {
            return pTemp;
        }
        pTemp = cPretas.getPecaClicada(aPosicao);
        if (pTemp != null) {
            return pTemp;
        }
        return null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        cBrancas.AutoDesenho(this.tTabuleiro);
        cPretas.AutoDesenho(this.tTabuleiro);
    }

    public void go() {
        TimerTask task = new TimerTask() {
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.DELAY);
    }

    public Posicao getPosicaoDoClique(MouseEvent aMouseEvent) {
        return new Posicao(aMouseEvent.getY() / Consts.SIZE,
                aMouseEvent.getX() / Consts.SIZE);
    }
    
    public boolean empateRepeticao(){
        for(int i = 0; i < 5; i++){
            if(tresUltimasPosicoesBranco[i] == null || tresUltimasPosicoesPreto[i] == null){
                return false;
            }
        }
        if(tresUltimasPosicoesBranco[0].igual(tresUltimasPosicoesBranco[2]) && tresUltimasPosicoesBranco[2].igual(tresUltimasPosicoesBranco[4]) &&
                tresUltimasPosicoesBranco[1].igual(tresUltimasPosicoesBranco[3]) &&
                
                tresUltimasPosicoesPreto[0].igual(tresUltimasPosicoesPreto[2]) && tresUltimasPosicoesPreto[2].igual(tresUltimasPosicoesPreto[4]) &&
                tresUltimasPosicoesPreto[1].igual(tresUltimasPosicoesPreto[3])){
            return true;
        }
        return false;
        
    }
    
    public void guardarPosicoesAnteriores(Posicao umaPosicao){
        if(brancoJoga){
           tresUltimasPosicoesBranco[aux[0] % 5] = new Posicao(umaPosicao.getLinha(), umaPosicao.getColuna());
           aux[0]++;
        }
        else{
            tresUltimasPosicoesPreto[aux[1] % 5] = new Posicao(umaPosicao.getLinha(), umaPosicao.getColuna());
            aux[1]++;
        }
    }
        
    @Override
    public void mousePressed(MouseEvent e) {
        
        if(empateRepeticao()){//Confere empate por tres movimentos identicos consecutivos de ambas as pecas
            situacaoJogo = 0;
        }
        
        int x = e.getX();//Pega as coordenadas do mouse
        int y = e.getY();
        this.clickLabel.setText("x:" + x + "  y:" + y + "   -   Quadrante: [" + y / Consts.SIZE + "," + x / Consts.SIZE + "]");
        Peca pecaClicada = this.getPecaClicada(this.getPosicaoDoClique(e));

        if(situacaoJogo == -1){ //Jogo em andamento
            if (pecaClicada == null && pecaEmMovimento == null) {
                System.out.println("\nNenhuma peca selecionada\n");
            } else if(pecaClicada != null){
                if(brancoJoga == pecaClicada.bBrancas && pecaEmMovimento == null){
                    System.out.printf("\nPeca " + pecaClicada + " selecionada em (%d, %d)\n",
                            this.getPosicaoDoClique(e).getLinha(), this.getPosicaoDoClique(e).getColuna());
                }
                else if(pecaEmMovimento == null){
                    if(brancoJoga == true)
                        System.out.println("\nAs pecas brancas que jogam!\n");
                    else
                        System.out.println("\nAs pecas pretas que jogam!\n");
                    pecaClicada = null;
                }
            }
            if (bEmJogada) {
                if (pecaClicada == null) { //soh movimenta
                    Posicao posicaoAnterior = new Posicao(pecaEmMovimento.getLinha(), pecaEmMovimento.getColuna());
                    System.out.printf("Posicao anterior: (%d %d)\n", posicaoAnterior.getLinha(), posicaoAnterior.getColuna());
                    if (this.getPosicaoDoClique(e).getLinha() < 8 &&
                        this.getPosicaoDoClique(e).getLinha() >= 0 &&
                        this.getPosicaoDoClique(e).getColuna() < 8 &&
                        this.getPosicaoDoClique(e).getLinha() >= 0 && //Nao permite posicionar pecas fora do tabuleiro
                            pecaEmMovimento.setPosicao(this.getPosicaoDoClique(e), this.tTabuleiro, true)) {
                            try{ //Evitando movimento que proporcione cheque
                                situacaoJogo = tTabuleiro.chequeMate(!brancoJoga);
                                this.tTabuleiro.conjuntoEmChequeException();
                                System.out.printf("Movimento realizado: (%d, %d)\n",
                                    this.getPosicaoDoClique(e).getLinha(), this.getPosicaoDoClique(e).getColuna());
                               this.guardarPosicoesAnteriores(this.getPosicaoDoClique(e));
                                    /*Guardando posicoes das pecas para contabilizar movimentos iguais e decidir empate ou nao.
                                    Apenas interessa movimentos em espacos vazios, que eh quando as repeticoes acontecem*/
                            }
                            
                            catch(ChequeException exp){
                                if(pecaEmMovimento != null && tTabuleiro.conjuntoEmCheque(brancoJoga) != null){
                                    tTabuleiro.trocaPecaPosicao(pecaEmMovimento.getPosicao(), posicaoAnterior);
                                    System.out.println(exp.getMessage());
                                    brancoJoga = !brancoJoga;
                                }
                                else{
                                    situacaoJogo = tTabuleiro.chequeMate(!brancoJoga);
                                    System.out.printf("Movimento realizado: (%d, %d)\n",
                                    this.getPosicaoDoClique(e).getLinha(), this.getPosicaoDoClique(e).getColuna());

                                }
                            }
                            pecaEmMovimento = null;
                            bEmJogada = false;
                            brancoJoga = !brancoJoga;
                            
                        } else {
                            System.out.println("Jogada ainda em movimento, selecione uma posicao valida");
                        }
                    } else {
                        Posicao posicaoAnterior = new Posicao(pecaEmMovimento.getLinha(), pecaEmMovimento.getColuna());

                        if (pecaEmMovimento.setPosicao(this.getPosicaoDoClique(e), this.tTabuleiro, true)){
                            if(pecaEmMovimento == pecaClicada){ //Deselecionando peca
                                System.out.println("A peca foi deselecionada, escolha outra peca");
                                pecaEmMovimento = null;
                                bEmJogada = false;
                            }
                            else{ //Comendo peca
                                if(!pecaEmMovimento.temAMesmaCorQue(pecaClicada)){
                                    Peca pecaFora = pecaClicada;
                                    try{ //Evitando movimento que proporcione cheque
                                        if(!pecaClicada.bBrancas){
                                            cPretas.pecaFora(pecaClicada);
                                        }
                                        else{
                                            cBrancas.pecaFora(pecaClicada);
                                        }
                                        situacaoJogo = tTabuleiro.chequeMate(!brancoJoga);
                                        this.tTabuleiro.conjuntoEmChequeException();
                                        System.out.printf("Movimento realizado: (%d, %d)\n",
                                            this.getPosicaoDoClique(e).getLinha(), this.getPosicaoDoClique(e).getColuna());
                                            this.guardarPosicoesAnteriores(this.getPosicaoDoClique(e));
                                        
                                    }
                                    catch(ChequeException exp){
                                        if(pecaEmMovimento != null && tTabuleiro.conjuntoEmCheque(brancoJoga) != null){
                                            tTabuleiro.trocaPecaPosicao(pecaEmMovimento.getPosicao(), posicaoAnterior);
                                            if(pecaFora.bBrancas){
                                                this.addPeca(pecaFora, CoresConjuntos.BRANCAS);
                                            }
                                            else{
                                                this.addPeca(pecaFora, CoresConjuntos.PRETAS);
                                            }
                                            
                                            System.out.println(exp.getMessage());
                                            brancoJoga = !brancoJoga;
                                        }
                                        else{
                                            situacaoJogo = tTabuleiro.chequeMate(!brancoJoga);
                                            System.out.printf("Movimento realizado: (%d, %d)\n",
                                            this.getPosicaoDoClique(e).getLinha(), this.getPosicaoDoClique(e).getColuna());
                                            this.guardarPosicoesAnteriores(this.getPosicaoDoClique(e));

                                        }
                                    }
                                    pecaEmMovimento = null;
                                    bEmJogada = false;
                                    brancoJoga = !brancoJoga;
                                    }

                            }
                        } else { //Posicao invalida escolhida
                            System.out.println("Jogada ainda em movimento, selecione uma posicao valida");
                        }
                    }
                } else { //Peca selecionada e pronta para ser mexida
                    if (pecaClicada != null) {
                        System.out.println("Movimentacao em andamento, selecione o destino da peca");
                        pecaEmMovimento = pecaClicada;
                        bEmJogada = true;
                    }
                }
            repaint();
        }
        else{
            System.out.println("FIM DE JOGO");
            switch (situacaoJogo) {
                case 1:
                    System.out.println("As pecas brancas venceram!");
                    break;
                case 2:
                    System.out.println("As pecas pretas venceram!");
                    break;
                default:
                    System.out.println("Empate");
                    break;
            }
        }
    }
        
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_S){
            File tanque = new File("estados"+File.separator+"POO.zip");
            try{
                tanque.createNewFile();
                FileOutputStream canoOut = new FileOutputStream(tanque);
                GZIPOutputStream compactador = new GZIPOutputStream(canoOut);
                ObjectOutputStream serializador = new ObjectOutputStream(compactador);
                serializador.writeObject(this.cBrancas);
                serializador.writeObject(this.cPretas);
                serializador.flush();
                serializador.close();
                System.out.println("Jogo salvo com sucesso");
            }catch(Exception execpt){
                System.out.println("Ocorreu o seguinte erro " + execpt.getMessage());    
            }
         }
        
        // Troca de peao com peca escolhida pelo jogador quando atinge o extremo do tabuleiro
        if(e.getKeyCode() == KeyEvent.VK_R){
            tTabuleiro.trocaPeao("Rainha", brancoJoga);
        }
        if(e.getKeyCode() == KeyEvent.VK_T){
            tTabuleiro.trocaPeao("Torre", brancoJoga);
        }
        if(e.getKeyCode() == KeyEvent.VK_B){
            tTabuleiro.trocaPeao("Bispo", brancoJoga);
        }
        if(e.getKeyCode() == KeyEvent.VK_C){
            tTabuleiro.trocaPeao("Cavalo", brancoJoga);
        }
        
        //Empate declarado
        if(e.getKeyCode() == KeyEvent.VK_E){
            System.out.println("Empate declarado pelos jogadores");
            situacaoJogo = 0;
        }

        repaint();
    }

    public void mouseClicked(MouseEvent e) {

    }
    
    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCenario = tTabuleiro;
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        coordenadaLabel = new javax.swing.JLabel();
        clickLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SCC0204 - Xadrez");
        setResizable(false);

        jPanelCenario.setMaximumSize(new java.awt.Dimension(800, 800));
        jPanelCenario.setMinimumSize(new java.awt.Dimension(800, 800));
        jPanelCenario.setPreferredSize(new java.awt.Dimension(800, 800));
        jPanelCenario.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout jPanelCenarioLayout = new javax.swing.GroupLayout(jPanelCenario);
        jPanelCenario.setLayout(jPanelCenarioLayout);
        jPanelCenarioLayout.setHorizontalGroup(
            jPanelCenarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelCenarioLayout.setVerticalGroup(
            jPanelCenarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel2.setText("Coordenada:");

        jLabel3.setText("click:");

        coordenadaLabel.setText("10");

        clickLabel.setText("quadrante");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelCenario, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 219, Short.MAX_VALUE)
                                .addComponent(clickLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(coordenadaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(293, 293, 293))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelCenario, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(clickLabel)
                        .addComponent(coordenadaLabel)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel clickLabel;
    private javax.swing.JLabel coordenadaLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelCenario;
    // End of variables declaration//GEN-END:variables

}
