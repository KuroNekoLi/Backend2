package com.cmoney.backend2.base.model.request

import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AccessTokenTest{

    private val TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IldBRktTTVptVmN6YzVGckdqTlBOT090emZudmE5RDNvIiwidHlwIjoiSldUIn0.eyJzdWIiOiIxMjMzNDciLCJ1c2VyX2d1aWQiOiJlY2FlMGFmOC0zMzJlLTQyNDgtYjAzNS0zMjM5YmVjODQ1ZjkiLCJhcHBfaWQiOiIyIiwidG9rZW5faWQiOiIxMiIsIm5iZiI6MTYwMzI2MDE3OSwiZXhwIjoxNjAzMzUwMTc5LCJpYXQiOjE2MDMyNjM3NzksImlzcyI6Imh0dHBzOi8vd3d3LmNtb25leS50dyIsImF1ZCI6ImNtb25leWFwaSJ9.LYOH-XaBRiRRtN8UgvP_fnet7sJOmo3MP0yEEr8BohbQan3OihWVcGPZ6KUkUrluwQhwYUgvRHy67kBaI1LldbmjT69DgrUozHuksupWHgFLjATHQw_eCO-3DFCx1LBzNRIhzCZpEvENVVRx6PbYen5cs_4YMRibW1i638daD31KNVV3kAopJCrn2Y8goPvI_ZaAHryPgthwna8UYtDzTuaaOa4FNaNlzaUAvBpfxlWfAaO0kSxCDyHvR2GWBCMRTdWA5N3QUwFkmkME7cKUMUvcwDZTN1SyCkuNqs0TWEzBlt4Xx1TJBEVYPApisP6_ahko31WnZO5C4icwYa1lkA"

    /**
     * 為了 is_guest = true
     */
    private val FAKE_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IldBRktTTVptVmN6YzVGckdqTlBOT090emZudmE5RDNvIiwidHlwIjoiSldUIn0.ewogICJzdWIiOiAiMTIzMzQ3IiwKICAidXNlcl9ndWlkIjogImVjYWUwYWY4LTMzMmUtNDI0OC1iMDM1LTMyMzliZWM4NDVmOSIsCiAgImFwcF9pZCI6ICIyIiwKICAiaXNfZ3Vlc3QiOiB0cnVlLAogICJ0b2tlbl9pZCI6ICIxMiIsCiAgIm5iZiI6IDE2MDMyNjAxNzksCiAgImV4cCI6IDE2MDMzNTAxNzksCiAgImlhdCI6IDE2MDMyNjM3NzksCiAgImlzcyI6ICJodHRwczovL3d3dy5jbW9uZXkudHciLAogICJhdWQiOiAiY21vbmV5YXBpIgp9.LYOH-XaBRiRRtN8UgvP_fnet7sJOmo3MP0yEEr8BohbQan3OihWVcGPZ6KUkUrluwQhwYUgvRHy67kBaI1LldbmjT69DgrUozHuksupWHgFLjATHQw_eCO-3DFCx1LBzNRIhzCZpEvENVVRx6PbYen5cs_4YMRibW1i638daD31KNVV3kAopJCrn2Y8goPvI_ZaAHryPgthwna8UYtDzTuaaOa4FNaNlzaUAvBpfxlWfAaO0kSxCDyHvR2GWBCMRTdWA5N3QUwFkmkME7cKUMUvcwDZTN1SyCkuNqs0TWEzBlt4Xx1TJBEVYPApisP6_ahko31WnZO5C4icwYa1lkA"

    @Test
    fun `使用合法的Token`() {
        val accessToken = AccessToken(TOKEN)
        Truth.assertThat(accessToken.getMemberId()).isEqualTo("123347")
        Truth.assertThat(accessToken.getAppId()).isEqualTo("2")
        Truth.assertThat(accessToken.getTokenId()).isEqualTo("12")
        Truth.assertThat(accessToken.getIssuedTime()).isEqualTo(1603260179)
        Truth.assertThat(accessToken.getExpiredTime()).isEqualTo(1603350179)
        Truth.assertThat(accessToken.getClientId()).isEqualTo("cmoneyapi")
        Truth.assertThat(accessToken.getIssuer()).isEqualTo("https://www.cmoney.tw")
        Truth.assertThat(accessToken.getIsGuest()).isFalse()
    }

    @Test
    fun `使用AccessToken建構式_會有預設值`() {
        val actual = AccessToken()
        Truth.assertThat(actual.getMemberId()).isEmpty()
        Truth.assertThat(actual.getAppId()).isEmpty()
        Truth.assertThat(actual.getTokenId()).isEmpty()
        Truth.assertThat(actual.getIssuedTime()).isEqualTo(0)
        Truth.assertThat(actual.getExpiredTime()).isEqualTo(0)
        Truth.assertThat(actual.getIssuer()).isEmpty()
        Truth.assertThat(actual.getClientId()).isEmpty()
        Truth.assertThat(actual.getIsGuest()).isFalse()
    }

    @Test
    fun `AccessToken是空字串_會有預設值`() {
        val actual = AccessToken()
        Truth.assertThat(actual.getMemberId()).isEmpty()
        Truth.assertThat(actual.getAppId()).isEmpty()
        Truth.assertThat(actual.getTokenId()).isEmpty()
        Truth.assertThat(actual.getIssuedTime()).isEqualTo(0)
        Truth.assertThat(actual.getExpiredTime()).isEqualTo(0)
        Truth.assertThat(actual.getIssuer()).isEmpty()
        Truth.assertThat(actual.getClientId()).isEmpty()
        Truth.assertThat(actual.getIsGuest()).isFalse()
    }

    @Test
    fun `AccessToken_is_guest為true`() {
        val accessToken = AccessToken(FAKE_TOKEN)
        Truth.assertThat(accessToken.getMemberId()).isEqualTo("123347")
        Truth.assertThat(accessToken.getAppId()).isEqualTo("2")
        Truth.assertThat(accessToken.getTokenId()).isEqualTo("12")
        Truth.assertThat(accessToken.getIssuedTime()).isEqualTo(1603260179)
        Truth.assertThat(accessToken.getExpiredTime()).isEqualTo(1603350179)
        Truth.assertThat(accessToken.getClientId()).isEqualTo("cmoneyapi")
        Truth.assertThat(accessToken.getIssuer()).isEqualTo("https://www.cmoney.tw")
        Truth.assertThat(accessToken.getIsGuest()).isTrue()
    }

    @Test
    fun `isEmpty_沒有設定Token_true`() {
        val actual = AccessToken()
        Truth.assertThat(actual.isEmpty()).isTrue()
    }

    @Test
    fun `isEmpty_有設定Token_false`() {
        val accessToken = AccessToken(TOKEN)
        Truth.assertThat(accessToken.isEmpty()).isFalse()
    }
}