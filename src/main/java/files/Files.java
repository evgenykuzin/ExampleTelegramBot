package files;

import java.io.*;
import java.util.ArrayList;

public class Files {
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
        return arrayList;
    }

    public static int fileLinesCount(String folder){
        return readFile(folder).size();
    }

}
