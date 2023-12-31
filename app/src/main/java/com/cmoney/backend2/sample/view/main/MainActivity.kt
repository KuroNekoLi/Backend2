package com.cmoney.backend2.sample.view.main

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cmoney.backend2.sample.R
import com.cmoney.backend2.sample.databinding.ActivityMainBinding
import com.cmoney.backend2.sample.extension.lockWindows
import com.cmoney.backend2.sample.extension.toast
import com.cmoney.backend2.sample.extension.unlockWindows
import com.cmoney.backend2.sample.servicecase.ServiceCase
import com.cmoney.backend2.sample.view.main.data.LoginEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        viewModel.error.observe(this) {
            toast(it)
        }
        viewModel.windowsLock.observe(this) {
            it.fold({
                lockWindows()
            }, {
                unlockWindows()
            })
        }
        viewModel.loginEvent.observe(this) {
            when (it) {
                LoginEvent.Success -> {
                    toast("登入成功")
                    apiTest()
                }
            }
        }
    }

    private fun initView() {
        val apps = resources.getStringArray(R.array.app_list)
        binding.appIdSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setAppId(apps[position].split(",")[0].toInt())
                viewModel.setClientId(apps[position].split(",")[2])
            }
        }
        val urls = resources.getStringArray(R.array.domain_url)
        binding.domainSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setDomain(urls[position])
            }


        }
        binding.loginButton.setOnClickListener {
            loginByEmail()
        }
    }

    private fun loginByEmail() {
        val account = binding.accountEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        viewModel.login(account, password)
    }

    private fun apiTest() {
        lifecycleScope.launch {
            val deferredList = listOf<ServiceCase>(
                // 要測試記得取消註解
//                ActivityServiceCase(),
//                AdditionalInformationRevisitTestCase(),
//                AuthorizationServiceCase(),
//                BillingServiceCase()
//                BrokerDataTransmissionServiceCase(),
//                CellphoneServiceCase(),
//                CentralizedImageServiceCase(),
//                ChatRoomServiceCase(),
//                ChipKServiceCase(),
//                ClientConfigurationServiceCase(),
//                CMTalkServiceCase(),
//                CommonServiceCase(this@MainActivity),
//                CommonUseServiceCase(),
//                CrawlSettingServiceCase(),
//                CrmServiceCase(),
//                CustomGroupServiceCase(),
//                CustomGroup2ServiceCase(),
//                DataServiceCase(),
//                EmilyServiceCase(),
//                ForumOceanServiceCase(),
//                FrontEndLoggerServiceCase(),
//                IdentityProviderServiceCase(),
//                ImageRecognitionServiceCase()
//                MediaServiceCase(),
//                MobileOceanServiceCase(),
//                NoteExtensionServiceCase()
//                NotesServiceCase(),
//                NotificationServiceCase(),
//                Notification2ServiceCase(),
//                OceanServiceCase(),
//                PortalServiceCase(),
//                ProductDataProviderServiceCase(),
//                ProfileServiceCase(),
//                RealTimeAfterMarketServiceCase(),
//                TickDataServiceCase(),
//                TrialServiceCase(),
//                UserBehaviorServiceCase(),
//                VideoChannelServiceCase(),
//                VirtualAssetsServiceCase(),
//                VirtualTradeServiceCase(),
//                VirtualTrading2ServiceCase()
            ).map { serviceCase ->
                async {
                    serviceCase.testAll()
                }
            }
            deferredList.awaitAll()
        }
    }
}
