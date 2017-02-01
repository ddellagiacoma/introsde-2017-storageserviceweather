package com.storageservice.weather.ws;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import com.storageservice.weather.model.Weather;

//Service Implementation
@WebService(endpointInterface = "com.storageservice.weather.ws.WeatherApi", serviceName = "storageServiceWeather")
public class WeatherApiImpl implements WeatherApi {
	static String localurl = "https://localdbservice.herokuapp.com/localdbservice";

	@Override
	public List<Weather> getForecastByLatLng(String lat, String lng) {
		List<Weather> forecast = new ArrayList();
		String url = "https://adapterservice.herokuapp.com/WeatherForecast?lat=" + lat + "&lng=" + lng;
		StringBuffer response = null;
		URL obj;
		try {
			obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			int responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONArray jarr = new JSONArray(response.toString());
			for (int i = 0; i < jarr.length(); i++) {
				Weather weather = new Weather();
				weather.setDate(jarr.getJSONObject(i).getString("date"));
				weather.setHigh(jarr.getJSONObject(i).getString("high"));
				weather.setLow(jarr.getJSONObject(i).getString("low"));
				weather.setText(jarr.getJSONObject(i).getString("text"));
				weather.setDay(jarr.getJSONObject(i).getString("day"));
				forecast.add(weather);
			}

		} catch (Exception e) {
			forecast = null;
			System.out.println("error in getting the weather " + e);
		}
		return forecast;
	}

	@Override
	public Weather getWeatherByLatLng(String lat, String lng) {
		String url = "https://adapterservice.herokuapp.com/Weather?lat=" + lat + "&lng=" + lng;
		StringBuffer response = null;
		URL obj;
		Weather weather = new Weather();
		try {
			obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			int responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONObject jobj = new JSONObject(response.toString());

			weather.setDate(jobj.getString("date"));
			weather.setTemp(jobj.getString("temp"));
			weather.setText(jobj.getString("text"));

		} catch (Exception e) {
			weather = null;
			System.out.println("error in getting the weather " + e);
		}
		return weather;
	}

}