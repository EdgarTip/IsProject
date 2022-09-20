
package uc.mei.is;




// @Since 3.0.0, rebrand to jakarta.xml
import com.proto.generated.Teacher;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

// old APIs 2.3.*,
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
import java.io.File;
//import javax.xml.bind.*;
//import javax.xml.stream.*;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
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

            jaxbMarshaller.marshal(createClassObject(), new File("output\\fruit.xml"));

            ClassT clss = createClassObject();
            jaxbMarshaller.marshal(clss, System.out);

            /*/
            try{
                FileOutputStream outputStream = new FileOutputStream(new File("/home/user/hellow.xml.gz"));
                GZIPOutputStream outputStreamZip = new GZIPOutputStream(outputStream);
            }
            catch(Exception e){
                return;
            }*/

            // XML Unmarshalling
            File file = new File("output\\fruit.xml");    
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ClassT o = (ClassT) jaxbUnmarshaller.unmarshal(file);
            System.out.println(o);

            // Proto Marshalling

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
       

        clss.setList(Arrays.asList(s1, s2, s3));

        return clss;
    }

    private static class AddProtoObjects {

        com.proto.generated.Teacher t1 = Teacher.newBuilder()
                .setName("Ricardo")
                .setTelephone(111111111)
                .setBirthdate("11/11/1111")
                .setAddress("aaa")
                .build();

        com.proto.generated.Student s1 = com.proto.generated.Student.newBuilder()
                .setName("Alberto")
                .setTelephone(999999999).setGender("male")
                .setBirthdate("22/22/2222").setAddress("bbb")
                .setTeacher(t1).build();

        com.proto.generated.Student s2 = com.proto.generated.Student.newBuilder()
                .setName("Patricia")
                .setTelephone(888888888).setGender("female")
                .setBirthdate("33/33/3333").setAddress("ccc")
                .setTeacher(t1).build();

        com.proto.generated.Student s3 = com.proto.generated.Student.newBuilder()
                .setName("Luis")
                .setTelephone(777777777).setGender("male")
                .setBirthdate("44/44/4444").setAddress("ddd")
                .setTeacher(t1).build();

    }
}