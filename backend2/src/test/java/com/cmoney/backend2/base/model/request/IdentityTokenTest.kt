package com.cmoney.backend2.base.model.request

import com.google.common.truth.Truth
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IdentityTokenTest{

    private val TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkEydWczbUIxRFQiLCJ0eXAiOiJKV1QifQ.eyJzdWIiOiIxNTE3MjAxIiwiaXNfbmV3X3VzZXIiOiJGYWxzZSIsInVzZXJfZ3VpZCI6ImI4YmFkMWJjLWFjOWQtNGM3Yi1hNzEwLTY0ZDAzNTYzOGE2YyIsIm5pY2tuYW1lIjoi6aCt5aW95aOv5aOvIiwiY3VzdG9tZXJfaWQiOiIxNDk2NTcxIiwibmJmIjoxNjAzNDIxOTc2LCJleHAiOjE2MDM1MTE5NzYsImlhdCI6MTYwMzQyNTU3NiwiaXNzIjoiaHR0cHM6Ly93d3cuY21vbmV5LnR3IiwiYXVkIjoiY21jaGlwa21vYmlsZSJ9.REcBS0Y_tv3vlKPF-fOEA0OOrmToCsQYaqyxp8NOeBVuRH8Dv0q6BbJNxn4Mw5aIYLrPolBxDodz_KF18_UAlWMsMcKlyUuKNyAHUi2sCK_Pz9_9CyqjXlaYvU93IyM55vhuyqw9JDGi7Syzl5Yr0liuqZMtPeZTPRY-yEDrQABpsMJcmTpjntPYNaUP4jpvDbXFREaZCHO9-ZzpZhKcrrjRwHhus7HQrp3r3_nd07p1-myDRGfV7rZxYi1_tm-D-5s9fRLOOSQ8R1lMmqzKCNr1qekh-xS3zYcOXA-8hgcxqi-bMg0NNnwIRa0QAKbTCmvkSGTjtY9rnuBjf-QqNw"

    /**
     * 為了 is_new_user = true
     */
    private val FAKE_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkEydWczbUIxRFQiLCJ0eXAiOiJKV1QifQ.ewogICJzdWIiOiAiMTUxNzIwMSIsCiAgImlzX25ld191c2VyIjogInRydWUiLAogICJ1c2VyX2d1aWQiOiAiYjhiYWQxYmMtYWM5ZC00YzdiLWE3MTAtNjRkMDM1NjM4YTZjIiwKICAibmlja25hbWUiOiAi6aCt5aW95aOv5aOvIiwKICAiY3VzdG9tZXJfaWQiOiAiMTQ5NjU3MSIsCiAgIm5iZiI6IDE2MDM0MjE5NzYsCiAgImV4cCI6IDE2MDM1MTE5NzYsCiAgImlhdCI6IDE2MDM0MjU1NzYsCiAgImlzcyI6ICJodHRwczovL3d3dy5jbW9uZXkudHciLAogICJhdWQiOiAiY21jaGlwa21vYmlsZSIKfQ==.REcBS0Y_tv3vlKPF-fOEA0OOrmToCsQYaqyxp8NOeBVuRH8Dv0q6BbJNxn4Mw5aIYLrPolBxDodz_KF18_UAlWMsMcKlyUuKNyAHUi2sCK_Pz9_9CyqjXlaYvU93IyM55vhuyqw9JDGi7Syzl5Yr0liuqZMtPeZTPRY-yEDrQABpsMJcmTpjntPYNaUP4jpvDbXFREaZCHO9-ZzpZhKcrrjRwHhus7HQrp3r3_nd07p1-myDRGfV7rZxYi1_tm-D-5s9fRLOOSQ8R1lMmqzKCNr1qekh-xS3zYcOXA-8hgcxqi-bMg0NNnwIRa0QAKbTCmvkSGTjtY9rnuBjf-QqNw"

    @Test
    fun `使用合法的Token`() {
        val token = IdentityToken(TOKEN)
        Truth.assertThat(token.getMemberId()).isEqualTo("1517201")
        Truth.assertThat(token.getMemberGuid()).isEqualTo("b8bad1bc-ac9d-4c7b-a710-64d035638a6c")
        Truth.assertThat(token.getMemberNickname()).isEqualTo("頭好壯壯")
        Truth.assertThat(token.getIssuedTime()).isEqualTo(1603421976)
        Truth.assertThat(token.getExpiredTime()).isEqualTo(1603511976)
        Truth.assertThat(token.getClientId()).isEqualTo("cmchipkmobile")
        Truth.assertThat(token.getIssuer()).isEqualTo("https://www.cmoney.tw")
    }

    @Test
    fun `使用AccessToken建構式_會有預設值`() {
        val token = IdentityToken()
        Truth.assertThat(token.getMemberId()).isEmpty()
        Truth.assertThat(token.getMemberGuid()).isEmpty()
        Truth.assertThat(token.getMemberNickname()).isEmpty()
        Truth.assertThat(token.getIssuedTime()).isEqualTo(0)
        Truth.assertThat(token.getExpiredTime()).isEqualTo(0)
        Truth.assertThat(token.getClientId()).isEmpty()
        Truth.assertThat(token.getIssuer()).isEmpty()
    }

    @Test
    fun `AccessToken是空字串_會有預設值`() {
        val token = IdentityToken("")
        Truth.assertThat(token.getMemberId()).isEmpty()
        Truth.assertThat(token.getMemberGuid()).isEmpty()
        Truth.assertThat(token.getMemberNickname()).isEmpty()
        Truth.assertThat(token.getIssuedTime()).isEqualTo(0)
        Truth.assertThat(token.getExpiredTime()).isEqualTo(0)
        Truth.assertThat(token.getClientId()).isEmpty()
        Truth.assertThat(token.getIssuer()).isEmpty()
    }

    @Test
    fun `AccessToken_用戶為新註冊用戶`() {
        val token = IdentityToken(FAKE_TOKEN)
        Truth.assertThat(token.getMemberId()).isEqualTo("1517201")
        Truth.assertThat(token.getMemberGuid()).isEqualTo("b8bad1bc-ac9d-4c7b-a710-64d035638a6c")
        Truth.assertThat(token.getMemberNickname()).isEqualTo("頭好壯壯")
        Truth.assertThat(token.getIssuedTime()).isEqualTo(1603421976)
        Truth.assertThat(token.getExpiredTime()).isEqualTo(1603511976)
        Truth.assertThat(token.getClientId()).isEqualTo("cmchipkmobile")
        Truth.assertThat(token.getIssuer()).isEqualTo("https://www.cmoney.tw")
    }

    @Test
    fun nickNameWithChineseCharacters() {
        Assert.assertEquals(
            "股市調查局長",
            IdentityToken(
                "eyJhbGciOiJSUzI1NiIsImtpZCI6IkEydWczbUIxRFQiLCJ0eXAiOiJKV1QifQ.eyJzdWIiOiIyNDY5MDkyIiwiaXNfbmV3X3VzZXIiOiJGYWxzZSIsInVzZXJfZ3VpZCI6IjIxYzNiYTU2LWJlMTktNGQxMC1hMWZkLWU5MWJmNGYxMmFlMSIsIm5pY2tuYW1lIjoi6IKh5biC6Kq_5p-l5bGA6ZW3IiwiY3VzdG9tZXJfaWQiOiIyNDQzNTc2IiwibmJmIjoxNjA2OTU1Njk2LCJleHAiOjE2MDcwNDU2OTYsImlhdCI6MTYwNjk1OTI5NiwiaXNzIjoiaHR0cHM6Ly93d3cuY21vbmV5LnR3IiwiYXVkIjoiY21jaGlwa21vYmlsZSJ9.QcD1SmBtG9uXe7sRyIrG1nnzSXS-WqlanV6dscM1qzK-8Zi8K_8H2HnT-Yk31Rk5cSR5GhmPaYSaMQCIlW_WDtVYlc0fOEqOQiEfNGVBivEkKFguOsHsOQ87aIKuZR_avVV0ARi9W7a-CGE154G1zfCED5_jeDwkQflIFvYGQ0gcd0mvsABjiUIIQ4FognRSm7WKWItoj1XvOC5dLRhh5dhyVSUsHch24zbXegjlUSnuN3zStUPLFrLx4bAw5pqgan3ZrPmqYsCd-NE-i5ivJCy-u72oOJWPY3NEcb_z6S8xGaUDcLFdOGAlllP6fHkUpmzJbjaiG6YBeY5_fPb2fQ"
            ).getMemberNickname()
        )
    }
}