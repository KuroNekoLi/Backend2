import groovy.namespace.QName
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.2" apply false
    id("com.android.library") version "7.2.2" apply false
    id("org.jetbrains.kotlin.android") version Versions.KOTLIN apply false
    id("org.jetbrains.kotlinx.kover") version "0.6.0"
    id("androidx.benchmark") version Versions.ANDROID_BENCHMARK apply false
}

tasks.register<Delete>("clean") {
    delete(buildDir)
}

koverMerged {
    enable()

    filters {
        projects {
            excludes += listOf(":app", ":benchmark")
        }
    }
}

afterEvaluate {
    tasks.getByName("koverMergedXmlReport") {
        doLast {
            val report = file("${buildDir}/reports/kover/merged/xml/report.xml")
            printCoverage(report)
        }
    }
}

fun printCoverage(xml: File) {
    if (!xml.exists()) {
        logger.quiet("xml $xml not exist")
        return
    }

    val parser = groovy.xml.XmlParser()
    parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
    parser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
    val results = parser.parse(xml)

    fun percentage(node: groovy.util.Node?): Double {
        node ?: return 0.0
        val covered = "${node.get("@covered")}".toDoubleOrNull() ?: 0.0
        val missed = "${node.get("@missed")}".toDoubleOrNull() ?: return 0.0
        return (covered / (covered + missed)) * 100
    }

    val counters: groovy.util.NodeList = results.getAt(QName("counter"))

    fun findNode(nodeList: groovy.util.NodeList, nodeAttributeName: String, noteAttributeValue: String): groovy.util.Node? {
        nodeList.forEach { node ->
            if (node !is groovy.util.Node) {
                return@forEach
            }
            if ((node.get(nodeAttributeName) as? String) == noteAttributeValue) {
                return node
            }
        }
        return null
    }

    val metrics = mapOf(
        "instruction" to  percentage(findNode(counters, "@type","INSTRUCTION")),
        "branch" to  percentage(findNode(counters, "@type","BRANCH")),
        "line" to  percentage(findNode(counters, "@type","LINE")),
        "method" to  percentage(findNode(counters, "@type","METHOD")),
        "class" to  percentage(findNode(counters, "@type","CLASS"))
    )

    logger.quiet("----- Code Coverage ----------")
    metrics.forEach { (key, value) -> logger.quiet(String.format(" - %-11s: %6.2f%%", key, value)) }
    logger.quiet("------------------------------")
}