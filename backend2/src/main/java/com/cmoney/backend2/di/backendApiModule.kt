package com.cmoney.backend2.di

import com.cmoney.backend2.activity.di.activityServiceModule
import com.cmoney.backend2.additioninformationrevisit.di.additionalInformationRevisitServiceModule
import com.cmoney.backend2.authorization.di.authorizationServiceModule
import com.cmoney.backend2.base.di.backendBaseModule
import com.cmoney.backend2.billing.di.billingServiceModule
import com.cmoney.backend2.brokerdatatransmission.di.brokerDataTransmissionServiceModule
import com.cmoney.backend2.cellphone.di.cellphoneServiceModule
import com.cmoney.backend2.centralizedimage.di.centralizedImageServiceModule
import com.cmoney.backend2.chat.di.chatServiceModule
import com.cmoney.backend2.chipk.di.chipkServiceModule
import com.cmoney.backend2.clientconfiguration.di.clientConfigurationModule
import com.cmoney.backend2.cmtalk.di.cmtalkServiceModule
import com.cmoney.backend2.common.di.commonServiceModule
import com.cmoney.backend2.commonuse.di.commonUseModule
import com.cmoney.backend2.crawlsetting.di.crawlSettingServiceModule
import com.cmoney.backend2.crm.di.crmServiceModule
import com.cmoney.backend2.customgroup.di.customGroupServiceModule
import com.cmoney.backend2.customgroup2.di.customGroup2ServiceModule
import com.cmoney.backend2.data.di.dataServiceModule
import com.cmoney.backend2.dtno.di.dtnoServiceModule
import com.cmoney.backend2.emilystock.di.emilyServiceModule
import com.cmoney.backend2.forumocean.di.forumOceanServiceModule
import com.cmoney.backend2.frontendlogger.di.frontEndLoggerServiceModule
import com.cmoney.backend2.identityprovider.di.identityProviderServiceModule
import com.cmoney.backend2.imagerecognition.di.imageRecognitionServiceModule
import com.cmoney.backend2.media.di.mediaServiceModule
import com.cmoney.backend2.mobileocean.di.mobileOceanServiceModule
import com.cmoney.backend2.note_extension.di.noteExtensionServiceModule
import com.cmoney.backend2.notes.di.notesServiceModule
import com.cmoney.backend2.notification.di.notificationServiceModule
import com.cmoney.backend2.notification2.di.notification2ServiceModule
import com.cmoney.backend2.ocean.di.oceanServiceModule
import com.cmoney.backend2.portal.di.portalServiceModule
import com.cmoney.backend2.productdataprovider.di.productProvider
import com.cmoney.backend2.profile.di.profileServiceModule
import com.cmoney.backend2.realtimeaftermarket.di.realtimeAfterMarketServiceModule
import com.cmoney.backend2.tickdata.di.tickDataServiceModule
import com.cmoney.backend2.trial.di.trialServiceModule
import com.cmoney.backend2.userbehavior.di.userBehaviorServiceModule
import com.cmoney.backend2.videochannel.di.videoChannelServiceModule
import com.cmoney.backend2.virtualassets.di.virtualAssetsServiceModule
import com.cmoney.backend2.virtualtrading2.di.virtualTrading2ServiceModule
import com.cmoney.backend2.vtwebapi.di.virtualTradeServiceModule
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * 所有Backend服務的Module。
 * 不使[backendServicesModule]的話，也可以個別設定每一個服務的Module定義。
 */
val backendServicesModule: Module = module {
    includes(
        backendBaseModule,
        activityServiceModule,
        additionalInformationRevisitServiceModule,
        authorizationServiceModule,
        billingServiceModule,
        brokerDataTransmissionServiceModule,
        cellphoneServiceModule,
        centralizedImageServiceModule,
        chatServiceModule,
        chipkServiceModule,
        clientConfigurationModule,
        cmtalkServiceModule,
        commonServiceModule,
        commonUseModule,
        crawlSettingServiceModule,
        crmServiceModule,
        customGroupServiceModule,
        customGroup2ServiceModule,
        dataServiceModule,
        dtnoServiceModule,
        emilyServiceModule,
        forumOceanServiceModule,
        frontEndLoggerServiceModule,
        identityProviderServiceModule,
        imageRecognitionServiceModule,
        mediaServiceModule,
        mobileOceanServiceModule,
        noteExtensionServiceModule,
        notesServiceModule,
        notificationServiceModule,
        notification2ServiceModule,
        oceanServiceModule,
        portalServiceModule,
        productProvider,
        profileServiceModule,
        realtimeAfterMarketServiceModule,
        tickDataServiceModule,
        trialServiceModule,
        userBehaviorServiceModule,
        videoChannelServiceModule,
        virtualAssetsServiceModule,
        virtualTradeServiceModule,
        virtualTrading2ServiceModule
    )
}