--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

-- Started on 2024-11-29 11:59:32

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 16608)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    cat_id integer NOT NULL,
    cat_nome character varying(20)
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16611)
-- Name: categoria_cat_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_cat_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categoria_cat_id_seq OWNER TO postgres;

--
-- TOC entry 4957 (class 0 OID 0)
-- Dependencies: 218
-- Name: categoria_cat_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoria_cat_id_seq OWNED BY public.categoria.cat_id;


--
-- TOC entry 219 (class 1259 OID 16612)
-- Name: empresa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empresa (
    emp_id integer NOT NULL,
    emp_razao character varying(60),
    emp_fantasia character varying(50),
    emp_cnpj character varying(18),
    emp_fone character varying(15),
    emp_email character varying(80),
    emp_vlremb numeric(10,2),
    end_id integer
);


ALTER TABLE public.empresa OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16615)
-- Name: empresa_emp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.empresa_emp_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.empresa_emp_id_seq OWNER TO postgres;

--
-- TOC entry 4958 (class 0 OID 0)
-- Dependencies: 220
-- Name: empresa_emp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.empresa_emp_id_seq OWNED BY public.empresa.emp_id;


--
-- TOC entry 221 (class 1259 OID 16616)
-- Name: endereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.endereco (
    end_id integer NOT NULL,
    end_cep character varying(9),
    end_rua character varying(50),
    end_numero character varying(8),
    end_bairro character varying(30),
    end_cidade character varying(30),
    end_uf character varying(2)
);


ALTER TABLE public.endereco OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16619)
-- Name: item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item (
    pro_id integer NOT NULL,
    ped_id integer NOT NULL,
    it_quant integer,
    it_valor numeric(10,2)
);


ALTER TABLE public.item OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16622)
-- Name: pedido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedido (
    ped_id integer NOT NULL,
    ped_data date,
    ped_clinome character varying(50),
    ped_clifone character varying(15),
    ped_total numeric(10,2),
    tpg_id integer,
    end_id integer,
    ped_viagem integer
);


ALTER TABLE public.pedido OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16625)
-- Name: pedido_ped_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pedido_ped_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pedido_ped_id_seq OWNER TO postgres;

--
-- TOC entry 4959 (class 0 OID 0)
-- Dependencies: 224
-- Name: pedido_ped_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pedido_ped_id_seq OWNED BY public.pedido.ped_id;


--
-- TOC entry 225 (class 1259 OID 16626)
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produto (
    pro_id integer NOT NULL,
    pro_nome character varying(30),
    pro_descr text,
    pro_valor numeric(10,2),
    pro_foto bytea,
    cat_id integer
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16631)
-- Name: produto_pro_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produto_pro_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.produto_pro_id_seq OWNER TO postgres;

--
-- TOC entry 4960 (class 0 OID 0)
-- Dependencies: 226
-- Name: produto_pro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produto_pro_id_seq OWNED BY public.produto.pro_id;


--
-- TOC entry 227 (class 1259 OID 16632)
-- Name: tipo_pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_pagamento (
    tpg_id integer NOT NULL,
    tpg_nome character varying(20)
);


ALTER TABLE public.tipo_pagamento OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16635)
-- Name: tipo_pagamento_tpg_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tipo_pagamento_tpg_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tipo_pagamento_tpg_id_seq OWNER TO postgres;

--
-- TOC entry 4961 (class 0 OID 0)
-- Dependencies: 228
-- Name: tipo_pagamento_tpg_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_pagamento_tpg_id_seq OWNED BY public.tipo_pagamento.tpg_id;


--
-- TOC entry 4770 (class 2604 OID 16636)
-- Name: categoria cat_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria ALTER COLUMN cat_id SET DEFAULT nextval('public.categoria_cat_id_seq'::regclass);


--
-- TOC entry 4771 (class 2604 OID 16637)
-- Name: empresa emp_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa ALTER COLUMN emp_id SET DEFAULT nextval('public.empresa_emp_id_seq'::regclass);


--
-- TOC entry 4772 (class 2604 OID 16638)
-- Name: pedido ped_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido ALTER COLUMN ped_id SET DEFAULT nextval('public.pedido_ped_id_seq'::regclass);


--
-- TOC entry 4773 (class 2604 OID 16639)
-- Name: produto pro_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto ALTER COLUMN pro_id SET DEFAULT nextval('public.produto_pro_id_seq'::regclass);


--
-- TOC entry 4774 (class 2604 OID 16640)
-- Name: tipo_pagamento tpg_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_pagamento ALTER COLUMN tpg_id SET DEFAULT nextval('public.tipo_pagamento_tpg_id_seq'::regclass);


--
-- TOC entry 4940 (class 0 OID 16608)
-- Dependencies: 217
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoria VALUES (2, 'Lanches');
INSERT INTO public.categoria VALUES (3, 'Bebidas');
INSERT INTO public.categoria VALUES (4, 'Sobremesas');
INSERT INTO public.categoria VALUES (1, 'Hamburgues');


--
-- TOC entry 4942 (class 0 OID 16612)
-- Dependencies: 219
-- Data for Name: empresa; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.empresa VALUES (1, 'Faiska Burguer LTDA', 'Faiska Burguer', '12.345.678/0001-90', '(18) 1234-5678', 'contato@faiskaburguer.com', 5.00, 1);


