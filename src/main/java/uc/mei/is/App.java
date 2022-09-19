
package uc.mei.is;




import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class App {

    public static void main(String[] args) {

        JAXBContext jaxbContext = null;
        try {

            Scanner sc =new Scanner(System.in);  

            System.out.println("Number professors:");
            int numberProfessors = sc.nextInt();
            System.out.println("Number students:");
            int numberStudents = sc.nextInt();
            System.out.println("Number of names per person: ");
            int numberNames = sc.nextInt();

            sc.close();
            
            //Create object
            ClassT clss = createClassObject(numberProfessors, numberStudents, numberNames);

            jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] {ClassT.class}, null);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //Serialize
            serializeXML("simplejaxb\\output\\xmlNoComp.xml", clss, jaxbMarshaller);

            //Decerialize
            File file = new File("simplejaxb\\output\\xmlNoComp.xml");    
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            unserializeXML(file, jaxbUnmarshaller);
            
            //Serialize with Gzip compression
            serializeXMLGzip(file, "simplejaxb\\output\\xmlGzip.xml");

            //Unserialize with Gzip compression
            unserializeXMLGzip("simplejaxb\\output\\xmlGzip.xml", "simplejaxb\\output\\xmlGzipRemade.xml");
            
            

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    //Serializes a XML file without compression
    private static void serializeXML(String filePath, ClassT clss, Marshaller jaxbMarshaller){
        try {
            jaxbMarshaller.marshal(clss, new File(filePath));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    //Unserializes a XML file. Result can be confirmed by removing the comment around the foreach loop
    private static void unserializeXML(File file,  Unmarshaller jaxbUnmarshaller){
        //Unserialize
        try {
            ClassT clss = (ClassT) jaxbUnmarshaller.unmarshal(file);
            
            //Verifying if output is correct
            /* 
            for(Professor prof : clss.getList()){
                System.out.println("Stud name: " + stud.getName() + "|Id: " + stud.getAge());
            }*/

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    //Serializes a file compressing it with Gzip
    private static void serializeXMLGzip(File file, String filePath){
        byte[] buffer = new byte[1024];
 
        try {
             
            FileOutputStream fileOutputStream =new FileOutputStream(filePath);
 
            GZIPOutputStream gzipOuputStream = new GZIPOutputStream(fileOutputStream);
 
            FileInputStream fileInput = new FileInputStream(file);
 
            int bytes_read;
             
            while ((bytes_read = fileInput.read(buffer)) > 0) {
                gzipOuputStream.write(buffer, 0, bytes_read);
            }
 
            fileInput.close();
 
            gzipOuputStream.finish();
            gzipOuputStream.close();
 
 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //Unserealize a file that was compressed using Gzip
    private static void unserializeXMLGzip(String filepath, String newFile){
        try {
            FileInputStream fis = new FileInputStream(filepath);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);

            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }

            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /*Populate an XML with data. The structure is the following:

        <class>
            <professor 1>
                <student 1></student 1>
                <student 2></student 2>
                ...
            </professor 1>
            <professor 2>
                <student 200></student 200>
                <student 2></student 2>
                ...
            ...
        </class>
    
    */
    private static ClassT createClassObject(int NumberProfessors, int NumberStudents, int NumberNames) {
        
        //Get random information from text files
        Scanner s;
        ArrayList<String> firstNamesList = new ArrayList<String>();
        ArrayList<String> middleNamesList = new ArrayList<String>();
        ArrayList<String> addressesList = new ArrayList<String>();
        try {
            s = new Scanner(new File("simplejaxb\\src\\main\\java\\uc\\mei\\is\\files\\firstNames.txt"));

            while (s.hasNext()){
                firstNamesList.add(s.next());
            }

            s = new Scanner(new File("simplejaxb\\src\\main\\java\\uc\\mei\\is\\files\\middleNames.txt"));

            while (s.hasNext()){
                middleNamesList.add(s.next());
            }

            s = new Scanner(new File("simplejaxb\\src\\main\\java\\uc\\mei\\is\files\\addresses.txt"));

            while (s.hasNext()){
                addressesList.add(s.next());
            }

            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        


        ClassT clss = new ClassT();


        return clss;
    }






    //Creates a random birthdate
    public static String randomDate() {
        int year = randBetween(1900, 2010);

        int day = randBetween(1, 29);

        int month = randBetween(1, 12);

        return String.valueOf(day) + "-" + String.valueOf(month) + "-" + String.valueOf(year);
    }

    //Creates a random phone number
    public static String randomTelephoneNumber(){
        int p1 = randBetween(900,999);
        int p2 = randBetween(100000,999999);

        return String.valueOf(p1) + String.valueOf(p2);
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
