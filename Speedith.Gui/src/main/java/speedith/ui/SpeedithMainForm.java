/*
 *   Project: Speedith
 * 
 * File name: SpeedithMainForm.java
 *    Author: Matej Urbas [matej.urbas@gmail.com]
 * 
 *  Copyright © 2011 Matej Urbas
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/*
 * SpeedithMainForm.java
 *
 * Created on 07-Nov-2011, 10:47:11
 */
package speedith.ui;

import scala.collection.JavaConversions;
//import scala.collection.immutable.Set;
import speedith.core.lang.*;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.COPDiagram;
import speedith.core.lang.cop.LUCOPDiagram;
import speedith.core.lang.reader.ReadingException;
import speedith.core.lang.reader.SpiderDiagramsReader;
import speedith.core.reasoning.*;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderRegionArg;
import speedith.core.reasoning.automatic.*;
import speedith.core.reasoning.rules.AddFeet;
import speedith.core.reasoning.rules.SplitSpiders;
import speedith.core.reasoning.rules.util.AutomaticUtils;
import speedith.core.reasoning.rules.util.HeuristicUtils;
import speedith.core.reasoning.rules.util.ReasoningUtils;
import speedith.core.reasoning.tactical.TacticApplicationException;
import speedith.core.reasoning.tactical.TacticProvider;
import speedith.core.reasoning.tactical.Tactics;
import speedith.ui.automatic.*;
import speedith.ui.input.TextSDInputDialog;
import speedith.ui.rules.InteractiveRuleApplication;
import speedith.ui.selection.SelectSubgoalDialog;
import speedith.ui.tactics.InteractiveTacticApplication;
import spiderdrawer.ui.MainForm;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import static speedith.i18n.Translations.i18n;

/**
 * The main application window of Speedith.
 *
 * @author Matej Urbas [matej.urbas@gmail.com]
 */
public class SpeedithMainForm extends javax.swing.JFrame {

  private static final String[] SpeedithIcons = {
    "SpeedithIconVennDiagram-16.png",
    "SpeedithIconVennDiagram-32.png",
    "SpeedithIconVennDiagram-48.png",
    "SpeedithIconVennDiagram-64.png",
    "SpeedithIconVennDiagram-128.png"
  };


  private ExecutorService service;
  private AutomaticProverThread automaticProof;
  private java.util.List<ProofChangedListener> proofChangedListeners;

  private Map<Boolean, Icon> proofFoundIcon;
  private DiagramType activeDiagram;
  private Boolean backgroundProofSearch;
  private Boolean showLowLevelTactics;

  private JMenuItem goalSpiderDrawerInputMenuItem;
  private javax.swing.JMenu drawMenu;
  private javax.swing.JMenuItem openMenuItem;
  private javax.swing.JMenuItem saveMenuItem;
  private javax.swing.JMenuItem openProofMenuItem;
  private javax.swing.JMenuItem saveProofMenuItem;

  private javax.swing.JMenuItem exitMenuItem;
  private javax.swing.JMenuItem settingsMenuItem;
  private javax.swing.JMenu fileMenu;
  private javax.swing.JMenuItem useSdExample1MenuItem;
  private javax.swing.JMenuItem useSdExample2MenuItem;
  private javax.swing.JMenuItem useSdExample3MenuItem;
  private javax.swing.JList<InfRuleListItem> lstAppliedRules;
  private javax.swing.JList<TacticListItem> lstTactics;
  
  //Zohreh
  private javax.swing.JList<InfRuleListItem> lstAppliedRulesLUCOP;
  
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JMenuItem goalTextInputMenuItem;
  private speedith.ui.ProofPanel proofPanel1;
  private javax.swing.JMenu proofMenu;
  private javax.swing.JMenuItem cropProof;
  private javax.swing.JMenuItem inspectProof;


  private javax.swing.JFileChooser goalFileChooser;
  private javax.swing.JFileChooser proofFileChooser;
  private javax.swing.JMenu openMenu;
  private javax.swing.JMenu saveMenu;
  private javax.swing.JMenuItem analyseItem;
  private javax.swing.JMenuItem heuristicItem;

  private javax.swing.JToolBar autoToolBar;
  private javax.swing.JButton replaceWithGenerated;
  private javax.swing.JButton cancelAutoProver;
  private javax.swing.JLabel proofFoundIndicator;
  private javax.swing.JButton startAutoProver;
  private javax.swing.JButton extendByOneStep;

  /**
   * Creates new form SpeedithMainForm
   */
  public SpeedithMainForm() {
	  
	//System.out.println(System.getProperty("java.version"));
	  
    readPreferences();
    proofFoundIcon = new HashMap<>(2);
    URL onUrl = SpeedithMainForm.class.getResource("lightbulb.png");
    if (onUrl != null) {
      ImageIcon onIcon = new ImageIcon(onUrl);
      proofFoundIcon.put(Boolean.TRUE, onIcon);
    } else {
      throw new RuntimeException("Lightbulb not found");
    }
    URL offUrl = SpeedithMainForm.class.getResource("lightbulb_off.png");
    if (offUrl != null) {
      ImageIcon offIcon = new ImageIcon(offUrl);
      proofFoundIcon.put(Boolean.FALSE, offIcon);
    } else {
      throw new RuntimeException("Lightbulb_off not found");
    }

    initComponents();

    proofChangedListeners = new ArrayList<>();
    try {
      ArrayList<Image> icons = new ArrayList<>();
      // Set the icon of this window:
      for (String path : SpeedithIcons) {
        InputStream imgStream = this.getClass().getResourceAsStream(path);
        icons.add(ImageIO.read(imgStream));
      }
      setIconImages(icons);
    } catch (IOException ex) {
      Logger.getLogger(SpeedithMainForm.class.getName()).log(Level.WARNING, "Speedith's icons could not have been loaded.", ex);
    }


    initThreading();
  }

  private void initThreading() {
    service = Executors.newFixedThreadPool(1);
    this.addWindowListener(new WindowListener() {
      @Override
      public void windowOpened(WindowEvent windowEvent) {

      }

      @Override
      public void windowClosing(WindowEvent windowEvent) {
        if (automaticProof != null) {
          automaticProof.cancel(true);
        }
        if (service != null) {
          service.shutdown();
        }
      }

      @Override
      public void windowClosed(WindowEvent windowEvent) {

      }

      @Override
      public void windowIconified(WindowEvent windowEvent) {

      }

      @Override
      public void windowDeiconified(WindowEvent windowEvent) {

      }

      @Override
      public void windowActivated(WindowEvent windowEvent) {

      }

      @Override
      public void windowDeactivated(WindowEvent windowEvent) {

      }
    });
    this.addProofChangedListener(new ProofChangedListener() {
      @Override
      public void interactiveRuleApplied(InteractiveRuleAppliedEvent e) {
        restartAutomatedReasoner();
      }

      @Override
      public void tacticApplied(TacticAppliedEvent e) {
        restartAutomatedReasoner();
      }

      @Override
      public void proofReplaced(ProofReplacedEvent e) {
        System.out.println("Proof replaced");
        disableAutomaticProofUI();
        if (proofPanel1.isFinished()) {
          cancelAutoProver.setEnabled(false);
          startAutoProver.setEnabled(false);
          extendByOneStep.setEnabled(false);
          replaceWithGenerated.setEnabled(false);
          proofFoundIndicator.setText("Idle");
        } else {
          restartAutomatedReasoner();
        }
      }

      @Override
      public void proofReduced(ProofReducedEvent e) {
        restartAutomatedReasoner();
      }

      @Override
      public void proofExtendedByStep(ProofExtendedByStepEvent e) {
        System.out.println("Proof extended by a single step");
        if (proofPanel1.isFinished()) {
          disableAutomaticProofUI();
          cancelAutoProver.setEnabled(false);
          startAutoProver.setEnabled(false);
          extendByOneStep.setEnabled(false);
          replaceWithGenerated.setEnabled(false);
          proofFoundIndicator.setText("Idle");
        }
      }
    });

  }

