package hello.jejulu.domain.util;

import java.util.Arrays;

public enum Role {
    ADMIN(0),
    MEMBER(1),
    HOST(2);

    private int value;

    Role(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public static Role enumOf(int n){
        return Arrays.stream(Role.values())
                .filter(target -> target.getValue() == n)
                .findAny().orElse(null);
    }
}
