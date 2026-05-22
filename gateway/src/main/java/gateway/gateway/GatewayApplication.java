package gateway.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()

				//enrutado microservicio Usuarios 8502
				.route("usuarios", r -> r.path("/usuarios/**")
						.uri("lb://USUARIOS"))

				//enrutado microservicio Reservas 8501
				.route("reservas", r -> r.path("/reservas/**")
						.uri("lb://RESERVAS"))

				//Comentarios API (8503) - Ruta exacta POST para los datos de GraphQL
				.route("comentarios-api", r -> r.path("/comentarios")
						.uri("lb://COMENTARIOS"))

				//Comentarios UI (8503) - Interfaz visual GraphiQL y sus recursos internos
				.route("comentarios-ui", r -> r.path("/graphiql", "/graphiql/**")
						.uri("lb://COMENTARIOS"))

				.build();
	}
}
