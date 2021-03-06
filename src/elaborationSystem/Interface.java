package elaborationSystem;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JScrollPane;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import javax.swing.JSeparator;

public class Interface extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, step1Panel, step2Panel, step3Panel, step4Panel;
	private JTabbedPane tabbedPane;
	private JTextField textSearch,textSave, textMidFiles, txtClassTest, txtClassRoot;
	private JButton btnCopy, btnSearchSrc, btnSave, btnSearchTest, btnRunTest, btnOtherFiles, btnProcess, btnAddDir;
	private JButton btnRootPathSrc, btnGetStatistics, btnClassTest, btnRootPathDest, btnClassRoot;
	private CopyTask copyTask;
	private ProcessTask processTask;
	private TestTask testTask;
	private JProgressBar progressBarStep1,progressBarStep2,progressBarStep3;
	private Path source, target;
	private int filesCount = 0;
	private JTextField txtTest, txtRootPathSrc, txtRootPathDest;
	private JLabel lblTestFolder,lblStatus,lblStep2,lblStep1,lblRootPathSrc,lblStep3,lblAdditionalFolders,lblClassTestRoot;
	private JLabel lblAdditionalFilesFolder, lblDestinationPackagesFolder, lblClassRoot, lblStatusStep2, lblStep, lblStatusStep3;
	private JSeparator separator,separator_1,separator_2,separator_3;
	private JScrollPane scrollPane_1;
	private JPanel additionalFormsPanel;
	private ArrayList<JButton> additionalButtons;
	private ArrayList<JTextField> additionalTextField;
	private int panelRows = 0;
	private String panelLayoutCol = "[][grow]";
	private String panelLayoutRow = "[]";
	private MigLayout panelLayout;

	private int ricursionCount = 0;
	private Color barColor;
	private double increment;
	private int progress;
	private ArrayList<URL> projectFolder = new ArrayList<URL>();


	
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
		barColor = new Color(0, 185, 0);
		
		panelLayout = new MigLayout("", panelLayoutCol, panelLayoutRow);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		step1Panel = new JPanel(new MigLayout("", "[][grow]", "[][][][][][][]"));
		step2Panel = new JPanel(new MigLayout("", "[][grow]", "[][][][][][][][]"));
		step3Panel = new JPanel(new MigLayout("", "[][grow]", "[][][][][][grow][][][][][][]"));
		step4Panel = new JPanel(new MigLayout("", "[][grow]", "[][][]"));
		
		
		tabbedPane.addTab("Step 1", step1Panel);
		tabbedPane.addTab("Step 2", step2Panel);
		tabbedPane.addTab("Step 3", step3Panel);
		tabbedPane.addTab("Step 4", step4Panel);
		
		
		
		
		setTitle("Tirocinio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 544, 417);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setFont(new Font("Arial", Font.PLAIN, 11));
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		
		contentPane.add(tabbedPane, "cell 0 0,grow");
		
		lblStep1 = new JLabel("Step 1:");
		lblStep1.setFont(new Font("Arial", Font.BOLD, 11));
		lblStep1.setEnabled(false);
		step1Panel.add(lblStep1, "cell 0 0");
		
		separator_1 = new JSeparator();
		step1Panel.add(separator_1, "cell 0 1 2 1,growx");
		
		JLabel lblNewLabel = new JLabel("Source folder: ");
		lblNewLabel.setToolTipText("Source where are located the source file of java project");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 11));
		step1Panel.add(lblNewLabel, "cell 0 2,alignx left");
		
		textSearch = new JTextField();
		step1Panel.add(textSearch, "flowx,cell 1 2,grow");
		textSearch.setColumns(10);
		
		btnSearchSrc = new JButton("...");
		btnSearchSrc.setFont(new Font("Arial", Font.BOLD, 11));
		step1Panel.add(btnSearchSrc, "cell 1 2,alignx right");
		btnSearchSrc.addActionListener(new ButtonHandler());
		
		JLabel lblNewLabel_1 = new JLabel("Destination folder: ");
		lblNewLabel_1.setToolTipText("Where processed files were saved");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 11));
		step1Panel.add(lblNewLabel_1, "cell 0 3,alignx left");
		
		textSave = new JTextField();
		step1Panel.add(textSave, "flowx,cell 1 3,grow");
		textSave.setColumns(10);
		
		btnSave = new JButton("...");
		btnSave.setFont(new Font("Arial", Font.BOLD, 11));
		step1Panel.add(btnSave, "cell 1 3,alignx right");
		btnSave.addActionListener(new ButtonHandler());
		
		btnCopy = new JButton("Copy");
		btnCopy.setFont(new Font("Arial", Font.BOLD, 11));
		step1Panel.add(btnCopy, "cell 0 4 2 1,alignx center");
		btnCopy.addActionListener(new ButtonHandler());
		
		lblStatus = new JLabel(" Processing requirements...");
		lblStatus.setFont(new Font("Arial", Font.BOLD, 11));
		step1Panel.add(lblStatus, "cell 0 5 2 1");
		lblStatus.setVisible(false);
		
		progressBarStep1 = new JProgressBar();
		progressBarStep1.setStringPainted(true);
		progressBarStep1.setToolTipText("Progress");
		progressBarStep1.setForeground(barColor);
		progressBarStep1.setVisible(false);
		step1Panel.add(progressBarStep1, "cell 0 6 2 1,grow");
		
		
		//panel step 2
		lblStep2 = new JLabel("Step 2:");
		lblStep2.setEnabled(false);
		lblStep2.setFont(new Font("Arial", Font.BOLD, 11));
		step2Panel.add(lblStep2, "cell 0 0");
		
		lblAdditionalFilesFolder = new JLabel("Additional files folder:");
		lblAdditionalFilesFolder.setToolTipText("Where will be saved mid-process files ");
		lblAdditionalFilesFolder.setFont(new Font("Arial", Font.BOLD, 11));
		step2Panel.add(lblAdditionalFilesFolder, "cell 0 2,alignx left");
		
		textMidFiles = new JTextField();
		step2Panel.add(textMidFiles, "flowx,cell 1 2,grow");
		textMidFiles.setColumns(10);
		
		lblRootPathSrc = new JLabel("Original packages folder: ");
		lblRootPathSrc.setToolTipText("Specify the first folder containing packages");
		lblRootPathSrc.setFont(new Font("Arial", Font.BOLD, 11));
		step2Panel.add(lblRootPathSrc, "cell 0 3,alignx left");
		
		txtRootPathSrc = new JTextField();
		txtRootPathSrc.setColumns(10);
		step2Panel.add(txtRootPathSrc, "flowx,cell 1 3,grow");
		
		separator = new JSeparator();
		step2Panel.add(separator, "cell 0 1 2 1,growx");
		
		lblDestinationPackagesFolder = new JLabel("Destination packages folder: ");
		lblDestinationPackagesFolder.setToolTipText("Specify the first folder containing packages");
		lblDestinationPackagesFolder.setFont(new Font("Arial", Font.BOLD, 11));
		step2Panel.add(lblDestinationPackagesFolder, "cell 0 4,alignx left");
		
		txtRootPathDest = new JTextField();
		txtRootPathDest.setColumns(10);
		step2Panel.add(txtRootPathDest, "flowx,cell 1 4,grow");
		
		btnOtherFiles = new JButton("...");
		btnOtherFiles.setFont(new Font("Arial", Font.BOLD, 11));
		step2Panel.add(btnOtherFiles, "cell 1 2");
		btnOtherFiles.addActionListener(new ButtonHandler());
		
		btnRootPathSrc = new JButton("...");
		btnRootPathSrc.setFont(new Font("Arial", Font.BOLD, 11));
		step2Panel.add(btnRootPathSrc, "cell 1 3");
		btnRootPathSrc.addActionListener(new ButtonHandler());
		
		btnRootPathDest = new JButton("...");
		btnRootPathDest.setFont(new Font("Arial", Font.BOLD, 11));
		step2Panel.add(btnRootPathDest, "cell 1 4");
		btnRootPathDest.addActionListener(new ButtonHandler());
		
		btnProcess = new JButton("Process Files");
		btnProcess.setFont(new Font("Arial", Font.BOLD, 11));
		step2Panel.add(btnProcess, "cell 0 5 2 1,alignx center");
		btnProcess.addActionListener(new ButtonHandler());
		
		lblStatusStep2 = new JLabel("Status");
		step2Panel.add(lblStatusStep2, "cell 0 6");
		
		progressBarStep2 = new JProgressBar();
		progressBarStep2.setStringPainted(true);
		progressBarStep2.setForeground(barColor);
		step2Panel.add(progressBarStep2, "cell 0 7 2 1,grow");
		
		
		//panel step 3
		lblStep3 = new JLabel("Step 3:");
		lblStep3.setFont(new Font("Arial", Font.BOLD, 11));
		lblStep3.setEnabled(false);
		step3Panel.add(lblStep3, "cell 0 0");
		
		separator_3 = new JSeparator();
		step3Panel.add(separator_3, "cell 0 1 2 1,growx");
		
		lblTestFolder = new JLabel("Test folder:");
		lblTestFolder.setFont(new Font("Arial", Font.BOLD, 11));
		step3Panel.add(lblTestFolder, "cell 0 2,alignx left");
		
		txtTest = new JTextField();
		step3Panel.add(txtTest, "flowx,cell 1 2,grow");
		txtTest.setColumns(10);
		
		btnRunTest = new JButton("Run Tests");
		btnRunTest.setFont(new Font("Arial", Font.BOLD, 11));
		btnRunTest.addActionListener(new ButtonHandler());
		step3Panel.add(btnRunTest, "cell 0 9 2 1,alignx center");
		
		lblClassRoot = new JLabel("Compiled source code folder:");
		lblClassRoot.setToolTipText("Folder where are stored the .class test files");
		lblClassRoot.setFont(new Font("Arial", Font.BOLD, 11));
		step3Panel.add(lblClassRoot, "cell 0 4,alignx left");
		
		txtClassRoot = new JTextField();
		txtClassRoot.setColumns(10);
		step3Panel.add(txtClassRoot, "flowx,cell 1 4,grow");
		
		
		lblClassTestRoot = new JLabel("Compiled tests folder:");
		lblClassTestRoot.setToolTipText("Folder where are stored the .class test files");
		lblClassTestRoot.setFont(new Font("Arial", Font.BOLD, 11));
		step3Panel.add(lblClassTestRoot, "cell 0 3,alignx left");
		
		txtClassTest = new JTextField();
		txtClassTest.setColumns(10);
		step3Panel.add(txtClassTest, "flowx,cell 1 3,grow");
		
		scrollPane_1 = new JScrollPane();
		step3Panel.add(scrollPane_1, "cell 0 5 2 4,grow");
		
		additionalFormsPanel = new JPanel();
		scrollPane_1.setViewportView(additionalFormsPanel);
		additionalFormsPanel.setLayout(panelLayout);
		
		btnAddDir = new JButton("+");
		btnAddDir.setFont(new Font("Arial", Font.BOLD, 12));
		additionalFormsPanel.add(btnAddDir, "flowy,cell 0 0 2 1,alignx center");
		btnAddDir.addActionListener(new ButtonHandler());

		
		lblAdditionalFolders = new JLabel("Additional folders or JARs");
		scrollPane_1.setColumnHeaderView(lblAdditionalFolders);
		
		btnSearchTest = new JButton("...");
		btnSearchTest.setFont(new Font("Arial", Font.BOLD, 11));
		step3Panel.add(btnSearchTest, "cell 1 2");
		btnSearchTest.addActionListener(new ButtonHandler());
		

		
		btnClassTest = new JButton("...");
		btnClassTest.setFont(new Font("Arial", Font.BOLD, 11));
		step3Panel.add(btnClassTest, "cell 1 3");
		btnClassTest.addActionListener(new ButtonHandler());
		

		
		btnClassRoot = new JButton("...");
		btnClassRoot.setFont(new Font("Arial", Font.BOLD, 11));
		step3Panel.add(btnClassRoot, "cell 1 4");
		btnClassRoot.addActionListener(new ButtonHandler());
		
		lblStatusStep3 = new JLabel("Status");
		step3Panel.add(lblStatusStep3, "cell 0 10");
		
		progressBarStep3 = new JProgressBar();
		progressBarStep3.setStringPainted(true);
		progressBarStep3.setForeground(barColor);
		step3Panel.add(progressBarStep3, "cell 0 11 2 1,grow");
		
		//panel step 4
		
		lblStep = new JLabel("Step 4");
		lblStep.setEnabled(false);
		lblStep.setFont(new Font("Arial", Font.BOLD, 12));
		step4Panel.add(lblStep, "cell 0 0");
		
		separator_2 = new JSeparator();
		step4Panel.add(separator_2, "cell 0 1 2 1,growx");
		
		btnGetStatistics = new JButton("Get Statistics");
		step4Panel.add(btnGetStatistics, "cell 0 2 2 1,alignx center,aligny center");
		btnGetStatistics.addActionListener(new ButtonHandler());
		
		//TODO
		//rimuovere  qui di seguito. Solo per test
		//pmd