  private void readPreferences() {
    Preferences prefs = Preferences.userNodeForPackage(SettingsDialog.class);
    String selected = prefs.get(InferenceRules.diagram_type_preference, null);
    if (selected != null) {
      activeDiagram = DiagramType.valueOf(selected);
    } else {
      // startup with spider diagrams as the default.
      activeDiagram = DiagramType.SpiderDiagram;
    }
    selected = prefs.get(AutomaticProverThread.background_preference, null);
    if (selected != null) {
      backgroundProofSearch = Boolean.valueOf(selected);
    } else {
      backgroundProofSearch = Boolean.FALSE;
    }

    selected = prefs.get(Tactics.level_preference, null);
    if (selected != null) {
      showLowLevelTactics = Boolean.valueOf(selected);
    } else {
      showLowLevelTactics = Boolean.FALSE;
    }
  }


  @SuppressWarnings("unchecked")
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    javax.swing.JSplitPane mainSplitPane = new javax.swing.JSplitPane();
    proofPanel1 = new speedith.ui.ProofPanel();
    JPanel pnlRulesSidePane = new JPanel();
    
    JLabel lblAppliedRules = new JLabel();
    JScrollPane scrlPnlAppliedRules = new JScrollPane();
    lstAppliedRules = new javax.swing.JList();
    
    JLabel lblTactics = new JLabel();
    lstTactics = new javax.swing.JList<>();
    JScrollPane scrlPnlTactics = new JScrollPane();
    
    //Zohreh 
    JLabel lblAppliedRulesLUCOP= new JLabel();
    lstAppliedRulesLUCOP = new javax.swing.JList<>();
    JScrollPane scrlPnlAppliedRulesLUCOP = new JScrollPane();
    
    menuBar = new javax.swing.JMenuBar();
    fileMenu = new javax.swing.JMenu();
    openMenu = new javax.swing.JMenu();
    saveMenu = new javax.swing.JMenu();

    settingsMenuItem = new javax.swing.JMenuItem();
    exitMenuItem = new javax.swing.JMenuItem();
    openMenuItem = new javax.swing.JMenuItem();
    saveMenuItem = new javax.swing.JMenuItem();
    openProofMenuItem = new javax.swing.JMenuItem();
    saveProofMenuItem = new javax.swing.JMenuItem();

    drawMenu = new javax.swing.JMenu();
    goalSpiderDrawerInputMenuItem = new javax.swing.JMenuItem();
    goalTextInputMenuItem = new javax.swing.JMenuItem();
    useSdExample1MenuItem = new javax.swing.JMenuItem();
    useSdExample2MenuItem = new javax.swing.JMenuItem();
    useSdExample3MenuItem = new javax.swing.JMenuItem();
    proofMenu = new javax.swing.JMenu();
    cropProof = new javax.swing.JMenuItem();
    analyseItem = new javax.swing.JMenuItem();
    heuristicItem = new javax.swing.JMenuItem();
    inspectProof = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("iCon");
    

    proofPanel1.setMinimumSize(new java.awt.Dimension(500, 300));
    proofPanel1.setPreferredSize(new java.awt.Dimension(750, 300));
    mainSplitPane.setLeftComponent(proofPanel1);

    pnlRulesSidePane.setMinimumSize(new java.awt.Dimension(100, 300));
    pnlRulesSidePane.setPreferredSize(new java.awt.Dimension(100, 300));
    pnlRulesSidePane.setLayout(new java.awt.GridBagLayout());

    lblAppliedRules.setLabelFor(lstAppliedRules);
    lblAppliedRules.setText(i18n("MAIN_FORM_RULE_LIST")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
//    pnlRulesSidePane.add(lblAppliedRules, gridBagConstraints);

    lstAppliedRules.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    lstAppliedRules.setModel(getRulesList());
    lstAppliedRules.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        onRuleItemClicked(evt);
      }
    });
    scrlPnlAppliedRules.setViewportView(lstAppliedRules);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
//    pnlRulesSidePane.add(scrlPnlAppliedRules, gridBagConstraints);

    lblTactics.setLabelFor(lstTactics);
    lblTactics.setText("Tactics:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
//    pnlRulesSidePane.add(lblTactics, gridBagConstraints);

    lstTactics.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    lstTactics.setModel(getTacticsList());
    lstTactics.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        onTacticClicked(e);
      }

    });
    scrlPnlTactics.setViewportView(lstTactics);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
