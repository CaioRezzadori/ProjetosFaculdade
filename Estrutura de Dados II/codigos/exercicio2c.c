/*

Projeto 2 ED II

Gabriel Sanches da Silva - N.USP: 11884693
Caio Assumpcao Rezzadori - N.USP: 11810481
Joao Marcelo R. Junior- N.USP: 8531118

*/


#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <assert.h>
#include <string.h>
#include <math.h>
#include "listaEncadeada.h"

// Definição das variaveis que controlam a medição de tempo
clock_t _ini, _fim;

// Definição do tipo booleano
typedef unsigned char bool;
#define TRUE  1
#define FALSE 0

// Definição do tipo string
typedef char * string;

#define MAX_STRING_LEN 20


unsigned converter(string s) {
   unsigned h = 0;
   for (int i = 0; s[i] != '\0'; i++) 
      h = h * 256 + s[i];
   return h;
}

string* ler_strings(const char * arquivo, const int n)
{
    FILE* f = fopen(arquivo, "r");
    
    string* strings = (string *) malloc(sizeof(string) * n);

    for (int i = 0; !feof(f); i++) {
        strings[i] = (string) malloc(sizeof(char) * MAX_STRING_LEN);
        fscanf(f, "%s\n", strings[i]);
    }

    fclose(f);

    return strings;
}

void inicia_tempo()
{
    srand(time(NULL));
    _ini = clock();
}

double finaliza_tempo()
{
    _fim = clock();
    return ((double) (_fim - _ini)) / CLOCKS_PER_SEC;
}



//Funcoes hash
struct tabela_hash{
    lista *chaves;
};

typedef struct tabela_hash hash;

void criar_hash(hash *t, unsigned B){
    t->chaves = (lista *) malloc(sizeof(lista) * B);
    for(int i = 0; i < B; i++){
        criar_lista(&t->chaves[i]); //Indica posicao vazia e nunca utilizada
    }
    return;
}

void destruir_hash(hash *t, unsigned B){
    for(int i = 0; i < B; i++){
        destruir_lista(&t->chaves[i]); //Indica posicao vazia e nunca utilizada
    }
    free(t->chaves);
    return;
}

int inserir_hash(hash *t, unsigned x, unsigned (*funcao_hash) (unsigned, unsigned), unsigned B){
    unsigned posicao = (*funcao_hash)(x, B);
    if(buscar_lista((*t).chaves[posicao], x) == 0){
        inserir_fim_lista(&t->chaves[posicao], x);
        return tamanho_lista((*t).chaves[posicao]) - 1;
    }
    return 0;

}


void remover_hash(hash *t, unsigned x, unsigned (*funcao_hash) (unsigned, unsigned), unsigned B){
    unsigned posicao = (*funcao_hash)(x, B);
    remover_lista(&t->chaves[posicao], x);
}

int buscar_hash(hash t, unsigned x, unsigned (*funcao_hash) (unsigned, unsigned), unsigned B){
    unsigned posicao = (*funcao_hash)(x, B);
    return buscar_lista(t.chaves[posicao], x);
}

unsigned h_div(unsigned x, unsigned B)
{
    return x % B;
}

unsigned h_mul(unsigned x, unsigned B)
{
    const double A = 0.6180;
    return fmod(x * A, 1) * B;
}



int main(int argc, char const *argv[])
{
    const int N = 50000;
    const int M = 70000;
    const int B = 150001;

    unsigned colisoes_h_div = 0;
    unsigned colisoes_h_mul = 0;

    unsigned encontrados_h_div = 0;
    unsigned encontrados_h_mul = 0;

    string* insercoes = ler_strings("strings_entrada.txt", N);
    string* consultas = ler_strings("strings_busca.txt", M);
    

    //////////////////////////////////////////////////////


    // cria tabela hash com hash por divisão
    hash tabela_div;
    criar_hash(&tabela_div, B);

    // inserção dos dados na tabela hash com hash por divisão
    inicia_tempo();
    for (int i = 0; i < N; i++) {
        // inserir insercoes[i] na tabela hash
        colisoes_h_div += inserir_hash(&tabela_div, converter(insercoes[i]), h_div, B);
    }
    double tempo_insercao_h_div = finaliza_tempo();

    // busca dos dados na tabela hash com hash por divisão
    inicia_tempo();
    for (int i = 0; i < M; i++) {
        // buscar consultas[i] na tabela hash
        encontrados_h_div += buscar_hash(tabela_div, converter(consultas[i]), h_div, B);
    }
    double tempo_busca_h_div = finaliza_tempo();

    // destroi tabela hash com hash por divisão
    destruir_hash(&tabela_div, B);


    //////////////////////////////////////////////////////


    // cria tabela hash com hash por multiplicação
    hash tabela_mul;
    criar_hash(&tabela_mul, B);

    // inserção dos dados na tabela hash com hash por multiplicação
    inicia_tempo();
    for (int i = 0; i < N; i++) {
        // inserir insercoes[i] na tabela hash
        colisoes_h_mul += inserir_hash(&tabela_mul, converter(insercoes[i]), h_mul, B);

    }
    double tempo_insercao_h_mul = finaliza_tempo();

    // busca dos dados na tabela hash com hash por multiplicação
    inicia_tempo();
    for (int i = 0; i < M; i++) {
        // buscar consultas[i] na tabela hash
        encontrados_h_mul += buscar_hash(tabela_mul, converter(consultas[i]), h_mul, B);

    }
    double tempo_busca_h_mul = finaliza_tempo();

    // destroi tabela hash com hash por multiplicação
    destruir_hash(&tabela_mul, B);


    //////////////////////////////////////////////////////


    printf("Hash por Divisão\n");
    printf("Colisões na inserção: %d\n", colisoes_h_div);
    printf("Tempo de inserção   : %fs\n", tempo_insercao_h_div);
    printf("Tempo de busca      : %fs\n", tempo_busca_h_div);
    printf("Itens encontrados   : %d\n", encontrados_h_div);
    printf("\n");
    printf("Hash por Multiplicação\n");
    printf("Colisões na inserção: %d\n", colisoes_h_mul);
    printf("Tempo de inserção   : %fs\n", tempo_insercao_h_mul);
    printf("Tempo de busca      : %fs\n", tempo_busca_h_mul);
    printf("Itens encontrados   : %d\n", encontrados_h_mul);

    return 0;
}
