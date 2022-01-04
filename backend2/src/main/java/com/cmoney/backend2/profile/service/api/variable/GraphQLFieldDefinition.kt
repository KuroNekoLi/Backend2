package com.cmoney.backend2.profile.service.api.variable

@Deprecated("Change to use ProfileWeb.getSelfMemberProfile")
enum class GraphQLFieldDefinition(val value: String) {
    Email("email"),
    IsBindingCellphone("isBindingCellphone"),
    PCoin("pCoin"),
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
    Level("levelInfo { exp level levelExp levelExpToNext}"),
    Badge("badges { badgeId isEquipped hasRead }")
}