#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <math.h>
#include <graphics.h>

int genislik= 800;
int uzunluk=800;

//Gönderilen iki nokta arasındaki uzaklığı hesaplayıp onu döndürür.
double distance(double x1,double y1,double x2,double y2)
{
    double dist = sqrt(pow((x1-x2),2) + pow((y1-y2),2));
    return dist;
}

//Göndeilen noktanın aldığı çember merkezi ve yarıçapa göre çemberin içnde olup olmadığını kontrol eder
int in_inside(double center_x,double center_y,int x,int y,double rad)
{
    if(distance(center_x,center_y,x,y)<=rad)
        return 1;
    return 0;
}


void disarda_mi(double center_x,double center_y,int array_x[],int array_y[],int size,double radius,double array[])
{
    //Tüm noktaların 3'lü kombinasyonlarını dener. Bu da yaklaşık 8.000.000 denemeye denk geliyo.
    for(double i=-1; i<1; i+=0.01)
    {
        for(double j=-1; j<1; j+=0.01)
        {
            for(double z=-1; z<1; z+=0.01)
            {
                //Merkez ve yarıçapı çok küçük değerde sürekli öteler
                double x = center_x+i;
                double y =center_y+j;
                double r = radius+z;
                int gecerli_mi=1;
                //Tm noktaların içinde olup olmadığının kontrolü yapılır
                for (int t=0; t<size; t++)
                {
                    if(in_inside(x,y,array_x[t],array_y[t],r) == 0)
                    {
                        gecerli_mi = 0;
                        break;
                    }
                }
                //Eğer içindeyse yeni merkez koordinatları ve yarıçap diziye atanır
                if(gecerli_mi == 1 && array[2]>r)
                {

                    array[0] = x;
                    array[1] = y;
                    array[2] = r;
                }
            }
        }
    }

}

//Gönderilen verilere göre çizgi çizdirir.
void drawLine(int moveToX, int moveToY,int cizX,int cizY,int color,int textX,int textY,char *name)
{
    moveto(moveToX, moveToY);
    setcolor(color);
    lineto(cizX, cizY);
    outtextxy(textX, textY, name);

}


void spline(double a,double *p,int array_x[],int array_y[])
{
    int p1, p2, p3, p4;
    //4 Nokta alınır baş ve son noktalar kontrol noktaları olur .
    p2 = int(a) + 1;
    p3 = p2 + 1;
    p4 = p3 + 1;
    p1 = p2 - 1;
    a = a - int(a);

    //Burda noktalara olan etki hesaplanır
    double aa = a * a;
    double aaa = aa* a;
    double b1 = -aaa + (2.0 * aa) - a;
    double b2 = (3.0 * aaa) - (5.0 * aa) + 2.0;
    double b3 = (-3.0 * aaa) + (4.0 * aa) + a;
    double b4 = aaa - aa;

    // Noktaların hepsinin bu a değerine göre çizdirilecekeğriye bir etkisi vardır. Bu etkilere göre eğrinin tnoktasındaki koordinatı belirlenir
    p[0] =  0.5 * ((array_x[p1] * b1) + (array_x[p2] * b2) + (array_x[p3] * b3) + (array_x[p4] * b4));
    p[1] =  0.5 * ((array_y[p1] * b1) + (array_y[p2] * b2) + (array_y[p3] * b3) + (array_y[p4] * b4));
}


void draw_spline(int array_x[],int array_y[],int uzunluk)
{
    int array_x2[uzunluk+2];
    int array_y2[uzunluk+2];
    //Pixe olarak kooridnat düzleminin başlangıç değeri atanır
    array_x2[0] = 0;
    array_y2[0] = 400;

    //Yeni bir dizi atanarak başlangıç değerleri kaybedilmez
    for (int k=1; k<uzunluk+1; k++)
    {
        array_x2[k] = array_x[k-1];
        array_y2[k] = array_y[k-1];
    }
    //Son değerleride koordinar düzleminin son pixelleri olur
    array_x2[uzunluk+1] = 800;
    array_y2[uzunluk+1] = 400;

    double x = array_x2[1];
    double y = array_y2[1];
    array_x[uzunluk] = 400;
    array_y[uzunluk] = 400;

    //Btün koordinatlar için katsayılar ve noktalar tek tek fonksiyonlara gönderilerek eriler çizdirilir

    for (double t = 0.00; t < double(uzunluk+2) - 3.0 ; t+= 0.001)
    {
        //array[0] ve array[1] arası çizdirilir
        double array[2];
        spline(t, array, array_x2,array_y2);
        line(x, y, array[0], array[1]);
        x = array[0];
        y = array[1];
    }

}

