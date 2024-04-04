import com.google.common.hash.Hashing;
import java.lang.Thread;
import java.lang.Exception;

public class Main {
    public static long shared_nextSeed = -1;
    public static boolean shared_isFound = false;

    public static void main(String[] args) {
        Long seedHash;
        System.out.println("Please enter a seed hash: ");
        seedHash = Long.parseLong(System.console().readLine());

        System.out.println("Starting from ABS: (default: 0)");
        String startFromStr = System.console().readLine();
        if (startFromStr.equals("")) {
            startFromStr = "0";
        }
        Main.shared_nextSeed = Long.parseLong(startFromStr);

        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                try {
                    long seed = Main.crackSeedByHash(seedHash);
                    Main.shared_isFound = true;
                    System.out.println("Seed: " + seed);
                } catch (Exception e) {}
            }).start();
        }
    }

    public static long crackSeedByHash(long seedHash) {
        while (!Main.shared_isFound) {
            long seed = Main.shared_nextSeed++;
            long hash = Main.hashSeed(seed);
            System.out.println("Calculating Hash: +" + seed + " -> " + hash);
            if (hash == seedHash) {
                return seed;
            }
            long hashNeg = Main.hashSeed(-seed);
            System.out.println("Calculating Hash: -" + seed + " -> " + hash);
            if (hash == seedHash) {
                return -seed;
            }
        }
        throw new RuntimeException("Seed not found!");
    }

    public static long hashSeed(long seed) {
        return Hashing.sha256().hashLong(seed).asLong();
    }
}
