# Estrutura do projeto

Este documento descreve para que serve cada pasta do repositório do **PHPForge IDE**.

## Visão geral

O projeto é um fork enxuto do Apache NetBeans. O código é organizado em *clusters* — grupos de módulos que são compilados juntos. Apenas os clusters necessários para a IDE PHP foram mantidos; o restante do código-fonte original foi removido.

```
phpforge-ide/
├── platform/     Base da plataforma NetBeans
├── ide/          Infraestrutura da IDE
├── webcommon/    Suporte a tecnologias web
├── php/          Funcionalidades PHP + branding do produto
├── harness/      Ferramentas de construção e testes
├── nb/           Launcher e empacotamento herdados
├── brand/        Assets da identidade visual (designer)
├── nbbuild/      Sistema de compilação
└── docs/         Documentação do projeto
```

## Pastas

### `platform/`
A fundação de tudo. Contém o sistema de módulos, o sistema de janelas, o sistema de arquivos virtual, autoupdate, gerenciamento de opções, a barra de progresso e os utilitários centrais (OpenIDE API). Sem este cluster, nada funciona.

### `ide/`
A infraestrutura da IDE: editor de código (realce, folding, completion, templates), parsing e indexação, APIs de projetos, suporte a versionamento (Git, Subversion, Mercurial), terminal, banco de dados, diff, tarefas (to-do), depuração e suporte a linguagens (Lexer, CSL). Também inclui os módulos HTML, CSS e XML que servem de base para o suporte web.

### `webcommon/`
Suporte a tecnologias web: JavaScript/TypeScript (editor, modelo, debugging Node), JSON, LESS, e ferramentas front-end (npm, bower, gulp, grunt, karma). É a base sobre a qual o suporte PHP/HTML5 é construído.

### `php/`
O coração do PHPForge IDE: editor PHP, completude de código, análise estática, refactoring, suporte a frameworks (Symfony, Zend, Nette, Doctrine, Latte, Twig, Smarty), integração com Composer, PHPUnit, Codeception, Atoum, Phing, depuração via Xdebug (protocolo DBGP), projetos PHP e geração de documentação (PHPDoc/ApiGen). Abriga também a identidade e a interface do produto:

* `phpforge.branding` — módulo de branding: injeta nos JARs da plataforma o nome **PHPForge IDE**, splash screen, ícones de janela, tela inicial (logo + atalhos), textos do About/Gerenciador de plugins, o LAF padrão e o README do pacote. Os artefatos originais estão em `brand/`.
* `phpforge.ui` — tema **PHPForge Dark** (FlatLaf Dark com paleta Darcula: fundo `#2B2B2B`, seleção `#214283`, bordas `#515151`), fonte **JetBrains Mono** embutida como padrão do editor, **Inter** como fonte de UI, cores de syntax PHP no perfil escuro (keywords `#CC7832`, strings `#6A8759`, variáveis `#9876AA`, etc.), altura de linha 1.25, toolbar única compacta (barra contextual do editor e medidor de memória ocultos), itens de menu não-PHP ocultos (Servers/Cloud), categoria PHP no topo do Options e widget de boas-vindas "Novo Projeto PHP / Clonar do Git / Abrir Projeto ou Pasta" com o Dashboard abrindo na inicialização.
* `phpforge.icons` — icon theme flat estilo JetBrains New UI: **set Lucide** (stroke monocromático `#A9B0B8`) rasterizado para os resources de ações/toolbar/árvores dos módulos `openide.actions`, `openide.loaders` e `projectui` (new/open/save/undo/redo/cut/copy/paste/find/delete/print/run/test/build/clean/pastas etc., com variantes `@2x` e SVG), mais **glyphs outline coloridos** (cores JetBrains) para os tipos PHP; gerado por `generate-icons.py` e injetado diretamente nos JARs base via `netbeans-extra` (locale-JARs não funcionam para ícones carregados pelo classloader global do `ImageUtilities`).
* `phpforge.kit` — agregador que ativa o branding, o Dashboard (tela de início), o importador de plugins e o detector de deadlock.
* `ide.dashboard` — widgets do Dashboard (links de ajuda, aparência, plugins, logo).
* `autoupdate.pluginimporter` e `deadlock.detector` — utilitários do produto.
* `o.n.upgrader` — migração de configurações de versões anteriores (exigido pelo launcher).

### `harness/`
Ferramentas usadas durante o próprio processo de compilação e teste: Ant tasks do NetBeans, Jemmy e Jellytools (automação de GUI), nbjunit e bibliotecas de inspeção de memória. Faz parte do cluster `platform`, então é compilado junto.

### `nb/`
Conteúdo de marca e empacotamento herdado do NetBeans: o launcher nativo (`ide.launcher`, origem do executável `bin/netbeans` e do `etc/netbeans.conf`) — é de onde o build copia o script de inicialização e a configuração padrão (diretórios de usuário `~/.phpforge`).

### `nbbuild/`
O sistema de compilação do NetBeans: scripts Ant (`build.xml`), templates de build, definição dos clusters (`cluster.properties`), cache de binários externos e saída do build (`netbeans/`, `build/`). É daqui que os comandos `ant -Dcluster.config=php ...` são executados.

### `brand/`
Assets oficiais da identidade visual do **PHPForge IDE** enviados pelo designer: marca e logo (SVG/PNG), ícones do aplicativo (16–512 px e tiles), splash screen (640×360), brand board com a paleta de cores (Obsidian `#0D0D0D`, Surface `#161616`, Mint `#1DD6B4`, Spark `#7CF2D9`, Text `#F5F5F5`, Muted `#9A9A9A`) e as fontes Space Grotesk e JetBrains Mono (OFL). É a fonte a partir da qual o `phpforge.branding` gera os recursos do produto.

### `docs/`
Documentação do projeto, como este arquivo.

## Arquivos na raiz

* `README.md` — apresentação e instruções de build.
* `LICENSE` / `NOTICE` — licença Apache 2.0 e avisos de atribuição (mantidos do NetBeans).
* `build.xml` — ponto de entrada do Ant na raiz (delega para `nbbuild/build.xml`).
* `icons.html` — catálogo de ícones da plataforma (referência visual, herdado).
* `PACKAGING.md` — notas de empacotamento herdadas do Apache NetBeans.
