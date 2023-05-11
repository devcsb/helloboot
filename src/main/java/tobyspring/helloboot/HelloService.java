package tobyspring.helloboot;

public interface HelloService {
    String sayHello(String name);

    // (꼼수)테스트코드에서 람다로 작성했던 코드를 수정하지 않기 위해, 새로 추가한 메서드를 default 메서드로 만들고, 구현하지 않았으면 0을 리턴하게끔 만든다.
    default int countOf(String name) {
        return 0;
    }
}