//		textMidFiles.setText("C:\\Users\\Marco\\Desktop\\files");
//		txtRootPathSrc.setText("C:\\Users\\Marco\\Desktop\\pmd-src-5.1.3\\src\\main\\java");
//		txtRootPathDest.setText("C:\\Users\\Marco\\Desktop\\nn\\src\\main\\java");
//
//		
//		txtTest.setText("C:\\Users\\Marco\\Desktop\\nn\\src\\test\\java");
//		txtClassTest.setText("C:\\Users\\Marco\\Desktop\\nn\\target\\test-classes");
//		txtClassRoot.setText("C:\\Users\\Marco\\Desktop\\nn\\target\\classes");
//			
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\.m2\\repository\\org\\apache\\ant\\ant-testutil\\1.7.1\\ant-testutil-1.7.1.jar"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\.m2\\repository\\jaxen\\jaxen\\1.1.1\\jaxen-1.1.1.jar"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\Desktop\\nn"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\Desktop\\nn\\target"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\Desktop\\nn\\src"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\Desktop\\nn\\src\\test\\resources\\net\\sourceforge\\pmd\\lang\\java\\rule\\codesize\\xml"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\Desktop\\nn\\src\\main\\resources\\rulesets"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\Desktop\\nn\\target\\test-classes\\net\\sourceforge\\pmd\\cpd\\files"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\.m2\\repository\\org\\apache\\commons\\commons-lang3\\3.1\\commons-lang3-3.1.jar"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\.m2\\repository\\commons-io\\commons-io\\2.2\\commons-io-2.2.jar"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\.m2\\repository\\org\\ow2\\asm\\asm\\5.0.2\\asm-5.0.2.jar"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\.m2\\repository\\org\\mozilla\\rhino\\1.7R3\\rhino-1.7R3.jar"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\.m2\\repository\\net\\sourceforge\\saxon\\saxon\\9.1.0.8\\saxon-9.1.0.8.jar"));
//		additionalTextField.add(new JTextField("C:\\Users\\Marco\\.m2\\repository\\net\\sourceforge\\saxon\\saxon\\9.1.0.8\\saxon-9.1.0.8-dom.jar"));
	
		
		//files di prova
