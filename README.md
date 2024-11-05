# 🍔 FaiskaBurguer (EM DESENVOLVIMENTO)

Sistema de gerenciamento de pedidos para lanchonetes e restaurantes, desenvolvido em Java com JavaFX e PostgreSQL. O projeto visa otimizar o processo de gerenciamento de pedidos e pagamentos, oferecendo uma interface intuitiva e funcionalidades avançadas para a equipe operacional de estabelecimentos alimentícios.

## Visão Geral

O FaiskaBurguer é um sistema de pedidos para lanchonetes e restaurantes, que permite gerenciar produtos, registrar pedidos de clientes, calcular o total do pedido, e selecionar métodos de pagamento.

## Funcionalidades

- **Cadastro de Produtos e Tipos de Pagamento**: Permite registrar produtos e diferentes formas de pagamento.
- **Gerenciamento de Pedidos**: Adiciona produtos a um pedido com quantidade e valor, incluindo controle de pedidos duplicados.
- **Controle de Entrega**: Possibilidade de marcar pedidos como "para viagem" e adicionar taxa de entrega.
- **Cálculo Automático do Total**: Calcula o valor total do pedido automaticamente ao adicionar ou remover produtos.
- **Banco de Dados**: Utiliza PostgreSQL para armazenamentodos dados.
  
## Tecnologias Utilizadas

- **Java 21+** com **JavaFX** para a interface gráfica
- **PostgreSQL** para o banco de dados
- **JDBC** para abstração do banco de dados - OBS: pretendo migrar ao JPA

## Pré-Requisitos

- **JDK 21+**
- **PostgreSQL** instalado e configurado
- **Maven** para gerenciamento de dependências

## Estrutura do Projeto

- `controllers`: Contém os controladores do JavaFX que gerenciam a lógica da interface gráfica.
- `db/dal`: Classes de Acesso a Dados (DAL) para interagir com o banco de dados.
- `db/entidade`: Modelos de entidade para representar os dados no banco.
- `db/util`: Para conexão com o banco de dados, utilizando o Padrão de Projeto Singleton.
- `db/viacep`: Para conexão com a API ViaCep.
- `views`: Arquivos FXML que definem a interface gráfica.

## Instruções de Uso

1. **Considerando que o banco de dados esta com as informações cadastradas, como: Tipo de pagamento e Categorias.**
2. **Registrar um Pedido**:
   - Preencha os dados do cliente.
   - Selecione os produtos e ajuste a quantidade, se necessário.
   - Escolha o tipo de pagamento.
   - Marque a opção "para viagem" se o pedido for de entrega, o que adicionará uma taxa ao total.
3. **Finalizar Pedido**: Após confirmar o pedido, ele será salvo no banco de dados, e a interface será resetada para novos pedidos.

## Contribuição

1. Faça um fork do projeto.
2. Crie uma nova branch para sua feature (`git checkout -b feature/sua-feature`).
3. Commit suas alterações (`git commit -am 'Adiciona nova feature'`).
4. Envie suas alterações para o GitHub (`git push origin feature/sua-feature`).
5. Abra um Pull Request.
