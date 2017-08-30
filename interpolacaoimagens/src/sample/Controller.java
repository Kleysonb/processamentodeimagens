package sample;

import interpolacao.Bilinear;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button abrirArquivo;

    private Bilinear bil;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.bil = new Bilinear();
    }

    @FXML
    private void seletorArquivo() {
        //System.out.println("oii");
        Stage mainStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        boolean b = fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            //mainStage.display(selectedFile);
            //System.out.println(selectedFile);
            this.bil.seletorArquivo(selectedFile);
            //ImageIO img = new ImageIO.(selectedFile);
        }
    }

}
