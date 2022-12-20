--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2 (Ubuntu 12.2-4)
-- Dumped by pg_dump version 12.2 (Ubuntu 12.2-4)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
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
-- Name: kilometrage; Type: TABLE; Schema: public; Owner: dina
--

CREATE TABLE public.kilometrage (
    id integer NOT NULL,
    vehiculeid integer NOT NULL,
    daty date DEFAULT CURRENT_DATE,
    debut double precision DEFAULT 0,
    fin double precision DEFAULT 0
);


ALTER TABLE public.kilometrage OWNER TO dina;

--
-- Name: kilometrage_id_seq; Type: SEQUENCE; Schema: public; Owner: dina
--

CREATE SEQUENCE public.kilometrage_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.kilometrage_id_seq OWNER TO dina;

--
-- Name: kilometrage_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dina
--

ALTER SEQUENCE public.kilometrage_id_seq OWNED BY public.kilometrage.id;


--
-- Name: kilometrage_vehiculeid_seq; Type: SEQUENCE; Schema: public; Owner: dina
--

CREATE SEQUENCE public.kilometrage_vehiculeid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.kilometrage_vehiculeid_seq OWNER TO dina;

--
-- Name: kilometrage_vehiculeid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dina
--

ALTER SEQUENCE public.kilometrage_vehiculeid_seq OWNED BY public.kilometrage.vehiculeid;


--
-- Name: tokens; Type: TABLE; Schema: public; Owner: dina
--

