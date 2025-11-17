# ReClick — Reserva de Ingressos

ReClick é uma aplicação web para descoberta de eventos e reserva de ingressos. O projeto oferece uma experiência simples para usuários que desejam reservar e acompanhar ingressos, e para vendedores que cadastram e gerenciam seus eventos, com confirmação de reservas e acompanhamento de valores arrecadados.

## Visão geral
- Descobrir eventos, visualizar detalhes e reservar ingressos
- Fluxo de pagamento via Pix (informativo) com confirmação manual do vendedor
- Perfis de usuário e vendedor com navegação e permissões adaptadas
- Interface responsiva com Tailwind CSS, focada em clareza e consistência
- Banco H2 em arquivo (persistente) para rodar localmente sem dependências externas

## Principais funcionalidades
- Usuário
  - Cadastro e login
  - Listagem de eventos e filtro por localidade
  - Visualização de detalhes (data, horário, local, descrição, imagem)
  - Reserva de ingressos e acompanhamento do status (PENDENTE, CONFIRMADO, RECUSADO)
  - Exibição de código de confirmação quando aprovado
  - Meu Perfil e Meus Ingressos
- Vendedor
  - Cadastro de evento (nome, imagem, data, horário, local, preço, descrição, chave Pix)
  - Edição e exclusão de eventos
  - Painel com reservas recebidas por evento e ação de confirmar/recusar
  - Visão de total arrecadado por evento

## Tecnologias & Stack
- Backend: Spring Boot (Web, Security, Data JPA)
- Template engine: Thymeleaf (+ extras spring-security)
- Estilo: Tailwind CSS via CDN
- Banco de dados: H2 (arquivo)
- Build/Dev: Maven, Java 17+

## Como executar localmente
Pré‑requisitos:
- Java 17 ou superior
- Maven 3.8+

Passos:
1. Clone o repositório
2. Na raiz do projeto, rode:
   - Windows/Linux/macOS: `mvn -f re-click/pom.xml spring-boot:run`
3. Acesse a aplicação em: http://localhost:8080

Banco de dados H2:
- Console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/reclickdb`
- Usuário: `sa`
- Senha: (em branco)

Observações de segurança/ambiente:
- O schema é atualizado automaticamente: `spring.jpa.hibernate.ddl-auto=update`
- A console H2 está habilitada apenas para ambiente local de desenvolvimento

## Papéis e permissões
- Tipo de conta no cadastro:
  - USUARIO: navegação padrão (perfil, meus ingressos)
  - VENDEDOR: acesso a painel do vendedor, cadastro/edição de eventos e reservas do evento
- A navegação no header é renderizada condicionalmente via Thymeleaf + Spring Security

## Principais rotas (web)
- Público
  - `/` — Home, eventos populares e listagem com filtro de localidade
  - `/eventos` — Catálogo de eventos
  - `/eventos/{id}` — Detalhes do evento
  - `/login`, `/cadastro`
- Usuário autenticado
  - `/reservas/meus-ingressos` — Minhas reservas/ingressos
  - `/perfilusuario`, `/perfilusuario/editar`
  - `/eventos/reservar` — Ação de reserva (POST)
- Vendedor
  - `/eventos/novo` e `/eventos/cadastrar` — Cadastro de evento
  - `/eventos/editar-evento/{id}`, `/eventos/editar/{id}` — Edição de evento
  - `/reservas/evento/{id}` — Reservas de um evento específico
  - `/reservas/{id}/confirmar`, `/reservas/{id}/recusar` — Ações sobre a reserva (POST)
  - `/perfilvendedor` — Painel do vendedor (eventos, compradores, total arrecadado)

## Estrutura do projeto
- `re-click/src/main/java/com/re_click/` — Código Java (config, controllers, models, repositories, services)
- `re-click/src/main/resources/templates/` — Páginas Thymeleaf (home, autenticação, eventos, perfis, reservas)
- `re-click/src/main/resources/application.properties` — Configurações (H2, Thymeleaf, logging)
- `data/reclickdb.mv.db` — Arquivo do banco H2 gerado em execução

## Fluxo de reservas e pagamento
1. Usuário reserva ingressos em um evento (status PENDENTE)
2. Vendedor revisa a reserva e confirma/recusa
3. Quando CONFIRMADO, o usuário vê um código de confirmação no “Meus Ingressos”
4. Pagamento via Pix é informativo (exibe a chave e orientações); confirmação é manual pelo vendedor

## Desenvolvimento
- O projeto usa Tailwind via CDN, o que facilita prototipagem sem build de CSS local
- Templates priorizam semântica (header/main/footer), acessibilidade básica (roles/aria-live) e responsividade
- Sugestão futura: criar layout base do Thymeleaf (fragmentos para header/footer) para reduzir repetição entre páginas

## Roadmap (sugestões)
- Recuperação de senha e verificação de email
- Geração/validação de QR Code Pix
- Pagamento integrado (gateway) com confirmação automática
- Testes automatizados (unitários e integração)
- Fragmentos Thymeleaf para layout base
- Internacionalização (i18n)

## Licença
Este projeto é de uso educacional/demonstrativo. Ajuste a licença conforme sua necessidade.
