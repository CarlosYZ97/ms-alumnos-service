package pe.scotiabank.ms.alumnos.service.infrastructure.repository;

import org.reactivestreams.Publisher;
import pe.scotiabank.ms.alumnos.service.infrastructure.repository.entity.AlumnoEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpringDataAlumnoRepositoryFake implements SpringDataAlumnoRepository {

    private final Map<Integer, AlumnoEntity> store = new ConcurrentHashMap<>();


    @Override
    public Flux<AlumnoEntity> findByEstado(Integer estado) {
        return Flux.fromStream(store.values().stream()
                .filter(e -> e.getEstado().equals(estado)));
    }

    @Override
    public <S extends AlumnoEntity> Mono<S> save(S entity) {
        store.put(entity.getId(), entity);
        return Mono.just(entity);
    }

    @Override
    public <S extends AlumnoEntity> Flux<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends AlumnoEntity> Flux<S> saveAll(Publisher<S> entityStream) {
        return null;
    }

    @Override
    public Mono<AlumnoEntity> findById(Integer integer) {
        return null;
    }

    @Override
    public Mono<AlumnoEntity> findById(Publisher<Integer> id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Integer id) {
        return Mono.just(store.containsKey(id));
    }

    @Override
    public Mono<Boolean> existsById(Publisher<Integer> id) {
        return null;
    }

    @Override
    public Flux<AlumnoEntity> findAll() {
        return null;
    }

    @Override
    public Flux<AlumnoEntity> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public Flux<AlumnoEntity> findAllById(Publisher<Integer> idStream) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Integer integer) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher<Integer> id) {
        return null;
    }

    @Override
    public Mono<Void> delete(AlumnoEntity entity) {
        return null;
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends Integer> integers) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends AlumnoEntity> entities) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends AlumnoEntity> entityStream) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }
}
