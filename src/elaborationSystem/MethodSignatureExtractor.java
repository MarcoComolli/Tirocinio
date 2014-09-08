package elaborationSystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class MethodSignatureExtractor {
	
	
	private LinkedList<File> dirList = new LinkedList<File>();
	private String currentFileName;
	private String currentDir;
	private TreeMap<String, Integer> methodMap;
	private File currentFile;

	
	BufferedWriter out;
	

	public MethodSignatureExtractor(String pathWrite) {
		
		methodMap = new TreeMap<String, Integer>();
		try {
			out = new BufferedWriter(new FileWriter(pathWrite));
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	

	// use ASTParse to parse string
	public void parse(String str) throws IOException {

		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setIgnoreMethodBodies(true);

		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {

			Set names = new HashSet();
			
            @Override
            public boolean visit(MethodInvocation node)
            {
            	SimpleName name = node.getName();
                String methodName = name.toString();
                
//                ITypeBinding typeBinding = node.getExpression().resolveTypeBinding();
//                IType type = (IType)typeBinding.getJavaElement();
//
//                System.out.printf("Type %s (method %s) calls %s\n", methodName, type.getFullyQualifiedName());
//                System.out.println("Type %s (method %s) calls %s\n"+ methodName+" "+ type.getFullyQualifiedName());
               
                return true;
            }

			@Override
			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name = node.getName();
				this.names.add(name.getIdentifier());

				return false; // do not continue
			}

			@Override
			public boolean visit(SimpleName node) {
				if (this.names.contains(node.getIdentifier())) {
				}
				return true;
			}
			
			public boolean visit(AnonymousClassDeclaration anonyomousClassDeclaration) {
				System.out.println("Visito classe anonima: ");
			    System.out.println(anonyomousClassDeclaration.toString());

			    return true;
			}

			@Override
			public boolean visit(MethodDeclaration node) {
				String nodeName;
				Type returned;
				String temp;
				
				nodeName = node.getName().toString();
				returned = node.getReturnType2(); //null se è un costruttore
				temp = extractPackageFromFile(currentFile) +  "/" + currentFileName + " " + nodeName + ".";
				
				System.out.println("Method: '" + node + "'"
						+ " return: " + node.getReturnType2() + "\nat line "
						+ +cu.getLineNumber(node.getStartPosition())
						+ "\n\n altri fattori: \n" + "node name "
						+ node.getName() + "\n" + "return type "
						+ node.getReturnType2()
						+ "\nparameters: ");
				
				List<SingleVariableDeclaration> list = node.parameters();
				for (int i = 0; i < list.size(); i++) {
					System.out.println(" - " + list.get(i).getType());
					temp = temp.concat(list.get(i).getType().toString() + " ");
				}
				if(node.getReturnType2() != null){//!nodeName.equals(currentFileName)
					methodMap.put(temp, cu.getLineNumber(node.getStartPosition()));
				}
				
				
				try {
					if(returned != null){ //se non è un costruttore
						System.out.println("SCRIVO! " + temp);
						out.write(temp);
						out.newLine();
						out.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}

			@Override
			public boolean visit(MethodRef node) {
				System.err.println("Method of '" + node + "' at line "
						+ cu.getLineNumber(node.getStartPosition()));
				return true;
			}
		});
	}


	// read file content into a string
	public String readFileToString(String filePath) throws IOException {
		currentFileName = extractClassFromPathString(filePath);
		currentDir = filePath;
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}
	
	public String extractFileFromPathString(String path){
		int i = path.lastIndexOf('\\');
		return path.substring(i+1);
	}
	
	public String extractClassFromPathString(String path){
		System.out.println(path);
		File f = new File(path);
		String fileName = f.getName();
		System.out.println(f.getName());
		if(fileName.contains(".")){
			int j = fileName.lastIndexOf('.');
			return fileName.substring(0,j);
		}
		else{
			return fileName;
		}
		
	}
	
	public String extractPackageFromFile(File f){
		String path = f.getParent();
		String temp;
		if(path != null){
			int i = path.lastIndexOf('\\');
			temp = path.substring(0,i);
			i = temp.lastIndexOf('\\');
			return path.substring(i+1).replace("\\", "/");
		}
		else{
			return null;
		}
	}

	// loop directory to get file list
	public TreeMap<String, Integer> parseFilesInDir(String dirPath){
		
		File dirs = new File(".");
		// String dirPath = dirs.getCanonicalPath() +
		// File.separator+"src"+File.separator;
		//dirPath = "C:/Users/Marco/Desktop/pmd-src-5.1.1/src/main/java/net/sourceforge/pmd";

		
		File root = new File(dirPath);
		System.out.println("rootpath: " + root.getAbsolutePath());
		File[] files = root.listFiles();
		if(files == null){
			if (root.isFile()) {
				currentFile = root;
				try {
					parse(readFileToString(root.getAbsolutePath()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return methodMap;
			}
		}
		for (int i = 0; i < files.length; i++) {
			System.out.println("file: " + files[i].getAbsolutePath());
		}
		String filePath = null;
		
		for (File f : files) {
			currentFile = f;
			filePath = f.getAbsolutePath();
			System.out.println("Current: " + filePath);
			if (f.isFile()) {

				try {
					parse(readFileToString(filePath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if(f.isDirectory()){ //Sennò aggiungilo alla lista e poi ci entrerai a vedere
				dirList.add(f);
			}
		}
		
		if(!dirList.isEmpty()){ //se la directory non è vuota
			System.out.println("Cerco in:" + dirList.getFirst().getAbsolutePath());
			parseFilesInDir(dirList.poll().getAbsolutePath());
		}
		return methodMap;
	}

}
