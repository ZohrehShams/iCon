package groupnet;

import groupnet.ui.Renderer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class ConApp extends Application {

    private static ConApp instance;
    private static Renderer renderer;

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public static ConApp getInstance() {
        return instance;
    }

    public static Renderer getRenderer() {
        return renderer;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        instance = this;
        renderer = new Renderer();

        stage.setScene(new Scene(renderer, 800, 600));
        stage.show();
    }
}
