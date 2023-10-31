import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class FolderLocker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input folder path
        System.out.println("Enter the path of the folder you want to lock: ");
        String folderPath = scanner.nextLine();

        // Input password
        System.out.println("Set a password for the folder: ");
        String password = scanner.nextLine();

        // Lock the folder
        lockFolder(folderPath, password);

        // Input the password to unlock the folder
        System.out.println("Enter the password to unlock the folder: ");
        String inputPassword = scanner.nextLine();

        // Unlock the folder
        if (inputPassword.equals(password)) {
            unlockFolder(folderPath);
            System.out.println("Folder unlocked successfully.");
        } else {
            System.out.println("Incorrect password. Folder not unlocked.");
        }

        scanner.close();
    }

    private static void lockFolder(String folderPath, String password) {
        File folder = new File(folderPath);
        if (folder.exists()) {
            try {
                // Create a locked folder with a different name
                File lockedFolder = new File(folderPath + "_locked");
                lockedFolder.mkdir();

                // Move the files to the locked folder
                for (File file : folder.listFiles()) {
                    Files.move(file.toPath(), new File(lockedFolder.getPath() + File.separator + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                System.out.println("Folder locked successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Folder does not exist.");
        }
    }

    private static void unlockFolder(String folderPath) {
        File lockedFolder = new File(folderPath + "_locked");
        if (lockedFolder.exists()) {
            try {
                // Move the files back to the original folder
                for (File file : lockedFolder.listFiles()) {
                    Files.move(file.toPath(), new File(folderPath + File.separator + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Delete the locked folder
                lockedFolder.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Locked folder does not exist.");
        }
    }
}
