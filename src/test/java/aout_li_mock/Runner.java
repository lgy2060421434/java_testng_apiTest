package aout_li_mock;

import com.github.dreamhead.moco.bootstrap.Main;

public class Runner {
    public static void main(String[] args) {
        test9();
    }
    public static void test9() {
        // http://localhost:12306/demo
        String[] args = "http -p 12306 -c src/test/resources/api.json".split(" ");
        Main.main(args);
    }
}
