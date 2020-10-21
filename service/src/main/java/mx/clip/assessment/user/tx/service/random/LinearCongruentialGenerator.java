package mx.clip.assessment.user.tx.service.random;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LinearCongruentialGenerator implements PseudoRandomGenerator {

    private int seed;

    private static long MULTIPLIER = 93826297;

    @Override
    public int nextInt() {


        int nextValue = (int)((MULTIPLIER*seed + 1) % Integer.MAX_VALUE);

        seed = nextValue;

        return nextValue;
    }
}
