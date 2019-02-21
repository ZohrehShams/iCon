package groupnet;

import groupnet.euler.Description;
import groupnet.ui.EulerDiagramVisTask;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class GroupNetProxy {

    public static void drawEulerDiagram(String informalDescription) {
        var task = new EulerDiagramVisTask(Description.from(informalDescription), ConApp.getRenderer());
        task.run();
        task.render();
    }
}
