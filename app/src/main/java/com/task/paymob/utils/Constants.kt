package com.task.paymob.utils



//SHARED PREFERENCE
object SHARED_PREFERENCE {

}

object USER_DATA {
    //@JvmField
    //var CURRENT_USER: ResponseConfirmCode? = null
    const val AUTHORIZATION = "Authorization"
    const val BEARER = "Bearer "
}


object USER_INFO {
    const val ENGLISH_LANGUAGE = "en"
    const val ARABIC_LANGUAGE = "ar"


    @JvmField
    var CURRENT_LANGUAGE: String = ""
    var TOKEN: String = ""
    var CURRENT_LANGUAGE_ID: String = ""

}

object APIS {
    //    http://ec2-54-194-113-34.eu-west-1.compute.amazonaws.com:5005
//    const val BASE_URL = "http://ec2-34-245-145-165.eu-west-1.compute.amazonaws.com:5005/"
    const val BASE_URL = "https://api.themoviedb.org/3/discover/movie/"



}

