package histograma;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Algoritmo {

    private int[][] matrizPixels;
    private BufferedImage img;
    private int totalPixels;
    public int[] histogramaImagem;

    public ArrayList<Integer[]> picos;

    public Algoritmo(File arquivo) throws IOException {
        this.img = ImageIO.read(arquivo);
        this.matrizPixels = GetSetPixels.gerarMatrizPixel(this.img);
        this.histogramaImagem = new int[256];
        this.picos = new ArrayList<>();
    }

    public void acharPicos() {
        boolean teste = false;
        for (int i = 1; i < histogramaImagem.length; i++) {
            if (histogramaImagem[i] > histogramaImagem[i - 1]) {
                teste = true;
            } else {
                if (teste) {
                    picos.add(new Integer[]{i - 1, histogramaImagem[i - 1]});
                    teste = false;
                }
            }
        }
    }

    public void vale() {
        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        this.totalPixels = altura * largura;

        int[][] imagemNiveldeCinza = imagemNiveldeCinza();
        GetSetPixels.exibirImagem(altura, largura, imagemNiveldeCinza, "nivel_de_cinza");

        int[][] imagemEqualizada = imagemNiveldeCinza;


        imprimirHistograma();
        acharPicos();
        imprimirPicos();
        int[] maioresPicos = pegar5maioresPicos();
        System.out.println(maioresPicos[0] + " - " + maioresPicos[1] + " - " + maioresPicos[2] + " - " + maioresPicos[3] + " - " + maioresPicos[4]);
        int limiar1 = pegarLimiar(maioresPicos[0], maioresPicos[1]);
        int limiar2 = pegarLimiar(maioresPicos[1], maioresPicos[2]);
        int limiar3 = pegarLimiar(maioresPicos[2], maioresPicos[3]);
        int limiar4 = pegarLimiar(maioresPicos[3], maioresPicos[4]);
        gerarImagemLimiraizada(limiar1, limiar2, limiar3 ,limiar4, imagemNiveldeCinza);
    }

    public void gerarImagemLimiraizada(int limiar1, int limiar2, int limiar3, int limiar4, int[][] imagemNivelCinza) {
        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        int[][] novaImagem = new int[altura][largura];
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                Color cor = new Color(imagemNivelCinza[i][j], true);
                int corPixel = cor.getRed();
                if (corPixel < limiar1) {
                    novaImagem[i][j] = Color.black.getRGB();
                } else if (corPixel < limiar2) {
                    novaImagem[i][j] = new Color(limiar1,limiar1,limiar1 ).getRGB();
                } else if (corPixel < limiar3) {
                    novaImagem[i][j] = new Color(limiar2,limiar2,limiar2).getRGB();
                } else if (corPixel < limiar4) {
                    novaImagem[i][j] = new Color(limiar3,limiar3,limiar3).getRGB();
                } else
                    novaImagem[i][j] = Color.white.getRGB();
            }
        }
        GetSetPixels.exibirImagem(altura, largura, novaImagem, "ImagemLimiarizada");
    }

    public void imprimirHistograma() {
        for (int i = 0; i < 256; i++) {
            System.out.println("Nivel: " + i + " = " + histogramaImagem[i]);
        }
    }

    public void imprimirPicos() {
        System.out.println();
        System.out.println("Numero de Picos: " + picos.size());
        for (int i = 0; i < picos.size(); i++) {
            System.out.print(picos.get(i)[0] + "(" + picos.get(i)[1] + ")");
            if (i != picos.size() - 1) {
                System.out.print(" - ");
            }
        }
        System.out.println();
    }

    public int[] pegar2maioresPicos() {
        int maior1 = 0;
        int maior2 = 1;

        for (int i = 2; i < picos.size(); i++) {
            if (picos.get(i)[1] > picos.get(maior1)[1]) {
                if (picos.get(maior2)[1] > picos.get(maior1)[1]) {
                    maior1 = maior2;
                    maior2 = i;
                } else {
                    maior2 = i;
                }
            } else {
                if (picos.get(i)[1] > picos.get(maior2)[1]) {
                    maior2 = i;
                }
            }
        }
        maior1 = picos.get(maior1)[0];
        maior2 = picos.get(maior2)[0];

        int[] picos = new int[]{maior1, maior2};
        return picos;
    }

    public int[] pegar5maioresPicos() {
        int maior1 = 0;
        int maior2 = 1;
        int maior3 = 2;
        int maior4 = 3;
        int maior5 = 4;

        for (int i = 5; i < picos.size(); i++) {
            if (picos.get(i)[1] > picos.get(maior1)[1]) {
                if (picos.get(maior2)[1] > picos.get(maior1)[1]) {
                    if (picos.get(maior3)[1] > picos.get(maior2)[1]) {
                        if (picos.get(maior4)[1] > picos.get(maior3)[1]) {
                            if (picos.get(maior5)[1] > picos.get(maior4)[1]) {
                                maior1 = maior2;
                                maior2 = maior3;
                                maior3 = maior4;
                                maior4 = maior5;
                                maior5 = i;
                            } else {
                                maior5 = i;
                            }
                        } else {
                            maior4 = i;
                        }
                    } else {
                        maior3 = i;
                    }
                } else {
                    maior2 = i;
                }
            } else {
                if (picos.get(i)[1] > picos.get(maior2)[1]) {
                    if (picos.get(maior3)[1] > picos.get(maior2)[1]) {
                        if (picos.get(maior4)[1] > picos.get(maior3)[1]) {
                            if (picos.get(maior5)[1] > picos.get(maior4)[1]) {
                                maior2 = maior3;
                                maior3 = maior4;
                                maior4 = maior5;
                                maior5 = i;
                            } else {
                                maior5 = i;
                            }
                        } else {
                            maior4 = i;
                        }
                    } else {
                        maior3 = i;
                    }
                } else {
                    if (picos.get(i)[1] > picos.get(maior3)[1]) {
                        if (picos.get(maior4)[1] > picos.get(maior3)[1]) {
                            if (picos.get(maior5)[1] > picos.get(maior4)[1]) {
                                maior3 = maior4;
                                maior4 = maior5;
                                maior5 = i;
                            } else {
                                maior5 = i;
                            }
                        } else {
                            maior4 = i;
                        }
                    } else {
                        if (picos.get(i)[1] > picos.get(maior4)[1]) {
                            if (picos.get(maior5)[1] > picos.get(maior4)[1]) {
                                maior3 = maior4;
                                maior4 = maior5;
                                maior5 = i;
                            } else {
                                maior5 = i;
                            }
                        }
                    }
                }
            }
        }
        maior1 = picos.get(maior1)[0];
        maior2 = picos.get(maior2)[0];
        maior3 = picos.get(maior3)[0];
        maior4 = picos.get(maior4)[0];
        maior5 = picos.get(maior5)[0];

        int[] picos = new int[]{maior1, maior2, maior3, maior4, maior5};
        return picos;
    }

    public int pegarLimiar(int pico1, int pico2) {
        int menor = pico1;
        for (int i = pico1 + 1; i <= pico2; i++) {
            if (histogramaImagem[i] < histogramaImagem[menor]) {
                menor = i;
            }
        }

        return menor;
    }

    public void verificarPixelInt(int pixel) {
        histogramaImagem[pixel]++;
    }

    public int[][] imagemNiveldeCinza() {
        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        int[][] imagemNivelCinza = new int[altura][largura];
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                imagemNivelCinza[i][j] = suporteCorNivelCinza(this.matrizPixels[i][j]);
            }
        }
        return imagemNivelCinza;
    }

    private int suporteCorNivelCinza(int pixel) {
        Color cor = new Color(pixel, true);
        int novoPixel;

        int red = cor.getRed();
        int green = cor.getGreen();
        int blue = cor.getBlue();

        int nivelDeCinza = (red + green + blue) / 3;

        int alpha = nivelDeCinza;
        red = nivelDeCinza;
        green = nivelDeCinza;
        blue = nivelDeCinza;

        verificarPixelInt(nivelDeCinza);

        Color novaCor = new Color(red, green, blue, alpha);
        novoPixel = novaCor.getRGB();
        return novoPixel;
    }
}
