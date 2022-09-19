package uc.mei.is;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;



// Java 8?
//import com.sun.xml.internal.txw2.annotation.XmlCDATA;
// jaxb 2
//import javax.xml.bind.annotation.*;




@XmlAccessorType(XmlAccessType.FIELD)
public class Student {

    @XmlAttribute
    String id;

    String name;
    int age;

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
