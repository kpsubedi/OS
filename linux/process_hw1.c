#include <unistd.h>
#include <stdio.h>


int main() {
        pid_t pid;
        pid = fork();
        pid = fork();
        pid = fork();
        pid = fork();

        if (pid > 0 )
                printf("I am the parent of pid=%d!\n", pid);
        else if (!pid) {
                printf("I am the child!\n");
                printf("My parent process id = %d!\n", getpid());
                }
        else if (pid == -1)
                perror("fork");

        return 0;
}
