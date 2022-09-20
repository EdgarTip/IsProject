package uc.mei.is;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Class", namespace = "http://www.dei.uc.pt/EAI")
@XmlType(propOrder = {"name", "list"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassT {

  @XmlElement(name = "professor")
  List<Professor> list;

  public ClassT(){
    list = new ArrayList<Professor>();
  }

  public List<Professor> getList() {
      return this.list;
  }

  public void setList(List<Professor> list) {
      this.list = list;
  }

  public void addProfessor(Professor prof){
    list.add(prof);
  }

}