package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private ImplementacaoUserDeatailsService implementacaoUserDeatailsService;
	
	
	@Override // Configura as solicitações de acesso ao HTTP
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable() // Desativa as configurações de memória do Spring
				.authorizeRequests() // Pertimir restringir acessos
				.antMatchers(HttpMethod.GET, "/").permitAll() // Qualquer usuário acessa a pagina inicial
				.antMatchers(HttpMethod.GET, "/cadastropessoa").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
				.and().formLogin().permitAll() // permite qualquer usuário
			.loginPage("/login")
			.defaultSuccessUrl("/cadastropessoa")
			.failureUrl("/login?error=true")
			.and()
			.logout().logoutSuccessUrl("/login") // Mapeia URL de Logout e invalida usuário autenticado
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		}
	
	
	@Override // Cria autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		auth.userDetailsService(implementacaoUserDeatailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		/*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
		.withUser("humberto")
		.password("$2a$10$OW6zLlkqYLCshyfJoah1uO9NzTk16ZBTLljjXBIK9qZ32BCav1Kki")
		.roles("ADMIN"); */
	}
	
	@Override // Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**");
		
	}
}