//		textSearch.setText("F:\\Documenti - Marco\\JavaPrg\\Workspace\\ProvaTests\\");
//		textSave.setText("C:\\Users\\Marco\\Desktop\\AAA copiati\\");
//		
//
//		textMidFiles.setText("C:\\Users\\Marco\\Desktop\\files");
//		txtRootPathSrc.setText("F:\\Documenti - Marco\\JavaPrg\\Workspace\\ProvaTests\\src\\classi");
//		txtRootPathDest.setText("C:\\Users\\Marco\\Desktop\\AAA copiati\\src\\classi");
//
//		
//		txtTest.setText("C:\\Users\\Marco\\Desktop\\AAA copiati\\src\\classiditest");
//		txtClassTest.setText("C:\\Users\\Marco\\Desktop\\AAA copiati\\bin");
//		txtClassRoot.setText("C:\\Users\\Marco\\Desktop\\AAA copiati\\bin\\files");
		
		
		//junit
//		
//		textMidFiles.setText("C:\\Users\\Marco\\Desktop\\files");
//		txtRootPathSrc.setText("C:\\Users\\Marco\\Desktop\\junit-master\\junit-master\\src\\main\\java");
//		txtRootPathDest.setText("C:\\Users\\Marco\\Desktop\\nnjunit\\junit-master\\src\\main\\java");
//
//		
//		txtTest.setText("C:\\Users\\Marco\\Desktop\\nnjunit\\junit-master\\src\\test\\java");
//		txtClassTest.setText("C:\\Users\\Marco\\Desktop\\nnjunit\\junit-master\\target\\classes");
//		txtClassRoot.setText("C:\\Users\\Marco\\Desktop\\nnjunit\\junit-master\\target\\test-classes");
		
		//jfreechart
		textMidFiles.setText("C:\\Users\\Marco\\Desktop\\files");
		txtRootPathSrc.setText("C:\\Users\\Marco\\Desktop\\jfreechart-1.0.19\\jfreechart-1.0.19\\source");
		txtRootPathDest.setText("C:\\Users\\Marco\\Desktop\\aajfreechart\\jfreechart-1.0.19\\source");

		
		txtTest.setText("C:\\Users\\Marco\\Desktop\\aajfreechart\\jfreechart-1.0.19\\tests");
		txtClassTest.setText("C:\\Users\\Marco\\Desktop\\aajfreechart\\jfreechart-1.0.19\\target\\test-classes");
		txtClassRoot.setText("C:\\Users\\Marco\\Desktop\\aajfreechart\\jfreechart-1.0.19\\target\\classes");
		
		additionalTextField.add(new JTextField("/C:/Users/Marco/.m2/repository/javax/servlet/servlet-api/2.5/servlet-api-2.5.jar"));
		additionalTextField.add(new JTextField("/C:/Users/Marco/.m2/repository/org/jfree/jcommon/1.0.23/jcommon-1.0.23.jar"));
		additionalTextField.add(new JTextField("/C:/Users/Marco/.m2/repository/junit/junit/4.11/junit-4.11.jar"));
		additionalTextField.add(new JTextField("/C:/Users/Marco/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar"));
		additionalTextField.add(new JTextField("/D:/Programmi/Eclipse/eclipse/configuration/org.eclipse.osgi/bundles/351/1/.cp/"));
		additionalTextField.add(new JTextField("/D:/Programmi/Eclipse/eclipse/configuration/org.eclipse.osgi/bundles/350/1/.cp/"));

		
	}
	
	
	private void getFilesNumber(String dirPath, JLabel label, JProgressBar progressBar ) {
		label.setText(" Processing requirements...");
		label.setVisible(true);
		progressBar.setIndeterminate(true);
		File f = new File(dirPath);
		File[] files = f.listFiles();
		if (files != null)
			for (int i = 0; i < files.length; i++) {
				if(files[i].isFile()){
					String filename = files[i].getName();
					String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
					System.out.println(extension);
					if (extension.equals("java")) {
						filesCount++;
					}
				}
				File file = files[i];
				if (file.isDirectory()) {
					getFilesNumber(file.getAbsolutePath(),label,progressBar);
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
		panelLayout.setRowConstraints(panelLayoutRow);
		additionalFormsPanel.add(t, "flowx,cell 1 " + (panelRows-1) + " ,grow");
		additionalFormsPanel.add(b, "cell 1 " + (panelRows-1) + " ");
		additionalFormsPanel.add(btnAddDir, "flowy,cell 1 " +(panelRows) + " 2 1,alignx center");
		additionalFormsPanel.revalidate();
	}
	
	private void runJunitTest(JUnitCore junit, ArrayList<File> directories, String path) {
		System.out.println("Ricorsione: " + ricursionCount);
		File f = new File(path);
		ArrayList<URL> urlList = new ArrayList<URL>();
	
		File bin = new File(txtClassTest.getText());
		File bin2 = new File(txtClassRoot.getText());
		try {
			urlList.add(bin.toURI().toURL());
			urlList.add(bin2.toURI().toURL());
			//TODO
			//prova
//			retrieveAllSubFolders("C:\\Users\\Marco\\Desktop\\nn");
			for (JTextField  txtf : additionalTextField) {
				urlList.add(new File(txtf.getText()).toURI().toURL());
			}
			//fine prova
//			for (URL url : projectFolder) {
//				urlList.add(url);
//			}
			
//			urlList.add(new File("C:\\Users\\Marco\\Desktop\\nn\\target\\test-classes").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\Desktop\\nn\\target\\classes").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\org\\apache\\ant\\ant\\1.8.1\\ant-1.8.1.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\org\\apache\\ant\\ant-launcher\\1.8.1\\ant-launcher-1.8.1.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\org\\apache\\ant\\ant-testutil\\1.7.1\\ant-testutil-1.7.1.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\jaxen\\jaxen\\1.1.1\\jaxen-1.1.1.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\dom4j\\dom4j\\1.6.1\\dom4j-1.6.1.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\jdom\\jdom\\1.0\\jdom-1.0.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\xml-apis\\xml-apis\\1.3.02\\xml-apis-1.3.02.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\xom\\xom\\1.0\\xom-1.0.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\xerces\\xmlParserAPIs\\2.6.2\\xmlParserAPIs-2.6.2.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\com\\beust\\jcommander\\1.27\\jcommander-1.27.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\org\\ow2\\asm\\asm\\5.0.2\\asm-5.0.2.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\net\\sourceforge\\saxon\\saxon\\9.1.0.8\\saxon-9.1.0.8.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\net\\sourceforge\\saxon\\saxon\\9.1.0.8\\saxon-9.1.0.8-dom.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\org\\mozilla\\rhino\\1.7R3\\rhino-1.7R3.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\junit\\junit\\4.11\\junit-4.11.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\org\\hamcrest\\hamcrest-core\\1.3\\hamcrest-core-1.3.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\xerces\\xercesImpl\\2.9.1\\xercesImpl-2.9.1.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\net\\java\\dev\\javacc\\javacc\\5.0\\javacc-5.0.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\commons-io\\commons-io\\2.2\\commons-io-2.2.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\org\\mockito\\mockito-all\\1.9.5\\mockito-all-1.9.5.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\org\\apache\\commons\\commons-lang3\\3.1\\commons-lang3-3.1.jar").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\org\\slf4j\\slf4j-api\\1.7.7\\slf4j-api1.7.7.jar").toURI().toURL());
//			urlList.add(new File("/D:/Programmi/Eclipse/eclipse/configuration/org.eclipse.osgi/bundles/351/1/.cp/").toURI().toURL());
//			urlList.add(new File("/D:/Programmi/Eclipse/eclipse/configuration/org.eclipse.osgi/bundles/350/1/.cp/").toURI().toURL());
//			urlList.add(new File("C:\\Users\\Marco\\.m2\\repository\\org\\hamcrest\\hamcrest-core\\1.3\\hamcrest-core1.3.jar").toURI().toURL());
			

			
			
			URL[] urlArray = new URL[3];
			urlArray = urlList.toArray(urlArray);

			System.out.println(Arrays.toString(urlArray));
			URLClassLoader urlClassloader = URLClassLoader.newInstance(urlArray);
			
			//TEST
			
//			try {
//				Class cl = Class.forName("org.junit.tests.internal.runners.statements.FailOnTimeoutTest", false, urlClassloader);
//
//				MyTracerClass.startRecordTestCoverage();
//				increment += 100.0/filesCount;
//				progress = (int)increment;
//				System.out.println(filesCount);
//				testTask.setTheProgress(progress);					
//				Result result = junit.run(cl);
//				float timeSec = result.getRunTime()/1000f;
//				int testNumber = result.getRunCount();
//				int failCount =  result.getFailureCount();
//				System.out.println("Test finito inizio endtest");
//				MyTracerClass.endRecordTestCoverage("net.sourceforge.pmd.lang.java.rule.codesize.CodesizeRulesTest",timeSec,testNumber, failCount);
//				System.out.println("Test finito " + testNumber + "/" + failCount);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			
			//TEST
			
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				if(files[i].isFile()){
					String filename = files[i].getName();
					String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
					System.out.println(extension);
					if (extension.equals("java")) {
						String fullname = getFullyQualifiedName(files[i].getAbsolutePath(), txtTest.getText());
						System.out.println("fullname: " + fullname);
						try {
							Class cl = Class.forName(fullname, false, urlClassloader);
							System.out.println("CLASSE: " + cl);
							MyTracerClass.startRecordTestCoverage();
							increment += 100.0/filesCount;
							progress = (int)increment;
							System.out.println(filesCount + " progress " + progress);
							testTask.setTheProgress(progress);					
							Result result = junit.run(cl);
							float timeSec = result.getRunTime()/1000f;
							int testNumber = result.getRunCount();
							int failCount =  result.getFailureCount();
							MyTracerClass.endRecordTestCoverage(fullname,timeSec,testNumber, failCount);
							System.out.println("Test finito");

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				else{
					directories.add(files[i]);
				}
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		if(!directories.isEmpty()){
			String dir =  directories.get(0).getAbsolutePath();
			directories.remove(0);
			ricursionCount++;
			System.out.println("Aggiungo 1 a ricorsione " + ricursionCount + " directory: " + directories.size() + "   " + dir );
			runJunitTest(junit, directories,dir);
			System.out.println("Ho finito la ricorsione " + ricursionCount + " -> " +  (ricursionCount-1));
			ricursionCount--;
		}
		if(ricursionCount == 0){
			System.out.println("Finiti i casi di test");
			MyTracerClass.endOfTests();
		}
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
				}
			}
			else if(event.getSource() == btnSave){
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int n = jfc.showSaveDialog(Interface.this);
				if(n == 0){
					String s = jfc.getSelectedFile().getAbsolutePath();
					textSave.setText(s);
				}
			}
			else if(event.getSource() == btnOtherFiles){
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int n = jfc.showSaveDialog(Interface.this);
				if(n == 0){
					String s = jfc.getSelectedFile().getAbsolutePath();
					textMidFiles.setText(s);
				}
			}
			else if(event.getSource() == btnCopy){
				btnCopy.setEnabled(false);
				progressBarStep1.setVisible(true);
				source = new File(textSearch.getText()).toPath();
				new File(textSave.getText()).mkdirs();
				target = new File(textSave.getText()).toPath();

				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));		
				copyTask = new CopyTask();
				copyTask.addPropertyChangeListener(new ChangeListener(progressBarStep1));
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
				}
			}
			else if(event.getSource() == btnRunTest){
				btnRunTest.setEnabled(false);
				progressBarStep3.setVisible(true);
				
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));		
				testTask = new TestTask();
				testTask.addPropertyChangeListener(new ChangeListener(progressBarStep3));
				long a = System.currentTimeMillis();
				testTask.execute();		
				System.out.println("Test eseguiti in " + (System.currentTimeMillis() - a));
			}
			else if(event.getSource() == btnProcess){

				btnProcess.setEnabled(false);
				progressBarStep2.setVisible(true);

				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));		
				processTask = new ProcessTask();
				processTask.addPropertyChangeListener(new ChangeListener(progressBarStep2));
				processTask.execute();
			}
			else if(event.getSource() == btnAddDir){
					addForm();
			}
			else if(event.getSource() == btnGetStatistics){
				long a;
				try {
					a = System.currentTimeMillis();
					TreeSet<String> treeSet = StatisticsDataOrderer.readAndOrderData();
					System.out.println("readAndOrderData " + ((System.currentTimeMillis() - a)/1000.0));
					StatisticsDataOrderer.writeBlockFrequency();
					a = System.currentTimeMillis();
					StatisticsDataOrderer.writeOrderedData(treeSet);
					System.out.println("writeOrderedData " + ((System.currentTimeMillis() - a)/1000.0));
					a = System.currentTimeMillis();
					StatisticsDataOrderer.writeNumberOfLinesForMethod(treeSet);
					System.out.println("writeNumberOfLinesForMethod " + ((System.currentTimeMillis() - a)/1000.0));
					a = System.currentTimeMillis();
					StatisticsDataOrderer.writeCoveredConditions();
					System.out.println("writeCoveredConditions " + ((System.currentTimeMillis() - a)/1000.0));
					a = System.currentTimeMillis();
					StatisticsDataOrderer.writePathsLength();
					System.out.println("writePathsLength " + ((System.currentTimeMillis() - a)/1000.0));
					
				} catch (Exception e) {

					e.printStackTrace();
				}
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
					}
				}
			}

		}
		
	}
	
	class ChangeListener implements PropertyChangeListener{
		JProgressBar progressBar;
		
		public ChangeListener(JProgressBar progressbar){
			this.progressBar = progressbar;
		}

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
            progressBarStep1.setValue(progress);
    		filesCount = 0;
            getFilesNumber(source.toString(), lblStatus, progressBarStep1);
            progressBarStep1.setIndeterminate(false);
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
        	progressBarStep1.setValue(progressBarStep1.getMaximum());
        	setCursor(null); //turn off the wait cursor
        	btnCopy.setEnabled(true);
        }
        
        //setta il progresso
        public void setTheProgress(int a){
        	this.setProgress(a);
        }
    }


	class ProcessTask extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() {
            int progress = 0;       
            //Initialize progress property.

            setProgress(0);
            progressBarStep2.setValue(progress);
    		filesCount = 0;
            getFilesNumber(txtRootPathSrc.getText(), lblStatusStep2, progressBarStep2);
            System.out.println(filesCount);
            progressBarStep2.setIndeterminate(false);
            lblStatusStep2.setText(" Processing files...");
            
            try {
				MyTracerClass.setFilesPath(textMidFiles.getText());
				StatisticsDataOrderer.setFilesPath(textMidFiles.getText());
				MethodSignatureExtractor mse = new MethodSignatureExtractor(textMidFiles.getText(), txtRootPathSrc.getText(),this,filesCount);
				TreeMap<String, Integer> m = mse.parseFilesInDir(txtRootPathSrc.getText());
				DirectoriesScanner p = new DirectoriesScanner(m, txtRootPathSrc.getText(), txtRootPathDest.getText(), textMidFiles.getText(), this, filesCount);
				p.parseFilesInDir(txtRootPathSrc.getText());
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
            catch (Exception e) {
				e.printStackTrace();
			}
            
            lblStatusStep2.setVisible(false);
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
        	Toolkit.getDefaultToolkit().beep();
        	progressBarStep2.setValue(progressBarStep2.getMaximum());
        	setCursor(null); //turn off the wait cursor
        	btnProcess.setEnabled(true);
        }
        
        //setta il progresso
        public void setTheProgress(int a){
        	this.setProgress(a);
        }
    }
	
	
	class TestTask extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() {
            int progress = 0;       
            //Initialize progress property.

            setProgress(0);
            progressBarStep3.setValue(progress);
    		filesCount = 0;
            getFilesNumber(txtTest.getText(), lblStatusStep3, progressBarStep3);
            System.out.println(filesCount);
            progressBarStep3.setIndeterminate(false);
            lblStatusStep3.setText(" Running tests...");
            
            JUnitCore junit = new JUnitCore();
			ArrayList<File> directories = new ArrayList<File>();
			runJunitTest(junit, directories, txtTest.getText());	
            
            lblStatusStep3.setVisible(false);
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
        	Toolkit.getDefaultToolkit().beep();
        	progressBarStep3.setValue(progressBarStep3.getMaximum());
        	setCursor(null); //turn off the wait cursor
        	btnRunTest.setEnabled(true);
        }
        
        //setta il progresso
        public void setTheProgress(int a){
        	this.setProgress(a);
        }
    }

	public void retrieveAllSubFolders(String path){
		ArrayList<File> pathlist = new ArrayList<File>();
		File f = new File(path);
		try {
			File[] files = f.listFiles();
			if (files != null)
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if (file.isDirectory()) {
						projectFolder.add(file.toURI().toURL());
						pathlist.add(file);
					}
				}

			while(!pathlist.isEmpty()){
				f = pathlist.remove(0);
				files = f.listFiles();
				if (files != null)
					for (int i = 0; i < files.length; i++) {
						File file = files[i];
						if (file.isDirectory()) {
							projectFolder.add(file.toURI().toURL());
							pathlist.add(file);
						}
					}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		System.out.println("End");

	}

}
