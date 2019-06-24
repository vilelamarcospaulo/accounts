package controllers;

import spark.utils.IOUtils;
import utils.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class GenericRestTest {
    protected static class TestResponse {
        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() throws IOException {
            return JsonUtils.readFromJson(body, Map.class);
        }
    }

    private String server = "http://localhost:8000/";
    protected TestResponse request(String method, String uri) {
        return this.request(method, uri, null);
    }

    protected TestResponse request(String method, String uri, String requestBody) {
        try {
            URL url = new URL(server + uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json");

            if (requestBody != null) {
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(requestBody.getBytes("UTF-8"));
                }
            }

            connection.connect();
            InputStream inputStream = connection.getResponseCode() < 400 ?
                    connection.getInputStream() :
                    connection.getErrorStream();
            String body = IOUtils.toString(inputStream);
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}