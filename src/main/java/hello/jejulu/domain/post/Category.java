package hello.jejulu.domain.post;

public enum Category {
    Tour(1),
    Resturent(2),
    Hotel(3);

    private int value;

    Category(int value){
        this.value = value;
    }
}
