/**
 *
 *  @author Cyran Aleksander S27730
 *
 */

package zad1;




public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
    // ...
    // część uruchamiająca GUI
    Gui gui = new Gui();
//    gui.frame.setVisible(true);


  }
}
