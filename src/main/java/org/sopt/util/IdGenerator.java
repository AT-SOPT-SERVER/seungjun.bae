package org.sopt.util;

//선택과제 2 - id생성 기능 유틸 클래스로 분리
//선택과제 5
//txt파일과 연동하니까 다시 시작했을 때 id가 다시 0부터 시작하는 이슈 발생
//txt파일에서 가장 높은 아이디 불러와서 초깃값으로 사용해야 할듯
//getter랑 setter 추가해서 service에 id 생성 로직 추가
public class IdGenerator {
    private static Integer id;

    private IdGenerator() {
        throw new UnsupportedOperationException("유틸 클래스는 인스턴스화할 수 없습니다.");
    }

    public static int newId(){
        return ++id;
    }

    public static void setId(int id) {
        IdGenerator.id = id;
    }

    public static Integer getId() {
        return id;
    }
}
