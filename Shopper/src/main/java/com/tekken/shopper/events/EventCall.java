package com.tekken.shopper.events;

import java.util.ArrayList;
import java.util.List;

public class EventCall {

    private List<Event> registeredEvent = new ArrayList<Event>();

    public EventCall(List<Event> registeredEvent) {
        call(ItemBuyingEvent.class);
    }

    public void call(Object object){
        //registeredEvent.stream().filter(a -> a.getClass().isInstance(object)).forEach(event -> ((object) event).);
    }

}
