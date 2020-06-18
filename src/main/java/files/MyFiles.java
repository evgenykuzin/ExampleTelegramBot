package files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.stream.Stream;

public class MyFiles {
    public static String resources;

    static {
        try {
            resources = new File(".").getCanonicalPath() + "\\src\\main\\java\\files\\resources\\";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static File file1 = new File(resources+"\\img\\ricardo.jpg");

    public static void writeToFile(String folder, String string) {
        File file = new File(resources + folder);
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(string+"\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readFile(String folder) {
        ArrayList<String> arrayList = new ArrayList<>();
        FileReader fileReader = null;
        try {
            File file = new File(resources + folder);
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String string;
        try {
            do {
                string = bufferedReader.readLine();
                if (string != null) {
                    arrayList.add(string);
                }
            } while (string != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void deleteString(String string, String folder) {
        try {
            Path input = Paths.get(folder);
            Path temp = Files.createTempFile("temp", ".txt");
            Stream<String> lines = Files.lines(input);
            try (BufferedWriter writer = Files.newBufferedWriter(temp)) {
                lines
                        .filter(line -> !line.contains(string))
                        .forEach(line -> {
                            try {
                                writer.write(line);
                                writer.newLine();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
            Files.move(temp, input, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static int fileLinesCount(String folder){
        return readFile(folder).size();
    }

}
