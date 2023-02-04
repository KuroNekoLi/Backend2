# Backend2 Benchmark

## 建立方式與步驟

- 請在預測試方法相同的 package 下建立 XXXBenchmark 檔案

```
// 方法所在的檔案 
backend2/src/main/java/com/cmoney/backend2/base/extension/DtnoExtension.kt

// benchmark所在的檔案
benchmark/src/androidTest/java/com/cmoney/backend2/base/extension/DtnoExtensionKtBenchmark.kt
```

- 測試方法要能清楚知道測試的方法名
- 測試方法要寫在 `BenchmarkRule.measureRepeated` 中
  
  - 不需要計時的部分可以寫在 `BenchmarkRule.Scope.runWithTimingDisabled` 中

```kotlin
benchmarkRule.measureRepeated {
    // 準備參數(不須計時的部分)
    val arg = runWithTimingDisabled {
        // generate the arguments.
    }
    // 要計時的內容
    val result = testMethod(arg = arg)
}
```

- 結果會輸出在 build/outputs 中
- 其中

  - androidTest_result: 可以用 IDE工具列 RUN > Import Tests from Files
  - connected_android_test_additional_output: 其中有 xxx.json 可以看到詳細的報告

```json
{
    "context": "裝置資訊",
    "benchmarks": [
        {
            "name": "toListOfType",
            "params": {},
            "className": "com.cmoney.backend2.base.extension.DtnoExtensionKtBenchmark",
            "totalRunTimeNs": 13576377446,
            "metrics": {
                "timeNs": {
                    "minimum": 2007703.2,
                    "maximum": 2363521.15,
                    "median": 2209023.775,
                    "runs": [
                        2229312.6,
                        2363521.15,
                        2025789.25,
                        2159596.55,
                        2272992.45
                    ]
                },
                "allocationCount": {
                    "minimum": 16485.0,
                    "maximum": 16485.15,
                    "median": 16485.1,
                    "runs": [
                        16485.15,
                        16485.0,
                        16485.05,
                        16485.1,
                        16485.1
                    ]
                }
            },
            "sampledMetrics": {},
            "warmupIterations": 1557,
            "repeatIterations": 20,
            "thermalThrottleSleepSeconds": 0
        },
        {
            "name": "toListOfSomething",
            "params": {},
            "className": "com.cmoney.backend2.base.extension.DtnoExtensionKtBenchmark",
            "totalRunTimeNs": 13775851372,
            "metrics": {
                "timeNs": {
                    "minimum": 9200606.625,
                    "maximum": 1.0543464375E7,
                    "median": 1.016477E7,
                    "runs": [
                        1.01677745E7,
                        1.02621495E7,
                        1.0233894125E7,
                        1.0310163875E7,
                        1.0055964625E7
                    ]
                },
                "allocationCount": {
                    "minimum": 65290.0,
                    "maximum": 65290.375,
                    "median": 65290.25,
                    "runs": [
                        65290.375,
                        65290.375,
                        65290.25,
                        65290.25,
                        65290.0
                    ]
                }
            },
            "sampledMetrics": {},
            "warmupIterations": 617,
            "repeatIterations": 8,
            "thermalThrottleSleepSeconds": 0
        }
    ]
}
```

### 解讀報告

- 目前關注 metrics 中兩項 `timeNs`、`allocationCount` 的統計資料

  - timeNs: 方法運行時間
  
  ```kotlin
  // 計算來源
  System.nanoTime()
  ```

  - allocationCount: 不太確定，推測是分配的資源數量
  
  ```kotlin
  // 計算來源
  Debug.startAllocCounting()
  Debug.stopAllocCounting()
  Debug.getGlobalAllocCount()
  ```

## 參考資料

- [Overview](https://developer.android.com/topic/performance/benchmarking/microbenchmark-overview)
- [Write a benchmark](https://developer.android.com/topic/performance/benchmarking/microbenchmark-write)
- [Benchmark-Common: MetricCapture](https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-main/benchmark/benchmark-common/src/main/java/androidx/benchmark/MetricCapture.kt)