CREATE TABLE public.tokens (
    utilisateurid integer,
    token character varying(250),
    dateexp timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.tokens OWNER TO dina;

--
-- Name: utilisateur; Type: TABLE; Schema: public; Owner: dina
--

CREATE TABLE public.utilisateur (
    id integer NOT NULL,
    nom character varying(40) NOT NULL,
    login character varying(50) NOT NULL,
    pwd character varying(50) NOT NULL
);


ALTER TABLE public.utilisateur OWNER TO dina;

--
-- Name: utilisateur_id_seq; Type: SEQUENCE; Schema: public; Owner: dina
--

CREATE SEQUENCE public.utilisateur_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.utilisateur_id_seq OWNER TO dina;

--
-- Name: utilisateur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dina
--

ALTER SEQUENCE public.utilisateur_id_seq OWNED BY public.utilisateur.id;


--
-- Name: vehicule; Type: TABLE; Schema: public; Owner: dina
--

CREATE TABLE public.vehicule (
    id integer NOT NULL,
    nomchauffeur character varying(50) NOT NULL,
    matricule character varying(50) NOT NULL
);


ALTER TABLE public.vehicule OWNER TO dina;

--
-- Name: vehicule_id_seq; Type: SEQUENCE; Schema: public; Owner: dina
--

CREATE SEQUENCE public.vehicule_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vehicule_id_seq OWNER TO dina;

--
-- Name: vehicule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dina
--

ALTER SEQUENCE public.vehicule_id_seq OWNED BY public.vehicule.id;


--
-- Name: kilometrage id; Type: DEFAULT; Schema: public; Owner: dina
--

ALTER TABLE ONLY public.kilometrage ALTER COLUMN id SET DEFAULT nextval('public.kilometrage_id_seq'::regclass);


--
-- Name: kilometrage vehiculeid; Type: DEFAULT; Schema: public; Owner: dina
--

ALTER TABLE ONLY public.kilometrage ALTER COLUMN vehiculeid SET DEFAULT nextval('public.kilometrage_vehiculeid_seq'::regclass);


--
-- Name: utilisateur id; Type: DEFAULT; Schema: public; Owner: dina
--

ALTER TABLE ONLY public.utilisateur ALTER COLUMN id SET DEFAULT nextval('public.utilisateur_id_seq'::regclass);


--
-- Name: vehicule id; Type: DEFAULT; Schema: public; Owner: dina
--

ALTER TABLE ONLY public.vehicule ALTER COLUMN id SET DEFAULT nextval('public.vehicule_id_seq'::regclass);


--
-- Data for Name: kilometrage; Type: TABLE DATA; Schema: public; Owner: dina
--

COPY public.kilometrage (id, vehiculeid, daty, debut, fin) FROM stdin;
4	2	2022-12-09	188	190
5	1	2022-12-09	188	190
6	3	2022-12-09	188	190
2	2	2022-01-01	1	69
1	2	2022-01-01	1	69
\.


--
-- Data for Name: tokens; Type: TABLE DATA; Schema: public; Owner: dina
--

COPY public.tokens (utilisateurid, token, dateexp) FROM stdin;
\N	78c78c7b3c28ce91a7bf8b01f90935549719efe4	2022-11-15 11:48:29
2	e27f47dbc18d61ec2169d483ec9dcb23f0d31153	2022-11-15 11:56:37
\N	171e16afb1ebbd9ceb72b1a9064087bff69633a3	2022-11-15 11:29:55
\N	54d95ae8cf482cbbe45b211e0db267491871e3b4	2022-11-15 11:38:56
2	f2c4064fd36ff429c6cb1859590e4eb12b694432	2022-11-15 11:34:33
2	45b8fa628f668087a4555f3a3669be892d2bf3e7	2022-11-15 11:34:33
\.


--
-- Data for Name: utilisateur; Type: TABLE DATA; Schema: public; Owner: dina
--

COPY public.utilisateur (id, nom, login, pwd) FROM stdin;
2	root	Dina	root
3	root	Dina	root
4	Rakoto	rakoto	root
\.


--
-- Data for Name: vehicule; Type: TABLE DATA; Schema: public; Owner: dina
--

COPY public.vehicule (id, nomchauffeur, matricule) FROM stdin;
2	Rakoto	4309TUN
3	Ravao	1238VOS
1	dina	8989UUU
\.


--
-- Name: kilometrage_id_seq; Type: SEQUENCE SET; Schema: public; Owner: dina
--

SELECT pg_catalog.setval('public.kilometrage_id_seq', 6, true);


--
-- Name: kilometrage_vehiculeid_seq; Type: SEQUENCE SET; Schema: public; Owner: dina
--

SELECT pg_catalog.setval('public.kilometrage_vehiculeid_seq', 2, true);


--
-- Name: utilisateur_id_seq; Type: SEQUENCE SET; Schema: public; Owner: dina
--

SELECT pg_catalog.setval('public.utilisateur_id_seq', 4, true);


--
-- Name: vehicule_id_seq; Type: SEQUENCE SET; Schema: public; Owner: dina
--

SELECT pg_catalog.setval('public.vehicule_id_seq', 4, true);


--
-- Name: kilometrage kilometrage_pkey; Type: CONSTRAINT; Schema: public; Owner: dina
--

ALTER TABLE ONLY public.kilometrage
    ADD CONSTRAINT kilometrage_pkey PRIMARY KEY (id);


--
-- Name: utilisateur utilisateur_pkey; Type: CONSTRAINT; Schema: public; Owner: dina
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT utilisateur_pkey PRIMARY KEY (id);


--
-- Name: vehicule vehicule_pkey; Type: CONSTRAINT; Schema: public; Owner: dina
--

ALTER TABLE ONLY public.vehicule
    ADD CONSTRAINT vehicule_pkey PRIMARY KEY (id);


--
-- Name: kilometrage kilometrage_vehiculeid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dina
--

ALTER TABLE ONLY public.kilometrage
    ADD CONSTRAINT kilometrage_vehiculeid_fkey FOREIGN KEY (vehiculeid) REFERENCES public.vehicule(id);


--
-- Name: tokens tokens_utilisateurid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dina
--

ALTER TABLE ONLY public.tokens
    ADD CONSTRAINT tokens_utilisateurid_fkey FOREIGN KEY (utilisateurid) REFERENCES public.utilisateur(id);


--
-- PostgreSQL database dump complete
--

