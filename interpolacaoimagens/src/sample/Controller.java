package sample;


import interpolacao.Interpolacao;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {

    @FXML
    private Button abrirArquivo;

    @FXML
    private ImageView vizinhoReducao;

    @FXML
    private ImageView vizinhoAmpliacao;

    @FXML
    private ImageView bilinearReducao;

    @FXML
    private ImageView bilinearAmpliacao;

    private Interpolacao interpolacao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //this.interpolacao = new Interpolacao();
    }

    @FXML
    private void seletorArquivo() throws IOException {
        //System.out.println("oii");
        Stage mainStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Arquivo");
        boolean b = fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagem", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            System.out.println("Arquivo Enviado para Interpolação.");
            this.interpolacao = new Interpolacao(selectedFile);
            //this.interpolacao.seletorArquivo(selectedFile);
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            //System.out.println("aqui");
            this.vizinhoReducao.setImage(this.interpolacao.vizinhoProxReducao());
            this.vizinhoAmpliacao.setImage(image);
            //this.bilinearReducao.setImage(this.interpolacao.bilinearReducao());
            this.bilinearAmpliacao.setImage(image);
        } catch (IOException ex) {
            //Logger.getLogger(JavaFXPixel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
