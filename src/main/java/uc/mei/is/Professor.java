package uc.mei.is;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;



// Java 8?
//import com.sun.xml.internal.txw2.annotation.XmlCDATA;
// jaxb 2
//import javax.xml.bind.annotation.*;



@XmlType(propOrder = {"id","name","telephone","address","birthdate", "list"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Professor {

    @XmlAttribute
    String id;

    @XmlElement(name = "student")
    List<Student> list;

    String name;
    String telephone;
    String address;
    String birthdate;

    

    public Professor() {}

    public Professor(String id, String name, String telephone, String address, String birthdate) {
        list = new ArrayList<Student>();
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.birthdate = birthdate;
    }

    public Professor(String name, String telephone, String address, String birthdate) {
        list = new ArrayList<Student>();
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.birthdate = birthdate;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void addStudent(Student student) {
        list.add(student);
    }

    public List<Student> getList() {
        return list;
    }

    public void setList(List<Student> list) {
        this.list = list;
    }

}
