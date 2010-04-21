/**
 * MainStartupDialog.java
 *
 * @author Created by Omnicore CodeGuide
 */

package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dialogs.PlateInputDialog;

public class MainStartupDialog extends JFrame
{
	private Image im;
	private int width = 475;
	private int height = 250;
	private JFrame TheStartUpDialog = this;
	
	
	public MainStartupDialog()
	{
		setDefaultCloseOperation( EXIT_ON_CLOSE ) ;
		JPanel content = (JPanel)getContentPane();
		Color color = new Color(1f, 1f, 1f,  1f);
		content.setBackground(color);
		content.setLayout(new BorderLayout());
		
		im = Toolkit.getDefaultToolkit().getImage("doc/Images/ImageRail_long.png");
		
		// Set the window's bounds, centering the window
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width-width)/2;
		int y = (screen.height-height)/2;
		setBounds(x,y,width,height);
		
		
		content.add(new TopPanel(), BorderLayout.NORTH);
		content.add(new MainPanel(), BorderLayout.CENTER);
		//Adding a border
		content.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		setVisible(true);
		
		
		repaint();
		
	}
	
	public class TopPanel extends JPanel
	{
		TopPanel()
		{
			setPreferredSize(new Dimension(width,85));
			repaint();
		}
		public void paintComponent(Graphics g)
		{
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g);
			if(im != null)
				g.drawImage(im, 0, 0, this);
			
			g2.setFont(MainGUI.Font_12);
		}
		
	}
	
	public class MainPanel extends JPanel
	{
		MainPanel()
		{
			setLayout(new GridLayout());
			add(new NewProjPanel(),0);
			add(new LoadProjPanel(),1);
		}
		
	}
	
	public class HyperLink extends JLabel
	{
		private final HyperLink link = this;
		public HyperLink(String text)
		{
			
			setText(text);
			setFont(new Font("Serif", Font.BOLD, 16));
			setHorizontalAlignment(JLabel.CENTER);
			setVerticalAlignment(JLabel.CENTER);
			addMouseListener(new java.awt.event.MouseAdapter()
							 {
						public void mouseEntered(java.awt.event.MouseEvent evt)
						{
							link.setForeground(Color.red);
						}
					});
			addMouseListener(new java.awt.event.MouseAdapter()
							 {
						public void mouseExited(java.awt.event.MouseEvent evt)
						{
							link.setForeground(Color.black);
						}
					});
		}
	}
	
	public class NewProjPanel extends JPanel
	{
		NewProjPanel()
		{
			setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
			HyperLink but = new HyperLink("Create new project...");
			
			
			but.addMouseListener(new java.awt.event.MouseAdapter()
								 {
						public void mouseClicked(java.awt.event.MouseEvent evt)
						{
							if(evt.getClickCount() > 0)
							{
								new PlateInputDialog(TheStartUpDialog);
							}
						}
					});
			
			
			setLayout(new GridLayout(3,1));
			add(new JLabel(""), 0);
			add(but, 1);
			add(new JLabel(""), 2);
			
			
		}
		
	}
	
	public class LoadProjPanel extends JPanel
	{
		LoadProjPanel()
		{
			setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
			HyperLink but = new HyperLink("Open existing project...");
			but.addMouseListener(new java.awt.event.MouseAdapter()
								 {
						public void mouseClicked(java.awt.event.MouseEvent evt)
						{
							if(evt.getClickCount() > 0)
							{
						MainGUI theGUI = gui.MainGUI.getGUI();
								File dir = gui.MainGUI.getGUI().getTheDirectory();
								JFileChooser fc = null;
								if (dir!=null)
									fc = new JFileChooser(dir);
								else
									fc = new JFileChooser();
								
						fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						fc.addChoosableFileFilter(new FileChooserFilter_IR());

								int returnVal = fc.showOpenDialog(null);
								if (returnVal == JFileChooser.APPROVE_OPTION)
								{
									File f = fc.getSelectedFile();
									if (f!=null && f.isDirectory())
									{
								if (theGUI.containsFile(f, "project.h5")
										|| f.getName().indexOf(".ir") > 0)
										{
											TheStartUpDialog.setVisible(false);
									theGUI.loadProject(f);
									theGUI.setVisible(true);
										}
										else
										{
											JOptionPane.showMessageDialog(null,"Invalid Project! \n\n Please try again","Invalid Project",JOptionPane.ERROR_MESSAGE);
										}
									}
									
								}
								
							}
						}
					});
			setLayout(new GridLayout(3,1));
			add(new JLabel(""), 0);
			add(but, 1);
			add(new JLabel(""), 2);
			
		}
		
		
	}
	
	

	class FileChooserFilter_IR extends javax.swing.filechooser.FileFilter {
		public boolean accept(File file) {
			String filename = file.getName();
			return filename.endsWith(".ir");
		}

		public String getDescription() {
			return "*.ir";
		}
	}
	
	
	
}
