
package uc.mei.is;




// @Since 3.0.0, rebrand to jakarta.xml
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

// old APIs 2.3.*,
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileInputStream;
//import javax.xml.bind.*;
//import javax.xml.stream.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.GZIPOutputStream;

public class App {

    public static void main(String[] args) {

        JAXBContext jaxbContext = null;
        try {

            //jaxbContext = JAXBContext.newInstance(Company.class);

            // EclipseLink MOXy needs jaxb.properties at the same package with Company.class or Staff.class
            // Alternative, I prefer define this via eclipse JAXBContextFactory manually.
            jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] {ClassT.class}, null);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();


            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(createClassObject(), new File("simplejaxb\\output\\fruit.xml"));

            


            ClassT clss = createClassObject();
            jaxbMarshaller.marshal(clss, System.out);

            // XML Unmarshalling
            File file = new File("simplejaxb\\output\\fruit.xml");    
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ClassT o = (ClassT) jaxbUnmarshaller.unmarshal(file);
            System.out.println(o);

            byte[] buffer = new byte[1024];
 
        try {
             
            FileOutputStream fileOutputStream =new FileOutputStream("simplejaxb\\output\\fruitZip");
 
            GZIPOutputStream gzipOuputStream = new GZIPOutputStream(fileOutputStream);
 
            FileInputStream fileInput = new FileInputStream("simplejaxb\\output\\fruit.xml");
 
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
    

            

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    private static ClassT createClassObject() {

        ClassT clss = new ClassT();

        Student s1 = new Student();
        s1.setName("Alberto");
        s1.setAge(21);
        s1.setId("201134441110");

        Student s2 = new Student();
        s2.setName("Patricia");
        s2.setAge(22);
        s2.setId("201134441116");

        Student s3 = new Student();
        s3.setName("Luis");
        s3.setAge(21);
        s3.setId("201134441210");

        Student s4 = new Student();
        s4.setName("Luis");
        s4.setAge(21);
        s4.setId("201134441210");
       

        clss.setList(Arrays.asList(s1, s2, s3, s4));

        return clss;
    }
}