package br.com.murilomoro.domain.exception

/**
 * Created by Murilo Moro on 31/01/19.
 */
class DefaultException(
    override val message: String = "Erro Inesperado"
) : Exception()