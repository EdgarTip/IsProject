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
public class Professor {

    @XmlAttribute
    String id;

    String name;
    String telephone;
    String address;
    String birthdate;

    public Professor() {}

    public Professor(String id, String name, String telephone, String address, String birthdate) {
        this.id = id;
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


}
