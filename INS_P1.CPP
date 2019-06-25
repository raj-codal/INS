//INS P1
/**
 *
 * @author Raj Dhanani
 */
#include<stdio.h>
#include<iostream.h>
#include<conio.h>

int isPrime(int x){
	int flag = 1;
	for(int i = 2 ;i < x; i++){
		if(x % i == 0){
			flag = 0;
		}
	}
	return flag;
}

int nthPrime(int n){
	int j=0,prev = 2;
	for(int count = 1; count <= n ; count++){
		while(1){
			if(isPrime(prev)){
				j = prev;
				prev++;
				break;
			}
			prev++;
		}
	}
	return j;
}

int GCD(int a,int b){
	int min = (a<b)?a:b;
	for(int c = min ; c > 1; c--){
		if(a%c == 0 && b%c == 0){
			return c;
		}
	}
	return 1;
}

int main(){
	clrscr();
	cout<<"Enter 2 numbers:\n";
	int a,b,n1,n2;
	cin>>a>>b;
	cout<<"GCD:"<<GCD(a,b);
	cout<<"Enter range for primes:\n";
	cin>>n1>>n2;
	cout<<"PRIMES ARE:";
	for(int i = n1 ; i <= n2; i++){
		cout<<nthPrime(i)<<"\n";
	}
	getch();

return 0;
}