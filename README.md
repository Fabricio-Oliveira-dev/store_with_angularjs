# Loja de livros feita com Spring Framework, Spring Security, AngularJS e Bootstrap
Este é um projeto de cadastro inspirado no que seria uma loja de livros, utilizando as operações de banco de dados criar, consultar, atualizar e deletar. Baseado com as principais tecnologias sendo descritas acima mais o adicional de relatório em pdf com JasperSoft Studio e Gráfico de Barras com Google Charts.

# Tela de Login
Esta é a tela de login, aonde é possível o usuário acessar com suas credenciais e caso alguma das duas informações (login e senha) estarem erradas, o Spring Security impede o usuário de logar.

![tela_de_login](https://user-images.githubusercontent.com/105288563/231555063-614ee3bd-4cfc-4756-bccf-83d66f42d89b.jpg)

# Página inicial
Após o usuário ser logado no sistema, é diretamente redirecionado para a página inicial do projeto. Segue abaixo um print com enumeração de características da tela demonstrando cada funcionalidade da página inicial.

1. Título da página inical com função de voltar para esta aba do sistema.
2. Menu rápido para os cadastros disponíveis do sistema quando clicado sobre
3. Sai do sistema e redireciona para a tela de login
4. Exibe a lista de clientes cadastrados mais a possibilidade de um novo cadastro
5. Exibe a lista de fornecedores cadastrados mais a possibilidade de um novo cadastro
6. Exibe a lista de livros cadastrados mais a possibilidade de um novo cadastro
7. Mostra a tela da loja de livros
8. Mostra os pedidos já realizados
9. Mostra um gráfico com a média de livros que foram pedidos por pedido

# Lista de clientes & cadastro de clientes
Ao acessar a tela de "Clientes", é possível realizar o cadastro de um novo cliente, filtrar para exibição algum cliente já cadastrado por nome, endereço ou telefone pelo campo "Filtro" e também é exibido uma lista paginada dos clientes cadastrados. A cada 6 usuários mostrados em tela, uma nova aba é criada.

Os usuários mostram as seguintes informações na listagem: ID, Nome, Endereço, Telefone, Remover (aonde o usuário é apagado) e Editar (aonde possibilita editar as informações já cadastradas).

![lista_clientes](https://user-images.githubusercontent.com/105288563/231559088-dbfc26e3-6e45-482b-bd9f-6bcb37a7773c.jpg)

![lista_clientes2](https://user-images.githubusercontent.com/105288563/231559107-6110b44e-4de6-4add-8362-db3dba430912.jpg)

Ao tentar cadastrar um novo cliente, as seguintes informações são disponíveis para preenchimento do mesmo:

Foto,
Id (gerado automaticamente, portanto não é possível inserir um número),
Nome,
Endereço,
Telefone,
Sexo,
Ativo (checkbox de sim ou não, desmarcado como no print indica que não),
Área de atuação do cliente,
Cpf,
Estado,
Cidade.

O campo de Telefone e Cpf possui formatação com jQuery para o formato brasileiro, (XX) X XXXX-XXXX & XXX.XXX.XXX-XX respectivamente.
O campo de Cidade é automaticamente carregado através do consumo da API Via Cep que exibe as cidades de acordo com o estado selecionado.

Finalizando o preenchimento, é possível cadastrar ou limpar as informações pelos respectivos botões exibidos abaixo dos campo de preenchimento.

Ao clicar no link lista de clientes mostrado logo abaixo do título de página, o usuário é redirecionado para a lista de clientes.

![cadastro_de_clientes](https://user-images.githubusercontent.com/105288563/231559937-62a96c58-44d3-424f-8abd-c8f5e3217f38.jpg)

# Lista de fornecedores & cadastro de fornecedores
Ao acessar a tela de "Fornecedor", é possível realizar o cadastro de um novo fornecedor, filtrar para exibição algum fornecedor já cadastrado por Razão Social, Cnpj ou Telefone pelo campo "Filtro" e também é exibido uma lista paginada dos fornecedores cadastrados. A cada 6 fornecedores mostrados em tela, uma nova aba é criada.

Os fornecedores mostram as seguintes informações na listagem: ID, Foto, Razão Social, Cnpj, Telefone, Remover (aonde o fornecedor é apagado) e Editar (aonde possibilita editar as informações já cadastradas).

![lista_fornecedores](https://user-images.githubusercontent.com/105288563/231581622-17050192-c7f2-4c3f-a685-1d364d15d633.jpg)

Ao tentar cadastrar um novo fornecedor, as seguintes informações são disponíveis para preenchimento do mesmo:
Foto,
Id (gerado automaticamente, portanto não é possível inserir um número),
Razão Social,
Nome Fantasia,
Endereço,
Telefone,
Cnpj,
Ativo (checkbox de sim ou não, desmarcado como no print indica que não),
Inscrição Estadual,
Estado,
Cidade.

O campo de Cnpj possui formatação com jQuery para o formato brasileiro, XX.XXX.XXX/XXXX-XX.
O campo de Cidade é automaticamente carregado através do consumo da API Via Cep que exibe as cidades de acordo com o estado selecionado.

![cadastro_fornecedores](https://user-images.githubusercontent.com/105288563/231581541-65d68333-84f2-492a-8716-e09affde06c5.jpg)

# Lista de livros & cadastro de livros
Ao acessar a tela de "Livro", é possível realizar o cadastro de um novo livro, filtrar para exibição algum livro já cadastrado por Título, Isbn ou Autor(es) pelo campo "Filtro" e também é exibido uma lista paginada dos livros cadastrados. A cada 6 livros mostrados em tela, uma nova aba é criada.

Os livros mostram as seguintes informações na listagem: ID, Foto, Título, Isbn, Autor(es), Remover (aonde o livro é apagado) e Editar (aonde possibilita editar as informações já cadastradas).

![lista_livros](https://user-images.githubusercontent.com/105288563/231584853-9f3cc632-1533-478f-b5cc-6f694e3a15a4.jpg)

![lista_livros2](https://user-images.githubusercontent.com/105288563/231584860-7f0256c7-820e-4e56-8843-a8b372854dc1.jpg)

Ao tentar cadastrar um novo livro, as seguintes informações são disponíveis para preenchimento do mesmo:
Foto,
Id (gerado automaticamente, portanto não é possível inserir um número),
Título,
Descrição,
Ano,
Páginas,
Fornecedor (seleciona qual o fornecedor do atual livro),
Preço.

O campo de Preço possui formatação com jQuery mask money para o valor monetário em R$.

![cadastro_de_livros](https://user-images.githubusercontent.com/105288563/231585877-14fc1ef7-a0e0-4fbb-8c93-988a55485d1a.jpg)

# Loja de livros
Por meio da tela de "Loja de livro", é possível selecionar quais livros deseja-se comprar adicionado-o ao carrinho de compras. Ao adicionar um livro ao carrinho de compras, o texto escrito "0 itens no carrinho", abaixo do título da página, é atualizado para o número de vezes que algum livro foi adicionado.

Abaixo do livro é possível ver a descrição do mesmo passando o mouse sobre o texto "Ver descrição" ou clicando sobre o mesmo texto.

A loja de livros possui paginação para otimização do carregamento do sistema nos navegadores. A cada 6 livros mostrados em tela, uma nova aba é criada.

![loja_de_livros](https://user-images.githubusercontent.com/105288563/231588144-5a96fe58-2fc5-4ae8-96e3-9bb783d3f5e0.jpg)

![loja_de_livros2](https://user-images.githubusercontent.com/105288563/231588171-b913f13d-37c2-460e-91a5-3779a698a792.jpg)

![loja_de_livros_3](https://user-images.githubusercontent.com/105288563/231588193-26fc894d-d864-4427-95cf-3d162a2a044b.jpg)

![loja_de_livros4](https://user-images.githubusercontent.com/105288563/231588691-6dcb251e-1dc1-4812-a114-0bfcb222c562.jpg)

Clicando no texto "Itens no carrinho", o usuário é redirecionado para a página do carrinho de compras.
Por aqui é possível adicionar um cliente para o livro que estará sendo adquirido através do campo "Pesquisa Cliente". Ao adicionar um cliente para o livro, os campo desabilitados para edição logo abaixo do filtro são preenchidos com o ID e Nome do cliente, respectivamente.

É possível filtrar por livros no carrinho através do campo "Filtro Livros" e alterar a quantidade dos livros selecionados pelo campo "Quantidade". A cada vez que uma quantidade é adicionada ou removida, o valor total exibido ao final da página é atualizado.

Pelo botão finalizar pedido, é possível finalizar o pedido e acontece um redirecionamento de página aonde é exibido um documento pdf com os itens comprados.

![carrinho](https://user-images.githubusercontent.com/105288563/231590045-dfe09f69-4d10-40bb-b5e2-e03435c905fc.jpg)

![carrinho2](https://user-images.githubusercontent.com/105288563/231590060-1e210023-5485-4ea5-9362-1c9761ad30ad.jpg)

![carrinho3](https://user-images.githubusercontent.com/105288563/231590105-70402294-8f4b-43af-bfd9-27c53b40263a.jpg)

![pedido](https://user-images.githubusercontent.com/105288563/231591443-afbe3ac8-77a4-4176-b89f-c311e79845b0.jpg)

![relatorio_apos_pedido](https://user-images.githubusercontent.com/105288563/231591466-efc6f943-8ddb-4cd4-92ce-181cc62b18aa.jpg)

# Pedidos
Por meio da tela "Pedidos" é possível ver os pedidos já realizados. São mostrados os campos ID (do pedido), Cliente, R$ (valor do pedido), Remover (Exclui o pedido) e Pdf (Pdf mostrado na seção loja de livros).

![pedidos_cadastrados](https://user-images.githubusercontent.com/105288563/231593889-daf55c98-8d1f-4128-9716-56baf8bc6db1.jpg)

# Média pedido
Através da tela "Média pedido" é visualizado o gráfico de barras descrevendo a relação de quantidade de livros pedidos (por pedido) e seus respectivos títulos. Ao passar o mouse sobre alguma barra, é exibido a quantidade daquele livro.

![grafico_de_pedidos](https://user-images.githubusercontent.com/105288563/231594333-2471eaf5-1570-437f-84ac-8e88d97558a6.jpg)
