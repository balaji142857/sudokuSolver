package com.krishnan.balaji.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;



public class SudokuView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JButton solve,stop,reset;
	private JTextField[] fields;
	
	
	public void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		fields = new JTextField[81];
		for(int i=0;i<81;i++)
			fields[i]=new JTextField();
		solve = new JButton("SOLVE");
		stop = new JButton("STOP");
		reset =  new JButton("RESET");
		
		 for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		    	System.out.println("feel: "+info.getName());
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		          //  break;
		        }
		    }
		 SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                UIManager.put("swing.boldMetal", Boolean.FALSE); 
	                createAndShowGUI();
	            }
	        });	
		 
	}

	
	 private void createAndShowGUI() {
		 final JFrame frame = new JFrame("Sudoku solver v1.0");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.add(new SudokuView());
	        frame.pack();
	        frame.setVisible(true);
	        frame.setBounds(10, 10, 725, 450);
	        frame.setResizable(false);
	        //frame.setIconImage(createImageIcon("images/0.png").getImage());
	        JMenu fileMenu = new JMenu("File");
	        JMenuBar menubar = new JMenuBar();
	        JMenuItem exit = new JMenuItem("Exit");
	        fileMenu.add(exit);
	        exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					frame.dispose();
				}
			});
	        JMenu helpMenu = new JMenu("Help");
	        JMenuItem help = new JMenuItem("Help");
	        JMenuItem contactUs = new JMenuItem("Contact Us");
	        helpMenu.add(help);
	        helpMenu.add(contactUs);
	        contactUs.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(frame, "balajikrishnan14@gmail.com;balaji2k9_srec14@ymail.com");
				}
			});
	        menubar.add(fileMenu);
	        menubar.add(helpMenu);
	        frame.setJMenuBar(menubar);
	        for(JTextField field : fields)
	        	frame.add(field);
	        frame.add(reset);
	        frame.add(stop);
	        frame.add(solve);
	}
 

	/**
	 * get the image associated with swing component relative to the 
	 * View class
	 * @param path
	 * @return
	 */
	protected static ImageIcon createImageIcon(String path) {
	        java.net.URL imgURL = SudokuView.class.getResource(path);
	        if (imgURL != null) {
	            return new ImageIcon(imgURL);
	        } else {
	            System.err.println("Couldn't find file: " + path);
	            return null;
	        }
	    }
}
