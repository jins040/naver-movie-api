package kr.re.kitri.service;

import kr.re.kitri.model.Item;
import kr.re.kitri.util.PostgresConstants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danawacomputer on 2017-05-12.
 */
public class MovieSearchService {

    // 드라이버 로딩용 생성자
    public MovieSearchService() {
        try {
            Class.forName(PostgresConstants.DRIVER_POSTGRES);
            System.out.println("Driver loading ok..");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // DB 데이터를 List로 변환
    public List<Item> getSearchList() {

        List<Item> list = new ArrayList<>();

        Connection conn = this.getConnection();

        if (conn != null) {

            try {

                // items DB 데이터를 리스트로 변환
                PreparedStatement pstmtTrans = conn.prepareStatement(PostgresConstants.TRANSLATE_QEURY);
                ResultSet rsTrans = pstmtTrans.executeQuery();

                TranslateItemsToList(rsTrans, list);

                conn.close();
                System.out.println("Connection closed..");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Connection error");
        }

        return list;
    }

    private void TranslateItemsToList(ResultSet rsTrans, List<Item> list) throws SQLException {

        while (rsTrans.next()) {

            list.add(new Item(
                    rsTrans.getInt(1),
                    rsTrans.getString(2),
                    rsTrans.getString(3),
                    rsTrans.getString(4),
                    rsTrans.getString(5),
                    rsTrans.getTimestamp(6).toLocalDateTime().toLocalDate(),
                    rsTrans.getString(7),
                    rsTrans.getString(8),
                    rsTrans.getDouble(9),
                    rsTrans.getInt(10)
            ));

        }
    }

    // JSON 값을 읽어온 값을 DB에..
    public void InputDataToDB(String searchWord) {

        String json = ReturnKeywordAsJson(searchWord);

        JSONObject jsonObj = new JSONObject(json);

        JSONArray jsonArr = jsonObj.getJSONArray("items");

        Connection conn = this.getConnection();

        String searchQuery = "";
        String itemsQuery = "";

        if(conn == null) {
            System.out.println("커넥션 연결 실패, 프로그램 종료합니다.");
            return;
        }

        try {

            // search data DB에 INPUT

            for (int i=0 ; i<10 ; i++) {
                searchQuery =
                        "INSERT INTO searching " +
                                "VALUES (" +
                                (i+1) + ", '" +
                                jsonObj.getString("lastBuildDate") + "', " +
                                jsonObj.getInt("total") + ", '" +
                                searchWord + "'" +
                                ");";

                PreparedStatement pstmtSearch = conn.prepareStatement(searchQuery);
                pstmtSearch.executeUpdate();
            }

            // items data를 DB에 INPUT
            for (int i=0 ; i<jsonArr.length() ; i++) {

                itemsQuery =
                        "INSERT INTO items " +
                                "VALUES (" +
                                (i+1) + ", '" +
                                jsonArr.getJSONObject(i).getString("title") + "', '" +
                                jsonArr.getJSONObject(i).getString("link") + "', '" +
                                jsonArr.getJSONObject(i).getString("image") + "', '" +
                                jsonArr.getJSONObject(i).getString("subtitle") + "', '" +
                                //Date.valueOf(jsonArr.getJSONObject(i).get("pubDate") + "-" + 01 + "-" + 01) + "', '" +
                                LocalDate.of(jsonArr.getJSONObject(i).getInt("pubDate"), 1, 1) + "', '" +
                                jsonArr.getJSONObject(i).getString("director") + "', '" +
                                jsonArr.getJSONObject(i).getString("actor") + "', '" +
                                jsonArr.getJSONObject(i).getDouble("userRating") + "', " +
                                (int)(Math.random()*9+1) +
                                ");";

                PreparedStatement pstmtItems = conn.prepareStatement(itemsQuery);
                pstmtItems.executeUpdate();
            }


            conn.close();
            System.out.println("Connection closed..");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // JSON 불러오기
    private String ReturnKeywordAsJson(String searchWord) {

        try {
            String text = URLEncoder.encode(searchWord, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text + "&display=10";

            URL url = new URL(apiURL);

            HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();

            // header
            urlcon.setRequestMethod("GET");
            urlcon.setRequestProperty("X-Naver-Client-Id", PostgresConstants.CLIENT_ID);
            urlcon.setRequestProperty("X-Naver-Client-Secret", PostgresConstants.CLIENT_SECRET);

            int responseCode = urlcon.getResponseCode();   // Gets the status code from an HTTP response message.

            // Body
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
            } else {    // 에러 발생
                br = new BufferedReader(new InputStreamReader(urlcon.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            return response.toString();


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return "";
        }

    }

    // Connection 수행
    private Connection getConnection() {

        try {
            Connection conn = DriverManager.getConnection(
                    PostgresConstants.DB_URL, PostgresConstants.USERNAME, PostgresConstants.PASSWORD
            );
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
