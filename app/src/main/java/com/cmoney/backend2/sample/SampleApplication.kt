package com.cmoney.backend2.sample

import android.app.Application
import com.cmoney.backend2.activity.di.activityServiceModule
import com.cmoney.backend2.additioninformationrevisit.di.additionalInformationRevisitServiceModule
import com.cmoney.backend2.authorization.di.authorizationServiceModule
import com.cmoney.backend2.base.di.BACKEND2_RETROFIT
import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.di.backendBaseModule
import com.cmoney.backend2.base.model.setting.Platform
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.billing.di.billingServiceModule
import com.cmoney.backend2.cellphone.di.cellphoneServiceModule
import com.cmoney.backend2.centralizedimage.di.centralizedImageServiceModule
import com.cmoney.backend2.chat.di.chatServiceModule
import com.cmoney.backend2.chipk.di.chipkServiceModule
import com.cmoney.backend2.clientconfiguration.di.clientConfigurationModule
import com.cmoney.backend2.cmtalk.di.cmtalkServiceModule
import com.cmoney.backend2.common.di.commonServiceModule
import com.cmoney.backend2.crm.di.crmServiceModule
import com.cmoney.backend2.customgroup.di.customGroupServiceModule
import com.cmoney.backend2.customgroup2.di.customGroup2ServiceModule
import com.cmoney.backend2.dtno.di.dtnoServiceModule
import com.cmoney.backend2.emilystock.di.emilyServiceModule
import com.cmoney.backend2.forumocean.di.forumOceanServiceModule
import com.cmoney.backend2.identityprovider.di.identityProviderServiceModule
import com.cmoney.backend2.media.di.mediaServiceModule
import com.cmoney.backend2.mobileocean.di.mobileOceanServiceModule
import com.cmoney.backend2.note_extension.di.noteExtensionServiceModule
import com.cmoney.backend2.notes.di.notesServiceModule
import com.cmoney.backend2.notification.di.notificationServiceModule
import com.cmoney.backend2.notification2.di.notification2ServiceModule
import com.cmoney.backend2.ocean.di.oceanServiceModule
import com.cmoney.backend2.portal.di.portalServiceModule
import com.cmoney.backend2.profile.di.profileServiceModule
import com.cmoney.backend2.realtimeaftermarket.di.realtimeAfterMarketServiceModule
import com.cmoney.backend2.sample.di.viewModule
import com.cmoney.backend2.sample.model.logger.ApplicationLoggerAdapter
import com.cmoney.backend2.sample.servicecase.DataServiceCase
import com.cmoney.backend2.tickdata.di.tickDataServiceModule
import com.cmoney.backend2.trial.di.trialServiceModule
import com.cmoney.backend2.userbehavior.di.userBehaviorServiceModule
import com.cmoney.backend2.virtualassets.di.virtualAssetsServiceModule
import com.cmoney.data_logdatarecorder.logger.LogDataRecorderLoggerAdapter
import com.cmoney.data_logdatarecorder.recorder.LogDataRecorder
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            // 應用程式的Logger
            Logger.addLogAdapter(ApplicationLoggerAdapter())
            // 模組的Logger
            Logger.addLogAdapter(LogDataRecorderLoggerAdapter())
        }
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            androidContext(this@SampleApplication)
            loadKoinModules(
                listOf(
                    viewModule,
                    //backend module
                    activityServiceModule,
                    additionalInformationRevisitServiceModule,
                    authorizationServiceModule,
                    backendBaseModule,
                    billingServiceModule,
                    cellphoneServiceModule,
                    chatServiceModule,
                    chipkServiceModule,
                    cmtalkServiceModule,
                    commonServiceModule,
                    customGroupServiceModule,
                    customGroup2ServiceModule,
                    DataServiceCase.getDataWebModule("https://datasv.cmoney.tw:5001/") {
                        get(BACKEND2_RETROFIT)
                    },
                    dtnoServiceModule,
                    emilyServiceModule,
                    forumOceanServiceModule,
                    identityProviderServiceModule,
                    centralizedImageServiceModule,
                    mediaServiceModule,
                    mobileOceanServiceModule,
                    noteExtensionServiceModule,
                    notesServiceModule,
                    notificationServiceModule,
                    notification2ServiceModule,
                    oceanServiceModule,
                    portalServiceModule,
                    profileServiceModule,
                    realtimeAfterMarketServiceModule,
                    tickDataServiceModule,
                    trialServiceModule,
                    virtualAssetsServiceModule,
                    crmServiceModule,
                    userBehaviorServiceModule,
                    clientConfigurationModule
                )
            )
        }
        LogDataRecorder.initialization(this) {
            isEnable = true
            appId = 2
            platform = com.cmoney.domain_logdatarecorder.data.information.Platform.Android
        }
        get<Setting>(BACKEND2_SETTING).apply {
            appVersion = BuildConfig.VERSION_NAME
            appVersionCode = BuildConfig.VERSION_CODE
            platform = Platform.Android
        }
    }
}