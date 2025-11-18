package Phaser;

import java.util.concurrent.Phaser;

public class PhaserExample {
    public static void main(String[] args) throws InterruptedException {
        // Custom Phaser with onAdvance method
        Phaser phaser = new Phaser() {
            // This runs at the end of each phase
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("\n--- Phase " + phase + " completed. Registered parties: " + registeredParties + " ---\n");
                // return true to terminate phaser, false to continue
                return registeredParties == 0;
            }
        };

        // Initial 3 players
        for (int i = 0; i < 3; i++) {
            phaser.register();
            new Thread(new Player("Player" + i, phaser)).start();
        }

        // Simulate a late joiner
        Thread.sleep(2000);
        System.out.println("⚡ Late joiner Player3 joining at phase " + phaser.getPhase());
        phaser.register();
        new Thread(new Player("Player3", phaser)).start();
    }
}

class Player implements Runnable {
    private String playerName;
    private Phaser phaser;

    public Player(String playerName, Phaser phaser) {
        this.playerName = playerName;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        try {
            System.out.println(playerName + " started running.");
            Thread.sleep((long)(Math.random() * 2000));

            // Phase 0
            System.out.println(playerName + " completed Phase 0, waiting for others...");
            phaser.arriveAndAwaitAdvance();

            // Phase 1
            System.out.println(playerName + " starts Phase 1.");
            Thread.sleep((long)(Math.random() * 2000));

            // Example: Player2 deregisters early
            if (playerName.equals("Player2")) {
                System.out.println(playerName + " deregistering early at Phase " + phaser.getPhase());
                phaser.arriveAndDeregister();
                return;
            }

            System.out.println(playerName + " completed Phase 1, waiting for others...");
            phaser.arriveAndAwaitAdvance();

            // Phase 2
            System.out.println(playerName + " starts Phase 2.");
            Thread.sleep((long)(Math.random() * 2000));
            System.out.println(playerName + " completed Phase 2 and deregisters.");
            phaser.arriveAndDeregister();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


