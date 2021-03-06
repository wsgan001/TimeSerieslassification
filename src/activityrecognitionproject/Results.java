
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activityrecognitionproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instance;

/**
 *
 * @author xju14zpu
 */
public class Results {
    
    public static double NLL(String dataLocation, int folds) throws FileNotFoundException, IOException, Exception{
        double NLL = 0;
        for (int i = 0; i < folds; i++) {
            File inputFile = new File(dataLocation + i + ".csv");
            FileReader reader = new FileReader(inputFile);
            BufferedReader br = new BufferedReader(reader);
            String lineString ;

            br.readLine();
            br.readLine();
            br.readLine();
            while((lineString = br.readLine()) != null){
                String [] lineArr = lineString.split(",");

                if(Double.parseDouble(lineArr[0]) == 0){
                    NLL += Math.log(Double.parseDouble(lineArr[3]))/Math.log(2);
                }
                else if(Double.parseDouble(lineArr[0]) == 1){
                    NLL += Math.log(Double.parseDouble(lineArr[4]))/Math.log(2);
                }
                else if(Double.parseDouble(lineArr[0]) == 2){
                    NLL += Math.log(Double.parseDouble(lineArr[5]))/Math.log(2);
                }
                else if(Double.parseDouble(lineArr[0]) == 3){
                    NLL += Math.log(Double.parseDouble(lineArr[6]))/Math.log(2);
                }
                else{
                    throw new Exception();
                }
            }
            
        }
        NLL /= folds;
        return NLL;
    }
    
    public static double [] shotAccuracies(String dataLocation, int folds) throws FileNotFoundException, IOException{
        double [] totalAccuracies = new double [4];
        
        for (int i = 0; i < folds; i++) {
            double [] accuracies = new double [4];
            double [] count = new double [4];
            File inputFile = new File(dataLocation + i + ".csv");
            FileReader reader = new FileReader(inputFile);
            BufferedReader br = new BufferedReader(reader);
            String lineString ;

            br.readLine();
            br.readLine();
            br.readLine();
            while((lineString = br.readLine()) != null){
                String [] lineArr = lineString.split(",");
                count[(int)Double.parseDouble(lineArr[0])]++;
                if(Double.parseDouble(lineArr[0]) == Double.parseDouble(lineArr[1])){
                    accuracies[(int)Double.parseDouble(lineArr[0])]++;
                }
            }
            for (int j = 0; j < accuracies.length; j++) {
                accuracies[j] /= count[j];
                totalAccuracies[j] += accuracies[j];
            }
            
        }
        for (int i = 0; i < totalAccuracies.length; i++) {
            totalAccuracies[i]/=folds;
        }
        return totalAccuracies;
    }
    
    public static double accuracy(String dataLocation, int folds) throws FileNotFoundException, IOException{
        double accuracy = 0;
        for (int i = 0; i < folds; i++) {
            File inputFile = new File(dataLocation + i + ".csv");
            FileReader reader = new FileReader(inputFile);
            BufferedReader br = new BufferedReader(reader);
            String lineString ;
            br.readLine();
            br.readLine();
            lineString = br.readLine();
            String [] lineArr = lineString.split(",");
            accuracy += Double.parseDouble(lineArr[0]);
        }
        accuracy /= folds;
        return accuracy;
    }
    
    
    
    public static double sportAccuracy(String dataLocation, int folds) throws FileNotFoundException, IOException{
        double balancedAccuracy = 0;
        for (int i = 0; i < folds; i++) {
            File inputFile = new File(dataLocation + i + ".csv");
            FileReader reader = new FileReader(inputFile);
            BufferedReader br = new BufferedReader(reader);
            String lineString ;
            br.readLine();
            br.readLine();
            lineString = br.readLine();
            String [] lineArr = lineString.split(",");
            balancedAccuracy += Double.parseDouble(lineArr[1]);
        }
        balancedAccuracy /= folds;
        return balancedAccuracy;
    }
    
    public static double balancedAccuracy(String dataLocation, int folds) throws FileNotFoundException, IOException{
        double balancedAccuracy = 0;
        for (int i = 0; i < folds; i++) {
            File inputFile = new File(dataLocation + i + ".csv");
            FileReader reader = new FileReader(inputFile);
            BufferedReader br = new BufferedReader(reader);
            String lineString ;
            br.readLine();
            br.readLine();
            lineString = br.readLine();
            String [] lineArr = lineString.split(",");
            balancedAccuracy += Double.parseDouble(lineArr[2]);
        }
        balancedAccuracy /= folds;
        return balancedAccuracy;
    }
    
