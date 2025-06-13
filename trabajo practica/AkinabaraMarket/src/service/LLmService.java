package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;


import com.google.gson.*;



import model.ProductoOtaku;
import util.ConfigManager;


public class LLmService {
    private static final String OPENROUTER_API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private final HttpClient httpClient;
    private final String apiKey;
    private final String model;
    public LLmService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        
        ConfigManager config = ConfigManager.getInstance();
        this.apiKey = config.getProperty("openrouter.api.key");
        this.model = config.getProperty("openrouter.model");
        new HashMap<>();
    }

    /**
     * Método principal para consultar al LLM
     * @param prompt Pregunta o instrucción para el modelo
     * @return Respuesta del modelo o mensaje de error
     */
    public String consultarLLM(String prompt) {
        System.out.println("\nEnviando prompt a la IA...");
        System.out.println("Modelo: " + model);
        System.out.println("Prompt: " + prompt.substring(0, Math.min(prompt.length(), 300)));
        
        if (apiKey == null || apiKey.isEmpty()) {
            String error = "Error: API Key no configurada. Por favor configure su API Key en config.properties";
            System.err.println("[DEBUG] " + error);
            return error;
        }

        try {
            HttpRequest request = crearRequest(prompt);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                String respuesta = extraerContenidoRespuesta(response.body());
                System.out.println("Respuesta recibida: " + respuesta.substring(0, Math.min(respuesta.length(), 500)));
                return respuesta;
            } else {
                String error = "Error en la API: " + response.statusCode() + " - " + response.body();
                System.err.println("error: " + error);
                return error;
            }
        } catch (Exception e) {
            System.err.println("[DEBUG] Error en la conexión: " + e.getMessage());
            e.printStackTrace();
            return "Error al consultar la IA: " + e.getMessage();
        }
    }

    private HttpRequest crearRequest(String prompt) {
        String requestBody = String.format(
            "{\"model\": \"%s\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
            model, prompt.replace("\"", "\\\""));
        
        return HttpRequest.newBuilder()
            .uri(URI.create(OPENROUTER_API_URL))
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .timeout(Duration.ofSeconds(30))
            .build();
    }

    /**
     * Función específica para generar descripciones de productos
     */
    public String generarDescripcionProducto(ProductoOtaku producto) {
        String prompt = String.format(
                "Genera una descripción en español de marketing breve y atractiva para el producto otaku: %s de la categoría %s. " +
                "La descripción debe ser de 2-3 frases, destacar sus características principales " +
                "y usar un lenguaje emocionante para fans del anime/manga. " +
                "Evita usar caracteres especiales o emojis.",
                producto.getNombre(), producto.getCategoria());
        
        return consultarLLM(prompt);
    }

    /**
     * Función específica para sugerir categorías
     */
    public String sugerirCategoria(String nombreProducto) {
        String categoriasDisponibles = "Figura, Manga, Póster, Lavero, Ropa, Videojuego, Otro";
        String prompt = String.format(
                "Para un producto otaku llamado \"%s\", sugiere una única categoría adecuada de esta lista: %s. " +
                "Responde solo con el nombre de la categoría, sin explicaciones adicionales y respondeme en español.",
                nombreProducto, categoriasDisponibles);
        
        return consultarLLM(prompt);
    }

 
    private String extraerContenidoRespuesta(String jsonResponse) {
        try {
            JsonObject json = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray choices = json.getAsJsonArray("choices");
            if (choices != null && choices.size() > 0) {
                JsonObject message = choices.get(0).getAsJsonObject().getAsJsonObject("message");
                return message.get("content").getAsString().trim();
            }
            return "La respuesta no contiene contenido válido.";
        } catch (Exception e) {
            return "Error al procesar la respuesta JSON: " + e.getMessage();
        }
    }


    /**
     * Método para verificar la conexión con la API
     */
    public boolean verificarConexion() {
        String testPrompt = "Responde con 'OK' si estás funcionando";
        String respuesta = consultarLLM(testPrompt);
        return respuesta.contains("OK");
    }
}

