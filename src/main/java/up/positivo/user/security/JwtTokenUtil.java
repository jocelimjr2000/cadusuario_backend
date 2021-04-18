package up.positivo.user.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import up.positivo.user.entities.Usuario;
import up.positivo.user.repositories.SessaoRepository;

@Component
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = -2550185165626007488L;
	public static final long JWT_TOKEN_VALIDITY = 500 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;
	
	@Autowired
	SessaoRepository sessaoRepository;
	
	/**
	 * Generate Token
	 * 
	 * @param token
	 * @return
	 */
	public String generateToken(Usuario usuario) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userData", usuario);
		
		return doGenerateToken(claims, usuario.getNome());
	}
	
	/**
	 * Validate Token
	 * 
	 * @param token
	 * @param userToken
	 * @return
	 */
	public Boolean validateToken(String token) {
		if(sessaoRepository.existsByToken(token) && !isTokenExpired(token)) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * Private Methods
	 */
	
	/**
	 * Create token
	 * 
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts
					.builder()
					.setClaims(claims)
					.setSubject(subject)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
					.signWith(SignatureAlgorithm.HS512, secret)
					.compact();
	}

	/**
	 * Check if the token is expired
	 * 
	 * @param token
	 * @return
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	/**
	 * Get UserName Token
	 * 
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	/**
	 * Get Expiration Date Token
	 * 
	 * @param token
	 * @return
	 */
	private Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	/**
	 * User data
	 * 
	 * @param token
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Usuario getUserData(String token) {
		Object claims = getAllClaimsFromToken(token).get("userData");
		
		Map<String,?> map = (Map<String, ?>) claims;

		Usuario usuario = new Usuario();
		usuario.setCpf((String) map.get("cpf"));
		usuario.setNome((String) map.get("nome"));
		usuario.setEmail((String) map.get("email"));
		usuario.setAprovado((String) map.get("aprovado"));
		usuario.setNivel((int) map.get("nivel"));
		
		return usuario;
	}
	
	/**
	 * Get data
	 * 
	 * @param token
	 * @return
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	/**
	 * Get all data
	 * 
	 * @param token
	 * @return
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts
					.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
	}
	
}