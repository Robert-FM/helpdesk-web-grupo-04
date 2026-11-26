# HelpDesk Web — Grupo 4 — Exclusão

Aplicação didática Java 21 / Spring Boot 3.5.0, HTML, CSS e JavaScript. Dados fictícios em memória, reiniciados a cada execução. Sem autenticação: não usar em produção.

## Executar sem instalar nada
1. Professor: extraia o ZIP e envie **todo** o conteúdo para um repositório GitHub (incluindo a pasta oculta `.github`). Use GitHub Desktop/CLI se disponível ou carregue os arquivos pela interface web, criando `.github/workflows/ci.yml` manualmente se necessário.
2. Estudantes: façam fork do repositório do seu grupo. Na aba Actions, habilitem workflows se solicitado. Execute `HelpDesk - Validacao` por **Run workflow** ou faça um commit.
3. Pressione `.` no repositório para abrir github.dev. Edite os TODOs; faça commit pela área Source Control. Não há execução Java no github.dev.
4. Na aba Actions abra a execução e os jobs `api` e `navegador`. Leia os logs. Baixe os artefatos de relatório quando disponíveis. Corrija e repita. O servidor usado pelo CI é temporário e não possui URL pública.
5. Entregue o link do fork, hash do commit, link da execução e explicação das mudanças.

## Missão
Use service.excluir e retorne 204 sem corpo ou 404. No JS confirme com confirm e envie DELETE; atualize a tela.

Arquivos principais: `src/main/java/br/edu/helpdesk/ChamadoController.java` e `src/main/resources/static/app.js`. Revise também `index.html` e `style.css`; melhore o texto de ajuda ou a apresentação sem alterar IDs usados nos testes.

## Contrato HTTP
| Método | Rota | Resultado |
|---|---|---|
| GET | /api/health | 200 |
| GET | /api/chamados?status=ABERTO | 200; filtro opcional, ABERTO ou RESOLVIDO; 400 se inválido |
| POST | /api/chamados | 201; 400 se inválido |
| PATCH | /api/chamados/1/status | 200; 400 se status inválido; 404 se ausente |
| DELETE | /api/chamados/1 | 204; 404 se ausente |
| GET | /api/chamados/estatisticas | 200; total, abertos, resolvidos, alta |

POST JSON: `{"solicitante":"Ana","descricao":"Falha","categoria":"REDE","prioridade":"ALTA"}`. Categorias: HARDWARE, REDE, SOFTWARE. Prioridades: BAIXA, MEDIA, ALTA. Status inicial ABERTO. PATCH JSON: `{"status":"RESOLVIDO"}`.

## Testes
26 testes Java (25 funcionais e health). Os cinco testes do grupo devem falhar inicialmente; os demais devem passar. O teste E2E do grupo deve falhar inicialmente.
Os testes não devem ser editados pelos estudantes. O workflow executa Maven e Playwright em jobs independentes. A validação completa exige ambos verdes. A revisão de usabilidade e CSS também é manual.

## Para o professor
Opcionalmente publique a versão de referência via Dockerfile no Coolify (porta 8080). Não é necessário para a aula. Não compartilhe o gabarito com os estudantes antes da atividade.
