package zad1;

import java.awt.*;
import javax.swing.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.CollationElementIterator;

public class Gui {

    JFrame frame;
    JTextField country;
    JTextField city;
    JTextField currency;
    JTextArea infoArea;

    public Gui() {
        init();
    }

    private void init() {
        // Ustawienie UI Manager
        JFrame dialogFrame = new JFrame();
        dialogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialogFrame.setBounds(100, 100, 300, 200);
        dialogFrame.getContentPane().setBackground(Color.lightGray);
        dialogFrame.setLocationRelativeTo(null);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogFrame.getContentPane().add(dialogPanel);
        dialogPanel.setBackground(Color.ORANGE);


        JLabel countryLabel = new JLabel("Country:");
        JLabel cityLabel = new JLabel("City:");
        JLabel currencyLabel = new JLabel("Currency:");

        // Zwiększenie rozmiaru czcionki dla nagłówków
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        countryLabel.setFont(labelFont);
        cityLabel.setFont(labelFont);
        currencyLabel.setFont(labelFont);

        JTextField countryField = new JTextField();
        JTextField cityField = new JTextField();
        JTextField currencyField = new JTextField();

        // Ustawienie rozmiaru czcionki dla pól tekstowych
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        countryField.setFont(fieldFont);
        cityField.setFont(fieldFont);
        currencyField.setFont(fieldFont);

        dialogPanel.add(countryLabel);
        dialogPanel.add(countryField);
        dialogPanel.add(cityLabel);
        dialogPanel.add(cityField);
        dialogPanel.add(currencyLabel);
        dialogPanel.add(currencyField);

        JButton okButton = new JButton("OK");
        okButton.setFont(labelFont); // Zwiększenie rozmiaru czcionki dla przycisku OK
        dialogPanel.add(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dialogFrame.dispose(); // zamykanie
                String countryText = countryField.getText();
                String cityText = cityField.getText();
                String currencyText = currencyField.getText();
                Service serv = new Service(countryText, cityText, currencyText);
                showMainGUI(countryText, serv.getHumidity(), serv.getTemp(), serv.getNBPRate(), cityText);
            }
        });

        dialogFrame.setVisible(true);
    }

    private void showMainGUI(String countryText, double humidity, double temp, double nbpRate, String cityText) {
        frame = new JFrame();
        frame.setBounds(100, 100, 1200, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel leftPanel = new JPanel(new BorderLayout());
        frame.getContentPane().add(leftPanel, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        leftPanel.add(infoPanel, BorderLayout.NORTH);

        JLabel countryLabel = new JLabel("Country: " + countryText);
        countryLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(countryLabel);

        JLabel humidityLabel = new JLabel("Humidity: " + humidity);
        humidityLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(humidityLabel);

        JLabel tempLabel = new JLabel("Temperature: " + temp);
        tempLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(tempLabel);
        infoPanel.setBackground(Color.ORANGE);

        JLabel nbpRateLabel = new JLabel("NBP Rate: " + nbpRate);
        nbpRateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(nbpRateLabel);

        JFXPanel panelFx = new JFXPanel();
        frame.getContentPane().add(panelFx, BorderLayout.CENTER);

        Platform.runLater(() -> {
            StackPane root = new StackPane();
            Scene scene = new Scene(root, panelFx.getWidth(), panelFx.getHeight());
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            webEngine.load("https://wikipedia.org/wiki/" + cityText);
            root.getChildren().add(webView);
            panelFx.setScene(scene);
        });

        frame.setVisible(true);
    }
}
