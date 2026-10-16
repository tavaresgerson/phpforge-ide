# PHPForge IDE

O **PHPForge IDE** é um ambiente de desenvolvimento integrado (IDE) focado em PHP, construído sobre a plataforma Apache NetBeans. O objetivo do projeto é oferecer uma experiência enxuta e otimizada para desenvolvimento PHP — removendo os módulos desnecessários e aprimorando a interface.

## Sobre este projeto

Este é um **fork do [Apache NetBeans](https://netbeans.apache.org/)**, mantido de forma independente. Ele preserva a arquitetura modular e robusta do NetBeans, mas restringe o conjunto de módulos compilados aos necessários para o desenvolvimento PHP.

* Baseado no código-fonte do Apache NetBeans (licença Apache 2.0 — ver [LICENSE](LICENSE) e [NOTICE](NOTICE)).
* Clusters compilados: `platform`, `ide`, `webcommon` e `php` (veja [docs/estrutura-do-projeto.md](docs/estrutura-do-projeto.md)).
* Identidade visual própria (marca, splash, ícones) — assets em `brand/`, aplicados pelo módulo `php/phpforge.branding`.
* Interface estilo JetBrains: tema escuro **PHPForge Dark** (paleta JetBrains New UI Dark: superfícies `#2B2D30`, editor `#2B2B2B`, seleção `#3574F0`, seleção inativa `#2E436E`, acento mint nas abas), menu bar fundida com a toolbar em faixa única, abas slim com underline azul, fonte JetBrains Mono no editor, Inter na UI, **icon set Lucide** monocromático na toolbar/árvores, Dashboard de boas-vindas e abas com indicador de modificado — módulos `phpforge.ui` e `phpforge.icons`.

## Requisitos

* Linux (outros sistemas podem funcionar, mas não são o foco)
* Git
* Ant 1.10+
* JDK 21 (obrigatório — o código é compilado com `--release 21`)

## Configurando o ambiente

Certifique-se de que o `JAVA_HOME` aponte para o JDK 21 antes de compilar:

```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
```

> Dica: adicione a linha acima ao seu `~/.bashrc` para não precisar exportar em toda sessão.

## Compilando para desenvolvimento

Para compilar a IDE com a configuração PHP e abri-la logo em seguida:

```bash
cd nbbuild
ant -Dcluster.config=php tryme
```

Para apenas compilar, sem abrir a IDE:

```bash
cd nbbuild
ant -Dcluster.config=php build-nozip
```

O resultado fica em `nbbuild/netbeans/`, já com o executável `bin/netbeans` e o arquivo `etc/netbeans.clusters` instalados. Para executar manualmente:

```bash
nbbuild/netbeans/bin/netbeans --userdir ~/.phpforge/dev
```

Sem o `--userdir`, a IDE usaria o diretório de usuário padrão (`~/.phpforge/<versão>`) — recomenda-se manter um diretório separado para não misturar com outras instalações.

## Gerando a versão de produção

O build de produção gera um arquivo ZIP pronto para distribuição. Use a propriedade `buildnumber` para definir o número da versão:

```bash
cd nbbuild
ant -Dcluster.config=php -Dbuildnumber=1.0.0 build
```

O pacote será gerado em:

```
nbbuild/PHPForge-1.0.0-php.zip
```

O ZIP já contém o executável `bin/netbeans`, a configuração em `etc/` e toda a identidade visual do PHPForge IDE (splash, ícones, tela inicial).

Para limpar completamente os artefatos de build antes de uma compilação limpa:

```bash
cd nbbuild
ant clean
```

## Documentação

* [docs/estrutura-do-projeto.md](docs/estrutura-do-projeto.md) — para que serve cada pasta do projeto.

## Licença

Apache License 2.0. Ver [LICENSE](LICENSE).
