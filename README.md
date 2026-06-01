# Jogo da Velha Supremo 🕹️

Projeto desenvolvido em Java que expande o jogo da velha tradicional para uma versão estratégica, onde cada casa do tabuleiro principal contém um minijogo completo.

## Divisão de Responsabilidades

### Giovana
- Implementação da lógica de verificação de vitória dos minitabuleiros.
- Implementação da lógica de verificação de vitória do tabuleiro macro.

### Hevelly
- Criação das matrizes do jogo (tabuleiros 9x9 e 3x3).
- Desenvolvimento da renderização do tabuleiro no console (`desenharTabuleiro`).
- Estrutura do loop principal e lógica de alternância de turnos (X e O).
- Sistema de validação de jogadas (casas ocupadas e entradas inválidas).
- Cálculo do direcionamento para o próximo minitabuleiro.
- Criação da estrutura base das funções de verificação de vitória.

## Funcionalidades Implementadas

O jogo já possui:

1. Exibição dinâmica do tabuleiro.
2. Controle automático de turnos.
3. Validação de entradas e jogadas.
4. Direcionamento automático para o próximo minitabuleiro.
5. Estrutura preparada para verificação de vitórias locais e globais.

## Tecnologias Utilizadas

- Java
- IntelliJ IDEA
- Git
- GitHub