package sample;


import conversao.Algoritmo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private Button abrirArquivo;

    @FXML
    private Label binariaTxt;

    @FXML
    private Label rotuladaTxt;

    @FXML
    private ComboBox<?> modeloPixel;

    @FXML
    private ComboBox<?> modeloConversao;

    @FXML
    private TextField cor1;

    @FXML
    private TextField cor2;

    @FXML
    private TextField cor3;


    @FXML
    private TextField corConversao1;

    @FXML
    private TextField corConversao2;

    @FXML
    private TextField corConversao3;

    private Algoritmo algoritmo;

    private ArrayList<String> opcoesArray;

    private ObservableList opcoes;


    @FXML
    void converterCores() throws IOException {
        String modeloIn = modeloPixel.getSelectionModel().getSelectedItem().toString();
        String modeloOut = modeloConversao.getSelectionModel().getSelectedItem().toString();

        double cor_1 = Double.parseDouble(cor1.getText());
        double cor_2 = Double.parseDouble(cor2.getText());
        double cor_3 = Double.parseDouble(cor3.getText());

        algoritmo = new Algoritmo(modeloIn, modeloOut, cor_1, cor_2, cor_3);
        if(modeloOut != "RGB"){
            double[] resultado = algoritmo.resultadoDouble;
            corConversao1.setText(resultado[0]+"");
            corConversao2.setText(resultado[1]+"");
            corConversao3.setText(resultado[2]+"");
        }else{
            int[] resultado = algoritmo.resultadoInt;
            corConversao1.setText(resultado[0]+"");
            corConversao2.setText(resultado[1]+"");
            corConversao3.setText(resultado[2]+"");
        }
    }

    @FXML
    void modeloPCombo() {
        String modelo = modeloPixel.getSelectionModel().getSelectedItem().toString();
        int posicaoModeloEscolhido = modeloPixel.getSelectionModel().getSelectedIndex();
        System.out.println(modelo + " -- " + posicaoModeloEscolhido);

        ObservableList novasOpcoesSemModeloEscolhido = FXCollections.observableArrayList(opcoesArray);
        novasOpcoesSemModeloEscolhido.remove(posicaoModeloEscolhido);
        System.out.println(novasOpcoesSemModeloEscolhido.toString());

        modeloConversao.setItems(novasOpcoesSemModeloEscolhido);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.opcoesArray = new ArrayList<>();
        this.opcoesArray.add("RGB");
        this.opcoesArray.add("HSV");
        this.opcoesArray.add("CMY");

        this.opcoes = FXCollections.observableArrayList("RGB", "HSV", "CMY");
        modeloPixel.setItems(opcoes);

    }
}