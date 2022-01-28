package com.cmoney.backend2.frontendlogger.service

import com.cmoney.backend2.frontendlogger.service.api.LogRequestBody

/**
 * FrontEndLogger
 *
 * 注意事項：為了資訊安全，請不要把機敏資訊帶上來，e.g.,用戶的帳號/密碼。
 * @see @see <a href="http://192.168.10.123:5601/app/discover#/?_g=(filters:!(),refreshInterval:(pause:!t,value:0),time:(from:now-15m,to:now))&_a=(columns:!(object.request.method,object.request.domain,object.request.path,object.userInfo.appId,object.userInfo.memberId,object.message),filters:!(),index:'1e139290-7f16-11ec-a999-3dc4aeaad150',interval:auto,query:(language:kuery,query:''),sort:!())">log</a>
 */
interface FrontEndLoggerWeb {

    val baseHost: String

    /**
     * 新增 log
     *
     * @param body 要新增的 log
     * @param indexName elk的index name
     * @param host
     * @return
     */
    suspend fun log(
        body: List<LogRequestBody>,
        indexName: String = "default",
        host: String = this.baseHost
    ): Result<Unit>

}
