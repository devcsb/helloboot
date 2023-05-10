package tobyspring.helloboot;

public interface HelloRepository {
    Hello findHello(String name);

    void increaseCount(String name);

    // default method를 활용하면, 인터페이스를 구현하는 클래스가 구현할 메소드의 개수가 줄어들고, 재사용해서 로직 구성이 편하므로, 유용하게 사용할 수 있다.
    // JAVA의 Comparator 인터페이스를 보면, default method 사용법을 학습할 수 있다.
    default int countOf(String name) {
        Hello hello = findHello(name);
        return hello == null ? 0 : hello.getCount();
    }
}
