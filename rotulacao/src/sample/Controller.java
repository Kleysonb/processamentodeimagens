package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rotulacao.Algoritmo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button abrirArquivo;

    private  Algoritmo algoritmo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void seletorArquivo() {
        //System.out.println("oii");
        Stage mainStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Arquivo");
        try{
            boolean b = fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imagem", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(mainStage);
            if (selectedFile != null) {
                System.out.println("Arquivo Enviado para Interpolação.");
                this.algoritmo = new Algoritmo(selectedFile);
                //this.algoritmo.converterParaBinaria();
            }
//            BufferedImage bufferedImage = ImageIO.read(selectedFile);
//            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException ex) {
            System.out.println("Error");
        }

    }

}