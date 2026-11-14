public class SynchronizedExample1 {

    public static void main(String[] args) {
        new PrintStringsThreadsync1("Hello ", "there.");
        new PrintStringsThreadsync1("How are ", "you?");
        new PrintStringsThreadsync1("Thank you ", "very much!");
    }

}