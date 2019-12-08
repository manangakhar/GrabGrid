package com.example.grabgrid.Entities;

import com.example.grabgrid.Enums.RewardType;
import com.example.grabgrid.RandomRewardGenerator;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Reward {
    RewardType rewardType;
    int rewardPoints;
    static int minimumRewardPoints = 0;

    public static Reward createReward() {
        RewardType rewardType = RandomRewardGenerator.randomReward(RewardType.class);

        switch (rewardType) {
            case TRANSPORT:
                return Reward.builder().rewardType(rewardType)
                        .rewardPoints(RandomRewardGenerator.rewardPoints()).build();
            case PAY:
                return Reward.builder().rewardType(rewardType)
                        .rewardPoints(RandomRewardGenerator.rewardPoints()).build();
            case MOVIE:
                return Reward.builder().rewardType(rewardType)
                        .rewardPoints(RandomRewardGenerator.rewardPoints()).build();
            case FOOD:
                return Reward.builder().rewardType(rewardType)
                        .rewardPoints(RandomRewardGenerator.rewardPoints()).build();
            default:
                return Reward.builder().rewardType(RewardType.NONE)
                        .rewardPoints(minimumRewardPoints).build();
        }
    }
}
