package a;

public class TaskA {
    private final Pot pot;

    public TaskA(Integer N, Integer n){
        this.pot = new Pot(N, 0);
        Bee[] beesThreads = new Bee[n];
        for (int i = 0; i<n; i++){
            beesThreads[i] = new Bee();
            beesThreads[i].setName("Bee " + (i+1));
            beesThreads[i].start();
        }
    }

    private class Pot{
        private final int volume;
        private int currentVolume;
        public Pot(int volume, int currentVolume){
            this.volume = volume;
            this.currentVolume = currentVolume;
        }
        public void PotEmpty(){
            currentVolume = 0;
        }
        public void fiilPot(){
            if(volume>currentVolume){
                currentVolume++;
            } else {
                throw new RuntimeException("currentVolume>volume");
            }
        }
    }

    private class Bear extends Thread{
        public Bear(){}

        @Override
        public void run(){
            while (Main.running){
                synchronized (pot){
                    if (pot.currentVolume >= pot.volume){
                        try{
                            pot.wait();
                        } catch (InterruptedException e){
                            throw new RuntimeException(e);
                        }
                    } else {
                        pot.fiilPot();
                        pot.notifyAll();
                        System.out.println("Bear ate all honey!");

                    }
                }
            }
        }
    }
    private class Bee extends Thread {
        Bee(){}
        @Override
        public void run(){
            while (Main.running){
                synchronized (pot){
                    if(pot.currentVolume >= pot.volume){
                        try {
                            pot.wait();
                        } catch (InterruptedException e){
                            throw new RuntimeException(e);
                        }
                    } else {
                        pot.fiilPot();
                        pot.notifyAll();
                        System.out.println("Bee #" + Thread.currentThread().getName() + " produced honey: " + pot.currentVolume);
                    }
                }
            }
        }
    }
}
