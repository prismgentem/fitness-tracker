package ru.prisma.fitnesstracker.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import ru.prisma.fitnesstracker.model.MacroResult;
import ru.prisma.fitnesstracker.model.UserProfile;
import ru.prisma.fitnesstracker.model.enums.ActivityLevel;
import ru.prisma.fitnesstracker.model.enums.Goal;
import ru.prisma.fitnesstracker.model.enums.Sex;
import ru.prisma.fitnesstracker.service.CalorieCalculator;
import ru.prisma.fitnesstracker.service.MacroCalculator;
import ru.prisma.fitnesstracker.service.ProfileValidator;

public class FitnessTrackerHttpServer {

    private static final int PORT = 8080;
    private static final String APPLICATION_JSON = "application/json";

    private final ObjectMapper objectMapper;
    private final CalorieCalculator calorieCalculator;
    private final MacroCalculator macroCalculator;

    public FitnessTrackerHttpServer() {
        this.objectMapper = new ObjectMapper();
        ProfileValidator profileValidator = new ProfileValidator();
        this.calorieCalculator = new CalorieCalculator(profileValidator);
        this.macroCalculator = new MacroCalculator();
    }

    public static void main(String[] args) throws IOException {
        FitnessTrackerHttpServer application = new FitnessTrackerHttpServer();
        HttpServer server = application.createServer();
        server.start();
        System.out.println("Fitness Tracker HTTP server started on http://localhost:" + PORT);
    }

    public HttpServer createServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/calculate", new CalculateHandler());
        return server;
    }

    private class CalculateHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                    sendJson(exchange, 405, new ErrorResponse("Method Not Allowed"));
                    return;
                }
                if (!isJsonRequest(exchange.getRequestHeaders())) {
                    sendJson(exchange, 415, new ErrorResponse("Content-Type must be application/json"));
                    return;
                }

                CalculateRequest request = readRequest(exchange.getRequestBody());
                UserProfile profile = new UserProfile(
                        request.age,
                        request.heightCm,
                        request.weightKg,
                        request.sex,
                        request.activityLevel,
                        request.goal
                );

                int targetCalories = calorieCalculator.calculateTargetCalories(profile);
                MacroResult macros = macroCalculator.calculateMacros(targetCalories, profile.getGoal());

                sendJson(exchange, 200, new CalculateResponse(targetCalories, macros));
            } catch (IllegalArgumentException exception) {
                sendJson(exchange, 400, new ErrorResponse(exception.getMessage()));
            } catch (Exception exception) {
                sendJson(exchange, 400, new ErrorResponse("Invalid request"));
            } finally {
                exchange.close();
            }
        }
    }

    private CalculateRequest readRequest(InputStream requestBody) throws IOException {
        return objectMapper.readValue(requestBody, CalculateRequest.class);
    }

    private boolean isJsonRequest(Headers headers) {
        String contentType = headers.getFirst("Content-Type");
        return contentType != null && contentType.toLowerCase().startsWith(APPLICATION_JSON);
    }

    private void sendJson(HttpExchange exchange, int statusCode, Object payload) throws IOException {
        byte[] response = objectMapper.writeValueAsString(payload).getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", APPLICATION_JSON);
        exchange.sendResponseHeaders(statusCode, response.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(response);
        }
    }

    private static class CalculateRequest {
        public Integer age;
        public Double heightCm;
        public Double weightKg;
        public Sex sex;
        public ActivityLevel activityLevel;
        public Goal goal;
    }

    private static class CalculateResponse {
        public final int targetCalories;
        public final MacroResult macros;

        private CalculateResponse(int targetCalories, MacroResult macros) {
            this.targetCalories = targetCalories;
            this.macros = macros;
        }
    }

    private static class ErrorResponse {
        public final String error;

        private ErrorResponse(String error) {
            this.error = error;
        }
    }
}
