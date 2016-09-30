CREATE TABLE tb_arquivo_doc (id_arquivo BIGINT NOT NULL, arquivo bytea, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_arquivo))
CREATE TABLE tb_categoria (id_categoria BIGINT NOT NULL, descricao VARCHAR(255), situacao INTEGER, id_categoria_pai BIGINT, PRIMARY KEY (id_categoria))
CREATE TABLE tb_documento (id_documento BIGINT NOT NULL, data_documento TIMESTAMP, data_inclusao TIMESTAMP, data_ultima_alteracao TIMESTAMP, descricao VARCHAR(255), observacao VARCHAR(255), situacao VARCHAR(255), id_arquivo BIGINT, id_categoria BIGINT, id_tipo_doc BIGINT, id_usuario BIGINT, PRIMARY KEY (id_documento))
CREATE TABLE tb_grupo_usuario (id_grupo_usuario BIGINT NOT NULL, grupo VARCHAR(255), situacao VARCHAR(255), PRIMARY KEY (id_grupo_usuario))
CREATE TABLE tb_pessoa (id_pessoa BIGINT NOT NULL, celular VARCHAR(255), cpf VARCHAR(255), email VARCHAR(255), nome VARCHAR(255), rg INTEGER, situacao VARCHAR(255), PRIMARY KEY (id_pessoa))
CREATE TABLE tb_tipo_doc (id_tipo_doc BIGINT NOT NULL, descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_tipo_doc))
CREATE TABLE tb_usuario (id_usuario BIGINT NOT NULL, logado_sistema SMALLINT DEFAULT 0, role VARCHAR(255), senha VARCHAR(60) NOT NULL, situacao VARCHAR(255), usuario VARCHAR(45) NOT NULL UNIQUE, id_pessoa BIGINT, PRIMARY KEY (id_usuario))
CREATE TABLE tb_balancete (id_balancete BIGINT NOT NULL, ano INTEGER, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, mes INTEGER, observacao VARCHAR(255), orgao VARCHAR(255), situacao VARCHAR(255), volume VARCHAR(255), id_arquivo BIGINT, id_usuario BIGINT, PRIMARY KEY (id_balancete))
CREATE TABLE tb_arquivo_balancet (id_arquivo_balanc BIGINT NOT NULL, arquivo bytea, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_arquivo_balanc))
CREATE TABLE tb_arquivo_rh (id_arquivo_rh BIGINT NOT NULL, arquivo bytea, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_arquivo_rh))
CREATE TABLE tb_rh (id_rh BIGINT NOT NULL, data_documento TIMESTAMP, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, observacao VARCHAR(255), situacao VARCHAR(255), tipo_documento VARCHAR(255), id_arquivo BIGINT, id_pessoa BIGINT, id_usuario BIGINT, PRIMARY KEY (id_rh))
CREATE TABLE tb_arq_proc_licit (id_arq_proc_licit BIGINT NOT NULL, arquivo bytea, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_arq_proc_licit))
CREATE TABLE tb_proc_licit (id_proc_licit BIGINT NOT NULL, ano INTEGER, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, modalidade_licit INTEGER, num_processo VARCHAR(255), objeto VARCHAR(255), observacao VARCHAR(255), situacao VARCHAR(255), id_arquivo BIGINT, id_usuario BIGINT, PRIMARY KEY (id_proc_licit))
CREATE TABLE tb_arq_lei (id_arq_lei BIGINT NOT NULL, arquivo bytea, content_type VARCHAR(255), descricao VARCHAR(255), situacao INTEGER, PRIMARY KEY (id_arq_lei))
CREATE TABLE tb_lei (id_lei BIGINT NOT NULL, ano INTEGER, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, num_lei VARCHAR(255), objeto VARCHAR(255), observacao VARCHAR(255), situacao VARCHAR(255), id_arquivo BIGINT, id_usuario BIGINT, PRIMARY KEY (id_lei))
CREATE TABLE tb_arquivo_doc_audit (id_arquivo_audit BIGINT NOT NULL, arquivo bytea, content_type VARCHAR(255), descricao VARCHAR(255), id_entidade BIGINT, situacao INTEGER, tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, PRIMARY KEY (id_arquivo_audit))
CREATE TABLE tb_categoria_audit (id_categoria_audit BIGINT NOT NULL, descricao VARCHAR(255), id_entidade BIGINT, situacao INTEGER, tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, id_categoria_pai BIGINT, PRIMARY KEY (id_categoria_audit))
CREATE TABLE tb_documento_audit (id_documento_audit BIGINT NOT NULL, data_documento TIMESTAMP, data_inclusao TIMESTAMP, data_ultima_alteracao TIMESTAMP, descricao VARCHAR(255), id_entidade BIGINT, observacao VARCHAR(255), situacao VARCHAR(255), tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, id_arquivo BIGINT, id_categoria BIGINT, id_tipo_doc BIGINT, id_usuario BIGINT, PRIMARY KEY (id_documento_audit))
CREATE TABLE tb_grup_usr_audit (id_grup_usr_audit BIGINT NOT NULL, grupo VARCHAR(255), id_entidade BIGINT, situacao VARCHAR(255), tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, PRIMARY KEY (id_grup_usr_audit))
CREATE TABLE tb_pessoa_audit (id_pessoa_audit BIGINT NOT NULL, celular VARCHAR(255), cpf VARCHAR(255), email VARCHAR(255), id_entidade BIGINT, nome VARCHAR(255), rg INTEGER, situacao VARCHAR(255), tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, PRIMARY KEY (id_pessoa_audit))
CREATE TABLE tb_tp_doc_audit (id_tp_doc_audit BIGINT NOT NULL, descricao VARCHAR(255), id_entidade BIGINT, situacao INTEGER, tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, PRIMARY KEY (id_tp_doc_audit))
CREATE TABLE tb_usr_audit (id_usr_audit BIGINT NOT NULL, id_entidade BIGINT, logado_sistema SMALLINT DEFAULT 0, role VARCHAR(255), senha VARCHAR(255), situacao VARCHAR(255), tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, usuario VARCHAR(255), id_pessoa BIGINT, PRIMARY KEY (id_usr_audit))
CREATE TABLE tb_balancete_audit (id_balancete_audit BIGINT NOT NULL, ano INTEGER, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, id_entidade BIGINT, mes INTEGER, observacao VARCHAR(255), orgao VARCHAR(255), situacao VARCHAR(255), tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, volume VARCHAR(255), id_arquivo BIGINT, id_usuario BIGINT, PRIMARY KEY (id_balancete_audit))
CREATE TABLE tb_arquivo_balancet_audit (id_arquivo_balanc_audit BIGINT NOT NULL, arquivo bytea, content_type VARCHAR(255), descricao VARCHAR(255), id_entidade BIGINT, situacao INTEGER, tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, PRIMARY KEY (id_arquivo_balanc_audit))
CREATE TABLE tb_arquivo_rh_audit (id_arquivo_rh_audit BIGINT NOT NULL, arquivo bytea, content_type VARCHAR(255), descricao VARCHAR(255), id_entidade BIGINT, situacao INTEGER, tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, PRIMARY KEY (id_arquivo_rh_audit))
CREATE TABLE tb_rh_audit (id_rh BIGINT NOT NULL, data_documento TIMESTAMP, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, id_entidade BIGINT, observacao VARCHAR(255), situacao VARCHAR(255), tempo INTEGER, tipo_documento VARCHAR(255), tipo_operacao_auditoria VARCHAR(255) NOT NULL, id_arquivo BIGINT, id_pessoa BIGINT, id_usuario BIGINT, PRIMARY KEY (id_rh))
CREATE TABLE tb_arq_proc_licit_audit (id_arq_proc_licit_audit BIGINT NOT NULL, arquivo bytea, content_type VARCHAR(255), descricao VARCHAR(255), id_entidade BIGINT, situacao INTEGER, tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, PRIMARY KEY (id_arq_proc_licit_audit))
CREATE TABLE tb_proc_licit_audit (id_proc_licit_audit BIGINT NOT NULL, ano INTEGER, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, id_entidade BIGINT, modalidade_licit INTEGER, num_processo VARCHAR(255), objeto VARCHAR(255), observacao VARCHAR(255), situacao VARCHAR(255), tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, id_arquivo BIGINT, id_usuario BIGINT, PRIMARY KEY (id_proc_licit_audit))
CREATE TABLE tb_arq_lei_audit (id_arq_lei_audit BIGINT NOT NULL, arquivo bytea, content_type VARCHAR(255), descricao VARCHAR(255), id_entidade BIGINT, situacao INTEGER, tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, PRIMARY KEY (id_arq_lei_audit))
CREATE TABLE tb_lei_audit (id_lei_audit BIGINT NOT NULL, ano INTEGER, data_indexacao TIMESTAMP, data_ultima_alteracao TIMESTAMP, id_entidade BIGINT, num_lei VARCHAR(255), objeto VARCHAR(255), observacao VARCHAR(255), situacao VARCHAR(255), tempo INTEGER, tipo_operacao_auditoria VARCHAR(255) NOT NULL, id_arquivo BIGINT, id_usuario BIGINT, PRIMARY KEY (id_lei_audit))
CREATE TABLE rl_categoria_grupousuario (id_categoria BIGINT NOT NULL, id_grupo_usuario BIGINT NOT NULL, PRIMARY KEY (id_categoria, id_grupo_usuario))
CREATE TABLE rl_func_grupo_usuario (GrupoUsuario_id_grupo_usuario BIGINT, id_funcionalidade VARCHAR(255))
CREATE TABLE rl_tipo_func_grupo_usuario (GrupoUsuario_id_grupo_usuario BIGINT, id_permissao VARCHAR(255))
CREATE TABLE rl_grupousuario_usuario (id_grupo_usuario BIGINT NOT NULL, id_usuario BIGINT NOT NULL, PRIMARY KEY (id_grupo_usuario, id_usuario))
CREATE TABLE rl_cat_grup_usr_audit (id_categoria BIGINT NOT NULL, id_grupo_usuario BIGINT NOT NULL, PRIMARY KEY (id_categoria, id_grupo_usuario))
CREATE TABLE rl_func_grup_usr_audit (GrupoUsuarioAudit_id_grup_usr_audit BIGINT, id_funcionalidade VARCHAR(255))
CREATE TABLE rl_tp_func_grup_usr_audit (GrupoUsuarioAudit_id_grup_usr_audit BIGINT, id_permissao VARCHAR(255))
CREATE TABLE rl_grupousr_usr (id_grupo_usuario BIGINT NOT NULL, id_usuario BIGINT NOT NULL, PRIMARY KEY (id_grupo_usuario, id_usuario))
ALTER TABLE tb_categoria ADD CONSTRAINT tbctgriadctgriapai FOREIGN KEY (id_categoria_pai) REFERENCES tb_categoria (id_categoria)
ALTER TABLE tb_documento ADD CONSTRAINT tbdocumentodrquivo FOREIGN KEY (id_arquivo) REFERENCES tb_arquivo_doc (id_arquivo)
ALTER TABLE tb_documento ADD CONSTRAINT tbdocumentodsuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario)
ALTER TABLE tb_documento ADD CONSTRAINT tbdcumentodtipodoc FOREIGN KEY (id_tipo_doc) REFERENCES tb_tipo_doc (id_tipo_doc)
ALTER TABLE tb_documento ADD CONSTRAINT tbdcmentodctegoria FOREIGN KEY (id_categoria) REFERENCES tb_categoria (id_categoria)
ALTER TABLE tb_usuario ADD CONSTRAINT tbusuarioid_pessoa FOREIGN KEY (id_pessoa) REFERENCES tb_pessoa (id_pessoa)
ALTER TABLE tb_balancete ADD CONSTRAINT tbbalancetedrquivo FOREIGN KEY (id_arquivo) REFERENCES tb_arquivo_balancet (id_arquivo_balanc)
ALTER TABLE tb_balancete ADD CONSTRAINT tbbalancetedsuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario)
ALTER TABLE tb_rh ADD CONSTRAINT tb_rh_id_arquivo FOREIGN KEY (id_arquivo) REFERENCES tb_arquivo_rh (id_arquivo_rh)
ALTER TABLE tb_rh ADD CONSTRAINT tb_rh_id_usuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario)
ALTER TABLE tb_rh ADD CONSTRAINT FK_tb_rh_id_pessoa FOREIGN KEY (id_pessoa) REFERENCES tb_pessoa (id_pessoa)
ALTER TABLE tb_proc_licit ADD CONSTRAINT tbproclicitdrquivo FOREIGN KEY (id_arquivo) REFERENCES tb_arq_proc_licit (id_arq_proc_licit)
ALTER TABLE tb_proc_licit ADD CONSTRAINT tbproclicitdsuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario)
ALTER TABLE tb_lei ADD CONSTRAINT tb_lei_id_arquivo FOREIGN KEY (id_arquivo) REFERENCES tb_arq_lei (id_arq_lei)
ALTER TABLE tb_lei ADD CONSTRAINT tb_lei_id_usuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario)
ALTER TABLE tb_categoria_audit ADD CONSTRAINT tbctgruditdctgrpai FOREIGN KEY (id_categoria_pai) REFERENCES tb_categoria_audit (id_categoria_audit)
ALTER TABLE tb_documento_audit ADD CONSTRAINT tbdcmntoauditdsrio FOREIGN KEY (id_usuario) REFERENCES tb_usr_audit (id_usr_audit)
ALTER TABLE tb_documento_audit ADD CONSTRAINT tbdcmntauditdctgra FOREIGN KEY (id_categoria) REFERENCES tb_categoria_audit (id_categoria_audit)
ALTER TABLE tb_documento_audit ADD CONSTRAINT tbdcmntoauditdrqvo FOREIGN KEY (id_arquivo) REFERENCES tb_arquivo_doc_audit (id_arquivo_audit)
ALTER TABLE tb_documento_audit ADD CONSTRAINT tbdcmntoauditdtpdc FOREIGN KEY (id_tipo_doc) REFERENCES tb_tp_doc_audit (id_tp_doc_audit)
ALTER TABLE tb_usr_audit ADD CONSTRAINT tbusrauditidpessoa FOREIGN KEY (id_pessoa) REFERENCES tb_pessoa_audit (id_pessoa_audit)
ALTER TABLE tb_balancete_audit ADD CONSTRAINT tbblnceteauditdsro FOREIGN KEY (id_usuario) REFERENCES tb_usr_audit (id_usr_audit)
ALTER TABLE tb_balancete_audit ADD CONSTRAINT tbblncteauditdrqvo FOREIGN KEY (id_arquivo) REFERENCES tb_arquivo_balancet_audit (id_arquivo_balanc_audit)
ALTER TABLE tb_rh_audit ADD CONSTRAINT tbrhauditidarquivo FOREIGN KEY (id_arquivo) REFERENCES tb_arquivo_rh_audit (id_arquivo_rh_audit)
ALTER TABLE tb_rh_audit ADD CONSTRAINT tbrhauditidusuario FOREIGN KEY (id_usuario) REFERENCES tb_usr_audit (id_usr_audit)
ALTER TABLE tb_rh_audit ADD CONSTRAINT tbrhauditid_pessoa FOREIGN KEY (id_pessoa) REFERENCES tb_pessoa_audit (id_pessoa_audit)
ALTER TABLE tb_proc_licit_audit ADD CONSTRAINT tbprclcitauditdsro FOREIGN KEY (id_usuario) REFERENCES tb_usr_audit (id_usr_audit)
ALTER TABLE tb_proc_licit_audit ADD CONSTRAINT tbprclcitauditdrqv FOREIGN KEY (id_arquivo) REFERENCES tb_arq_proc_licit_audit (id_arq_proc_licit_audit)
ALTER TABLE tb_lei_audit ADD CONSTRAINT tbleiauditdarquivo FOREIGN KEY (id_arquivo) REFERENCES tb_arq_lei_audit (id_arq_lei_audit)
ALTER TABLE tb_lei_audit ADD CONSTRAINT tbleiauditdusuario FOREIGN KEY (id_usuario) REFERENCES tb_usr_audit (id_usr_audit)
ALTER TABLE rl_categoria_grupousuario ADD CONSTRAINT rlctgrgrpsriodctgr FOREIGN KEY (id_categoria) REFERENCES tb_categoria (id_categoria)
ALTER TABLE rl_categoria_grupousuario ADD CONSTRAINT rlctgrgrpsrodgrpsr FOREIGN KEY (id_grupo_usuario) REFERENCES tb_grupo_usuario (id_grupo_usuario)
ALTER TABLE rl_func_grupo_usuario ADD CONSTRAINT rlfncgrGrpsrdgrpsr FOREIGN KEY (GrupoUsuario_id_grupo_usuario) REFERENCES tb_grupo_usuario (id_grupo_usuario)
ALTER TABLE rl_tipo_func_grupo_usuario ADD CONSTRAINT rltpfncGrpsrdgrpsr FOREIGN KEY (GrupoUsuario_id_grupo_usuario) REFERENCES tb_grupo_usuario (id_grupo_usuario)
ALTER TABLE rl_grupousuario_usuario ADD CONSTRAINT rlgrpsrsariodgrpsr FOREIGN KEY (id_grupo_usuario) REFERENCES tb_grupo_usuario (id_grupo_usuario)
ALTER TABLE rl_grupousuario_usuario ADD CONSTRAINT rlgrpsrousuariodsr FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id_usuario)
ALTER TABLE rl_cat_grup_usr_audit ADD CONSTRAINT rlctgrpsruditdctgr FOREIGN KEY (id_categoria) REFERENCES tb_categoria_audit (id_categoria_audit)
ALTER TABLE rl_cat_grup_usr_audit ADD CONSTRAINT rlctgrpsrditdgrpsr FOREIGN KEY (id_grupo_usuario) REFERENCES tb_grup_usr_audit (id_grup_usr_audit)
ALTER TABLE rl_func_grup_usr_audit ADD CONSTRAINT rlfGrpsrdtdgrpsrdt FOREIGN KEY (GrupoUsuarioAudit_id_grup_usr_audit) REFERENCES tb_grup_usr_audit (id_grup_usr_audit)
ALTER TABLE rl_tp_func_grup_usr_audit ADD CONSTRAINT rltGrpsrdtdgrpsrdt FOREIGN KEY (GrupoUsuarioAudit_id_grup_usr_audit) REFERENCES tb_grup_usr_audit (id_grup_usr_audit)
ALTER TABLE rl_grupousr_usr ADD CONSTRAINT rlgrpousrusrdsario FOREIGN KEY (id_usuario) REFERENCES tb_usr_audit (id_usr_audit)
ALTER TABLE rl_grupousr_usr ADD CONSTRAINT rlgrpsrsrdgrpsario FOREIGN KEY (id_grupo_usuario) REFERENCES tb_grup_usr_audit (id_grup_usr_audit)
CREATE SEQUENCE seq_arquivo_balanc_audit START WITH 1
CREATE SEQUENCE seq_balancete_audit START WITH 1
CREATE SEQUENCE seq_categoria START WITH 1
CREATE SEQUENCE seq_categoria_audit START WITH 1
CREATE SEQUENCE seq_documento_audit START WITH 1
CREATE SEQUENCE seq_rh START WITH 1
CREATE SEQUENCE seq_usr_audit START WITH 1
CREATE SEQUENCE seq_arq_proc_licit_audit START WITH 1
CREATE SEQUENCE seq_tipo_doc START WITH 1
CREATE SEQUENCE seq_arq_lei START WITH 1
CREATE SEQUENCE seq_proc_licit START WITH 1
CREATE SEQUENCE seq_lei START WITH 1
CREATE SEQUENCE seq_arquivo_rh START WITH 1
CREATE SEQUENCE seq_grupo_usuario START WITH 1
CREATE SEQUENCE seq_documento START WITH 1
CREATE SEQUENCE seq_arq_proc_licit START WITH 1
CREATE SEQUENCE seq_arq_lei_audit START WITH 1
CREATE SEQUENCE seq_pessoa_audit START WITH 1
CREATE SEQUENCE seq_lei_audit START WITH 1
CREATE SEQUENCE seq_pessoa START WITH 1
CREATE SEQUENCE seq_arquivo_rh_audit START WITH 1
CREATE SEQUENCE seq_usuario START WITH 1
CREATE SEQUENCE seq_balancete START WITH 1
CREATE SEQUENCE seq_arquivo_balanc START WITH 1
CREATE SEQUENCE seq_tp_doc_audit START WITH 1
CREATE SEQUENCE seq_arquivo_doc START WITH 1
CREATE SEQUENCE seq_grup_usr_audit START WITH 1
CREATE SEQUENCE seq_proc_licit_audit START WITH 1
CREATE SEQUENCE seq_arquivo_doc_audit START WITH 1
