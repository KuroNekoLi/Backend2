package com.cmoney.backend2.base.model.calladapter

import com.cmoney.backend2.activity.service.ActivityService
import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitService
import com.cmoney.backend2.authorization.service.AuthorizationService
import com.cmoney.backend2.billing.service.BillingService
import com.cmoney.backend2.brokerdatatransmission.service.BrokerDataTransmissionService
import com.cmoney.backend2.cellphone.service.CellphoneService
import com.cmoney.backend2.centralizedimage.service.CentralizedImageService
import com.cmoney.backend2.chipk.service.ChipKService
import com.cmoney.backend2.clientconfiguration.service.ClientConfigurationService
import com.cmoney.backend2.cmtalk.service.CMTalkService
import com.cmoney.backend2.common.service.CommonService
import com.cmoney.backend2.commonuse.service.CommonUseService
import com.cmoney.backend2.crawlsetting.service.CrawlSettingService
import com.cmoney.backend2.customgroup.service.CustomGroupService
import com.cmoney.backend2.customgroup2.service.CustomGroup2Service
import com.cmoney.backend2.data.service.DataService
import com.cmoney.backend2.emilystock.service.EmilyService
import com.cmoney.backend2.forumocean.service.ForumOceanService
import com.cmoney.backend2.frontendlogger.service.FrontEndLoggerService
import com.cmoney.backend2.identityprovider.service.IdentityProviderService
import com.cmoney.backend2.imagerecognition.service.ImageRecognitionService
import com.cmoney.backend2.media.service.MediaService
import com.cmoney.backend2.mobileocean.service.MobileOceanService
import com.cmoney.backend2.note_extension.service.NoteExtensionService
import com.cmoney.backend2.notes.service.NotesService
import com.cmoney.backend2.notification.service.NotificationService
import com.cmoney.backend2.notification2.service.Notification2Service
import com.cmoney.backend2.ocean.service.OceanService
import com.cmoney.backend2.portal.service.PortalService
import com.cmoney.backend2.profile.service.ProfileService
import com.cmoney.backend2.realtimeaftermarket.service.RealTimeAfterMarketService
import com.cmoney.backend2.tickdata.service.TickDataService
import com.cmoney.backend2.trial.service.TrialService
import com.cmoney.backend2.videochannel.service.VideoChannelService
import com.cmoney.backend2.virtualassets.service.VirtualAssetsService
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Service
import com.cmoney.backend2.vtwebapi.service.VirtualTradeService
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.reflect.KClass
import kotlin.reflect.full.memberFunctions

@RunWith(Parameterized::class)
class RecordApiTest(
    private val service: KClass<*>
) {

    @Test
    fun checkAllAbstractMethodAnnotationWithRecordApi() {
        service.memberFunctions.forEach { function ->
            if (!function.isAbstract) {
                return@forEach
            }
            val hasRecordApiAnnotation = function.annotations.any { annotation ->
                annotation is RecordApi
            }
            Truth.assertThat(hasRecordApiAnnotation).isTrue()
        }
    }

    companion object {

        @JvmStatic
        @Parameterized.Parameters(name = "Check {0}")
        fun getServices(): Iterable<Array<Any?>> {
            return listOf(
                arrayOf<Any?>(ActivityService::class),
                arrayOf<Any?>(AdditionalInformationRevisitService::class),
                arrayOf<Any?>(AuthorizationService::class),
                arrayOf<Any?>(BillingService::class),
                arrayOf<Any?>(CellphoneService::class),
                arrayOf<Any?>(ChipKService::class),
                arrayOf<Any?>(CMTalkService::class),
                arrayOf<Any?>(CommonService::class),
                arrayOf<Any?>(CommonUseService::class),
                arrayOf<Any?>(CrawlSettingService::class),
                arrayOf<Any?>(CustomGroupService::class),
                arrayOf<Any?>(CustomGroup2Service::class),
                arrayOf<Any?>(DataService::class),
                arrayOf<Any?>(EmilyService::class),
                arrayOf<Any?>(ForumOceanService::class),
                arrayOf<Any?>(IdentityProviderService::class),
                arrayOf<Any?>(CentralizedImageService::class),
                arrayOf<Any?>(MediaService::class),
                arrayOf<Any?>(MobileOceanService::class),
                arrayOf<Any?>(NoteExtensionService::class),
                arrayOf<Any?>(NotesService::class),
                arrayOf<Any?>(NotificationService::class),
                arrayOf<Any?>(Notification2Service::class),
                arrayOf<Any?>(OceanService::class),
                arrayOf<Any?>(PortalService::class),
                arrayOf<Any?>(ProfileService::class),
                arrayOf<Any?>(RealTimeAfterMarketService::class),
                arrayOf<Any?>(TickDataService::class),
                arrayOf<Any?>(TrialService::class),
                arrayOf<Any?>(VideoChannelService::class),
                arrayOf<Any?>(VirtualAssetsService::class),
                arrayOf<Any?>(ClientConfigurationService::class),
                arrayOf<Any?>(ImageRecognitionService::class),
                arrayOf<Any?>(BrokerDataTransmissionService::class),
                arrayOf<Any?>(FrontEndLoggerService::class),
                arrayOf<Any?>(VirtualTradeService::class),
                arrayOf<Any?>(VirtualTrading2Service::class)
            )
        }
    }
}