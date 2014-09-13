package com.qe2.activemq.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import com.qe2.messages.MessageSender;




public class ActiveMQMessageSenderFrame extends JFrame {

	private static final long serialVersionUID = 1L;	
	private JButton sendActiveMQMessageBtn = new JButton("Send");	
	private JLabel vrnLbl = new JLabel("Sends the xml to activemq");
	
	
	/**
	 * BridgeCrossingFrame 
	 */
	public ActiveMQMessageSenderFrame()
	{
		setupFrame();
		setupControls();
		addComponentsToPane(this.getContentPane());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);		
	}
	
	/**
	 * setupFrame
	 */
	private void setupFrame()
	{
		setTitle("ActiveMQ Message Sender Applet");
		setSize(300,200); // default size is 0,0
		setLocation(20,300); // default is 0,0 (top left corner)
	}
	
	/**
	 * Adding Event listeners to the button
	 */
	private void setupControls()
	{
		sendActiveMQMessageBtn.setEnabled(true);
		sendActiveMQMessageBtn.addActionListener(new ActionListener() {		         
			@Override
			public void actionPerformed(ActionEvent e) {
				MessageSender messageSender = new MessageSender();
				messageSender.sendMessage();
			}
        });
		
	}
	
	/**
	 * Add components to the container
	 * @param pane
	 */
	private void addComponentsToPane(Container pane)
	{
				
		JPanel content = new JPanel();		
		content.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
		content.setBackground(Color.BLUE);
		content.setLayout(new BoxLayout(content,
				BoxLayout.PAGE_AXIS));        
        JPanel crossingPnl = new JPanel(false);
        crossingPnl.setLayout(new BoxLayout(crossingPnl,
					BoxLayout.PAGE_AXIS));        
        Border appletBorder = BorderFactory.createLineBorder(Color.BLUE);
        appletBorder = BorderFactory.createTitledBorder(
        		appletBorder, "ActiveMQ Message Sender");        
        crossingPnl.setBorder(appletBorder);        
        JPanel vrnPnl = new JPanel();       
        vrnPnl.add(vrnLbl);	  	
		Border gateBdr = BorderFactory.createEmptyBorder(0, 70, 5, 70);	
        JPanel sendBtnPnl = new JPanel();
        sendBtnPnl.add(sendActiveMQMessageBtn);		
        crossingPnl.add(vrnPnl);
        crossingPnl.add(sendBtnPnl);      
		content.add(crossingPnl);
        pane.add(content);		
	}
	
	
}
