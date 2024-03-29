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
    String telephone;
    String address;
    String birthdate;
    String registrationDate;
    String gender;
    
    public Student() {
    }

    public Student(String id, String name, String telephone, String address, String birthdate, String registrationDate, String gender) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.birthdate = birthdate;
        this.registrationDate = registrationDate;
        this.gender = gender;
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

    public String getRegistrationDate() {
        return this.registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getGender() {
		return this.gender; 
	}

    public void setGender(String gender) {
		this.gender = gender;
	}




}
