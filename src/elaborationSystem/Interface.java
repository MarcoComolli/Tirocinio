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
import java.util.TreeMap;

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
	private JButton btnCopy, btnSearchSrc, btnSave, btnSearchTest, btnRunTest, btnOtherFiles, btnProcess, btnAddDir;
	private CopyTask copyTask;
	private JProgressBar progressBar;
	private Path source, target;
	private int filesCount = 0;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	private JTextField txtTest;
	private JLabel lblTestFolder,lblStatus,lblStep2,lblStep1,lblRootPathSrc,lblStep3,lblAdditionalFolders,lblClassTestRoot;
	private JTextField textMidFiles;
	private JLabel lblAdditionalFilesFolder;
	private JSeparator separator;
	private JSeparator separator_1;
	private JTextField txtRootPathSrc;
	private JButton btnRootPathSrc;
	private JTextField txtClassTest;
	private JButton btnClassTest;
	private JScrollPane scrollPane_1;
	private JPanel panel;
	private ArrayList<JButton> additionalButtons;
	private ArrayList<JTextField> additionalTextField;
	private int panelRows = 0;
	private String panelLayoutCol = "[][grow]";
	private String panelLayoutRow = "[]";
	private MigLayout panelLayout;
	private JTextField txtRootPathDest;
	private JButton btnRootPathDest;
	private JLabel lblDestinationPackagesFolder;
	private JTextField txtClassRoot;
	private JButton btnClassRoot;
	private JLabel lblClassRoot;
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
		additionalButtons = new ArrayList<JButton>();
		additionalTextField = new ArrayList<JTextField>();
		panelLayout = new MigLayout("", panelLayoutCol, panelLayoutRow);
		
		setTitle("Tirocinio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 544, 618);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setFont(new Font("Arial", Font.PLAIN, 11));
		contentPane.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][][][][][][][][][][grow 8][][][][][grow]"));
		
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
		
		btnCopy = new JButton("Copy");
		btnCopy.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnCopy, "cell 0 4 2 1,alignx center");
		btnCopy.addActionListener(new ButtonHandler());
		
		lblStatus = new JLabel(" Processing requirements...");
		lblStatus.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblStatus, "cell 0 5 2 1");
		lblStatus.setVisible(false);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setToolTipText("Progress");
		progressBar.setForeground(new Color(0, 185, 0));
		progressBar.setVisible(false);
		contentPane.add(progressBar, "cell 0 6 2 1,grow");
		
		lblStep2 = new JLabel("Step 2:");
		lblStep2.setEnabled(false);
		lblStep2.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblStep2, "cell 0 7");
		
		lblAdditionalFilesFolder = new JLabel("Additional files folder:");
		lblAdditionalFilesFolder.setToolTipText("Where will be saved mid-process files ");
		lblAdditionalFilesFolder.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblAdditionalFilesFolder, "cell 0 9,alignx left");
		
		textMidFiles = new JTextField();
		contentPane.add(textMidFiles, "flowx,cell 1 9,grow");
		textMidFiles.setColumns(10);
		
		lblRootPathSrc = new JLabel("Original packages folder: ");
		lblRootPathSrc.setToolTipText("Specify the first folder containing packages");
		lblRootPathSrc.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblRootPathSrc, "cell 0 10,alignx left");
		
		txtRootPathSrc = new JTextField();
		txtRootPathSrc.setColumns(10);
		contentPane.add(txtRootPathSrc, "flowx,cell 1 10,grow");
		
		separator = new JSeparator();
		contentPane.add(separator, "cell 0 8 2 1,growx");
		
		lblDestinationPackagesFolder = new JLabel("Destination packages folder: ");
		lblDestinationPackagesFolder.setToolTipText("Specify the first folder containing packages");
		lblDestinationPackagesFolder.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblDestinationPackagesFolder, "cell 0 11,alignx left");
		
		txtRootPathDest = new JTextField();
		txtRootPathDest.setColumns(10);
		contentPane.add(txtRootPathDest, "flowx,cell 1 11,grow");
		
		btnProcess = new JButton("Process Files");
		btnProcess.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnProcess, "cell 0 12 2 1,alignx center");
		btnProcess.addActionListener(new ButtonHandler());
		
		lblStep3 = new JLabel("Step 3:");
		lblStep3.setFont(new Font("Arial", Font.BOLD, 11));
		lblStep3.setEnabled(false);
		contentPane.add(lblStep3, "cell 0 13");
		
		lblTestFolder = new JLabel("Test folder:");
		lblTestFolder.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblTestFolder, "cell 0 14,alignx left");
		
		txtTest = new JTextField();
		contentPane.add(txtTest, "flowx,cell 1 14,grow");
		txtTest.setColumns(10);
		
		btnRunTest = new JButton("Run Tests");
		btnRunTest.setFont(new Font("Arial", Font.BOLD, 11));
		btnRunTest.addActionListener(new ButtonHandler());
		
		lblClassRoot = new JLabel("Compiled source code folder:");
		lblClassRoot.setToolTipText("Folder where are stored the .class test files");
		lblClassRoot.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblClassRoot, "cell 0 16,alignx left");
		
		txtClassRoot = new JTextField();
		txtClassRoot.setColumns(10);
		contentPane.add(txtClassRoot, "flowx,cell 1 16,grow");
		contentPane.add(btnRunTest, "cell 0 21 2 1,alignx center");
		
		lblClassTestRoot = new JLabel("Compiled tests folder:");
		lblClassTestRoot.setToolTipText("Folder where are stored the .class test files");
		lblClassTestRoot.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(lblClassTestRoot, "cell 0 15,alignx left");
		
		txtClassTest = new JTextField();
		txtClassTest.setColumns(10);
		contentPane.add(txtClassTest, "flowx,cell 1 15,grow");
		
		scrollPane_1 = new JScrollPane();
		contentPane.add(scrollPane_1, "cell 0 17 2 4,grow");
		
		panel = new JPanel();
		scrollPane_1.setViewportView(panel);
		panel.setLayout(panelLayout);
		
		btnAddDir = new JButton("+");
		btnAddDir.setFont(new Font("Arial", Font.BOLD, 12));
		panel.add(btnAddDir, "flowy,cell 0 0 2 1,alignx center");
		btnAddDir.addActionListener(new ButtonHandler());

		
		lblAdditionalFolders = new JLabel("Additional folders or JARs");
		scrollPane_1.setColumnHeaderView(lblAdditionalFolders);
		

		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 22 2 1,grow");
		
		textPane = new JTextPane();
		textPane.setContentType("text/html");
		scrollPane.setViewportView(textPane);
		
		btnSearchTest = new JButton("...");
		btnSearchTest.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnSearchTest, "cell 1 14");
		btnSearchTest.addActionListener(new ButtonHandler());
		
		btnOtherFiles = new JButton("...");
		btnOtherFiles.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnOtherFiles, "cell 1 9");
		btnOtherFiles.addActionListener(new ButtonHandler());
		
		btnRootPathSrc = new JButton("...");
		btnRootPathSrc.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnRootPathSrc, "cell 1 10");
		btnRootPathSrc.addActionListener(new ButtonHandler());
		
		btnClassTest = new JButton("...");
		btnClassTest.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnClassTest, "cell 1 15");
		btnClassTest.addActionListener(new ButtonHandler());
		
		btnRootPathDest = new JButton("...");
		btnRootPathDest.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnRootPathDest, "cell 1 11");
		btnRootPathDest.addActionListener(new ButtonHandler());
		
		btnClassRoot = new JButton("...");
		btnClassRoot.setFont(new Font("Arial", Font.BOLD, 11));
		contentPane.add(btnClassRoot, "cell 1 16");
		btnClassRoot.addActionListener(new ButtonHandler());
	

		
	}
	
	
	private void getFilesNumber(String dirPath) {
		lblStatus.setText(" Processing requirements...");
		lblStatus.setVisible(true);
		progressBar.setIndeterminate(true);
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
	}
	
	public String getClassName(File f){
		String s = "";
		String pack = f.getParent().substring(f.getParent().lastIndexOf('\\') + 1);
		s += pack + ".";
		String file = f.getName().substring(0, f.getName().indexOf('.'));
		s += file;
		return s;
	}
    
	public String getFullyQualifiedName(String currentPath, String root){
		if(root.contains("/")){
			root = root.replace('/', '\\');
		}
		String name =  currentPath.replace(root, "");
		name = name.replace(File.separatorChar, '.');
		name = name.substring(1, name.lastIndexOf('.'));
		System.out.println(name);
		return name;
	}
	
	private void addForm(){
		
		JButton b = new JButton("...");
		b.setFont(new Font("Arial", Font.BOLD, 11));
		b.addActionListener(new PanelButtonHandler());
		additionalButtons.add(b);
		
		JTextField t = new JTextField();
		t.setColumns(10);
		additionalTextField.add(t);
		
		
		panelLayoutRow += "[]";
		panelRows++;
		System.out.println(panelLayoutRow + " " + panelRows);
		panelLayout.setRowConstraints(panelLayoutRow);
		panel.add(t, "flowx,cell 1 " + (panelRows-1) + " ,grow");
		panel.add(b, "cell 1 " + (panelRows-1) + " ");
		panel.add(btnAddDir, "flowy,cell 1 " +(panelRows) + " 2 1,alignx center");
		panel.revalidate();
		System.out.println("cell 1 " + panelRows + " ");
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
				if(txtTest != null && !txtTest.equals("")){
					jfc.setCurrentDirectory(new java.io.File(txtTest.getText()));
				}
				else{
					jfc.setCurrentDirectory(new java.io.File("."));
				}
				int n = jfc.showOpenDialog(Interface.this);
				if(n == 0){
					String s = jfc.getSelectedFile().getAbsolutePath();
					txtTest.setText(s);
					System.out.println(s);
				}
			}
			else if(event.getSource() == btnRootPathSrc){
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(txtRootPathSrc != null && !txtRootPathSrc.equals("")){
					jfc.setCurrentDirectory(new java.io.File(txtRootPathSrc.getText()));
				}
				else{
					jfc.setCurrentDirectory(new java.io.File("."));
				}
				int n = jfc.showOpenDialog(Interface.this);
				if(n == 0){
					String s = jfc.getSelectedFile().getAbsolutePath();
					txtRootPathSrc.setText(s);
					System.out.println(s);
				}
			}
			else if(event.getSource() == btnClassRoot){
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(txtClassRoot != null && !txtClassRoot.equals("")){
					jfc.setCurrentDirectory(new java.io.File(txtClassRoot.getText()));
				}
				else{
					jfc.setCurrentDirectory(new java.io.File("."));
				}
				int n = jfc.showOpenDialog(Interface.this);
				if(n == 0){
					String s = jfc.getSelectedFile().getAbsolutePath();
					txtClassRoot.setText(s);
					System.out.println(s);
				}
			}
			else if(event.getSource() == btnClassTest){
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(txtClassTest != null && !txtClassTest.equals("")){
					jfc.setCurrentDirectory(new java.io.File(txtClassTest.getText()));
				}
				else{
					jfc.setCurrentDirectory(new java.io.File("."));
				}
				int n = jfc.showOpenDialog(Interface.this);
				if(n == 0){
					String s = jfc.getSelectedFile().getAbsolutePath();
					txtClassTest.setText(s);
					System.out.println(s);
				}
			}
			else if(event.getSource() == btnRootPathDest){
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(txtRootPathDest != null && !txtRootPathDest.equals("")){
					jfc.setCurrentDirectory(new java.io.File(txtRootPathDest.getText()));
				}
				else{
					jfc.setCurrentDirectory(new java.io.File("."));
				}
				int n = jfc.showOpenDialog(Interface.this);
				if(n == 0){
					String s = jfc.getSelectedFile().getAbsolutePath();
					txtRootPathDest.setText(s);
					System.out.println(s);
				}
			}
			else if(event.getSource() == btnRunTest){
				JUnitCore junit = new JUnitCore();
				ArrayList<File> directories = new ArrayList<File>();
				runJunitTest(junit, directories, txtTest.getText());			
			}
			else if(event.getSource() == btnProcess){

				try {
					MethodSignatureExtractor mse = new MethodSignatureExtractor(textMidFiles.getText(), txtRootPathSrc.getText());
					TreeMap<String, Integer> m = mse.parseFilesInDir(txtRootPathSrc.getText());
					ParenthesisAdder p = new ParenthesisAdder(m, txtRootPathSrc.getText(), txtRootPathDest.getText(), textMidFiles.getText());
					p.parseFilesInDir(txtRootPathSrc.getText());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(event.getSource() == btnAddDir){
					addForm();
			}

		}

		private void runJunitTest(JUnitCore junit, ArrayList<File> directories, String path) {
			File f = new File(path);
			ArrayList<URL> urlList = new ArrayList<URL>();
		
			File bin = new File(txtClassTest.getText());
			File bin2 = new File(txtClassRoot.getText());
			try {
				urlList.add(bin.toURI().toURL());
				urlList.add(bin2.toURI().toURL());
				
				for (JTextField  txtf : additionalTextField) {
					urlList.add(new File(txtf.getText()).toURI().toURL());
				}
				
				URL[] urlArray = new URL[2];
				urlArray = urlList.toArray(urlArray);

				URLClassLoader urlClassloader = URLClassLoader.newInstance(urlArray);

				File[] files = f.listFiles();
				for (int i = 0; i < files.length; i++) {
					if(files[i].isFile()){
						System.out.println("file attuale: " + files[i]);
						String fullname = getFullyQualifiedName(files[i].getAbsolutePath(), txtTest.getText());
						System.out.println("fullname: " + fullname);

						try {
							Class cl = Class.forName(fullname, false, urlClassloader);
							System.out.println("CLASSEEEE: " + cl);
							Result result = junit.run(cl);
							System.out.println("Test finito");
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
					else{
						directories.add(files[i]);
					}
				}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("FINITI I CASI DI TEST");
			if(!directories.isEmpty()){
				String dir =  directories.get(0).getAbsolutePath();
				directories.remove(0);
				runJunitTest(junit, directories,dir);
			}
		}
		
	}
	
	
	class PanelButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			for (int i = 0; i < additionalButtons.size(); i++) {
				if(event.getSource() == additionalButtons.get(i)){
					JFileChooser jfc = new JFileChooser();
					jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					jfc.setCurrentDirectory(new java.io.File("."));
					int n = jfc.showOpenDialog(Interface.this);
					if(n == 0){
						String s = jfc.getSelectedFile().getAbsolutePath();
						additionalTextField.get(i).setText(s);
						System.out.println(s);
					}
				}
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
            progressBar.setIndeterminate(false);
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
