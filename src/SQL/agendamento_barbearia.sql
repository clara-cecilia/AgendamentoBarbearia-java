/* 1. Cria o banco de dados */
CREATE DATABASE agendamento_barbearia;

/* 2. Seleciona o banco para usar */
USE agendamento_barbearia;

select * from agendamentos;
/* 3. Cria a tabela 'agendamentos' */
CREATE TABLE agendamentos (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              nome_cliente VARCHAR(100) NOT NULL,
                              servico ENUM('Corte', 'Barba', 'Corte e Barba', 'Outro') NOT NULL,
                              data_hora DATETIME NOT NULL,
                              status ENUM('Agendado', 'Concluído', 'Cancelado') DEFAULT 'Agendado'
);

/* 4. Insere alguns dados de exemplo */
INSERT INTO agendamentos (nome_cliente, servico, data_hora) VALUES
                                                                ('Carlos Silva', 'Corte', '2025-11-15 10:00:00'),
                                                                ('Marcos Almeida', 'Barba', '2025-11-15 11:30:00'),
                                                                ('Rafael Souza', 'Corte e Barba', '2025-11-16 14:00:00');