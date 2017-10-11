package laplaciano;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class GetSetPixels {
    public static int[][] gerarMatrizPixel(BufferedImage img) {
        //Recuperando Altura e Largura da Imagem
        int altura = img.getWidth();
        int largura = img.getHeight();
        System.out.println("Altura: " + altura + "\nLargura: " + largura + "\n");

        //Criando matriz que receberá os pixels da imagem real
        int matrizPixel[][] = new int[altura][largura];
        //Populando matriz com os pixels
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                matrizPixel[i][j] = img.getRGB(i, j);
            }
        }

        //exibirImagem(altura, largura, matrizPixel, img);
        return matrizPixel;
    }

    public static File exibirImagem(int altura, int largura, int[][] matrizPixel, String nome) {
        //Gerando uma nova imagem com as suas respectivas dimensões
        System.out.println("Gerando Imagem Nova");
        BufferedImage novaImagem = new BufferedImage(altura, largura, BufferedImage.TYPE_INT_RGB);

        //Atualizando a imagem gerada pixel a pixel
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                //Dada as coordenadas 'i' e 'j', temos cada posição de pixel da nova imagem, que pode ser alterada por qualquer pixel desejado
                novaImagem.setRGB(i, j, matrizPixel[i][j]);
            }
        }
        try {
            System.out.println("Nova Imagem Gerada");
            File f = new File(nome+".jpg");
            ImageIO.write(novaImagem, "jpg", f);
            return f;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

}