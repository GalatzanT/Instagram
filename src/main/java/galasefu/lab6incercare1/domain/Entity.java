package galasefu.lab6incercare1.domain;

import java.io.PushbackInputStream;

import java.io.Serializable;
import java.util.Objects;

public class Entity<ID> implements Serializable {


    private static final long serialVersionUID = 7331115341259248461L;
    protected ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
