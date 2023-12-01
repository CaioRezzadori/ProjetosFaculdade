#include <stdio.h>
#include <stdlib.h>
#include "listaEncadeada.h"

typedef struct no_st no;

struct no_st{
    unsigned valor;
    no *proximo;
};

void criar_lista(lista *l){
    l->inicio = NULL;
    l->fim = NULL;
    l->tamanho = 0;
}



int remover_lista(lista *l, unsigned elemento){
    no *aux = l->inicio;
    no *elemento_removido;
    if(aux->valor == elemento){
        // no *elemento_removido = aux;
        l->inicio = l->inicio->proximo;
        free(aux);
        aux = NULL;
        l->tamanho--;
        return 0;
    }

    while(aux->proximo != NULL && aux->proximo->valor != elemento){
        aux = aux->proximo;
    }

    if(aux->proximo == NULL){
        return 1;
    }

    else if(aux->proximo == l->fim){
        elemento_removido = aux->proximo;
        l->fim = aux;
        free(elemento_removido);
        elemento_removido = NULL;
        l->tamanho--;
        return 0;
    }
    elemento_removido = aux->proximo;
    aux->proximo = aux->proximo->proximo;
    free(elemento_removido);
    elemento_removido = NULL;
    l->tamanho--;
    return 0;
    
}

void destruir_lista(lista *l){
    while(l->tamanho > 0){
        remover_lista(l, l->fim->valor);
    }    
}

void inserir_fim_lista(lista *l, unsigned elemento){
    no *elemento_inserido = (no *) malloc(sizeof(no));
    elemento_inserido->valor = elemento;
    elemento_inserido->proximo = NULL;
    if(l->inicio == NULL){
        l->inicio = elemento_inserido;
        l->fim = elemento_inserido;
    }
    else{
        l->fim->proximo = elemento_inserido;
        l->fim = l->fim->proximo;
    }    
    l->tamanho++;
}

void imprimir_lista(lista l){
    no *aux = l.inicio;
    printf("(");
    for(int i = 0; i < l.tamanho; i++){
        printf("%d, ", aux->valor);
        aux = aux->proximo;
    }
    printf("\b\b)\n");
}


int buscar_lista(lista l, unsigned elemento){
    if(l.tamanho == 0){
        return 0;
    }
    no *aux = l.inicio;
    // Tratando primeiro elemento
    if(aux->valor == elemento){
        return 1; // Elemento encontrado 
    }

    while(aux != l.fim){
        aux = aux->proximo;
        if(aux->valor == elemento){
            return 1; // Elemento encontrado 
        }
    } 

    return 0; // Elemento nao encontrado
} //(Os valores 0 e 1 foram utilizados para facilitar a contagem de elementos encontrados no hash)

int tamanho_lista(lista l){
    return l.tamanho;
}