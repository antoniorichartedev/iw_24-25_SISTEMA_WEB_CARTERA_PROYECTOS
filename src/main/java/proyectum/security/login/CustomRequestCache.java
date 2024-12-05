package proyectum.security.login;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletResponse;

class CustomRequestCache extends HttpSessionRequestCache {

    public void saveRequest(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response) {
        if (!SecurityUtils.isFrameworkInternalRequest(request)) {
            super.saveRequest(request, (jakarta.servlet.http.HttpServletResponse) response);
        }
    }
}
