-- Inserção de dados na tabela tb_address
INSERT INTO tb_address (bairro, cep, cidade, complemento, logradouro, uf) VALUES ('Centro', '12345000', 'São Paulo', 'Apto 101', 'Rua Principal', 'SP');
INSERT INTO tb_address (bairro, cep, cidade, complemento, logradouro, uf) VALUES ('Jardim', '98765000', 'Rio de Janeiro', 'Casa 1', 'Av. Secundária', 'RJ');

-- Inserção de dados na tabela tb_user
INSERT INTO tb_user (cpf, nome, password, address_id) VALUES ('00000000000', 'Administrador', '$2a$10$s0aE6BlolyvRNnjtIkuCgOFze5biYuZ.vuzSXZUhVe8Ig7rbJ5m5C', 1);
INSERT INTO tb_user (cpf, nome, password, address_id) VALUES ('11111111111', 'Usuario', '$2a$10$Z/lIGMmnM5r4QMp8NYsrRuQCR.FqAKW8cpNZxcsHy1EjNUO9ksyIm', 2);

-- Inserção de dados na tabela tb_role com base no enum RoleType
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_USER');

-- Inserção de dados na tabela tb_email
INSERT INTO tb_email (client_id, email) VALUES ( 1, 'Administrador@gmail.com');
INSERT INTO tb_email (client_id, email) VALUES ( 2, 'Usuario@gmail.com');

-- Inserção de dados na tabela tb_phone com base no enum PhoneType
INSERT INTO tb_phone (phone_type, client_id, numero) VALUES (0, 1, '1122334455');
INSERT INTO tb_phone (phone_type, client_id, numero) VALUES (1, 2, '9988776655');

-- Associando usuários às roles na tabela intermediária tb_user_role
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1); -- recebe a permissão ROLE_ADMIN
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 2); -- recebe a permissão ROLE_USER
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2); -- também recebe a permissão ROLE_USER

-- Inserção de dados na tabela tb_product
INSERT INTO tb_product (name, description, price) VALUES ('Smartphone Samsung Galaxy A54', 'Smartphone com tela Super AMOLED 6.4", 128GB de armazenamento, 8GB RAM, câmera tripla 50MP', 1799.90);
INSERT INTO tb_product (name, description, price) VALUES ('Notebook Lenovo IdeaPad 3i', 'Notebook com processador Intel Core i5, 8GB RAM, SSD 256GB, tela 15.6" Full HD, Windows 11', 2899.99);
INSERT INTO tb_product (name, description, price) VALUES ('Fone de Ouvido JBL Tune 510BT', 'Fone de ouvido Bluetooth, até 40 horas de bateria, conexão multiponto, microfone embutido', 199.99);
INSERT INTO tb_product (name, description, price) VALUES ('Smart TV LG 50" 4K', 'Smart TV LED 50" Ultra HD 4K, webOS, Wi-Fi, 3 HDMI, 2 USB, Bluetooth, HDR', 2499.90);
INSERT INTO tb_product (name, description, price) VALUES ('Impressora Multifuncional Epson EcoTank L3250', 'Impressora tanque de tinta colorida, Wi-Fi Direct, USB, imprime até 7.500 páginas em preto', 1149.50);