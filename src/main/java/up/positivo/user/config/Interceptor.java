package up.positivo.user.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import up.positivo.user.security.JwtTokenUtil;

@Component
public class Interceptor implements HandlerInterceptor {

	@Value("${apikey}")
	private String apikey;

	@Value("${jwt.secret}")
	private String secret;

	private String publicEndPoint = "/login";

	private String validationHeader = "Validation";

	@Autowired
	JwtTokenUtil jtwTokenUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String requestApikey = request.getHeader("apikey");
		String requestEndPoint = request.getRequestURI();

		// Check apikey
		if (apikey.equals(requestApikey)) {

			// Check public route
			if (publicEndPoint.equals(requestEndPoint)) {
				response.addHeader(validationHeader, "Ok");
				return true;
			}

			// Get received token
			String requestToken = request.getHeader("token");
			
			// Check token exists
			if (requestToken == null || "".equals(requestToken)) {

				response.addHeader(validationHeader, "Token is required");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

				return false;
			}

			// Check token is valid
			boolean validateToken = jtwTokenUtil.validateToken(requestToken);

			if (!validateToken) {

				response.addHeader(validationHeader, "Invalid token");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

				return false;
			}

			// Request is valid
			response.addHeader(validationHeader, "Ok");
			return true;

		}

		response.addHeader(validationHeader, "Invalid ApiKey");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

		return false;
	}

}