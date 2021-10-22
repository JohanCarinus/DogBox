package com.example.dogbox.repository

import android.net.Uri
import androidx.core.net.toUri
import org.json.JSONObject


class DogsRepositoryImpl : DogsRepository {

    val MAX_DOGS = 50
    val PAGE_SIZE = 20
    val URL_FIELD_NAME = "message"
    val TEMP_DOGS_JSON = "{\"message\":[\"https:\\/\\/images.dog.ceo\\/breeds\\/mastiff-bull\\/n02108422_861.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/spaniel-brittany\\/n02101388_1456.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/samoyed\\/n02111889_16334.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/samoyed\\/n02111889_13657.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/pomeranian\\/pic2_p_1.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/dalmatian\\/cooper2.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/boxer\\/IMG_0002.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/african\\/n02116738_9829.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/schipperke\\/n02104365_1151.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/labradoodle\\/labradoodle-forrest.png\",\"https:\\/\\/images.dog.ceo\\/breeds\\/terrier-toy\\/n02087046_5838.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/collie-border\\/n02106166_4107.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/malinois\\/n02105162_8072.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/retriever-flatcoated\\/n02099267_40.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/australian-shepherd\\/pepper2.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/bulldog-english\\/bunz.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/terrier-bedlington\\/n02093647_655.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/borzoi\\/n02090622_6293.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/schnauzer-giant\\/n02097130_1828.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/corgi-cardigan\\/n02113186_6992.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/sheepdog-shetland\\/n02105855_7612.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/akita\\/512px-Ainu-Dog.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/corgi-cardigan\\/n02113186_10891.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/malamute\\/n02110063_12597.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/bluetick\\/n02088632_1077.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/boxer\\/n02108089_2670.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/bulldog-english\\/mami.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/whippet\\/n02091134_16109.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/terrier-silky\\/n02097658_329.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/pug\\/n02110958_630.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/buhund-norwegian\\/hakon1.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/pomeranian\\/n02112018_3599.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/husky\\/n02110185_14906.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/terrier-australian\\/n02096294_6455.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/bulldog-english\\/jager-1.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/shiba\\/shiba-6.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/african\\/n02116738_308.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/terrier-sealyham\\/n02095889_5718.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/terrier-border\\/n02093754_8019.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/bulldog-english\\/mami.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/bulldog-boston\\/n02096585_1295.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/retriever-curly\\/n02099429_2756.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/malamute\\/n02110063_12447.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/akita\\/Akita_Inu_dog.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/pointer-germanlonghair\\/hans2.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/spaniel-blenheim\\/n02086646_1368.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/frise-bichon\\/jh-ezio-2.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/labradoodle\\/labradoodle-forrest.png\",\"https:\\/\\/images.dog.ceo\\/breeds\\/havanese\\/00100trPORTRAIT_00100_BURST20191222103956878_COVER.jpg\",\"https:\\/\\/images.dog.ceo\\/breeds\\/pekinese\\/n02086079_347.jpg\"],\"status\":\"success\"}"

    override fun getDogUrls(breed: String): List<Uri> {
        val dogUrlsJSONArray = JSONObject(TEMP_DOGS_JSON).getJSONArray(URL_FIELD_NAME) // TODO: Catch failed JSON conversion
        val dogUrlsList = mutableListOf<Uri>()

        for(i in 0 until dogUrlsJSONArray.length()) {
            dogUrlsList.add(dogUrlsJSONArray.getString(i).toUri())
        }

        return dogUrlsList
    }
}