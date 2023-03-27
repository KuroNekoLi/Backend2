package com.cmoney.backend2.base.extension

import android.content.Context
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmoney.backend2.base.extension.data.CommodityInformation
import com.cmoney.backend2.base.extension.data.UsaCompanyInformation
import com.cmoney.backend2.base.model.response.dtno.DtnoData
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStreamReader

/**
 * Benchmark, which will execute on an Android device.
 *
 * The body of [BenchmarkRule.measureRepeated] is measured in a loop, and Studio will
 * output the result. Modify your code to see how it affects performance.
 */
@RunWith(AndroidJUnit4::class)
class DtnoExtensionKtBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val gson = GsonBuilder().serializeNulls().setLenient().create()

    @Test
    fun toListOfSomething_23445586() {
        benchmarkRule.measureRepeated {
            val dtnoData = runWithTimingDisabled {
                context.assets.open("dtno_23445586.json")
                    .use { inputStream ->
                        gson.fromJson<DtnoData>(
                            JsonReader(InputStreamReader(inputStream)),
                            DtnoData::class.java
                        )
                    }
            }
            dtnoData.toListOfType<UsaCompanyInformation>(gson = gson)
        }
    }

    @Test
    fun toListOfSomething_4210983() {
        benchmarkRule.measureRepeated {
            val dtnoData = runWithTimingDisabled {
                context.assets.open("dtno_4210983.json")
                    .use { inputStream ->
                        gson.fromJson<DtnoData>(
                            JsonReader(InputStreamReader(inputStream)),
                            DtnoData::class.java
                        )
                    }
            }
            dtnoData.toListOfType<CommodityInformation>(gson = gson)
        }
    }

    @Test
    fun toListOfType_23445586() {
        benchmarkRule.measureRepeated {
            val dtnoData = runWithTimingDisabled {
                context.assets.open("dtno_23445586.json")
                    .use { inputStream ->
                        gson.fromJson<DtnoData>(
                            JsonReader(InputStreamReader(inputStream)),
                            DtnoData::class.java
                        )
                    }
            }
            dtnoData.toListOfType<UsaCompanyInformation>(gson)
        }
    }

    @Test
    fun toListOfType_4210983() {
        benchmarkRule.measureRepeated {
            val dtnoData = runWithTimingDisabled {
                context.assets.open("dtno_4210983.json")
                    .use { inputStream ->
                        gson.fromJson<DtnoData>(
                            JsonReader(InputStreamReader(inputStream)),
                            DtnoData::class.java
                        )
                    }
            }
            dtnoData.toListOfType<CommodityInformation>(gson)
        }
    }
}