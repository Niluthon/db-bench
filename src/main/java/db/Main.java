package db;


import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.DeploymentOptions;
import io.vertx.mutiny.core.Vertx;
import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.Map;

public class Main {


  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

//    Uni<Void> startHibernate = Uni.createFrom().deferred(() -> {
//      Persistence
//        .createEntityManagerFactory("postgresql-example")
//        .unwrap(Mutiny.SessionFactory.class);
//
//      return Uni.createFrom().voidItem();
//    });
//
//    startHibernate = vertx.executeBlocking(startHibernate)
//      .onItem().invoke(() -> System.out.println("Hibernate started"))
//        .onFailure().invoke(Throwable::printStackTrace);


    vertx.deployVerticle(MainVerticle.class.getName(), new DeploymentOptions().setInstances(2))
      .subscribe().with(
        ok -> System.out.println("MainVerticle deployed"),
        throwable -> System.out.println("MainVerticle failed to deploy")
      );
  }
}
