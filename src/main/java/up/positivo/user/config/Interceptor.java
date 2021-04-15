package up.positivo.user.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

import up.positivo.user.entities.Usuario;
import up.positivo.user.security.JwtTokenUtil;

@Component
public class Interceptor implements HandlerInterceptor {

	@Value("${apikey}")
	private String apikey;

	@Value("${jwt.secret}")
	private String secret;

	private String[] PUBLIC = {
			// -- Swagger UI v2
			"/v2/api-docs", 
			"/configuration/ui",
			"/configuration/security", 
			"/login",
			"/csrf",
			"/error",
	};

	private String validationHeader = "Validation";

	@Autowired
	JwtTokenUtil jtwTokenUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String requestApikey = request.getHeader("apikey");
		String requestEndPoint = request.getRequestURI();
		
		boolean contains = Arrays.stream(PUBLIC).anyMatch(requestEndPoint::equals);
		
		if(
				requestEndPoint.contains("/webjars") || 
				requestEndPoint.contains("/swagger-resources") || 
				requestEndPoint.contains("/swagger-ui.html") || 
				contains) {
			response.addHeader(validationHeader, "Ok");
			return true;
		}else {
			System.out.println(">>>>>> Not Public " + requestEndPoint);
		}
		
		// Check apikey
		if (apikey.equals(requestApikey)) {

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
			
			// Get token data
			Usuario userData = jtwTokenUtil.getUserData(requestToken);
			
			request.setAttribute("usuarioNivel", userData.getNivel());
			request.setAttribute("usuarioNome", userData.getNome());
			
			// Request is valid
			response.addHeader(validationHeader, "Ok");
			return true;

		}

		response.addHeader(validationHeader, "Invalid ApiKey");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

		return false;
	}

}