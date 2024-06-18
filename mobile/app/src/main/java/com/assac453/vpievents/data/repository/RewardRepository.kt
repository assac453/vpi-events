package com.assac453.vpievents.data.repository

import com.assac453.vpievents.data.entity.Reward
import com.assac453.vpievents.logic.Constants
import com.assac453.vpievents.network.service.RewardService

interface RewardRepository {
    suspend fun getRewards(status: String): List<Reward>
}

class NetworkRewardRepository(
    private val rewardService: RewardService
) : RewardRepository {
    override suspend fun getRewards(status: String): List<Reward> {
        return rewardService.rewards(status).map { item ->
            Reward(
                id = item.id.toString(),
                name = item.name,
                cost = item.pointsRequired,
                url = "http://${Constants.SERVER_ADDRESS}:${Constants.PORT}/storage/${item.image}",
            )
        }
//        return MockRewards().getRewards(status)
    }
}

/*

class MockRewards() : RewardRepository {
    override suspend fun getRewards(status: String): List<Reward> {
        return mutableListOf(
            Reward(
                "1",
                "Ручка ВПИ",
                4,
                "https://pilotrus.ru/wa-data/public/shop/products/57/01/157/images/7026/7026.750@2x.jpg"
            ),
            Reward(
                "2",
                "Рюкзак ВПИ",
                10,
                "https://media.komus.ru/medias/sys_master/root/h89/he6/11799453728798/843414-1-800Wx800H.jpg"
            ),
            Reward(
                "3",
                "Футболка ВПИ",
                30,
                "https://files.gifts.ru/reviewer/tb/13/6140.60_73_500.jpg"
            ),
            Reward(
                "4",
                "Блокнот ВПИ",
                3,
                "https://media.komus.ru/medias/sys_master/root/h10/h94/10568726741022/204993-1-800Wx800H.jpg"
            ),
            Reward(
                "5",
                "Карандаш ВПИ",
                2,
                "https://krasniykarandash.ru/upload/iblock/7ed/7ed6cd65fb90772398b4ffbaa2ffd9bd.jpg"
            ),
            Reward(
                "6",
                "Кружка ВПИ",
                8,
                "https://cs1.livemaster.ru/storage/36/66/b3a7b11eaa139492e1426c72c9rc--posuda-kruzhka-shrek.jpg"
            ),
            Reward(
                "7",
                "Магнит ВПИ",
                1,
                "https://magniart.ru/image/cache/catalog/product/magnet/bredberi-1200x900.jpg"
            ),
        )
    }

}
*/