//Gönderilenlere göre nokta yazdırır (koordiant düzlemi üzerinde)
void Sayilar(int tasiX,int tasiY,int color,int rakam1,int rakam2,char* sayi)
{
    moveto(tasiX,tasiY);
    outtextxy(rakam1, rakam2,sayi);
}


int control(double center_x,double center_y,int array_x[],int array_y[],int size,double radius)
{
    //Bütün noktaların çemberin içinde olup olmadığını kontrol eder.İçindeyse 0 Değilse 1 döndürür.
    int temp =0;
    for (int i=0; i<size; i++)
    {
        double dist1 = distance(center_x,center_y,array_x[i],array_y[i]);

        if(dist1>radius)
        {
            return 1;
        }
    }
    return 0;
}


//Gönderilen en uzak iki noktanın arasındaki uzaklığın yarıçap olarak döndürür
double radius(int a,int b,int c,int d)
{
    double dist = distance(a,b,c,d);
    double radius = dist/2;
    return radius;
}

//Dışarda nokta kalıyosa tüm noktaların ağırlık merkezini çemberin merkez koordinatları olarak atar.
void circle_center2 (int array_x[],int array_y[],int size,double array[3])
{
    int x_toplam=0,y_toplam =0;
    double temp =0,rad=0;
    //Tüm noktaların toplamı bulunur.
    for (int i=0; i<size; i++)
    {
        x_toplam += array_x[i];
        y_toplam += array_y[i];
    }
    //Ağırlşık Merkezi bulunur
    double center_x = x_toplam/size;
    double center_y = y_toplam/size;

    //merkeze en uzak nokta bulunarak yarıçap bulunur
    for (int i=0; i<size; i++)
    {
        double dist = distance(center_x,center_y,double(array_x[i]),double(array_y[i]));
        if (dist >rad)
            rad = dist;
    }
    //bulunan merkez koordiantları ve yarıçap fonksiyona gönderilen diziye atanır.
    array[0]=center_x;
    array[1]=center_y;
    array[2]=rad;
}

void circle_center (int a,int b,int c,int d,double array[3])
{
    //Eğer verilen noktalar arasında biribirine uzaklığı eşit olan birden fazla nokta yoksa bu fonksiyona gelir
    //Yaıçap ve merkez noktalrı birbirine en uzak iki noktanın ortası kabul edilir.
    double rad = radius(a,b,c,d);
    double middle = double(a-c) / 2;
    double center_x = a-middle;
    double middle2 = double(b-d) / 2;
    double center_y = b-middle2;
    array[0]=center_x;
    array[1]=center_y;
    array[2]=rad;
}

//Alınan notalarda en uzak iki noktayı bulur
void find_farthermost(int array1[],int array2[],int size,int array3[2])
{
    int temp2 =0,temp3 =0,i,j;
    double temp=0.0,dist =0.0;

    //Tüm noktaları gezer
    for (i=0; i<size-1; i++)
    {
        for (j=i+1; j<size; j++)
        {
            //uzaklığı hesaplar
            dist = distance(array1[i],array2[i],array1[j],array2[j]);
            if (dist>temp)
            {
                //İf kullanılarak en uzak olan noktaların indisleri değişkenlerle tutulur
                temp = dist;
                temp2 = i;
                temp3 = j;
            }
        }

    }
    //İndexler diziye atılır
    array3[0] = temp2;
    array3[1] = temp3;

}


