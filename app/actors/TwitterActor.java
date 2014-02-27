package actors;

import akka.actor.UntypedActor;

public class TwitterActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Search) {
            // use a pool of actors
            getSender().tell(new Results(), getSelf());
        }
    }

    public static class Search {
        
        private final String query;
        
        public Search(String query) {
            this.query = query;
        }
        
    }
    
    public static class Results {
        
    }
    
}