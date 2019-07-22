package com.rail.zWebSocket;

import java.util.Scanner;

public class TestServer {

	public static void main(String[] args) {
        ServerManager serverManager=new ServerManager();

        boolean isRun=true;
        Scanner scanner=new Scanner(System.in);
        System.out.println("Input your port or close input:");
        while(isRun){
            int input=scanner.nextInt();
            //输入如果是0标识关闭Server端口
            if (input==0) {
            	serverManager.Stop();
            	isRun=false;
			}else {
				//如果是其他值则启动对应的端口的服务端
				serverManager.Start(input,false);
			}
        }

	}

}