    public static void writeTable( String [][] dataPaths, String fileName) {
        
        FileWriter fileWriter = null;
        final String NEW_LINE_SEPARATOR = "\n";
        final String COMMA_DELIMITER = ",";
        StringBuilder results = new StringBuilder();
        try {
                fileWriter = new FileWriter("/Users/phillipperks/Desktop/3rd-Year-Project/results/Tables/" +fileName+ ".csv");
                for (int i = 0; i < dataPaths.length; i++) {
                
                    double accuracy = accuracy(dataPaths[i][1],30);
                    double sportAccuracy = sportAccuracy(dataPaths[i][1],30);
                    double balancedAccuracy = balancedAccuracy(dataPaths[i][1],30);

                    results.append(dataPaths[i][0]).append(COMMA_DELIMITER).append(accuracy);
                    results.append(COMMA_DELIMITER).append(sportAccuracy);
                    results.append(COMMA_DELIMITER).append(balancedAccuracy);
                    results.append(NEW_LINE_SEPARATOR);
                }
                
                fileWriter.append(results);
                
                





                System.out.println("rotation file was created successfully !!!");

        } catch (Exception e) {
                System.out.println("Error in CsvFileWriter !!!");
                e.printStackTrace();
        } finally {

                try {
                        fileWriter.flush();
                        fileWriter.close();
                } catch (IOException e) {
                        System.out.println("Error while flushing/closing fileWriter !!!");
                        e.printStackTrace();
                }

        }
    }
    
    public static void writeNLLTable( String [][] dataPaths, String fileName) {
        
        FileWriter fileWriter = null;
        final String NEW_LINE_SEPARATOR = "\n";
        final String COMMA_DELIMITER = ",";
        StringBuilder results = new StringBuilder();
        try {
                fileWriter = new FileWriter("/Users/phillipperks/Desktop/3rd-Year-Project/FinalResults/Tables/" +fileName+ ".csv");
                for (int i = 0; i < dataPaths.length; i++) {
                
                    double NLL = NLL(dataPaths[i][1],30);
                    results.append(dataPaths[i][0]).append(COMMA_DELIMITER).append(NLL);
                    results.append(NEW_LINE_SEPARATOR);
                }
                fileWriter.append(results);
                System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
                System.out.println("Error in CsvFileWriter !!!");
                e.printStackTrace();
        } finally {

                try {
                        fileWriter.flush();
                        fileWriter.close();
                } catch (IOException e) {
                        System.out.println("Error while flushing/closing fileWriter !!!");
                        e.printStackTrace();
                }

        }
    }
    
    
    public static void shotAccuraciesTable( String [][] dataPaths, String fileName) {
        FileWriter fileWriter = null;
        final String NEW_LINE_SEPARATOR = "\n";
        final String COMMA_DELIMITER = ",";
        StringBuilder results = new StringBuilder();
        try {
                fileWriter = new FileWriter("/Users/phillipperks/Desktop/3rd-Year-Project/FinalResults/Tables/" +fileName+ ".csv");
                results.append(COMMA_DELIMITER).append("smash").append(COMMA_DELIMITER);
                results.append("clear").append(COMMA_DELIMITER);
                results.append("forehand").append(COMMA_DELIMITER);
                results.append("backand").append(NEW_LINE_SEPARATOR);
                for (int i = 0; i < dataPaths.length; i++) {
                
                    double [] shotAccuracies = shotAccuracies(dataPaths[i][1],30);
                    
                    results.append(dataPaths[i][0]).append(COMMA_DELIMITER);
                    results.append(shotAccuracies[0]).append(COMMA_DELIMITER);
                    results.append(shotAccuracies[1]).append(COMMA_DELIMITER);
                    results.append(shotAccuracies[2]).append(COMMA_DELIMITER);
                    results.append(shotAccuracies[3]).append(NEW_LINE_SEPARATOR);
                }
                fileWriter.append(results);
                System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
                System.out.println("Error in CsvFileWriter !!!");
                e.printStackTrace();
        } finally {

                try {
                        fileWriter.flush();
                        fileWriter.close();
                } catch (IOException e) {
                        System.out.println("Error while flushing/closing fileWriter !!!");
                        e.printStackTrace();
                }

        }
    }
}
