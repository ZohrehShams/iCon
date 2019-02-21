package groupnet.ui

import groupnet.ConApp
import groupnet.euler.Curve
import groupnet.euler.EulerDiagram
import groupnet.euler.Zone
import groupnet.gn.GNDiagram
import groupnet.gui.SettingsController
import groupnet.network.NetworkGraph
import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.text.Font
import javafx.scene.text.Text
import java.io.File
import java.util.*
import javax.imageio.ImageIO

/**
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
class Renderer : Pane() {

    private val rootShadedZones = Pane()

    private val rootSceneGraph = Pane()

    private val colors = arrayListOf<Color>()
    private var colorIndex = 0

    init {
        children.addAll(rootShadedZones, rootSceneGraph)

        // these values are adapted from "How should we use colour in ED" Andrew Blake, et al
        for (i in 0..29) {
            colors.add(Color.hsb(((i + 1) * 32).toDouble(),
                    if (i == 1 || i == 2) 0.26 else 0.55,
                    if (i == 1 || i == 2) 0.88 else 0.92))
        }

        Collections.swap(colors, 1, 9)
        Collections.swap(colors, 3, 7)
    }

    fun renderAsImage(diagram: EulerDiagram) {
        renderEulerDiagram(diagram)

        doRender()
    }

    fun renderAsImage(diagram: GNDiagram) {
        renderGNDiagram(diagram)

        doRender()
    }

    private fun doRender(): ImageView {
        drawDebug()

        scaleX = 0.2
        scaleY = 0.2

        val snapshot = snapshot(null, null)

        scaleX = 1.0
        scaleY = 1.0

        clear()

        val imageView = ImageView(snapshot)

        imageView.isPreserveRatio = true
        imageView.fitHeightProperty().bind(ConApp.getInstance().stage.heightProperty().subtract(100))
        imageView.fitWidthProperty().bind(ConApp.getInstance().stage.widthProperty().subtract(100))

        rootSceneGraph.children += imageView

        return imageView
    }

    fun saveToPNG(diagram: EulerDiagram, fileName: String) {
        renderEulerDiagram(diagram)

        scaleX = 0.2
        scaleY = 0.2

        val snapshot = snapshot(null, null)
        val image = SwingFXUtils.fromFXImage(snapshot, null)
        ImageIO.write(image, "png", File("$fileName.png"))

        scaleX = 1.0
        scaleY = 1.0
    }

    fun saveToPNG(diagram: GNDiagram, fileName: String) {
        renderGNDiagram(diagram)

        scaleX = 0.12
        scaleY = 0.12

        val snapshot = snapshot(null, null)
        val image = SwingFXUtils.fromFXImage(snapshot, null)
        ImageIO.write(image, "png", File("$fileName.png"))

        scaleX = 1.0
        scaleY = 1.0
    }

    fun renderGNDiagram(diagram: GNDiagram) {
        renderEulerDiagram(diagram.d)
        renderGraph(diagram.g)
    }

    fun renderEulerDiagram(diagram: EulerDiagram) {
        diagram.curves.forEach {
            renderCurve(it)
        }

        diagram.shadedZones.forEach {
            renderShadedZone(it)
        }
    }

    private fun renderCurve(curve: Curve) {
        val shape = curve.getShape()
        shape.strokeWidth = 16 * 2.5
        shape.stroke = colors[colorIndex++]
        shape.fill = null

        val label = Text(curve.toString())
        label.font = Font.font(128 * 4.5)
        label.fill = shape.stroke

        label.layoutX = -label.layoutBounds.width / 2
        label.layoutY = label.layoutBounds.height / 5

        label.translateXProperty().bindBidirectional(curve.labelPositionXProperty())
        label.translateYProperty().bindBidirectional(curve.labelPositionYProperty())

        //mouseGestures.makeDraggable(label)

//        if (curve is PathCurve) {
//
//            curve.path.elements.forEach { element ->
//                if (element is CubicCurveTo) {
//
//                    val circle = Circle(15.0, 15.0, 15.0, Color.RED)
//                    circle.stroke = Color.CORNFLOWERBLUE
//
//                    circle.translateXProperty().bindBidirectional(element.controlX1Property())
//                    circle.translateYProperty().bindBidirectional(element.controlY1Property())
//
//                    val circle2 = Circle(15.0, 15.0, 15.0, Color.RED)
//                    circle2.stroke = Color.CORNFLOWERBLUE
//
//                    circle2.translateXProperty().bindBidirectional(element.controlX2Property())
//                    circle2.translateYProperty().bindBidirectional(element.controlY2Property())
//
//                    val circle3 = Circle(15.0, 15.0, 15.0, Color.RED)
//                    circle3.stroke = Color.YELLOWGREEN
//
//                    circle3.translateXProperty().bindBidirectional(element.xProperty())
//                    circle3.translateYProperty().bindBidirectional(element.yProperty())
//
//                    mouseGestures.makeDraggable(circle)
//                    mouseGestures.makeDraggable(circle2)
//                    mouseGestures.makeDraggable(circle3)
//
//                    val group = Group(circle, circle2, circle3)
//                    group.visibleProperty().bind(settings.showControlsProperty())
//
//                    rootSceneGraph.children.addAll(group)
//                }
//            }
//        }

        rootSceneGraph.children.addAll(shape, label)
    }

    private fun renderShadedZone(zone: Zone) {
        val shape = zone.getShape()
        shape.fill = Color.color(0.75, 0.75, 0.75, 1.0)

        rootShadedZones.children.add(shape)
    }

    private fun renderGraph(graph: NetworkGraph) {
        graph.nodes.forEach { node ->
            val r = SettingsController.NODE_SIZE

            val circle = Circle(r, r, r, Color.BLACK)
            circle.translateX = node.x - r
            circle.translateY = node.y - r

//            val label = Text(node.label)
//            label.font = Font.font(44.0)
//            label.translateX = node.x - r
//            label.translateY = node.y - r

            rootSceneGraph.children.addAll(circle)
        }

        graph.edges.forEach { edge ->
            val line = Line(edge.v1.x, edge.v1.y,
                    edge.v2.x, edge.v2.y)

            line.strokeWidth = 4.0 * 2 * Math.sqrt(edge.size)
            rootSceneGraph.children.addAll(line)
        }
    }

    fun clear() {
        colorIndex = 0

        rootSceneGraph.children.clear()
        rootShadedZones.children.clear()
    }

    fun drawDebug() {
        rootSceneGraph.children.addAll(SettingsController.debugNodes)

        println(SettingsController.debugPoints)

        SettingsController.debugPoints.forEach { node ->
            val r = 40.0

            val circle = Circle(r, r, r, Color.BLACK)
            circle.translateX = node.x - r
            circle.translateY = node.y - r

            rootSceneGraph.children.addAll(circle)
        }

        SettingsController.debugPoints.clear()
    }
}