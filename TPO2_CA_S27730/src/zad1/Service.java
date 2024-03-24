/**
 *
 *  @author Cyran Aleksander S27730
 *
 */

package zad1;





import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class Service {

    String countryName;
    String moneyCode;
    String cityName;

    double humidity;
    double temp;
    double nbpRate;

    public Service(String country, String city, String currency) {
        this(country);

        getWeather(city);
        //getRateFor(currency);
        // getNBPRate();

    }

    public Service(String countryName) {
        this.countryName = countryName;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL("https://restcountries.com/v3.1/name/" + countryName);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        JSONArray jsonArray = new JSONArray(stringBuilder.toString());
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        JSONObject currencies = jsonObject.getJSONObject("currencies");

        // Poniższy kod zakłada, że interesuje Cię pierwsza znaleziona waluta.
        // W rzeczywistości, możesz potrzebować innej logiki, jeśli kraj ma wiele walut.
        String currencyCode = currencies.keys().next(); // Pobiera klucz pierwszej waluty
        JSONObject currency = currencies.getJSONObject(currencyCode);
        this.moneyCode = currencyCode; // Możesz też chcieć użyć 'currency.getString("name")' w zależności od potrzeb
    }


    public String getWeather(String miasto) {

        StringBuilder stringB = null;
        String returnInfo = null;
        this.cityName = miasto;

        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + miasto
                    + "&appid=feebc4d66bfb26721638137968b99ffa");
            URLConnection citeConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(citeConnection.getInputStream()));
            stringB = new StringBuilder();
            String temp;

            while ((temp = br.readLine()) != null) {
                stringB.append(temp);
            }

            br.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
        returnInfo = stringB.toString();

        JSONObject weather = new JSONObject(returnInfo).getJSONObject("main");

        humidity = weather.getDouble("humidity");
        temp = weather.getDouble("temp");

        return returnInfo;
    }

    public double getRateFor(String kod_waluty) {
        StringBuilder sb = null;

        if (kod_waluty.equals(this.moneyCode)) {
            return 1.0;
        }

        try {
            URL url = new URL(
                    "https://open.er-api.com/v6/latest/" + kod_waluty);
            URLConnection citeConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(citeConnection.getInputStream()));
            sb = new StringBuilder();
            String wrt;

            while ((wrt = br.readLine()) != null) {
                sb.append(wrt);
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        return jsonObject.getJSONObject("rates").getDouble(this.moneyCode);
    }

    public double getNBPRate() {
        boolean tmp = false;

        if (moneyCode.equals("PLN")) {
            this.nbpRate = 1.0;
            return this.nbpRate;
        }

        StringBuilder sb = null;
        try {
            URL url = new URL("https://api.nbp.pl/api/exchangerates/rates/a/" + moneyCode + "/?format=json");
            URLConnection citeConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(citeConnection.getInputStream()));
            sb = new StringBuilder();
            String wrt;

            while ((wrt = br.readLine()) != null) {
                sb.append(wrt);
                tmp =true;
            }

            br.close();

        } catch (IOException e) {
            //e.printStackTrace();
        }
        if(tmp!=true) {
            try {
                URL url = new URL("https://open.er-api.com/v6/latest/" + moneyCode + "/?format=json");
                URLConnection citeConnection = url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(citeConnection.getInputStream()));
                sb = new StringBuilder();
                String wrt;

                while ((wrt = br.readLine()) != null) {
                    sb.append(wrt);
                }

                br.close();

            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        System.out.println(jsonObject.toString());
        this.nbpRate = jsonObject.getJSONArray("rates").getJSONObject(0).getDouble("mid");
        return this.nbpRate;
    }

    public String getCountry() {
        return countryName;
    }

    public String getMoney() {
        return moneyCode;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemp() {
        return temp;
    }

    @Override
    public String toString() {
        return "Service [countryName=" + countryName + ", moneyCode=" + moneyCode
                + ", humidity=" + humidity + ", temp=" + temp + ", nbpRate=" + nbpRate + "]";
    }

}
