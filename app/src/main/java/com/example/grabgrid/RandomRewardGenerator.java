package com.example.grabgrid;

import java.security.SecureRandom;

public class RandomRewardGenerator {
    private static int rewardPointValue = 100;
    private static final SecureRandom random = new SecureRandom();

    public static <T extends Enum<?>> T randomReward(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static int rewardPoints() {
        return random.nextInt(rewardPointValue);
    }
}
