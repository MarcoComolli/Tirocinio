package elaborationSystem;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import elaborationSystem.Interface.CopyTask;


public class CopyFileVisitor extends SimpleFileVisitor<Path> {
	private final Path targetPath;
	private Path sourcePath = null;
	private CopyTask ct;
	private int progress = 0;
	private int filesNumber;
	private double increment;
	
	public CopyFileVisitor(Path targetPath, CopyTask ct, int filesNumber) {
		this.targetPath = targetPath;
		this.ct = ct;
		this.filesNumber = filesNumber;
	}

	@Override
	public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
		if (sourcePath == null) {
			sourcePath = dir;
		} else {
			Files.createDirectories(targetPath.resolve(sourcePath.relativize(dir)));
		}
		return FileVisitResult.CONTINUE;
	}

    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
    	Files.copy(file, targetPath.resolve(sourcePath.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
    	increment += 100.0/filesNumber;
    	progress = (int)increment;
    	ct.setTheProgress(progress);
		
    	return FileVisitResult.CONTINUE;
    }
}