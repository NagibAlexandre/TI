-- Criação da tabela Pokemon
CREATE TABLE public.pokemon (
    id integer DEFAULT nextval('public."id-pokemon"'::regclass) NOT NULL,
    nome text,
    duracao float,
    genero text,
    datafabricacao timestamp without time zone,
    datalancamento date
);

-- Definindo a chave primária
ALTER TABLE public.pokemon ADD CONSTRAINT pokemon_pkey PRIMARY KEY (id);

-- Sequência para gerar IDs automaticamente
CREATE SEQUENCE public."id-pokemon"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;

-- Define a coluna 'id' para usar a sequência
ALTER TABLE public.pokemon ALTER COLUMN id SET DEFAULT nextval('public."id-pokemon"'::regclass);
