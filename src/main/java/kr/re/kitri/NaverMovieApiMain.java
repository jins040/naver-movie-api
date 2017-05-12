package kr.re.kitri;

import kr.re.kitri.model.Item;
import kr.re.kitri.service.MovieSearchService;

import java.util.List;
import java.util.Scanner;

/**
 * Created by danawacomputer on 2017-05-12.
 */
public class NaverMovieApiMain {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        while (true) {

            // 사용자로부터 검색어를 받는다.
            // 검색어로 네이버 API 호출하여 JSON 스트링을 확보
            // JSON 스트링을 자바 컬렉션으로 옮겨 담는다.(service에서 lastBuildDate, total, ItemList 3개 생성) ex) Item.setTitle();, searchID는 안넣으면 0으로 자동 초기화
            // 자바 컬렉션의 내용을 데이터베이스에 인서트한다.
            //
            // **코딩 플로우를 짤 때 테스트도 고려해야 한다. (하나씩 할 때마다 테스트 가능, 나머지는 //todo ~~

            System.out.print("검색어를 입력(종료는 Quit) : ");
            String searchWord = in.nextLine();

            if (searchWord.equals("Quit")) {

                System.exit(1);

            } else {

                MovieSearchService service = new MovieSearchService();

                service.InputDataToDB(searchWord);

                List<Item> list = service.getSearchList();

                if (list.size() > 0) {

                    list.forEach(System.out::println);

                    System.out.printf("%d개의 결과가 정상적으로 조회되었습니다.\n", list.size());
                    System.out.println("DB insert completed..");

                } else {
                    System.out.println("Data가 없습니다..");
                }
            }
        }

    }
}
