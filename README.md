# Sincronizador Arquivos Socket
Sincronizador de Arquivos entre máquinas usando Socket

## Operações necessárias no Postgres
```
CREATE ROLE sincronizador LOGIN
  ENCRYPTED PASSWORD 'md55ac55ef7c15d26130a503fe5ab6f9552'
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;
```

```
CREATE DATABASE sincronizador
  WITH OWNER = sincronizador
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'pt_BR.UTF-8'
       LC_CTYPE = 'pt_BR.UTF-8'
       CONNECTION LIMIT = -1;
```

```
CREATE TABLE usuario
(
  id_usuario integer NOT NULL DEFAULT nextval('"Usuario_id_usuario_seq"'::regclass),
  nome character varying NOT NULL,
  email character varying NOT NULL,
  senha character varying NOT NULL,
  data_cadastrado date NOT NULL,
  CONSTRAINT "Usuario_pkey" PRIMARY KEY (id_usuario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario
  OWNER TO sincronizador;
```

```
CREATE TABLE login_usuario
(
  id_login_usuario serial NOT NULL,
  id_usuario integer,
  ip character varying,
  ultima_entrada timestamp without time zone,
  mac character varying,
  CONSTRAINT login_usuario_pkey PRIMARY KEY (id_login_usuario),
  CONSTRAINT pk_usuario FOREIGN KEY (id_usuario)
      REFERENCES usuario (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE login_usuario
  OWNER TO sincronizador;

-- Index: fki_pk_usuario

-- DROP INDEX fki_pk_usuario;

CREATE INDEX fki_pk_usuario
  ON login_usuario
  USING btree
  (id_usuario);
```

## Informações para o JDBC
- Driver: org.postgresql.Driver
- Link: jdbc:postgresql://localhost/sincronizador
- Login: sincronizador
- Senha: sync123
