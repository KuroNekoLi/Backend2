package com.cmoney.backend2.profile.service.api.profilefield

/**
 * 會員資訊欄位定義
 *
 * @property value 要求欄位名稱
 */
enum class MemberProfileField(val value: String) {
    EMAIL("email"),
    NAME("name"),
    P_COIN("pCoin"),
    NICKNAME("nickname"),
    GENDER("gender"),
    BIRTHDAY("birthday"),
    ADDRESS("address"),
    SIGNUP_DATE("signupDate"),
    BIO("bio"),
    CONTACT_EMAIL("contactEmail"),
    IMAGE("image"),
    CITY("city"),
    EDUCATION("education"),
    PROFESSION("profession"),
    INVESTMENT_EXPERIENCE("investmentExperience"),
    INVESTMENT_PROPERTY("investmentProperty"),
    INVESTMENT_TOOLS("investmentTools"),
    CUSTOMER_ID("customerId"),
    ACCOUNT("account"),
    CELLPHONE("cellphone"),
    CODE("code"),
    NUMBER("number"),
    FACEBOOK("facebook"),
    FB_ID("fbId"),
    APPLE_ID("appleId"),
    GUEST_ID("guestId"),
    LEVEL_INFO("levelInfo"),
    EXP("exp"),
    LEVEL("level"),
    LEVEL_EXP("levelExp"),
    LEVEL_EXP_TO_NEXT("levelExpToNext"),
    BADGES("badges"),
    BADGE_ID("badgeId"),
    IS_EQUIPPED("isEquipped"),
    HAS_READ("hasRead")
}