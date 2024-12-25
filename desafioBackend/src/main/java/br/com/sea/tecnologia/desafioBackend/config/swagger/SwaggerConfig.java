package br.com.sea.tecnologia.desafioBackend.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
     @Bean
     public OpenAPI customOpenAPI() {
          final String securitySchemeName = "bearerAuth";

          return new OpenAPI()
                  .info(new Info()
                          .title("API Desafio Backend SEA TECNOLOGIA")
                          .version("1.0.0")
                          .description("API com Segurança Customizada, protegida com OAuth2, JWT e Spring Security.\n\n"
                                       + "Repositório GitHub do projeto: [DesafioBackend_SeaTecnologia](https://github.com/gabriellglrs/DesafioBackend_SeaTecnologia)\n\n"
                                       + "**Atenção**: Antes de acessar os endpoints, é necessário realizar a autorização com um token de login.\n\n"
                                       + "### Criar Usuários para Login\n\n" + "Para acessar os endpoints, é necessário autenticar com um dos usuários pré-definidos abaixo:\n\n"
                                       + "**Administrador**:\n"
                                       + "Username: 00000000000\n"
                                       + "Password: 123qwe!@#\n\n"
                                       + "**Usuário Comum**:\n"
                                       + "Username: 11111111111\n"
                                       + "Password: 123qwe123\n\n"
                                       + "_Não se esqueça de fazer o login pelo Postman para obter o token de acesso._"))
                  .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                  .components(new Components()
                          .addSecuritySchemes(securitySchemeName,
                                  new SecurityScheme()
                                          .name(securitySchemeName)
                                          .type(SecurityScheme.Type.HTTP)
                                          .scheme("bearer")
                                          .bearerFormat("JWT"))); // Adiciona suporte para tokens JWT

     }
}
