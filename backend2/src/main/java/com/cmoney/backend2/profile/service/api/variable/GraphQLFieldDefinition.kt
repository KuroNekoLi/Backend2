package com.cmoney.backend2.profile.service.api.variable

enum class GraphQLFieldDefinition(val value: String) {
    Email("email"),
    Name("name"),
    NickName("nickname"),
    Gender("gender"),
    Birthday("birthday"),
    Address("address"),
    SignUpData("signupDate"),
    Bio("bio"),
    ContactEmail("contactEmail"),
    Image("image"),
    City("city"),
    Education("education"),
    Profession("profession"),
    InvestmentExperience("investmentExperience"),
    InvestmentProperty("investmentProperty"),
    InvestmentTools("investmentTools"),
    CustomerId("customerId"),
    Account("account { email cellphone { code number } facebook { fbId email name } appleId guestId }"),
    Level("levelInfo { exp level toNext threshold }"),
    Badge("badges { id equipped read }")
}