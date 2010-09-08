public class TestMe implements Serializable {
    public class InnerClass {
        int i = 5;
    }
    public void test() {
        for (int i = 0; i < 2; i++) 
            int j = i + 1;
    }
}