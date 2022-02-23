package utils;

public class CredentialConstant {

    private static String MYSQL_USER_NAME_PROD;
    private static String MYSQL_PASSWORD_PROD;

    private static String TEST_REPORT_SENDER_USERNAME;
    private static String TEST_REPORT_SENDER_PASSWORD;

    public static void set_test_report_username(String M_TEST_REPORT_SENDER_USERNAME) {
        TEST_REPORT_SENDER_USERNAME = M_TEST_REPORT_SENDER_USERNAME;
    }

    public static void set_test_report_password(String M_TEST_REPORT_SENDER_PASSWORD) {
        TEST_REPORT_SENDER_PASSWORD = M_TEST_REPORT_SENDER_PASSWORD;
    }

    public static String get_test_report_username() {
        return TEST_REPORT_SENDER_USERNAME;
    }

    public static String get_test_report_password() {
        return TEST_REPORT_SENDER_PASSWORD;
    }

    public static String get_mysql_user_name() {
        return MYSQL_USER_NAME_PROD;
    }

    public static void set_mysql_user_name(String M_MYSQL_USER_NAME) {
        MYSQL_USER_NAME_PROD = M_MYSQL_USER_NAME;
    }

    public static String get_mysql_password() {
        return MYSQL_PASSWORD_PROD;
    }

    public static void set_mysql_password(String M_MYSQL_PASSWORD) {
        MYSQL_PASSWORD_PROD = M_MYSQL_PASSWORD;
    }


}
