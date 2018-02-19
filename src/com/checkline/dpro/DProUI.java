package com.checkline.dpro;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
    private DecimalFormat dc;
    
	public DProUI(DPro dPro, String version) {
		super("D-PRO - " + version);
		this.dPro = dPro;
		this.initialize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width / 2 - (this.getWidth()/2), screenSize.height / 2 - (this.getHeight()/2));
	}

	private void initialize() {
        this.delayLabel = new javax.swing.JLabel();
        this.delaySpinner = new javax.swing.JSpinner();
        this.setDelayButton = new javax.swing.JButton();
        this.readingsScrollPane = new javax.swing.JScrollPane();
        this.readingsList = new javax.swing.JList();
        this.hiLabel = new javax.swing.JLabel();
        this.loLabel = new javax.swing.JLabel();
        this.avgLabel = new javax.swing.JLabel();
        this.devLabel = new javax.swing.JLabel();
        this.hiText = new javax.swing.JTextField();
        this.loText = new javax.swing.JTextField();
        this.avgText = new javax.swing.JTextField();
        this.devText = new javax.swing.JTextField();
        this.exportButton = new javax.swing.JButton();
        this.clearButton = new javax.swing.JButton();
        this.portButton = new javax.swing.JButton();
        this.logScrollPane = new javax.swing.JScrollPane();
        this.logText = new javax.swing.JTextArea();
        this.startButton = new javax.swing.JButton();
        this.dc = new DecimalFormat("0.00");
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        this.delayLabel.setText("Delay");

        this.delaySpinner.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(2.0d), Double.valueOf(0.1d), null, Double.valueOf(0.1d)));
        
        JFormattedTextField txt = ((JSpinner.NumberEditor) this.delaySpinner.getEditor()).getTextField();
        NumberFormatter formatter = (NumberFormatter) txt.getFormatter();
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        formatter.setFormat(decimalFormat);
        formatter.setAllowsInvalid(false);
        
        this.setDelayButton.setText("Set");
        this.setDelayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setDelayButtonActionPerformed(evt);
            }
        });
        
        this.readingsList.setModel(new DefaultListModel<Double>());
        
        this.readingsScrollPane.setViewportView(readingsList);

        this.hiLabel.setText("MAX");

        this.loLabel.setText("MIN");

        this.avgLabel.setText("\u03BC");

        this.devLabel.setText("\u03C3");

        this.hiText.setEditable(false);
        this.hiText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hiTextActionPerformed(evt);
            }
        });

        this.loText.setEditable(false);
        this.loText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loTextActionPerformed(evt);
            }
        });

        this.avgText.setEditable(false);
        this.avgText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avgTextActionPerformed(evt);
            }
        });

        this.devText.setEditable(false);
        this.devText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                devTextActionPerformed(evt);
            }
        });

        this.exportButton.setText("Export");
        this.exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        this.clearButton.setText("Clear");
        this.clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        
        this.portButton.setText("Choose Port");
        this.portButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portButtonActionPerformed(evt);
            }
        });
        
        this.logText.setEditable(false);
        this.logText.setColumns(20);
        this.logText.setRows(5);
        ((DefaultCaret)this.logText.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.logScrollPane.setViewportView(logText);
        
        this.startButton.setText("Start");
        this.startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                startButtonMouseClicked(e);
				
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
                                    .addComponent(devText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(avgText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(loText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(hiText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    protected void clearButtonActionPerformed(ActionEvent evt) {
    	this.dPro.clear();
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
        this.dPro.exportReadings();
    }       
    
    private void portButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
    	this.dPro.portSettings();
    }   
    
    private void startButtonMouseClicked(ActionEvent e) {
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
    
    public ArrayList<Double> getReadings() {
    	ArrayList<Double> ret = new ArrayList<Double>();
		for (int i = 0; i < this.readingsList.getModel().getSize(); i++) {
			ret.add((double) this.readingsList.getModel().getElementAt(i));
		}
		return ret;
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
	
	public void updateStatistics(double high, double low, double avg, double stddev) {
		this.hiText.setText(this.dc.format(high));
		this.loText.setText(this.dc.format(low));
		this.avgText.setText(this.dc.format(avg));
		this.devText.setText(this.dc.format(stddev));
	}
	
}
