package com.company;

import com.xu.ArrayOfString;
import com.xu.WeatherWebService;
import com.xu.WeatherWebServiceSoap;

public class Main {

    public static void main(String[] args) {
        WeatherWebService weatherWebService = new WeatherWebService();
        WeatherWebServiceSoap weatherWebServiceSoap = weatherWebService.getWeatherWebServiceSoap();
        System.out.println("---------weatherInfo-----------");
        ArrayOfString weatherInfo = weatherWebServiceSoap.getWeatherbyCityName("上海");
        for (String str: weatherInfo.getString()
             ) {
            System.out.println("weatherInfo"+str);
        }
    }
}
























