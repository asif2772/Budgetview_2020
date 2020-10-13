package security

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.security.authentication.AuthenticationTrustResolver

@Secured('permitAll')
class LoginController {
    AuthenticationTrustResolver authenticationTrustResolver
    SpringSecurityService springSecurityService

    def index() {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: conf.successHandler.defaultTargetUrl
        }
        else {
            redirect action: 'auth', params: params
        }

    }
    def auth () {

        def conf = getConf()
        if (springSecurityService.isLoggedIn()) {
            redirect uri: conf.successHandler.defaultTargetUrl
            return
        }

        String postUrl = request.contextPath + conf.apf.filterProcessesUrl
        render view: 'auth', model: [postUrl: postUrl,
                                     usernameParameter: conf.apf.usernameParameter,
                                     passwordParameter: conf.apf.passwordParameter]
    }

    def loginSuccess(){

        redirect(controller: 'dashboard', action: 'index')
       /* if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
            redirect(controller: 'registerUser', action: 'admin')
        } else {
            redirect(controller: 'registerUser', action: 'index')
        }*/

    }

    protected ConfigObject getConf() {
        SpringSecurityUtils.securityConfig
    }
}

