#include <stdio.h>
#include <stdlib.h>
#include <locale.h>
#include <string.h>

struct node
{
    int kacTane;
    char kelime[30];
    struct node *next;
};

struct node *ilk=NULL, *son=NULL;

void basaEkle(int kacTane,char kelime[]){
    struct node *yeni = (struct node*) malloc(sizeof(struct node));
    yeni->kacTane=kacTane;
    strcpy(yeni->kelime,kelime);

    if (ilk == NULL){
        ilk = yeni;
        son =yeni;
        ilk->next = NULL;
    }

    else{
        yeni->next=ilk;
        ilk = yeni;
    }
}
/*https://drive.google.com/file/d/1GK1I7UzglF4UxiryH0dizwcQOFqfF4GB/view?usp=sharing */
void sonaEkle(int kacTane,char kelime[]){
    struct node *yeni = (struct node*) malloc(sizeof(struct node));
    yeni->kacTane=kacTane;
    strcpy(yeni->kelime,kelime);

    if (ilk == NULL){
        ilk = yeni;
        son = yeni;
        ilk->next = NULL;
    }

    else{
        son->next = yeni;
        son = yeni;
        son->next = NULL;
    }
}

void arayaEkle(struct node *ekle){
    struct node *gecici = (struct node*) malloc(sizeof(struct node));
    gecici = ilk;
    if(ilk == NULL) basaEkle(ekle->kacTane,ekle->kelime);

    else if (ilk != NULL && ilk->next == NULL){
        if(ilk->kacTane >= ekle->kacTane){
            sonaEkle(ekle->kacTane,ekle->kelime);
        }
        else{
            basaEkle(ekle->kacTane,ekle->kelime);
        }
    }

    else{
        struct node *gecici1 = (struct node*) malloc(sizeof(struct node));
        while(gecici != NULL){

            gecici1 = gecici->next;

            if (gecici1 == NULL){
                sonaEkle(ekle->kacTane,ekle->kelime);
                break;
            }

           else if (gecici->kacTane == ekle->kacTane || gecici1->kacTane == ekle->kacTane)
            {
                gecici->next = ekle;
                ekle->next = gecici1;
                break;
            }

            else if(gecici->kacTane > ekle->kacTane && gecici1->kacTane < ekle->kacTane){
                gecici->next = ekle;
                ekle->next = gecici1;
                break;
            }
            gecici = gecici->next;
        }

    }
}

void listele(){
    struct node *gez=ilk;
    while(gez!=NULL)
    {
        printf("%s = %d\n",gez->kelime,gez->kacTane);
        gez=gez->next;
    }

}

int varMi(struct node *bul){
    struct node *gecici;
    gecici = ilk;
    while (gecici != NULL)
    {
        if(strcmp(gecici->kelime,bul->kelime)==0){
            return 1;
        }
        gecici = gecici->next;
    }
    return 0;
}

int kacTaneVar(char kelime[]){
    int count = 0;
    FILE *fptr = fopen("C:\\a.txt", "r");

    char temp[30];

    while(!feof(fptr)){
         fscanf(fptr, "%s",&temp);
         if (strcmp(kelime,temp)==0) count++;
    }
    fclose(fptr);
    return count;

}

int main()
{
    setlocale(LC_ALL,"Turkish");

    FILE *fp = fopen("C:\\a.txt", "r");

    if(fp == NULL)
    {
        printf("Dosya Açılamadı..\n");
        return 1;
    }

    char temp[30];

    while(!feof(fp)){

        fscanf(fp, "%s",temp);
        int a = kacTaneVar(temp);

        struct node *yeni = (struct node*) malloc(sizeof(struct node));
        yeni->kacTane = a;
        yeni->next = NULL;
        strcpy(yeni->kelime,temp);
        if(varMi(yeni) == 0){
            arayaEkle(yeni);
        }
    }

    listele();



    return 0;
}
