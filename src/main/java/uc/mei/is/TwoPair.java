package uc.mei.is;

public class TwoPair {
    ClassT clss;
    ClassTProto tproto;

    public ClassT getClss() {
        return this.clss;
    }

    public void setClss(ClassT clss) {
        this.clss = clss;
    }

    public ClassTProto getTproto() {
        return this.tproto;
    }

    public void setTproto(ClassTProto tproto) {
        this.tproto = tproto;
    }

    public TwoPair(ClassT clss, ClassTProto tproto){
        this.clss = clss;
        this.tproto = tproto;
    }

    
}
