package uc.mei.is;

import java.util.List;

public class ClassTProto {

    List<com.proto.generated.Student> stlist;
    List<com.proto.generated.Teacher> tlist;

    public List<com.proto.generated.Student> getStList() {
        return this.stlist;
    }
    public List<com.proto.generated.Teacher> getTList() {
        return this.tlist;
    }

    public void setStList(List<com.proto.generated.Student> stlist) {this.stlist = stlist; }
    public void setTList(List<com.proto.generated.Teacher> tlist) {
        this.tlist = tlist;
    }


}