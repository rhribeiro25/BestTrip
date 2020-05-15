  * Como executar a aplicação,
    -> Execute a aplicação spring boot normalmente
    -> Forneça o seguinte comando no shell da IDE: best-trip input-routes.csv
    -> Digite alguma rota no shell como por exemplo: GRU-CDG
    -> Utilize a collection Postman disponibilidade em (./files) para testes rest

  * Estrutura dos arquivos/pacotes,
    -> A aplicação conta com MVC de 4 camadas sendo: models, controllers, services e repositories
    
  * Explique as decisões de design adotadas para a solução,
    -> Foi utilizado TDD como fererencia para testes em Junit, com intuito de minimizar os riscos de futuras manutenções quebrarem outras partes do sistema.
    -> Ferramentas Spring utilizadas: Spring Shell e Spring MVC, por ser incorporado ao projeto spring boot e funcionar muito bem, com leveza e praticidade.
    -> Algumas boas práticas foram implementadas como o uso de Interfaces, herança e injeção de dependências. A utilização de design patterns permite que a aplicação seja mais desacopladas, escalável e de fácil manutenção.
    -> O repository simula consultas em DB ao manipular o arquivo, um dos principais pilares do SOLID é a questão de de cada modulo, classe ou metodo ter sua responsablidade bem definiida e implementada.

  * Descreva sua APÌ Rest de forma simplificada.
    -> O endpoint "consulta de melhor rota", trata-se de um serviço rest que utiliza o metodo get para fornecer a consulta desejada por meio de um parâmetro DE_PARA.
    -> O endpoint "registro de nova rota", trata-se de um serviço rest implementado com o metodo post, e passando em seu coir um json que permite a gravação em arquivo.