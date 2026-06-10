:

🍺 SMART MALT
Sistema de Gestão de Estoque para Adegas e Cervejarias
📌 Objetivo do Projeto
O SMART MALT tem como objetivo desenvolver e implementar um sistema de gestão de estoque eficiente, prático e intuitivo para adegas e cervejarias físicas.
O sistema organiza e controla o estoque de produtos, facilita o cadastro e gerenciamento de fornecedores e clientes, integra setores da loja (caixa, administração e estoque), otimiza processos internos e aumenta a produtividade.

📖 Justificativa
O gerenciamento de estoques é um dos principais desafios enfrentados por adegas e cervejarias, devido à variedade de produtos, marcas, fornecedores e datas de validade.
A falta de controle pode gerar:

Ausência de produtos

Excesso de mercadorias paradas

Perdas financeiras

Dificuldades no planejamento de reposição

O SMART MALT surge como solução tecnológica específica para o segmento, promovendo organização, redução de prejuízos e aumento da competitividade.

⚙️ Funcionalidades
📦 Cadastro de produtos (bebidas, marcas, categorias, preços e estoque)

🔄 Controle de entrada e saída de mercadorias

🏭 Cadastro e gerenciamento de fornecedores

👤 Cadastro e histórico de clientes

🔗 Integração entre setores (caixa, administração e estoque)

📊 Monitoramento do nível de estoque

🖥 Interface simples e intuitiva

💻 Sistema multiusuário (até 3 computadores interligados)

🔐 Perfis de Acesso
👨‍💼 Gerente (Administrador)

Login: admin

Senha: 123

💰 Funcionário (Caixa)

Login: caixa

Senha: 123

🧱 Arquitetura do Projeto
O sistema segue a arquitetura MVC (Model-View-Controller):

Código
src/
 ├── controller/
 ├── dao/
 ├── model/
 ├── view/
 └── main/
💻 Tecnologias Utilizadas
Java

JDBC

MySQL

POO (Programação Orientada a Objetos)

Arquitetura MVC

▶️ Como Executar o Projeto
Clonar o repositório

bash
git clone https://github.com/pablothome/smart-malt-java.git
Importar no Eclipse

File → Import → Existing Projects into Workspace → selecione a pasta clonada.

Configurar o banco de dados MySQL

Criar um banco chamado smart_malt.

Importar o arquivo smart-malt.sql:

bash
mysql -u root -p smart_malt < banco/smart-malt.sql
Ajustar configuração de conexão

No arquivo de configuração do projeto (ex: persistence.xml ou application.properties), definir:

Código
jdbc:mysql://localhost:3306/smart_malt
user=root
password=SUASENHA
Executar o sistema

Rodar a classe principal (Main ou Login) no Eclipse.

👨‍💻 Autor
Projeto desenvolvido para fins acadêmicos por Pablo Thome.

📌 Observações
Sistema desenvolvido para ambiente local.

Recomendado uso com banco MySQL configurado.

Usuários padrão já definidos no sistema.

⭐ Conclusão
O SMART MALT é uma solução completa para controle de estoque em adegas e cervejarias, promovendo organização, eficiência e melhor tomada de decisão gerencial.
