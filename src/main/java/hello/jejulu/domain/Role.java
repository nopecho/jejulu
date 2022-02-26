package hello.jejulu.domain;

public enum Role {
    ADMIN(0),
    MEMBER(1),
    HOST(2);

    private int value;

    Role(int value){
        this.value = value;
    }
}