--
-- TOC entry 4944 (class 0 OID 16616)
-- Dependencies: 221
-- Data for Name: endereco; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.endereco VALUES (1, '19407260', 'Rua Carmelindo Braga', '10', 'Residencial Morumbi', 'Presidente Venceslau', 'SP');


--
-- TOC entry 4945 (class 0 OID 16619)
-- Dependencies: 222
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.item VALUES (1, 1, 1, 15.00);
INSERT INTO public.item VALUES (4, 1, 1, 3.50);
INSERT INTO public.item VALUES (5, 1, 1, 7.00);


--
-- TOC entry 4946 (class 0 OID 16622)
-- Dependencies: 223
-- Data for Name: pedido; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pedido VALUES (1, '2024-11-20', 'Pedro', '(11) 9999-8888', 25.50, 2, NULL, 0);


--
-- TOC entry 4948 (class 0 OID 16626)
-- Dependencies: 225
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.produto VALUES (1, 'Hambúrguer Clássico', 'Hambúrguer simples com queijo, alface e tomate', 15.00, NULL, 1);
INSERT INTO public.produto VALUES (2, 'Cheeseburguer', 'Hambúrguer com queijo cheddar', 18.00, NULL, 1);
INSERT INTO public.produto VALUES (3, 'Batata Frita', 'Porção de batata frita crocante', 8.00, NULL, 1);
INSERT INTO public.produto VALUES (5, 'Sorvete', 'Sorvete de chocolate 100ml', 7.00, NULL, 4);
INSERT INTO public.produto VALUES (4, 'Coca-Cola', 'Refrigerante de 350ml', 3.50, NULL, 3);


--
-- TOC entry 4950 (class 0 OID 16632)
-- Dependencies: 227
-- Data for Name: tipo_pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tipo_pagamento VALUES (1, 'Dinheiro');
INSERT INTO public.tipo_pagamento VALUES (2, 'Cartão de Crédito');
INSERT INTO public.tipo_pagamento VALUES (3, 'Cartão de Débito');
INSERT INTO public.tipo_pagamento VALUES (4, 'Pix');


--
-- TOC entry 4962 (class 0 OID 0)
-- Dependencies: 218
-- Name: categoria_cat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_cat_id_seq', 1, true);


--
-- TOC entry 4963 (class 0 OID 0)
-- Dependencies: 220
-- Name: empresa_emp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.empresa_emp_id_seq', 1, false);


--
-- TOC entry 4964 (class 0 OID 0)
-- Dependencies: 224
-- Name: pedido_ped_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pedido_ped_id_seq', 2, true);


--
-- TOC entry 4965 (class 0 OID 0)
-- Dependencies: 226
-- Name: produto_pro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produto_pro_id_seq', 4, true);


--
-- TOC entry 4966 (class 0 OID 0)
-- Dependencies: 228
-- Name: tipo_pagamento_tpg_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_pagamento_tpg_id_seq', 1, false);


--
-- TOC entry 4776 (class 2606 OID 16642)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (cat_id);


--
-- TOC entry 4778 (class 2606 OID 16644)
-- Name: empresa empresa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_pkey PRIMARY KEY (emp_id);


--
-- TOC entry 4780 (class 2606 OID 16646)
-- Name: endereco endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (end_id);


--
-- TOC entry 4782 (class 2606 OID 16648)
-- Name: item item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (pro_id, ped_id);


--
-- TOC entry 4784 (class 2606 OID 16650)
-- Name: pedido pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (ped_id);


--
-- TOC entry 4786 (class 2606 OID 16652)
-- Name: produto produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (pro_id);


--
-- TOC entry 4788 (class 2606 OID 16654)
-- Name: tipo_pagamento tipo_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_pagamento
    ADD CONSTRAINT tipo_pagamento_pkey PRIMARY KEY (tpg_id);


--
-- TOC entry 4789 (class 2606 OID 16655)
-- Name: empresa emp_end_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT emp_end_id_fkey FOREIGN KEY (end_id) REFERENCES public.endereco(end_id) NOT VALID;


--
-- TOC entry 4790 (class 2606 OID 16660)
-- Name: item item_ped_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_ped_id_fkey FOREIGN KEY (ped_id) REFERENCES public.pedido(ped_id) NOT VALID;


--
-- TOC entry 4791 (class 2606 OID 16665)
-- Name: item item_pro_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pro_id_fkey FOREIGN KEY (pro_id) REFERENCES public.produto(pro_id);


--
-- TOC entry 4792 (class 2606 OID 16670)
-- Name: pedido pedido_end_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_end_id_fkey FOREIGN KEY (end_id) REFERENCES public.endereco(end_id) NOT VALID;


--
-- TOC entry 4793 (class 2606 OID 16675)
-- Name: pedido pedido_tpg_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_tpg_id_fkey FOREIGN KEY (tpg_id) REFERENCES public.tipo_pagamento(tpg_id) NOT VALID;


--
-- TOC entry 4794 (class 2606 OID 16680)
-- Name: produto produto_cat_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_cat_id_fkey FOREIGN KEY (cat_id) REFERENCES public.categoria(cat_id);


-- Completed on 2024-11-29 11:59:32

--
-- PostgreSQL database dump complete
--

