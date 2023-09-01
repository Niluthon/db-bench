package db;

import entities.UserEntity;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.http.HttpServer;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import io.vertx.mutiny.ext.web.handler.BodyHandler;
import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.List;

public class MainVerticle extends AbstractVerticle {


  private Mutiny.SessionFactory emf;

  @Override
  public Uni<Void> asyncStart() {
    Uni<Void> startHibernate = Uni.createFrom().deferred(() -> {
      emf = Persistence
        .createEntityManagerFactory("postgresql-example")
        .unwrap(Mutiny.SessionFactory.class);
      return Uni.createFrom().voidItem();
    });

    startHibernate = vertx.executeBlocking(startHibernate);

    Router router = Router.router(vertx);

    BodyHandler bodyHandler = BodyHandler.create();
    router.post().handler(bodyHandler::handle);

    router.get("/user/:id").respond(this::findUser);
    router.get("/users").respond(this::list);
    router.post("/user").respond(this::insertUser)
        .failureHandler((rc) -> {
          rc.failure().printStackTrace();
        });
    router.put("/user/:id").respond(this::update);

    Uni<HttpServer> startHttpServer = vertx.createHttpServer()
      .requestHandler(router::handle)
      .exceptionHandler(Throwable::printStackTrace)
      .listen(8080)
      .onItem().invoke(() -> System.out.println("HTTP server started on port 8080"));

    return Uni.combine().all().unis(startHibernate, startHttpServer).discardItems();
  }


  private Uni<UserEntity> findUser(RoutingContext routingContext) {
    Long id = Long.valueOf(routingContext.pathParam("id"));

    return emf.withSession(session -> session.find(UserEntity.class, id));
  }


  private Uni<UserEntity> update(RoutingContext rc) {
    Long id = Long.valueOf(rc.pathParam("id"));

    return emf.withSession(session -> session.find(UserEntity.class, id))
      .onItem().invoke(userEntity -> {
        JsonObject body = rc.body().asJsonObject();

        String email = body.getString("email");
        String name = body.getString("name");

        userEntity.setEmail(email);
        userEntity.setName(name);
      });
  }

  private Uni<List<UserEntity>> list(RoutingContext rc) {
    return emf.withSession(session -> session.createQuery("from UserEntity", UserEntity.class).getResultList());
  }

  private Uni<Void> insertUser(RoutingContext rc) {
    JsonObject body = rc.body().asJsonObject();

    String email = body.getString("email");
    String name = body.getString("name");

    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(email);
    userEntity.setName(name);

    return emf.withSession(session -> session.persist(userEntity));
  }

}
