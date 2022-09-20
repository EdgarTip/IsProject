package uc.mei.is;

import java.util.List;
import com.proto.generated.Teacher;
import com.proto.generated.Student;

public class ClassTProto {

    List<Student> stlist;
    List<Teacher> tlist;

    public List<com.proto.generated.Student> getStList() {
        return this.stlist;
    }
    public List<Teacher> getTList() {
        return this.tlist;
    }

    public void setStList(List<com.proto.generated.Student> stlist) {this.stlist = stlist; }
    public void setTList(List<Teacher> tlist) {
        this.tlist = tlist;
    }


}