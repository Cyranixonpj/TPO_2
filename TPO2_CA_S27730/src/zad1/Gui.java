package zad1;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextArea;

public class Gui {

    JFrame frame;
    private JTextField country;
    private JTextField city;
    private JTextField currency;

    public Gui() {
        init();
    }

    private void init() {
        frame = new JFrame();
        frame.setBounds(100, 100,1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JFXPanel panel = new JFXPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        JPanel panel2 = new JPanel();
        frame.getContentPane().add(panel2, BorderLayout.SOUTH);

        JLabel setCountry = new JLabel("Country:");
        panel2.add(setCountry);

        country = new JTextField();
        panel2.add(country);
        country.setColumns(10);

        JLabel setCity = new JLabel("City:");
        panel2.add(setCity);

        city = new JTextField();
        panel2.add(city);
        city.setColumns(10);

        JLabel setCurrency = new JLabel("Currency:");
        panel2.add(setCurrency);

        currency = new JTextField();
        panel2.add(currency);
        currency.setColumns(10);

        final JFXPanel panelFx = new JFXPanel();
        panel.add(panelFx);

        TextArea txt = new TextArea();
        frame.getContentPane().add(txt, BorderLayout.WEST);
        txt.setSize(new Dimension(100, 100));

        JButton btnGetInfo = new JButton("Get Info");
        frame.getContentPane().add(btnGetInfo, BorderLayout.EAST);

        btnGetInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Service serv = new Service(country.getText(),city.getText(),currency.getText());
                loadPage(city.getText());

                txt.setText("CountryName :"+serv.getCountry()+ "\n"+
                        "MoneyCode :"+ serv.getMoney()+"\n"+
                        "humidity"+serv.getHumidity()+"\n"+
                        "Temperature"+serv.getTemp()+"\n"+
                        "npbRate"+serv.getNBPRate()+"\n"
                );
            }

            private void loadPage(String text) {
                Platform.runLater(() -> {
                    StackPane root = new StackPane();
                    Scene scene = new Scene(root, panel.getWidth(), panel.getHeight() - 5);
                    WebView webView = new WebView();
                    WebEngine webEngine = webView.getEngine();
                    webEngine.load("https://wikipedia.org/wiki/" + text);
                    root.getChildren().add(webView);
                    panelFx.setScene(scene);
                });
            }
        });
    }
}
