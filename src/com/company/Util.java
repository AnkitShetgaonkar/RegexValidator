package com.company;

/**
 * @author Ankit
 */
public class Util {

    static String[] nameTestCases = {"Bruce Schneier",
            "Schneier, Bruce",
            "Schneier, Bruce Wayne",
            "O'Malley, John F.",
            "John O'Malley-Smith",
            "Cher",
            "Ron O''Henry",
            "Ron O'Henry-Smith-Barnes",
            "L33t Hacker",
            "<Script > alert(\"XSS\") </Script >",
            "Brad Everett Samuel Smith",
            "select * from users;"};

    static String[] numberTestCases = {"123-1234",
            "+1(703)111-2121",
            "32 (21) 212-2324",
            "1(703)123-1234",
            "011 701 111 1234",
            "12345.12345",
            "011 1 703 111 1234",
            "Incorrect",
            "(001) 123-1234",
            "123",
            "1/703/123/1234",
            "Nr 102-123-1234",
            "<script>alert(\"XSS\")</script>",
            "7031111234",
            "+1234 (201) 123-1234",
            "(001) 123-1234",
            "+01 (703) 123-1234",
            "(703) 123-1234 ext 204"};

    public static String[] getNameTestCases() {
        return nameTestCases;
    }

    public static String[] getNumberTestCases() {
        return numberTestCases;
    }
}
