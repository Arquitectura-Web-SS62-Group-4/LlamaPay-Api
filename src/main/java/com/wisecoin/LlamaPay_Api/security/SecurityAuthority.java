package com.wisecoin.LlamaPay_Api.security;

import com.wisecoin.LlamaPay_Api.entities.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private Authority authority;

    @Override
    public String getAuthority() {
        return authority.getName();
    }
}
