package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.profile.service.ProfileWeb
import com.cmoney.backend2.profile.service.api.getusergraphqlinfo.UserGraphQLInfo
import com.cmoney.backend2.profile.service.api.mutationmyusergraphqlinfo.MutationData
import com.cmoney.backend2.profile.service.api.variable.GraphQLFieldDefinition
import com.cmoney.backend2.sample.extension.logResponse
import com.cmoney.backend2.sample.servicecase.data.GetNicknameAndAvatarResponse
import com.google.gson.reflect.TypeToken
import org.koin.core.component.inject


class ProfileServiceCase : ServiceCase {
    private val profileWeb by inject<ProfileWeb>()

    override suspend fun testAll() {
        profileWeb.getAccount().logResponse(TAG)
        profileWeb.sendVerificationEmail(EMAIL_FOR_TESTING).logResponse(TAG)
        profileWeb.sendForgotPasswordEmail(EMAIL_FOR_TESTING).logResponse(TAG)
        profileWeb.sendVerificationSms(PHONE_FOR_TESTING).logResponse(TAG)
        profileWeb.checkCodeEmail(EMAIL_FOR_TESTING, CODE_FOR_TESTING).logResponse(TAG)
        profileWeb.checkCodeSms(PHONE_FOR_TESTING, CODE_FOR_TESTING).logResponse(TAG)
        profileWeb.changePassword("1234", "1234").logResponse(TAG)
        profileWeb.resetPasswordBySms(CODE_FOR_TESTING, PHONE_FOR_TESTING, "1234")
        profileWeb.resetPasswordByEmail(CODE_FOR_TESTING, EMAIL_FOR_TESTING, "1234")
        profileWeb.linkEmail(CODE_FOR_TESTING, "tester_13@cmoney.com.tw").logResponse(TAG)
//        profileWeb.linkPhone(CODE_FOR_TESTING, "+886912345678").logResponse(TAG)
        profileWeb.linkFacebook("token").logResponse(TAG)
        profileWeb.linkContactEmail(CODE_FOR_TESTING, EMAIL_FOR_TESTING)


        profileWeb.convertGuestByEmail(EMAIL_FOR_TESTING, "1234", "1234").logResponse(TAG)
//                profileWeb.convertGuestBySms(PHONE_FOR_TESTING,"1234","1234").logResponse(TAG)
        profileWeb.signUpByEmail(EMAIL_FOR_TESTING).logResponse(TAG)
        profileWeb.signUpByPhone(PHONE_FOR_TESTING).logResponse(TAG)
        profileWeb.signUpCompleteByEmail(
            EMAIL_FOR_TESTING,
            CODE_FOR_TESTING,
            "password"
        ).logResponse(TAG)
        profileWeb.signUpCompleteByPhone(
            PHONE_FOR_TESTING,
            CODE_FOR_TESTING,
            "password"
        ).logResponse(TAG)
        profileWeb.getRegistrationCodeByEmail(
            EMAIL_FOR_TESTING,
            CODE_FOR_TESTING
        ).logResponse(TAG)
        profileWeb.getRegistrationCodeByPhone(
            EMAIL_FOR_TESTING,
            CODE_FOR_TESTING
        ).logResponse(TAG)

        profileWeb.getSelfMemberProfile {
            pCoin
            name
            nickname
            gender
            birthday
            address
            signupDate
            bio
            contactEmail
            image
            city
            education
            profession
            investmentExperience
            investmentProperty
            investmentTools
            customerId
            account {
                email
                cellphone {
                    code
                    number
                }
                facebook {
                    fbId
                    email
                    name
                }
                appleId
                guestId
            }
            levelInfo {
                exp
                level
                levelExp
                levelExpToNext
            }
            badges {
                badgeId
                isEquipped
                hasRead
            }
        }
            .logResponse(TAG)
        profileWeb.mutateMemberProfile(
            mutationData = MutationData.Builder(
                nickname = "Tester_X",
                image = "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422",
                bio = "我的自我介紹"
            ).build()
        ).logResponse(TAG)

        profileWeb.mutationMyUserGraphQlInfo<GetNicknameAndAvatarResponse>(
            type = object : TypeToken<GetNicknameAndAvatarResponse>() {}.type,
            variable = MutationData.Builder(
                nickname = "Tester_X",
                image = "https://storage.googleapis.com/cmoney-image/1378ceeb-2f10-4ef5-8d38-cb63f8f97422",
                bio = "我的自我介紹"
            ).build()
        ).logResponse(TAG)
        profileWeb.getMyUserGraphQlInfo<GetNicknameAndAvatarResponse>(
            fields = setOf(
                GraphQLFieldDefinition.NickName,
                GraphQLFieldDefinition.Image,
                GraphQLFieldDefinition.Level,
                GraphQLFieldDefinition.Badge
            ),
            type = object : TypeToken<GetNicknameAndAvatarResponse>() {}.type
        ).logResponse(TAG)

        profileWeb.getUserGraphQLInfo<GetNicknameAndAvatarResponse>(
            memberIds = listOf(
                1, 2, 3, 4, 5
            ),
            fields = setOf(
                UserGraphQLInfo.NickName,
                UserGraphQLInfo.ID,
                UserGraphQLInfo.Image,
                UserGraphQLInfo.Bio
            ),
            //注意，因為回傳會是List，記得在型別要先加上List -> TypeToken<List<T>>(){}.type
            type = object : TypeToken<List<GetNicknameAndAvatarResponse>>() {}.type
        ).logResponse(TAG)

        val result = profileWeb.getOtherMemberProfiles(
            memberIds = listOf(1, 2, 3, 4, 5)
        ) {
            nickname
            id
            image
            bio
        }
        result.fold({
            it.forEach { otherMemberProfile ->
                println("id: ${otherMemberProfile.id}, nickname: ${otherMemberProfile.nickname}, image: ${otherMemberProfile.image}, bio: ${otherMemberProfile.bio}")
            }
        }, {
            println("failed: $it")
        })
    }

    companion object {
        private const val PHONE_FOR_TESTING = "+886978850397"
        private const val EMAIL_FOR_TESTING = "tester_13@cmoney.com.tw"
        private const val CODE_FOR_TESTING = "94168"
        private const val TAG = "Profile"
    }
}