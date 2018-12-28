//SagarSangale
//3172528

#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <time.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <strings.h>

#define buffersize 4096




struct sockaddr_in sock_serv,clt;

int main (int argc, char**argv){
 
	int fd, sfd;
    
	char buffer[buffersize];
	long int count=0, n; // long type
	char filename[256];
    unsigned int l=sizeof(struct sockaddr_in);
	
   	printf("\nsizeof(struct sockaddr_in) : %u ",l);
	
	if (argc <2){
		printf("Error usage : %s <port_serv>\n",argv[0]);
		exit(0);
		//return EXIT_FAILURE;
	}
    
 
    
	sfd = socket(AF_INET,SOCK_DGRAM,0); //SOCK_DGRAM for UDP
	if (sfd == -1){
        perror("socket fail");
        exit(0);
		//return EXIT_FAILURE;
	}
    
   
	bzero(&sock_serv,l);  //The bzero() function copies n bytes, each with a value of zero, into string
	
	sock_serv.sin_family=AF_INET;
	sock_serv.sin_port=htons(atoi(argv[1]));
	sock_serv.sin_addr.s_addr=htonl(INADDR_ANY);
    
	
	if(bind(sfd,(struct sockaddr*)&sock_serv,l)==-1)  /*The bind() system call binds a socket to an address, in this case the address of the current host and port number on which the server will run. It takes three arguments, the socket file descriptor, the address to which is bound, and the size of the address to which it is bound. The second argument is a pointer to a structure of type sockaddr, but what is passed in is a structure of type sockaddr_in, and so this must be cast to the correct type. This can fail for a number of reasons, the most obvious being that this socket is already in use on this machine. */
	{

		perror("bind fail");
		exit(0);
		//return EXIT_FAILURE;
	}
    
    
    
	
	bzero(filename,256);
	sprintf(filename,"New file");  //sprintf means string printf
	printf("Creating the output file : %s\n",filename);
    
	
	if((fd=open(filename,O_CREAT|O_WRONLY|O_TRUNC,0600))==-1){
		perror("open fail");
		exit(0);
		//return EXIT_FAILURE;
	}
    
	
	bzero(&buffer,buffersize);
    n=recvfrom(sfd,&buffer,buffersize,0,(struct sockaddr *)&clt,&l);
    
	while(n)
	{
		printf("%ld of data received \n",n);
		printf("Tadda... \n");
		if(n==-1){
			perror("read fails");
			exit(0);
			//return EXIT_FAILURE;
		}
		count+=n;
		write(fd,buffer,n);
		bzero(buffer,buffersize);
        n=recvfrom(sfd,&buffer,buffersize,0,(struct sockaddr *)&clt,&l);
        sleep(1);
	}
    
	printf("Number of bytes transferred: %ld \n",count);
    
    close(sfd);
    close(fd);
	return 0;
}

/*
$ ./ser 5004
sizeof(struct sockaddr_in) : 16 Creating the output file : New file
512 of data received 
512 of data received 
512 of data received 
512 of data received 
512 of data received 
512 of data received 
393 of data received 
Number of bytes transferred: 3465 
*/

