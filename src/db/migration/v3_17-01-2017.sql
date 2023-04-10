
CREATE TABLE usuario (
    id integer NOT NULL,
    login character varying(100) NOT NULL,
    senha character varying(100) NOT NULL,
    ativo boolean
);


ALTER TABLE public.usuario OWNER TO postgres;


CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO postgres;


ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


CREATE TABLE usuarioacesso (
    usuario bigint NOT NULL,
    acesso character varying(70)
);


ALTER TABLE public.usuarioacesso OWNER TO postgres;



ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);



INSERT INTO usuario (id, login, senha, ativo) VALUES (1, 'alex', '123', true);



SELECT pg_catalog.setval('usuario_id_seq', 1, false);


INSERT INTO usuarioacesso (usuario, acesso) VALUES (1, 'ADMIN');

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);



ALTER TABLE ONLY usuarioacesso
    ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario) REFERENCES usuario(id);

