import com.google.common.hash.Hashing;

public class Main {
    public static void main(String[] args) {
        Long seedHash;
        System.out.println("Please enter a seed hash: ");
        seedHash = Long.parseLong(System.console().readLine());
        System.out.println("Seed hash: " + seedHash);
        System.out.println("Seed: " + Main.crackSeedByHash(seedHash));
    }

    public static long crackSeedByHash(long seedHash) {
        for (long i = 0; i < Long.MAX_VALUE; i++) {
            long hash = Main.hashSeed(i);
            System.out.println("Calculating Hash: +" + i + " -> " + hash);
            if (hash == seedHash) {
                return i;
            }
            long hashNeg = Main.hashSeed(-i);
            System.out.println("Calculating Hash: -" + i + " -> " + hash);
            if (hash == seedHash) {
                return -i;
            }
        }
        throw new RuntimeException("Seed not found!");
    }

    public static long hashSeed(long seed) {
        return Hashing.sha256().hashLong(seed).asLong();
    }
}
