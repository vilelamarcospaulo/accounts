import static spark.Spark.get;
import static spark.Spark.port;

public class Main {
    public static void main(String[] args) {
        // server configuration
        port(8000);

        //routes
        get("/hello", (req, res) -> "Hello World");
    }
}
