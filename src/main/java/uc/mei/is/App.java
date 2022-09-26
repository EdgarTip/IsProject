
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

            ArrayList<Long> xmlMarshal = new ArrayList<Long>();
            ArrayList<Long> xmlUnmarshal = new ArrayList<Long>();
            ArrayList<Long> xmlGZIPMarshal = new ArrayList<Long>();
            ArrayList<Long> xmlGZIPUnmarshal = new ArrayList<Long>();
            ArrayList<Long> protoSerialize = new ArrayList<Long>();
            ArrayList<Long> protoUnserialize = new ArrayList<Long>();





            Scanner sc =new Scanner(System.in);  

            System.out.println("Number professors:");
            int numberProfessors = sc.nextInt();
            System.out.println("Number students:");
            int numberStudents = sc.nextInt();
            System.out.println("Number of names per person: ");
            int numberNames = sc.nextInt();
            System.out.println("Simulation numbers: ");
            int simulationAmount = sc.nextInt();

            sc.close();
            


            for(int i = 0; i < simulationAmount; i++){
                //Create object
                TwoPair obj = createClassObject(numberProfessors, numberStudents, numberNames);

                ClassT clss = obj.getClss();
                ClassTProto tProto = obj.getTproto();

                jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] {ClassT.class}, null);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                //Serialize
                xmlMarshal.add(serializeXML("simplejaxb\\output\\xmlNoComp.xml", clss, jaxbMarshaller));

                //Unserialize
                File file = new File("simplejaxb\\output\\xmlNoComp.xml");    
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                xmlUnmarshal.add((unserializeXML(file, jaxbUnmarshaller)));
                
                //Serialize with Gzip compression
                xmlGZIPMarshal.add(serializeXMLGzip(clss, jaxbMarshaller, "simplejaxb\\output\\xmlNoComp.xml", "simplejaxb\\output\\xmlGzip.xml"));

                //Unserialize with Gzip compression
                xmlGZIPUnmarshal.add(unserializeXMLGzip("simplejaxb\\output\\xmlGzip.xml", "simplejaxb\\output\\xmlGzipRemade.xml"));

                //Serialize Proto
                protoSerialize.add(serializeProto("simplejaxb\\output\\classroom",tProto));
                
                //Unserialize Proto
                protoUnserialize.add(unserializeProto("simplejaxb\\output\\classroom"));
            
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd-HH-mm-ss");
            LocalDateTime now = LocalDateTime.now();

            String folderPath = Integer.toString(numberProfessors) + "-" + Integer.toString(numberStudents) + "-" + Integer.toString(numberNames) + "-" + Integer.toString(simulationAmount);
            new File("simplejaxb\\output\\results\\" + folderPath).mkdirs();
            writeFile("simplejaxb\\output\\results\\" + folderPath + "\\xmlMarshal.txt", xmlMarshal);
            writeFile("simplejaxb\\output\\results\\" + folderPath + "\\ xmlUnmarshal.txt", xmlUnmarshal);
            writeFile("simplejaxb\\output\\results\\" + folderPath + "\\xmlGZIPMarshal.txt", xmlGZIPMarshal);
            writeFile("simplejaxb\\output\\results\\" + folderPath + "\\xmlGZIPUnmarshal.txt", xmlGZIPUnmarshal);
            writeFile("simplejaxb\\output\\results\\" + folderPath + "\\protoSerialize.txt", protoSerialize);
            writeFile("simplejaxb\\output\\results\\" + folderPath + "\\protoUnserialize.txt", protoUnserialize);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeFile(String filePath, ArrayList<Long> list) throws IOException{
        FileWriter writer = new FileWriter(filePath); 
            for(Long str: list) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
    }
    //Serializes a XML file without compression
    private static long serializeXML(String filePath, ClassT clss, Marshaller jaxbMarshaller){
        long elapsedTime = 0;
        try {
            long start = System.currentTimeMillis();
            
            jaxbMarshaller.marshal(clss, new File(filePath));
            
            long end = System.currentTimeMillis();
            elapsedTime = end - start;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return elapsedTime;
    }

    //Unserializes a XML file. Result can be confirmed by removing the comment around the foreach loop
    private static long unserializeXML(File file,  Unmarshaller jaxbUnmarshaller){
        //Unserialize
        long elapsedTime = 0;
        try {
            long start = System.currentTimeMillis();
            // some time passes
            
            ClassT clss = (ClassT) jaxbUnmarshaller.unmarshal(file);
            long end = System.currentTimeMillis();
            elapsedTime = end - start;
            
            
            //Verifying if output is correct
            /* 
            for(Professor prof : clss.getList()){
                System.out.println("Stud name: " + stud.getName() + "|Id: " + stud.getAge());
            }*/

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return elapsedTime;
    }

    private static long serializeProto(String filePath, ClassTProto tproto){
        // Proto Marshalling
        //ClassTProto clss2 = AddProtoObjects();
        long elapsedTime = 0;
        Classrooms.Builder classrooms = Classrooms.newBuilder();

        for (Teacher t: tproto.tlist){
            classrooms.addTeachers(t);
        }

        FileOutputStream output;
        try {
            output = new FileOutputStream(filePath);

            long start = System.currentTimeMillis();
            classrooms.build().writeTo(output);
            long end = System.currentTimeMillis();
            elapsedTime = end - start;
            
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elapsedTime;
        //System.out.println(classrooms);
    }
    
    private static long unserializeProto(String filePath){
        // Proto Unmarshalling
        long elapsedTime = 0;
        Classrooms classr;
        try {
            long start = System.currentTimeMillis();
            classr = Classrooms.parseFrom(new FileInputStream(filePath));
            long end = System.currentTimeMillis();
            elapsedTime = end - start;

            //System.out.println("\n-------\n");
            //System.out.println(classr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elapsedTime;
        
    }
    //Serializes a file compressing it with Gzip
    private static long serializeXMLGzip(ClassT clss, Marshaller jaxbMarshaller, String filePath, String outputPath){
        byte[] buffer = new byte[1024];
        
        long elapsedTime = 0; 
        try {

            long start = System.currentTimeMillis();

            jaxbMarshaller.marshal(clss, new File(filePath));

            File file = new File(filePath);    

            FileOutputStream fileOutputStream =new FileOutputStream(outputPath);
 
            GZIPOutputStream gzipOuputStream = new GZIPOutputStream(fileOutputStream);
 
            FileInputStream fileInput = new FileInputStream(file);
 
            int bytes_read;
             
            while ((bytes_read = fileInput.read(buffer)) > 0) {
                gzipOuputStream.write(buffer, 0, bytes_read);
            }
            
            long end = System.currentTimeMillis();
            elapsedTime = end - start;
            fileInput.close();
 
            gzipOuputStream.finish();
            gzipOuputStream.close();
 
 
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return elapsedTime;
    }

    //Unserealize a file that was compressed using Gzip
    private static long unserializeXMLGzip(String filepath, String newFile){
        long elapsedTime = 0; 
        try {
            
            FileInputStream fis = new FileInputStream(filepath);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);

            byte[] buffer = new byte[1024];
            int len;
            long start = System.currentTimeMillis();
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }

            long end = System.currentTimeMillis();
            elapsedTime = end - start;

            
            fos.close();
            gis.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return elapsedTime;
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
    private static TwoPair createClassObject(int numberProfessors, int numberStudents, int numberNames) {
        
        //Get random information from text files
        Scanner s;
        ClassTProto tproto = new ClassTProto();
        ArrayList<String> firstNamesList = new ArrayList<String>();
        ArrayList<String> middleNamesList = new ArrayList<String>();
        ArrayList<String> addressesList = new ArrayList<String>();
        ArrayList<com.proto.generated.Student> studentList = new ArrayList<com.proto.generated.Student>();
        ArrayList<com.proto.generated.Teacher> teacherList = new ArrayList<com.proto.generated.Teacher>();
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
            
            String professorId = randomId();
            String professorTelephone = randomTelephoneNumber();
            String professorBirthDate = randomDate();
            Professor prof = createProfessor(professorName, address, professorId, professorTelephone, professorBirthDate);
            
            clss.addProfessor(prof);

            ArrayList<com.proto.generated.Student> students = new ArrayList<com.proto.generated.Student>();

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
                
                String id = randomId();
                String telephone = randomTelephoneNumber();
                String date = randomDate();
                String date2 = randomDate();
                Student stud = createStudent(studentName, studentAddress, "male", telephone, id, date, date2);
                
                com.proto.generated.Student s1 = com.proto.generated.Student.newBuilder()
                .setName(studentName)
                .setTelephone(telephone).setGender("male")
                .setBirthdate(date).setAddress(address).setRegistrationDate(date2)
                .build();

                students.add(s1);
                studentList.add(s1);

                prof.addStudent(stud);
            }

            com.proto.generated.Teacher t1 = Teacher.newBuilder()
                .setName(professorName)
                .setTelephone(professorTelephone)
                .setBirthdate(professorBirthDate)
                .setAddress(address)
                .addAllStudents(students).build();

            teacherList.add(t1);
        }

        tproto.setStList(studentList);
        tproto.setTList(teacherList);
        TwoPair obj = new TwoPair(clss, tproto);


        return obj;
    }

    //Creates a new student object
    public static Student createStudent(String name, String address, String gender, String telephone, String id, String date, String date2){
        return new Student(id, name, telephone, address, date, date2, gender);
    }

    //Creates a new professor object
    public static Professor createProfessor(String name, String address, String id, String telephone, String date){
        return new Professor(id, name, telephone, address, date);
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
