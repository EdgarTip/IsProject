package uc.mei.is;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "Class", namespace = "http://www.dei.uc.pt/EAI")
@XmlType(propOrder = {"name", "list"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassT {

  @XmlElement(name = "student")
  List<Student> list;



  public List<Student> getList() {
      return this.list;
  }

  public void setList(List<Student> list) {
      this.list = list;
  }


}