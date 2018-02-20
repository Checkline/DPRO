package com.checkline.dpro;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultCaret;
import javax.swing.text.NumberFormatter;

@SuppressWarnings("serial")
public class DProUI extends JFrame {
	
	protected DPro dPro;
    private JLabel avgLabel, devLabel, delayLabel, hiLabel, loLabel;
    private JTextField avgText, devText, hiText, loText;
    private JTextArea logText;
    private JButton clearButton, setDelayButton, portButton, startButton, exportButton;
    private JSpinner delaySpinner;
    private JScrollPane logScrollPane;
    private JList<Double> readingsList;
    private JScrollPane readingsScrollPane;
    private DecimalFormat dc;
    
    
	public DProUI(DPro dPro, String version) {
		super("DPRO - " + version);
		this.dPro = dPro;
		this.initialize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width / 2 - (this.getWidth()/2), screenSize.height / 2 - (this.getHeight()/2));
	}

	private void initialize() {
        this.delayLabel = new JLabel();
        this.delaySpinner = new JSpinner();
        this.setDelayButton = new JButton();
        this.readingsScrollPane = new JScrollPane();
        this.readingsList = new JList<Double>();
        this.hiLabel = new JLabel();
        this.loLabel = new JLabel();
        this.avgLabel = new JLabel();
        this.devLabel = new JLabel();
        this.hiText = new JTextField();
        this.loText = new JTextField();
        this.avgText = new JTextField();
        this.devText = new JTextField();
        this.exportButton = new JButton();
        this.clearButton = new JButton();
        this.portButton = new JButton();
        this.logScrollPane = new JScrollPane();
        this.logText = new JTextArea();
        this.startButton = new JButton();
        this.dc = new DecimalFormat("0.00");
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        this.delayLabel.setText("Delay");
        this.hiLabel.setText("MAX");
        this.loLabel.setText("MIN");
        this.avgLabel.setText("\u03BC");
        this.devLabel.setText("\u03C3");
        this.exportButton.setText("Export");
        this.clearButton.setText("Clear");
        this.portButton.setText("Choose Port");
        this.startButton.setText("Start");
        this.setDelayButton.setText("Set");
        
        this.delaySpinner.setModel(new SpinnerNumberModel(Double.valueOf(2.0d), Double.valueOf(0.1d), null, Double.valueOf(0.1d)));
        
        JFormattedTextField txt = ((JSpinner.NumberEditor) this.delaySpinner.getEditor()).getTextField();
        NumberFormatter formatter = (NumberFormatter) txt.getFormatter();
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        formatter.setFormat(decimalFormat);
        formatter.setAllowsInvalid(false);

        this.devText.setEditable(false);
        this.logText.setEditable(false);
        this.avgText.setEditable(false);
        this.loText.setEditable(false);
        this.hiText.setEditable(false);

        this.readingsList.setModel(new DefaultListModel<Double>());
        this.readingsScrollPane.setViewportView(readingsList);
        
        this.logText.setColumns(20);
        this.logText.setRows(5);
        ((DefaultCaret)this.logText.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.logScrollPane.setViewportView(logText);
        
        this.addListeners();
        this.buildLayout();
        pack();
	}

	private void clearButtonActionPerformed(ActionEvent e) {
    	this.dPro.clear();
	}

	private void setDelayButtonActionPerformed(ActionEvent e) {                                             
    	this.dPro.setDelay((double) this.delaySpinner.getValue());
    }                                                                            

    private void exportButtonActionPerformed(ActionEvent e) {                                          
        this.dPro.exportReadings();
    }       
    
    private void portButtonActionPerformed(ActionEvent e) {                                           
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
    
	public void updateStatistics(double high, double low, double avg, double stddev) {
		this.hiText.setText(this.dc.format(high));
		this.loText.setText(this.dc.format(low));
		this.avgText.setText(this.dc.format(avg));
		this.devText.setText(this.dc.format(stddev));
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
	
	public void clear() {
		((DefaultListModel<Double>) this.readingsList.getModel()).removeAllElements();
		this.logText.setText("");
		this.updateStatistics(0, 0, 0, 0);
	}

	private void addListeners() {
        this.setDelayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setDelayButtonActionPerformed(e);
            }
        });
         
        this.exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportButtonActionPerformed(e);
            }
        });
       
        this.clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearButtonActionPerformed(e);
            }
        });
        
        this.portButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                portButtonActionPerformed(e);
            }
        });
        
        this.startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                startButtonMouseClicked(e);	
			}
		});        
	}	

	private void buildLayout() {
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
	}	
}
