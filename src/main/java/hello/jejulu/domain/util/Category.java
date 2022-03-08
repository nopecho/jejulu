package hello.jejulu.domain.util;

import java.util.Arrays;

public enum Category {
    TOUR(1),
    RESTURENT(2),
    HOTEL(3);

    private int value;

    Category(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public static Category enumOf(int n){
        return Arrays.stream(Category.values())
                .filter(target -> target.getValue() == n)
                .findAny().orElse(null);
    }
}
