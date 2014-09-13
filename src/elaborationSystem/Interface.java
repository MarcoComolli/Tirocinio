package elaborationSystem;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JProgressBar;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import javax.swing.JSeparator;
import java.awt.Rectangle;

public class Interface extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textSearch;
	private JTextField textSave;
	private JButton btnCopy, btnSearchSrc, btnSave;
	private CopyTask copyTask;
	private JProgressBar progressBar;
	private Path source, target;
	private int filesCount = 0;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	private JTextField txtTest;
	private JLabel lblTestFolder;
	private JButton btnSearchTest;
	private JButton btnRunTest;
	private JLabel lblStatus;
	private JTextField textMidFiles;
	private JLabel lblAdditionalFilesFolder;
	private JButton btnOtherFiles;
	private JSeparator separator;
	private JLabel lblStep;
	private JLabel lblStep1;
	private JSeparator separator_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interface() {
		
		setTitle("Tirocinio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 301);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setFont(new Font("Arial", Font.PLAIN, 11));
		contentPane.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][][][][][grow]"));
		
		lblStep1 = new JLabel("Step 1:");
		lblStep1.setFont(new Font("Arial", Font.BOLD, 11));
		lblStep1.setEnabled(false);
		contentPane.add(lblStep1, "cell 0 0");
		
		separator_1 = new JSeparator();
		contentPane.add(separator_1, "cell 0 1 2 1,growx");
		
		JLabel lblNewLabel = new JLabel("Source folder: ");
		lblNewLabel.setToolTipText("Source where are located the source file of java project");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblNewLabel, "cell 0 2,alignx left");
		
		textSearch = new JTextField();
		contentPane.add(textSearch, "flowx,cell 1 2,grow");
		textSearch.setColumns(10);
		
		btnSearchSrc = new JButton("...");
		btnSearchSrc.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnSearchSrc, "cell 1 2,alignx right");
		btnSearchSrc.addActionListener(new ButtonHandler());
		
		JLabel lblNewLabel_1 = new JLabel("Destination folder: ");
		lblNewLabel_1.setToolTipText("Where processed files were saved");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 11));
		getContentPane().add(lblNewLabel_1, "cell 0 3,alignx left");
		
		textSave = new JTextField();
		contentPane.add(textSave, "flowx,cell 1 3,grow");
		textSave.setColumns(10);
		
		btnSave = new JButton("...");
		btnSave.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnSave, "cell 1 3,alignx right");
		btnSave.addActionListener(new ButtonHandler());
		
		lblAdditionalFilesFolder = new JLabel("Additional files folder:");
		lblAdditionalFilesFolder.setToolTipText("Where will be saved mid-process files ");
		lblAdditionalFilesFolder.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblAdditionalFilesFolder, "cell 0 4,alignx left");
		
		textMidFiles = new JTextField();
		contentPane.add(textMidFiles, "flowx,cell 1 4,grow");
		textMidFiles.setColumns(10);
		
		btnCopy = new JButton("Copy");
		btnCopy.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnCopy, "cell 0 5 2 1,alignx center");
		btnCopy.addActionListener(new ButtonHandler());
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setToolTipText("Progress");
		progressBar.setForeground(new Color(0, 128, 0));
		progressBar.setVisible(false);
		
		lblStatus = new JLabel(" Processing requirements...");
		lblStatus.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblStatus, "cell 0 6 2 1");
		contentPane.add(progressBar, "cell 0 7 2 1,grow");
		lblStatus.setVisible(false);
		
		lblStep = new JLabel("Step 2:");
		lblStep.setEnabled(false);
		lblStep.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblStep, "cell 0 8");
		
		separator = new JSeparator();
		contentPane.add(separator, "cell 0 9 2 1,growx");
		
		lblTestFolder = new JLabel("Test folder:");
		lblTestFolder.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblTestFolder, "cell 0 10,alignx left");
		
		txtTest = new JTextField();
		contentPane.add(txtTest, "flowx,cell 1 10,grow");
		txtTest.setColumns(10);
		
		btnRunTest = new JButton("Run Tests");
		btnRunTest.setFont(new Font("Arial", Font.BOLD, 11));
		btnRunTest.addActionListener(new ButtonHandler());
		contentPane.add(btnRunTest, "cell 0 11 2 1,alignx center");
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 12 2 1,grow");
		
		textPane = new JTextPane();
		textPane.setContentType("text/html");
		scrollPane.setViewportView(textPane);
		
		btnSearchTest = new JButton("...");
		btnSearchTest.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnSearchTest, "cell 1 10");
		
		btnOtherFiles = new JButton("...");
		btnOtherFiles.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnOtherFiles, "cell 1 4");
		btnOtherFiles.addActionListener(new ButtonHandler());
	

		
	}
	
	
	private void getFilesNumber(String dirPath) {
		lblStatus.setText(" Processing requirements...");
		lblStatus.setVisible(true);
		File f = new File(dirPath);
		File[] files = f.listFiles();
		if (files != null)
			for (int i = 0; i < files.length; i++) {
				filesCount++;
				File file = files[i];
				if (file.isDirectory()) {
					System.out.println(filesCount);
					getFilesNumber(file.getAbsolutePath());
				}
			}
	System.out.println(filesCount);
	}
	
	public String getClassName(File f){
		String s = "";
		String pack = f.getParent().substring(f.getParent().lastIndexOf('\\') + 1);
		s += pack + ".";
		String file = f.getName().substring(0, f.getName().indexOf('.'));
		s += file;
		return s;
	}
    
	
	class ButtonHandler implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == btnSearchSrc){
				//apri il file explorer
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.setCurrentDirectory(new java.io.File("."));
				int n = jfc.showOpenDialog(Interface.this);
				if(n == 0){
					String s = jfc.getSelectedFile().getAbsolutePath();
					textSearch.setText(s);
					System.out.println(s);
				}
			}
			else if(event.getSource() == btnSave){
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int n = jfc.showSaveDialog(Interface.this);
				if(n == 0){
					String s = jfc.getSelectedFile().getAbsolutePath();
					textSave.setText(s);
					System.out.println(s);
				}
			}
			else if(event.getSource() == btnOtherFiles){
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int n = jfc.showSaveDialog(Interface.this);
				if(n == 0){
					String s = jfc.getSelectedFile().getAbsolutePath();
					textMidFiles.setText(s);
					System.out.println(s);
				}
			}
			else if(event.getSource() == btnCopy){
				btnCopy.setEnabled(false);
				progressBar.setVisible(true);
				source = new File(textSearch.getText()).toPath();
				new File(textSave.getText()).mkdirs();
				target = new File(textSave.getText()).toPath();

				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));		
				copyTask = new CopyTask();
				copyTask.addPropertyChangeListener(new ChangeListener());
				copyTask.execute();
			}
			else if(event.getSource() == btnSearchTest){
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.setCurrentDirectory(new java.io.File("."));
				int n = jfc.showOpenDialog(Interface.this);
				if(n == 0){
					String s = jfc.getSelectedFile().getAbsolutePath();
					txtTest.setText(s);
					System.out.println(s);
				}


			}
			else if(event.getSource() == btnRunTest){
				JUnitCore junit = new JUnitCore();
				ArrayList<File> directories = new ArrayList<File>();
				runJunitTest(junit, directories, txtTest.getText());			
			}

		}

		private void runJunitTest(JUnitCore junit, ArrayList<File> directories, String path ) {
			File f = new File(path);
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				if(files[i].isFile()){
					try {
						Class cl = Class.forName(getClassName(files[i]));
						Result result = junit.run(cl);
						//System.err.println("Failure " + result.getFailureCount());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				else{
					directories.add(files[i]);
				}
			}
			if(!directories.isEmpty()){
				runJunitTest(junit, directories, directories.get(0).getAbsolutePath());
			}
		}
		
	}
	
	class ChangeListener implements PropertyChangeListener{

		public void propertyChange(PropertyChangeEvent evt) {
	        if ("progress" == evt.getPropertyName()) {
	            int progress = (Integer) evt.getNewValue();
	            progressBar.setValue(progress);
	        } 
	    }
		
	}
	
	class CopyTask extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() {
            int progress = 0;       
            //Initialize progress property.
            setProgress(0);
            progressBar.setValue(progress);
            getFilesNumber(source.toString());
            lblStatus.setText(" Copying files...");
            try {
				Files.walkFileTree(source, new CopyFileVisitor(target, this, filesCount));
			} catch (IOException e) {
				e.printStackTrace();
			}
            lblStatus.setVisible(false);
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
        	Toolkit.getDefaultToolkit().beep();
        	progressBar.setValue(progressBar.getMaximum());
        	setCursor(null); //turn off the wait cursor
        	btnCopy.setEnabled(true);
        }
        
        //setta il progresso
        public void setTheProgress(int a){
        	this.setProgress(a);
        }
    }

	


}
