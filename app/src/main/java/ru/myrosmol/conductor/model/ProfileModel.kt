package ru.myrosmol.conductor.model

data class ProfileModel(
    var id: Int? = null,
    var email: String? = null,
    var fullname: String? = null,
    var tokens: String? = null,
    var role: Int? = null,
    var coins: Int? = null,
    var position: String? = null,
    var birthDate: String? = null,
    var description: String? = null,
    var telegram: String? = null,
    var whatsApp: String? = null,
    var vk: String? = null,
    var roadmapId: Int? = null,
    var divisionId: Int? = null
)