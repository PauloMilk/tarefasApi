package com.paulo.tarefas.api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//
//import org.springframework.context.annotation.Bean;
//
//import com.paulo.tarefas.api.entity.PerfilTipo;
//import com.paulo.tarefas.api.entity.Usuario;
//import com.paulo.tarefas.api.repository.UsuarioRepository;
//import com.paulo.tarefas.api.utils.PasswordUtils;

@SpringBootApplication
public class ApiApplication {
	
//	@Autowired
//	private UsuarioRepository usuarioRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
//	
//	@Bean
//	public CommandLineRunner commandLineRunner() {
//		return args -> {
//			
//			if(usuarioRepository.findAll().isEmpty()) {
//				Usuario usuario = new Usuario();
//				usuario.setEmail("pauloleitecosta14@gmail.com");
//				usuario.setPerfil(PerfilTipo.ROLE_USUARIO);
//				usuario.setSenha(PasswordUtils.gerarBCrypt("123456"));
//				usuario.setAtivo(false);
//				usuarioRepository.save(usuario);
//				
//				Usuario admin = new Usuario();
//				admin.setEmail("admin@gmail.com");
//				admin.setPerfil(PerfilTipo.ROLE_ADMIN);
//				admin.setSenha(PasswordUtils.gerarBCrypt("123456"));
//				admin.setAtivo(false);
//				usuarioRepository.save(admin);
//			}
//		};
//	}

}