int main()
{
    //Dosya tanımlama
    FILE *file;
    char karakter,array[100];
    int array3[100];
    int i=0,j=0,a=0,b=0;
    int further[2];
    double center_rad[3];
    //Dosya açma
    file = fopen("C:\\Coordinations.txt","r+");

    //Dosyanın içi boş mu değil mi kontrol etme
    if (file == NULL)
    {
        printf("Dosya Acilamadi...");
    }
    else
    {
        //Tüm karakterler alınaraqk bir diziye atanır
        while (karakter != EOF)
        {
            karakter=fgetc(file);
            array[j] = karakter;
            j++;
        }
        //Dosyaddaki tüm karakterler sayı mı değil mi diye kontrol edilir
        for (int k=0; k<j; k++)
        {
            if (isdigit(array[k]))
            {
                //Sayının iki basamklı olup olmadığı kontrol edilir
                if (isdigit(array[k]) && isdigit(array[k+1]))
                {
                    //Eğer sayı iki basamaklı işe burda kontrolü yapılıp iki rakam iki basamaklı sayıya çevrilir
                    int toplam = 10*(array[k] - '0') + (array[k+1] - '0');
                    array3[i] = toplam;
                    if(array[k-1] == '-')
                    {
                        array3[i] *= -1;
                    }
                    k++;

                }
                else
                {
                    array3[i] = array[k] - '0';
                }
                //Eğer sayıdan önceki karakter '-2 ise sayı negatif e döndürülür
                if(array[k-1] == '-')
                {
                    array3[i] *= -1;
                }
                i++;


            }

        }
    }

    //Alınan rakam sayısının yarısı kadar bir X ve bir Y sizisi oluşturulur

    int array_x[j/2],array_y[j/2];

    //Sırayla birinci sayı X dizisine ikinci sayı Y dizisine atılır
    for (int k=0; k<i; k++)
    {

        if(k%2==0)
        {
            array_x[a] =  array3[k];
            a++;
        }
        else
        {
            array_y[b] =  array3[k];
            b++;
        }
    }

    //Tüm indexler gezilerek X ve y dizileri X dizisine gre sıralanır.Spline eğrisinin birbiri üzerinden geememesi için.
    for (int k=0; k<i/2-1; k++)
    {
        for(int l=k+1; l<i/2; l++)
        {
            if(array_x[k]>array_x[l])
            {
                int temp = array_x[k];
                array_x[k] = array_x[l];
                array_x[l] = temp;
                int temp2 =array_y[k];
                array_y[k] = array_y[l];
                array_y[l] = temp2;
            }
        }
    }

    //En uzak iki nokta bulunur

    find_farthermost(array_x,array_y,i/2,further);
    //Uzak noktalara göre merkez ve yarıçap belirlenir
    circle_center(array_x[further[0]],array_y[further[0]],array_x[further[1]],array_y[further[1]],center_rad);
    //Dışarda nokta kalıp kalmadığının kontrolü yapılır
    int ctrl = control(center_rad[0],center_rad[1],array_x,array_y,i/2,center_rad[2]);

    // Eğer varsa yeni merkez koordinatları ve yarıçap belirlenir
    if (ctrl == 1)
    {
        circle_center2(array_x,array_y,i/2,center_rad);
    }

    //Tü noktaların 3'lğ kombinasyonalrı denenerke merkez koordinatlar ve yarıçap ın doğru olduğu garnatşlenir.

    disarda_mi(center_rad[0],center_rad[1],array_x,array_y,i/2,center_rad[2],center_rad);

    int s=0;
    char c[4];
    //Bİr pencere açılır
    initwindow(genislik,uzunluk);
    //Ölçüleri bellirlenir
    moveto(genislik/2, uzunluk/2);

    //Kooridnat düzlemi üzerine +x noktalrı yazdırılır
    for(int a=-400; a<genislik/40; a+=20)
    {
        if (s>=10)
        {
            char a2 = '0' + (s%10);
            char a1 = '0' + (s/10);
            c[0] = a1;
            c[1] = a2;
            c[2] = '\0';
            s++;
            Sayilar(0,uzunluk/2,WHITE,genislik+a,uzunluk/2,c);
        }
        else
        {
            char o = '0' + s;
            c[0] = o;
            c[1] = '\0';
            Sayilar(0,uzunluk/2,WHITE,genislik+a,uzunluk/2,c);
            s++;
        }
    }
    s=1;
    //Kooridnat düzlemi üzerine +y noktalrı yazdırılır
    for(int a=20; a<400; a+=20)
    {
        if (s>=10)
        {
            char a2 = '0' + (s%10);
            char a1 = '0' + (s/10);
            c[0] = a1;
            c[1] = a2;
            c[2] = '\0';
            s++;
            Sayilar(0,uzunluk/2,WHITE,genislik/2,(uzunluk/2)-a,c);
        }
        else
        {
            char o = '0' + s;
            c[0] = o;
            c[1] = '\0';
            Sayilar(0,uzunluk/2,WHITE,genislik/2,(uzunluk/2)-a,c);
            s++;
        }
    }

    s=1;
    //Kooridnat düzlemi üzerine -y noktalrı yazdırılır
    for(int a=20; a<400; a+=20)
    {
        if (s>=10)
        {
            char a2 = '0' + (s%10);
            char a1 = '0' + (s/10);
            c[0] ='-';
            c[1] = a1;
            c[2] = a2;
            c[3] = '\0';
            s++;
            Sayilar(0,uzunluk/2,WHITE,genislik/2-a,uzunluk/2,c);
        }
        else
        {
            char o = '0' + s;
            c[0] ='-';
            c[1] = o;
            c[2] = '\0';
            Sayilar(0,uzunluk/2,WHITE,genislik/2-a,uzunluk/2,c);
            s++;
        }
    }

    s=1;
    //Kooridnat düzlemi üzerine -x noktalrı yazdırılır
    for(int a=20; a<400; a+=20)
    {
        if (s>=10)
        {
            char a2 = '0' + (s%10);
            char a1 = '0' + (s/10);
            c[0] ='-';
            c[1] = a1;
            c[2] = a2;
            c[3] = '\0';
            s++;
            Sayilar(0,uzunluk/2,WHITE,genislik/2,(uzunluk/2)+a,c);
        }
        else
        {
            char o = '0' + s;
            c[0] ='-';
            c[1] = o;
            c[2] = '\0';
            Sayilar(0,uzunluk/2,WHITE,genislik/2,(uzunluk/2)+a,c);
            s++;
        }
    }

    //Kooridnat düzleminin x ve y eksenlerinin isimleri gösterilir
    drawLine(0,uzunluk/2,genislik,uzunluk/2,WHITE,genislik,uzunluk / 2 + 10,"X");
    drawLine(genislik / 2,0, genislik/2,uzunluk,WHITE,genislik/2+10,0,"Y");

    double center_x,center_y,radp;

    //Yarıçap pixel cinsine dönüştürülür
    radp = center_rad[2] * 20;
    //Merkezin X ve Y koordinatları pixel cinsine dönüştürülür
    center_x = 400 + (center_rad[0]*20);
    center_y = 400 - (center_rad[1]*20);

    //Merkez koordiantları ve Yarıçap yazdıırlır
    printf("Merkez : {%f,%f}\n Yaricap: %f ",center_rad[0],center_rad[1],center_rad[2]);

    //Dosyadan alınan noktalar ekrana çizdirilir
    for (int k=0; k<i/2; k++)
    {
        fillellipse(400 + (array_x[k]*20),400 - (array_y[k] *20),5,5);
    }

    //Dosyadan alınan tüm noktalar pixel cinsine dönüştürülür
    for (int k=0; k<i/2; k++)
    {
        array_x[k] = 400 + (array_x[k]*20);
        array_y[k] = 400 - (array_y[k] *20);
    }
    //Spline eğrisi çizdirilir
    draw_spline(array_x,array_y,i/2);
    //Merkez noktası çizdirilir
    fillellipse(center_x,center_y,7,7);
    //Çember çizdirilir
    circle(center_x,center_y,radp);
    //pencere kapatılır
    getch();
    closegraph();
    //Dosya kapatılır
    fclose(file);
    return 0;
}
