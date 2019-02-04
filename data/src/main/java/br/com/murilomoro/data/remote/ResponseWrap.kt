package br.com.murilomoro.data.remote

data class ResponseWrap<T>(val results: List<T> = arrayListOf())