package com.github.airbnb.config.security;


import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MatchersSecurityConfiguration {

    private HttpMethodResourceAntMatchers matchers;

    /**
     * Returns all http matchers
     * @return
     */
    public HttpMethodResourceAntMatchers getMatchers() {
        if (matchers == null) {
            matchers = new HttpMethodResourceAntMatchers();
            matchers.antMatchers(HttpMethod.GET, "/user/info").hasAnyRole("admin", "user")
                    .antMatchers(HttpMethod.POST, "/user/withdraw").hasAnyRole("user")
                    ;
        }
        return matchers;
    }
}
