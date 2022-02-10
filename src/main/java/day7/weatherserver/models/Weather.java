package day7.weatherserver.models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Weather {

    private String city;
    private String main;
    private String description;
    private String icon;
    private Float temperature;

    public String getCity() { return this.city; }
    public void setCity(String city) { this.city = city; }

    public String getMain() { return main; }
    public void setMain(String main) { this.main = main; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public String getIcon() { 
        return "http://openweathermap.org/img/wn/%s@2x.png".formatted(icon);
    }
    public void setIcon(String icon) { this.icon = icon; }

    public Float getTemperature() { return this.temperature; }
    public void setTemperature(Float temperature) { this.temperature = temperature; }


    public static Weather create (JsonObject o) {
        Weather w = new Weather();
        /* w.setMain(o.getString("main"));
        w.setDescription(o.getString("description"));
        w.setIcon(o.getString("icon"));

        if (o.containsKey("city"))
            w.setCity(o.getString("city"));
        
        if (o.containsKey("temperature")) {
            double temp = o.getJsonNumber("temperature").doubleValue();
            w.setTemperature((float)temp);
        } */

        w.setCity(o.getString("name"));
		JsonArray arr = o.getJsonArray("weather");
		if (!arr.isEmpty()) {
			JsonObject wo = arr.getJsonObject(0);
			w.setMain(wo.getString("main"));
			w.setDescription(wo.getString("description"));
			w.setIcon(wo.getString("icon"));
		}
		w.setTemperature((float)o.getJsonObject("main").getJsonNumber("temp").doubleValue());

        return w;      
    }

    public static Weather create (String jsonString) {
        try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())){
            final JsonReader reader = Json.createReader(is);
            return create(reader.readObject());
        } catch (Exception ex) {
            return null;
         }

    }

    @Override
    public String toString() {
        return "city: %s, main: %s, description: %s, icon: %s, temperature: %f"
                .formatted(city, main, description, icon, temperature);
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("city", city)
            .add("main", main)
            .add("description", description)
            .add("icon", icon)
            .add("temperature", temperature)
            .build();
    }

    
}
