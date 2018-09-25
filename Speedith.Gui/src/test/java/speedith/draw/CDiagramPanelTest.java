package speedith.draw;


import static speedith.i18n.Translations.i18n;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;


import speedith.core.lang.ConceptDiagram;
import speedith.core.lang.reader.ReadingException;
import speedith.core.lang.reader.SpiderDiagramsReader;
import speedith.ui.ArrowPanel;
import speedith.ui.CDCirclesPanelEx3;


import static speedith.core.reasoning.util.unitary.TestConceptDiagrams.*;

public class CDiagramPanelTest extends JFrame{
	private static final String PATH_TO_AXIOMS = "axioms";
    private JMenu fileMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem exitMenuItem;

    private JMenu drawMenu;
    private JMenuItem textInputMenuItem;
    private JMenuItem useCdExample1MenuItem;
    private JMenuItem useCdExample2MenuItem;
    private JMenuItem useCdExample3MenuItem;
    private JMenuItem useCdExample4MenuItem;

    private JFileChooser descriptionFileChooser;
    private JMenu openMenu;
    private JMenu saveMenu;

    private  CDCirclesPanelEx3 boundaryPanel;
    private ArrowPanel glassPanel;
    private JMenuBar menuBar;

    public CDiagramPanelTest() {
        initUI();
    }

