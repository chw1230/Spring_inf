package hello.core.singleton;

public class StatefulService {

    private int price; // 상태를 유지하는 필드

    public void order(String name, int price) {
        System.out.println("name: " + name + ", price: " + price);
        this.price = price; // 이부분이 문제에 해당이 된다!! -> 값을 바꾸도록 설계하면 안됨!
    }

    // 읽기 기능
    public int getPrice() {
        return price;
    }
}