//    pnlRulesSidePane.add(scrlPnlTactics,gridBagConstraints);
    
    
    
    //Zohreh
    lblAppliedRulesLUCOP.setLabelFor(lstAppliedRulesLUCOP);
    lblAppliedRulesLUCOP.setText("Inference Rules:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
    pnlRulesSidePane.add(lblAppliedRulesLUCOP, gridBagConstraints);

    lstAppliedRulesLUCOP.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    //Zohreh: this has to change
    lstAppliedRulesLUCOP.setModel(getMyRulesList());
    
    lstAppliedRulesLUCOP.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
    	  onMyRuleItemClicked(e);
      }

    });
    scrlPnlAppliedRulesLUCOP.setViewportView(lstAppliedRulesLUCOP);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 7;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
    pnlRulesSidePane.add(scrlPnlAppliedRulesLUCOP,gridBagConstraints);
    
    
    
    
    mainSplitPane.setRightComponent(pnlRulesSidePane);
    
    



    initMenuBar();

    initToolBar();

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(autoToolBar)
                    .addComponent(mainSplitPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
            layout.createSequentialGroup()
                    .addComponent(autoToolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE)
                    .addComponent(mainSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
    );


    goalFileChooser = new JFileChooser();
    goalFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Speedith diagram files", "sdt"));
    goalFileChooser.setMultiSelectionEnabled(false);

    proofFileChooser = new JFileChooser();
    proofFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Speedith proof files", "prf"));
    proofFileChooser.setMultiSelectionEnabled(false);

    pack();
  }// </editor-fold>//GEN-END:initComponents



  private void initToolBar() {
    autoToolBar = new JToolBar();
    startAutoProver = new javax.swing.JButton();
    replaceWithGenerated = new javax.swing.JButton();
    cancelAutoProver = new javax.swing.JButton();
    extendByOneStep = new javax.swing.JButton();

    startAutoProver.setText("Start");
    startAutoProver.setEnabled(false);
    startAutoProver.setToolTipText("Start automated proof search");
    startAutoProver.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        onProveFromHere();
      }
    });
    replaceWithGenerated.setText("Solve");
    replaceWithGenerated.setEnabled(false);
    replaceWithGenerated.setToolTipText("Extend the current proof by the automatic prover");
    replaceWithGenerated.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        extendWithAutomaticProof();
      }
    });

    cancelAutoProver.setText("Cancel");
    cancelAutoProver.setEnabled(false);
    cancelAutoProver.setToolTipText("Cancel the automatic proof attempt");
    cancelAutoProver.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        cancelAutomaticProof();
      }
    });

    extendByOneStep.setText("Hint");
    extendByOneStep.setEnabled(false);
    extendByOneStep.setToolTipText("Show a possible next proof step");
    extendByOneStep.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        extendByOneAutomatedStep();
      }
    });

    proofFoundIndicator = new JLabel();
    proofFoundIndicator.setIcon(proofFoundIcon.get(Boolean.TRUE));
    proofFoundIndicator.setDisabledIcon(proofFoundIcon.get(Boolean.FALSE));
    proofFoundIndicator.setEnabled(false);
    proofFoundIndicator.setToolTipText("Lights up, if a proof has been found automatically");
    proofFoundIndicator.setText("Idle");

    JLabel description = new JLabel();
    description.setText("Automatic Prover:");

    autoToolBar.addSeparator();
    autoToolBar.add(description);
    autoToolBar.addSeparator();
    autoToolBar.add(startAutoProver);
    autoToolBar.add(cancelAutoProver);
    autoToolBar.add(extendByOneStep);
    autoToolBar.add(replaceWithGenerated);
    autoToolBar.add(proofFoundIndicator);
    autoToolBar.setFloatable(false);
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
    openMenuItem.setText("Load Goal");
    openMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        onOpen();
      }
    });
    openMenu.add(openMenuItem);

    saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
    saveMenuItem.setMnemonic('S');
    saveMenuItem.setText("Save selected Subgoal");
    saveMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        onSave();
      }
    });
    saveMenu.add(saveMenuItem);


    openProofMenuItem.setText("Load Proof");
    openProofMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        onOpenProof();
      }
    });
    openMenu.add(openProofMenuItem);

    saveProofMenuItem.setText("Save Proof");
    saveProofMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        onSaveProof();
      }
    });
    saveMenu.add(saveProofMenuItem);

    settingsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
    settingsMenuItem.setMnemonic('P');
    settingsMenuItem.setText("Preferences");
    settingsMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        onSettings();
      }
    });
    fileMenu.add(settingsMenuItem);

    exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
    exitMenuItem.setMnemonic('x');
    exitMenuItem.setText("Exit");
    exitMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        exitMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(exitMenuItem);

    menuBar.add(fileMenu);

    drawMenu.setMnemonic('D');
    drawMenu.setText("Draw");

    goalSpiderDrawerInputMenuItem.setText("Use SpiderDrawer"); // NOI18N
    goalSpiderDrawerInputMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        onSpiderDrawerClicked();
      }
    });
    drawMenu.add(goalSpiderDrawerInputMenuItem);

    goalTextInputMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
    goalTextInputMenuItem.setMnemonic(ResourceBundle.getBundle("speedith/i18n/strings").getString("MAIN_FORM_TEXT_INPUT_MNEMONIC").charAt(0));
    ResourceBundle bundle = ResourceBundle.getBundle("speedith/i18n/strings"); // NOI18N
    goalTextInputMenuItem.setText(bundle.getString("MAIN_FORM_TEXT_INPUT")); // NOI18N
    goalTextInputMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        onTextInputClicked(evt);
      }
    });
    drawMenu.add(goalTextInputMenuItem);

    useSdExample1MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
    useSdExample1MenuItem.setMnemonic(i18n("MAIN_FORM_USE_EXAMPLE1_MNEMONIC").charAt(0));
    useSdExample1MenuItem.setText(i18n("MAIN_FORM_USE_EXAMPLE1")); // NOI18N
    useSdExample1MenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        onExample1(evt);
      }
    });
    drawMenu.add(useSdExample1MenuItem);

    useSdExample2MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_MASK));
    useSdExample2MenuItem.setMnemonic(i18n("MAIN_FORM_USE_EXAMPLE2_MNEMONIC").charAt(0));
    useSdExample2MenuItem.setText(i18n("MAIN_FORM_USE_EXAMPLE2")); // NOI18N
    useSdExample2MenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        onExample2(evt);
      }
    });
    drawMenu.add(useSdExample2MenuItem);

    useSdExample3MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_MASK));
    useSdExample3MenuItem.setMnemonic(i18n("MAIN_FORM_USE_EXAMPLE3_MNEMONIC").charAt(0));
    useSdExample3MenuItem.setText(i18n("MAIN_FORM_USE_EXAMPLE3")); // NOI18N
    useSdExample3MenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        onExample3(evt);
      }
    });
    drawMenu.add(useSdExample3MenuItem);

    menuBar.add(drawMenu);

    proofMenu.setMnemonic('P');
    proofMenu.setText("Proof");

    cropProof.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
    cropProof.setMnemonic('R');
    cropProof.setText("Reduce to selected Subgoal");
    cropProof.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        onCropProof();
      }
    });
    proofMenu.add(cropProof);

    analyseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
    analyseItem.setMnemonic('n');
    analyseItem.setText("Analyse");
    analyseItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        analyseProof();
      }
    });
    proofMenu.add(analyseItem);

    heuristicItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
    heuristicItem.setText("Compute Basic Heuristic");
    heuristicItem.setMnemonic('H');
    heuristicItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        onComputeHeuristic();
      }
    });
    proofMenu.add(heuristicItem);

    inspectProof.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
    inspectProof.setText("Inspect");
    inspectProof.setMnemonic('i');
    inspectProof.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        onInspectProof();
      }
    });
    proofMenu.add(inspectProof);

    menuBar.add(proofMenu);

    setJMenuBar(menuBar);
  }

  private void onInspectProof() {
    ProofPanel fullProof = new ProofPanel();
    try {
      fullProof.replaceCurrentProof(proofPanel1.createFlattenedProof());
      JDialog frame = new JDialog(this, "Full Proof", true);
      frame.getContentPane().add(fullProof);
      frame.pack();
      frame.setVisible(true);
    } catch (TacticApplicationException e) {
      JOptionPane.showMessageDialog(this, e.getLocalizedMessage());
    }
  }

  private void onComputeHeuristic() {
    if (proofPanel1.getLastGoals() != null) {
      int heuristic = 0;
      int contourMetr = 0;
      int zoneMetr =0;
      int shZoneMetr =0;
      int connMetr = 0;

      try {
        SpiderDiagram goal = proofPanel1.getSelected().getGoalAt(0);

        if (ReasoningUtils.isImplicationOfConjunctions(goal)) {
            CompoundSpiderDiagram impl = (CompoundSpiderDiagram) goal;
            java.util.Set<String> contours = new HashSet<>(AutomaticUtils.collectContours(impl.getOperand(0)));
            contours.addAll(AutomaticUtils.collectContours(impl.getOperand(1)));
            scala.collection.immutable.Set<String> scalaContours = JavaConversions.asScalaSet(contours).toSet();
            SpiderDiagram cform1 = HeuristicUtils.computeCForm(impl.getOperand(0), scalaContours);
            SpiderDiagram cform2 = HeuristicUtils.computeCForm(impl.getOperand(1),scalaContours);
            SpiderDiagram vennCForm1 = HeuristicUtils.computeVennForm(cform1);
            SpiderDiagram vennCForm2 = HeuristicUtils.computeVennForm(cform2);
            heuristic += HeuristicUtils.metric(impl.getOperand(0), impl.getOperand(1));
            contourMetr += HeuristicUtils.contourDiffMetric(impl.getOperand(0), impl.getOperand(1));
            zoneMetr += HeuristicUtils.zoneDiffMetric(cform1, cform2);
            shZoneMetr += HeuristicUtils.shadingDiffMetric(vennCForm1, vennCForm2);
            connMetr += HeuristicUtils.connectiveDiffMetric(impl.getOperand(0), impl.getOperand(1));


          } else {
            throw new AutomaticProofException("The selected goal is not an implication of conjunctions.");
          }

        int cost = proofPanel1.getProver().getStrategy().getCost(proofPanel1);
        JOptionPane.showMessageDialog(this,"Cost: "+ cost +
                "\nFull Heuristic: "+ heuristic +
                "\nContour Heuristic: "+contourMetr+
                "\nZone Heuristic: "+zoneMetr +
                "\nShading Heuristic: "+shZoneMetr+
                "\nConnective Heuristic: "+connMetr
                ,"Heuristic of Selected Goal", JOptionPane.INFORMATION_MESSAGE);
      } catch (AutomaticProofException e) {
        JOptionPane.showMessageDialog(this, e.getLocalizedMessage());
      }
    }
  }

  private void extendWithAutomaticProof() {
    if (automaticProof != null) {
      try {
        Proof autoProof = automaticProof.getProof();
        proofPanel1.extendProof(autoProof);
        fireProofChangedEvent(new ProofReplacedEvent(this));
      } catch (AutomaticProofException e) {
        JOptionPane.showMessageDialog(this, e.getLocalizedMessage());
      }

    }
  }

  private void extendByOneAutomatedStep() {
  if (automaticProof != null) {
    try {
      Proof autoProof = automaticProof.getProof();
      proofPanel1.extendByOneStep(autoProof);
      fireProofChangedEvent(new ProofExtendedByStepEvent(this));
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, e.getLocalizedMessage());
    }

  }
  }

  private void cancelAutomaticProof() {
    if (automaticProof != null) {
      automaticProof.cancel(true);
      System.out.println("Was Cancelled: "+automaticProof.isCancelled());
      System.out.println("Is Finished: "+automaticProof.isFinished());
      // remove the reference to the automatic prover thread so that the garbage
      // collector can do its magic
      automaticProof = null;

    }
    proofFoundIndicator.setText("Idle");
    cancelAutoProver.setEnabled(false);
    startAutoProver.setEnabled(true);
  }

  private void analyseProof() {
    if (proofPanel1.getGoals().isEmpty()) {
      JOptionPane.showMessageDialog(this, "No proof to analyse.");
      return;
    }
    try {
      Proof fullProof = proofPanel1.createFlattenedProof();
      int length = ProofAnalyser.length(fullProof);
      int maxClutter = ProofAnalyser.maximumClutter(fullProof);
      double avgClutter = ProofAnalyser.averageClutter(fullProof);
      int velocity = ProofAnalyser.maximalClutterVelocity(fullProof);
      int complexR = ProofAnalyser.complexRuleCount(fullProof);
      double avgComplex = ProofAnalyser.averageNumberOfComplexRules(fullProof);
      int interactions = ProofAnalyser.numberOfInteractions(fullProof);
      double avgInteractions = ProofAnalyser.averageInteractions(fullProof);
      int automatic = ProofAnalyser.numberOfAutomaticRuleApplications(fullProof);

      JOptionPane.showMessageDialog(this, "Length:\t" + length +
              "\nMaximum Clutter:\t" + maxClutter +
              "\nAverage Clutter:\t" + String.format("%.2f", avgClutter) +
              "\nNumber of Complex Rules:\t" + complexR +
              "\nAverage Number of Complex Rules:\t" + String.format("%.2f", avgComplex) +
              "\nNumber of Interactions:\t" + interactions +
              "\nAverage Number of Interactions:\t" + String.format("%.2f", avgInteractions) +
              "\nMaximal Clutter Velocity:\t" + velocity +
              "\nAutomatic Rule Applications:\t" + automatic);
    } catch (TacticApplicationException e) {
      JOptionPane.showMessageDialog(this, "An error occurred while applying a tactic:\n" + e.getLocalizedMessage());

    }
  }

  private void onSaveProof() {
    if (proofPanel1.getGoals().isEmpty()) {
      JOptionPane.showMessageDialog(this, "No proof to be saved exists.");
      return;
    }
    int returnVal = proofFileChooser.showSaveDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = proofFileChooser.getSelectedFile();
      if (file.exists()) {
        int reallySave = JOptionPane.showConfirmDialog(this, "File " + file.getName() + " exists at given path. Save anyway?", "File already exists", JOptionPane.YES_NO_OPTION);
        if (reallySave == JOptionPane.NO_OPTION) {
          return;
        }
      }
      try (
              FileOutputStream fileStream = new FileOutputStream(file);
              ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)) {
        objectStream.writeObject(proofPanel1.getProof());
        objectStream.flush();
      } catch (IOException ioe) {
        JOptionPane.showMessageDialog(this, "An error occurred while accessing the file:\n" + ioe.getLocalizedMessage());
      }
    }
  }

  private void onOpenProof() {
    int returnVal = proofFileChooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = proofFileChooser.getSelectedFile();
      Proof inputProof = null;
      try (
        FileInputStream inputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
        inputProof = (Proof) objectInputStream.readObject();

      } catch (IOException ioe) {
        JOptionPane.showMessageDialog(this, "An error occurred while accessing the file:\n" + ioe.getLocalizedMessage());
      }  catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
      proofPanel1.replaceCurrentProof(inputProof);
      this.setTitle("iCon"+": " + file.getName());
    }
  }

  private void onCropProof() {
    if (proofPanel1.getSelected() != null) {
      proofPanel1.reduceToSelected();
      fireProofChangedEvent(new ProofReducedEvent( this));
    }
  }


  private void onOpen() {
    int returnVal = goalFileChooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = goalFileChooser.getSelectedFile();
      try {
    	//SpiderDiagram input = COPDiagramReader.readSpiderDiagram(file);
        SpiderDiagram input = SpiderDiagramsReader.readSpiderDiagram(file);
        if (!input.isValid()) {
          throw new ReadingException("The spider diagram contained in the file is not valid.");
        }
        proofPanel1.newProof(Goals.createGoalsFrom(ReasoningUtils.normalize(input)));
        this.setTitle("iCon"+": " + file.getName());
        cancelAutomaticProof();
        if (backgroundProofSearch) {
          startAutomatedReasoner();
        } else {
          startAutoProver.setEnabled(true);
        }
      } catch (IOException ioe) {
        JOptionPane.showMessageDialog(this, "An error occurred while accessing the file:\n" + ioe.getLocalizedMessage());
      } catch (ReadingException re) {
        JOptionPane.showMessageDialog(this, "An error occurred while reading the contents of the file:\n" + re.getLocalizedMessage());
      }
    }
  }

  private void onSave() {
    if (proofPanel1.getGoals().isEmpty()) {
      JOptionPane.showMessageDialog(this, "No subgoal to be saved exists.");
      return;
    }
    if (proofPanel1.getSelected() == null) {
      JOptionPane.showMessageDialog(this, "No subgoal selected", "No subgoal selected", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Goals selectedGoals = proofPanel1.getSelected();
    SpiderDiagram toSave = null;
    int subgoalindex = 0;
    if (selectedGoals.getGoalsCount() > 1) {
      SelectSubgoalDialog dsd = new SelectSubgoalDialog(this, true, selectedGoals);
      dsd.pack();
      dsd.setVisible(true);

      if (!dsd.isCancelled()) {
        subgoalindex = dsd.getSelectedIndex();
      } else {
        // stop selection
        return;
      }
    }
    toSave = selectedGoals.getGoalAt(subgoalindex);
    int returnVal = goalFileChooser.showSaveDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = goalFileChooser.getSelectedFile();
      if (file.exists()) {
        int reallySave = JOptionPane.showConfirmDialog(this, "File " + file.getName() + " exists at given path. Save anyway?", "File already exists", JOptionPane.YES_NO_OPTION);
        if (reallySave == JOptionPane.NO_OPTION) {
          return;
        }
      }
      try {

        FileWriter writer = new FileWriter(file);
        writer.write(toSave.toString());
        writer.flush();
        writer.close();
      } catch (IOException ioe) {
        JOptionPane.showMessageDialog(this, "An error occurred while accessing the file:\n" + ioe.getLocalizedMessage());
      }
    }

  }


  private void onSettings() {
    SettingsDialog settings = new SettingsDialog(this, true);
    settings.setVisible(true);
    proofPanel1.setProver(settings.getSelectedProver());
    proofPanel1.getProver().setStrategy(settings.getSelectedStrategy());
    if (settings.getSelectedDiagramType() != activeDiagram) {
      activeDiagram = settings.getSelectedDiagramType();
      lstAppliedRules.setModel(getRulesList());
      lstAppliedRules.repaint();
      lstTactics.setModel(getTacticsList());
      lstTactics.repaint();
    }
    backgroundProofSearch = settings.isBackGroundSearchEnabled();
    if (settings.isShowLowLevelTacticsEnabled() != showLowLevelTactics) {
      showLowLevelTactics = settings.isShowLowLevelTacticsEnabled();
      lstTactics.setModel(getTacticsList());
      lstTactics.repaint();
    }
  }

  private void onProveFromHere() {
    if (activeDiagram != DiagramType.EulerDiagram) {
      JOptionPane.showMessageDialog(this,"The automatic provers only work for Euler diagrams");
      return;
    }
    startAutomatedReasoner();
  }

  private void startAutomatedReasoner() {
    disableAutomaticProofUI();
    automaticProof = new AutomaticProverThread(proofPanel1.getProof(), proofPanel1.getProver()) {
      @Override
      protected void done() {
          try {
            Proof result = get();
            if (result != null && result.isFinished()) {
              setProof(result);
              System.out.println("Successful! ");
              enableAutomaticProofUI();
            } else {
              System.out.println("Unsuccessful! ");
              proofFoundIndicator.setText("Unable to solve");
              cancelAutoProver.setEnabled(false);
            }
            } catch (InterruptedException| ExecutionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(proofPanel1.getRootPane(), "An error occurred:" +e);
          } catch (CancellationException e) {
            proofFoundIndicator.setText("Idle");
            cancelAutoProver.setEnabled(false);
            startAutoProver.setEnabled(true);
          }
//        } else {
//        }
      }
    };
    /*automaticProof.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if ("state".equals(propertyChangeEvent.getPropertyName())) {
          if (SwingWorker.StateValue.DONE.equals(propertyChangeEvent.getNewValue())) {
            if (automaticProof.isFinished()) {
              System.out.println("Successful! "+propertyChangeEvent.getNewValue());
              enableAutomaticProofUI();
            } else {
              System.out.println("Unsuccessful! "+propertyChangeEvent.getNewValue());
              proofFoundIndicator.setText("Unable to solve");
              cancelAutoProver.setEnabled(false);
            }
          }
        }
      }
    });*/
    service.submit(automaticProof);
    proofFoundIndicator.setText("Searching...");
  }

  private void restartAutomatedReasoner() {
    cancelAutomaticProof();
    if (backgroundProofSearch) {
      startAutomatedReasoner();
    } else {
      startAutoProver.setEnabled(true);
    }
  }

  private void enableAutomaticProofUI() {
    startAutoProver.setEnabled(false);
    replaceWithGenerated.setEnabled(true);
    extendByOneStep.setEnabled(true);
    proofFoundIndicator.setEnabled(true);
    proofFoundIndicator.setText("Success!");
    cancelAutoProver.setEnabled(false);
  }

  private void disableAutomaticProofUI() {
    startAutoProver.setEnabled(false);
    replaceWithGenerated.setEnabled(false);
    extendByOneStep.setEnabled(false);
    proofFoundIndicator.setEnabled(false);
    cancelAutoProver.setEnabled(true);
  }

  public void addProofChangedListener(ProofChangedListener l) {
    proofChangedListeners.add(l);
  }

  public void removeProofChangedListener(ProofChangedListener l) {
    proofChangedListeners.remove(l);
  }

  private void fireProofChangedEvent(ProofChangedEvent e) {
    if (e instanceof InteractiveRuleAppliedEvent) {
      for(ProofChangedListener l : proofChangedListeners) {
        l.interactiveRuleApplied((InteractiveRuleAppliedEvent) e);
      }
    } else if (e instanceof TacticAppliedEvent) {
      for (ProofChangedListener l: proofChangedListeners) {
        l.tacticApplied((TacticAppliedEvent) e);
      }
    } else if (e instanceof ProofReplacedEvent) {
      for (ProofChangedListener l: proofChangedListeners) {
        l.proofReplaced((ProofReplacedEvent) e);
      }
    } else if (e instanceof ProofReducedEvent) {
      for (ProofChangedListener l: proofChangedListeners) {
        l.proofReduced((ProofReducedEvent) e);
      }
    } else if (e instanceof ProofExtendedByStepEvent) {
      for (ProofChangedListener l: proofChangedListeners) {
        l.proofExtendedByStep((ProofExtendedByStepEvent) e);
      }
    }
  }

  private void onSpiderDrawerClicked() {
    MainForm spiderDrawer = new MainForm(this, true, false);
    boolean done = spiderDrawer.showDialog();

    if (done) {
      SpiderDiagram spiderDiagram;
      try {
    	  //Zohreh
    	  //spiderDiagram = COPDiagramReader.readSpiderDiagram(spiderDrawer.getSpiderDiagram());
          spiderDiagram = SpiderDiagramsReader.readSpiderDiagram(spiderDrawer.getSpiderDiagram());
        proofPanel1.newProof(Goals.createGoalsFrom(spiderDiagram));
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
  }

  private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
    this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }//GEN-LAST:event_exitMenuItemActionPerformed

  private void onExample1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onExample1
    proofPanel1.newProof(Goals.createGoalsFrom(getExampleABC()));
    setTitle("iCon" + ": " + "Example 1");
  }//GEN-LAST:event_onExample1

  private void onExample2(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onExample2
    proofPanel1.newProof(Goals.createGoalsFrom(getExampleB()));
    setTitle("iCon"+": "+"Example 2");
  }//GEN-LAST:event_onExample2

  private void onExample3(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onExample3
    proofPanel1.newProof(Goals.createGoalsFrom(getExampleC()));
    setTitle("iCon"+": "+"Example 3");
  }//GEN-LAST:event_onExample3

  private void onRuleItemClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onRuleItemClicked
    if (evt.getClickCount() == 2) {
      if (!proofPanel1.isFinished()) {
        int index = lstAppliedRules.locationToIndex(evt.getPoint());
        DefaultComboBoxModel model = (DefaultComboBoxModel) lstAppliedRules.getModel();
        InfRuleListItem selectedRule = (InfRuleListItem) model.getElementAt(index);
        applyRule(selectedRule);
      }
    }
  }//GEN-LAST:event_onRuleItemClicked

  private void onTacticClicked(MouseEvent e) {
    if (e.getClickCount() == 2) {
      if (!proofPanel1.isFinished()) {
        int index = lstTactics.locationToIndex(e.getPoint());
        DefaultComboBoxModel model = (DefaultComboBoxModel) lstTactics.getModel();
        TacticListItem selectedRule = (TacticListItem) model.getElementAt(index);
        applyTactic(selectedRule);
      }
    }
  }
  
  private void onMyRuleItemClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onRuleItemClicked
	    if (evt.getClickCount() == 2) {
	      if (!proofPanel1.isFinished()) {
	        int index = lstAppliedRulesLUCOP.locationToIndex(evt.getPoint());
	        DefaultComboBoxModel model = (DefaultComboBoxModel) lstAppliedRulesLUCOP.getModel();
	        InfRuleListItem selectedRule = (InfRuleListItem) model.getElementAt(index);
	        applyRule(selectedRule);
	      }
	    }
	  }

  private void onTextInputClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onTextInputClicked
    TextSDInputDialog dialog = new TextSDInputDialog(this, true);
    if (proofPanel1.getLastGoals() != null && !proofPanel1.getLastGoals().isEmpty()) {
      dialog.setSpiderDiagramText(proofPanel1.getLastGoals().getGoalAt(0));
    } else {
      dialog.setSpiderDiagramText(getExampleA());
    }
    dialog.setVisible(true);
    if (!dialog.isCancelled() && dialog.getSpiderDiagram() != null) {
      proofPanel1.newProof(Goals.createGoalsFrom(ReasoningUtils.normalize(dialog.getSpiderDiagram())));
      setTitle("iCon");
    }
  }//GEN-LAST:event_onTextInputClicked

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(SpeedithMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

        /*
         * Create and display the form
         */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new SpeedithMainForm().setVisible(true);
      }
    });
  }

  // <editor-fold defaultstate="collapsed" desc="Static methods for example creation">
  /**
   * The first main example used in most of our papers. Useful for testing the
   * rules: split spider, add feet, idempotency, and tautology of implication.
   */
  public static CompoundSpiderDiagram getExampleA() {
    PrimarySpiderDiagram psd1 = getSDExample1();
    PrimarySpiderDiagram psd2 = getSDExample7();
    CompoundSpiderDiagram csd = SpiderDiagrams.createCompoundSD(Operator.Implication, psd1, psd2);
    return csd;
  }
  
  public static SpiderDiagram getExampleACOP() {
	  COPDiagram cop = getSDExample1COP();  
	  return cop;
	  }

  /**
   * The second example. Useful for testing the rule: idempotency.
   */
  public static SpiderDiagram getExampleB() {
    try {
      return SpiderDiagramsReader.readSpiderDiagram("BinarySD {arg1 = PrimarySD { spiders = [\"s\", \"s'\"], sh_zones = [], habitats = [(\"s\", [([\"A\", \"B\"], [])]), (\"s'\", [([\"A\"], [\"B\"]), ([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"])]}, arg2 = PrimarySD { spiders = [\"s\", \"s'\"], sh_zones = [], habitats = [(\"s'\", [([\"A\", \"B\"], [])]), (\"s\", [([\"A\"], [\"B\"]), ([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"])]}, operator = \"op &\" }");
    } catch (Exception ex) {
      throw new RuntimeException();
    }
  }

  /**
   * The third example. Useful for testing the rule: implication tautology.
   */
  public static SpiderDiagram getExampleC() {
    try {
      return SpiderDiagramsReader.readSpiderDiagram("BinarySD {arg1 = PrimarySD { spiders = [\"s\", \"s'\"], sh_zones = [], habitats = [(\"s\", [([\"A\", \"B\"], [])]), (\"s'\", [([\"A\"], [\"B\"]), ([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"])]}, arg2 = PrimarySD { spiders = [\"s\", \"s'\"], sh_zones = [], habitats = [(\"s'\", [([\"A\", \"B\"], [])]), (\"s\", [([\"A\"], [\"B\"]), ([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"])]}, operator = \"op -->\" }");
    } catch (Exception ex) {
      throw new RuntimeException();
    }
  }

  /**
   * s1: A, B s2: AB
   */
  public static PrimarySpiderDiagram getSDExample1() {
    Set<Zone> present = new HashSet<>();
    present.add(Zone.fromOutContours("A","B"));
    PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, present);
    Region s1Region = regionA_B__B_A();
    Region s2Region = regionAB();
    emptyPSD = emptyPSD.addSpider("t1", s1Region);
    return emptyPSD.addSpider("t2", s2Region);
  }

  /**
   * s1: A s2: AB
   */
  public static PrimarySpiderDiagram getSDExample5() {
    PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, null);
    Region s1Region = regionA_B();
    Region s2Region = regionAB();
    emptyPSD = emptyPSD.addSpider("s1", s1Region);
    return emptyPSD.addSpider("s2", s2Region);
  }

  /**
   * s1: B s2: AB
   */
  public static PrimarySpiderDiagram getSDExample6() {
    PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, null);
    Region s1Region = regionB_A();
    Region s2Region = regionAB();
    emptyPSD = emptyPSD.addSpider("s1", s1Region);
    return emptyPSD.addSpider("s2", s2Region);
  }

  /**
   * s1: A, AB s2: B, AB
   */
  public static PrimarySpiderDiagram getSDExample7() {
    Set<Zone> present = new HashSet<>();
    present.add(Zone.fromOutContours("A","B"));
    PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, present);
    Region s1Region = regionA_B__AB();
    Region s2Region = regionB_A__AB();
    emptyPSD = emptyPSD.addSpider("u1", s1Region);
    return emptyPSD.addSpider("u2", s2Region);
  }

  /**
   * s1: B, AB s2: AB
   */
  public static PrimarySpiderDiagram getSDExample8() {
    PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, null);
    Region s1Region = regionB_A__AB();
    Region s2Region = regionAB();
    emptyPSD = emptyPSD.addSpider("s1", s1Region);
    return emptyPSD.addSpider("s2", s2Region);
  }

  /**
   * s1: B, AB s2: A, AB
   */
  public static PrimarySpiderDiagram getSDExample9() {
    PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, null);
    Region s1Region = regionB_A__AB();
    Region s2Region = regionA_B__AB();
    emptyPSD = emptyPSD.addSpider("s1", s1Region);
    return emptyPSD.addSpider("s2", s2Region);
  }

  /**
   * s1: A, AB s2: AB
   */
  public static PrimarySpiderDiagram getSDExample10() {
    PrimarySpiderDiagram emptyPSD = SpiderDiagrams.createPrimarySD(null, null, null, null);
    Region s1Region = regionA_B__AB();
    Region s2Region = regionAB();
    emptyPSD = emptyPSD.addSpider("s1", s1Region);
    return emptyPSD.addSpider("s2", s2Region);
  }

  public static Goals getStep0() {
    CompoundSpiderDiagram csd = getExampleA();
    return Goals.createGoalsFrom(csd);
  }

  public static Goals getStep1() {
    RuleArg ruleArg = new SpiderRegionArg(0, 1, "s1", regionA_B());
    return applyInferenceRule(SplitSpiders.InferenceRuleName, ruleArg, getStep0());
  }

  public static Goals getStep2() {
    RuleArg ruleArg = new SpiderRegionArg(0, 2, "s1", regionAB());
    return applyInferenceRule(AddFeet.InferenceRuleName, ruleArg, getStep1());
  }

  public static Goals getStep3() {
    RuleArg ruleArg = new SpiderRegionArg(0, 3, "s1", regionAB());
    return applyInferenceRule(AddFeet.InferenceRuleName, ruleArg, getStep2());
  }

  public static Goals getStep4() {
    RuleArg ruleArg = new SpiderRegionArg(0, 2, "s2", regionB_A());
    return applyInferenceRule(AddFeet.InferenceRuleName, ruleArg, getStep3());
  }

  public static CompoundSpiderDiagram getSDExample2() {
    PrimarySpiderDiagram psd1 = getSDExample1();
    PrimarySpiderDiagram psd2 = getSDExample1();
    CompoundSpiderDiagram csd = SpiderDiagrams.createCompoundSD(Operator.Equivalence, psd1, psd2);
    return csd;
  }

  public static NullSpiderDiagram getSDExample3() {
    return SpiderDiagrams.createNullSD();
  }

  public static CompoundSpiderDiagram getSDExample4() {
    PrimarySpiderDiagram sd1 = getSDExample1();
    SpiderDiagram sd2 = getSDExample2();
    CompoundSpiderDiagram csd = SpiderDiagrams.createCompoundSD(Operator.Conjunction, sd1, sd2);
    return csd;
  }

  public static CompoundSpiderDiagram getSDExample11() {
    SpiderDiagram sd1 = getSDExample4();
    SpiderDiagram sd2 = SpiderDiagrams.createNullSD();
    CompoundSpiderDiagram csd = SpiderDiagrams.createCompoundSD(Operator.Equivalence, sd1, sd2);
    return csd;
  }

 // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Inference rule list and application">
  private ListModel<InfRuleListItem> getRulesList() {
    Set<String> knownInferenceRules = InferenceRules.getKnownInferenceRules(activeDiagram);
    InfRuleListItem[] infRules = new InfRuleListItem[knownInferenceRules.size()];
    int i = 0;
    for (String providerName : knownInferenceRules) {
      infRules[i++] = new InfRuleListItem(InferenceRules.getProvider(providerName));
    }
    Arrays.sort(infRules);
    return new DefaultComboBoxModel<>(infRules);

  }
  
  //Zohreh
  private ListModel<InfRuleListItem> getMyRulesList() {
	    Set<String> knownInferenceRules = InferenceRules.getKnownInferenceRules(DiagramType.LUCOPDiagram);
	    Set<String> knownInferenceRulesCD = InferenceRules.getKnownInferenceRules(DiagramType.ConceptDiagram);

	    TreeSet<String> allKnownInferenceRulesCD = new TreeSet<String>(knownInferenceRules);
	    allKnownInferenceRulesCD.addAll(knownInferenceRulesCD);
	    
	    InfRuleListItem[] infRules = new InfRuleListItem[allKnownInferenceRulesCD.size()];
	    int i = 0;
	    for (String providerName : allKnownInferenceRulesCD) {
	      infRules[i++] = new InfRuleListItem(InferenceRules.getProvider(providerName));
	    }
	    Arrays.sort(infRules);
	    return new DefaultComboBoxModel<>(infRules);

	  }

  private static class InfRuleListItem implements Comparable<InfRuleListItem> {

    private final InferenceRuleProvider<? extends RuleArg> infRuleProvider;

    public InfRuleListItem(InferenceRuleProvider<? extends RuleArg> infRuleProvider) {
      if (infRuleProvider == null) {
        throw new IllegalArgumentException(speedith.core.i18n.Translations.i18n("GERR_NULL_ARGUMENT", "infRuleProvider"));
      }
      this.infRuleProvider = infRuleProvider;
    }

    public InferenceRuleProvider<? extends RuleArg> getInfRuleProvider() {
      return infRuleProvider;
    }

    @Override
    public String toString() {
      return infRuleProvider.getPrettyName();
    }

    @Override
    public int compareTo(InfRuleListItem o) {
      return infRuleProvider.toString().compareToIgnoreCase(o.toString());
    }
  }

  private void applyRule(InfRuleListItem selectedRule) {

    try {
      InteractiveRuleApplication.applyRuleInteractively(this, selectedRule.getInfRuleProvider().getInferenceRule(), proofPanel1);
      fireProofChangedEvent(new InteractiveRuleAppliedEvent(this));
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getLocalizedMessage());
    }
  }

  private void applyTactic(TacticListItem selectedTactic) {
    int subgoalIndex = 0;

    try {
      InteractiveTacticApplication.applyTacticInteractively(this, selectedTactic.getTacticProvider().getTactic(), subgoalIndex, proofPanel1);
      fireProofChangedEvent(new TacticAppliedEvent(this));
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getLocalizedMessage());
    }
  }

  private static Goals applyInferenceRule(String infRuleName, RuleArg ruleArg, Goals goals0) {
    InferenceRule<? extends RuleArg> infRule = InferenceRules.getInferenceRule(infRuleName);
    try {
      RuleApplicationResult rar = infRule.apply(ruleArg, goals0);
      goals0 = rar.getGoals();
    } catch (RuleApplicationException ex) {
      throw new RuntimeException(ex);
    }
    return goals0;
  }

  // </editor-fold>

  private ListModel<TacticListItem> getTacticsList() {
    Set<String> knownTactics = Tactics.getKnownTactics(activeDiagram, showLowLevelTactics);
    TacticListItem[] tactics = new TacticListItem[knownTactics.size()];
    int i = 0;
    for (String providerName : knownTactics) {
      tactics[i++] = new TacticListItem(Tactics.getProvider(providerName));
    }
    Arrays.sort(tactics);
    return new DefaultComboBoxModel<>(tactics);
  }

  private static class TacticListItem implements Comparable<TacticListItem> {

    private final TacticProvider tacticProvider;

    public TacticListItem(TacticProvider tacticProvider) {
      if (tacticProvider == null) {
        throw new IllegalArgumentException(speedith.core.i18n.Translations.i18n("GERR_NULL_ARGUMENT", "infRuleProvider"));
      }
      this.tacticProvider = tacticProvider;
    }

    public TacticProvider getTacticProvider() {
      return tacticProvider;
    }

    @Override
    public String toString() {
      return tacticProvider.getPrettyName();
    }

    @Override
    public int compareTo(TacticListItem o) {
      return tacticProvider.toString().compareToIgnoreCase(o.toString());
    }
  }

  // <editor-fold defaultstate="collapsed" desc="Private methods creating examples of regions and zones">
  private static Region regionA_B() {
    return new Region(zoneA_B());
  }

  private static Region regionA_B__AB() {
    return new Region(zoneA_B(), zoneAB());
  }

  private static Region regionA_B__B_A() {
    return new Region(zoneA_B(), zoneB_A());
  }

  private static Region regionB_A() {
    return new Region(zoneB_A());
  }

  private static Region regionB_A__AB() {
    return new Region(zoneB_A(), zoneAB());
  }

  private static Region regionAB() {
    return new Region(zoneAB());
  }

  private static Zone zoneAB() {
    return Zone.fromInContours("A", "B");
  }

  private static Zone zoneA_B() {
    return Zone.fromInContours("A").withOutContours("B");
  }

  private static Zone zoneB_A() {
    return Zone.fromInContours("B").withOutContours("A");
  }
  
 
  public static SpiderDiagram getExampleABC() {
	  COPDiagram cop = getSDExample123();  
	  return cop;
	  }
  
  public static COPDiagram getSDExample123() {
		TreeSet<String> spiders = new TreeSet<>();
		spiders.add("t1");
		spiders.add("t2");
		
        TreeMap<String, Region> habitats = new TreeMap<>();
        habitats.put("t1", new Region(Zone.fromInContours("B").withOutContours("A")));
        habitats.put("t2", new Region(Zone.fromInContours("A").withOutContours("B")));
        
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B"));
		
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromInContours("B").withOutContours("A"));
		presentZones.add(Zone.fromInContours("A").withOutContours("B"));
		presentZones.add(Zone.fromOutContours("A","B"));
		
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(new Arrow("A","B","solid"));
        
	  return COPDiagram.createCOPDiagram(spiders, habitats, shadedZones, 
				presentZones, arrows);
	  }
  
  
  public static SpiderDiagram getExampleFLUCOP() {
	  COPDiagram cop = getSDExample14LUCOP();  
	  return cop;
	  }
  
  public static COPDiagram getSDExample14LUCOP() {
		TreeSet<String> spiders = new TreeSet<>();
		spiders.add("t1");
		spiders.add("t2");
		
        TreeMap<String, Region> habitats = new TreeMap<>();
        habitats.put("t1", new Region(Zone.fromInContours("B").withOutContours("A")));
        habitats.put("t2", new Region(Zone.fromInContours("A").withOutContours("B")));
        
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B"));
		
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromInContours("B").withOutContours("A"));
		presentZones.add(Zone.fromInContours("A").withOutContours("B"));
		presentZones.add(Zone.fromOutContours("A","B"));
		
		TreeSet<Arrow> arrows = new TreeSet<>();
		
		TreeMap<String,String> spiderLabels = new TreeMap<>();
		
		TreeMap<String,String> curveLabels = new TreeMap<>();
		curveLabels.put("A","A");
		curveLabels.put("B","B");
        
	  return LUCOPDiagram.createLUCOPDiagram(spiders, habitats, shadedZones, 
				presentZones, arrows, spiderLabels, curveLabels);
	  } 
  
  
  public static SpiderDiagram getExampleG() {
	  COPDiagram cop = getSDExample15();  
	  return cop;
	  }
  
  public static COPDiagram getSDExample15() {
		TreeSet<String> spiders = new TreeSet<>();
		
        TreeMap<String, Region> habitats = new TreeMap<>();
        
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B").withOutContours(""));
		shadedZones.add(Zone.fromInContours("A","B",""));
		shadedZones.add(Zone.fromInContours("A","").withOutContours("B"));
		
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromOutContours("A","B",""));
		presentZones.add(Zone.fromInContours("A").withOutContours("B",""));
		presentZones.add(Zone.fromInContours("B").withOutContours("A",""));
		presentZones.add(Zone.fromInContours("").withOutContours("A","B"));
		presentZones.add(Zone.fromInContours("","B").withOutContours("A"));
		
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(new Arrow("A","B","solid"));
        
	  return COPDiagram.createCOPDiagram(spiders, habitats, shadedZones, 
				presentZones, arrows);
	  }
  
  
  public static SpiderDiagram getExampleDLUCOP() {
	  LUCOPDiagram lucop = getSDExample12LUCOP();  
	  return lucop;
	  }
  
  public static LUCOPDiagram getSDExample12LUCOP() {
		TreeSet<String> spiders = new TreeSet<>();
		
        TreeMap<String, Region> habitats = new TreeMap<>();
        
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B").withOutContours("C"));
		shadedZones.add(Zone.fromInContours("A","B","C"));
		shadedZones.add(Zone.fromInContours("A","C").withOutContours("B"));
		
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromOutContours("A","B","C"));
		presentZones.add(Zone.fromInContours("A").withOutContours("B","C"));
		presentZones.add(Zone.fromInContours("B").withOutContours("A","C"));
		presentZones.add(Zone.fromInContours("C").withOutContours("A","B"));
		presentZones.add(Zone.fromInContours("C","B").withOutContours("A"));
		
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(new Arrow("A","B","solid"));
		
		TreeMap<String,String> spiderLabels = new TreeMap<>();
		
		TreeMap<String,String> curveLabels = new TreeMap<>();
		curveLabels.put("A","A");
		curveLabels.put("B","B");
		curveLabels.put("C","");
		
	  return LUCOPDiagram.createLUCOPDiagram(spiders, habitats, shadedZones, 
				presentZones, arrows,spiderLabels,curveLabels);
	  }
  
  public static SpiderDiagram getExampleD() {
	  COPDiagram cop = getSDExample12();  
	  return cop;
	  }
  
  public static COPDiagram getSDExample12() {
		TreeSet<String> spiders = new TreeSet<>();
		
        TreeMap<String, Region> habitats = new TreeMap<>();
        
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B").withOutContours("C"));
		shadedZones.add(Zone.fromInContours("A","B","C"));
		shadedZones.add(Zone.fromInContours("A","C").withOutContours("B"));
		
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromOutContours("A","B","C"));
		presentZones.add(Zone.fromInContours("A").withOutContours("B","C"));
		presentZones.add(Zone.fromInContours("B").withOutContours("A","C"));
		presentZones.add(Zone.fromInContours("C").withOutContours("A","B"));
		presentZones.add(Zone.fromInContours("C","B").withOutContours("A"));
		
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(new Arrow("A","B","solid"));
        
	  return COPDiagram.createCOPDiagram(spiders, habitats, shadedZones, 
				presentZones, arrows);
	  }
  
  
  public static SpiderDiagram getExampleELUCOP() {
	  //LUCOPDiagram lucop = getSDExample13LUCOP();  
	  LUCOPDiagram lucop = getSDExample15LUCOP();  
	  return lucop;
	  }
  
  
  public static LUCOPDiagram getSDExample15LUCOP() {
		TreeSet<String> spiders = new TreeSet<>();
		
        TreeMap<String, Region> habitats = new TreeMap<>();
        
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("B").withOutContours("A"));
		
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromOutContours("A","B"));
		presentZones.add(Zone.fromInContours("A").withOutContours("B"));
		presentZones.add(Zone.fromInContours("A","B"));
		
		TreeSet<Arrow> arrows = new TreeSet<>();
		
		TreeMap<String,String> spiderLabels = new TreeMap<>();
		
		TreeMap<String,String> curveLabels = new TreeMap<>();
		curveLabels.put("A","A");
		curveLabels.put("B","B");
        
	  return LUCOPDiagram.createLUCOPDiagram(spiders, habitats, shadedZones, 
				presentZones, arrows, spiderLabels,curveLabels);
	  }
  
  public static COPDiagram getSDExample1COP() {
		TreeSet<String> spiders = new TreeSet<>();
		spiders.add("t1");
		spiders.add("t2");
		
      TreeMap<String, Region> habitats = new TreeMap<>();
      habitats.put("t1", new Region(Zone.fromInContours("B").withOutContours("A")));
      habitats.put("t2", new Region(Zone.fromInContours("A").withOutContours("B")));
      
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B"));
		
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromInContours("B").withOutContours("A"));
		presentZones.add(Zone.fromInContours("A").withOutContours("B"));
		presentZones.add(Zone.fromOutContours("A","B"));
		
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(new Arrow("A","B","solid"));
      
	  return COPDiagram.createCOPDiagram(spiders, habitats, shadedZones, 
				presentZones, arrows);
	  }

  //</editor-fold>
}
