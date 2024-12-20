toc.dat                                                                                             0000600 0004000 0002000 00000057611 14723342445 0014461 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        PGDMP       !                |            estoque    17.0    17.0 O    %           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false         &           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false         '           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false         (           1262    25193    estoque    DATABASE     ~   CREATE DATABASE estoque WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE estoque;
                     postgres    false                     2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                     pg_database_owner    false         )           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                        pg_database_owner    false    4         �            1259    33264    cliente    TABLE     �   CREATE TABLE public.cliente (
    idcliente integer NOT NULL,
    nome character varying(100) NOT NULL,
    cpf character(11) NOT NULL
);
    DROP TABLE public.cliente;
       public         heap r       postgres    false    4         �            1259    33263    cliente_idcliente_seq    SEQUENCE     �   CREATE SEQUENCE public.cliente_idcliente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.cliente_idcliente_seq;
       public               postgres    false    228    4         *           0    0    cliente_idcliente_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.cliente_idcliente_seq OWNED BY public.cliente.idcliente;
          public               postgres    false    227         �            1259    25202    credenciais    TABLE     �   CREATE TABLE public.credenciais (
    idcredencial integer NOT NULL,
    usuario character varying(50) NOT NULL,
    senha character varying(255) NOT NULL,
    tipo character varying(20) NOT NULL
);
    DROP TABLE public.credenciais;
       public         heap r       postgres    false    4         �            1259    25201    credenciais_idcredencial_seq    SEQUENCE     �   CREATE SEQUENCE public.credenciais_idcredencial_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.credenciais_idcredencial_seq;
       public               postgres    false    4    218         +           0    0    credenciais_idcredencial_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.credenciais_idcredencial_seq OWNED BY public.credenciais.idcredencial;
          public               postgres    false    217         �            1259    33294    demanda    TABLE     �   CREATE TABLE public.demanda (
    iditem integer NOT NULL,
    idcliente integer NOT NULL,
    datahora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    contagem integer DEFAULT 1
);
    DROP TABLE public.demanda;
       public         heap r       postgres    false    4         �            1259    33287    desconto    TABLE     *  CREATE TABLE public.desconto (
    iddesconto integer NOT NULL,
    tipo character varying(50) NOT NULL,
    percentual numeric(5,2) NOT NULL,
    descricao character varying(200),
    pontosminimos integer DEFAULT 0,
    CONSTRAINT desconto_percentual_check CHECK ((percentual > (0)::numeric))
);
    DROP TABLE public.desconto;
       public         heap r       postgres    false    4         �            1259    33286    desconto_iddesconto_seq    SEQUENCE     �   CREATE SEQUENCE public.desconto_iddesconto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.desconto_iddesconto_seq;
       public               postgres    false    4    231         ,           0    0    desconto_iddesconto_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.desconto_iddesconto_seq OWNED BY public.desconto.iddesconto;
          public               postgres    false    230         �            1259    25278    estoque    TABLE     �   CREATE TABLE public.estoque (
    idproduto integer NOT NULL,
    preco numeric(10,2) NOT NULL,
    descricao character varying(200) NOT NULL,
    quantidade integer NOT NULL,
    CONSTRAINT estoque_quantidade_check CHECK ((quantidade >= 0))
);
    DROP TABLE public.estoque;
       public         heap r       postgres    false    4         �            1259    25277    estoque_idproduto_seq    SEQUENCE     �   CREATE SEQUENCE public.estoque_idproduto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.estoque_idproduto_seq;
       public               postgres    false    4    226         -           0    0    estoque_idproduto_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.estoque_idproduto_seq OWNED BY public.estoque.idproduto;
          public               postgres    false    225         �            1259    33272 
   fidelidade    TABLE     >  CREATE TABLE public.fidelidade (
    idcliente integer NOT NULL,
    pontos integer DEFAULT 0 NOT NULL,
    descontoultimacompra numeric(5,2) DEFAULT 0,
    CONSTRAINT fidelidade_descontoultimacompra_check CHECK ((descontoultimacompra >= (0)::numeric)),
    CONSTRAINT fidelidade_pontos_check CHECK ((pontos >= 0))
);
    DROP TABLE public.fidelidade;
       public         heap r       postgres    false    4         �            1259    25211    funcionario    TABLE     �   CREATE TABLE public.funcionario (
    idfuncionario integer NOT NULL,
    idcredencial integer NOT NULL,
    nome character varying(100) NOT NULL
);
    DROP TABLE public.funcionario;
       public         heap r       postgres    false    4         �            1259    25210    funcionario_idfuncionario_seq    SEQUENCE     �   CREATE SEQUENCE public.funcionario_idfuncionario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE public.funcionario_idfuncionario_seq;
       public               postgres    false    4    220         .           0    0    funcionario_idfuncionario_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public.funcionario_idfuncionario_seq OWNED BY public.funcionario.idfuncionario;
          public               postgres    false    219         �            1259    25260    itemdemandado    TABLE     r   CREATE TABLE public.itemdemandado (
    iditem integer NOT NULL,
    descricao character varying(200) NOT NULL
);
 !   DROP TABLE public.itemdemandado;
       public         heap r       postgres    false    4         �            1259    25259    itemdemandado_iditem_seq    SEQUENCE     �   CREATE SEQUENCE public.itemdemandado_iditem_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.itemdemandado_iditem_seq;
       public               postgres    false    224    4         /           0    0    itemdemandado_iditem_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.itemdemandado_iditem_seq OWNED BY public.itemdemandado.iditem;
          public               postgres    false    223         �            1259    25223    produto    TABLE     �   CREATE TABLE public.produto (
    idproduto integer NOT NULL,
    preco numeric(10,2) NOT NULL,
    descricao character varying(200) NOT NULL
);
    DROP TABLE public.produto;
       public         heap r       postgres    false    4         �            1259    25222    produto_idproduto_seq    SEQUENCE     �   CREATE SEQUENCE public.produto_idproduto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.produto_idproduto_seq;
       public               postgres    false    4    222         0           0    0    produto_idproduto_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.produto_idproduto_seq OWNED BY public.produto.idproduto;
          public               postgres    false    221         �            1259    33311 	   transacao    TABLE     �  CREATE TABLE public.transacao (
    idtransacao integer NOT NULL,
    idproduto integer NOT NULL,
    idfuncionario integer NOT NULL,
    idcliente integer,
    preco numeric(10,2) NOT NULL,
    descricao character varying(200) NOT NULL,
    quantidade integer NOT NULL,
    datahora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    tipo character varying(20) NOT NULL,
    iddesconto integer,
    CONSTRAINT transacao_quantidade_check CHECK ((quantidade > 0))
);
    DROP TABLE public.transacao;
       public         heap r       postgres    false    4         �            1259    33310    transacao_idtransacao_seq    SEQUENCE     �   CREATE SEQUENCE public.transacao_idtransacao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.transacao_idtransacao_seq;
       public               postgres    false    4    234         1           0    0    transacao_idtransacao_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.transacao_idtransacao_seq OWNED BY public.transacao.idtransacao;
          public               postgres    false    233         Q           2604    33267    cliente idcliente    DEFAULT     v   ALTER TABLE ONLY public.cliente ALTER COLUMN idcliente SET DEFAULT nextval('public.cliente_idcliente_seq'::regclass);
 @   ALTER TABLE public.cliente ALTER COLUMN idcliente DROP DEFAULT;
       public               postgres    false    228    227    228         L           2604    33261    credenciais idcredencial    DEFAULT     �   ALTER TABLE ONLY public.credenciais ALTER COLUMN idcredencial SET DEFAULT nextval('public.credenciais_idcredencial_seq'::regclass);
 G   ALTER TABLE public.credenciais ALTER COLUMN idcredencial DROP DEFAULT;
       public               postgres    false    218    217    218         T           2604    33290    desconto iddesconto    DEFAULT     z   ALTER TABLE ONLY public.desconto ALTER COLUMN iddesconto SET DEFAULT nextval('public.desconto_iddesconto_seq'::regclass);
 B   ALTER TABLE public.desconto ALTER COLUMN iddesconto DROP DEFAULT;
       public               postgres    false    230    231    231         P           2604    25281    estoque idproduto    DEFAULT     v   ALTER TABLE ONLY public.estoque ALTER COLUMN idproduto SET DEFAULT nextval('public.estoque_idproduto_seq'::regclass);
 @   ALTER TABLE public.estoque ALTER COLUMN idproduto DROP DEFAULT;
       public               postgres    false    225    226    226         M           2604    33262    funcionario idfuncionario    DEFAULT     �   ALTER TABLE ONLY public.funcionario ALTER COLUMN idfuncionario SET DEFAULT nextval('public.funcionario_idfuncionario_seq'::regclass);
 H   ALTER TABLE public.funcionario ALTER COLUMN idfuncionario DROP DEFAULT;
       public               postgres    false    220    219    220         O           2604    25263    itemdemandado iditem    DEFAULT     |   ALTER TABLE ONLY public.itemdemandado ALTER COLUMN iditem SET DEFAULT nextval('public.itemdemandado_iditem_seq'::regclass);
 C   ALTER TABLE public.itemdemandado ALTER COLUMN iditem DROP DEFAULT;
       public               postgres    false    223    224    224         N           2604    25226    produto idproduto    DEFAULT     v   ALTER TABLE ONLY public.produto ALTER COLUMN idproduto SET DEFAULT nextval('public.produto_idproduto_seq'::regclass);
 @   ALTER TABLE public.produto ALTER COLUMN idproduto DROP DEFAULT;
       public               postgres    false    222    221    222         X           2604    33314    transacao idtransacao    DEFAULT     ~   ALTER TABLE ONLY public.transacao ALTER COLUMN idtransacao SET DEFAULT nextval('public.transacao_idtransacao_seq'::regclass);
 D   ALTER TABLE public.transacao ALTER COLUMN idtransacao DROP DEFAULT;
       public               postgres    false    233    234    234                   0    33264    cliente 
   TABLE DATA           7   COPY public.cliente (idcliente, nome, cpf) FROM stdin;
    public               postgres    false    228       4892.dat           0    25202    credenciais 
   TABLE DATA           I   COPY public.credenciais (idcredencial, usuario, senha, tipo) FROM stdin;
    public               postgres    false    218       4882.dat            0    33294    demanda 
   TABLE DATA           H   COPY public.demanda (iditem, idcliente, datahora, contagem) FROM stdin;
    public               postgres    false    232       4896.dat           0    33287    desconto 
   TABLE DATA           Z   COPY public.desconto (iddesconto, tipo, percentual, descricao, pontosminimos) FROM stdin;
    public               postgres    false    231       4895.dat           0    25278    estoque 
   TABLE DATA           J   COPY public.estoque (idproduto, preco, descricao, quantidade) FROM stdin;
    public               postgres    false    226       4890.dat           0    33272 
   fidelidade 
   TABLE DATA           M   COPY public.fidelidade (idcliente, pontos, descontoultimacompra) FROM stdin;
    public               postgres    false    229       4893.dat           0    25211    funcionario 
   TABLE DATA           H   COPY public.funcionario (idfuncionario, idcredencial, nome) FROM stdin;
    public               postgres    false    220       4884.dat           0    25260    itemdemandado 
   TABLE DATA           :   COPY public.itemdemandado (iditem, descricao) FROM stdin;
    public               postgres    false    224       4888.dat           0    25223    produto 
   TABLE DATA           >   COPY public.produto (idproduto, preco, descricao) FROM stdin;
    public               postgres    false    222       4886.dat "          0    33311 	   transacao 
   TABLE DATA           �   COPY public.transacao (idtransacao, idproduto, idfuncionario, idcliente, preco, descricao, quantidade, datahora, tipo, iddesconto) FROM stdin;
    public               postgres    false    234       4898.dat 2           0    0    cliente_idcliente_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.cliente_idcliente_seq', 30, true);
          public               postgres    false    227         3           0    0    credenciais_idcredencial_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public.credenciais_idcredencial_seq', 2, true);
          public               postgres    false    217         4           0    0    desconto_iddesconto_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.desconto_iddesconto_seq', 12, true);
          public               postgres    false    230         5           0    0    estoque_idproduto_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.estoque_idproduto_seq', 4, true);
          public               postgres    false    225         6           0    0    funcionario_idfuncionario_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.funcionario_idfuncionario_seq', 2, true);
          public               postgres    false    219         7           0    0    itemdemandado_iditem_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.itemdemandado_iditem_seq', 2, true);
          public               postgres    false    223         8           0    0    produto_idproduto_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.produto_idproduto_seq', 4, true);
          public               postgres    false    221         9           0    0    transacao_idtransacao_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.transacao_idtransacao_seq', 182, true);
          public               postgres    false    233         l           2606    33271    cliente cliente_cpf_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_cpf_key UNIQUE (cpf);
 A   ALTER TABLE ONLY public.cliente DROP CONSTRAINT cliente_cpf_key;
       public                 postgres    false    228         n           2606    33269    cliente cliente_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (idcliente);
 >   ALTER TABLE ONLY public.cliente DROP CONSTRAINT cliente_pkey;
       public                 postgres    false    228         `           2606    25207    credenciais credenciais_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.credenciais
    ADD CONSTRAINT credenciais_pkey PRIMARY KEY (idcredencial);
 F   ALTER TABLE ONLY public.credenciais DROP CONSTRAINT credenciais_pkey;
       public                 postgres    false    218         b           2606    25209 #   credenciais credenciais_usuario_key 
   CONSTRAINT     a   ALTER TABLE ONLY public.credenciais
    ADD CONSTRAINT credenciais_usuario_key UNIQUE (usuario);
 M   ALTER TABLE ONLY public.credenciais DROP CONSTRAINT credenciais_usuario_key;
       public                 postgres    false    218         t           2606    33299    demanda demanda_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.demanda
    ADD CONSTRAINT demanda_pkey PRIMARY KEY (iditem, idcliente);
 >   ALTER TABLE ONLY public.demanda DROP CONSTRAINT demanda_pkey;
       public                 postgres    false    232    232         r           2606    33293    desconto desconto_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.desconto
    ADD CONSTRAINT desconto_pkey PRIMARY KEY (iddesconto);
 @   ALTER TABLE ONLY public.desconto DROP CONSTRAINT desconto_pkey;
       public                 postgres    false    231         j           2606    25284    estoque estoque_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.estoque
    ADD CONSTRAINT estoque_pkey PRIMARY KEY (idproduto);
 >   ALTER TABLE ONLY public.estoque DROP CONSTRAINT estoque_pkey;
       public                 postgres    false    226         p           2606    33280    fidelidade fidelidade_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY public.fidelidade
    ADD CONSTRAINT fidelidade_pkey PRIMARY KEY (idcliente);
 D   ALTER TABLE ONLY public.fidelidade DROP CONSTRAINT fidelidade_pkey;
       public                 postgres    false    229         d           2606    25216    funcionario funcionario_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_pkey PRIMARY KEY (idfuncionario);
 F   ALTER TABLE ONLY public.funcionario DROP CONSTRAINT funcionario_pkey;
       public                 postgres    false    220         h           2606    25265     itemdemandado itemdemandado_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.itemdemandado
    ADD CONSTRAINT itemdemandado_pkey PRIMARY KEY (iditem);
 J   ALTER TABLE ONLY public.itemdemandado DROP CONSTRAINT itemdemandado_pkey;
       public                 postgres    false    224         f           2606    25228    produto produto_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (idproduto);
 >   ALTER TABLE ONLY public.produto DROP CONSTRAINT produto_pkey;
       public                 postgres    false    222         v           2606    33318    transacao transacao_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_pkey PRIMARY KEY (idtransacao);
 B   ALTER TABLE ONLY public.transacao DROP CONSTRAINT transacao_pkey;
       public                 postgres    false    234         z           2606    33305    demanda demanda_idcliente_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.demanda
    ADD CONSTRAINT demanda_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);
 H   ALTER TABLE ONLY public.demanda DROP CONSTRAINT demanda_idcliente_fkey;
       public               postgres    false    4718    228    232         {           2606    33300    demanda demanda_iditem_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.demanda
    ADD CONSTRAINT demanda_iditem_fkey FOREIGN KEY (iditem) REFERENCES public.itemdemandado(iditem);
 E   ALTER TABLE ONLY public.demanda DROP CONSTRAINT demanda_iditem_fkey;
       public               postgres    false    224    232    4712         x           2606    25285    estoque estoque_idproduto_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.estoque
    ADD CONSTRAINT estoque_idproduto_fkey FOREIGN KEY (idproduto) REFERENCES public.produto(idproduto);
 H   ALTER TABLE ONLY public.estoque DROP CONSTRAINT estoque_idproduto_fkey;
       public               postgres    false    226    4710    222         y           2606    33281 $   fidelidade fidelidade_idcliente_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.fidelidade
    ADD CONSTRAINT fidelidade_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);
 N   ALTER TABLE ONLY public.fidelidade DROP CONSTRAINT fidelidade_idcliente_fkey;
       public               postgres    false    229    4718    228         |           2606    33336    transacao fk_desconto    FK CONSTRAINT     �   ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT fk_desconto FOREIGN KEY (iddesconto) REFERENCES public.desconto(iddesconto);
 ?   ALTER TABLE ONLY public.transacao DROP CONSTRAINT fk_desconto;
       public               postgres    false    234    4722    231         w           2606    25217 )   funcionario funcionario_idcredencial_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_idcredencial_fkey FOREIGN KEY (idcredencial) REFERENCES public.credenciais(idcredencial);
 S   ALTER TABLE ONLY public.funcionario DROP CONSTRAINT funcionario_idcredencial_fkey;
       public               postgres    false    218    220    4704         }           2606    33329 "   transacao transacao_idcliente_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);
 L   ALTER TABLE ONLY public.transacao DROP CONSTRAINT transacao_idcliente_fkey;
       public               postgres    false    228    4718    234         ~           2606    33324 &   transacao transacao_idfuncionario_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_idfuncionario_fkey FOREIGN KEY (idfuncionario) REFERENCES public.funcionario(idfuncionario);
 P   ALTER TABLE ONLY public.transacao DROP CONSTRAINT transacao_idfuncionario_fkey;
       public               postgres    false    4708    220    234                    2606    33319 "   transacao transacao_idproduto_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_idproduto_fkey FOREIGN KEY (idproduto) REFERENCES public.produto(idproduto);
 L   ALTER TABLE ONLY public.transacao DROP CONSTRAINT transacao_idproduto_fkey;
       public               postgres    false    4710    234    222                                                                                                                               4892.dat                                                                                            0000600 0004000 0002000 00000000235 14723342445 0014270 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        24	Pedro Vas	01234567891
25	Junior	12345678988
26	Pedrido	01234567892
27	jimin	98765432100
28	JUNI	01234567897
29	JID	12345678770
30	JENIFF	01234567898
\.


                                                                                                                                                                                                                                                                                                                                                                   4882.dat                                                                                            0000600 0004000 0002000 00000000077 14723342445 0014273 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	prop	prop123	Proprietário
2	func1	func123	Funcionário
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                                                 4896.dat                                                                                            0000600 0004000 0002000 00000000111 14723342445 0014265 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	25	2024-11-30 19:56:30.098706	2
2	25	2024-11-30 20:05:34.576363	1
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                                       4895.dat                                                                                            0000600 0004000 0002000 00000000516 14723342445 0014275 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        7	Primeira Compra	15.00	15% de desconto para primeira compra	0
8	Fidelidade	5.00	5% de desconto para clientes.	20
9	Fidelidade	10.00	10% de desconto para clientes.	40
10	Fidelidade	15.00	15% de desconto para clientes.	60
11	Fidelidade	20.00	20% de desconto para clientes.	80
12	Fidelidade	35.00	35% de desconto para clientes.	80
\.


                                                                                                                                                                                  4890.dat                                                                                            0000600 0004000 0002000 00000000074 14723342445 0014267 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        4	7.70	Brahma	15
2	5.50	Cerveja	86
1	22.20	Heineken	87
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                                                    4893.dat                                                                                            0000600 0004000 0002000 00000000101 14723342445 0014261 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        26	0	0.00
27	0	0.00
28	8	0.00
24	4	0.00
30	0	0.00
29	4	0.00
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                                               4884.dat                                                                                            0000600 0004000 0002000 00000000031 14723342445 0014263 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	1	Lucas
2	2	Diego
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       4888.dat                                                                                            0000600 0004000 0002000 00000000031 14723342445 0014267 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	Budweiser
2	Água
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       4886.dat                                                                                            0000600 0004000 0002000 00000000063 14723342445 0014272 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        1	22.20	Heineken
2	5.50	Cerveja
4	7.70	Brahma
\.


                                                                                                                                                                                                                                                                                                                                                                                                                                                                             4898.dat                                                                                            0000600 0004000 0002000 00000016147 14723342445 0014307 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        67	1	2	\N	22.20	Heineken	1	2024-11-27 21:28:33.142051	Venda	\N
68	1	2	24	22.20	Heineken	1	2024-11-27 21:29:33.887146	Venda	\N
69	1	2	24	22.20	Heineken	1	2024-11-27 21:30:12.398664	Venda	\N
70	1	2	24	222.00	Heineken	10	2024-11-27 21:30:12.404061	Venda	\N
71	1	2	24	44.40	Heineken	2	2024-11-27 21:30:29.13619	Venda	\N
72	1	2	24	22.20	Heineken	1	2024-11-28 16:42:08.317734	Venda	\N
73	1	2	24	22.20	Heineken	1	2024-11-28 16:52:33.299264	Venda	\N
74	1	2	28	22.20	Heineken	1	2024-11-28 21:54:39.318785	Venda	\N
75	1	2	24	17.76	Heineken	1	2024-11-28 22:06:18.015395	Venda	\N
76	1	2	24	88.80	Heineken	3	2024-11-28 22:07:14.988393	Venda	\N
77	1	2	24	88.80	Heineken	2	2024-11-28 22:07:14.993965	Venda	\N
78	1	2	24	22.20	Heineken	1	2024-11-29 00:31:39.613129	Venda	\N
79	1	2	24	39.96	Heineken	2	2024-11-29 00:33:28.834782	Venda	\N
80	1	2	24	44.40	Heineken	2	2024-11-29 14:10:00.825585	Venda	\N
81	1	2	24	22.20	Heineken	1	2024-11-29 14:14:50.954984	Venda	\N
82	1	2	24	22.20	Heineken	1	2024-11-29 14:18:47.826699	Venda	\N
83	1	2	24	22.20	Heineken	1	2024-11-29 14:41:54.640064	Venda	\N
84	1	2	24	22.20	Heineken	1	2024-11-29 14:43:39.71251	Venda	\N
85	1	2	24	35.52	Heineken	2	2024-11-29 14:57:39.190748	Venda	\N
86	1	2	24	31.08	Heineken	2	2024-11-29 15:00:13.930989	Venda	\N
87	1	2	24	17.76	Heineken	1	2024-11-29 15:06:38.84458	Venda	\N
88	1	2	24	22.20	Heineken	1	2024-11-29 15:07:56.356332	Venda	\N
89	1	2	24	22.20	Heineken	1	2024-11-29 15:08:40.849367	Venda	\N
90	1	2	29	16.04	Heineken	1	2024-11-29 17:09:14.069516	Venda	\N
91	1	2	29	66.60	Heineken	3	2024-11-29 17:11:16.741905	Venda	\N
92	1	2	24	22.20	Heineken	1	2024-11-29 19:47:00.310973	Venda	\N
93	1	2	24	66.60	Heineken	2	2024-11-29 19:50:28.960668	Venda	\N
94	1	2	24	66.60	Heineken	1	2024-11-29 19:50:28.966367	Venda	\N
95	1	2	24	44.40	Heineken	2	2024-11-29 19:58:08.29629	Venda	\N
96	1	2	24	22.20	Heineken	1	2024-11-29 20:00:52.458114	Venda	\N
97	1	2	24	22.20	Heineken	1	2024-11-29 20:02:28.427509	Venda	\N
98	1	2	24	22.20	Heineken	1	2024-11-29 20:04:42.765394	Venda	\N
99	1	2	24	22.20	Heineken	1	2024-11-29 20:07:27.419369	Venda	\N
100	1	2	24	22.20	Heineken	1	2024-11-29 20:08:53.775277	Venda	\N
101	1	2	24	44.40	Heineken	2	2024-11-29 20:12:57.637833	Venda	\N
102	1	2	24	17.76	Heineken	1	2024-11-29 20:16:24.511659	Venda	\N
103	1	2	24	22.20	Heineken	1	2024-11-29 20:17:30.801736	Venda	\N
104	1	2	24	14.43	Heineken	1	2024-11-29 21:30:25.43426	Venda	\N
105	1	2	24	14.43	Heineken	1	2024-11-29 21:34:08.774636	Venda	\N
106	1	2	24	14.43	Heineken	1	2024-11-29 21:34:50.207404	Venda	\N
107	1	2	24	22.20	Heineken	1	2024-11-29 21:38:28.231348	Venda	\N
108	2	2	24	7.15	Cerveja	2	2024-11-29 21:43:14.761762	Venda	\N
109	2	2	24	7.15	Cerveja	2	2024-11-29 21:47:48.797494	Venda	\N
110	2	2	24	5.50	Cerveja	1	2024-11-29 21:49:31.462833	Venda	\N
111	2	2	24	7.15	Cerveja	2	2024-11-29 21:49:49.951246	Venda	\N
112	2	2	24	14.30	Cerveja	2	2024-11-29 22:02:16.281274	Venda	\N
113	2	2	24	14.30	Cerveja	2	2024-11-29 22:02:16.286656	Venda	\N
114	2	2	24	10.73	Cerveja	1	2024-11-29 22:09:12.107749	Venda	\N
115	2	2	24	10.73	Cerveja	2	2024-11-29 22:09:12.113518	Venda	\N
116	1	2	24	28.86	Heineken	2	2024-11-29 22:09:45.169874	Venda	\N
117	1	2	24	14.43	Heineken	1	2024-11-29 22:14:43.391677	Venda	\N
118	1	2	24	28.86	Heineken	2	2024-11-29 22:18:23.914062	Venda	\N
119	1	2	24	14.43	Heineken	1	2024-11-29 22:27:42.540387	Venda	\N
120	1	2	24	43.29	Heineken	3	2024-11-29 22:34:47.395596	Venda	\N
121	1	2	24	28.86	Heineken	2	2024-11-29 22:38:24.986221	Venda	\N
122	2	2	24	7.15	Cerveja	2	2024-11-29 22:43:24.308302	Venda	\N
123	2	2	24	7.15	Cerveja	2	2024-11-29 23:02:53.845944	Venda	\N
124	2	2	24	7.15	Cerveja	2	2024-11-29 23:05:27.653034	Venda	\N
125	2	2	29	20.90	Cerveja	4	2024-11-29 23:08:08.615709	Venda	\N
126	2	2	29	10.45	Cerveja	2	2024-11-29 23:12:31.770773	Venda	\N
127	2	2	29	22.00	Cerveja	4	2024-11-29 23:39:21.596361	Venda	\N
128	2	2	29	9.35	Cerveja	2	2024-11-29 23:40:00.94081	Venda	\N
129	2	2	29	15.68	Cerveja	3	2024-11-29 23:54:27.626195	Venda	\N
130	1	2	29	42.18	Heineken	2	2024-11-30 00:00:26.01451	Venda	\N
131	1	2	29	21.09	Heineken	1	2024-11-30 00:02:26.601357	Venda	\N
132	1	2	29	21.09	Heineken	1	2024-11-30 00:03:52.666864	Venda	\N
133	1	2	29	21.09	Heineken	1	2024-11-30 00:04:45.505506	Venda	\N
134	1	2	29	21.09	Heineken	1	2024-11-30 00:08:41.306579	Venda	\N
135	1	2	29	21.09	Heineken	1	2024-11-30 00:10:07.06056	Venda	\N
136	1	2	29	21.09	Heineken	1	2024-11-30 00:13:53.522948	Venda	\N
137	2	2	29	11.00	Cerveja	2	2024-11-30 00:17:22.635476	Venda	\N
138	1	2	29	21.09	Heineken	1	2024-11-30 00:22:26.733813	Venda	\N
139	2	2	29	10.45	Cerveja	2	2024-11-30 00:23:13.603865	Venda	\N
140	2	2	29	11.00	Cerveja	2	2024-11-30 00:25:15.351862	Venda	\N
141	2	2	29	10.45	Cerveja	2	2024-11-30 00:28:34.898365	Venda	\N
142	2	2	29	10.45	Cerveja	2	2024-11-30 00:39:08.179721	Venda	\N
143	1	2	29	244.20	Heineken	11	2024-11-30 00:40:57.585554	Venda	\N
144	1	2	29	244.20	Heineken	11	2024-11-30 00:46:18.190662	Venda	\N
145	2	2	29	22.00	Cerveja	4	2024-11-30 00:47:18.719877	Venda	\N
146	2	2	29	11.00	Cerveja	2	2024-11-30 00:47:58.525849	Venda	\N
147	1	2	29	44.40	Heineken	1	2024-11-30 00:48:22.787405	Venda	\N
148	1	2	29	44.40	Heineken	1	2024-11-30 00:48:22.793943	Venda	\N
149	1	2	29	21.09	Heineken	1	2024-11-30 00:48:49.424402	Venda	\N
150	1	2	29	42.18	Heineken	2	2024-11-30 01:13:18.358988	Venda	\N
151	1	2	29	44.40	Heineken	2	2024-11-30 11:51:09.263668	Venda	\N
152	2	2	29	11.00	Cerveja	1	2024-11-30 11:51:34.782831	Venda	\N
153	2	2	29	11.00	Cerveja	1	2024-11-30 11:51:34.78794	Venda	\N
154	2	2	29	5.23	Cerveja	1	2024-11-30 11:52:18.177295	Venda	\N
155	2	2	29	22.00	Cerveja	1	2024-11-30 11:52:51.881583	Venda	\N
156	2	2	29	22.00	Cerveja	1	2024-11-30 11:52:51.886632	Venda	\N
157	2	2	29	22.00	Cerveja	1	2024-11-30 11:52:51.888361	Venda	\N
158	2	2	29	22.00	Cerveja	1	2024-11-30 11:52:51.890143	Venda	\N
159	2	2	29	16.50	Cerveja	1	2024-11-30 11:53:11.981807	Venda	\N
160	2	2	29	16.50	Cerveja	1	2024-11-30 11:53:11.987108	Venda	\N
161	2	2	29	16.50	Cerveja	1	2024-11-30 11:53:11.989163	Venda	\N
162	2	2	29	10.45	Cerveja	2	2024-11-30 11:53:35.399759	Venda	\N
163	2	2	29	11.00	Cerveja	2	2024-11-30 11:55:32.182181	Venda	\N
164	2	2	29	22.00	Cerveja	2	2024-11-30 11:55:56.761122	Venda	\N
165	2	2	29	22.00	Cerveja	2	2024-11-30 11:55:56.767219	Venda	\N
166	2	2	29	5.50	Cerveja	1	2024-11-30 12:07:15.454218	Venda	\N
167	2	2	29	5.50	Cerveja	1	2024-11-30 12:09:39.188203	Venda	\N
168	2	2	29	0.00	Cerveja	1	2024-11-30 12:10:05.110069	Venda	\N
169	2	2	29	16.50	Cerveja	1	2024-11-30 12:13:52.544018	Venda	\N
170	2	2	29	49.90	Cerveja	1	2024-11-30 12:29:03.433346	Venda	\N
171	2	2	24	5.50	Cerveja	1	2024-11-30 12:40:11.079304	Venda	\N
172	2	2	24	11.00	Cerveja	1	2024-11-30 12:40:31.765529	Venda	\N
173	2	2	24	11.00	Cerveja	1	2024-11-30 12:40:31.770446	Venda	\N
174	2	1	\N	38.50	Cerveja	7	2024-11-30 15:56:40.80422	Compra	\N
175	1	1	\N	266.40	Heineken	12	2024-11-30 15:58:11.365972	Compra	\N
176	4	1	\N	115.50	Brahma	15	2024-11-30 15:59:38.564087	Compra	\N
177	2	1	29	5.23	Cerveja	1	2024-12-01 00:47:58.002011	Venda	\N
179	1	2	29	22.20	Heineken	1	2024-12-01 13:58:11.538846	Venda	\N
180	1	2	29	22.20	Heineken	1	2024-12-01 13:58:52.473934	Venda	\N
181	1	2	29	22.20	Heineken	1	2024-12-01 13:59:07.881494	Venda	\N
182	1	2	29	21.09	Heineken	1	2024-12-01 13:59:26.485915	Venda	8
\.


                                                                                                                                                                                                                                                                                                                                                                                                                         restore.sql                                                                                         0000600 0004000 0002000 00000045405 14723342445 0015404 0                                                                                                    ustar 00postgres                        postgres                        0000000 0000000                                                                                                                                                                        --
-- NOTE:
--
-- File paths need to be edited. Search for $$PATH$$ and
-- replace it with the path to the directory containing
-- the extracted data files.
--
--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

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

DROP DATABASE estoque;
--
-- Name: estoque; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE estoque WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';


ALTER DATABASE estoque OWNER TO postgres;

\connect estoque

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

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    idcliente integer NOT NULL,
    nome character varying(100) NOT NULL,
    cpf character(11) NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- Name: cliente_idcliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cliente_idcliente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cliente_idcliente_seq OWNER TO postgres;

--
-- Name: cliente_idcliente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cliente_idcliente_seq OWNED BY public.cliente.idcliente;


--
-- Name: credenciais; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credenciais (
    idcredencial integer NOT NULL,
    usuario character varying(50) NOT NULL,
    senha character varying(255) NOT NULL,
    tipo character varying(20) NOT NULL
);


ALTER TABLE public.credenciais OWNER TO postgres;

--
-- Name: credenciais_idcredencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.credenciais_idcredencial_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.credenciais_idcredencial_seq OWNER TO postgres;

--
-- Name: credenciais_idcredencial_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.credenciais_idcredencial_seq OWNED BY public.credenciais.idcredencial;


--
-- Name: demanda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.demanda (
    iditem integer NOT NULL,
    idcliente integer NOT NULL,
    datahora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    contagem integer DEFAULT 1
);


ALTER TABLE public.demanda OWNER TO postgres;

--
-- Name: desconto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.desconto (
    iddesconto integer NOT NULL,
    tipo character varying(50) NOT NULL,
    percentual numeric(5,2) NOT NULL,
    descricao character varying(200),
    pontosminimos integer DEFAULT 0,
    CONSTRAINT desconto_percentual_check CHECK ((percentual > (0)::numeric))
);


ALTER TABLE public.desconto OWNER TO postgres;

--
-- Name: desconto_iddesconto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.desconto_iddesconto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.desconto_iddesconto_seq OWNER TO postgres;

--
-- Name: desconto_iddesconto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.desconto_iddesconto_seq OWNED BY public.desconto.iddesconto;


--
-- Name: estoque; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estoque (
    idproduto integer NOT NULL,
    preco numeric(10,2) NOT NULL,
    descricao character varying(200) NOT NULL,
    quantidade integer NOT NULL,
    CONSTRAINT estoque_quantidade_check CHECK ((quantidade >= 0))
);


ALTER TABLE public.estoque OWNER TO postgres;

--
-- Name: estoque_idproduto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.estoque_idproduto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.estoque_idproduto_seq OWNER TO postgres;

--
-- Name: estoque_idproduto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.estoque_idproduto_seq OWNED BY public.estoque.idproduto;


--
-- Name: fidelidade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fidelidade (
    idcliente integer NOT NULL,
    pontos integer DEFAULT 0 NOT NULL,
    descontoultimacompra numeric(5,2) DEFAULT 0,
    CONSTRAINT fidelidade_descontoultimacompra_check CHECK ((descontoultimacompra >= (0)::numeric)),
    CONSTRAINT fidelidade_pontos_check CHECK ((pontos >= 0))
);


ALTER TABLE public.fidelidade OWNER TO postgres;

--
-- Name: funcionario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.funcionario (
    idfuncionario integer NOT NULL,
    idcredencial integer NOT NULL,
    nome character varying(100) NOT NULL
);


ALTER TABLE public.funcionario OWNER TO postgres;

--
-- Name: funcionario_idfuncionario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.funcionario_idfuncionario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.funcionario_idfuncionario_seq OWNER TO postgres;

--
-- Name: funcionario_idfuncionario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.funcionario_idfuncionario_seq OWNED BY public.funcionario.idfuncionario;


--
-- Name: itemdemandado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.itemdemandado (
    iditem integer NOT NULL,
    descricao character varying(200) NOT NULL
);


ALTER TABLE public.itemdemandado OWNER TO postgres;

--
-- Name: itemdemandado_iditem_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.itemdemandado_iditem_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.itemdemandado_iditem_seq OWNER TO postgres;

--
-- Name: itemdemandado_iditem_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.itemdemandado_iditem_seq OWNED BY public.itemdemandado.iditem;


--
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produto (
    idproduto integer NOT NULL,
    preco numeric(10,2) NOT NULL,
    descricao character varying(200) NOT NULL
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- Name: produto_idproduto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produto_idproduto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.produto_idproduto_seq OWNER TO postgres;

--
-- Name: produto_idproduto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produto_idproduto_seq OWNED BY public.produto.idproduto;


--
-- Name: transacao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transacao (
    idtransacao integer NOT NULL,
    idproduto integer NOT NULL,
    idfuncionario integer NOT NULL,
    idcliente integer,
    preco numeric(10,2) NOT NULL,
    descricao character varying(200) NOT NULL,
    quantidade integer NOT NULL,
    datahora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    tipo character varying(20) NOT NULL,
    iddesconto integer,
    CONSTRAINT transacao_quantidade_check CHECK ((quantidade > 0))
);


ALTER TABLE public.transacao OWNER TO postgres;

--
-- Name: transacao_idtransacao_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transacao_idtransacao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.transacao_idtransacao_seq OWNER TO postgres;

--
-- Name: transacao_idtransacao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transacao_idtransacao_seq OWNED BY public.transacao.idtransacao;


--
-- Name: cliente idcliente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente ALTER COLUMN idcliente SET DEFAULT nextval('public.cliente_idcliente_seq'::regclass);


--
-- Name: credenciais idcredencial; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credenciais ALTER COLUMN idcredencial SET DEFAULT nextval('public.credenciais_idcredencial_seq'::regclass);


--
-- Name: desconto iddesconto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.desconto ALTER COLUMN iddesconto SET DEFAULT nextval('public.desconto_iddesconto_seq'::regclass);


--
-- Name: estoque idproduto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estoque ALTER COLUMN idproduto SET DEFAULT nextval('public.estoque_idproduto_seq'::regclass);


--
-- Name: funcionario idfuncionario; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionario ALTER COLUMN idfuncionario SET DEFAULT nextval('public.funcionario_idfuncionario_seq'::regclass);


--
-- Name: itemdemandado iditem; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itemdemandado ALTER COLUMN iditem SET DEFAULT nextval('public.itemdemandado_iditem_seq'::regclass);


--
-- Name: produto idproduto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto ALTER COLUMN idproduto SET DEFAULT nextval('public.produto_idproduto_seq'::regclass);


--
-- Name: transacao idtransacao; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao ALTER COLUMN idtransacao SET DEFAULT nextval('public.transacao_idtransacao_seq'::regclass);


--
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (idcliente, nome, cpf) FROM stdin;
\.
COPY public.cliente (idcliente, nome, cpf) FROM '$$PATH$$/4892.dat';

--
-- Data for Name: credenciais; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.credenciais (idcredencial, usuario, senha, tipo) FROM stdin;
\.
COPY public.credenciais (idcredencial, usuario, senha, tipo) FROM '$$PATH$$/4882.dat';

--
-- Data for Name: demanda; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.demanda (iditem, idcliente, datahora, contagem) FROM stdin;
\.
COPY public.demanda (iditem, idcliente, datahora, contagem) FROM '$$PATH$$/4896.dat';

--
-- Data for Name: desconto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.desconto (iddesconto, tipo, percentual, descricao, pontosminimos) FROM stdin;
\.
COPY public.desconto (iddesconto, tipo, percentual, descricao, pontosminimos) FROM '$$PATH$$/4895.dat';

--
-- Data for Name: estoque; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.estoque (idproduto, preco, descricao, quantidade) FROM stdin;
\.
COPY public.estoque (idproduto, preco, descricao, quantidade) FROM '$$PATH$$/4890.dat';

--
-- Data for Name: fidelidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fidelidade (idcliente, pontos, descontoultimacompra) FROM stdin;
\.
COPY public.fidelidade (idcliente, pontos, descontoultimacompra) FROM '$$PATH$$/4893.dat';

--
-- Data for Name: funcionario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.funcionario (idfuncionario, idcredencial, nome) FROM stdin;
\.
COPY public.funcionario (idfuncionario, idcredencial, nome) FROM '$$PATH$$/4884.dat';

--
-- Data for Name: itemdemandado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.itemdemandado (iditem, descricao) FROM stdin;
\.
COPY public.itemdemandado (iditem, descricao) FROM '$$PATH$$/4888.dat';

--
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.produto (idproduto, preco, descricao) FROM stdin;
\.
COPY public.produto (idproduto, preco, descricao) FROM '$$PATH$$/4886.dat';

--
-- Data for Name: transacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transacao (idtransacao, idproduto, idfuncionario, idcliente, preco, descricao, quantidade, datahora, tipo, iddesconto) FROM stdin;
\.
COPY public.transacao (idtransacao, idproduto, idfuncionario, idcliente, preco, descricao, quantidade, datahora, tipo, iddesconto) FROM '$$PATH$$/4898.dat';

--
-- Name: cliente_idcliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_idcliente_seq', 30, true);


--
-- Name: credenciais_idcredencial_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.credenciais_idcredencial_seq', 2, true);


--
-- Name: desconto_iddesconto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.desconto_iddesconto_seq', 12, true);


--
-- Name: estoque_idproduto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.estoque_idproduto_seq', 4, true);


--
-- Name: funcionario_idfuncionario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.funcionario_idfuncionario_seq', 2, true);


--
-- Name: itemdemandado_iditem_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.itemdemandado_iditem_seq', 2, true);


--
-- Name: produto_idproduto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produto_idproduto_seq', 4, true);


--
-- Name: transacao_idtransacao_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transacao_idtransacao_seq', 182, true);


--
-- Name: cliente cliente_cpf_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_cpf_key UNIQUE (cpf);


--
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (idcliente);


--
-- Name: credenciais credenciais_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credenciais
    ADD CONSTRAINT credenciais_pkey PRIMARY KEY (idcredencial);


--
-- Name: credenciais credenciais_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credenciais
    ADD CONSTRAINT credenciais_usuario_key UNIQUE (usuario);


--
-- Name: demanda demanda_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.demanda
    ADD CONSTRAINT demanda_pkey PRIMARY KEY (iditem, idcliente);


--
-- Name: desconto desconto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.desconto
    ADD CONSTRAINT desconto_pkey PRIMARY KEY (iddesconto);


--
-- Name: estoque estoque_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estoque
    ADD CONSTRAINT estoque_pkey PRIMARY KEY (idproduto);


--
-- Name: fidelidade fidelidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fidelidade
    ADD CONSTRAINT fidelidade_pkey PRIMARY KEY (idcliente);


--
-- Name: funcionario funcionario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_pkey PRIMARY KEY (idfuncionario);


--
-- Name: itemdemandado itemdemandado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itemdemandado
    ADD CONSTRAINT itemdemandado_pkey PRIMARY KEY (iditem);


--
-- Name: produto produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (idproduto);


--
-- Name: transacao transacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_pkey PRIMARY KEY (idtransacao);


--
-- Name: demanda demanda_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.demanda
    ADD CONSTRAINT demanda_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);


--
-- Name: demanda demanda_iditem_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.demanda
    ADD CONSTRAINT demanda_iditem_fkey FOREIGN KEY (iditem) REFERENCES public.itemdemandado(iditem);


--
-- Name: estoque estoque_idproduto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estoque
    ADD CONSTRAINT estoque_idproduto_fkey FOREIGN KEY (idproduto) REFERENCES public.produto(idproduto);


--
-- Name: fidelidade fidelidade_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fidelidade
    ADD CONSTRAINT fidelidade_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);


--
-- Name: transacao fk_desconto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT fk_desconto FOREIGN KEY (iddesconto) REFERENCES public.desconto(iddesconto);


--
-- Name: funcionario funcionario_idcredencial_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_idcredencial_fkey FOREIGN KEY (idcredencial) REFERENCES public.credenciais(idcredencial);


--
-- Name: transacao transacao_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);


--
-- Name: transacao transacao_idfuncionario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_idfuncionario_fkey FOREIGN KEY (idfuncionario) REFERENCES public.funcionario(idfuncionario);


--
-- Name: transacao transacao_idproduto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transacao
    ADD CONSTRAINT transacao_idproduto_fkey FOREIGN KEY (idproduto) REFERENCES public.produto(idproduto);


--
-- PostgreSQL database dump complete
--

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           