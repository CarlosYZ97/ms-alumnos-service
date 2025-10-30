package pe.scotiabank.ms.alumnos.service.infrastructure.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class AuditingAspect {

    @Before("execution(* org.springframework.data.repository.reactive.ReactiveCrudRepository+.save(..))")
    public void beforeSave(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length == 0) return;

        Object entity = args[0];

        try {
            var clazz = entity.getClass();

            var updatedAtField = clazz.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(entity, LocalDateTime.now());

            var idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            Object idValue = idField.get(entity);

            if (idValue == null) {
                var createdAtField = clazz.getDeclaredField("createdAt");
                createdAtField.setAccessible(true);
                createdAtField.set(entity, LocalDateTime.now());
            }

            log.debug("[AuditingAspect] Auditoría aplicada a {}", clazz.getSimpleName());

        } catch (NoSuchFieldException e) {
        } catch (Exception e) {
            log.error("[AuditingAspect] Error aplicando auditoría: {}", e.getMessage());
        }
    }

}
