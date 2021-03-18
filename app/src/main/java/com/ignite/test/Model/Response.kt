package com.ignite.test.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class Response(
	val next: String? = null,
	val previous: Any? = null,
	val count: Int? = null,
	val results: List<ResultsItem?>? = null
)


data class AuthorsItem(
	val name: String? = null,
	val deathYear: Int? = null,
	val birthYear: Int? = null
)


data class Formats(
	val textHtmlCharsetUtf8: String? = null,
	val imageJpeg: String? = null,
	val applicationPdf: String? = null,
	val textPlainCharsetUtf8: String? = null,
	val applicationRdfXml: String? = null,
	val applicationXMobipocketEbook: String? = null,
	val applicationEpubZip: String? = null,
	val applicationZip: String? = null,
	val textPlainCharsetUsAscii: String? = null,
	val textPlainCharsetIso88591: String? = null,
	val textPlain: String? = null,
	val textHtmlCharsetIso88591: String? = null,
	val textHtmlCharsetUsAscii: String? = null,
	val textRtf: String? = null
)


data class ResultsItem(
	val copyright: Boolean? = null,
	val translators: List<Any?>? = null,
	val formats: Formats? = null,
	val languages: List<String?>? = null,
	val mediaType: String? = null,
	val subjects: List<String?>? = null,
	val bookshelves: List<String?>? = null,
	val id: Int? = null,
	val title: String? = null,
	val authors: List<AuthorsItem?>? = null,
	val downloadCount: Int? = null
)


data class TranslatorsItem(
	val name: String? = null,
	val deathYear: Any? = null,
	val birthYear: Any? = null
)
