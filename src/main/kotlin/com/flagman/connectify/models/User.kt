package com.flagman.connectify.models

import jakarta.persistence.*
import lombok.Getter
import lombok.Setter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

@Entity(name = "Users")
@Getter
@Setter
final class User(
    var username: String? = null,
    var password: String? = null,
    var email: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long? = null

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    private var roles: Set<Role> = HashSet()

    constructor(user: User) : this(user.username, user.password, user.email) {
        this.username = username
        this.password = password
        this.email = email
    }
}