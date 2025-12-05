package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LocalFileSystemImageManager implements FileSystemImageManager {
    private static final Set<String> SUPPORTED_FORMATS = Set.of("jpg", "jpeg", "png", "bmp");

    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {
        if (imageFile == null) {
            throw new IllegalArgumentException("File cannot be null.");
        }

        if (!imageFile.exists()) {
            throw new IOException("File does not exist: " + imageFile.getPath());
        }

        if (!imageFile.isFile()) {
            throw new IOException("The provided path is not a regular file: " + imageFile.getPath());
        }

        if (!isSupportedFormat(imageFile)) {
            throw new IOException("File format not supported.");
        }

        BufferedImage image = ImageIO.read(imageFile);

        if (image == null) {
            throw new IOException("Unsupported or corrupted image format: " + imageFile.getPath());
        }

        return image;
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {
        if (imagesDirectory == null) {
            throw new IllegalArgumentException("Image directory cannot be null.");
        }

        if (!imagesDirectory.exists()) {
            throw new IOException("Image directory does not exist: " + imagesDirectory.getPath());
        }

        if (!imagesDirectory.isDirectory()) {
            throw new IOException("The provided path is not a regular directory: " + imagesDirectory.getPath());
        }

        File[] files = imagesDirectory.listFiles();
        if (files == null) {
            throw new IOException("Unable to read directory: " + imagesDirectory.getPath());
        }

        List<BufferedImage> images = new ArrayList<>();

        for (File file : files) {
            images.add(loadImage(file));
        }

        return images;
    }

    @Override
    public void saveImage(BufferedImage image, File imageFile) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null.");
        }

        if (imageFile == null) {
            throw new IllegalArgumentException("File cannot be null.");
        }

        if (imageFile.exists()) {
            throw new IOException("File already exists: " + imageFile.getPath());
        }

        File parent = imageFile.getParentFile();
        if (parent != null && !parent.exists()) {
            throw new IOException("Parent directory does not exist: " + parent.getPath());
        }

        if (!isSupportedFormat(imageFile)) {
            throw new IOException("File format not supported.");
        }

        String format = getFileExtension(imageFile);

        if (!ImageIO.write(image, format, imageFile)) {
            throw new IOException("Could not write to file.");
        }
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf('.');

        if (lastIndex > 0 && lastIndex < name.length() - 1) {
            return name.substring(lastIndex + 1);
        }

        return "";
    }

    private boolean isSupportedFormat(File file) {
        return SUPPORTED_FORMATS.contains(getFileExtension(file));
    }
}
