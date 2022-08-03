package triatlon.repository.interfete;


import triatlon.domain.Entity;

public interface Repository<ID,E extends Entity> {
    Iterable<E>findAll();
    void add(E entity);
    void update(long id,E entity);
    void delete (ID id);
}
