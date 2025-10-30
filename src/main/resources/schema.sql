CREATE TABLE IF NOT EXISTS alumnos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    estado INT NOT NULL,
    edad INT NOT NULL,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
