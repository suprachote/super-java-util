package com.suprachote.superjavautil.service;

import com.google.common.base.CaseFormat;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service
public class TextFileInputService {

    public TextFileInputService() {

    }

    public void runFirst(){
        //read file
        ArrayList<String> inputTextList = new ArrayList<>();
        try {
            inputTextList = readFileToArraylist("input_file.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> outputTextList;
        String outputText;

        outputTextList = convertDatabaseColumnToEntityParameter(inputTextList);
        outputText = converArraylistToStringNewLineEach(outputTextList);

        try {
            writeStringToFile("output_file.txt",outputText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("output:\n" + outputText);
    }

    public ArrayList<String> readFileToArraylist(String fileName) throws IOException {
        //read input file
        Resource resource = new ClassPathResource(fileName);
//        InputStream inputStream = resource.getInputStream();
        File file = resource.getFile();

        // doc>> https://www.baeldung.com/java-file-to-arraylist
        //put each line of file into arraylist
        ArrayList<String> inputTextList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                inputTextList.add(br.readLine());
            }
        }

        return inputTextList;
    }
    private ArrayList<String> convertFromUpperUnderscoreToLowerCamel(ArrayList<String> inputTextList){
        ArrayList<String> outputTextList = new ArrayList<>();
        for(String line: inputTextList){
            outputTextList.add(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, line));
        }
        return outputTextList;
    }

    private ArrayList<String> addTextToFrontAndBack(ArrayList<String> inputTextList,String frontText,String backText){
        ArrayList<String> outputTextList = new ArrayList<>();
        for(String line: inputTextList){
            outputTextList.add(frontText+line+backText);
        }
        return outputTextList;
    }

    private ArrayList<String> convertDatabaseColumnToEntityParameter(ArrayList<String> databaseColumnList){
        ArrayList<String> camelCaseTextList = new ArrayList<>();
        camelCaseTextList = convertFromUpperUnderscoreToLowerCamel(databaseColumnList);
        ArrayList<String> tempList = new ArrayList<>();
        for(int i = 0; i < databaseColumnList.size(); i++){
            String firstLine = "@Column( name = \"" + databaseColumnList.get(i) + "\")";
            String secondLine = "private String " + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, databaseColumnList.get(i));
            tempList.add(firstLine + "\n" + secondLine + ";");
        }
        return tempList;
    }

    private String converArraylistToStringNewLineEach(ArrayList<String> stringArrayList){
        String outputString = "";
        for(String i: stringArrayList){
            outputString = outputString + i + "\n";
        }
        return outputString;
    }

    private void writeStringToFile(String fileName, String textToWrite) throws IOException {
        File file = new File("./" + fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(textToWrite);
        writer.close();
        System.out.println("output file are saved at: super-java-util\\target\\classes\\output_file.txt");
    }
}
