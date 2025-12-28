# 🎫 ReClick - Sistema de Reserva de Ingressos

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen?style=for-the-badge&logo=spring)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-green?style=for-the-badge)
![Tailwind CSS](https://img.shields.io/badge/Tailwind%20CSS-3.0-38bdf8?style=for-the-badge&logo=tailwind-css)
![H2 Database](https://img.shields.io/badge/H2-Database-yellow?style=for-the-badge)

**Uma aplicação web completa para descoberta de eventos e reserva de ingressos**

[Funcionalidades](#-funcionalidades) • [Tecnologias](#-tecnologias) • [Instalação](#-instalação) • [Uso](#-como-usar) • [Estrutura](#-estrutura-do-projeto)

</div>

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação](#-instalação)
- [Como Usar](#-como-usar)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Arquitetura](#-arquitetura)
- [Rotas e Endpoints](#-rotas-e-endpoints)
- [Banco de Dados](#-banco-de-dados)
- [Segurança](#-segurança)
- [Design e UI](#-design-e-ui)
- [Desenvolvimento](#-desenvolvimento)
- [Troubleshooting](#-troubleshooting)
- [Roadmap](#-roadmap)
- [Contribuindo](#-contribuindo)
- [Licença](#-licença)

---

## 🎯 Sobre o Projeto

O **ReClick** é uma plataforma web moderna e intuitiva desenvolvida para conectar organizadores de eventos e participantes. O sistema permite que vendedores cadastrem e gerenciem seus eventos, enquanto usuários podem descobrir, visualizar detalhes e reservar ingressos de forma simples e segura.

### Características Principais

- ✅ **Interface Moderna**: Design responsivo e intuitivo com Tailwind CSS
- ✅ **Sistema de Perfis**: Diferenciação entre Usuários e Vendedores
- ✅ **Gestão Completa**: Cadastro, edição e exclusão de eventos
- ✅ **Sistema de Reservas**: Fluxo completo de reserva com confirmação manual
- ✅ **Pagamento via PIX**: Integração informativa para pagamentos
- ✅ **Painel Administrativo**: Gestão de eventos e aprovações
- ✅ **Segurança**: Autenticação e autorização com Spring Security

---

## ✨ Funcionalidades

### 👤 Para Usuários

- **Autenticação**
  - Cadastro de conta (tipo Usuário ou Vendedor)
  - Login seguro com Spring Security
  - Gerenciamento de perfil

- **Descoberta de Eventos**
  - Listagem completa de eventos disponíveis
  - Filtro por localidade
  - Visualização de eventos populares na homepage
  - Detalhes completos: data, horário, local, descrição, imagem

- **Reserva de Ingressos**
  - Seleção de quantidade de ingressos
  - Reserva com status (PENDENTE, CONFIRMADO, RECUSADO)
  - Visualização de código de confirmação quando aprovado
  - Acompanhamento de todas as reservas em "Meus Ingressos"

- **Perfil**
  - Visualização de informações pessoais
  - Edição de dados (exceto email)
  - Histórico de eventos reservados

### 🏢 Para Vendedores

- **Gestão de Eventos**
  - Cadastro completo de eventos (nome, imagem, data, horário, local, preço, descrição, chave PIX)
  - Edição de eventos existentes
  - Exclusão de eventos
  - Visualização de total arrecadado por evento

- **Gestão de Reservas**
  - Visualização de todas as reservas recebidas
  - Aprovação ou recusa de reservas pendentes
  - Acompanhamento por evento específico
  - Painel centralizado com visão geral

### 👨‍💼 Para Administradores

- **Painel Administrativo**
  - Aprovação/rejeição de eventos pendentes
  - Estatísticas gerais (eventos aprovados, pendentes, total de vendedores)
  - Gestão completa do sistema

---

## 🛠 Tecnologias

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.4.4** - Framework principal
  - Spring Web MVC
  - Spring Data JPA
  - Spring Security
- **H2 Database** - Banco de dados em memória/arquivo
- **Lombok** - Redução de boilerplate
- **Maven** - Gerenciamento de dependências

### Frontend
- **Thymeleaf** - Template engine
- **Tailwind CSS 3.0** - Framework CSS (via CDN)
- **Font Awesome 6.0** - Ícones
- **Inter Font** - Tipografia moderna

### Ferramentas de Desenvolvimento
- **Maven Wrapper** - Build sem necessidade de Maven instalado
- **H2 Console** - Interface web para banco de dados

---

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java 21** ou superior
  ```bash
  java -version
  ```
- **Maven 3.8+** (opcional, o projeto inclui Maven Wrapper)
  ```bash
  mvn -version
  ```

### Verificação de Instalação

```bash
# Verificar Java
java -version
# Deve mostrar: openjdk version "21" ou superior

# Verificar Maven (se instalado)
mvn -version
```

---

## 🚀 Instalação

### 1. Clonar o Repositório

```bash
git clone <url-do-repositório>
cd reserva-ingresso
```

### 2. Executar a Aplicação

#### Opção 1: Usando Maven Wrapper (Recomendado)

**Windows:**
```bash
cd re-click
.\mvnw.cmd spring-boot:run
```

**Linux/macOS:**
```bash
cd re-click
./mvnw spring-boot:run
```

#### Opção 2: Usando Maven Instalado

```bash
cd re-click
mvn spring-boot:run
```

#### Opção 3: Compilar e Executar JAR

```bash
cd re-click
mvn clean package
java -jar target/re-click-0.0.1-SNAPSHOT.jar
```

### 3. Acessar a Aplicação

Após a inicialização, acesse:

- **Aplicação**: http://localhost:8080
- **Console H2**: http://localhost:8080/h2-console

### 4. Configuração do Banco de Dados H2

Ao acessar o console H2, use:

- **JDBC URL**: `jdbc:h2:file:./data/reclickdb`
- **Usuário**: `sa`
- **Senha**: (deixe em branco)

> ⚠️ **Nota**: O banco de dados será criado automaticamente na pasta `data/` na primeira execução.

---

## 📖 Como Usar

### Primeiro Acesso

1. **Acesse a aplicação**: http://localhost:8080
2. **Crie uma conta**:
   - Clique em "Login/Cadastro"
   - Escolha o tipo de conta (Usuário ou Vendedor)
   - Preencha os dados necessários
   - Para Vendedores: informe telefone e nome da empresa

### Como Usuário

1. **Explorar Eventos**
   - Navegue pela homepage ou acesse "Eventos"
   - Use o filtro por localidade se desejar
   - Clique em "Ler mais" para ver detalhes

2. **Reservar Ingressos**
   - Acesse os detalhes do evento
   - Selecione a quantidade de ingressos
   - Clique em "Reservar Ingresso"
   - Siga as instruções de pagamento PIX
   - Aguarde a confirmação do vendedor

3. **Acompanhar Reservas**
   - Acesse "Meus Ingressos" no menu
   - Visualize o status de cada reserva
   - Quando confirmado, copie o código de confirmação

### Como Vendedor

1. **Cadastrar Evento**
   - Faça login como vendedor
   - Acesse "Meu Perfil"
   - Clique em "Adicionar Evento"
   - Preencha todos os dados do evento
   - Aguarde aprovação do administrador

2. **Gerenciar Reservas**
   - No painel do vendedor, veja todos os eventos
   - Clique em "Ver Compradores" em um evento
   - Aprove ou recuse reservas pendentes
   - Acompanhe o total arrecadado

3. **Editar/Excluir Eventos**
   - No painel, clique em "Editar" para modificar
   - Ou "Excluir" para remover (com confirmação)

---

## 📁 Estrutura do Projeto

```
reserva-ingresso/
│
├── re-click/                          # Módulo principal
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/re_click/
│   │   │   │       ├── config/        # Configurações
│   │   │   │       │   ├── SecurityConfig.java
│   │   │   │       │   ├── ThymeleafConfig.java
│   │   │   │       │   └── CustomAuthenticationSuccessHandler.java
│   │   │   │       ├── controller/   # Controladores REST/Web
│   │   │   │       │   ├── HomeController.java
│   │   │   │       │   ├── LoginController.java
│   │   │   │       │   ├── CadastroController.java
│   │   │   │       │   ├── EventoController.java
│   │   │   │       │   ├── ReservaController.java
│   │   │   │       │   ├── ProfileController.java
│   │   │   │       │   ├── UsuarioController.java
│   │   │   │       │   └── AdminController.java
│   │   │   │       ├── model/        # Entidades JPA
│   │   │   │       │   ├── Pessoa.java (base)
│   │   │   │       │   ├── Usuario.java
│   │   │   │       │   ├── Vendedor.java
│   │   │   │       │   ├── Admin.java
│   │   │   │       │   ├── Evento.java
│   │   │   │       │   ├── Reserva.java
│   │   │   │       │   ├── TipoConta.java (enum)
│   │   │   │       │   ├── StatusEvento.java (enum)
│   │   │   │       │   └── StatusPagamento.java (enum)
│   │   │   │       ├── repository/    # Repositórios JPA
│   │   │   │       │   ├── UsuarioRepository.java
│   │   │   │       │   ├── VendedorRepository.java
│   │   │   │       │   ├── AdminRepository.java
│   │   │   │       │   ├── EventoRepository.java
│   │   │   │       │   └── ReservaRepository.java
│   │   │   │       ├── service/       # Lógica de negócio
│   │   │   │       │   ├── AppUserDetailsService.java
│   │   │   │       │   └── EventoService.java
│   │   │   │       └── ReClickApplication.java
│   │   │   └── resources/
│   │   │       ├── templates/         # Templates Thymeleaf
│   │   │       │   ├── index.html
│   │   │       │   ├── login.html
│   │   │       │   ├── cadastro.html
│   │   │       │   ├── eventos.html
│   │   │       │   ├── eventoDetalhes.html
│   │   │       │   ├── perfilusuario.html
│   │   │       │   ├── perfilvendedor.html
│   │   │       │   ├── cadastrarevento.html
│   │   │       │   ├── editar-evento.html
│   │   │       │   ├── meusIngressos.html
│   │   │       │   ├── reservasEvento.html
│   │   │       │   ├── resumoReserva.html
│   │   │       │   └── admin.html
│   │   │       └── application.properties
│   │   └── test/                      # Testes
│   ├── pom.xml                        # Dependências Maven
│   └── mvnw, mvnw.cmd                 # Maven Wrapper
│
├── data/                              # Banco de dados H2 (gerado)
│   └── reclickdb.mv.db
│
└── README.md                          # Este arquivo
```

---

## 🏗 Arquitetura

### Padrão MVC (Model-View-Controller)

```
┌─────────────┐
│   Browser   │
└──────┬──────┘
       │ HTTP Request
       ▼
┌─────────────────┐
│   Controller    │ ← Lógica de controle
└──────┬──────────┘
       │
       ├──────────────┐
       ▼              ▼
┌──────────┐    ┌──────────┐
│  Service │    │   View   │ ← Thymeleaf Templates
└─────┬────┘    └──────────┘
      │
      ▼
┌─────────────┐
│ Repository  │ ← Acesso a dados
└──────┬──────┘
       │
       ▼
┌─────────────┐
│     H2      │ ← Banco de dados
└─────────────┘
```

### Fluxo de Autenticação

```
1. Usuário acessa /login
2. Spring Security intercepta
3. AppUserDetailsService carrega usuário
4. Autenticação bem-sucedida
5. Redirecionamento baseado no papel (ROLE)
   - USUARIO → /perfilusuario
   - VENDEDOR → /perfilvendedor
   - ADMIN → /admin
```

### Fluxo de Reserva

```
1. Usuário visualiza evento
2. Seleciona quantidade
3. Submete reserva (POST /eventos/reservar)
4. Status: PENDENTE
5. Vendedor revisa no painel
6. Vendedor confirma/recusa
7. Status: CONFIRMADO/RECUSADO
8. Se confirmado: código gerado
9. Usuário visualiza em "Meus Ingressos"
```

---

## 🛣 Rotas e Endpoints

### Rotas Públicas

| Rota | Método | Descrição |
|------|--------|-----------|
| `/` | GET | Homepage com eventos populares |
| `/eventos` | GET | Listagem completa de eventos |
| `/eventos/{id}` | GET | Detalhes de um evento específico |
| `/login` | GET/POST | Página de login |
| `/cadastro` | GET/POST | Página de cadastro |
| `/logout` | POST | Logout do usuário |

### Rotas de Usuário (Autenticado)

| Rota | Método | Descrição |
|------|--------|-----------|
| `/perfilusuario` | GET | Perfil do usuário |
| `/perfilusuario/editar` | GET/POST | Editar perfil |
| `/reservas/meus-ingressos` | GET | Lista de reservas do usuário |
| `/eventos/reservar` | POST | Criar nova reserva |
| `/resumo-reserva` | GET | Resumo da reserva criada |

### Rotas de Vendedor (Autenticado)

| Rota | Método | Descrição |
|------|--------|-----------|
| `/perfilvendedor` | GET | Painel do vendedor |
| `/eventos/novo` | GET | Formulário de novo evento |
| `/eventos/cadastrar` | POST | Criar novo evento |
| `/eventos/editar-evento/{id}` | GET | Formulário de edição |
| `/eventos/editar/{id}` | POST | Atualizar evento |
| `/eventos/excluir/{id}` | POST | Excluir evento |
| `/reservas/evento/{id}` | GET | Reservas de um evento |
| `/reservas/{id}/confirmar` | POST | Confirmar reserva |
| `/reservas/{id}/recusar` | POST | Recusar reserva |

### Rotas de Administrador

| Rota | Método | Descrição |
|------|--------|-----------|
| `/admin` | GET | Painel administrativo |
| `/admin/aprovar-evento/{id}` | GET | Aprovar evento |
| `/admin/rejeitar-evento/{id}` | GET | Rejeitar evento |

---

## 💾 Banco de Dados

### Modelo de Dados

```
Pessoa (abstrata)
├── id: Long
├── nome: String
├── email: String
└── senha: String

Usuario extends Pessoa
└── eventos: List<Evento>

Vendedor extends Pessoa
├── telefone: String
├── nomeEmpresa: String
└── eventos: List<Evento>

Admin extends Pessoa

Evento
├── id: Long
├── nome: String
├── urlImagem: String
├── data: LocalDate
├── horario: LocalTime
├── local: String
├── preco: BigDecimal
├── descricao: String
├── chavePix: String
├── status: StatusEvento
└── vendedor: Vendedor

Reserva
├── id: Long
├── quantidade: Integer
├── status: StatusPagamento
├── codigoConfirmacao: String
├── evento: Evento
└── usuario: Usuario
```

### Enums

- **TipoConta**: `USUARIO`, `VENDEDOR`
- **StatusEvento**: `PENDENTE`, `APROVADO`, `REJEITADO`
- **StatusPagamento**: `PENDENTE`, `CONFIRMADO`, `RECUSADO`

### Configuração

O banco H2 está configurado para:
- Persistência em arquivo (`./data/reclickdb.mv.db`)
- Auto-atualização do schema (`spring.jpa.hibernate.ddl-auto=update`)
- Console web habilitado em `/h2-console`

---

## 🔒 Segurança

### Spring Security

- **Autenticação**: Baseada em formulário (form-based)
- **Autorização**: Baseada em roles (ROLE_USUARIO, ROLE_VENDEDOR, ROLE_ADMIN)
- **Senhas**: Armazenadas com BCrypt (hash automático)
- **CSRF**: Proteção habilitada (tokens em formulários)

### Permissões

| Recurso | Usuário | Vendedor | Admin |
|---------|---------|----------|-------|
| Ver eventos | ✅ | ✅ | ✅ |
| Reservar ingressos | ✅ | ❌ | ❌ |
| Cadastrar eventos | ❌ | ✅ | ✅ |
| Gerenciar reservas | ❌ | ✅ | ✅ |
| Aprovar eventos | ❌ | ❌ | ✅ |

### Navegação Condicional

O header é renderizado dinamicamente via Thymeleaf:
- Links aparecem baseados no papel do usuário
- "Meu Perfil" muda conforme o tipo de conta
- Botões de ação aparecem apenas para usuários autorizados

---

## 🎨 Design e UI

### Design System

- **Cores Principais**:
  - Primária: Emerald-600 (#059669)
  - Secundária: Gray-200 (#e5e7eb)
  - Sucesso: Green-600
  - Erro: Red-500
  - Aviso: Yellow-500

- **Tipografia**:
  - Fonte: Inter (Google Fonts)
  - Tamanhos: Hierarquia clara (text-3xl, text-2xl, text-xl, etc.)

- **Componentes**:
  - Botões: `rounded-lg`, `transition`, `font-medium`
  - Cards: `shadow-md`, `hover:shadow-lg`
  - Inputs: `rounded-lg`, `focus:ring-2`

### Responsividade

- **Mobile First**: Design otimizado para telas pequenas
- **Breakpoints Tailwind**:
  - `sm`: 640px
  - `md`: 768px
  - `lg`: 1024px

### Acessibilidade

- Atributos ARIA onde necessário
- Navegação por teclado
- Contraste adequado
- Labels descritivos

---

## 💻 Desenvolvimento

### Configuração do Ambiente

1. **IDE Recomendada**: IntelliJ IDEA ou Eclipse
2. **Plugins Úteis**:
   - Lombok Plugin
   - Spring Boot Tools
   - Thymeleaf Plugin

### Estrutura de Commits

```
feat: adiciona nova funcionalidade
fix: corrige bug
docs: atualiza documentação
style: formatação de código
refactor: refatoração
test: adiciona testes
chore: tarefas de manutenção
```

### Logs

O projeto está configurado para exibir logs de debug do Spring Security:
```properties
logging.level.org.springframework.security=DEBUG
```

Para produção, altere para `INFO` ou `WARN`.

---

## 🐛 Troubleshooting

### Problema: Aplicação não inicia

**Solução**:
- Verifique se a porta 8080 está livre
- Confirme que Java 21 está instalado
- Verifique os logs de erro no console

### Problema: Erro de conexão com banco

**Solução**:
- Certifique-se de que a pasta `data/` existe
- Verifique as permissões de escrita
- Limpe o banco se necessário: delete `data/reclickdb.mv.db`

### Problema: Página em branco

**Solução**:
- Limpe o cache do navegador
- Verifique se o Thymeleaf está processando corretamente
- Confira os logs do servidor

### Problema: Erro de autenticação

**Solução**:
- Verifique se o usuário existe no banco
- Confirme que a senha está correta
- Verifique os logs do Spring Security

### Problema: Estilos não carregam

**Solução**:
- Verifique conexão com internet (Tailwind via CDN)
- Confirme que o CDN do Tailwind está acessível
- Verifique o console do navegador para erros

---

## 🗺 Roadmap

### Versão Atual (v0.0.1)
- ✅ Sistema básico de autenticação
- ✅ CRUD de eventos
- ✅ Sistema de reservas
- ✅ Painel de vendedor
- ✅ Painel administrativo
- ✅ Interface responsiva

### Próximas Versões

#### v0.1.0 - Melhorias de UX
- [ ] Fragmentos Thymeleaf para layout base
- [ ] Melhorias na página inicial
- [ ] Sistema de busca avançada
- [ ] Filtros múltiplos (data, preço, categoria)

#### v0.2.0 - Funcionalidades Avançadas
- [ ] Recuperação de senha
- [ ] Verificação de email
- [ ] Notificações por email
- [ ] Upload de imagens (substituir URL)

#### v0.3.0 - Integrações
- [ ] Integração com gateway de pagamento
- [ ] Geração de QR Code para ingressos
- [ ] API REST para mobile
- [ ] Webhooks para notificações

#### v0.4.0 - Performance e Escalabilidade
- [ ] Cache de eventos
- [ ] Paginação otimizada
- [ ] Migração para PostgreSQL/MySQL
- [ ] Testes automatizados

#### v1.0.0 - Produção
- [ ] Internacionalização (i18n)
- [ ] Documentação completa da API
- [ ] Deploy automatizado
- [ ] Monitoramento e métricas

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Siga estes passos:

1. **Fork o projeto**
2. **Crie uma branch** para sua feature (`git checkout -b feature/AmazingFeature`)
3. **Commit suas mudanças** (`git commit -m 'feat: adiciona AmazingFeature'`)
4. **Push para a branch** (`git push origin feature/AmazingFeature`)
5. **Abra um Pull Request**

### Diretrizes

- Siga o padrão de código existente
- Adicione comentários quando necessário
- Teste suas mudanças
- Atualize a documentação se necessário

---

## 📝 Licença

Este projeto é de uso educacional e demonstrativo. Sinta-se livre para usar, modificar e distribuir conforme sua necessidade.

---

## 👥 Autores

- **Equipe de Desenvolvimento** - Desenvolvimento inicial

---

## 🙏 Agradecimentos

- Spring Boot Community
- Tailwind CSS Team
- Thymeleaf Contributors
- Todos os mantenedores das bibliotecas utilizadas

---

## 📞 Contato

Para dúvidas, sugestões ou problemas:

- **Email**: suporte@reclick.com
- **Telefone**: (11) 91234-5678
- **Horário de Atendimento**: Segunda a Sexta, 9h às 18h

---

<div align="center">

**Desenvolvido com ❤️ usando Spring Boot**

⭐ Se este projeto foi útil, considere dar uma estrela!

</div>
