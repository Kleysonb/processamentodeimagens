package rotulacao;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Algoritmo {

    private int[][] matrizPixels;
    private BufferedImage img;

    public Algoritmo(File arquivo) throws IOException {
        this.img = ImageIO.read(arquivo);
        this.matrizPixels = GetSetPixels.gerarMatrizPixel(this.img);
    }

    public void rotular() {
        System.out.println("Rotulando elementos");
        Map<Integer, Integer> equivalente = new HashMap<Integer, Integer>();

        ArrayList<Integer> chave = new ArrayList<>();
        ArrayList<Integer> valor = new ArrayList<>();

        int altura = img.getWidth();
        int largura = img.getHeight();
        int BRANCO = -1;
        int PRETO = 0;

        int novaMatrizPixel[][] = new int[altura][largura];
        int matrizBinaria[][] = converterParaBinaria();

        int label = 15;
        //ArrayList<Integer> labelUsada = new ArrayList<>();

        for (int i = 1; i < altura; i++) {
            for (int j = 1; j < largura; j++) {
                //Se for Branco, move para o próximo Pixel
                if (matrizBinaria[i][j] == PRETO) {
                    //Se for Preto, analisa-se s e r
                    int r = matrizBinaria[i - 1][j];
                    int s = matrizBinaria[i][j - 1];
                    if (s == r && r == BRANCO) {
                        //Se s e r forem branco e p for preto assinala-se um novo label para p e
                        //anota-se que esse label já foi usado
                        novaMatrizPixel[i][j] = label;
                        //labelUsada.add(label);
                        label++;
                    } else {
                        if (s == r && r == PRETO) {
                            //Se r e s forem preto e possuem diferentes labels, assinala-se
                            //um dos labels a p e anota-se que esses labels são
                            //equivalentes.
                            if (novaMatrizPixel[i][j - 1] != novaMatrizPixel[i - 1][j]) {
                                //p recebe r
                                novaMatrizPixel[i][j] = novaMatrizPixel[i - 1][j];
                                //Assinalando equivalência, usando a tabela ASCII
                                //System.out.println("i: " + i + "  j: " + j);
                                //System.out.println(novaMatrizPixel[i-1][j] + " = " + novaMatrizPixel[i][j-1]);
                                equivalente.put(novaMatrizPixel[i - 1][j], new Integer(novaMatrizPixel[i][j - 1]));
                                chave.add(novaMatrizPixel[i - 1][j]);
                                valor.add(novaMatrizPixel[i][j - 1]);
                            } else {
                                //Se r e s são preto e possuem labels iguais
                                //p recebe r
                                //System.out.println("Label Igual");
                                novaMatrizPixel[i][j] = novaMatrizPixel[i - 1][j];
                            }
                        } else {
                            if (r == PRETO || s == PRETO) {
                                if (r == PRETO) {
                                    //p recebe r
                                    novaMatrizPixel[i][j] = novaMatrizPixel[i - 1][j];
                                } else {
                                    //p recebe s
                                    novaMatrizPixel[i][j] = novaMatrizPixel[i][j - 1];
                                }
                            }
                        }
                    }
                }
//                else {
//                    novaMatrizPixel[i][j] = BRANCO;
//                }
            }
        }
        //exibirAux(altura, largura, novaMatrizPixel);
        System.out.println();
        verificarEquivalencia(altura, largura, novaMatrizPixel, chave, valor);
        //GetSetPixels.exibirImagem(altura, largura, novaMatrizPixel);
    }

    private void verificarEquivalencia(int altura, int largura, int[][] novaMatrizPixel, ArrayList<Integer> chave, ArrayList<Integer> valor) {
        System.out.println("Verificando Equivalência");
        //int novaMatriz[][] = new int[altura][largura];
        ArrayList<Integer> passou = new ArrayList<>();

        for(int i = 0; i < chave.size(); i++){
            if(chave.get(i) == 15){
                System.out.println(15 + " : " + valor.get(i));
            }
        }

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                if (novaMatrizPixel[i][j] != 0) {
                    int x;

                }
            }
        }

        //exibirAux(altura, largura, novaMatrizPixel);
    }

    private void verificarEquivalencia(Map<Integer, Integer> equivalente, int altura, int largura, int[][] novaMatrizPixel) {
        System.out.println("Verificando Equivalência");
        //int novaMatriz[][] = new int[altura][largura];
        ArrayList<Integer> passou = new ArrayList<>();

        for (int key : equivalente.keySet()) {
            int value = equivalente.get(key);
            System.out.println(key + " = " + value);
        }
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                if (novaMatrizPixel[i][j] != 0) {
                    passou.add(novaMatrizPixel[i][j]);
                    int x;
                    while (equivalente.get(novaMatrizPixel[i][j]) != null) {
                        int aux = equivalente.get(novaMatrizPixel[i][j]);
                        passou.add(aux);
                        novaMatrizPixel[i][j] = aux;
                        //novaMatriz[i][j] = 8;
                    }
                }
            }
        }
        exibirAux(altura, largura, novaMatrizPixel);
    }


    private void exibirAux(int altura, int largura, int[][] novaMatrizPixel) {
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                System.out.print(novaMatrizPixel[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[][] converterParaBinaria() {
        System.out.println("Convertendo imagem para binária");
        int altura = img.getWidth();
        int largura = img.getHeight();

        int novaMatrizPixel[][] = new int[altura][largura];

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {

                if ((this.matrizPixels[i][j] & 0xff) > 127) {
                    //Branco
                    novaMatrizPixel[i][j] = -1;
                } else {
                    //Preto
                    novaMatrizPixel[i][j] = 0;
                }
            }
        }

        //exibirAux(largura, altura, novaMatrizPixel);
        //GetSetPixels.exibirImagem(altura, largura, novaMatrizPixel);
        return novaMatrizPixel;

    }


}
