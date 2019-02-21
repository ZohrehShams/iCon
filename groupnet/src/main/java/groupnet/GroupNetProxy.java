package groupnet;

import groupnet.euler.Description;
import groupnet.ui.EulerDiagramVisTask;

import static javafx.application.Platform.runLater;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class GroupNetProxy {

    public static void drawEulerDiagram(String informalDescription) {
        runLater(() -> {
            var norm = informalDescription.replace(".", " ").trim();

            var task = new EulerDiagramVisTask(Description.from(norm), ConApp.getRenderer());
            task.run();
            task.render();
        });
    }
}
