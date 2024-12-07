@file:Suppress("UsePropertyAccessSyntax")

package com.flagman.connectify.configurations.logs

import org.springframework.context.annotation.Bean
import org.springframework.web.filter.CommonsRequestLoggingFilter

@Bean
fun requestLoggingFilter(): CommonsRequestLoggingFilter {
    return CommonsRequestLoggingFilter().apply {
        setIncludeClientInfo(true);
        setIncludeQueryString(true);
        setIncludePayload(true);
        setMaxPayloadLength(64000)
    }
}