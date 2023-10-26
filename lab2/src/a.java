import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class a {
    private static final Random random = new Random();
    private  boolean[][] forest;
    private final AtomicBoolean founded;
    private final AtomicInteger curRow;

    private final Integer forestSize;
    private final Integer beesCount;

    private class BeeSwarm extends Thread {
        public BeeSwarm() {
        }

        @Override
        public void run() {
            while (!founded.get() && curRow.get() < forestSize) {
                checkRow(curRow.get());
                curRow.set(curRow.get() + 1);
            }
        }
    }
    public a(Integer forestSize,Integer beesCount){
        this.forestSize = forestSize;
        this.beesCount = beesCount;
        forest = createForest();
        founded = new AtomicBoolean(false);
        curRow = new AtomicInteger(0);
    }
    private boolean[][] createForest(){
        forest = new boolean[forestSize][forestSize];
        int winnieRow = random.nextInt(forestSize);
        int winnieCol = random.nextInt(forestSize);
        forest[winnieRow][winnieCol] = true;
        System.out.println("Winnie in: [" + winnieRow +"][" + winnieCol + "]");
        return forest;
    }
    private void checkRow(int row) {
        if (founded.get()) {
            return;
        }
        System.out.println(Thread.currentThread().getName() + " bee swarm  in row " + row);
        for (int i = 0; i < forestSize; i++) {
            if (forest[row][i]) {
                System.out.println(Thread.currentThread().getName() + " Winnie was founded in row " + row);
                founded.set(true);
                break;
            }
        }
    }
    public void startSearch() {
        BeeSwarm[] beesThreads = new BeeSwarm[beesCount];
        for (int i = 0; i < beesCount; i++) {
            beesThreads[i] = new BeeSwarm();
            beesThreads[i].setName("swarm " + (i + 1));
            beesThreads[i].start();
        }
        for (int i = 0; i < beesCount; i++) {
            try {
                beesThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        a x = new a(15,12);
        x.startSearch();
    }
}
