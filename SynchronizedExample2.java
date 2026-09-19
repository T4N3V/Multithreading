public class SynchronizedExample2 {

    public static void main(String[] args) {

        TwoStringssync2 ts = new TwoStringssync2();

        new PrintStringsThreadsync2("Hello ", "there.", ts);
        new PrintStringsThreadsync2("How are ", "you?", ts);
        new PrintStringsThreadsync2("Thank you ", "very much!", ts);
    }

}
