CREATE TABLE tb_arquivo_doc (id_arquivo BIGINT NOT NULL, arquivo BYTEA, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_arquivo));
CREATE TABLE tb_categoria (id_categoria BIGINT NOT NULL, descricao VARCHAR(255), situacao INTEGER, id_categoria_pai BIGINT, PRIMARY KEY (id_categoria));
CREATE TABLE tb_documento (id_documento BIGINT NOT NULL, data_documento TIMESTAMP, data_inclusao TIMESTAMP, data_ultima_alteracao TIMESTAMP, descricao VARCHAR(255), observacao VARCHAR(255), situacao VARCHAR(255), id_arquivo BIGINT, id_categoria BIGINT, id_tipo_doc BIGINT, id_usuario BIGINT, PRIMARY KEY (id_documento));
CREATE TABLE tb_grupo_usuario (id_grupo_usuario BIGINT NOT NULL, grupo VARCHAR(255), situacao VARCHAR(255), PRIMARY KEY (id_grupo_usuario));
CREATE TABLE tb_pessoa (id_pessoa BIGINT NOT NULL, celular VARCHAR(255), cpf VARCHAR(255), email VARCHAR(255), nome VARCHAR(255), rg INTEGER, situacao VARCHAR(255), PRIMARY KEY (id_pessoa));
CREATE TABLE tb_tipo_doc (id_tipo_doc BIGINT NOT NULL, descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_tipo_doc));
CREATE TABLE tb_usuario (id_usuario BIGINT NOT NULL, logado_sistema SMALLINT DEFAULT 0, role VARCHAR(255), senha VARCHAR(60) NOT NULL, situacao VARCHAR(255), usuario VARCHAR(45) NOT NULL UNIQUE, id_pessoa BIGINT, PRIMARY KEY (id_usuario));
CREATE TABLE tb_balancete (id_balancete BIGINT NOT NULL, ano INTEGER, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, mes INTEGER, observacao VARCHAR(255), orgao VARCHAR(255), situacao VARCHAR(255), volume VARCHAR(255), id_arquivo BIGINT, id_usuario BIGINT, PRIMARY KEY (id_balancete));
CREATE TABLE tb_arquivo_balancet (id_arquivo_balanc BIGINT NOT NULL, arquivo BYTEA, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_arquivo_balanc));
CREATE TABLE tb_arquivo_rh (id_arquivo_rh BIGINT NOT NULL, arquivo BYTEA, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_arquivo_rh));
CREATE TABLE tb_rh (id_rh BIGINT NOT NULL, data_documento TIMESTAMP, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, observacao VARCHAR(255), situacao VARCHAR(255), tipo_documento VARCHAR(255), id_arquivo BIGINT, id_pessoa BIGINT, id_usuario BIGINT, PRIMARY KEY (id_rh));
CREATE TABLE tb_arq_proc_licit (id_arq_proc_licit BIGINT NOT NULL, arquivo BYTEA, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_arq_proc_licit));
CREATE TABLE tb_proc_licit (id_proc_licit BIGINT NOT NULL, ano INTEGER, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, modalidade_licit INTEGER, num_processo VARCHAR(255), objeto VARCHAR(255), observacao VARCHAR(255), situacao VARCHAR(255), id_arquivo BIGINT, id_usuario BIGINT, PRIMARY KEY (id_proc_licit));
CREATE TABLE tb_arq_lei (id_arq_lei BIGINT NOT NULL, arquivo BYTEA, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_arq_lei));
CREATE TABLE tb_lei (id_lei BIGINT NOT NULL, ano INTEGER, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, num_lei VARCHAR(255), objeto VARCHAR(255), observacao VARCHAR(255), situacao VARCHAR(255), id_arquivo BIGINT, id_usuario BIGINT, PRIMARY KEY (id_lei));
CREATE TABLE tb_arq_audit (arquivo BYTEA, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, id_arquivo BIGINT NOT NULL, tp_operacao VARCHAR(255) NOT NULL, data_hora BIGINT NOT NULL, PRIMARY KEY (id_arquivo, tp_operacao, data_hora));
CREATE TABLE tb_cat_audit (descricao VARCHAR(255), situacao INTEGER, id_categoria BIGINT NOT NULL, tp_operacao VARCHAR(255) NOT NULL, data_hora BIGINT NOT NULL, tipo_op_cat_pai VARCHAR(255), id_cat_pai BIGINT, id_dt_hora_cat_pai BIGINT, PRIMARY KEY (id_categoria, tp_operacao, data_hora));
CREATE TABLE tb_doc_audit (data_documento TIMESTAMP, data_inclusao TIMESTAMP, data_ultima_alteracao TIMESTAMP, descricao VARCHAR(255), observacao VARCHAR(255), situacao VARCHAR(255), id_documento BIGINT NOT NULL, tp_operacao VARCHAR(255) NOT NULL, data_hora BIGINT NOT NULL, tipo_op_arq VARCHAR(255), id_arq_audit BIGINT, id_dt_hora_arq BIGINT, id_dt_hora_cat BIGINT, tipo_op_cat VARCHAR(255), id_cat BIGINT, id_tp_doc_audit BIGINT, tipo_op_tp_doc VARCHAR(255), id_dt_hora_tp_doc BIGINT, id_dt_hora_usr BIGINT, id_usr_audit BIGINT, tipo_op_usr VARCHAR(255), PRIMARY KEY (id_documento, tp_operacao, data_hora));
CREATE TABLE tb_grp_audit (grupo VARCHAR(255), situacao VARCHAR(255), id_grupo_usuario BIGINT NOT NULL, tp_operacao VARCHAR(255) NOT NULL, data_hora BIGINT NOT NULL, PRIMARY KEY (id_grupo_usuario, tp_operacao, data_hora));
CREATE TABLE tb_pessoa_audit (celular VARCHAR(255), cpf VARCHAR(255), email VARCHAR(255), nome VARCHAR(255), rg INTEGER, situacao VARCHAR(255), id_pessoa BIGINT NOT NULL, tp_operacao VARCHAR(255) NOT NULL, data_hora BIGINT NOT NULL, PRIMARY KEY (id_pessoa, tp_operacao, data_hora));
CREATE TABLE tb_tp_doc_audit (descricao VARCHAR(255), situacao INTEGER, id_tp_doc BIGINT NOT NULL, tp_operacao VARCHAR(255) NOT NULL, data_hora BIGINT NOT NULL, PRIMARY KEY (id_tp_doc, tp_operacao, data_hora));
CREATE TABLE tb_usr_audit (logado_sistema SMALLINT DEFAULT 0, role VARCHAR(255), senha VARCHAR(255), situacao VARCHAR(255), usuario VARCHAR(255), id_usuario BIGINT NOT NULL, tp_operacao VARCHAR(255) NOT NULL, data_hora BIGINT NOT NULL, id_ent_pess BIGINT, id_data_hora BIGINT, tipo_op_audit_pess VARCHAR(255), PRIMARY KEY (id_usuario, tp_operacao, data_hora));
CREATE TABLE tb_balancete_audit (ano INTEGER, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, mes INTEGER, observacao VARCHAR(255), orgao VARCHAR(255), situacao VARCHAR(255), volume VARCHAR(255), id_balancete BIGINT NOT NULL, tp_operacao VARCHAR(255) NOT NULL, data_hora BIGINT NOT NULL, tipo_op_audit_arq VARCHAR(255), id_dt_hora_arq BIGINT, id_ent_arq BIGINT, id_dt_hora_usr BIGINT, tipo_op_audit_usr VARCHAR(255), id_ent_usr BIGINT, PRIMARY KEY (id_balancete, tp_operacao, data_hora));
CREATE TABLE tb_arquivo_balancet_audit (arquivo BYTEA, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, id_arq_balanc BIGINT NOT NULL, tp_operacao VARCHAR(255) NOT NULL, data_hora BIGINT NOT NULL, PRIMARY KEY (id_arq_balanc, tp_operacao, data_hora));
CREATE TABLE tb_arquivo_rh_audit (id_arquivo_rh_audit BIGINT NOT NULL, arquivo BYTEA, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_arquivo_rh_audit));
CREATE TABLE tb_arq_proc_licit_audit (id_arq_proc_licit_audit BIGINT NOT NULL, arquivo BYTEA, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_arq_proc_licit_audit));
CREATE TABLE tb_arq_lei_audit (arquivo BYTEA, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, id_arq_lei BIGINT NOT NULL, tp_operacao VARCHAR(255) NOT NULL, data_hora BIGINT NOT NULL, PRIMARY KEY (id_arq_lei, tp_operacao, data_hora));
CREATE TABLE tb_lei_audit (ano INTEGER, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, num_lei VARCHAR(255), objeto VARCHAR(255), observacao VARCHAR(255), situacao VARCHAR(255), id_lei BIGINT NOT NULL, tp_operacao VARCHAR(255) NOT NULL, data_hora BIGINT NOT NULL, tipo_op_audit_arq VARCHAR(255), id_dt_hora_arq BIGINT, id_ent_arq BIGINT, id_dt_hora_usr BIGINT, id_ent_usr_audit BIGINT, tipo_op_usr VARCHAR(255), PRIMARY KEY (id_lei, tp_operacao, data_hora));
CREATE TABLE rl_categoria_grupousuario (id_categoria BIGINT NOT NULL, id_grupo_usuario BIGINT NOT NULL, PRIMARY KEY (id_categoria, id_grupo_usuario));
CREATE TABLE rl_func_grupo_usuario (GrupoUsuario_id_grupo_usuario BIGINT, id_funcionalidade VARCHAR(255));
CREATE TABLE rl_tipo_func_grupo_usuario (GrupoUsuario_id_grupo_usuario BIGINT, id_permissao VARCHAR(255));
CREATE TABLE rl_grupousuario_usuario (id_grupo_usuario BIGINT NOT NULL, id_usuario BIGINT NOT NULL, PRIMARY KEY (id_grupo_usuario, id_usuario));
CREATE TABLE rl_cat_grup_usr_audit (tipo_op_cat VARCHAR(255) NOT NULL, id_cat_audit BIGINT NOT NULL, id_dt_hora_cat BIGINT NOT NULL, tipo_op_grp_usr VARCHAR(255) NOT NULL, id_cat_grp_usr_audit BIGINT NOT NULL, id_dt_hora_grp_usr BIGINT NOT NULL, PRIMARY KEY (tipo_op_cat, id_cat_audit, id_dt_hora_cat, tipo_op_grp_usr, id_cat_grp_usr_audit, id_dt_hora_grp_usr));
CREATE TABLE rl_func_grp_usr_audit (id_grupo_usuario BIGINT, tp_operacao VARCHAR(255), data_hora BIGINT, id_funcionalidade VARCHAR(255));
CREATE TABLE rl_tp_func_grp_usr_audit (id_grupo_usuario BIGINT, tp_operacao VARCHAR(255), data_hora BIGINT, id_permissao VARCHAR(255));
CREATE TABLE rl_grp_usr_audit (tipo_op_cat_grp_usr VARCHAR(255) NOT NULL, id_cat_grp_usr_audit BIGINT NOT NULL, id_dt_hora_cat_grp_usr BIGINT NOT NULL, tipo_op_audit_usr VARCHAR(255) NOT NULL, id_ent_usr BIGINT NOT NULL, id_dt_hora_usr BIGINT NOT NULL, PRIMARY KEY (tipo_op_cat_grp_usr, id_cat_grp_usr_audit, id_dt_hora_cat_grp_usr, tipo_op_audit_usr, id_ent_usr, id_dt_hora_usr));
ALTER TABLE tb_categoria ADD CONSTRAINT tbctgriadctgriapai FOREIGN KEY (id_categoria_pai) REFERENCES tb_categoria (id_categoria);
ALTER TABLE tb_documento ADD CONSTRAINT tbdocumentodrquivo FOREIGN KEY (id_arquivo) REFERENCES tb_arquivo_doc (id_arquivo);
ALTER TABLE tb_documento ADD CONSTRAINT tbdocumentodsuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario);
ALTER TABLE tb_documento ADD CONSTRAINT tbdcumentodtipodoc FOREIGN KEY (id_tipo_doc) REFERENCES tb_tipo_doc (id_tipo_doc);
ALTER TABLE tb_documento ADD CONSTRAINT tbdcmentodctegoria FOREIGN KEY (id_categoria) REFERENCES tb_categoria (id_categoria);
ALTER TABLE tb_usuario ADD CONSTRAINT tbusuarioid_pessoa FOREIGN KEY (id_pessoa) REFERENCES tb_pessoa (id_pessoa);
ALTER TABLE tb_balancete ADD CONSTRAINT tbbalancetedrquivo FOREIGN KEY (id_arquivo) REFERENCES tb_arquivo_balancet (id_arquivo_balanc);
ALTER TABLE tb_balancete ADD CONSTRAINT tbbalancetedsuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario);
ALTER TABLE tb_rh ADD CONSTRAINT tb_rh_id_arquivo FOREIGN KEY (id_arquivo) REFERENCES tb_arquivo_rh (id_arquivo_rh);
ALTER TABLE tb_rh ADD CONSTRAINT tb_rh_id_usuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario);
ALTER TABLE tb_rh ADD CONSTRAINT FK_tb_rh_id_pessoa FOREIGN KEY (id_pessoa) REFERENCES tb_pessoa (id_pessoa);
ALTER TABLE tb_proc_licit ADD CONSTRAINT tbproclicitdrquivo FOREIGN KEY (id_arquivo) REFERENCES tb_arq_proc_licit (id_arq_proc_licit);
ALTER TABLE tb_proc_licit ADD CONSTRAINT tbproclicitdsuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario);
ALTER TABLE tb_lei ADD CONSTRAINT tb_lei_id_arquivo FOREIGN KEY (id_arquivo) REFERENCES tb_arq_lei (id_arq_lei);
ALTER TABLE tb_lei ADD CONSTRAINT tb_lei_id_usuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario);
ALTER TABLE tb_cat_audit ADD CONSTRAINT tbcatauditidcatpai FOREIGN KEY (id_cat_pai, tipo_op_cat_pai, id_dt_hora_cat_pai) REFERENCES tb_cat_audit (id_categoria, tp_operacao, data_hora);
ALTER TABLE tb_doc_audit ADD CONSTRAINT tbdocauditdsraudit FOREIGN KEY (id_usr_audit, tipo_op_usr, id_dt_hora_usr) REFERENCES tb_usr_audit (id_usuario, tp_operacao, data_hora);
ALTER TABLE tb_doc_audit ADD CONSTRAINT tbdcuditdtpdcaudit FOREIGN KEY (id_tp_doc_audit, tipo_op_tp_doc, id_dt_hora_tp_doc) REFERENCES tb_tp_doc_audit (id_tp_doc, tp_operacao, data_hora);
ALTER TABLE tb_doc_audit ADD CONSTRAINT tb_doc_auditid_cat FOREIGN KEY (id_cat, tipo_op_cat, id_dt_hora_cat) REFERENCES tb_cat_audit (id_categoria, tp_operacao, data_hora);
ALTER TABLE tb_doc_audit ADD CONSTRAINT tbdocauditdrqaudit FOREIGN KEY (id_arq_audit, tipo_op_arq, id_dt_hora_arq) REFERENCES tb_arq_audit (id_arquivo, tp_operacao, data_hora);
ALTER TABLE tb_usr_audit ADD CONSTRAINT tbusrauditdentpess FOREIGN KEY (id_ent_pess, tipo_op_audit_pess, id_data_hora) REFERENCES tb_pessoa_audit (id_pessoa, tp_operacao, data_hora);
ALTER TABLE tb_balancete_audit ADD CONSTRAINT tbblncteauditdntrq FOREIGN KEY (id_ent_arq, tipo_op_audit_arq, id_dt_hora_arq) REFERENCES tb_arquivo_balancet_audit (id_arq_balanc, tp_operacao, data_hora);
ALTER TABLE tb_balancete_audit ADD CONSTRAINT tbblncteauditdntsr FOREIGN KEY (id_ent_usr, tipo_op_audit_usr, id_dt_hora_usr) REFERENCES tb_usr_audit (id_usuario, tp_operacao, data_hora);
ALTER TABLE tb_lei_audit ADD CONSTRAINT tbleiauditidentarq FOREIGN KEY (id_ent_arq, tipo_op_audit_arq, id_dt_hora_arq) REFERENCES tb_arq_lei_audit (id_arq_lei, tp_operacao, data_hora);
ALTER TABLE tb_lei_audit ADD CONSTRAINT tbluditdntusraudit FOREIGN KEY (id_ent_usr_audit, tipo_op_usr, id_dt_hora_usr) REFERENCES tb_usr_audit (id_usuario, tp_operacao, data_hora);
ALTER TABLE rl_categoria_grupousuario ADD CONSTRAINT rlctgrgrpsriodctgr FOREIGN KEY (id_categoria) REFERENCES tb_categoria (id_categoria);
ALTER TABLE rl_categoria_grupousuario ADD CONSTRAINT rlctgrgrpsrodgrpsr FOREIGN KEY (id_grupo_usuario) REFERENCES tb_grupo_usuario (id_grupo_usuario);
ALTER TABLE rl_func_grupo_usuario ADD CONSTRAINT rlfncgrGrpsrdgrpsr FOREIGN KEY (GrupoUsuario_id_grupo_usuario) REFERENCES tb_grupo_usuario (id_grupo_usuario);
ALTER TABLE rl_tipo_func_grupo_usuario ADD CONSTRAINT rltpfncGrpsrdgrpsr FOREIGN KEY (GrupoUsuario_id_grupo_usuario) REFERENCES tb_grupo_usuario (id_grupo_usuario);
ALTER TABLE rl_grupousuario_usuario ADD CONSTRAINT rlgrpsrsariodgrpsr FOREIGN KEY (id_grupo_usuario) REFERENCES tb_grupo_usuario (id_grupo_usuario);
ALTER TABLE rl_grupousuario_usuario ADD CONSTRAINT rlgrpsrousuariodsr FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario);
ALTER TABLE rl_cat_grup_usr_audit ADD CONSTRAINT rlctgrpsdctgrpsrdt FOREIGN KEY (id_cat_grp_usr_audit, tipo_op_grp_usr, id_dt_hora_grp_usr) REFERENCES tb_grp_audit (id_grupo_usuario, tp_operacao, data_hora);
ALTER TABLE rl_cat_grup_usr_audit ADD CONSTRAINT rlctgrpsruditdctdt FOREIGN KEY (id_cat_audit, tipo_op_cat, id_dt_hora_cat) REFERENCES tb_cat_audit (id_categoria, tp_operacao, data_hora);
ALTER TABLE rl_func_grp_usr_audit ADD CONSTRAINT rlfncgrpsrdtdgrpsr FOREIGN KEY (id_grupo_usuario, tp_operacao, data_hora) REFERENCES tb_grp_audit (id_grupo_usuario, tp_operacao, data_hora);
ALTER TABLE rl_tp_func_grp_usr_audit ADD CONSTRAINT rltpfncgrpsrdgrpsr FOREIGN KEY (id_grupo_usuario, tp_operacao, data_hora) REFERENCES tb_grp_audit (id_grupo_usuario, tp_operacao, data_hora);
ALTER TABLE rl_grp_usr_audit ADD CONSTRAINT rlgrpsrauditdntusr FOREIGN KEY (id_ent_usr, tipo_op_audit_usr, id_dt_hora_usr) REFERENCES tb_usr_audit (id_usuario, tp_operacao, data_hora);
ALTER TABLE rl_grp_usr_audit ADD CONSTRAINT rlgrpsrddctgrpsrdt FOREIGN KEY (id_cat_grp_usr_audit, tipo_op_cat_grp_usr, id_dt_hora_cat_grp_usr) REFERENCES tb_grp_audit (id_grupo_usuario, tp_operacao, data_hora);
CREATE SEQUENCE seq_arquivo_rh_audit START WITH 1;
CREATE SEQUENCE seq_pessoa START WITH 1;
CREATE SEQUENCE seq_arquivo_rh START WITH 1;
CREATE SEQUENCE seq_arquivo_balanc START WITH 1;
CREATE SEQUENCE seq_arquivo_doc START WITH 1;
CREATE SEQUENCE seq_documento START WITH 1;
CREATE SEQUENCE seq_arq_lei START WITH 1;
CREATE SEQUENCE seq_arq_proc_licit_audit START WITH 1;
CREATE SEQUENCE seq_lei START WITH 1;
CREATE SEQUENCE seq_proc_licit START WITH 1;
CREATE SEQUENCE seq_grupo_usuario START WITH 1;
CREATE SEQUENCE seq_categoria START WITH 1;
CREATE SEQUENCE seq_usuario START WITH 1;
CREATE SEQUENCE seq_balancete START WITH 1;
CREATE SEQUENCE seq_arq_proc_licit START WITH 1;
CREATE SEQUENCE seq_rh START WITH 1;
CREATE SEQUENCE seq_tipo_doc START WITH 1;
