package com.storageservice.weather.ws;

import java.util.List;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.storageservice.weather.model.Weather;

//service definition
@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) 
public interface WeatherApi {
	
    @WebMethod(operationName="getForecastByLatLng")
    @WebResult(name="forecast") 
    public List<Weather> getForecastByLatLng(@WebParam(name="lat") String lat,@WebParam(name="lng") String lng);
    
    @WebMethod(operationName="getWeatherByLatLng")
    @WebResult(name="weather") 
    public Weather getWeatherByLatLng(@WebParam(name="lat") String lat,@WebParam(name="lng") String lng);
    
}