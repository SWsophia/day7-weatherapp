package day7.weatherserver.services;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import day7.weatherserver.models.Weather;

@Service
public class WeatherService {

    public String appId;
    public String URL_WEATHER = "http://api.openweathermap.org//data/2.5/weather";
   

    public WeatherService() {
        String k = System.getenv("OPENWEATHERMAP_KEY");
        System.out.printf("appid is: %s", k);
        if ((null != k) && (k.trim().length()>0)) {
            appId = k;
        }else {
            appId = "abc123";
        }
        System.out.printf("appid now is: %s", appId);
    }
    

    public Weather getWeather(String city) {

        String url = UriComponentsBuilder.
                fromUriString(URL_WEATHER).
                queryParam("q", city.replaceAll(" ", "\\+")).
                queryParam("appid", appId).
                queryParam("units", "metric").toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        String body = resp.getBody();
       
        return Weather.create(body);

    }


    
}