    private void initUI() {
        boundaryPanel = new CDCirclesPanelEx3();
        glassPanel = new ArrowPanel();

        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        openMenu = new JMenu();
        saveMenu = new JMenu();
        exitMenuItem = new JMenuItem();
        openMenuItem = new JMenuItem();
        saveMenuItem = new JMenuItem();

        drawMenu = new JMenu();
        textInputMenuItem = new JMenuItem();
        useCdExample1MenuItem = new JMenuItem();
        useCdExample2MenuItem = new JMenuItem();
        useCdExample3MenuItem = new JMenuItem();
        useCdExample4MenuItem = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ontology Visualiser");

        initMenuBar();
        setContentPane(boundaryPanel);
        setGlassPane(glassPanel);
        getGlassPane().setVisible(true);

        descriptionFileChooser = new JFileChooser(PATH_TO_AXIOMS);
        descriptionFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("OntologyVisualiser diagram files", "sdt"));
        descriptionFileChooser.setMultiSelectionEnabled(false);
        pack();
    }

    private void initMenuBar() {
        fileMenu.setMnemonic('F');
        fileMenu.setText("File");

        openMenu.setText("Load");
        fileMenu.add(openMenu);
        saveMenu.setText("Save");
        fileMenu.add(saveMenu);

        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        openMenuItem.setMnemonic('L');
        openMenuItem.setText("Load Description");
        openMenuItem.addActionListener(evt -> onOpen());
        openMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveMenuItem.setMnemonic('S');
        saveMenuItem.setText("Save selected description");
        saveMenuItem.addActionListener(evt -> onSave());
        saveMenu.add(saveMenuItem);

        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(evt -> exitMenuItemActionPerformed());
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        drawMenu.setMnemonic('D');
        drawMenu.setText("Draw");

//        textInputMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
//        textInputMenuItem.setMnemonic(ResourceBundle.getBundle("speedith/i18n/strings").getString("MAIN_FORM_TEXT_INPUT_MNEMONIC").charAt(0));
//        ResourceBundle bundle = ResourceBundle.getBundle("speedith/i18n/strings"); // NOI18N
//        textInputMenuItem.setText(bundle.getString("MAIN_FORM_TEXT_INPUT")); // NOI18N
//        textInputMenuItem.addActionListener(evt -> onTextInputClicked());
//        drawMenu.add(textInputMenuItem);

        useCdExample1MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
        useCdExample1MenuItem.setMnemonic(i18n("MAIN_FORM_USE_EXAMPLE1_MNEMONIC").charAt(0));
        useCdExample1MenuItem.setText("Subclass example"); // NOI18N
        useCdExample1MenuItem.addActionListener(evt -> onExample1());
        drawMenu.add(useCdExample1MenuItem);

        useCdExample2MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_MASK));
        useCdExample2MenuItem.setMnemonic(i18n("MAIN_FORM_USE_EXAMPLE2_MNEMONIC").charAt(0));
        useCdExample2MenuItem.setText("ObjectSomeValuesFrom Example");
        useCdExample2MenuItem.addActionListener(evt -> onExample2());
        drawMenu.add(useCdExample2MenuItem);

        useCdExample3MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_MASK));
        useCdExample3MenuItem.setMnemonic(i18n("MAIN_FORM_USE_EXAMPLE3_MNEMONIC").charAt(0));
        useCdExample3MenuItem.setText("ClassAssertion Example"); // NOI18N
        useCdExample3MenuItem.addActionListener(evt -> onExample3());
        drawMenu.add(useCdExample3MenuItem);

        useCdExample4MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, InputEvent.CTRL_MASK));
        useCdExample4MenuItem.setMnemonic('4');
        useCdExample4MenuItem.setText("Datatype Definition Example"); // NOI18N
        useCdExample4MenuItem.addActionListener(evt -> onExample4());
        drawMenu.add(useCdExample4MenuItem);
        menuBar.add(drawMenu);
        setJMenuBar(menuBar);
    }

    private void onOpen() {
        int returnVal = descriptionFileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = descriptionFileChooser.getSelectedFile();
            try {
                //Diagram input = DiagramsReader.readConceptDiagram(file);
                ConceptDiagram input = (ConceptDiagram) SpiderDiagramsReader.readSpiderDiagram(file);
                if (!input.isValid()) {
                    throw new ReadingException("The diagram contained in the file is not valid.");
                }
                boundaryPanel.setDiagram(input);
                setArrowPanel();
                this.setTitle("Ontology Visualisation" + ": " + file.getName());
                pack();
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(this, "An error occurred while accessing the file:\n" + ioe.getLocalizedMessage());
            } catch (ReadingException re) {
                JOptionPane.showMessageDialog(this, "An error occurred while reading the contents of the file:\n" + re.getLocalizedMessage());
            }
        }
    }

    private void onSave() {
    }

    private void exitMenuItemActionPerformed() {
        this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void setArrowPanel() {
        this.remove(glassPanel);
        SwingUtilities.invokeLater(() -> {
                glassPanel = boundaryPanel.getArrowGlassPanel();
                setGlassPane(glassPanel);
                getGlassPane().setVisible(true);
                pack();
        });
        validate();
        repaint();
    }

    private void onExample1() {
        boundaryPanel.setDiagram(getExampleA());
        setArrowPanel();
        setTitle("OntologyVisualiser" + ": " + "Subclass");
    }

    private void onExample2() {
        boundaryPanel.setDiagram(getExampleA());
        setArrowPanel();
        setTitle("OntologyVisualiser" + ": ObjectSomeValuesFrom(op CE)");
    }

    private void onExample3() {
        boundaryPanel.setDiagram(getExampleA());
        setArrowPanel();
        setTitle("OntologyVisualiser: " + ": ClassAssertion ");
    }

    private void onExample4() {
        boundaryPanel.setDiagram(getExampleA());
        setArrowPanel();
        setTitle("OntologyVisualiser: " + ": DatatypeDefinition(DT DataUnionOf(DR1, DR2))");
    }

//    private void onTextInputClicked() {
//        CDInputDialog dialog = new CDInputDialog();
//        dialog.setVisible(true);
//        if (!dialog.isCancelled() && dialog.getConceptDiagram() != null) {
//            boundaryPanel.setDiagram(dialog.getConceptDiagram());
//            setArrowPanel();
//            setTitle("OntologyVisualiser");
//        }
//    }

    private static ConceptDiagram getExampleA() {
        try {
            return fourCarCD;
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

//    private static Diagram getExampleB() {
//        try {
//            return DiagramsReader.readConceptDiagram(OBJECT_SOME_VALUE);
//        } catch (Exception ex) {
//            throw new RuntimeException();
//        }
//    }
//
//    private static Diagram getExampleC() {
//        try {
//            return DiagramsReader.readConceptDiagram(CLASS_EXPRESSION);
//        } catch (Exception ex) {
//            throw new RuntimeException();
//        }
//    }
//
//    private static Diagram getExampleD() {
//        try {
//            return DiagramsReader.readConceptDiagram(DATATYPE_DEFINITION);
//        } catch (Exception ex) {
//            throw new RuntimeException();
//        }
//    }

    public static void main(String[] args) {
        new CDiagramPanelTest().setVisible(true);
    }

    public void testVisualisation(String axiom) {
        ConceptDiagram diagram = null;
        try {
            diagram = (ConceptDiagram) SpiderDiagramsReader.readSpiderDiagram(axiom);
        } catch (ReadingException e) {
            e.printStackTrace();
        }
        boundaryPanel.setDiagram(diagram);
        setArrowPanel();
    }

}
