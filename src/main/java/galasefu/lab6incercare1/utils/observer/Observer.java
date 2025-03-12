package galasefu.lab6incercare1.utils.observer;

import galasefu.lab6incercare1.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}
