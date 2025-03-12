package galasefu.lab6incercare1.utils.observer;

import galasefu.lab6incercare1.utils.events.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);
    void deleteObserver(Observer<E> e);
    void notifyObeservers(E t);
}
