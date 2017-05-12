package kr.re.kitri.util;

/**
 * Created by danawacomputer on 2017-05-12.
 */
public interface PostgresConstants {

    String DRIVER_POSTGRES = "org.postgresql.Driver";
    String DB_URL = "jdbc:postgresql://localhost:5432/navermovieapi";
    String USERNAME = "postgres";
    String PASSWORD = "0409";

    String CLIENT_ID = "zYKOAUxRRPueChQC3b9b";   // App 클라이언트 아이디값
    String CLIENT_SECRET = "RoFsIfRyvz";         // App 클라이언트 시크릿값

    String TRANSLATE_QEURY =
            "selct * from items";

}
