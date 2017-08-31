package interpolacao;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class Interpolacao {

    private File arquivo;
    private int altura, largura;
    private byte[] bytes;

    public Interpolacao(File arquivo) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(arquivo);
        this.arquivo = arquivo;
        this.altura = bufferedImage.getWidth();
        this.largura = bufferedImage.getHeight();

        System.out.println("altura: " + this.altura + "\nlargura: " + this.largura);
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
        System.out.println(data.getData());
        this.bytes = data.getData();
        this.popularMatriz();


    }

    private void exibir(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            System.out.println(bytes[i] + " ");
        }
    }

    private byte[][] popularMatriz() {
        System.out.println("altura: " + altura + "\nlargura: " + largura);
        byte matrizImagem[][] = new byte[altura][largura];
        for (int i = 0; i < altura - 1; i++) {
            for (int j = 0; j < largura - 1; j++) {
                matrizImagem[i][j] = bytes[i * altura + j];

                //System.out.print(((int) matrizImagem[i][j])+128 + " ");
            }
            //System.out.println();
        }
        return matrizImagem;
    }

    public Image vizinhoProxReducao() {
        try {
            System.out.println(this.bytes);
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(this.bytes));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            return image;
        } catch (Exception x) {
            System.out.println("Error");
            return null;
        }
    }

}
