package com.flagman.connectify.configurations.websocket

import com.flagman.connectify.jwt.JWT
import com.flagman.connectify.models.User
import com.flagman.connectify.services.UserService
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfiguration(
    private val jwt: JWT,
    private val userService: UserService,
) : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/topic")
        config.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry
            .addEndpoint("/chat")
            .setAllowedOriginPatterns("*")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(object : ChannelInterceptor {
            override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
                var accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)

                if (StompCommand.CONNECT == accessor?.command) {
                    var authorizationHeader = accessor.getFirstNativeHeader("authorization")
                    var token = authorizationHeader?.substring(7)

                    var username = jwt.getUsernameFromToken(token.toString())
                    var userDetails = userService.getUserByUsername(username)
                    userService.setUserOnline(username)
                    var usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, null)
                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken

                    accessor.setUser(usernamePasswordAuthenticationToken)
                } else if (StompCommand.DISCONNECT == accessor?.command) {
                    var username = ((accessor.user as UsernamePasswordAuthenticationToken).principal as User).username
                    if (username != null) {
                        userService.setUserOffline(username)
                    }
                }

                return message
            }
        })
    }
}