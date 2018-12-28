#include<stdio.h>//This header file contains declarations used in most input and output and is typically included in all C programs.
#include<stdlib.h>
#include<unistd.h>
#include<netinet/in.h>//The header file in.h contains constants and structures needed for internet domain addresses
#include<string.h>
#include<netdb.h>
#include <sys/types.h>// This header file contains definitions of a number of data types used in system calls. These types are used in the next two include files.
#include <sys/socket.h>// The header file socket.h includes a number of definitions of structures needed for sockets.

int  main(int argc, char *argv[])
{ 
	struct sockaddr_in serversocket;
  	int fd,i,newfd;
	char buffer[500];
if (argc < 3) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       exit(0);
    }
        printf("\tThis is Chat Client.\n");
       

        fd=socket(AF_INET,SOCK_STREAM,0);//int socket(domain, type, protocol)

        if(fd<0)
                printf("Socket Could Not Be Created");
        else
                printf("Socket Created Succesfully");

        printf("\nListensocket Value : %d",fd);
	
	 bzero((char *)&serversocket,sizeof(serversocket));
	 
        serversocket.sin_family=AF_INET;
        serversocket.sin_port=htons(atoi(argv[2]));
        serversocket.sin_addr.s_addr=INADDR_ANY;
      	i = connect(fd,(struct sockaddr *)&serversocket,sizeof(serversocket)); /*The connect() system call connects the socket referred to by the file descriptor sockfd to the address specified by addr. The addrlen argument specifies the size of addr.*/

        if(i==0)
                printf("\nConnect SUCCESS\n");
        else
                printf("\nConnect ERROR\n");
	
FILE *fp;
char ch;
int j=0;

	fp=fopen("received.txt","ab");
	if(fp!=NULL)
	{
		int n;	
		bzero(&buffer,500);	
		read(fd,&buffer,sizeof(buffer));
	 	printf("%s\n",buffer);
		
		fprintf(fp,buffer,500);
		 
fclose(fp);
close(fd);
return 0;
}
}
