import java.io.File;
import java.io.IOException;
import java.util.*;

public class ProgramClass {

    public static void main(String[] args) {
        File file = new File("fileSystem");
        userInteraction(file);
    }

    public static void userInteraction(File file) {
        Scanner answer = new Scanner(System.in);

        while (true) {
            System.out.println("You have main directory - \"FileSystem\".");
            System.out.println("Enter the number of the command, which you want to execute.");
            System.out.println("1 - Create new file.");
            System.out.println("2 - Create new directory.");
            System.out.println("3 - Show your directory.");
            System.out.println("0 - End the program.");

            try {
                switch (answer.nextInt()) {
                    case 1:
                        createFileOrDirectory(file, (byte) 1);
                        break;
                    case 2:
                        createFileOrDirectory(file, (byte) 2);
                        break;
                    case 3:
                        showStructure(file);
                        break;
                    case 0:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("You have entered the number of a command that does not exist.");
                }
            } catch (InputMismatchException e) {
                System.out.println("You entered the wrong command. ");
                break;
            }
        }
    }

    public static void showStructure(File file) {
        List<File> files = new LinkedList<>(Arrays.asList(file.listFiles()));
        files.sort((left, right) -> {
            if (left.isFile() && right.isDirectory()) {
                return 1;
            } else if (right.isFile() && left.isDirectory()) {
                return -1;
            }
            return left.getName().compareTo(right.getName());
        });

        List<String> receivedList = new LinkedList<>(additionalRecursiveMethodForShowStructure(files, ""));
        receivedList.forEach(string -> System.out.println(string));
    }

    public static List<String> additionalRecursiveMethodForShowStructure(List<File> files, String countOfSpaces) {
        List<String> finalStringListOfFiles = new LinkedList<>();
        String tempCountOfSpaces = countOfSpaces + " ";
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).isDirectory()) {
                finalStringListOfFiles.add(countOfSpaces + "*" + files.get(i).getName());
                List<File> tempListOfFiles = new LinkedList<>(Arrays.asList(files.get(i).listFiles()));
                finalStringListOfFiles.addAll(additionalRecursiveMethodForShowStructure(tempListOfFiles, tempCountOfSpaces));
            } else if (files.get(i).isFile()) {
                finalStringListOfFiles.add(countOfSpaces + files.get(i).getName());
            }
        }
        return finalStringListOfFiles;
    }

    public static void createFileOrDirectory(File file, byte fileOrDirectory) {
        Scanner fileOrDirectoryNameScanner = new Scanner(System.in);
        switch (fileOrDirectory) {
            case 1:
                System.out.println("Enter a name for this file.");
                String fileName = fileOrDirectoryNameScanner.nextLine();
                File newFile = new File(file.getName() + "/" + fileName);
                try {
                    if (newFile.createNewFile()) {
                        System.out.println("The file was created successfully.");
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                break;
            case 2:
                System.out.println("Enter a name for this directory.");
                String directoryName = fileOrDirectoryNameScanner.nextLine();
                File newDirectory = new File(file.getName() + "/" + directoryName + "/");
                if (newDirectory.mkdir()) {
                    System.out.println("The directory was created successfully.");
                } else {
                    System.out.println("Directory already exists.");
                }
                break;
        }
    }
}
