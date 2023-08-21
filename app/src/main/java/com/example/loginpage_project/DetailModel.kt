package com.example.loginpage_project

class DetailModel {
    constructor(
        key: String,
        firstName: String,
        lastName: String,
        birth_Date: String,
        e_mail_ID: String,
        male: Boolean,
        female: Boolean,
        mobile_No: String,
        sports: Boolean,
        game: Boolean,
        music: Boolean
    ) {
        this.key = key
        this.firstName = firstName
        this.lastName = lastName
        this.birth_Date = birth_Date
        this.e_mail_ID = e_mail_ID
        this.male = male
        this.female = female
        this.mobile_No = mobile_No
        this.sports = sports
        this.game = game
        this.music = music
    }

    lateinit var key: String
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var birth_Date: String
    lateinit var e_mail_ID: String
       var male:Boolean
       var female:Boolean
    lateinit var mobile_No: String
    var sports: Boolean
    var game: Boolean
    var music: Boolean


}


