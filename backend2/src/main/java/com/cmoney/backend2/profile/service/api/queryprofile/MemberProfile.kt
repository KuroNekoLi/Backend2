package com.cmoney.backend2.profile.service.api.queryprofile

/**
 * 會員資料
 *
 * @property id 編號
 * @property account 帳號資訊
 * @property address 地址
 * @property badges 徽章資訊集合
 * @property bio 自我介紹
 * @property birthday 生日
 * @property city 居住地 (台北市、新北市、基隆市、桃園市、新竹市、
 * 新竹縣、苗栗縣、台中市、彰化縣、南投縣、雲林縣、
 * 嘉義市、嘉義縣、台南市、高雄市、屏東縣、宜蘭縣、
 * 花蓮縣、台東縣、澎湖縣、金門縣、其他)
 * @property contactEmail
 * @property customerId
 * @property education 教育程度，(中學以下、高中/高職、專科、大學、研究所以上)
 * @property gender 性別
 * @property image 頭像(URL)
 * @property investmentExperience 投資經驗  0~1、1~3、3~5、5~10、10~20、20~30、30~50)
 * @property investmentProperty 投資標的 (積極(短線交易)、穩健(波段操作)、保守(長期持有))
 * @property investmentTools 投資工具 (股票、基金、期貨、選擇權、外匯、黃金、定存、不動產)
 * @property isBindingCellphone 是否已綁定手機
 * @property levelInfo 等級資訊
 * @property name 名稱
 * @property nickname 暱稱
 * @property pCoin P幣
 * @property profession 職業 (專職投資人、金融保險、電子製造、傳統製造、網際網路/資訊軟體、餐飲/百貨/零售、媒體出版、不動產/建築/營造、醫療、教育研究、公務人員、農漁牧、家管)
 * @property signupDate 註冊日期
 */
class MemberProfile private constructor(
    private val params: MemberProfileQueryParams,
    private val _id: String,
    private val _account: Account?,
    private val _address: String?,
    private val _badges: List<Badges>?,
    private val _bio: String?,
    private val _birthday: String?,
    private val _city: String?,
    private val _contactEmail: String?,
    private val _customerId: Long?,
    private val _education: String?,
    private val _gender: String?,
    private val _image: String?,
    private val _investmentExperience: String?,
    private val _investmentProperty: String?,
    private val _investmentTools: List<String>?,
    private val _isBindingCellphone: Boolean?,
    private val _levelInfo: LevelInfo?,
    private val _name: String?,
    private val _nickname: String?,
    private val _pCoin: Long?,
    private val _profession: String?,
    private val _signupDate: String?
) {
    internal constructor(
        params: MemberProfileQueryParams,
        id: String,
        raw: RawMemberProfile
    ) : this(
        params = params,
        _id = id,
        _account = params.account?.let { accountQueryParams ->
            if (raw.account != null) {
                Account(params = accountQueryParams, raw = raw.account)
            } else {
                null
            }
        },
        _address = raw.address,
        _badges = params.badges?.let { badgesQueryParams ->
            if (raw.badges != null) {
                raw.badges.map { badges ->
                    Badges(params = badgesQueryParams, raw = badges)
                }
            } else {
                null
            }
        },
        _bio = raw.bio,
        _birthday = raw.birthday,
        _city = raw.city,
        _contactEmail = raw.contactEmail,
        _customerId = raw.customerId,
        _education = raw.education,
        _gender = raw.gender,
        _image = raw.image,
        _investmentExperience = raw.investmentExperience,
        _investmentProperty = raw.investmentProperty,
        _investmentTools = raw.investmentTools,
        _isBindingCellphone = raw.isBindingCellphone,
        _levelInfo = params.levelInfo?.let { levelInfoQueryParams ->
            if (raw.levelInfo != null) {
                LevelInfo(params = levelInfoQueryParams, raw = raw.levelInfo)
            } else {
                null
            }
        },
        _name = raw.name,
        _nickname = raw.nickname,
        _pCoin = raw.pCoin,
        _profession = raw.profession,
        _signupDate = raw.signupDate
    )

    val id: String
        get() {
            return _id
        }

    val account: Account?
        get() {
            requireNotNull(params.account) {
                "MemberProfileQueryBuilder.account not request"
            }
            return _account
        }

    val address: String?
        get() {
            requireNotNull(params.address) {
                "MemberProfileQueryBuilder.address not request"
            }
            return _address
        }

    val badges: List<Badges>?
        get() {
            requireNotNull(params.badges) {
                "MemberProfileQueryBuilder.badges not request"
            }
            return _badges
        }

    val bio: String?
        get() {
            requireNotNull(params.bio) {
                "MemberProfileQueryBuilder.bio not request"
            }
            return _bio
        }

    val birthday: String?
        get() {
            requireNotNull(params.birthday) {
                "MemberProfileQueryBuilder.birthday not request"
            }
            return _birthday
        }

    val city: String?
        get() {
            requireNotNull(params.city) {
                "MemberProfileQueryBuilder.city not request"
            }
            return _city
        }

    val contactEmail: String?
        get() {
            requireNotNull(params.contactEmail) {
                "MemberProfileQueryBuilder.contactEmail not request"
            }
            return _contactEmail
        }

    val customerId: Long?
        get() {
            requireNotNull(params.customerId) {
                "MemberProfileQueryBuilder.customerId not request"
            }
            return _customerId
        }

    val education: String?
        get() {
            requireNotNull(params.education) {
                "MemberProfileQueryBuilder.education not request"
            }
            return _education
        }

    val gender: String?
        get() {
            requireNotNull(params.gender) {
                "MemberProfileQueryBuilder.gender not request"
            }
            return _gender
        }

    val image: String?
        get() {
            requireNotNull(params.image) {
                "MemberProfileQueryBuilder.image not request"
            }
            return _image
        }

    val investmentExperience: String?
        get() {
            requireNotNull(params.investmentExperience) {
                "MemberProfileQueryBuilder.investmentExperience not request"
            }
            return _investmentExperience
        }

    val investmentProperty: String?
        get() {
            requireNotNull(params.investmentProperty) {
                "MemberProfileQueryBuilder.investmentProperty not request"
            }
            return _investmentProperty
        }

    val investmentTools: List<String>?
        get() {
            requireNotNull(params.investmentTools) {
                "MemberProfileQueryBuilder.investmentTools not request"
            }
            return _investmentTools
        }

    val isBindingCellphone: Boolean?
        get() {
            requireNotNull(params.isBindingCellphone) {
                "MemberProfileQueryBuilder.isBindingCellphone not request"
            }
            return _isBindingCellphone
        }

    val levelInfo: LevelInfo?
        get() {
            requireNotNull(params.levelInfo) {
                "MemberProfileQueryBuilder.levelInfo not request"
            }
            return _levelInfo
        }

    val name: String?
        get() {
            requireNotNull(params.name) {
                "MemberProfileQueryBuilder.name not request"
            }
            return _name
        }

    val nickname: String?
        get() {
            requireNotNull(params.nickname) {
                "MemberProfileQueryBuilder.nickname not request"
            }
            return _nickname
        }

    val pCoin: Long?
        get() {
            requireNotNull(params.pCoin) {
                "MemberProfileQueryBuilder.pCoin not request"
            }
            return _pCoin
        }

    val profession: String?
        get() {
            requireNotNull(params.profession) {
                "MemberProfileQueryBuilder.profession not request"
            }
            return _profession
        }

    val signupDate: String?
        get() {
            requireNotNull(params.signupDate) {
                "MemberProfileQueryBuilder.signupDate not request"
            }
            return _signupDate
        }
}