
#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <strings.h>


#define buffersize 4096  //for music.mp3 file

//#define buffersize 512   // for index.jpeg





struct sockaddr_in sock_serv;
struct stat buffer;

int main (int argc, char *argv[]){
	
    int sfd,fd;
     
    char buf[buffersize];
    long int count=0, m,sz;//long
	long int n;


    
	if (argc < 4){
		printf("Error usage : %s <ip_serv> <port_serv> <filename>\n",argv[0]);
		exit(0);
		//return EXIT_FAILURE;
	}
    
    
  
	
    
	sfd = socket(AF_INET,SOCK_DGRAM,0); //SOCK_DGRAM for UDP
	if (sfd == -1)
	{
       		perror("socket fail");
       		exit(0);
        //return EXIT_FAILURE;
	}
    
       int l=sizeof(struct sockaddr_in);
	
	bzero(&sock_serv,l);
	
	sock_serv.sin_family=AF_INET;
	sock_serv.sin_port=htons(atoi(argv[2])); //hostname and port no are passed in terminal (stored in argv[] array)

	sock_serv.sin_addr.s_addr=inet_addr(argv[1]);
	

    
	if ((fd = open(argv[3],O_RDONLY))==-1)
	{
		perror("open fail");
		exit(0);
		//return EXIT_FAILURE;
	}
    
	
	if (stat(argv[3],&buffer)==-1) /*The functions take a struct stat buffer argument, which is used to return the file attributes. functions take a filename argument. If the file is a symbolic link, stat() returns attributes of the eventual target of the link, */
	{
		perror("stat fail");
		exit(0);
		//return EXIT_FAILURE;
	}
	else
		sz=buffer.st_size;
		printf("\n sz %ld:",sz);
    
	
	bzero(&buf,buffersize);
    
	  n=read(fd,buf,buffersize); /*The read() function shall attempt to read 'n' bytes from the file associated with the open file descriptor 'fd', into the buffer pointed to by buf. */
	  
	 
	while(n){
		if(n==-1){
			perror("read fails");
			exit(0);
			//return EXIT_FAILURE;
		}
		m=sendto(sfd,buf,n,0,(struct sockaddr*)&sock_serv,l); //The sendto() function shall send a message through a connection-mode or connectionless-mode socket. If the socket is connectionless-mode, the message shall be sent to the address specified by 3rd argument (socket object). If the socket is connection-mode, 3rd argument is ignored.

		 sleep(1);	       //delay has been introduced for larger files
		printf(" \nm %ld:", m);
		if(m==-1){
			perror("send error");
			exit(0);
			//return EXIT_FAILURE;
		}
		count+=m;
		
		bzero(buf,buffersize);
        	n=read(fd,buf,buffersize);
	}

	
	
	m=sendto(sfd,buf,0,0,(struct sockaddr*)&sock_serv,l);
	 
	printf("Number of bytes transferred : %ld\n",count);
	
    
    close(sfd);
    close(fd);
	return 0;
}

/*
$ ./cli 127.0.0.1 5004 index.jpeg

 sz 3465: 
m 512: 
m 512: 
m 512: 
m 512: 
m 512: 
m 512: 
m 393:Number of bytes transferred : 3465
*/

