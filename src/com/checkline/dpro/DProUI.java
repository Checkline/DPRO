package com.checkline.dpro;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.PrintStream;
import java.text.DecimalFormat;

import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;
import javax.swing.text.NumberFormatter;

public class DProUI extends JFrame {
	
	protected DPro dPro;
    private JLabel avgLabel;
    private JTextField avgText;
    private JButton clearButton;
    private JLabel delayLabel;
    private JSpinner delaySpinner;
    private JLabel devLabel;
    private JTextField devText;
    private JButton exportButton;
    private JLabel hiLabel;
    private JTextField hiText;
    private JLabel loLabel;
    private JTextField loText;
    private JScrollPane logScrollPane;
    private JTextArea logText;
    private JButton portButton;
    private JList readingsList;
    private JScrollPane readingsScrollPane;
    private JButton setDelayButton;
    private JButton startButton;
	
	public DProUI(DPro dPro, String version) {
		super("D-PRO - " + version);
		this.dPro = dPro;
		this.initialize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width / 2 - (this.getWidth()/2), screenSize.height / 2 - (this.getHeight()/2));
	}

	private void initialize() {
        delayLabel = new javax.swing.JLabel();
        delaySpinner = new javax.swing.JSpinner();
        setDelayButton = new javax.swing.JButton();
        readingsScrollPane = new javax.swing.JScrollPane();
        readingsList = new javax.swing.JList();
        hiLabel = new javax.swing.JLabel();
        loLabel = new javax.swing.JLabel();
        avgLabel = new javax.swing.JLabel();
        devLabel = new javax.swing.JLabel();
        hiText = new javax.swing.JTextField();
        loText = new javax.swing.JTextField();
        avgText = new javax.swing.JTextField();
        devText = new javax.swing.JTextField();
        exportButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        portButton = new javax.swing.JButton();
        logScrollPane = new javax.swing.JScrollPane();
        logText = new javax.swing.JTextArea();
        startButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        delayLabel.setText("Delay");

        delaySpinner.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(2.0d), Double.valueOf(0.1d), null, Double.valueOf(0.1d)));
        
        JFormattedTextField txt = ((JSpinner.NumberEditor) delaySpinner.getEditor()).getTextField();
        NumberFormatter formatter = (NumberFormatter) txt.getFormatter();
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        formatter.setFormat(decimalFormat);
        formatter.setAllowsInvalid(false);
        
        setDelayButton.setText("Set");
        setDelayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setDelayButtonActionPerformed(evt);
            }
        });
        
        this.readingsList.setModel(new DefaultListModel<Double>());
        
        readingsScrollPane.setViewportView(readingsList);

        hiLabel.setText("HI");

        loLabel.setText("LO");

        avgLabel.setText("AVG");

        devLabel.setText("DEV");

        hiText.setEditable(false);
        hiText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hiTextActionPerformed(evt);
            }
        });

        loText.setEditable(false);
        loText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loTextActionPerformed(evt);
            }
        });

        avgText.setEditable(false);
        avgText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avgTextActionPerformed(evt);
            }
        });

        devText.setEditable(false);
        devText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                devTextActionPerformed(evt);
            }
        });

        exportButton.setText("Export");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear");

        portButton.setText("Choose Port");
        portButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portButtonActionPerformed(evt);
            }
        });
        
        logText.setEditable(false);
        logText.setColumns(20);
        logText.setRows(5);
        ((DefaultCaret)this.logText.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        logScrollPane.setViewportView(logText);
        
        startButton.setText("Start");
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(delayLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(delaySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(readingsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(devLabel)
                                    .addComponent(avgLabel)
                                    .addComponent(loLabel)
                                    .addComponent(hiLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(devText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(avgText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(loText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(hiText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(setDelayButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(exportButton)
                                .addGap(18, 18, 18)
                                .addComponent(clearButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(startButton)
                                .addGap(10, 10, 10)
                                .addComponent(portButton)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delayLabel)
                    .addComponent(delaySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setDelayButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(readingsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hiText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hiLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(loLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(avgText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(avgLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(devText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(devLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(portButton)
                    .addComponent(startButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exportButton)
                    .addComponent(clearButton))
                .addContainerGap())
        );

        pack();
	}

    private void setDelayButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
    	this.dPro.setDelay((double) this.delaySpinner.getValue());
    }                                              

    private void hiTextActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void loTextActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void avgTextActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void devTextActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }       
    
    private void portButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
    	this.dPro.portSettings();
    }   
    
    private void startButtonMouseClicked(java.awt.event.MouseEvent evt) {
    	if(this.dPro.isRunning()) {
    		this.dPro.stopRunning();
    	}
    	else {
    		this.dPro.startRunning();
    	}
    }
	
    public void addReading(double reading) {
    	((DefaultListModel<Double>)this.readingsList.getModel()).insertElementAt(reading,0);
    }

	public JButton getStartButton() {
		return this.startButton;
	}

	public double getDelayText() {
		return Double.parseDouble(((JSpinner.NumberEditor) this.delaySpinner.getEditor()).getTextField().getText());
	}
    
	public void setDelayText(double d) {
		this.delaySpinner.setValue(d);
	}

	public JTextArea getLogArea() {
		return this.logText;
	}
	
}
