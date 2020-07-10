# Testes de API (ServeRest)
Neste teste, 3 endpoints do servidor ServeRest são estudados:

> Primeiro Endpoint: /login
- No primeiro teste, um usuário tenta fazer o login mantendo os campos de email e senha vazios. HTTP request: post.
- No segundo teste, um usuário provê um email e/ou senha inválidos. HTTP request: post.
- No terceiro teste, um usuário fornece email e senha válidos e é bem-sucedido ao fazer o login. HTTP request: post.
- Em todos estes cenários, a validação consiste em analisar se o status code correto é retornado e se as mensagens de erro adequadas são exibidas.

> Segundo Endpoint: /carrinhos
- No primeiro teste, um usuário obtém informação sobre o carrinho existente. O teste averigua se o status code correto é retornado e se o valor total de fato corresponde à soma dos preços individuais dos itens no carrinho (multiplicados pela sua quantidade). HTTP request: get. 
- No segundo teste, um usuário faz o login (HTTP request: post) e, usando a token de autenticação retornada, cria um carrinho de compras (HTTP request: post). Em seguida, a compra é cancelada para evitar mudanças na database (HTTP request: delete). Cada etapa do processo é validada através do status code, mas a etapa de criação do carrinho de compras possui uma confirmação adicional: verifica-se se a mensagem de confirmação retornada é a esperada.
- No terceiro teste, um usuário faz o login (HTTP request: post) e, usando a token de autenticação retornada, cria um carrinho de compras (HTTP request: post). Porém, em seguida, esse usuário tenta criar um segundo carrinho (HTTP request: post). Nesse momento, é feita a verificação tanto do status code quanto da mensagem de erro retornados. Por fim, a compra é cancelada para evitar mudanças na database (HTTP request: delete).
- No quarto teste, um usuário tenta criar um carrinho de compras (HTTP request: post) sem prover a token de autenticação. Novamente, verifica-se se o status code e a mensagem de erro retornadas são os adequados.

> Terceiro Endpoint: /produtos
- Neste teste, um usuário busca informações sobre um produto específico e é feita a verificação se o item retornado de fato corresponde ao procurado no request. Além disso, também é feita a validação do status code.


## Como executar
Abra o terminal e execute o comando 'npx serverest' para iniciar o servidor (default: http://localhost:3000). Em seguida, acesse a raiz do seu projeto e execute o comando 'mvn test' para rodar todos os testes.

## Requerimentos
- Java (configurar as variáveis de ambiente).
- Maven (configurar as variáveis de ambiente).