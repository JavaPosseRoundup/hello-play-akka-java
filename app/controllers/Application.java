package controllers;

import actors.TwitterActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Function;
import akka.routing.FromConfig;
import play.libs.Akka;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import akka.pattern.Patterns;
import scala.Function1;
import scala.concurrent.Future;
import scala.runtime.AbstractFunction1;

public class Application extends Controller {
    
    static final ActorRef twitterPool = Akka.system().actorOf(
            Props.create(TwitterActor.class).withRouter(
                    new FromConfig()).withDispatcher("my-dispatcher"), "twitterpool");
    
    public static Result index() {
        return ok(views.html.index.render("Hello Play Framework"));
    }
    
    public static F.Promise<Result> searchTwitter(String query) {
        
        Future<Object> twitterResultsFuture = Patterns.ask(twitterPool, new TwitterActor.Search(query), 10000);
        
        Future<Result> resultFuture = twitterResultsFuture.map(new AbstractFunction1<Object, Result>() {
            public Result apply(Object twitterResults) {
                
                return ok("hello");
            }
        }, Akka.system().dispatcher());

        return F.Promise.wrap(resultFuture);
    }
    
}
