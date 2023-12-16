package a;

public class Data {
    public static class Record {
        public String name;
        public String phone;

        public Record(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }
    }

    public static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}