
package uc.mei.is;




// @Since 3.0.0, rebrand to jakarta.xml

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.proto.generated.Classrooms;
import com.proto.generated.Teacher;

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

    private static void serializeProto(String filePath){
        // Proto Marshalling
        ClassTProto clss2 = AddProtoObjects();
        Classrooms.Builder classrooms = Classrooms.newBuilder();

        for (Teacher t: clss2.tlist){
            classrooms.addTeachers(t);
        }

        FileOutputStream output;
        try {
            output = new FileOutputStream("output\\classroom");
            classrooms.build().writeTo(output);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(classrooms);
    }
    
    private static void unserializeProto(String filePath){
        // Proto Unmarshalling
        Classrooms classr;
        try {
            classr = Classrooms.parseFrom(new FileInputStream("output\\classroom"));
            //System.out.println("\n-------\n");
            //System.out.println(classr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
    private static ClassT createClassObject(int numberProfessors, int numberStudents, int numberNames) {
        
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

            s = new Scanner(new File("simplejaxb\\src\\main\\java\\uc\\mei\\is\\files\\addresses.txt"));

            while (s.hasNext()){
                addressesList.add(s.next());
            }

            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        ClassT clss = new ClassT();

        for(int i = 0; i < numberProfessors; i++){

            String professorName = "";

            Random random = new Random();
            int randomIndex = random.nextInt(firstNamesList.size());
            professorName += firstNamesList.get(randomIndex);
            

            for(int k = 0; k < numberNames - 1; k++){
                int randomIndex2 = random.nextInt(middleNamesList.size());
                professorName += " " + middleNamesList.get(randomIndex2);
            }

            String address = "";
            int randomIndex3 = random.nextInt(addressesList.size());
            address += addressesList.get(randomIndex3);
            
            Professor prof = createProfessor(professorName, address);
            
            clss.addProfessor(prof);

            for(int j = 0; j < numberStudents; j++){
                String studentName = "";

                int randomIndex4 = random.nextInt(firstNamesList.size());
                studentName += firstNamesList.get(randomIndex4);
    
                for(int k = 0; k < numberNames; k++){
                    int randomIndex5 = random.nextInt(middleNamesList.size());
                    studentName += " " + middleNamesList.get(randomIndex5);
                }
    
                String studentAddress = "";
                int randomIndex6 = random.nextInt(addressesList.size());
                studentAddress += addressesList.get(randomIndex6);
                
                Student stud = createStudent(studentName, studentAddress, "male");

                prof.addStudent(stud);
            }
        }
        


        return clss;
    }

    private static ClassTProto AddProtoObjects() {

        com.proto.generated.Student s1 = com.proto.generated.Student.newBuilder()
                .setName("Alberto")
                .setTelephone(999999999).setGender("male")
                .setBirthdate("22/22/2222").setAddress("bbb")
                .build();

        com.proto.generated.Student s2 = com.proto.generated.Student.newBuilder()
                .setName("Patricia")
                .setTelephone(888888888).setGender("female")
                .setBirthdate("33/33/3333").setAddress("ccc")
                .build();

        com.proto.generated.Student s3 = com.proto.generated.Student.newBuilder()
                .setName("Luis")
                .setTelephone(777777777).setGender("male")
                .setBirthdate("44/44/4444").setAddress("ddd")
                .build();

        com.proto.generated.Teacher t1 = Teacher.newBuilder()
                .setName("Ricardo")
                .setTelephone(111111111)
                .setBirthdate("11/11/1111")
                .setAddress("aaa")
                .addStudents(s1).addStudents(s2).addStudents(s3)
                .build();

        ClassTProto tproto = new ClassTProto();
        tproto.setStList(Arrays.asList(s1, s2, s3));
        tproto.setTList(Arrays.asList(t1));

        return tproto;
    }

    //Creates a new student object
    public static Student createStudent(String name, String address, String gender){
        return new Student(randomId(), name, randomTelephoneNumber(), address, randomDate(), randomDate(), gender);
    }

    //Creates a new professor object
    public static Professor createProfessor(String name, String address){
        return new Professor(randomId(), name, randomTelephoneNumber(), address, randomDate());
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

    //Creates random id for student
    public static String randomId(){
        int p1 = randBetween(100000,999999);
        int p2 = randBetween(100000,999999);
        
        return String.valueOf(p1) + String.valueOf(p2);
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
