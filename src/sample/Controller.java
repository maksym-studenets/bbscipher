package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class Controller implements Initializable {
    public TextField vP;
    public TextField vQ;
    public TextField vSeed;
    public TextArea vReadText;
    public TextArea vWriteText;

    public void code(ActionEvent actionEvent) {
        String text = vReadText.getText().toLowerCase().replaceAll(" ","");
        char[] charText = text.toCharArray();
        byte[] byteText = text.getBytes();
        double p = Double.parseDouble(vP.getText());
        double q = Double.parseDouble(vQ.getText());
        double seed = Double.parseDouble(vSeed.getText());
        if (!((p%4==3) && (q%4==3) && (99<p &&p<1000) && (99<q && q<1000))) {
            vWriteText.setText("Invalid p or q");
            return;
        }
        bbs b = new bbs(p,q,seed);
        for(int j = 0; j <= byteText.length-1; ++j) {
            vWriteText.appendText(""+(b.getrandom())+'\n');
            byteText[j] =(byte)  (byteText[j] ^ (byte)b.getrandom());
        }
        vWriteText.appendText("Byte text - " + new String(byteText));

    }

    public void toBinary(int n){
        String binary = "";
        while (n > 0) {
            int d = n & 1;
            binary = d + binary;
            n = n >> 1;
        }
    }
    public void reverse(ActionEvent actionEvent) {
        String tmp = vReadText.getText();
        vReadText.setText(vWriteText.getText());
        vWriteText.setText(tmp);
    }

    public void readText(ActionEvent actionEvent) {
        try {
            vReadText.setText(readFromFile(openFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeText(ActionEvent actionEvent) {
        try {
            FileWriter writer = new FileWriter(openFile().getAbsolutePath());
            writer.write(vWriteText.getText());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public File openFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        return fileChooser.showOpenDialog(new Stage());
    }
    public String readFromFile(File file) throws IOException {
        if (file!=null) {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            StringBuilder text = new StringBuilder();
            String s;
            while ((s = in.readLine()) != null) {
                text.append(s);
                text.append("\n");
            }
            in.close();
            return text.toString();
        }
        return "";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int p =0;
        int q = 0;
        while(!((p%4==3) && (q%4==3))){
            p = ThreadLocalRandom.current().nextInt(100, 1000);
            q = ThreadLocalRandom.current().nextInt(100, 1000);
        }
        vP.setText(""+p);
        vQ.setText(q+"");
        int x =ThreadLocalRandom.current().nextInt(100, 1000);;
        while (!bbs.check_if_mutually_prime(x,p*q)){
            x = ThreadLocalRandom.current().nextInt(100, 1000);
            System.out.print(x +" ");
        }
        vSeed.setText(x+"");
    }
}
