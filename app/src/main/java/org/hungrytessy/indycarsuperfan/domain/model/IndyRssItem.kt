package org.hungrytessy.indycarsuperfan.domain.model

data class IndyRssItem(
    val guid: String?,
    val title: String?,
    val author: String?,
    val link: String?,
    val pubDate: String?,
    val description: String?,
    val content: String?,
    val image: String?,
    val video: String?,
    val sourceName: String?,
    val sourceUrl: String?,
    val categories: List<String>,
)
