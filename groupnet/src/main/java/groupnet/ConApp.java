package groupnet;

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
    @Override
    public void start(Stage stage) throws Exception {
        Text t = new Text("iCon v2.0");
        t.setFont(Font.font(72));
        stage.setScene(new Scene(new StackPane(t), 800, 600));
        stage.show();
    }
}
